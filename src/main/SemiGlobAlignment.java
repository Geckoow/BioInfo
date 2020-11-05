package main;

public class SemiGlobAlignment {
    private final Fragment f;
    private final Fragment g;
    private final int width;
    private final int height;
    public final Fragment alignF;
    public final Fragment alignG;

    public int[][] matrix;

    private final int GAP = -2;
    private final int MATCH = 1;
    private final int MISSMATCH = -1;

    /**
     * Generate a semi global alignment between two fragments
     * @param f first Fragment the biggest
     * @param g second Fragment the smaller
     */
    public SemiGlobAlignment(Fragment f, Fragment g){
        this.f = f;
        this.g = g;
        this.alignF = new Fragment();
        this.alignG = new Fragment();

        this.width = f.getSize() + 1;
        this.height = g.getSize() + 1;

        matrix = new int[width][height];

        instantiateMatrix();
    }

    /**
     * Initialize the sim score matrix
     */
    public void instantiateMatrix(){
        initCol();
        initRows();

        for(int i = 1; i < width; i++){
            for(int j = 1; j < height; j++){
                sim(i, j);
            }
        }
    }

    /**
     * Fill the first row with 0
     */
    public void initRows(){
        for(int i = 0; i < width; i++){
            matrix[i][0] = 0;
        }
    }

    /**
     * Fill the first column with 0
     */
    public void initCol(){
        for(int i = 0; i < height; i++){
            matrix[0][i] = 0;
        }
    }

    /**
     * Calculate the score of similarity between two nucleotide
     * @param i index of the row
     * @param j index of the column
     */
    public void sim(int i, int j){
        int score = matrix[i-1][j-1] + matching(i, j);
        if(matrix[i-1][j] + GAP > score)
            score = matrix[i-1][j] + GAP;
        if(matrix[i][j-1] + GAP > score)
            score = matrix[i][j-1] + GAP;
        matrix[i][j] = score;
    }

    /**
     * Check if two nucleotide match
     * @param i index of the first nucleotide
     * @param j index of the second nucleotide
     * @return match or missmatch
     */
    public int matching(int i, int j){
        char fChar = f.getCharAtIndex(i-1);
        char gChar = g.getCharAtIndex(j-1);
        if (fChar == gChar)
            return MATCH;
        else
            return MISSMATCH;
    }

    /**
     * Return the row index of the maximum score in the last column of the matrix
     * @return the max score in the last column
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
     * Return the row index of the maximum score in the last row of the matrix
     * @return the max score in the last row
     */
    public int getIndexMaxLastRow(){
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
    /**
     * Check if the match comes from the upper row
     * @param i index of row
     * @param j index of column
     * @return true if the match comes from the upper row, false either
     */
    public boolean upMatch(int i, int j){
        int currentScore = matrix[i][j];
        return currentScore == matrix[i-1][j] + GAP;
    }
    /**
     * Check if the match comes from the left column
     * @param i index of row
     * @param j index of column
     * @return true if the match comes from the left column, false either
     */
    public boolean leftMatch(int i, int j){
        int currentScore = matrix[i][j];
        return currentScore == matrix[i][j-1] + GAP;
    }
    /**
     * Check if the match comes from the diag
     * @param i index of row
     * @param j index of column
     * @return true if the match comes from the diag, false either
     */
    public boolean diagMatch(int i, int j){
        int currentScore = matrix[i][j];
        return currentScore == matrix[i-1][j-1] + matching(i, j);
    }

    /**
     * Start filling the alignment with the start of the biggest fragment and gap for the small one
     */
    public void startAlign(){
        for(int i = f.getSize()-1; i >= getIndexMaxLastCol(); i--){
            alignF.list.add(f.getCharAtIndex(i));
            alignG.list.add('-');
        }
    }

    /**
     * Complete the alignment with the rest of the fragment or gap
     * @param index index in the fragment
     */
    public void fillAlign(int index){
        if(index > 0){
            while (index-1 > -1){
                alignF.list.add(f.getCharAtIndex(index-1));
                alignG.list.add('-');
                index--;
            }
        }
        alignF.invert();
        alignG.invert();
    }
    public int fgAligment(){
        if(getIndexMaxLastCol() != 0) {
            int include = generateAlignment(getIndexMaxLastCol(), height - 1, f, g, true);
            if(include > 0)
                return matrix[getIndexMaxLastCol()][height-1];
            else
                return -1;
        }
        else
            return 0;
    }
    public int gfAlignment(){
        if(getIndexMaxLastRow() != 0) {
            int include = generateAlignment(width-1, getIndexMaxLastRow(), f, g, false);
            if(include > 0)
                return matrix[width-1][getIndexMaxLastRow()];
            else
                return -1;
        }
        else
            return 0;
    }
    /**
     * Generate the alignment between the two fragments of the constructor and store them in the alignments frags
     */
    public int generateAlignment(int i, int j, Fragment f, Fragment g, boolean leftToRight){

        //startAlign();
        int interScore = matrix[i][j];

        while (i > 0 && j > 0){
            int fIndex = i -1;
            int gIndex = j -1;
            if(diagMatch(i, j)){
                alignF.list.add(f.getCharAtIndex(fIndex));
                alignG.list.add(g.getCharAtIndex(gIndex));
                i--;
                j--;
            }
            else if(upMatch(i, j)){
                alignF.list.add(f.getCharAtIndex(fIndex));
                alignG.list.add('-');
                i--;
            }
            else if(leftMatch(i, j)){
                alignF.list.add('-');
                alignG.list.add(g.getCharAtIndex(gIndex));
                j--;
            }
        }
        //fillAlign(i);
        alignF.invert();
        alignG.invert();
        if(leftToRight == true)
            return j;
        else
            return i;
    }
}
