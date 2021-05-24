package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.GraphStatistics;
import org.insa.graphs.model.Node;

public class LabelStar extends Label{
	
	private double estimated_cost;
	
	//Plein de façons d'aborder la question, choix d'utiliser les data pour le calcul dans le builder et non l'algo
	public LabelStar(Node sommet, ShortestPathData data) {
		super(sommet);
		Node destination = data.getDestination();
		if (data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
			this.estimated_cost = destination.getPoint().distanceTo(sommet.getPoint());
		} else {
			double maxSpeed = data.getMaximumSpeed() == GraphStatistics.NO_MAXIMUM_SPEED ? data.getGraph().getGraphInformation().getMaximumSpeed() : data.getMaximumSpeed();
			this.estimated_cost = destination.getPoint().distanceTo(sommet.getPoint()) / maxSpeed * 3.6;
		}
	}
	
	
	@Override
	public double getTotalCost() {
		return this.estimated_cost+this.getCost();
	}
		
}