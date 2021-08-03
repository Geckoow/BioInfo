package main;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class OverlapGraph {
	
	/**
	 * list of the Edges represented by a priority queue, in order to store the arcs according to a priority
	 */
	private PriorityBlockingQueue<Edge> edges;
	/**
	 * list of the fragments
	 */
    private ArrayList<Fragment> fragments;
    /**
     * list qui g�re les inclusion tel que listInclusion[i] = -1 s il n y a pas d'inclusion de fragment
     * listInclusion[i] = j si le i�me fragment inclus dans le ji�me
     */
    private ArrayList<Integer> listInclusion ;
    
    
    public OverlapGraph(ArrayList<Fragment> fragList){ 
        fragments = fragList;
        edges = new PriorityBlockingQueue<Edge>();
        
        listInclusion = new ArrayList<Integer>();
        initListInclusion(fragList.size());
        
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
    	
    	if(listInclusion.get(startP) != -1 || listInclusion.get(endP) != -1 )
    		return;
    	
    	SemiGlobAlignment sgaFG = new SemiGlobAlignment(f, g);
    	SemiGlobAlignment sgaFpG = new SemiGlobAlignment(f, g.getComplementaryInverse());
    	
    	if(isIncluded(sgaFG, sgaFpG, f, g, startP, endP))
    		return;
    //	System.out.println(sgaFG.getFGScore());
    //	System.out.println(sgaFG.getScoreTransposed());
    //	System.out.println(sgaFpG.getFGScore());
    //	System.out.println(sgaFpG.getScoreTransposed());
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
    
    private void initListInclusion(int size) {
    	for(int i = 0; i < size; i++) {
    		listInclusion.add(-1);
    	}
    }
    
    public boolean isIncluded(SemiGlobAlignment sgaFG, SemiGlobAlignment sgaFpG, Fragment f, Fragment g, int posF, int posG) {
    	if(sgaFpG.getScoreTransposed() == -1 || sgaFpG.getFGScore() == -1  || sgaFG.getScoreTransposed() == -1 || sgaFG.getFGScore() == -1 ) {
    		if(f.getSize() < g.getSize())
    			listInclusion.set(posF, posG);
    		else {
    			listInclusion.set(posG, posF);
			}//tmp
    		//System.out.println("inclu");
    		return true;
    	}
    	return false;
    	
    
    }
    public PriorityBlockingQueue<Edge> getEdges() {
    	return new PriorityBlockingQueue<Edge> (edges);
    }
	public ArrayList<Integer> getListInclusion() {
		return listInclusion;
	}
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}
}

