package life;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int lifeCycle = 1;
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        String[] inputStrArr = inputStr.split(" ");
        int dim = Integer.parseInt(inputStrArr[0]);
        long seed = Long.parseLong(inputStrArr[1]);
        int steps = Integer.parseInt(inputStrArr[2]);
        char[][] matrix = new char[dim][dim];
        char[][] matrix2 = new char[dim][dim];
        fillMatrix(matrix, seed, dim);
        printMatrix(matrix);
        System.out.println("-----------------------");

        while (lifeCycle < steps + 1) {
            matrix2 = null;
            matrix2 = calculateWorld(matrix);
            printMatrix(matrix2);
            matrix = null;
            matrix = cloneArray(matrix2);

            System.out.println("-----------------------------");
            lifeCycle++;

        }




    }

    public static int countNeighbors(char[][] lifeU2, int m, int n){
        int count = 0;
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 2; j++){
                if ((lifeU2[m + i - 1][n + j - 1] == 'O') && !(i == 1 && j == 1)) count++;
            }
        }
        return count;
    }

    public static char[][] calculateWorld(char[][] lifeU){
        int dim = lifeU.length;
        char[][] largeWorld = new char[dim + 2][dim + 2];
        char[][] newWorld = new char[dim][dim];
        largeWorld = enlargeWorld(lifeU);
        for (int i = 1; i < dim - 1; i++ ){
            for (int j = 1; j < dim - 1; j++){
                if ((largeWorld[i][j] == ' ') && (countNeighbors(largeWorld, i, j) == 3)) largeWorld[i][j] = 'O';
                if ((largeWorld[i][j] == 'O') && ((countNeighbors(largeWorld, i, j) < 2) ||(countNeighbors(largeWorld, i, j) > 3))){
                    largeWorld[i][j] = ' ';
                }
            }
        }
        newWorld = stripWorld(largeWorld);
        return newWorld;
    }

    public static char[][] enlargeWorld(char[][] lifeU){
        int dim2 = lifeU.length + 2;
        char[][] lifeU2 = new char[dim2][dim2];
        for (int i = 1; i < dim2 - 1; i++){
            for (int j = 1; j < dim2 - 1; j++){
                lifeU2[i][j] = lifeU[i-1][j-1];
            }
        }
        for (int i = 0; i < dim2; i++){
            if (i == 0 || i == dim2 -1) {
                lifeU2[0][i] = ' ';
                lifeU2[dim2 - 1][i] = ' ';
                lifeU2[i][0] = ' ';
                lifeU2[dim2 - 1][0] = ' ';
            }
            else {
                lifeU2[0][i] = lifeU[dim2 - 3][i - 1];
                lifeU2[dim2 - 1][i] = lifeU[0][i - 1];
                lifeU2[i][0] = lifeU[i - 1][dim2 -3];
                lifeU2[i][dim2 - 1] = lifeU[i - 1][0];
            }
        }
        return lifeU2;
    }

    public static char[][] stripWorld(char[][] lifeU){
        int dim = lifeU.length;
        int dim2 = dim - 2;
        char[][] smallWorld = new char[dim2][dim2];
        for (int i = 1; i < dim - 1; i++){
            for (int j = 1; j < dim - 1; j++){
                smallWorld[i - 1][j - 1] = lifeU[i][j];
            }
        }
        return smallWorld;
    }

    public static void fillMatrix(char[][] lifeU, long seed, int dim){
        Random random = new Random(seed);
        boolean life = false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                life = random.nextBoolean();
                if (life) lifeU[i][j] = 'O';
                else lifeU[i][j] = ' ';
            }
        }
    }

    public static char[][] cloneArray(char[][] src) {
        int length = src.length;
        char[][] target = new char[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public static void printMatrix(char[][] mat) {
        int dim = mat.length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (j == dim - 1) {
                    System.out.printf("%s%n", mat[i][j]);
                } else {
                    System.out.printf("%s", mat[i][j]);
                }
            }
        }
    }

}
