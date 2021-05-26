package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.AccessRestrictions.AccessMode;
import org.insa.graphs.model.AccessRestrictions.AccessRestriction;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class VeloStarUnique extends AStarAlgorithm {

    public VeloStarUnique(ShortestPathData data) {
        super(data);
    }
    
    @Override
	protected double Evaluate(Label nearest, Arc arc, ShortestPathData data) {
    	if (arc.getRoadInformation().getAccessRestrictions().isAllowedFor(AccessMode.MOTORCAR, AccessRestriction.ALLOWED)) {
    		double DANGER = 5;
    		return nearest.getCost() + DANGER * data.getCost(arc);
    	}
		return nearest.getCost() + data.getCost(arc);
	}
}
