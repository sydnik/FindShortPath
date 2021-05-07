public class Rover {

    private static int[][] staticMap = null;
    private static int maxStep = 0;
    private static int[][] allAddress;
    public static void calculateRoverPath(int[][] map) {
        staticMap = map;
        maxStep = (map[0].length*map.length) - 1;
        allAddress = new int[maxStep+1][2];
        int[] first;
        int[] second;
        first = reflectionFindPath(map[0][1],1, new int[]{0,1},new int[]{0,0});
        second = reflectionFindPath(map[1][0],1, new int[]{1,0},new int[]{0,0});
        for (int i=0;i<first.length;i++){
            if(i%2==0){
                System.out.print(first[i]);
            }
            else if(i%2==1){
                System.out.println(first[i]);
            }
        }



        if(first[0]<second[0]){

        }
    }

    //на данный момент возвращает сколько топлива потрачено
    //главное не запутаться)  и чтобы хватило оперативки
    //первое число количество топлива, второй количество шагов, и далее список адресов
    private static int[] reflectionFindPath(int height,int step,int [] address,int [] startAddress){
        if(step>maxStep){
            return new int[]{Integer.MAX_VALUE};
        }
        if(address[0]==staticMap.length-1&&address[1]==staticMap[0].length-1){
            System.out.println(step+" Шаг");
            int [] returnInt = new int[2+((step+3)*2)];
            returnInt[1] = step;
            returnInt [0] = Math.abs(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]])+step;
            returnInt[(1+step)*2+1]=address[1];
            returnInt[(1+step)*2]=address[0];
            return returnInt;
        }

        int stepNew = step;
        int[] first = new int[1];
        int[] second = new int[1];
        int[] third = new int[1];
        int newAddress[][] = new int[3][2];
        int check = 0;
        if(address[0]+1!=startAddress[0]||address[1]!=startAddress[1]){
            newAddress[check][0] = address[0]+1;
            newAddress[check][1] = address[1];
            check++;
        }

        else if(address[0]!=startAddress[0]||address[1]-1!=startAddress[1]){
            newAddress[check][0] = address[0];
            newAddress[check][1] = address[1]-1;
            check++;
        }
        else if(address[0]+1!=startAddress[0]||address[1]+1!=startAddress[1]){
            newAddress[check][0] = address[0];
            newAddress[check][1] = address[1]+1;
            check++;
        }
        else {
            newAddress[check][0] = address[0] - 1;
            newAddress[check][1] = address[1];
        }

        try {
            first = reflectionFindPath(staticMap[startAddress[0]][startAddress[1]],stepNew+1, new int[]{newAddress[0][0],newAddress [0][1]},address);
        }catch (Exception e){ first[0] = Integer.MAX_VALUE;
        }
        try {
            second = reflectionFindPath(staticMap[startAddress[0]][startAddress[1]],stepNew+1, new int[]{newAddress[1][0],newAddress [1][1]},address);
        }catch (Exception e){second[0] = Integer.MAX_VALUE;
        }
        try {
            third = reflectionFindPath(staticMap[startAddress[0]][startAddress[1]],stepNew+1, new int[]{newAddress[2][0],newAddress [2][1]},address);
        }catch (Exception e){third[0] = Integer.MAX_VALUE;
        }

        if(first.length!=1){
            first[2+(step)*2+1]=address[1];
            first[2+(step)*2]=address[0];
            first[0] = first[0] + Math.abs(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]]);
        }
        if(second.length!=1){
            second[2+(step)*2+1]=address[1];
            second[2+(step)*2]=address[0];
            second[0] = second[0] + Math.abs(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]]);
        }
        if(third.length!=1){
            third[2+(step)*2+1]=address[1];
            third[2+(step)*2]=address[0];
            third[0] = third[0] + Math.abs(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]]);
        }

        if(first[0]==second[0]&&second[0]==third[0]){
            return first;
        }
        else if(first[0]<second[0]&&first[0]<third[0]){
            return first;
        }
        else if(second[0]<first[0]&&second[0]<third[0]){
            return second;
        }
        else if(third[0]<second[0]&&third[0]<first[0]){
            return third;
        }
        else if(first[0]<second[0]||first[0]<third[0]){
            return first;
        }
        else if(second[0]<third[0]||second[0]<first[0]){
            return second;
        }
        return third;
    }



}
