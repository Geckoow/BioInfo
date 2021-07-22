package test;
import static org.junit.Assert.*;

import org.junit.Test;

import main.Fragment;

public class FragmentTest {
    Fragment f = new Fragment("-atcg-gt");
    Fragment f_compInverse = new Fragment("ac-cgat-");
    Fragment f_comp = new Fragment("-tagc-ca");
    Fragment f_inverse = new Fragment("tg-gcta-");
    

    @Test
    public void testComplementaryInverse(){
        Fragment comp = f.getComplementaryInverse();
        assertEquals(f_compInverse.toString(), comp.toString());
    }
    @Test
    public void testComplementary(){
        Fragment comp = f.getComplementary();
        assertEquals(f_comp.toString(), comp.toString());
    }
    
    @Test
    public void testInverse(){
        Fragment comp = f.invert();
        assertEquals(f_inverse.toString(), comp.toString());
    }

}
