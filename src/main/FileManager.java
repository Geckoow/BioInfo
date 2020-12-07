package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	private short idCollection;
	private ArrayList<Fragment> fragments;
	
	public void parse(File file) {
		fragments = new ArrayList<Fragment>();
		try {
			String line, frag = "";
			BufferedReader in = new BufferedReader(new FileReader(file));
			line = in.readLine();
			char colNum = line.charAt(-1);
			this.idCollection = Short.parseShort(""+colNum);
			while((line = in.readLine()) != null) {
				if(line.startsWith(">") || line.equals("")) {
					if(frag.length() > 0)                 //String.length complexité o(1) ou o(n)??
						fragments.add(new Fragment(frag));
					frag="";
				}
				else {
					
					frag += line;
					}
			}
			if(frag.length() > 0)
				fragments.add(new Fragment(frag));
			in.close();
		}catch(FileNotFoundException e) {
			System.out.println("fichier absent");
			e.printStackTrace();
			//System.exit(-1);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("erreur de lecture");
			//System.exit(-1);
		}
		
	}

	public short getIdCollection() {
		return idCollection;
	}


	public ArrayList<Fragment> getFragments() {
		return fragments;
	}

}

