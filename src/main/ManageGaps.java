package main;

import java.util.ArrayList;

public class ManageGaps {

	private ArrayList<Fragment> fragments;
	private ArrayList<LinkedFragment> lFragments;
	
	public ManageGaps(FileManager fm) {
		this.fragments = fm.getFragments();	
		lFragments = new ArrayList<LinkedFragment>();
		}
	/**
	 * construit les fragments avec une meme taille
	 * @param path hamilton path
	 */
	public void buildFragments(HamiltonPath path) {
		Edge e = path.getHalmitonPath().get(path.getBeginPath());
		LinkedFragment [] fg = edgeToFragment(e);
	}
	
	private LinkedFragment[] edgeToFragment(Edge e) {
		
		Fragment f = fragments.get(e.getStartP());
		Fragment g = fragments.get(e.getEndP());
		/*
		 * cas d'arc n'ayant rien en commun attcg----
		 * 										 acfg
		 */
		if(e.getWeight() == 0) {
			LinkedFragment f1 = new LinkedFragment();
			LinkedFragment g1 = new LinkedFragment();
			for(int i = 0; i < f.getSize(); i++) {
				f1.insertFirst(f.getByteAtIndex(i));
			}
			f1.addEndOffset(g1.reelSize());
			g1.addStartOffset(f1.reelSize());
			for(int i = 0; i < g.getSize(); i++) {
				g1.insertFirst(g.getByteAtIndex(i));
			}
			return new LinkedFragment[] {f1,g1};
			
		}
		if(e.getType().equals(EdgeType.FpG) || e.getType().equals(EdgeType.FpGp))
			f = f.getComplementaryInverse();
		if(e.getType().equals(EdgeType.GpF) || e.getType().equals(EdgeType.GpFp))
			f = g.getComplementaryInverse();
		if(e.getType().equals(EdgeType.FGp) || e.getType().equals(EdgeType.FpGp))
			g = g.getComplementaryInverse();
		if(e.getType().equals(EdgeType.GFp) || e.getType().equals(EdgeType.GpFp))
			g = f.getComplementaryInverse();
		
		SemiGlobAlignment sg = new SemiGlobAlignment(f, g);	
		sg.generateAlignment();
		return new LinkedFragment[] {sg.alignF,sg.alignG};
	}
}
