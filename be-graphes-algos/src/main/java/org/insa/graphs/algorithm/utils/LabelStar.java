package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Node;

public class LabelStar extends Label{
	
	private double estimated_cost;
	
	public LabelStar(Node sommet, ShortestPathData data) {
		super(sommet);
		Node destination = data.getDestination();
		if (data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
			this.estimated_cost = destination.getPoint().distanceTo(sommet.getPoint());
		}else {
			this.estimated_cost = destination.getPoint().distanceTo(sommet.getPoint())/data.getGraph()
						.getGraphInformation().getMaximumSpeed()*3.6;
		}
	}
	
	
	@Override
	public double getTotalCost() {
		return this.estimated_cost+this.getCost();
	}
		
}