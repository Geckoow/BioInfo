package main;

import java.util.ArrayList;

public class OverlapGraph {
    private ArrayList<Fragment> nodes;
    public OverlapGraph(ArrayList<Fragment> fragList){
        this.nodes = fragList;
    }

public class Edge{
        private Fragment f;
        private Fragment g;
        private int weight;

        public Edge(Fragment f, Fragment g){
            this.f = f;
            this.g = g;
        }
        public void maxWeight(){
            SemiGlobAlignment e1 = new SemiGlobAlignment(f, g);
            SemiGlobAlignment e2 = new SemiGlobAlignment(g, f);
            SemiGlobAlignment e3 = new SemiGlobAlignment(f.getComplementary(), g);
            SemiGlobAlignment e4 = new SemiGlobAlignment(f, g.getComplementary());
            SemiGlobAlignment e5 = new SemiGlobAlignment(f.getComplementary(), g.getComplementary());
        }
    }
}

