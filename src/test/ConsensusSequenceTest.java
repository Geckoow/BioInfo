package test;


import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

import main.Consensus;
import main.Fragment;
import main.LinkedFragment;

public class ConsensusSequenceTest {
	
	public ArrayList<LinkedFragment> l = new ArrayList<LinkedFragment>();
	public Fragment f = new Fragment();
	
	@Test
	public void voteTest() {
		LinkedFragment lf = new LinkedFragment(stringToByte("atgc"));
		lf.addEndOffset(5);
		l.add(lf);												//	"atgc-----"
																//	"-tgcat---"
		lf = new LinkedFragment(stringToByte("tgcat"));			//	"------gcc"
		lf.addStartOffset(1);
		lf.addEndOffset(3);
		l.add(lf);
		
		lf = new LinkedFragment(stringToByte("gcc"));
		lf.addStartOffset(6);
		l.add(lf);
		
		Consensus cc = new Consensus();
		Fragment finalFrag = cc.consensusVote(l);
		System.out.println(finalFrag);

	}
	
	@Test

	
	public LinkedList<Byte> stringToByte(String s){
		LinkedList<Byte> linkedList = new LinkedList<Byte>();
		for(int i = 0; i < s.length(); i++) {
			linkedList.add(f.getByteFromChar(s.charAt(i)));
			}
		return linkedList;
		}
	

}
