package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Main {

	static int[] extractIntFromPath(String path){
		String str = path;
		str = str.replaceAll("[^-?0-9]+", " ");
		List<String> array = Arrays.asList(str.trim().split(" "));
		int[] tab = new int[2];
		tab[0] = parseInt(array.get(array.size()-2));
		tab[1] = parseInt(array.get(array.size()-1));
		return tab;
	}
	static void dnaSequencer(FileManager fm, String inFastaPath, String outFastaPath, String outICFastaPath){
		String completePath = inFastaPath;
		int[] numbers = extractIntFromPath(completePath);
		fm.parse(new File(completePath));
		int n = fm.getFragments().size();

		OverlapGraph graph = new OverlapGraph(fm.getFragments());

		HamiltonPath h = new HamiltonPath(graph,n);

		ManageGaps manager = new ManageGaps(fm);
		ArrayList<LinkedFragment> multAlign = manager.manageGaps(h);

		Consensus consensus = new Consensus();
		LinkedFragment finalFrag = consensus.consensusVote(multAlign);
		Fragment frag = new Fragment(finalFrag.toString());
		Fragment ICFinalFrag = frag.getComplementaryInverse();

		String fastaFormat = ">Groupe-3 Collection "+numbers[numbers.length-1]+" Longueur "+numbers[numbers.length-2]+"\n";
		try {
			FileWriter myWriter = new FileWriter(outFastaPath);
			FileWriter myWriter2 = new FileWriter(outICFastaPath);
			myWriter.write(fastaFormat+finalFrag.toString());
			myWriter2.write(fastaFormat+ICFinalFrag.toString());
			myWriter.close();
			myWriter2.close();
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
    public static void main(String[] args){
		//	FileManager rd = new FileManager();
			//Collection 10000
		//	dnaSequencer(rd, "Collections/10000/collection1.fasta", "Collections/10000/answer.fasta", "Collections/10000/ICAnswer.fasta");
			//Collection 11200
		//	dnaSequencer(rd, "Collections/11200/collection4.fasta", "Collections/11200/answer.fasta", "Collections/11200/ICAnswer.fasta");
			//Collection 16320
		//	dnaSequencer(rd, "Collections/16320/collection5.fasta", "Collections/16320/answer.fasta", "Collections/16320/ICAnswer.fasta");
		if(args.length == 3){
			try{
				FileManager rdu = new FileManager();
				dnaSequencer(rdu, args[0], args[1], args[2]);
			}catch (Exception e){
				System.out.println("Erreur vérifié la validité des chemins spécifiés");
			}
		}
		else {
			System.out.println("Erreur nombre de paramètres fournis incorrects");
		}
		
		/*rd.parse(new File("Collections/10000/collection1.fasta"));
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
    	System.out.println("begin index "+h.getBeginPath()); 
    	h.displayPath();
    	System.out.println("end Hamilton");
    	 
 		ManageGaps manager = new ManageGaps(rd);

		ArrayList<LinkedFragment> multAlign = manager.manageGaps(h);
		for(int i = 0; i < multAlign.size(); i++){
			System.out.println(multAlign.get(i));
		}

		Consensus consensus = new Consensus();
		//ConsensusSequence consensuss = new ConsensusSequence(multAlign);
		//Fragment finalFrags = consensuss.vote();
		
		LinkedFragment finalFrag = consensus.consensusVote(multAlign);
		System.out.println(finalFrag.toString());
		System.out.println();
		System.out.println(new Fragment(finalFrag).getComplementaryInverse().toString());
		//System.out.println(finalFrags.getComplementaryInverse().toString());
		
	}    */
    }
}
