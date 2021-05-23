package org.insa.graphs.algorithm.utils;


import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Before;
import org.junit.Test;

public class DijkstraTest	{

	
	Graph graph;
	
	@Before
	public void initAll() throws Exception	{
		
		//Using Midi-Pyrenees map to run the tests
		String mapName = "/home/kronk/Documents/INSA/3MIC/BE_Graphes_Maps/midi-pyrenees.mapgr";
		
		//Create a graph reader
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
		
		//Read the graph
		graph = reader.read();
	}
	
	//---------------------------------
	//DISTANCE TESTS
	//---------------------------------
	
	//Shortest path length tests, Dijkstra vs BF, condition all roads allowed
	@Test
	public void Length10kmAllRoads() {
		int from = 617255;
		int to = 28614;
		ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(0));
		
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
		ShortestPathSolution solutionDijkstra = Dijkstra.run();
		
		//Bellman-Ford for comparison
		BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
		ShortestPathSolution solutionBF = BellmanFord.run();
		
		//Comparing the resulting lengths		
		assertEquals(solutionDijkstra.getPath().getLength(), solutionBF.getPath().getLength(), 0.001);		
	}
	
	
	@Test
	public void Length50kmAllRoads() {
		int from = 20277;
		int to = 97898;
		ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(0));
		
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
		ShortestPathSolution solutionDijkstra = Dijkstra.run();
		
		//Bellman-Ford for comparison
		BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
		ShortestPathSolution solutionBF = BellmanFord.run();
		
		//Comparing the resulting lengths		
		assertEquals(solutionDijkstra.getPath().getLength(), solutionBF.getPath().getLength(), 0.001);		
	}
	
	@Test
	public void Length200kmAllRoads() {
		int from = 574310;
		int to = 333430;
		ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(0));
		
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
		ShortestPathSolution solutionDijkstra = Dijkstra.run();
		
		//Bellman-Ford for comparison
		BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
		ShortestPathSolution solutionBF = BellmanFord.run();
		
		//Comparing the resulting lengths		
		assertEquals(solutionDijkstra.getPath().getLength(), solutionBF.getPath().getLength(), 0.001);		
	}
	
	//Shortest path length tests, Dijkstra vs BF, condition car only
	@Test
	public void Length10kmCarRoads() {
		int from = 617255;
		int to = 28614;
		ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(1));
		
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
		ShortestPathSolution solutionDijkstra = Dijkstra.run();
		
		//Bellman-Ford for comparison
		BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
		ShortestPathSolution solutionBF = BellmanFord.run();
		
		//Comparing the resulting lengths		
		assertEquals(solutionDijkstra.getPath().getLength(), solutionBF.getPath().getLength(), 0.001);		
	}
	
	
	@Test
	public void Length50kmCarRoads() {
		int from = 20277;
		int to = 97898;
		ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(1));
		
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
		ShortestPathSolution solutionDijkstra = Dijkstra.run();
		
		//Bellman-Ford for comparison
		BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
		ShortestPathSolution solutionBF = BellmanFord.run();
		
		//Comparing the resulting lengths		
		assertEquals(solutionDijkstra.getPath().getLength(), solutionBF.getPath().getLength(), 0.001);		
	}
	
	@Test
	public void Length200kmCarRoads() {
		int from = 574310;
		int to = 333430;
		ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(1));
		
		DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
		ShortestPathSolution solutionDijkstra = Dijkstra.run();
		
		//Bellman-Ford for comparison
		BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
		ShortestPathSolution solutionBF = BellmanFord.run();
		
		//Comparing the resulting lengths		
		assertEquals(solutionDijkstra.getPath().getLength(), solutionBF.getPath().getLength(), 0.001);		
	}
	
	
	//---------------------------------
	//TIME TESTS
	//---------------------------------
	
	//Fastest path time tests, Dijkstra vs BF, condition all roads allowed
		@Test
		public void Time10kmAllRoads() {
			int from = 617255;
			int to = 28614;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		
		@Test
		public void Time50kmAllRoads() {
			int from = 20277;
			int to = 97898;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		@Test
		public void Time200kmAllRoads() {
			int from = 574310;
			int to = 333430;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		//Fastest path time tests, Dijkstra vs BF, condition car only
		@Test
		public void Time10kmCarRoads() {
			int from = 617255;
			int to = 28614;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(3));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		
		@Test
		public void Time50kmCarRoads() {
			int from = 20277;
			int to = 97898;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(3));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		@Test
		public void Time200kmCarRoads() {
			int from = 574310;
			int to = 333430;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(3));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		//Fastest path time tests, Dijkstra vs BF, condition pedestrians
		@Test
		public void Time10kmPedestrians() {
			int from = 617255;
			int to = 28614;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(4));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
				
		@Test
		public void Time50kmPedestrians() {
			int from = 20277;
			int to = 97898;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(4));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		@Test
		public void Time200kmPedestrians() {
			int from = 574310;
			int to = 333430;
			ShortestPathData data = new ShortestPathData(graph, graph.get(from), graph.get(to), ArcInspectorFactory.getAllFilters().get(4));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
	
		//
}
