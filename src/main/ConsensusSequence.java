package main;

import java.util.ArrayList;
import java.util.Iterator;

public class ConsensusSequence {
	
	private ArrayList<Iterator<Byte>> listIterators;
	private ArrayList<LinkedFragment> tabOfAlignmentFragment;
	private byte[] intToByte = new byte[]{1, -1, 2, -2};
	
	public ConsensusSequence(ArrayList<LinkedFragment> tabOfAlignmentFragment) {
		
		this.tabOfAlignmentFragment	= tabOfAlignmentFragment;
		listIterators = new ArrayList<Iterator<Byte>>(tabOfAlignmentFragment.size());
		for(LinkedFragment lf : tabOfAlignmentFragment )
			listIterators.add(lf.getInnerList().iterator());
	}
	
	/**
	 * returns a fragment, so the ith character is the most frequent character of the ith characters of the fragments of the multi-alignment
	 * @return fragment resulting from the majority vote
	 */
	public Fragment vote() {
		int fragSize = tabOfAlignmentFragment.get(0).size();
		Fragment resFragment = new Fragment(new byte[fragSize]);
		/*
		 * index 0, 1, 2, 3 respectively for the characters 'a' (1) , 't' (-1) , 'c' (2), 'g' (-2) 
		 */
		short [] occurenceTab = new short [4];   
		
		int line = 0;
		for(int j = 0; j < fragSize; j++) {
		//	for(int i = 0; i < tabOfAlignmentFragment.size(); i++) {
			// on ignore les lignes lorsque l'iterateur est en position des gaps de fin de fragment
			while( line < tabOfAlignmentFragment.size() - 1 && j > tabOfAlignmentFragment.get(line).reelSize()-1) 
				line++;
			int i = line;
			// on ignore les lignes lorsque l'iterateur est en position des gaps de debut de fragment
			while( i < tabOfAlignmentFragment.size() && j > tabOfAlignmentFragment.get(i).getStartOffset()-1) {
				byte b = listIterators.get(i).next();
				//on ignore les gaps
				if(b != 0) {
					if(b == 1)
						occurenceTab[0]++;
					else if(b == -1)
						occurenceTab[1]++;
					else if(b == 2)
						occurenceTab[2]++;
					else 
						occurenceTab[3]++;
				}
				i++;
			}
			int maxIndex = getMaxIndex(occurenceTab);
			resFragment.insertByte(j, intToByte[maxIndex]);
			occurenceTab = new short [4];
		}
		return resFragment;
	}
	/**
	 * help to know the index of max value for the choice of the most represented character during the consensus vote
	 * @param tab
	 * @return 0 or 1 or 2 or 3 if the most frequent character is a (1) or t (-1) or c (2) or g (-2) 
	 */
	public int getMaxIndex(short [] tab) {
		int maxInd = 0;
		for(int i = 0; i < tab.length; i++) {
			if(tab[i] > tab[maxInd])
				maxInd = i;		}
		return maxInd;	}
	
}
