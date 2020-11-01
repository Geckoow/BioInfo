package main;

public class Main {
    public static void main(String[] args){
        Fragment ff = new Fragment("CAGCACTTGGATTCTCGG");
        Fragment f = new Fragment("CAGCGTGG");

        SemiGlobAlignment alignment = new SemiGlobAlignment(ff, f);
        int a = alignment.getIndexMaxLastCol();
        System.out.println(a);
        alignment.generateAlignment();
        for (int i = 0; i < alignment.matrix.length; i++){
            for(int j = 0; j < alignment.matrix[0].length; j++) {
                System.out.print(alignment.matrix[i][j]);
            }
            System.out.print("\n");
        }
        for(int i = 0; i < alignment.alignF.getSize(); i++){
            System.out.print(alignment.alignF.getCharAtIndex(i));
        }
        System.out.println("\n");
        for(int i = 0; i < alignment.alignF.getSize(); i++){
            System.out.print(alignment.alignG.getCharAtIndex(i));
        }
    }
}
