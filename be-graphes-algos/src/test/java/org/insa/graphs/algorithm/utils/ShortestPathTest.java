package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public abstract class ShortestPathTest {
    @Parameterized.Parameter
    public ShortestPathData parameters;
    protected static String carre = "D:\\\\vince\\\\Documents\\\\INSA TOULOUSE\\\\3MIC\\\\S6\\\\BE_Graphes\\\\Maps\\\\carre.mapgr";
    protected static String insa = "D:\\\\vince\\\\Documents\\\\INSA TOULOUSE\\\\3MIC\\\\S6\\\\BE_Graphes\\\\Maps\\\\insa.mapgr";
    
    /**
     * Create algorithm from data
     *
     * @param data Data to pass to algorithm
     * @return New algorithm
     */
    public abstract ShortestPathAlgorithm createAlgorithm(ShortestPathData data);

    @Parameterized.Parameters
    public static Collection<ShortestPathData> data() throws IOException {
        BinaryGraphReader reader = new BinaryGraphReader(new DataInputStream(new FileInputStream(carre)));
        Graph graphCarre = reader.read();
        reader.close();
        
        reader = new BinaryGraphReader(new DataInputStream(new FileInputStream(insa)));
        Graph graphInsa = reader.read();
        reader.close();
        
        Collection<ShortestPathData> objects = new ArrayList<>();
        //for each filter
        for (ArcInspector inspector : ArcInspectorFactory.getAllFilters()) {
        	//for every node couples
            for (Node origin : graphCarre.getNodes()) {
                for (Node destination : graphCarre.getNodes()) {
                	//create an object in the collection with those parameters
                    objects.add(new ShortestPathData(graphCarre, origin, destination, inspector));
                }
            }
            //Limit the # of tests for huge maps
            int i = 0;
            for (Node origin : graphInsa.getNodes()) {
                for (Node destination : graphInsa.getNodes()) {
                    i++;
                    objects.add(new ShortestPathData(graphInsa, origin, destination, inspector));
                }
                if(i > 8000) {
                    break;
                }
            }
            
        }
        return objects;
    }
    
    @Test
    public void all() {
        ShortestPathAlgorithm algorithm = this.createAlgorithm(this.parameters);
        BellmanFordAlgorithm algoBF = new BellmanFordAlgorithm(this.parameters);
        
        ShortestPathSolution solutionBF = algoBF.run();
        ShortestPathSolution solution = algorithm.run();
        assertEquals(solutionBF.isFeasible(), solution.isFeasible());
        if(solutionBF.isFeasible()) {
        	
            assertNotNull(solution.getPath());
            
            //cost calculation that works with all the filters
            double costAlgo, costBF;
            costAlgo = costBF = 0;
            for (Arc arc : solution.getPath().getArcs()) {
            	costAlgo += solution.getInputData().getCost(arc);
            }
            for (Arc arc : solutionBF.getPath().getArcs()) {
            	costBF += solutionBF.getInputData().getCost(arc);
            }

            assertEquals(costAlgo, costBF, 0.001);
        }
    }
}
