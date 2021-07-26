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
        assertEquals(3, alignment.getMatrix()[10][8]);
    }
    @Test
    public void alignTest(){
        alignment.generateAlignment();
        assertEquals("cagcacttggattctcgg-----", alignment.alignF.toString());
        assertEquals("---------------cagcgtgg", alignment.alignG.toString());
    }
}
