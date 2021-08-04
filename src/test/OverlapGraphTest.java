package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import main.Fragment;
import main.SemiGlobAlignment;

public class OverlapGraphTest {
	Fragment f = new Fragment("ctg");
	Fragment fp= new Fragment("cag");
	Fragment g = new Fragment("tga");
	Fragment gp= new Fragment("tca");
	
	@Test
	/* weight(f->g) = weight(g'->f')*/
	public void testWeightFGwithGpFP() {
		SemiGlobAlignment sga = new SemiGlobAlignment(f,g);
		SemiGlobAlignment sgaa = new SemiGlobAlignment(gp,fp);
		assertTrue(sga.getFGScore() == sgaa.getFGScore());
	}
	
	@Test
	/* weight(g->f) = weight(f'->g')*/
	public void testWeightGFwithFPGp() {
		SemiGlobAlignment sga = new SemiGlobAlignment(g,f);
		SemiGlobAlignment sgaa = new SemiGlobAlignment(fp,gp);
		assertTrue(sga.getFGScore() == sgaa.getFGScore());
	}
	
	@Test
	/* weight(g'->f) = weight(f'->g)*/
	public void testWeightGpFwithFpG() {
		SemiGlobAlignment sga = new SemiGlobAlignment(gp,f);
		SemiGlobAlignment sgaa = new SemiGlobAlignment(fp,g);
		assertTrue(sga.getFGScore() == sgaa.getFGScore());
	}
	
	@Test
	/* weight(g->f') = weight(f->g')*/
	public void testWeightGFpwithFGP() {
		SemiGlobAlignment sga = new SemiGlobAlignment(g,fp);
		SemiGlobAlignment sgaa = new SemiGlobAlignment(f,gp);
		assertTrue(sga.getFGScore() == sgaa.getFGScore());
	}
}
