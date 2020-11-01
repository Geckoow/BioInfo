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
        char fChar = f.getCharAtIndex(i-1);
        char gChar = g.getCharAtIndex(j-1);
        if (fChar == gChar)
            return MATCH;
        else
            return MISSMATCH;
    }

    public int getIndexMaxLastCol(){
        int max = matrix[0][height-1];
        int index = 0;
        for(int i = 1; i < width; i++){
            System.out.println(max + " / " + matrix[i][height-1]);
            if(matrix[i][height-1] > max) {
                max = matrix[i][height-1];
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
        for(int i = f.getSize()-1; i >= getIndexMaxLastCol(); i--){
            alignF.list.add(f.getCharAtIndex(i));
            alignG.list.add('-');
        }
    }

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
    public void generateAlignment(){

        startAlign();

        int i = getIndexMaxLastCol();
        int j = height-1;

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
        fillAlign(i);
    }
}
