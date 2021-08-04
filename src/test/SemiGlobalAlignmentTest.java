package test;

import static org.junit.Assert.assertFalse;


import org.junit.Test;

import main.Fragment;
import main.SemiGlobAlignment;

public class SemiGlobalAlignmentTest {
//	Fragment f = new Fragment("cagcacttggattctcgg"); 
//	Fragment g = new Fragment("cagcgtgg"); 
	
	Fragment f = new Fragment("ctaaaaatcc"); 
	Fragment g = new Fragment("ttatgcatta"); 
    
    Fragment a = new Fragment("atgc"); 
    Fragment b = new Fragment("tgcat"); 
    Fragment c = new Fragment("gcc"); 
    
    SemiGlobAlignment fg = new SemiGlobAlignment(f,g);
	SemiGlobAlignment ab = new SemiGlobAlignment(a,b);
	SemiGlobAlignment bc = new SemiGlobAlignment(b,c);

	  @Test
	    public void generateAlignmentTest() 
	    {
	    	//CAGCA-CTTGGATTCTCGG
	    	//   CAGCGTGG
	    	fg.generateAlignment();
	    	String f1 = fg.alignF.toString();
	    	String g1 = fg.alignG.toString();
	    	System.out.println("score fg: "+fg.getFGScore());
	    	System.out.println("score gf: "+fg.getScoreTransposed());
	    	
	    	System.out.println(fg.alignF.getStartOffset());
	    	System.out.println(f1);
	    	System.out.println(fg.alignF.getEndOffset());
	    	
	
	    	System.out.println(fg.alignG.getStartOffset());
	    	System.out.println(g1);
	    	System.out.println(fg.alignG.getEndOffset());
	    	
	    	fg.displayMatrix();
	    						 
	    	assertFalse(g1.equals("cagca-cttggattctcgg"));
	    	assertFalse(f1.equals("---cagcgtgg--------"));
	    }
}
	    	

   /* 
    @Test
    public void generateAlignmentTest() 
    {
    	//CAGCA-CTTGGATTCTCGG
    	//   CAGCGTGG
    	gf.generateAlignment();
    	String f1 = LinkedListToString(gf.alignF);
    	String g1 = LinkedListToString(gf.alignG);
    	assertTrue(g1.equals("cagca-cttggattctcgg"));
    	assertTrue(f1.equals("---cagcgtgg--------"));
    	
    	//ATGC 
    	// TGCAT
    	ab.generateAlignment();
    	String a1 = LinkedListToString(ab.alignF);
    	String b1 = LinkedListToString(ab.alignG);
    	assertTrue(a1.equals("atgc--"));
    	assertTrue(b1.equals("-tgcat"));
    	
    	//  TGCAT
    	//       GCC
    	bc.generateAlignment();
    	String b2 = LinkedListToString(bc.alignF);
    	String c1 = LinkedListToString(bc.alignG);
    	assertTrue(b2.equals("tgcat---"));
    	assertTrue(c1.equals("-----gcc"));
    }
    
    @Test
    public void getScore() {
    	
    }
   
    public String LinkedListToString(LinkedList<Byte> l) {
    	String result = "";
    	for(int i = 0; i < l.size(); i++){
             result+=f.getCharFromByte(l.get(i));
         }
    	return result;
    }
*/

