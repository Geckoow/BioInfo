package main;

import java.util.ArrayList;

public class Consensus {
    private short[] tab;
    /**
     * Constructor
     */
    public Consensus(){
        tab = new short[]{ 0, 0, 0, 0};
    }
    public void resetArray(short[] array){
        for(int i = 0; i < array.length; i++){
            array[i] = 0;
        }
    }
    /**
     * Return byte based on array index
     * @param index array index
     * @return byte corresponding to array index
     */
    public byte indexToByte(short index){
        switch (index) {
            case 0:
                return (byte) 1;
            case 1:
                return (byte) -1;
            case 2:
                return (byte) 2;
            case 3:
                return (byte) -2;
        }
        throw new IllegalArgumentException(Integer.toString(index));
    }

    /**
     * return array index based on byte
     * @param b byte
     * @return array index corresponding to byte
     */
    public short byteToIndex(byte b){
        switch (b) {
            case (byte) 1:
                return 0;
            case (byte) -1:
                return 1;
            case (byte) 2:
                return 2;
            case (byte) -2:
                return 3;
        }
        throw new IllegalArgumentException(Byte.toString(b));
    }

    /**
     * Return the byte who appear the most time in an array
     * @param array array of short
     * @return the byte who appear the most
     */
    public byte maxIteration(short[] array){
        short maxIte = array[0];
        short indexMaxIte = 0;
        for(short i = 1; i < array.length; i++){
            if(array[i] > maxIte){
                maxIte = array[i];
                indexMaxIte = i;
            }
        }
        return indexToByte(indexMaxIte);
    }

    /**
     * Return the byte who appear the most at an index for all the fragments given
     * @param lfrag list of linked fragment
     * @param index index
     * @return the byte who appear the most at index for all the fragments in lfrag
     */
    public byte maxCol(ArrayList<LinkedFragment> lfrag, int index){
        resetArray(tab);
        for (int i = 0;i < lfrag.size(); i++){
            LinkedFragment frag = lfrag.get(i);
            if(index-frag.getStartOffset() >= 0 && index < frag.size()-frag.getEndOffset()){
            	Byte b = frag.getInnerList().get(index-frag.getStartOffset());
            	if(b != 0) {
            		short tabIndex = byteToIndex(b);
            		tab[tabIndex] += 1;}
            }
        }
        return maxIteration(tab);
    }

    /**
     * Create the final fragment based on a fragment list and a consensus vote
     * @param fragList List of linked fragments
     * @return the fragment obtained by a consensus vote over fraglist
     */
    public Fragment consensusVote(ArrayList<LinkedFragment> fragList){
        int index = fragList.get(0).size();
        byte[] list = new byte[index];
        for(int i = 0; i < index; i++){
            byte consensus = maxCol(fragList, i);
            list[i] = consensus;
        }
        Fragment finalFrag = new Fragment(list);
        return finalFrag;
    }
}
