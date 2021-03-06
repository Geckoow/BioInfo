package main;

public class Fragment {
	/**
	 * The byte array materializing the fragment
	 */
    private byte[] list;
    
    /**
     * true if this fragment is the inverse fragment of another
     */
    private boolean isInverse = false;
     /**
      * true if this fragment is the complementary fragment of another
      */
    private boolean isComplementary = false;
    /**
     * 
     */
    private int startOffset = 0;
    /**
     * 
     */
    private int endOffset = 0;
    
    
    public Fragment(byte[] l){
        this.list = l;
    }
    
    public Fragment(String s){
    	list = new byte[s.length()];
    	for (int i = 0; i < s.length(); i++){
    		list[i]=this.getByteFromChar(s.charAt(i)); //charAt en temps constant
    	}
    }

  
	/**
	 * method that converts a char to a byte.
	 * @param c char to convert
	 * @return 0, 1, -1, 2, -2 respectively for '-', 'a', 't', 'c', 'g'
	 */
    public byte getByteFromChar(char c) {
        switch (c) {
            case '-':
                return 0;
            case 'a' :
                return 1;
            case 't':
                return -1;
            case 'c':
                return 2;
            case 'g':
                return -2;
            default:
                throw new IllegalArgumentException();
        }
    }
    
	/**
	 * method that converts a byte to a char.
	 * @param b byte to convert
	 * @return '-', 'a', 't', 'c', 'g' respectively for 0, 1, -1, 2, -2 
	 */
    public char getCharFromByte(byte b){
        switch (b) {
            case 0:
                return '-';
            case 1:
                return 'a';
            case -1:
                return 't';
            case 2:
                return 'c';
            case -2:
                return 'g';
            default:
                throw new IllegalArgumentException();
        }
    }
    
    /**
     * method which returns the complement of a byte 
     * @param b byte to complement
     * @return the complement of the character
     */
    public byte getCharComplementary(byte b){
        return (byte) -b;
    }
    
    /**
     * method which returns the complement of this Fragment
     * @return complementary of this fragments
     */
    public Fragment getComplementary(){
        byte[] comp = new byte[list.length];
        int listSize = list.length-1;
        for(int i = list.length-1; i > -1; i--){
        	comp[listSize -i] = getCharComplementary(list[i]);
        }
        return new Fragment(comp);
    }
    /**
     * reverse this fragment
     */
    public void invert(){
    	byte[] invert = new byte[list.length];
    	int listSize = list.length-1;
        for(int i = list.length-1; i > -1; i--){
        	invert[listSize-i] = list[i];
        }
        list = invert;
    }
    
    public byte getByteAtIndex(int i){
        return list[i];
    }
    public int getSize(){
        return list.length;
    }
    
    @Override
    public String toString(){
        String frag = "";
        for(int i = 0; i < list.length; i++){
            frag += this.getCharFromByte(list[i]);
        }
        return frag;
    }

	public boolean isInverse() {
		return isInverse;
	}

	public void setInverse(boolean isInverse) {
		this.isInverse = isInverse;
	}

	public boolean isComplementary() {
		return isComplementary;
	}

	public void setComplementary(boolean isComplementary) {
		this.isComplementary = isComplementary;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void addStartOffset(int startOffset) {
		startOffset += startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void addEndOffset(int endOffset) {
		endOffset += endOffset;
	}
   
    
}
