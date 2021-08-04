package main;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageGaps {

	private ArrayList<Fragment> fragments;
	private ArrayList<LinkedFragment> lFragments;

	/**
	 * Constructor used in testing
	 */
	public ManageGaps(){
		this.fragments = new ArrayList<>();
		lFragments = new ArrayList<LinkedFragment>();
	}

	/**
	 * Constructor of gaps manager
	 * @param fm fileManager
	 */
	public ManageGaps(FileManager fm) {
		this.fragments = fm.getFragments();
		lFragments = new ArrayList<LinkedFragment>();
	}

	/**
	 * Function who returned list of fragments based on an edge(corresponding to starting/ending node of the edge)
	 * @param e Edge
	 * @return List of linked fragments corresponding to starting/ending node of the edge
	 */
	public LinkedFragment[] edgeToFragment(Edge e) {

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
		//On fait attention à modifier f/g en fonction du type d'arc
		if(e.getType().equals(EdgeType.FpG) || e.getType().equals(EdgeType.FpGp))
			f = fragments.get(e.getStartP()).getComplementaryInverse();
		if(e.getType().equals(EdgeType.GpF) || e.getType().equals(EdgeType.GpFp))
			f = fragments.get(e.getStartP()).getComplementaryInverse();
		if(e.getType().equals(EdgeType.FGp) || e.getType().equals(EdgeType.FpGp))
			g = fragments.get(e.getEndP()).getComplementaryInverse();
		if(e.getType().equals(EdgeType.GFp) || e.getType().equals(EdgeType.GpFp))
			g = fragments.get(e.getEndP()).getComplementaryInverse();

		SemiGlobAlignment sg = new SemiGlobAlignment(f, g);
		sg.generateAlignment();
		return new LinkedFragment[] {sg.alignF,sg.alignG};
	}

	/**
	 * Function who propagate the gaps upside
	 * @param startFrag Fragment index in our fragments list where we will start the propagation
	 * @param index Position where we will add the gap in the fragment
	 */
	public void propagTopGap(int startFrag, int index){
		for(int i =startFrag; i>=0; i--){
			if (startFrag > lFragments.size())
				break;
			lFragments.get(startFrag).insert(index, (byte)0);
		}
	}

	/**
	 * Function who propagate the gaps downside
	 * @param comFrag Actual fragment
	 * @param endFrag Fragment where we want to propagate the gap
	 * @param index Position where we want to add a gap in the fragment
	 */
	public void propagBotGap(LinkedFragment comFrag, LinkedFragment endFrag, int index){
		comFrag.insert(index, (byte)0);
		if(index > endFrag.getStartOffset())
			endFrag.insert(index, (byte) 0);
		else
			endFrag.addStartOffset(1);
	}

	/**
	 * Multiple alignment and gap propagation on all the fragments obtained from hamilton path
	 * @param path hamilton path
	 * @return List of linked fragments who are alligned and where all the gap where propagated
	 */
	public ArrayList<LinkedFragment> manageGaps(HamiltonPath path){
		HashMap<Integer, Edge> hamilton = path.getHalmitonPath();
		int hashKeyStart = path.getBeginPath();
		lFragments = new ArrayList<LinkedFragment>(path.getHalmitonPath().size());

		Edge startingEdge = hamilton.get(hashKeyStart);
		LinkedFragment[] edgeCouple = edgeToFragment(startingEdge);

		LinkedFragment f = edgeCouple[0];
		LinkedFragment gEnd = edgeCouple[1];

		lFragments.add(f);
		for (int i = 1; i < hamilton.size(); i++){
			lFragments.add(gEnd);
			startingEdge = hamilton.get(startingEdge.getEndP());
			edgeCouple = edgeToFragment(startingEdge);

			LinkedFragment gStart = edgeCouple[0];
			LinkedFragment h = edgeCouple[1];

			int startGap = gEnd.getStartOffset();
			gStart.addStartOffset(startGap);
			h.addStartOffset(startGap);

			while(startGap < gEnd.size() && startGap < gStart.size()){
				if(gEnd.getInnerList().get(startGap-gEnd.getStartOffset()) != gStart.getInnerList().get(0)){
					if(gEnd.getInnerList().get(startGap-gEnd.getStartOffset()) == (byte) 0)
						propagBotGap(gStart, h, startGap);
					else if(gStart.getInnerList().get(0) == (byte) 0) {
						propagTopGap(i, startGap);
					}
				}
				startGap++;
			}
			if(startGap < gEnd.size())
				h.addEndOffset(gEnd.size()-startGap);
			gEnd = h;
		}
		lFragments.add(gEnd);

		for (int k = 0, lFragmentsSize = lFragments.size(); k < lFragmentsSize; k++) {
			LinkedFragment frag = lFragments.get(k);
			if(k == 0)
				frag.addEndOffset(gEnd.size() - frag.reelSize()-frag.getEndOffset());
			else
				frag.addEndOffset(gEnd.size() - frag.reelSize());
		}
		return lFragments;
	}

	/**
	 * Getter for test
	 * @return list of linked fragments
	 */
	public ArrayList<LinkedFragment> getlFragments() {
		return lFragments;
	}

	/**
	 * Getter for test
	 * @return List of Fragments
	 */
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}
}
