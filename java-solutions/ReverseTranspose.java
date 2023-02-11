import java.util.Arrays;
import java.util.Scanner;
public class ReverseTranspose {
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] matrix = new int[1][0];
        int pointx = 0;
        int pointy = 0;
        int sizex = 0;

        while (in.hasNextLine()) {
            Scanner line = new Scanner(in.nextLine());
            pointx = 0;
            if (line.hasNextInt()) {
                matrix[pointy] = new int[1];
            }
            while (line.hasNextInt()) {
                if (matrix[pointy].length == pointx) {
                    matrix[pointy] = Arrays.copyOf(matrix[pointy],matrix[pointy].length*2);
                }
                matrix[pointy][pointx] = line.nextInt();
                pointx+=1;
            }
            if (matrix[pointy]!=null) {
                matrix[pointy] = Arrays.copyOf(matrix[pointy], pointx);
            }

            pointy+=1;
            if (pointx > sizex) {
                sizex = pointx;
            }
            if (matrix.length == pointy) {
                matrix = Arrays.copyOf(matrix, matrix.length*2);
            }
        }
        for (int i = 0; i < sizex; i++) {
            for (int i1 = 0; i1 < matrix.length; i1++) {
                if (matrix[i1] != null) {
                    if (matrix[i1].length > i) {
                        System.out.print(matrix[i1][i] + " ");
                    }
                }
            }
            System.out.println("");
        }
       // System.out.println(1);
/*
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] != null) {
                for (int i1 = 0; i1 < matrix[i].length; i1++) {
                    System.err.print(matrix[i][i1] + " ");
                }
                System.err.println("");
            } else {
                break;
            }
        }

 */


    }
}
