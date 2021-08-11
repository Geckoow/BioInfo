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

		Fragment finalFrag = consensus.consensusVote(multAlign);
		Fragment ICFinalFrag = finalFrag.getComplementaryInverse();

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
    }
}
