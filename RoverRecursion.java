import java.io.FileWriter;
import java.io.IOException;

public class RoverRecursion {
    private static int[][] staticMap = null;
    private static int maxStep = 0;

    public static void calculateRoverPath(int[][] map) {
        staticMap = map;
        maxStep = (map[0].length*map.length) - 1;
        int[] first;
        int[] second;
        int[][] checkList = new int[maxStep-1][2];
        StringBuilder result = new StringBuilder("");
        first = reflectionFindPath(map[0][1],1, new int[]{0,1},new int[]{0,0},checkList);
        second = reflectionFindPath(map[1][0],1, new int[]{1,0},new int[]{0,0},checkList);

        if(first[0]<second[0]){
            result.append("[").append(first[2]).append("][").append(first[3]).append("]");
            for (int i = 4;i<first.length;i = i+2){
                result.append("->[").append(first[i]).append("][").append(first[i+1]).append("]");
            }
            result.append("\nsteps: ").append(first[1])
                    .append("\nfuel: ").append(first[0]);
        }
        else {
            result.append("[").append(second[2]).append("][").append(second[3]).append("]");
            for (int i = 4;i<second.length;i = i+2){
                result.append("->[").append(second[i]).append("][").append(second[i+1]).append("]");
            }
            result.append("\nsteps: ").append(second[1])
                    .append("\nfuel: ").append(second[0]);

        }
        try(FileWriter writer = new FileWriter("path-plan.txt")) {
            writer.write(String.valueOf(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //первое число количество топлива, второй количество шагов, и далее список адресов
    private static int[] reflectionFindPath(int height,int step,int [] address,int [] startAddress,int[][] checklist){
        if(address[0]==staticMap.length-1&&address[1]==staticMap[0].length-1){
            int [] result = new int[2+((step+1)*2)];
            result[1] = step;
            result [0] = moduleNumber(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]])+step;
            result[(1+step)*2+1]=address[1];
            result[(1+step)*2]=address[0];
            return result;
        }
        //Проверки что путь не зашел на ячейку где  он уже был и не стоит ли он  рядом в клетке с путем которым он был
        for(int i=0;i<step;i++){
                if (address[0] == checklist[i][0] && address[1] == checklist[i][1]) {
                    return new int[]{Integer.MAX_VALUE};
                }
                try {
                    if (address[0] - 1 == checklist[i][0] && address[1] == checklist[i][1]&&startAddress[0]!=address[0] - 1&& startAddress[1]!=address[1]) {
                        return new int[]{Integer.MAX_VALUE};
                    }
                }catch (Exception e){}
            try {
                if (address[0] + 1 == checklist[i][0] && address[1] == checklist[i][1]&&startAddress[0]!=address[0] + 1&& startAddress[1]!=address[1]) {
                    return new int[]{Integer.MAX_VALUE};
                }
            }catch (Exception e){}
            try {
                if (address[0]  == checklist[i][0] && address[1]-1 == checklist[i][1]&&startAddress[0]!=address[0]&& startAddress[1]!=address[1]-1) {
                    return new int[]{Integer.MAX_VALUE};
                }
            }catch (Exception e){}
            try {
                if (address[0] == checklist[i][0] && address[1]+1 == checklist[i][1]&&startAddress[0]!=address[0]&& startAddress[1]!=address[1]+1) {
                    return new int[]{Integer.MAX_VALUE};
                }
            }catch (Exception e){}

        }


        int stepNew = step;
        int[] first = new int[1];
        int[] second = new int[1];
        int[] third = new int[1];
        int newAddress[][] = new int[3][2];
        int[][] newCheckList = copyList(checklist);

        newCheckList[step][0] = address[0];
        newCheckList[step][1] = address[1];
        int check = 0;
        if(address[0]+1!=startAddress[0]||address[1]!=startAddress[1]){
            newAddress[check][0] = address[0]+1;
            newAddress[check][1] = address[1];
            check++;
        }

        if(address[0]!=startAddress[0]||address[1]-1!=startAddress[1]){
            newAddress[check][0] = address[0];
            newAddress[check][1] = address[1]-1;
            check++;
        }
        if(address[0]!=startAddress[0]||address[1]+1!=startAddress[1]){
            newAddress[check][0] = address[0];
            newAddress[check][1] = address[1]+1;
            check++;
        }
        if (address[0]-1!=startAddress[0]||address[1]!=startAddress[1]){
            newAddress[check][0] = address[0] - 1;
            newAddress[check][1] = address[1];
        }

        try {
            first = reflectionFindPath(staticMap[startAddress[0]][startAddress[1]],stepNew+1, new int[]{newAddress[0][0],newAddress [0][1]},address,newCheckList);
        }catch (Exception e){ first[0] = Integer.MAX_VALUE;
        }
        try {
            second = reflectionFindPath(staticMap[startAddress[0]][startAddress[1]],stepNew+1, new int[]{newAddress[1][0],newAddress [1][1]},address,newCheckList);
        }catch (Exception e){second[0] = Integer.MAX_VALUE;
        }
        try {
            third = reflectionFindPath(staticMap[startAddress[0]][startAddress[1]],stepNew+1, new int[]{newAddress[2][0],newAddress [2][1]},address,newCheckList);
        }catch (Exception e){third[0] = Integer.MAX_VALUE;
        }

        if(first.length!=1){
            first[2+(step)*2+1]=address[1];
            first[2+(step)*2]=address[0];
            first[0] = first[0] + moduleNumber(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]]);
        }
        if(second.length!=1){
            second[2+(step)*2+1]=address[1];
            second[2+(step)*2]=address[0];
            second[0] = second[0] + moduleNumber(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]]);
        }
        if(third.length!=1){
            third[2+(step)*2+1]=address[1];
            third[2+(step)*2]=address[0];
            third[0] = third[0] + moduleNumber(staticMap[startAddress[0]][startAddress[1]]-staticMap[address[0]][address[1]]);
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
