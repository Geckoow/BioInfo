package test;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import main.Fragment;
import main.SemiGlobAlignment;

public class SemiGlobalAlignmentTest {
	Fragment f = new Fragment("cagcacttggattctcgg"); 
	Fragment g = new Fragment("cagcgtgg"); 
    
    Fragment a = new Fragment("atgc"); 
    Fragment b = new Fragment("tgcat"); 
    Fragment c = new Fragment("gcc"); 
    
    SemiGlobAlignment gf = new SemiGlobAlignment(g,f);
	SemiGlobAlignment ab = new SemiGlobAlignment(a,b);
	SemiGlobAlignment bc = new SemiGlobAlignment(b,c);


    
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

}
