import java.io.IOException;
import java.util.Arrays;

public class Reverse {
    public static void main (String[] args) {
        NewScanner in = new NewScanner(System.in);
        String[][] matrix = new String[1][1];
        int pointx;
        int pointy = 0;
        int sizex = 0;
        String a;
        String ls = Character.toString(System.lineSeparator().charAt(0));
        try {
            while (in.hasNext()) {
                a = in.next();
                pointx = 0;
                matrix[pointy] = new String[1];
                while (!a.equals(ls)) {
                    if (matrix[pointy].length == pointx) {
                        matrix[pointy] = Arrays.copyOf(matrix[pointy], matrix[pointy].length * 2);
                    }
                    matrix[pointy][pointx] = a;
                    pointx += 1;
                    a = in.next();
                }
                if (matrix[pointy] != null) {
                    matrix[pointy] = Arrays.copyOf(matrix[pointy], pointx);
                }
                pointy += 1;
                if (pointx > sizex) {
                    sizex = pointx;
                }
                if (matrix.length == pointy) {
                    matrix = Arrays.copyOf(matrix, matrix.length * 2);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        matrix = Arrays.copyOf(matrix, pointy);
/*                                                  transpose
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

 */
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int i1 = matrix[i].length - 1; i1 >= 0; i1--) {
                System.out.print(matrix[i][i1] + " ");
            }
            System.out.println();
        }
        in.close();
    }
}
