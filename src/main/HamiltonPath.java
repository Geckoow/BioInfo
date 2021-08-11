package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;



public class HamiltonPath {
	
	/**
	 * hashmap where the Hamiltonian path will be stored
	 */
    private HashMap<Integer, Edge> halmitonPath;
 //   private int sumWeight = 0;
    private int countSet;
    private OverlapGraph graph;
    
    /**
     * index of the starting node of the Hamiltonian path
     */
    private int beginPath;
    
    /**
     * 
     * @param graph overlapGraph
     * @param fragmentsSize number of fragments
     */
    public HamiltonPath(OverlapGraph graph, int fragmentsSize) {
    	this(graph.getEdges(), graph.getListInclusion(), fragmentsSize) ;
    	this.graph = graph;
	}
    /**
     * built the Hamiltonian path
     * @param edges all arcs generated
     * @param listInclusion table of fragment positions included in others
     * @param fragmentsSize number of fragments
     */
    public HamiltonPath(PriorityBlockingQueue<Edge> edges, ArrayList<Integer> listInclusion, int fragmentsSize) {

    	halmitonPath = new HashMap<Integer, Edge>();
    	/**
    	 * -1 if arc entering i', 0 if no arc entering i, 1 if arc entering i
    	 */
    	int in[] = new int[fragmentsSize];
    	/**
    	 * -1 if outgoing arc in i', if no outgoing arc in i, 1 if outgoing arc in i
    	 */
    	int out[] = new int[fragmentsSize];
    	Edge e = edges.poll();
    	
    	StructUnionFind uFind = new StructUnionFind(fragmentsSize);
    	
    	/**
    	 * on selectionne juste les arcs avec des fragments inclus dans d'autres
    	 */
    	for(int i = 0; i < listInclusion.size(); i++) {
    		if(listInclusion.get(i) != -1)
    			uFind.union(i, listInclusion.get(i));
    	}
    	countSet = fragmentsSize - 1;
    	while(e != null ) {
    		int e1 = e.getStartP();
    		int e2 = e.getEndP();
  
    		if(isAddedArc(listInclusion,uFind,e,in,out)) {
    			
    			halmitonPath.put(e1, e);
    			in[e2] = getInOut(e,false);
    			out[e1] = getInOut(e,true);
    			if(uFind.union(e1, e2)) {
    				countSet--;
    			}
    		}
    		if(countSet == 0) 
    			break;
    		e = edges.poll();
    	}
    	setBeginPath(halmitonPath, in);   }
    
    private void setBeginPath(HashMap<Integer, Edge> hmp, int[] in) {
    	for (Integer i: hmp.keySet()) {
    		if(in[i] == 0) {
    			beginPath = i;
    			break;
    		}
    	}
    }

	private boolean isAddedArc(ArrayList<Integer> listInclusion,StructUnionFind uFind, Edge e, int [] in, int [] out) {
    	int e1 = e.getStartP();
    	int e2 = e.getEndP();
    	if(listInclusion.get(e1) == -1 && listInclusion.get(e2) == -1){
    		//on s'assure qu'aucun arc ne soit de f et aucun n'entre dans g avant d'envisager faire l'arc f->g
			if(out[e1] == 0  &&  in[e2] == 0) {
				/*
				 * on regarde aussi si le complementaire inverse n'a pas deja ete utilise pour la source et la destination
				 * c'est � dire : 1) g'->y dans ce cas on ne peut plus prendre le fragment g
				 * 				  2) x->f' dans ce cas on ne peut plus prendre le fragment f
				 *       g'->y
				 *    f->g
				 * x->f'
				 */
				if((out[e2] == 0 || out[e2] == getInOut(e,false)) && (in[e1] == 0 || in[e1] == getInOut(e,true)))
					return uFind.find(e1) != uFind.find(e2);  // il ne faut pas autoriser un cycle y-x
			}
		}
		return false;
    }
	/**
	 * allows you to fill the in and out tables
	 * @param e arc
	 * @param testSource true if we want to test the nature of the source otherwise nature of destination
	 * @return -1 if an inverse complement is selected, 1 else
	 */
    private int getInOut(Edge e, boolean testSource) {
		if(testSource && (e.getType().equals(EdgeType.GpFp) || e.getType().equals(EdgeType.FpGp)||e.getType().equals(EdgeType.GpF) || e.getType().equals(EdgeType.FpG)))
			return -1;   
		else if(!testSource && (e.getType().equals(EdgeType.GpFp) || e.getType().equals(EdgeType.FpGp)||e.getType().equals(EdgeType.FGp) || e.getType().equals(EdgeType.GFp)))
			return -1;
		return 1;
	}
	//https://www.cs.waikato.ac.nz/~bernhard/317/source/graph/UnionFind.java
	//https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
	//O(Logn)
    public class StructUnionFind{
    	
    	private int[] _parent;
    	private int[] _rank;
    	
    	/**
    	 * 
    	 * @param max set number has one element.
    	 */
    	public StructUnionFind(int max) {
    		_parent = new int[max];
    	    _rank = new int[max];

    	    for (int i = 0; i < max; i++) {
    	      _parent[i] = i;
    	    }
    	  }
    	
    	/**
    	 * determines the equivalence class of element i.
    	 * @param i
    	 * @return class of element i
    	 */
    	public int find(int i) {
    		int p = _parent[i];
    	    if (i == p)
    	      return i;
    	    return _parent[i] = find(p);
    	}
    	
    	/**
    	 * combines two equivalence classes i, j into one
    	 * @param i
    	 * @param j
    	 *  @return true if combination success , false otherwise 
    	 */
    	public boolean union(int i, int j) {
    		int root1 = find(i);
    	    int root2 = find(j);

    	    if (root2 == root1) 
    	    	return false;
    	    if (_rank[root1] > _rank[root2])
    	      _parent[root2] = root1;
    	    else if (_rank[root2] > _rank[root1])
    	      _parent[root1] = root2;
    	    else {
    	      _parent[root2] = root1;
    	      _rank[root1]++;
    	    }
    	    return true;
    	}
    	public String toString() {
    		return "<UnionFind\np " + Arrays.toString(_parent) + "\nr " + Arrays.toString(_rank) + "\n>";
    	}
    }
	public HashMap<Integer, Edge> getHalmitonPath() {
		return new HashMap<Integer, Edge>(halmitonPath);
	}
	
	// just for testing
	public void displayPath() {
		HashMap<Integer, Edge> path = getHalmitonPath();
		for(Edge e:path.values()) {
			System.out.println(e.toString()+": "+graph.getFragments().get(e.getStartP())+" ---> "+graph.getFragments().get(e.getEndP()));
		}
	}
	public int getBeginPath() {
		return beginPath;
	}
}
