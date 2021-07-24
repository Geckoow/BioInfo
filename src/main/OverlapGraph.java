package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class OverlapGraph {
	
	/**
	 * list of the Edges
	 */
	private PriorityBlockingQueue<Edge> edges;
	/**
	 * list of the fragments
	 */
    private ArrayList<Fragment> fragments;
    
    
    public OverlapGraph(ArrayList<Fragment> fragList){ 
        fragments = fragList;
        edges = new PriorityBlockingQueue<Edge>();

        int listSize = fragList.size();
        
        for (int i = 0; i < listSize; i++) {
        	for (int j = i+1; j < listSize; j++) {
        		generateArc(i,j);
        	}
        }
    }
    /**
     * generates the 8 arc possibilities 
     * @param startP index of the arc source node
     * @param endPoint index of the destination node of the arc
     */
    public void generateArc(int startP, int endP) {
    	Fragment f = fragments.get(startP);
    	Fragment g = fragments.get(endP);
    
    	SemiGlobAlignment e1 = new SemiGlobAlignment(f, g);
    	SemiGlobAlignment e2 = new SemiGlobAlignment(f.getComplementary(), g);
    
    	/* weight(f->g) = weight(g'->f')*/
    	edges.add(new Edge(startP,endP,e1.getFGScore(),EdgeType.FG));
    	edges.add(new Edge(endP,startP,e1.getFGScore(),EdgeType.GpFp));
    	/* weight(g->f) = weight(f'->g')*/
    	edges.add(new Edge(startP,endP,e1.getScoreTransposed(),EdgeType.FpGp));
    	edges.add(new Edge(endP,startP,e1.getScoreTransposed(),EdgeType.GF));
    	/* weight(g'->f) = weight(f'->g)*/
    	edges.add(new Edge(endP,startP,e2.getScoreTransposed(),EdgeType.GpF));
    	edges.add(new Edge(startP,endP,e2.getScoreTransposed(),EdgeType.FpG));
    	/* weight(g->f') = weight(f->g')*/
    	edges.add(new Edge(endP,startP,e2.getFGScore(),EdgeType.GFp));
    	edges.add(new Edge(startP,endP,e2.getFGScore(),EdgeType.FGp));
    }
    public PriorityBlockingQueue<Edge> getEdges() {
    	return new PriorityBlockingQueue<Edge> (edges);
    }
}

