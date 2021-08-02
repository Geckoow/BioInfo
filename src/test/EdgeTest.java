package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.Edge;
import main.EdgeType;

public class EdgeTest {
	
	Edge e1 = new Edge(0, 1, 4, EdgeType.FG);
	Edge e11 = new Edge(0, 1, 4, EdgeType.FG);
	
	Edge e111 = new Edge(1, 1, 4, EdgeType.FGp);
	
	Edge e2 =  new Edge(1, 0, 5, EdgeType.GF);
	Edge e22 =  new Edge(1, 0, 6, EdgeType.GF);
	
	Edge e3 = new Edge(1, 13, 4, EdgeType.FG);
	Edge e4 =  new Edge(3, 5, 5, EdgeType.GF);
	@Test
	public void testEquals() {
		assertTrue(e1.equals(e11));
		assertFalse(e1.equals(e2));
		assertFalse(e1.equals(e111));;
	}
	@Test
	public void testCompareTo() {
		assertTrue(e22.compareTo(e2) < 0); //ordre decroissant 5 puis 4
		assertTrue(e1.compareTo(e111) < 0); //egalite de poid, on prends d'abord la plus petite coordonnes
		System.out.println(e2.compareTo(e1));
		System.out.println((e2.compareTo(e4)));
	}
}
