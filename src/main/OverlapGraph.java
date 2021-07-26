package main;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import sun.jvm.hotspot.opto.MachReturnNode;

public class OverlapGraph {
	
	/**
	 * list of the Edges
	 */
	private PriorityBlockingQueue<Edge> edges;
	/**
	 * list of the fragments
	 */
    private ArrayList<Fragment> fragments;
   
    private ArrayList<Integer> listInclusion ;
    
    
    public OverlapGraph(ArrayList<Fragment> fragList){ 
        fragments = fragList;
        edges = new PriorityBlockingQueue<Edge>();
        listInclusion = new ArrayList<Integer>(fragList.size());
        initListInclusion();
        
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
    	
    
    	SemiGlobAlignment sgaFG = new SemiGlobAlignment(f, g);
    	SemiGlobAlignment sgaFpG = new SemiGlobAlignment(f, g.getComplementaryInverse());
    	/* weight(f->g) = weight(g'->f')*/
    	edges.add(new Edge(startP,endP,sgaFG.getFGScore(),EdgeType.FG));
    	edges.add(new Edge(endP,startP,sgaFG.getFGScore(),EdgeType.GpFp));
    	/* weight(g->f) = weight(f'->g')*/
    	edges.add(new Edge(startP,endP,sgaFG.getScoreTransposed(),EdgeType.FpGp));
    	edges.add(new Edge(endP,startP,sgaFG.getScoreTransposed(),EdgeType.GF));
    	/* weight(g'->f) = weight(f'->g)*/
    	edges.add(new Edge(endP,startP,sgaFpG.getScoreTransposed(),EdgeType.GpF));
    	edges.add(new Edge(startP,endP,sgaFpG.getScoreTransposed(),EdgeType.FpG));
    	/* weight(g->f') = weight(f->g')*/
    	edges.add(new Edge(endP,startP,sgaFpG.getFGScore(),EdgeType.GFp));
    	edges.add(new Edge(startP,endP,sgaFpG.getFGScore(),EdgeType.FGp));
    }
    
    private void initListInclusion() {
    	for(int i = 0; i < listInclusion.size(); i++) {
    		listInclusion.add(-1);
    	}
    }
    
    public boolean manageInclusion(SemiGlobAlignment sgaFG, SemiGlobAlignment sgaFpG, Fragment f, Fragment g, int posF, int posG) {
    	if(sgaFpG.getScoreTransposed() == -1 || sgaFpG.getFGScore() == -1  || sgaFG.getScoreTransposed() == -1 || sgaFG.getFGScore() == -1 ) {
    		if(f.getSize() < g.getSize())
    			listInclusion.add(posF, posG);
    		else {
    			listInclusion.add(posG, posF);
			}
    		return true;
    	}
    	return false;
    	
    
    }
    public PriorityBlockingQueue<Edge> getEdges() {
    	return new PriorityBlockingQueue<Edge> (edges);
    }
}

