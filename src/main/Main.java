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
			int i = 0;
			String frag = finalFrag.toString();
			String icFrag = ICFinalFrag.toString();
			myWriter.write(fastaFormat);
			myWriter2.write(fastaFormat);
			while (i < finalFrag.toString().length()){
				if(i+80 < finalFrag.toString().length()) {
					myWriter.write(frag.substring(i, i + 80) + "\n");
					myWriter2.write(icFrag.substring(i, i + 80) + "\n");
				}
				else {
					myWriter.write(frag.substring(i) + "\n");
					myWriter2.write(icFrag.substring(i) + "\n");
				}
				i = i+80;
			}
			myWriter.close();
			myWriter2.close();
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
    public static void main(String[] args){
		/*if(args.length == 0){
			FileManager rd = new FileManager();
			//Collection 10000
			dnaSequencer(rd, "Collections/100000/collection2.fasta", "Collections/100000/answer.fasta", "Collections/100000/ICAnswer.fasta");
		}*/
		if(args.length == 5){
			if(args[1].equals("-out") && args[3].equals("-out-ic")) {
				try {
					FileManager rdu = new FileManager();
					dnaSequencer(rdu, args[0], args[2], args[4]);
				} catch (Exception e) {
					System.out.println("Erreur vérifié la validité des chemins spécifiés");
				}
			}
			else{
				System.out.println("Format d'entrée incorrectes, le format doit être : InPath -out OutPath -out-ic OutIcPath");
			}
		}
		else {
			System.out.println("Erreur nombre de paramètres fournis incorrects : il doit y avoir 5 argument dont -out et -out-ic comme arguments 2 et 4");
		}
    }
}
