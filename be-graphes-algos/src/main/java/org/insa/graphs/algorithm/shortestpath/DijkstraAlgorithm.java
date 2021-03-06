package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    public Label init(Node node) {
    	Label label = new Label(node);
    	return label;

    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        // TODO:
        //get graph data
        Graph graph = data.getGraph();
        notifyOriginProcessed(data.getOrigin());
        
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        HashMap<Node, Label> labels = new HashMap<Node, Label>();
        
        //initialisation de la map de labels
        for (Node node : graph.getNodes()) {
        	Label label = this.init(node);
        	labels.put(node, label);
        }
        
        //insertion de l'origine du graphe, initialisation de son coût
        heap.insert(labels.get(data.getOrigin()));
        heap.findMin().setCost(0.);
        
        
        //Execution de l'algo
        while (!heap.isEmpty()) {
            Label nearest = heap.deleteMin();
            nearest.setMarked();
            notifyNodeMarked(nearest.getSommet());
            
            if (nearest.getSommet() == data.getDestination()) {
            	break;
            }
            //System.out.println("While");
            for (Arc arc: nearest.getSommet().getSuccessors()) {
            	
            	if(!data.isAllowed(arc)) {
            		continue;
            	}
            	
            	notifyNodeReached(arc.getDestination());
            	//System.out.println("for");
            	Label son = labels.get(arc.getDestination());
            	if (!son.isMarked()) {
                	//System.out.println("Pas marqué");
            		double evaluate = this.Evaluate(nearest, arc, data);
            		if (son.getCost() > evaluate) {
                    	//System.out.println("length");
            			if(son.getFather() != null) {
            				heap.remove(son);
                        	//System.out.println("insert heap");
            			}
            			son.setCost(evaluate);
            			heap.insert(son);
            			son.setFather(arc);
            		}
            	}
            }
            
        }
        
        Label dest = labels.get(data.getDestination());
        //Cas d'absence de plus court chemin
        if(dest.getFather() == null) {
        	solution = new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE, null);
        }else {//Creation de la liste des arcs
        	ArrayList<Arc> whole_path = new ArrayList<Arc>();
        	Arc path = dest.getFather();
        	whole_path.add(path);
        	//System.out.println(whole_path.get(0));
        	while(path.getOrigin() != data.getOrigin()) {
        		path = labels.get(path.getOrigin()).getFather();
        		whole_path.add(path);
        	}
        	
        	Collections.reverse(whole_path);
        	solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, new Path(data.getGraph(),whole_path));
        	
        }
        
        return solution;
    }
    
	protected double Evaluate(Label nearest, Arc arc, ShortestPathData data) {
		return nearest.getCost() + data.getCost(arc);
	}

}