import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Rover {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
//        int map[][] =   {{1, 2, 1, 9, 9},
//                         {1, 4, 3, 9, 1},
//                         {3, 6, 1, 5, 6},
//                         {1, 5, 9, 2, 3},
//                         {1, 5, 7, 3, 1}};
        int map[][] =    {{3, 4, 5},
                        {7, 1, 4},
                         {2, 5, 6}};
        calculateRoverPath(map);
        System.out.println(System.currentTimeMillis()-time);
    }


    private static int[][] staticMap = null;
    private static int maxStep = 0;
    private static int[][][] matrixForSolution;
    private static int[][][] matrixCopy;
    private static int maxInt = Integer.MAX_VALUE/2;



        public static void calculateRoverPath(int[][] map) {
            staticMap=map;
            createMatrix(map);
            fillMatrix();





//            for (int k = 0; k < matrixForSolution.length; k++) {
//                for (int i = 0; i < matrixForSolution.length; i++)
//                    for (int j = 0; j < matrixForSolution.length; j++) {
//                        matrixForSolution[i][j][0] = min(matrixForSolution[i][j][0], matrixForSolution[i][k][0] + matrixForSolution[k][j][0]);
//                    }
//            }
            int numberOne,numberTwo;
                for(int y = 0;y<matrixForSolution.length;y++){
                    numberOne = matrixForSolution[0][y][0];
                    numberTwo = matrixForSolution[0][y][1];
                    for(int x =0;x<matrixForSolution.length;x++){
//                        matrixForSolution[y][x][0] = matrixForSolution
                    }
                }
                for(int i =0;i<matrixForSolution.length;i++){
                for(int j =0;j<matrixForSolution.length;j++){
                    System.out.print(matrixForSolution[i][j][0]+" ");
                }
                System.out.println();
            }
        }






    private static void createMatrix(int [][] map){
        matrixForSolution = new int[map.length*map[0].length+1][map.length *map[0].length+1][3];
        for(int y =1;y<matrixForSolution.length;y++){
            for(int x =1;x<matrixForSolution[0].length;x++){
                matrixForSolution[y][x][0] = maxInt;
            }

        }
        for(int y =1;y<map.length;y++){
            for(int x =1;x<map[0].length;x++){
                matrixForSolution[y][x][0] = maxInt;
            }

        }
        int y=0;
        int x = 0;
        for(int i = 1;i<matrixForSolution.length;i++){

            matrixForSolution[i][i][0] = 0;
            matrixForSolution[i][0][0] = i;
            matrixForSolution[0][i][0] = i;
            matrixForSolution[i][0][1] = y;
            matrixForSolution[0][i][1] = y;
            matrixForSolution[0][i][2] = x;
            matrixForSolution[i][0][2] = x;
            x++;
            if(i%map.length==0){
                y++;
                x =0;
            }
        }

    }
    private static void fillMatrix(){
            int i=2;
        for(int y = 0;y<staticMap.length;y++){
            for(int x = 0;x<staticMap[0].length;x++){
                try {
                    matrixForSolution[i][i-1][0] = 1+moduleNumber(staticMap[y][x]-staticMap[y][x-1]);
                    i++;
                }catch (Exception e){

                }
                try {
                    matrixForSolution[0][i][0] = 1+moduleNumber(staticMap[y][x]-staticMap[y-1][x]);
                    i++;
                }catch (Exception e){

                }
                try {
                    matrixForSolution[0][i][0] = 1+moduleNumber(staticMap[y][x]-staticMap[y+1][x]);
                    i++;
                }catch (Exception e){

                }
                try {
                    matrixForSolution[0][i+1][0] = 1+moduleNumber(staticMap[y][x]-staticMap[y][x+1]);
                    i++;
                }catch (Exception e){

                }
            }
        }
    }
    private static int min (int first,int second){
            if(first>second){
                return second;
            }
            return  first;
    }
    private static int moduleNumber (int i){
        if(i<0){
            return -i;
        }
        return i;
    }
    private static int[][] copyList (int[][] list){
        int[][] newChecklist = new int[maxStep-1][2];
        for(int i =0;i<maxStep-1;i++){
            newChecklist[i][0] = list[i][0];
            newChecklist[i][1] = list[i][1];
        }
        return newChecklist;
    }


}
