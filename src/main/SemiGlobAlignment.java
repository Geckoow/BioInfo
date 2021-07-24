package main;


public class SemiGlobAlignment {
    private final Fragment f;
    private final Fragment g;
    public final LinkedFragment alignG;
    public final LinkedFragment alignF;
  
    /**
     * 1 + size of g (col)
     */
    private int _cols; 
   
    /**
     * 1 + size of f (line)
     */
    private int _lines;

    public short[][] matrix;

    private final short GAP = -2;
    private final short MATCH = 1;
    private final short MISSMATCH = -1;

    public SemiGlobAlignment(Fragment f, Fragment g){
        this.f = f;         
        this.g = g;			
        this.alignG = new LinkedFragment();
        this.alignF = new LinkedFragment();

        this._cols = g.getSize() + 1;
        this._lines = f.getSize() + 1;

        matrix = new short[_lines][_cols];

        instantiateMatrix();
    }
    /**
     * builds the semi-global alignment matrix
     */
    public void instantiateMatrix(){
        initCol();
        initRows();

        for(int i = 1; i < _lines; i++)
            for(int j = 1; j < _cols; j++)
                sim(i, j);
    }
    /**
     * place the zeros on the first column of the semi-global alignment matrix
     */
    public void initCol(){
        for(int i = 0; i < _lines; i++)
            matrix[i][0] = 0;
    }
    /**
     * place the zeros on the first line of the semi-global alignment matrix
     */
    public void initRows(){
        for(int j = 0; j < _cols; j++)
            matrix[0][j] = 0;
    }

    public void sim(int i, int j){
        int score = matrix[i-1][j-1] + matching(i, j);
        if(matrix[i-1][j] + GAP > score)
            score = matrix[i-1][j] + GAP;
        if(matrix[i][j-1] + GAP > score)
            score = matrix[i][j-1] + GAP;
        matrix[i][j] = (short)score;
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
        int max = matrix[0][_cols-1];
        int index = 0;
        for(int i = 1; i < _lines; i++){
            if(matrix[i][_cols-1] > max) {
                max = matrix[i][_cols-1];
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
        int max = matrix[_lines-1][0];
        int index = 0;
        for(int i = 1; i < _cols; i++){
            if(matrix[_lines-1][i] > max) {
                max = matrix[_lines-1][i];
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
    	int iMax = getIndexMaxLastLine();
    	for(int i = _cols-2; i >= iMax; i--){ //_cols-2 == g.size() - 1
        	alignG.insertFirst(g.getByteAtIndex(i));
        }
    	alignF.addEndOffset(_cols-1 - iMax );  //'-'
    }
    public void fillAlignF(int index){
    	alignG.addStartOffset(index); 
        while (index > 0){
            	 alignF.insertFirst(f.getByteAtIndex(index-1));
                 index--;
            }
        
    }
    /*
    public void fillAlignG(int index){

        if(index > 0){
            while (index-1 > -1){
            	 alignF.addFirst(g.getByteAtIndex(index-1));
                 alignG.addFirst((byte) 0);  // '-'
                 index--;
            }
        }
    }
    */
    public void generateAlignment(){  //f->g

        startAlign();

        int j = getIndexMaxLastLine();
        int i = _lines-1;

        while (i > 0 && j > 0){
            int fIndex = i;
            int gIndex = j;
            if(diagMatch(i, j)){
            	alignF.insertFirst(f.getByteAtIndex(fIndex-1));
            	alignG.insertFirst(g.getByteAtIndex(gIndex-1));
                i--;
                j--;
            }
            else if(upMatch(i, j)){
            	alignF.insertFirst(f.getByteAtIndex(fIndex-1));
            	alignG.insertFirst((byte) 0);
                i--;
            }
            else if(leftMatch(i, j)){
            	alignF.insertFirst((byte) 0);
            	alignG.insertFirst(g.getByteAtIndex(gIndex-1));
                j--;
            }
        }
        
        fillAlignF(i);
  
        //fillAlignG(j);
    }
    /**
     * the score of the global alignment from f to g :if f is not included to g 
     * -1 else
     * @return
     */
    public int getFGScore() {
    	int indexMax = getIndexMaxLastLine();
    	if (indexMax == 0) // pas de preffixe commun
    		return 0;
    	else if(fIncludedInG(new Cell(_lines - 1, indexMax)))
    		return -1;
    	else
    		//System.out.println(matrix[0].length);
    		return matrix[_lines-1][indexMax];
    	
    }
    
    /**
     * tests the inclusion of the alignment f going up the starting cell to the end cell
     * @param cell starting cell of the alignment reconstruction
     * @return true if f is included in g, false else
     */
    private boolean fIncludedInG(Cell cell) {
		return getLastCell(cell).line > 0;
	}
    
    /**
     * tests the inclusion of the alignment g going up the starting cell to the end cell
     * @param cell starting cell of the alignment reconstruction
     * @return true if g is included in f, false else
     */
    private boolean gIncludedInF(Cell cell) {
    	return getLastCell(cell).col > 0;
    }

	/**
     * the score of the global alignment from g to f : if g is not included to f 
     * -1 else
     * @return
     */
    public int getScoreTransposed() {
    	int indexMax = getIndexMaxLastCol();
    	if (indexMax == 0)
    		return 0;
    	else if(gIncludedInF(new Cell(indexMax, _cols - 1)))
    		return -1;
    	else
    	return matrix[getIndexMaxLastCol()][_cols-1];
    }
    
    /**
     * goes up the alignment to the final cell
     * @param beginCell starting cell of the alignment reconstruction
     * @return final cell of alignment reconstruction
     */
    public Cell getLastCell(Cell beginCell ) {
    	Cell beginCel = new Cell(beginCell.line,beginCell.col);
    	
    	while (beginCel.line >= 1 && beginCel.col >= 1) {
    		if(diagMatch(beginCel.line, beginCel.col))
    			beginCel.setIndex(beginCel.line - 1, beginCel.col - 1);
    		else if(upMatch(beginCel.line, beginCel.col))
    			beginCel.setIndex(beginCel.line - 1, beginCel.col);
    		else {
    			beginCel.setIndex(beginCel.line, beginCel.col - 1);
			}
    			
    	}
    	return beginCel;
    }
    /**
     * A class that represents a cell of the semi-global alignment matrix
     *
     */
    private class Cell{
    	private int line;
    	private int col;
    	
    	private Cell(int line,int col) {
    		this.line = line;
    		this.col = col;
    	}
    	private void setIndex(int i, int j) {
    		line = i;
    		col = j;
    	}
    }
    	
}
