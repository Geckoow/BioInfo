package test;

import main.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ManageGapsTest {

    /**
     * Test based on semi-global alignment of course pg63
     */
    Fragment f = new Fragment("acaatgatc----");
    Fragment g = new Fragment("-caa-ga-tcagga--");
    Fragment h = new Fragment("--agagtcaggacc");
    ManageGaps manager;
    ArrayList<LinkedFragment> lfragment;
    Edge edge;

    @Before
    public void init(){
        manager = new ManageGaps();
        lfragment = manager.getlFragments();
        LinkedFragment f1 = new LinkedFragment();
        LinkedFragment g1 = new LinkedFragment();
        LinkedFragment h1 = new LinkedFragment();
        for(int i = 0; i < f.getSize(); i++) {
            if(i < 10)
                f1.insertLast(f.getByteAtIndex(i));
            else
                f1.addEndOffset(1);
        }
        for(int i = 0; i < g.getSize(); i++) {
            if(i < 1)
                g1.addStartOffset(1);
            else if(1 <= i && i <= 13)
                g1.insertLast(g.getByteAtIndex(i));
            else
                g1.addEndOffset(1);
        }
        for(int i = 0; i < h.getSize(); i++) {
            if(i < 2)
                h1.addStartOffset(1);
            else
                h1.insertLast(h.getByteAtIndex(i));
        }
        lfragment.add(f1);
        lfragment.add(g1);
        lfragment.add(h1);

        manager.getFragments().add(f);
        manager.getFragments().add(g);
        manager.getFragments().add(h);

        edge = new Edge(0, 1, 2, EdgeType.FG);


    }
    @Test
    public void ManageGapTest(){

    }
    @Test
    public void propagTopGap(){
        int index = 7;
        while (index < lfragment.get(1).size()-lfragment.get(1).getEndOffset()) {
            if(lfragment.get(1).getInnerList().get(index-lfragment.get(1).getStartOffset()) == (byte)0)
                manager.propagTopGap(0, index);
            index++;
        }
        lfragment.get(0).addEndOffset(lfragment.get(1).size()-index);
        assertEquals("acaatga-tc------", lfragment.get(0).toString());
    }

    @Test
    public void propagBotGap(){
        int index = 0;
        while (index < 4) {
            if(lfragment.get(1).getInnerList().get(index) == (byte)0) {
                lfragment.get(1).getInnerList().remove(index);
                manager.propagBotGap(lfragment.get(1), lfragment.get(2), index);
            }
            index++;
        }
        lfragment.get(2).addStartOffset(lfragment.get(1).getStartOffset());
        for(int i = 1; i < lfragment.size(); i++){
            System.out.println(lfragment.get(i));
        }
        assertEquals("---a-gagtcaggacc", lfragment.get(2).toString());
    }
    @Test
    public void fragmentToEdge(){
        LinkedFragment[] list = manager.edgeToFragment(edge);
        System.out.println(list[0].toString());
        System.out.println(list[1].toString());
    }
}
