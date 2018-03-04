package org.insa.graph.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

import org.insa.graph.AccessRestrictions;
import org.insa.graph.AccessRestrictions.AccessMode;
import org.insa.graph.AccessRestrictions.AccessRestriction;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.GraphInformation;
import org.insa.graph.Node;
import org.insa.graph.Point;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;

public class BinaryGraphReaderInsa2018 extends BinaryReader implements GraphReader {

    // Map version and magic number targeted for this reader.
    private static final int VERSION = 5;
    private static final int MAGIC_NUMBER = 0x208BC3B3;

    // Length of the map id field (in bytes)
    protected static final int MAP_ID_FIELD_LENGTH = 32;

    /**
     * Create a new access information by parsing the given value (V6 version).
     * 
     * @param access
     * @return
     */
    protected static AccessRestrictions toAccessInformationV7(final long access) {

        // See the following for more information:
        // https://github.com/Holt59/OSM2Graph/blob/master/src/main/org/laas/osm2graph/model/AccessData.java

        // The order of values inside this array is VERY IMPORTANT: For allRestrictions,
        // the order correspond to the 4 bits value (i.e. FORBIDDEN is 0 or PRIVATE is
        // 2) - UKNOWN is not included because value above 6 (FORESTRY) are all
        // considered unknown.
        final AccessRestriction[] allRestrictions = new AccessRestriction[] { AccessRestriction.FORBIDDEN,
                AccessRestriction.ALLOWED, AccessRestriction.PRIVATE, AccessRestriction.DESTINATION,
                AccessRestriction.DELIVERY, AccessRestriction.CUSTOMERS, AccessRestriction.FORESTRY };

        // The order of values inside this array is VERY IMPORTANT: The order is such
        // that each 4-bits group of the long value is processed in the correct order,
        // i.e. FOOT is processed first (4 lowest bits), and so on.
        final AccessMode[] allModes = new AccessMode[] { AccessMode.FOOT, null, AccessMode.BICYCLE,
                AccessMode.SMALL_MOTORCYCLE, AccessMode.AGRICULTURAL, AccessMode.MOTORCYCLE, AccessMode.MOTORCAR,
                AccessMode.HEAVY_GOODS, null, AccessMode.PUBLIC_TRANSPORT };

        // fill maps...
        EnumMap<AccessMode, AccessRestriction> restrictions = new EnumMap<>(AccessMode.class);
        long copyAccess = access;
        for (AccessMode mode: allModes) {
            if (mode == null) {
                continue; // filling cells
            }
            int value = (int) (copyAccess & 0xf);
            if (value < allRestrictions.length) {
                restrictions.put(mode, allRestrictions[value]);
            }
            else {
                restrictions.put(mode, AccessRestriction.UNKNOWN);
            }
            copyAccess = copyAccess >> 4;
        }

        return new AccessRestrictions(restrictions, access);
    }

    /**
     * Convert a character to its corresponding road type.
     * 
     * @param ch Character to convert.
     * 
     * @return Road type corresponding to ch.
     * 
     * @see http://wiki.openstreetmap.org/wiki/Highway_tag_usage.
     */
    protected static RoadType toRoadType(char ch) {
        switch (ch) {
        case 'a':
            return RoadType.MOTORWAY;
        case 'b':
            return RoadType.TRUNK;
        case 'c':
            return RoadType.PRIMARY;
        case 'd':
            return RoadType.SECONDARY;
        case 'e':
            return RoadType.MOTORWAY_LINK;
        case 'f':
            return RoadType.TRUNK_LINK;
        case 'g':
            return RoadType.PRIMARY_LINK;
        case 'h':
            return RoadType.SECONDARY_LINK;
        case 'i':
            return RoadType.TERTIARY;
        case 'j':
            return RoadType.RESIDENTIAL;
        case 'k':
        case 'l':
            return RoadType.UNCLASSIFIED;
        case 'm':
            return RoadType.LIVING_STREET;
        case 'n':
            return RoadType.SERVICE;
        case 'o':
            return RoadType.ROUNDABOUT;
        case 'p':
            return RoadType.PEDESTRIAN;
        case 'r':
            return RoadType.CYCLEWAY;
        case 's':
            return RoadType.TRACK;
        case 'z':
            return RoadType.COASTLINE;
        }
        return RoadType.UNCLASSIFIED;
    }

    /**
     * Create a new BinaryGraphReader using the given DataInputStream.
     * 
     * @param dis
     */
    public BinaryGraphReaderInsa2018(DataInputStream dis) {
        super(MAGIC_NUMBER, VERSION, dis);
    }

