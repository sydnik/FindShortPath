

public class Rover {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        int map[][] =   {{1, 2, 1, 9, 9},
                         {1, 4, 3, 9, 1},
                         {3, 6, 1, 5, 6},
                         {1, 5, 9, 2, 3},
                         {1, 5, 7, 3, 1}};
//        int map[][] =    {{1, 5, 9},
//                        {1, 5, 9},
//                         };
//        int map[][] =    {{ 4, 5},
//                         { 1, 4},
//                        { 5, 6}};
//
        calculateRoverPath(map);
        System.out.println(System.currentTimeMillis()-time);
    }


    private static int[][] staticMap = null;
    private static int maxStep = 0;
    private static int[][][] matrixForSolution;//.z(0)-потрачено топливо z(1) - шаги все остальное z- будет собирать путь(кординаты по котором шел)
    private static int maxInt = 99;



    public static void calculateRoverPath(int[][] map) {
            staticMap=map;
            createMatrix(map);
            fillOneStepMatrix();
            fillMatrix();



                for(int i =0;i<matrixForSolution.length;i++){
                    for(int j =0;j<matrixForSolution.length;j++){
                    System.out.print(matrixForSolution[i][j][0]+" ");
                }
                System.out.println();
            }
                for(int i=0;i<matrixForSolution[1][matrixForSolution.length-1].length;i+=2){
                    System.out.println(matrixForSolution[1][matrixForSolution.length-1][i]+" "+matrixForSolution[1][matrixForSolution.length-1][i+1]);
                }
        System.out.println(matrixForSolution[1][matrixForSolution.length-1][1]);
//
        }


        //Делаю таблицу и заплднгяю ее 0 и большими числами.
    private static void createMatrix(int [][] map){
        matrixForSolution = new int[map.length*map[0].length+1][map.length *map[0].length+1][staticMap.length*staticMap[0].length*2+2];
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
    //Создаю таблицу и делаю из каждой ячейки 1 шаг. И заполняю данные.
    private static void fillOneStepMatrix(){
        for(int i =0; i<staticMap.length*staticMap[0].length+1;i++) {
                    try {
                        matrixForSolution[1+i][1+i+1][0] = 1 + moduleNumber(staticMap[i/staticMap[0].length][i%staticMap[0].length] - staticMap[i/staticMap[0].length][i%staticMap[0].length+1]);
                        matrixForSolution[1+i+1][1+i][0] = matrixForSolution[1+i][1+i+1][0];//потрачено энергии

                        matrixForSolution[1+i][1+i+1][1] = 1;
                        matrixForSolution[1+i+1][1+i][1] = 1;//шаги

                        matrixForSolution[1+i][1+i+1][2] = i/staticMap.length;
                        matrixForSolution[1+i][1+i+1][3] = i%staticMap[0].length;
                        matrixForSolution[1+i][1+i+1][4] = i/staticMap.length;
                        matrixForSolution[1+i][1+i+1][5] = i%staticMap[0].length+1;//координаты

                        matrixForSolution[1+i][1+i+1][2] = i/staticMap.length;
                        matrixForSolution[1+i+1][1+i][3] = i%staticMap[0].length;
                        matrixForSolution[1+i+1][1+i][4] = i/staticMap.length;
                        matrixForSolution[1+i+1][1+i][5] = i%staticMap[0].length+1;//координаты

                    } catch (Exception e) { }
                    try {
                        matrixForSolution[i+1][i+1+staticMap[0].length][0] = 1 + moduleNumber(staticMap[i/staticMap[0].length][i%staticMap[0].length] - staticMap[i/staticMap[0].length+1][i%staticMap[0].length]);
                        matrixForSolution[i+1+staticMap[0].length][i+1][0] = matrixForSolution[i+1][i+1+staticMap[0].length][0];

                        matrixForSolution[i+1][i+1+staticMap[0].length][1] = 1;
                        matrixForSolution[i+1+staticMap[0].length][i+1][1] = 1;

                        matrixForSolution[i+1][i+1+staticMap[0].length][2] = i/staticMap.length;
                        matrixForSolution[i+1][i+1+staticMap[0].length][3] = i%staticMap[0].length;
                        matrixForSolution[i+1][i+1+staticMap[0].length][4] = i/staticMap.length+1;
                        matrixForSolution[i+1][i+1+staticMap[0].length][5] = i%staticMap[0].length;

                        matrixForSolution[i+1+staticMap[0].length][i+1][2] = i/staticMap.length;
                        matrixForSolution[i+1+staticMap[0].length][i+1][3] = i%staticMap[0].length;
                        matrixForSolution[i+1+staticMap[0].length][i+1][4] = i/staticMap.length+1;
                        matrixForSolution[i+1+staticMap[0].length][i+1][5] = i%staticMap[0].length;
                    } catch (Exception e) { }
        }



    }
    //узнаю самый дешевый путь
    private static void fillMatrix(){

        for(int i= 1;i<matrixForSolution.length;i++) {
            for (int y = 1; y < matrixForSolution.length; y++) {
                for (int x = 1; x < matrixForSolution.length; x++) {
                    int first =1;
                    if(matrixForSolution[y][x][0]!=min(matrixForSolution[y][x][0], matrixForSolution[y][i][0] + matrixForSolution[i][x][0])) {
                        matrixForSolution[y][x][0] = matrixForSolution[y][i][0] + matrixForSolution[i][x][0];
                        matrixForSolution[y][x][1] = matrixForSolution[y][i][1] + matrixForSolution[i][x][1];
                        for(first = 1;first<matrixForSolution[y][i][1]+2;first++){
                            matrixForSolution[y][x][first*2] = matrixForSolution[y][i][first*2];
                            matrixForSolution[y][x][first*2+1] = matrixForSolution[y][i][first*2+1];

                        }
                        for(int second =0; second<matrixForSolution[i][x][1];second++){
                            matrixForSolution[y][x][first+second*2] = matrixForSolution[i][x][second*2];
                            matrixForSolution[y][x][first+second*2+1] = matrixForSolution[i][x][second*2+1];
                        }

                    }

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


}
