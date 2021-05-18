import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Rover {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
//        int map[][] =   {{1, 1, 1, 1, 1, 1, 1},
//                         {9, 9, 9, 9, 9, 9, 1},
//                         {1, 1, 1, 1, 1, 9, 1},
//                         {1, 1, 1, 9, 1, 9, 1},
//                         {1, 1, 1, 9, 1, 1, 1},
//                         {9, 9, 1, 9, 9, 9, 9},
//                         {1, 5, 1, 1, 1, 1, 9},
//                         {1, 5, 7, 3, 1, 9, 9},
//                         {1, 5, 7, 3, 1, 1, 1}};
        int map[][] =   {{1, 1, 2, 1, 2},
                          {9, 9, 9, 9, 1},
                          {1, 1, 1, 1, 1},
                         {1, 9, 9, 9, 9},
                         {1, 1, 1, 1, 1}};
        calculateRoverPath(map);
        System.out.println(System.currentTimeMillis()-time);
    }


        private static int[][] staticMap = null;
        private static int[][][] matrixForSolution;//.z(0)-потрачено топливо z(1) - шаги все остальное z- будет собирать путь(кординаты по котором шел)
        private static int maxInt = 99;
        private static int[][] shortPath = null;

        public static void calculateRoverPath(int[][] map) {
            staticMap=map;
            createMatrix();
            fillOneStepMatrix();
            fillMatrix();
            findShortPath();
            writeResult();
        }
        //Делаю таблицу и заплднгяю ее 0 и большими числами.
        private static void createMatrix(){
        matrixForSolution = new int[staticMap.length*staticMap[0].length][staticMap.length *staticMap[0].length][3];
        for(int y =0;y<matrixForSolution.length;y++){
            for(int x =0;x<matrixForSolution[0].length;x++){
                matrixForSolution[y][x][0] = maxInt;
            }

        }
    }
        //Создаю таблицу и делаю из каждой ячейки 1 шаг. И заполняю данные.
        private static void fillOneStepMatrix(){
        for(int i =0; i<staticMap.length*staticMap[0].length+1;i++) {
                    try {
                        matrixForSolution[i][1+i][0] = 1 + moduleNumber(staticMap[i/staticMap[0].length][i%staticMap[0].length] - staticMap[i/staticMap[0].length][i%staticMap[0].length+1]);
                        matrixForSolution[1+i][i][0] = matrixForSolution[i][1+i][0];//потрачено энергии

                        matrixForSolution[i][1+i][1] = 1;
                        matrixForSolution[1+i][i][1] = 1;//шаги

                    } catch (Exception e) { }
                    try {
                        matrixForSolution[i][i+staticMap[0].length][0] = 1 + moduleNumber(staticMap[i/staticMap[0].length][i%staticMap[0].length] - staticMap[i/staticMap[0].length+1][i%staticMap[0].length]);
                        matrixForSolution[i+staticMap[0].length][i][0] = matrixForSolution[i][i+staticMap[0].length][0];

                        matrixForSolution[i][i+staticMap[0].length][1] = 1;
                        matrixForSolution[i+staticMap[0].length][i][1] = 1;
                    } catch (Exception e) { }

        }

            for (int y = 0; y < matrixForSolution.length; y++)
            {
                for (int x = 0; x < matrixForSolution.length; x++)
                {
                    if (y == x) {
                        matrixForSolution[y][x][2] = 0;
                    }
                    else if (matrixForSolution[y][x][0] != maxInt) {
                        matrixForSolution[y][x][2] = y;
                    }
                }
            }

    }
        //узнаю самый дешевый путь
        private static void fillMatrix(){

        for(int i= 0;i<matrixForSolution.length;i++) {
            for (int y = 0; y < matrixForSolution.length; y++) {
                for (int x = 0; x < matrixForSolution.length; x++) {
                    if(matrixForSolution[y][i][0] + matrixForSolution[i][x][0] < matrixForSolution[y][x][0]) {
                        matrixForSolution[y][x][0] = matrixForSolution[y][i][0] + matrixForSolution[i][x][0];
                        matrixForSolution[y][x][1] = matrixForSolution[y][i][1] + matrixForSolution[i][x][1];

                        matrixForSolution[y][x][2] = matrixForSolution[i][x][2];
                        }
                    }
                }
            }



        }
        //Нахожу кратчайший путь и записывают в статик
        private static void findShortPath(){
        int[] list = new int[matrixForSolution[0][matrixForSolution.length-1][1]+1];// Список адресов не в ту стороны
            list[0] = matrixForSolution[1].length-1;

            for (int i = 1;i < matrixForSolution[1][matrixForSolution.length-1][1]+1;i++) {
                list[i] = matrixForSolution[1][list[i - 1]][2];
            }
            shortPath = new int [list.length][2];
            int k =0;
            for(int i=list.length-1;i>= 0;i--){
                shortPath[i][0] = (list[k])/staticMap[0].length;
                shortPath[i][1] = (list[k])%staticMap[0].length;
                k++;
            }

        }
        private static int moduleNumber (int i){
        if(i<0){
            return -i;
        }
        return i;
    }
        private static void writeResult(){
        StringBuilder result = new StringBuilder("");

        result.append("[").append(shortPath[0][0]).append("][").append(shortPath[0][1]).append("]");
        for (int i = 1;i<shortPath.length; i++){
            result.append("->[").append(shortPath[i][0]).append("][").append(shortPath[i][1]).append("]");
        }//Координаты пути

        result.append("\nsteps: ").append(matrixForSolution[0][matrixForSolution.length-1][1])
                .append("\nfuel: ").append(matrixForSolution[0][matrixForSolution.length-1][0]);

        try(FileWriter writer = new FileWriter("path-plan.txt")) {
            writer.write(String.valueOf(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
