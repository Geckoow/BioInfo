package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class HamiltonPath {

    private HashMap<Integer, Edge> halmitonPath;
    private int sumWeight = 0;
    private int countSet;
    
    public HamiltonPath(PriorityBlockingQueue<Edge> edges, int fragmentsSize) {
    	halmitonPath = new HashMap<Integer, Edge>();
    	/**
    	 * false in position i if no arc entering i true otherwise
    	 */
    	boolean in[] = new boolean[fragmentsSize];
    	/**
    	 * false in position i if no outgoing arc in i true otherwise
    	 */
    	boolean out[] = new boolean[fragmentsSize];

    	Edge e = edges.poll();
    	StructUnionFind uFind = new StructUnionFind(fragmentsSize);
    	countSet = fragmentsSize - 1;
    	while(e != null ) {
    		int e1 = e.getStartP();
    		int e2 = e.getEndP();
    		if(!in[e2] && !out[e1]) {
    			if((!out[e2] || e.getType().equals(EdgeType.FGp)) && (!in[e1] || e.getType().equals(EdgeType.FpG))
    					&& (uFind.find(e1) != uFind.find(e2))) {
    				halmitonPath.put(e1, e);
    				sumWeight += e.getWeight();
    				if(uFind.union(e1, e2)) {
    					countSet--;
    				}
    				in[e2] = true;
    				out[e1] = true;
    				
    			}
    		}
    		if(countSet == 0) 
    			break;
    		e = edges.poll();
    		}
    		
    	
    }
    //https://www.cs.waikato.ac.nz/~bernhard/317/source/graph/UnionFind.java
	//https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
	//used it to detect cycle in a graph
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
}
