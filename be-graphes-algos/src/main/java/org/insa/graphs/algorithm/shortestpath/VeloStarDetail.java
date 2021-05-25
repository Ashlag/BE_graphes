package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.AccessRestrictions.AccessMode;
import org.insa.graphs.model.AccessRestrictions.AccessRestriction;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.RoadInformation.RoadType;

public class VeloStarDetail extends AStarAlgorithm {

    public VeloStarDetail(ShortestPathData data) {
        super(data);
    }
    
    @Override
	protected double Evaluate(Label nearest, Arc arc, Mode mode) {
    	RoadType type = arc.getRoadInformation().getType();

    	double DANGER = 1;
    	switch (type) {
	    	case CYCLEWAY:
	    		DANGER = 1;
	    		break;
	    	case MOTORWAY:
	    		DANGER = 15;
	    		break;
	    	case PEDESTRIAN:
	    		DANGER = 1;
	    		break;
	    	case TRUNK:
	    		DANGER = 13;
	    		break;
	    	case PRIMARY:
	    		DANGER = 10;
	    		break;
	    	case SECONDARY:
	    		DANGER = 7;
	    		break;
	    	case SECONDARY_LINK:
	    		DANGER = 7;
	    		break;
	    	case COASTLINE:
	    		DANGER = 5;
	    		break;
	    	case TERTIARY:
	    		DANGER = 5;
	    		break;
	    	case SERVICE:
	    		DANGER = 5;
	    		break;
	    	case UNCLASSIFIED:
	    		DANGER = 5;
	    		break;
	    	case RESIDENTIAL:
	    		DANGER = 3;
	    		break;
	    	case LIVING_STREET:
	    		DANGER = 3;
	    		break;
	    	case ROUNDABOUT:
	    		DANGER = 3;
	    		break;
	    	case TRACK:
	    		DANGER = 2;
	    		break;
	    	default:
	    		DANGER = 5;
    	}
    	return nearest.getCost() + DANGER * (mode == AbstractInputData.Mode.LENGTH ? arc.getLength() : arc.getMinimumTravelTime());
	}
} 

