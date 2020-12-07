package main;

public class Main {
    public static void main(String[] args) {
        Fragment f = new Fragment("cagcacttggattctcgg");
        Fragment g = new Fragment("cagcgtgg");
        SemiGlobAlignment alignment = new SemiGlobAlignment(f, g);

        for(int i = 0; i < alignment.matrix.length; i++){
            for(int j = 0; j < alignment.matrix[0].length; j++){
                if(alignment.matrix[i][j] >= 0)
                    System.out.print("+" + alignment.matrix[i][j]);
                else
                    System.out.print(alignment.matrix[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println("\n\n\n");
        System.out.println(f.getComplementary());
        SemiGlobAlignment s2 = new SemiGlobAlignment(f, g.getComplementary());
        for(int i = 0; i < s2.matrix.length; i++){
            for(int j = 0; j < s2.matrix[0].length; j++){
                if(s2.matrix[i][j] >= 0)
                    System.out.print("+" + s2.matrix[i][j]);
                else
                    System.out.print(s2.matrix[i][j]);
            }
            System.out.print("\n");
        }
    }
}
