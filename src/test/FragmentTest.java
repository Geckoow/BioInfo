package test;
import static org.junit.Assert.*;

import org.junit.Test;

import main.Fragment;

public class FragmentTest {
    Fragment f = new Fragment("-atcg-gt");
    Fragment g = new Fragment("ac-cgat-");

    @Test
    public void testComp(){
        Fragment comp = f.getComplementary();
        assertEquals(g.toString(), comp.toString());
    }
    
}
