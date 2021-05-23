package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	
	private final Node sommet;
	private Arc father;
	private double cost;
	private boolean marked;
	
	public Label(Node sommet) {
		this.sommet = sommet;
		this.father = null;
		this.cost = Double.POSITIVE_INFINITY;
		this.marked = false;		
	}
	
	public Node getSommet() {
		return this.sommet;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public boolean isMarked() {
		return this.marked;
	}
	
	public void setMarked() {
		this.marked = true;
	}
	
	public Arc  getFather() {
		return this.father;
	}
	
	public void setFather(Arc daddy) {
		this.father = daddy;
	}
	
	public double getTotalCost() {
		return this.cost;
	}
	
	@Override
	public int compareTo(Label other) {
		return (int)(this.getTotalCost()-other.getTotalCost());
	}
}