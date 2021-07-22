package main;


import java.util.LinkedList;

public class SemiGlobAlignment {
    private final Fragment f;
    private final Fragment g;
    public final LinkedList<Byte> alignG;
    public final LinkedList<Byte> alignF;
  
    /**
     * 1 + size of f 
     */
    private int width;
   
    /**
     * 1 + size of g 
     */
    private int height;

    public int[][] matrix;

    private final short GAP = -2;
    private final short MATCH = 1;
    private final short MISSMATCH = -1;

    public SemiGlobAlignment(Fragment f, Fragment g){
        this.f = g;         // adaptation de l'implematation pour 
        this.g = f;			// obternir l'alignement f->G pour (f,g)s
        this.alignG = new LinkedList<Byte>();
        this.alignF = new LinkedList<Byte>();

        this.width = g.getSize() + 1;
        this.height = f.getSize() + 1;

        matrix = new int[width][height];

        instantiateMatrix();
    }
    public void instantiateMatrix(){
        initCol();
        initRows();

        for(int i = 1; i < width; i++){
            for(int j = 1; j < height; j++){
                sim(i, j);
            }
        }
    }
    public void initRows(){
        for(int i = 0; i < width; i++){
            matrix[i][0] = 0;
        }
    }
    public void initCol(){
        for(int i = 0; i < height; i++){
            matrix[0][i] = 0;
        }
    }

    public void sim(int i, int j){
        int score = matrix[i-1][j-1] + matching(i, j);
        if(matrix[i-1][j] + GAP > score)
            score = matrix[i-1][j] + GAP;
        if(matrix[i][j-1] + GAP > score)
            score = matrix[i][j-1] + GAP;
        matrix[i][j] = score;
    }

    public int matching(int i, int j){
        byte fChar = f.getByteAtIndex(i-1);
        byte gChar = g.getByteAtIndex(j-1);
        if (fChar == gChar)
            return MATCH;
        else
            return MISSMATCH;
    }
    /**
     * 
     * @return the index of the row with the highest score on the last column
     */
    public int getIndexMaxLastCol(){
        int max = matrix[0][height-1];
        int index = 0;
        for(int i = 1; i < width; i++){
            if(matrix[i][height-1] > max) {
                max = matrix[i][height-1];
                index = i;
            }
        }
        return index;
    }
    
    /**
     * 
     * @return the index of the column with the highest score on the last row
     */
    public int getIndexMaxLastLine(){
        int max = matrix[width-1][0];
        int index = 0;
        for(int i = 1; i < height; i++){
            if(matrix[width-1][i] > max) {
                max = matrix[width-1][i];
                index = i;
            }
        }
        return index;
    }

    public boolean upMatch(int i, int j){
        int currentScore = matrix[i][j];
        return currentScore == matrix[i-1][j] + GAP;
    }

    public boolean leftMatch(int i, int j){
        int currentScore = matrix[i][j];
        return currentScore == matrix[i][j-1] + GAP;
    }

    public boolean diagMatch(int i, int j){
        int currentScore = matrix[i][j];
        return currentScore == matrix[i-1][j-1] + matching(i, j);
    }
    public void startAlign(){
    	int iMax = getIndexMaxLastCol();
    	for(int i = f.getSize()-1; i >= iMax; i--){
        	alignG.addFirst(f.getByteAtIndex(i));
            alignF.addFirst((byte) 0);  //'-'
        }
    }
    public void fillAlignF(int index){

        if(index > 0){
            while (index-1 > -1){
            	 alignG.addFirst(f.getByteAtIndex(index-1));
                 alignF.addFirst((byte) 0);  // '-'
                 index--;
            }
        }
    }
    public void fillAlignG(int index){

        if(index > 0){
            while (index-1 > -1){
            	 alignF.addFirst(g.getByteAtIndex(index-1));
                 alignG.addFirst((byte) 0);  // '-'
                 index--;
            }
        }
    }
    public void generateAlignment(){  //f->g

        startAlign();

        int i = getIndexMaxLastCol();
        int j = height-1;

        while (i > 0 && j > 0){
            int fIndex = i -1;
            int gIndex = j -1;
            if(diagMatch(i, j)){
            	alignG.addFirst(f.getByteAtIndex(fIndex));
            	alignF.addFirst(g.getByteAtIndex(gIndex));
                i--;
                j--;
            }
            else if(upMatch(i, j)){
            	alignG.addFirst(f.getByteAtIndex(fIndex));
            	alignF.addFirst((byte) 0);
                i--;
            }
            else if(leftMatch(i, j)){
            	alignG.addFirst((byte) 0);
            	alignF.addFirst(g.getByteAtIndex(gIndex));
                j--;
            }
        }
        if (i>0)
        	fillAlignF(i);
        else
        	fillAlignG(j);
    }
    /**
     * the score of the global alignment from f to g
     * @return
     */
    public int getScore() {
    	//System.out.println(matrix[0].length);
    	return matrix[getIndexMaxLastCol()][height-1];
    }
    /**
     * the score of the global alignment from g to f
     * @return
     */
    public int getScoreTransposed() {
    	return matrix[width-1][getIndexMaxLastLine()];
    }
    	
}
