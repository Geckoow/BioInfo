package main;

import java.io.File;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    public static void main(String[] args){
    	
    	FileManager rd= new FileManager ();
        rd.parse(new File("Collections/3/collection1.fasta"));
        int n = rd.getFragments().size();
        System.out.println("nombre de fragments: "+n);

    	System.out.println("start overlapGraph");
    	OverlapGraph graph = new OverlapGraph(rd.getFragments());
    	PriorityBlockingQueue<Edge> edges = graph.getEdges();
    	int m = edges.size();
    	System.out.println(m);
        for (int i = 0; i < m; i++){
        		Edge e = edges.poll();
        		System.out.println(e.getType());
                System.out.println(e);
        }
        System.out.println("end overlapGraph");
        
    	System.out.println("start Hamilton");
    	
    	System.out.println(graph.getEdges().size());
    	
    	HamiltonPath h = new HamiltonPath(graph,n);
    	
    	System.out.println("arcs:" +h.getHalmitonPath().size());
    	h.displayPath();
    	System.out.println("end Hamilton");
    	 
 
 /*
    	
      //  Fragment ff = new Fragment("CAGCACTTGGATTCTCGG".toLowerCase());
     //  Fragment f = new Fragment("CAGCGTGG".toLowerCase());
       

        

        SemiGlobAlignment alignment = new SemiGlobAlignment(d, a);
        int q = alignment.getIndexMaxLastCol();
        System.out.println(q);
        alignment.generateAlignment();
        for (int i = 0; i < alignment.matrix.length; i++){
            for(int j = 0; j < alignment.matrix[0].length; j++) {
                System.out.print(alignment.matrix[i][j]);
            }
            System.out.print("\n");
        }
        for(int i = 0; i < alignment.alignF.size(); i++){
            System.out.print(d.getCharFromByte(alignment.alignF.get(i)));
        }
        System.out.println("\n");
        for(int i = 0; i < alignment.alignF.size(); i++){
            System.out.print(d.getCharFromByte(alignment.alignG.get(i)));
        }
        System.out.println(alignment.getScore());
        System.out.println(alignment.getScoreTransposed());
        }     
      //  ReadFile rd= new ReadFile();
        //rd.parse(new File("Collections/10000/collection1.fasta"));
       // System.out.print( rd.fragments.size());
        
    */    
    }
}
