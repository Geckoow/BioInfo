package main;

import java.util.ArrayList;

public class OverlapGraph {

    /**
     * list of the Edges
     */
    private ArrayList<Edge> edges;
    /**
     * list of the fragments
     */
    private ArrayList<Fragment> fragments;



    public OverlapGraph(ArrayList<Fragment> fragList){
        fragments = fragList;
        edges = new ArrayList<Edge>();
        int listSize = fragList.size();

        for (int i = 0; i < listSize; i++) {
            for (int j = 0; j < listSize; j++) {
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
        edges.add(new Edge(startP,endP,e1.getScore(),EdgeType.FG));
        edges.add(new Edge(endP,startP,e1.getScore(),EdgeType.GpFp));
        /* weight(g->f) = weight(f'->g')*/
        edges.add(new Edge(startP,endP,e1.getScoreTransposed(),EdgeType.FpGp));
        edges.add(new Edge(endP,startP,e1.getScoreTransposed(),EdgeType.GF));
        /* weight(g'->f) = weight(f'->g)*/
        edges.add(new Edge(endP,startP,e2.getScoreTransposed(),EdgeType.GpF));
        edges.add(new Edge(startP,endP,e2.getScoreTransposed(),EdgeType.FpG));
        /* weight(g->f') = weight(f->g')*/
        edges.add(new Edge(endP,startP,e2.getScore(),EdgeType.GFp));
        edges.add(new Edge(startP,endP,e2.getScore(),EdgeType.FGp));
    }

    public void buildHamiltonPath() {

    }



    /*********************** Edge Class **************************************************/
    public class Edge implements Comparable<Edge>{
        /**
         * start point index
         */
        private int startP;
        /**
         * end point index
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

        public Edge( int startP, int endP, int weight,EdgeType type ){
            this.startP = startP;
            this.startP = endP;
            this.weight = weight;
            this.type = type;

        }

        public int getStartP() {
            return startP;
        }

        public int getEndP() {
            return endP;
        }

        public int getWeight() {
            return weight;
        }

        /**
         * for ranking by decreasing weight
         */
        @Override
        public int compareTo(Edge o) {
            return 0;             //  à definir
        }

        public boolean equals(Edge o) {
            if (o == this)
                return true;
            if(o instanceof Edge) {
                Edge tmp = (Edge)o;
                return tmp.startP == this.startP
                        && tmp.endP == this.endP
                        && tmp.weight == this.weight;
            }
            return false;
        }

        public EdgeType getType() {
            return type;
        }
        @Override
        public String toString() {
            return null;
        }
    }
}