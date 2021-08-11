package main;


/*********************** Edge Class **************************************************/
public class Edge implements Comparable<Edge>{
	/**
	 * index of the source node
	 */
    private int startP;
    /**
     * index of the destination node
     */
    private int endP;
    /**
     * the weigh of the arc
     */
    private int weight;
    /**
     * type of the arc
     */
    private EdgeType type;

    /**
     * Constructor of the Edge
     * @param startP of the source node
     * @param endP of the destination node
     * @param weight weight of this Edge
     * @param type Edge type
     */
    public Edge( int startP, int endP, int weight,EdgeType type ){
        this.startP = startP;
        this.endP = endP;
        this.weight = weight;
        this.type = type;
        
    }
    
	public int getStartP() {
		return startP;
	}

	public int getEndP() {
		return endP;
	}
	/*
	 * the score of this Edge
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * for ranking by decreasing weight and ascending order for the position
	 */
	@Override
	public int compareTo(Edge o) {
		if(o.getWeight() != getWeight())		
			return (o.getWeight() - getWeight()) ;
		else if(startP != o.startP)
			return startP - o.startP;
		else 
			return endP - o.endP;
		
	}
	
	public boolean equals(final Edge o) {
		if (this == o)
			return true;	
		if (o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		if(o instanceof Edge) {			
			Edge tmp = (Edge)o;
			if(tmp.startP == this.startP  && tmp.endP == this.endP && tmp.weight == this.weight && this.getType().equals(tmp.getType()))
				return true;
		}
		return false;
	}

	public EdgeType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return getType()+" "+startP+"----("+weight+")--->"+endP;
	}
}
