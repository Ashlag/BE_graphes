package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    public Label init(Node node) {
    	//Suffit à changer le comportement, c'est pas beau ? 
    	return new LabelStar(node, this.getInputData());
    }
}
