package org.insa.graphs.algorithm.utils;


import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.AbstractSolution.Status;
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

	
	Graph graphBig, graphSmol, graphIsl;
	
	@Before
	public void initAll() throws Exception	{
		
		//Using Midi-Pyrenees, Haute-Garonne and Polynesia maps for tests
		String mapNameBig = "/home/kronk/Documents/INSA/3MIC/BE_Graphes_Maps/midi-pyrenees.mapgr";
		String mapNameSmol = "/home/kronk/Documents/INSA/3MIC/BE_Graphes_Maps/haute-garonne.mapgr";
		String mapNameIsl = "/home/kronk/Documents/INSA/3MIC/BE_Graphes_Maps/french-polynesia.mapgr";
		
		//Create a graph reader
		GraphReader readerBig = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapNameBig))));
		GraphReader readerSmol = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapNameSmol))));
		GraphReader readerIsl = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapNameIsl))));

		//Read the graph
		graphBig = readerBig.read();
		graphSmol = readerSmol.read();
		graphIsl = readerIsl.read();
		
		//closing the readers
		readerBig.close();
		readerSmol.close();
		readerIsl.close();

	}
	
	//---------------------------------
	//DISTANCE TESTS
	//---------------------------------
	

	//Shortest path length tests, Dijkstra vs BF, condition all roads allowed
	@Test
	public void Length10kmAllRoads() {
		int from = 23938;
		int to = 7392;
		ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(0));
		
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
		int from = 71678;
		int to = 147968;
		ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(0));
		
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
		int from = 140779;
		int to = 128666;
		ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(0));
		
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
		int from = 23938;
		int to = 7392;
		ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(1));
		
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
		int from = 71678;
		int to = 147968;
		ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(1));
		
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
		int from = 140779;
		int to = 128666;
		ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(1));
		
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
			int from = 23938;
			int to = 7392;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
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
			int from = 71678;
			int to = 147968;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
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
			int from = 140779;
			int to = 128666;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
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
			int from = 23938;
			int to = 7392;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(3));
			
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
			int from = 71678;
			int to = 147968;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(3));
			
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
			int from = 140779;
			int to = 128666;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(3));
			
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
			int from = 23938;
			int to = 7392;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(4));
			
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
			int from = 71678;
			int to = 147968;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(4));
			
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
			int from = 140779;
			int to = 128666;
			ShortestPathData data = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(4));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(data);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		
		//--------------------------------
		//Infeasible Tests
		//--------------------------------
		
		//Test existing path condition all roads, but not caronly
		@Test
		public void LengthCarOnlyInfeasible() {
			int from = 145434;
			int to = 8451;
			ShortestPathData dataAll = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(0));
			ShortestPathData dataCar = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(1));
			
			//Allroads
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(dataAll);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(dataAll);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//CarOnly
			DijkstraAlgorithm DijkstraCar = new DijkstraAlgorithm(dataCar);
			ShortestPathSolution solutionDijkstraCar = DijkstraCar.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFordCar = new BellmanFordAlgorithm(dataCar);
			ShortestPathSolution solutionBFCar = BellmanFordCar.run();

			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getLength(), solutionBF.getPath().getLength(), 0.001);
			assertEquals(solutionDijkstraCar.getStatus(), Status.INFEASIBLE);
			assertEquals(solutionBFCar.getStatus(), Status.INFEASIBLE);
		}
		
		@Test
		public void TimeCarOnlyInfeasible() {
			int from = 145434;
			int to = 8451;
			ShortestPathData dataAll = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(2));
			ShortestPathData dataCar = new ShortestPathData(graphSmol, graphSmol.get(from), graphSmol.get(to), ArcInspectorFactory.getAllFilters().get(3));
			
			//Allroads
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(dataAll);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(dataAll);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//CarOnly
			DijkstraAlgorithm DijkstraCar = new DijkstraAlgorithm(dataCar);
			ShortestPathSolution solutionDijkstraCar = DijkstraCar.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFordCar = new BellmanFordAlgorithm(dataCar);
			ShortestPathSolution solutionBFCar = BellmanFordCar.run();

			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionBF.getPath().getMinimumTravelTime(), 0.001);
			assertEquals(solutionDijkstraCar.getStatus(), Status.INFEASIBLE);
			assertEquals(solutionBFCar.getStatus(), Status.INFEASIBLE);
		}
		
		@Test
		public void LengthInfeasible() {
			int from = 13860;
			int to = 11010;
			ShortestPathData dataAll = new ShortestPathData(graphIsl, graphIsl.get(from), graphIsl.get(to), ArcInspectorFactory.getAllFilters().get(0));
			
			//Allroads
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(dataAll);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(dataAll);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getStatus(), Status.INFEASIBLE);
			assertEquals(solutionBF.getStatus(), Status.INFEASIBLE);
		}

		public void TimeInfeasible() {
			int from = 13860;
			int to = 11010;
			ShortestPathData dataAll = new ShortestPathData(graphIsl, graphIsl.get(from), graphIsl.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
			//Allroads
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(dataAll);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Bellman-Ford for comparison
			BellmanFordAlgorithm BellmanFord = new BellmanFordAlgorithm(dataAll);
			ShortestPathSolution solutionBF = BellmanFord.run();
			
			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getStatus(), Status.INFEASIBLE);
			assertEquals(solutionBF.getStatus(), Status.INFEASIBLE);
		}
		
		//------------------------------
		//SANS ORACLE
		//------------------------------
		
		//------------------------------
		//DISTANCE
		//------------------------------
		@Test
		public void SousChemin() {
			int from = 239566;
			int to = 5201;
			
			int fromSub = 29459;
			int toSub = 5201;
			
			//Creation Chemin
			ShortestPathData data = new ShortestPathData(graphBig, graphBig.get(from), graphBig.get(to), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Création sous Chemin
			ShortestPathData dataSub = new ShortestPathData(graphBig, graphBig.get(fromSub), graphBig.get(toSub), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm DijkstraSub = new DijkstraAlgorithm(dataSub);
			ShortestPathSolution solutionDijkstraSub = DijkstraSub.run();

			//Comparing the resulting lengths		
			assertTrue(solutionDijkstra.getPath().getLength() - solutionDijkstraSub.getPath().getLength() > 0);		
		}
		
		@Test
		public void InegaliteTriangulaire() {
			int A = 239566;
			int B = 352060;
			int C = 5201;
			
			//Create AC
			ShortestPathData dataAC = new ShortestPathData(graphBig, graphBig.get(A), graphBig.get(C), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm DijkstraAC = new DijkstraAlgorithm(dataAC);
			ShortestPathSolution solutionDijkstraAC = DijkstraAC.run();
			
			//Create AB
			ShortestPathData dataAB = new ShortestPathData(graphBig, graphBig.get(A), graphBig.get(B), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm DijkstraAB = new DijkstraAlgorithm(dataAB);
			ShortestPathSolution solutionDijkstraAB = DijkstraAB.run();
			
			//Create BC
			ShortestPathData dataBC = new ShortestPathData(graphBig, graphBig.get(B), graphBig.get(C), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm DijkstraBC = new DijkstraAlgorithm(dataBC);
			ShortestPathSolution solutionDijkstraBC = DijkstraBC.run();

			//Comparing the resulting lengths		
			assertTrue(solutionDijkstraAB.getPath().getLength() + solutionDijkstraBC.getPath().getLength() >= solutionDijkstraAC.getPath().getLength());		
		}
		
		@Test
		public void ReversePath() {
			int from = 143627;
			int to = 143619;
						
			//Creation Chemin
			ShortestPathData data = new ShortestPathData(graphBig, graphBig.get(from), graphBig.get(to), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Création Chemin inverse
			ShortestPathData dataSub= new ShortestPathData(graphBig, graphBig.get(to), graphBig.get(from), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm DijkstraSub = new DijkstraAlgorithm(dataSub);
			ShortestPathSolution solutionDijkstraSub = DijkstraSub.run();

			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getLength(), solutionDijkstraSub.getPath().getLength(), 0.001);		
		}
		
		//------------------------------
		//TIME
		//------------------------------
		@Test
		public void SousCheminTime() {
			int from = 239566;
			int to = 5201;
			
			int fromSub = 29459;
			int toSub = 5201;
			
			//Creation Chemin
			ShortestPathData data = new ShortestPathData(graphBig, graphBig.get(from), graphBig.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Création sous Chemin
			ShortestPathData dataSub = new ShortestPathData(graphBig, graphBig.get(fromSub), graphBig.get(toSub), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm DijkstraSub = new DijkstraAlgorithm(dataSub);
			ShortestPathSolution solutionDijkstraSub = DijkstraSub.run();

			//Comparing the resulting lengths		
			assertTrue(solutionDijkstra.getPath().getMinimumTravelTime() - solutionDijkstraSub.getPath().getMinimumTravelTime() > 0);		
		}
		
		@Test
		public void InegaliteTriangulaireTime() {
			int A = 239566;
			int B = 352060;
			int C = 5201;
			
			//Create AC
			ShortestPathData dataAC = new ShortestPathData(graphBig, graphBig.get(A), graphBig.get(C), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm DijkstraAC = new DijkstraAlgorithm(dataAC);
			ShortestPathSolution solutionDijkstraAC = DijkstraAC.run();
			
			//Create AB
			ShortestPathData dataAB = new ShortestPathData(graphBig, graphBig.get(A), graphBig.get(B), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm DijkstraAB = new DijkstraAlgorithm(dataAB);
			ShortestPathSolution solutionDijkstraAB = DijkstraAB.run();
			
			//Create BC
			ShortestPathData dataBC = new ShortestPathData(graphBig, graphBig.get(B), graphBig.get(C), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm DijkstraBC = new DijkstraAlgorithm(dataBC);
			ShortestPathSolution solutionDijkstraBC = DijkstraBC.run();

			//Comparing the resulting lengths		
			assertTrue(solutionDijkstraAB.getPath().getMinimumTravelTime() + solutionDijkstraBC.getPath().getMinimumTravelTime() >= solutionDijkstraAC.getPath().getMinimumTravelTime());		
		}
		

		@Test
		public void ReversePathTime() {
			int from = 143627;
			int to = 143619;
						
			//Creation Chemin
			ShortestPathData data = new ShortestPathData(graphBig, graphBig.get(from), graphBig.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Création Chemin inverse
			ShortestPathData dataSub= new ShortestPathData(graphBig, graphBig.get(to), graphBig.get(from), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm DijkstraSub = new DijkstraAlgorithm(dataSub);
			ShortestPathSolution solutionDijkstraSub = DijkstraSub.run();

			//Comparing the resulting lengths		
			assertEquals(solutionDijkstra.getPath().getMinimumTravelTime(), solutionDijkstraSub.getPath().getMinimumTravelTime(), 0.001);		
		}
		
		
		//------------------------------
		//MIXED
		//------------------------------
		@Test
		public void ShortestvsFastest() {
			int from = 295086;
			int to = 11579;
						
			//Creation Chemin Shortest
			ShortestPathData data = new ShortestPathData(graphBig, graphBig.get(from), graphBig.get(to), ArcInspectorFactory.getAllFilters().get(0));
			
			DijkstraAlgorithm Dijkstra = new DijkstraAlgorithm(data);
			ShortestPathSolution solutionDijkstra = Dijkstra.run();
			
			//Création Chemin Fastest
			ShortestPathData dataFast= new ShortestPathData(graphBig, graphBig.get(from), graphBig.get(to), ArcInspectorFactory.getAllFilters().get(2));
			
			DijkstraAlgorithm DijkstraFast = new DijkstraAlgorithm(dataFast);
			ShortestPathSolution solutionDijkstraFast = DijkstraFast.run();

			//Comparing the resulting lengths		
			assertTrue(solutionDijkstra.getPath().getLength() <= solutionDijkstraFast.getPath().getLength());
			assertTrue(solutionDijkstraFast.getPath().getMinimumTravelTime() <= solutionDijkstra.getPath().getMinimumTravelTime());

		}


}
