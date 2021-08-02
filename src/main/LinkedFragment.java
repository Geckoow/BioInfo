package main;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedFragment {
	
    /**
     * other representation of Fragment with linkedList 
     */
    private LinkedList<Byte> innerList; //list without start and end offset
    

    /**
     * The number of gaps at the start of the word
     */
    private int startOffset = 0;
    /**
     * The number of gaps at the end of the word
     */
    private int endOffset = 0;
    
    
    /*
     * initialization constructor
     */
    public LinkedFragment() {
    	this.innerList = new LinkedList<Byte>();
    	
    }
    
    public LinkedFragment(LinkedList<Byte> l) {
    	this.innerList = l;
    	
    }
    /**
     * insert a byte at the start of the fragment
     * @param b
     */
    public void insertFirst(byte b) {
    	innerList.addFirst(b);
    }
    
    /**
     * insert a byte at the end of the fragment
     * @param b
     */
    public void insertLast(byte b) {
    	innerList.addLast(b);
    }
    
    /**
     * insert a byte at the specific position of the fragment
     * @param index
     * @param b
     */
    public void insert(int index ,byte b) {
    	if(index >= startOffset && index <= (startOffset + innerList.size() - 1))
    		innerList.add(index-startOffset,b);
    	//if the index is found after the end of the fragment and the data is a gap, we increment endOffset
    	else if(index >= (startOffset + innerList.size()) && b == 0 )
    		endOffset++;
    	//if the index is found before the start of the fragment and the data is a gap, we increment startOffset
    	else if(index < startOffset && b == 0 )
    		startOffset++;
    	else
    		 throw new IllegalArgumentException("index must be included in the zone without gaps");
    	
    }
    public int getStartOffset() {
		return startOffset;
	}

	public void addStartOffset(int values) {
		startOffset += values;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void addEndOffset(int values) {
		endOffset += values;
	}
	/**
	 * 
	 * @return a reference to the innerList
	 */
	public LinkedList<Byte> getInnerList() {
		return innerList;
	}
	/**
	 * size of fragment with start and end Gaps
	 * return startOffset + length of fragment + end Gaps
	 */
	public int size() {
		return startOffset+innerList.size()+endOffset;
	}
	/**
	 * size of fragment without endGaps
	 * return startOffset + length of fragment
	 */
	public int reelSize() {
		return startOffset+innerList.size();
	}
	@Override
	public String toString() {
		Fragment frag = new Fragment(this);
		return frag.toString();
	}
	
	public Iterator<Byte> listIterator(){
		return innerList.iterator();
	}

}
