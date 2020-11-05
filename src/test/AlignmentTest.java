package test;

import main.Fragment;
import main.SemiGlobAlignment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlignmentTest {
    /**
     * Test based on semi-global alignment of course pg63
     */
    Fragment f = new Fragment("CAGCACTTGGATTCTCGG");
    Fragment g = new Fragment("CAGCGTGG");
    SemiGlobAlignment alignment;

    @Before
    public void init(){
        alignment = new SemiGlobAlignment(f, g);
    }

    @Test
    public void testMatrix(){

    }
    @Test
    public void maxLastColTest(){
        assertEquals(10, alignment.getIndexMaxLastCol());
        assertEquals(3, alignment.matrix[10][8]);
    }
    @Test
    public void alignTest(){
        System.out.println(alignment.gfAlignment());
        System.out.println(alignment.alignF.toString());
        System.out.println(alignment.alignG.toString());
        assertEquals("CAGCA-CTTGGATTCTCGG", alignment.alignF.toString());
        assertEquals("---CAGCGTGG--------", alignment.alignG.toString());
    }
}
