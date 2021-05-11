import java.lang.reflect.Array;
import java.util.Arrays;

public class Rover {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        int map[][] =   {{1, 1, 1, 1, 1, 1, 1, 1},
                         {9, 9, 9, 9, 9, 9, 9, 1},
                         {1, 1, 1, 1, 1, 1, 1, 1},
                         {1, 9, 9, 9, 9, 2, 9, 9},
                         {1, 1, 1, 1, 1, 4, 9, 1},
                         {9, 9, 9, 9, 1, 2, 1, 9},
                         {1, 5, 7, 3, 1, 1, 1, 1},
                         {1, 5, 7, 3, 1, 1, 1, 1}};


        calculateRoverPath(map);
        System.out.println(System.currentTimeMillis()-time);
    }


        private static int[][] staticMap = null;
        private static int[][][] matrixForSolution;//.z(0)-потрачено топливо z(1) - шаги все остальное z- будет собирать путь(кординаты по котором шел)
        private static int maxInt = 9999999;
        private static int[][] shortPath = null;



        public static void calculateRoverPath(int[][] map) {
            staticMap=map;
            createMatrix();
            fillOneStepMatrix();
        for(int i =0;i<matrixForSolution.length;i++){
            for(int j =0;j<matrixForSolution.length;j++){
                System.out.print(matrixForSolution[i][j][0]+" ");
            }
            System.out.println();
        }
            fillMatrix();
            findShortPath();



                for(int i =0;i<matrixForSolution.length;i++){
                    for(int j =0;j<matrixForSolution.length;j++){
                    System.out.print(matrixForSolution[i][j][0]+" ");
                }
                System.out.println();
            }
                for(int i=0;i<matrixForSolution[1][matrixForSolution.length-1].length;i++){
                    System.out.println(matrixForSolution[1][matrixForSolution.length-1][i]);
                }
                for(int i = 0;i<shortPath.length;i++){
                    System.out.println(Arrays.toString(shortPath[i]));
                }
        System.out.println(matrixForSolution[matrixForSolution.length-1][1][1]);
//
        }


        //Делаю таблицу и заплднгяю ее 0 и большими числами.
        private static void createMatrix(){
        matrixForSolution = new int[staticMap.length*staticMap[0].length+1][staticMap.length *staticMap[0].length+1][3];
        for(int y =1;y<matrixForSolution.length;y++){
            for(int x =1;x<matrixForSolution[0].length;x++){
                matrixForSolution[y][x][0] = maxInt;
            }

        }
        for(int y =1;y<staticMap.length;y++){
            for(int x =1;x<staticMap[0].length;x++){
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
            if(i%staticMap.length==0){
                y++;
                x =0;
            }
        }

    }
        //Создаю таблицу и делаю из каждой ячейки 1 шаг. И заполняю данные.
        private static void fillOneStepMatrix(){
        for(int i =0; i<staticMap.length*staticMap[0].length+1;i++) {
                    try {
                        matrixForSolution[1+i][1+i+1][0] = 1 + moduleNumber(staticMap[i/staticMap[0].length][i%staticMap[0].length] - staticMap[i/staticMap[0].length][i%staticMap[0].length+1]);
                        matrixForSolution[1+i+1][1+i][0] = matrixForSolution[1+i][1+i+1][0];//потрачено энергии

                        matrixForSolution[1+i][1+i+1][1] = 1;
                        matrixForSolution[1+i+1][1+i][1] = 1;//шаги

                        matrixForSolution[1+i][1+i+1][2] = i+1;//координаты

                        matrixForSolution[1+i+1][1+i][2] = i+1;//координаты

                    } catch (Exception e) { }
                    try {
                        matrixForSolution[i+1][i+1+staticMap[0].length][0] = 1 + moduleNumber(staticMap[i/staticMap[0].length][i%staticMap[0].length] - staticMap[i/staticMap[0].length+1][i%staticMap[0].length]);
                        matrixForSolution[i+1+staticMap[0].length][i+1][0] = matrixForSolution[i+1][i+1+staticMap[0].length][0];

                        matrixForSolution[i+1][i+1+staticMap[0].length][1] = 1;
                        matrixForSolution[i+1+staticMap[0].length][i+1][1] = 1;

                        matrixForSolution[i+1][i+1+staticMap[0].length][2] = i+1;
                        matrixForSolution[i+1+staticMap[0].length][i+1][2] = 1+i+staticMap[0].length;

                    } catch (Exception e) { }
        }



    }
        //узнаю самый дешевый путь
        private static void fillMatrix(){

        for(int i= 1;i<matrixForSolution.length;i++) {
            for (int y = 1; y < matrixForSolution.length; y++) {
                for (int x = 1; x < matrixForSolution.length; x++) {
//                    if(x==i||y==i){
//                        continue;
//                    }
                    if(matrixForSolution[y][x][0]>min(matrixForSolution[y][x][0], matrixForSolution[y][i][0] + matrixForSolution[i][x][0])) {
                        matrixForSolution[y][x][0] = matrixForSolution[y][i][0] + matrixForSolution[i][x][0];
                        matrixForSolution[y][x][1] = matrixForSolution[y][i][1] + matrixForSolution[i][x][1];
//Может косяк в минимкм? Есть косяк 3 строчку ссылается не на те ячейки и пишет не правильный путь
                            matrixForSolution[y][x][2] = i;
                    }

                    }
                }
                if(i==21){
                    for(int b =0;b<matrixForSolution.length;b++){
                        for(int a =0;a<matrixForSolution.length;a++){
                            System.out.print(matrixForSolution[b][a][0]+" ");
                        }
                        System.out.println();
                    }
                }
            }



        }
        //Нахожу кратчайший путь и записывают в статик
        private static void findShortPath(){
        int[] list = new int[matrixForSolution[1][matrixForSolution.length-1][1]+1];// Список адресов не в ту стороны
            list[0] = matrixForSolution[1].length-1;

            for (int i = 1;i < matrixForSolution[1][matrixForSolution.length-1][1]+1;i++){
                list[i] = matrixForSolution[1][list[i-1]][2];
            }
            shortPath = new int [list.length][2];
            int k =0;
            for(int i=list.length-1;i>= 0;i--){
                shortPath[i][0] = (list[k]-1)/staticMap[0].length;//-1 делаю т.к. для себя добавлял номер в 0 строку/столбец
                shortPath[i][1] = (list[k]-1)%staticMap[0].length;
                k++;
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


}