    @Override
    public Graph read() throws IOException {

        // Read and check magic number and file version.
        checkMagicNumberOrThrow(dis.readInt());
        checkVersionOrThrow(dis.readInt());

        // Read map id.
        String mapId;
        String mapName = "";

        if (getCurrentVersion() < 6) {
            mapId = "0x" + Integer.toHexString(dis.readInt());
        }
        else {
            mapId = readFixedLengthString(MAP_ID_FIELD_LENGTH, "UTF-8");
            mapName = dis.readUTF();
        }

        observers.forEach((observer) -> observer.notifyStartReading(mapId));

        // Number of descriptors and nodes.
        int nbDesc = dis.readInt();
        int nbNodes = dis.readInt();

        // Number of successors for each nodes.
        int[] nbSuccessors = new int[nbNodes];
        int nbTotalSuccessors = 0;

        // Construct an array list with initial capacity of nbNodes.
        ArrayList<Node> nodes = new ArrayList<Node>(nbNodes);

        // Read nodes.
        observers.forEach((observer) -> observer.notifyStartReadingNodes(nbNodes));
        for (int node = 0; node < nbNodes; ++node) {
            float longitude = ((float) dis.readInt()) / 1E6f;
            float latitude = ((float) dis.readInt()) / 1E6f;
            nbSuccessors[node] = dis.readUnsignedByte();
            nbTotalSuccessors += nbSuccessors[node];
            final Node aNode = new Node(node, new Point(longitude, latitude));
            nodes.add(aNode);
            observers.forEach((observer) -> observer.notifyNewNodeRead(aNode));
        }

        // Check format.
        checkByteOrThrow(255);

        // Read descriptors.
        RoadInformation[] descs = new RoadInformation[nbDesc];

        // Read
        observers.forEach((observer) -> observer.notifyStartReadingDescriptors(nbDesc));
        int maxSpeed = 0;
        for (int descr = 0; descr < nbDesc; ++descr) {
            final RoadInformation roadinf = readRoadInformation();
            descs[descr] = roadinf;
            observers.forEach((observer) -> observer.notifyNewDescriptorRead(roadinf));

            // Update max speed
            maxSpeed = Math.max(roadinf.getMaximumSpeed(), maxSpeed);
        }

        // Check format.
        checkByteOrThrow(254);

        // Read successors and convert to arcs.
        int maxLength = 0;
        final int copyNbTotalSuccesors = nbTotalSuccessors; // Stupid Java...
        observers.forEach((observer) -> observer.notifyStartReadingArcs(copyNbTotalSuccesors));
        for (int node = 0; node < nbNodes; ++node) {
            for (int succ = 0; succ < nbSuccessors[node]; ++succ) {

                // Read target node number.
                int destNode = this.read24bits();

                // Read information number.
                int descrNum = this.read24bits();

                // Length of the arc.
                int length = dis.readUnsignedShort();
                maxLength = Math.max(length, maxLength);

                // Number of segments.
                int nbSegments = dis.readUnsignedShort();

                // Chain of points corresponding to the segments.
                ArrayList<Point> points = new ArrayList<Point>(nbSegments + 2);
                points.add(nodes.get(node).getPoint());

                for (int seg = 0; seg < nbSegments; ++seg) {
                    Point lastPoint = points.get(points.size() - 1);

                    float dlon = (dis.readShort()) / 2.0e5f;
                    float dlat = (dis.readShort()) / 2.0e5f;

                    points.add(new Point(lastPoint.getLongitude() + dlon, lastPoint.getLatitude() + dlat));
                }

                points.add(nodes.get(destNode).getPoint());

                RoadInformation info = descs[descrNum];
                Node orig = nodes.get(node);
                Node dest = nodes.get(destNode);

                // Add successor to initial arc.
                Arc arc = new Arc(orig, dest, length, info, points);

                // And reverse arc if its a two-way road.
                if (!info.isOneWay()) {
                    // Add without segments.
                    ArrayList<Point> rPoints = new ArrayList<Point>(points);
                    Collections.reverse(rPoints);
                    new Arc(dest, orig, length, info, rPoints);
                }
                observers.forEach((observer) -> observer.notifyNewArcRead(arc));
            }
        }

        // Check format.
        checkByteOrThrow(253);

        observers.forEach((observer) -> observer.notifyEndReading());

        this.dis.close();

        return new Graph(mapId, mapName, nodes, new GraphInformation(maxSpeed, maxLength));
    }

    /**
     * Read the next road information from the stream.
     * 
     * @throws IOException
     */
    private RoadInformation readRoadInformation() throws IOException {
        char type = (char) dis.readUnsignedByte();
        int x = dis.readUnsignedByte();
        AccessRestrictions access = new AccessRestrictions();
        if (getCurrentVersion() >= 7) {
            access = toAccessInformationV7(dis.readLong());
        }
        return new RoadInformation(toRoadType(type), access, (x & 0x80) > 0, (x & 0x7F) * 5, dis.readUTF());
    }

}