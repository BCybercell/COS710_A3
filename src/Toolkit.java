import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Toolkit is used as a class to draw variables and subclasses across different classes
 */
public class Toolkit {
    // Create a toolkit using seed passed in
    Toolkit(long seed){

        rand = new Random(seed);
    }
    int getPopSize(){
        double r = rand.nextDouble();
        if (r <0.33){
            return popSize[0];
        }
        else if (r <0.67){
            return popSize[1];
        }
        else{
            return popSize[2];
        }
    }

    int getTourSize(){
        double r = rand.nextDouble();
        if (r <0.25){
            return tourSize[0];
        }
        else if (r <0.5){
            return tourSize[1];
        }
        else if (r <0.75){
            return tourSize[2];
        }
        else {
            return tourSize[3];
        }
    }

    String getRandomFunction(){
        double r = rand.nextDouble();
        if (r <0.125){
            return functionSet[0];
        }
        else if (r <0.25){
            return functionSet[1];
        }
        else if (r <0.375){
            return functionSet[2];
        }
        else if (r <0.5){
            return functionSet[3];
        }
        else if (r <0.625){
            return functionSet[4];
        }
        else if (r <0.75){
            return functionSet[5];
        }
        else if (r <0.875){
            return functionSet[6];
        }
        else {
            return functionSet[7];
        }
    }

    Integer getArity(String function){
        int count = 0;
        for (String s: functionSet
             ) {
            if (s.equals(function)){
                return arity[count];
            }
            count++;
        }
        return 0;
    }

    Integer getPos(String function){
        int count = 0;
        for (String s: functionSet
        ) {
            if (s.equals(function)){
                return count;
            }
            count++;
        }
        return -1;
    }

    void setTerminals(){

    }

    String getRandomTerminal(boolean isMain, int numFunctionTrees){ // TODO tune main being on/off
        double r = rand.nextDouble();
//        int len = terminalSetBase.length;
        int len = 3 + numFunctionTrees;
        if (!isMain){
            len = 3;
        }
        for (int i = 0; i < len; i++) {

            if (r <((1/ (double) len)*(i+1))){

                return terminalSetBase[i];
            }
        }
        return terminalSetBase[len-1];

    }

    //DONE
    List<String[]> readDataFile(String path){
        List<String[]> toReturn = new ArrayList<String[]>();
        try {
            String row;
            BufferedReader fileReader = new BufferedReader(new FileReader(path));

            while ((row = fileReader.readLine()) != null) {
                String[] data = row.split(",");
                int len = data.length;
                if (len > 1)
                    toReturn.add(data);

            }
            fileReader.close();
            return toReturn;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return toReturn;
        }

    }

    //DONE
    List<String[]> fixClassImbalance(List<String[]> oldData){
        /*
        * Makes sure that each class has at least 80 elements and no more than 100, fixes class imbalance
        *
        *
        *
        * */
        List<String[]> toReturn = new ArrayList<String[]>();
        Integer [] numberDecision = {0, 0, 0}; // I, S, A
        int count = 0;
        for (String [] el: oldData) {
            ++count;

            toReturn.add(el);

            switch (el[8]) {
                case "I" -> numberDecision[0] = numberDecision[0] + 1;
                case "S" -> numberDecision[1] = numberDecision[1] + 1;
                case "A" -> numberDecision[2] = numberDecision[2] + 1;
                default -> System.out.println(el[8] + " " + count);
            }
        }

        while (numberDecision[0] < 80 ||numberDecision[1] < 80 || numberDecision[2] < 80){

            int len = toReturn.size();
            int ran = rand.nextInt(len);
            String [] el = toReturn.get(ran);
            if (el[8].equals("I")  && numberDecision[0] < 100){
                numberDecision[0] = numberDecision[0]+1;
                toReturn.add(el);
            }
            if (el[8].equals("S") && numberDecision[1] < 100){
                numberDecision[1] = numberDecision[1]+1;
                toReturn.add(el);
            }
            if (el[8].equals("A") && numberDecision[2] < 100){
                numberDecision[2] = numberDecision[2]+1;
                toReturn.add(el);
            }
        }

        return toReturn;
    }

    //DONE
    List<List<String[]>> splitData(List<String []> data){
        List<String[]> train= new ArrayList<String[]>();
        List<String[]> test= new ArrayList<String[]>();
        for (String [] el:data
             ) {
            if (rand.nextDouble() > 0.15){ // 0.15 test to train ratio
                train.add(el);
            }
            else {
                test.add(el);
            }
        }

        List<List<String[]>> allData = new ArrayList<>();
        allData.add(train);
        allData.add(test);
        System.out.println("Train test ratio = "+allData.get(0).size()+ ":"+ allData.get(1).size());
        return allData;
    }

    int[] popSize = {50, 100, 150};
    int[] tourSize = {2, 4, 6, 8};
    List<List<String>> terminalSet;
    String[] functionSet = {"L-CORE", "L-SURF", "L-O2", "L-BP", "SURF-STBL", "CORE-STBL", "BP-STBL", "COMFORT"} ;
    Integer[] arity ={3, 3, 4, 3, 3, 3, 3, 21};
//    String[] terminalSetBase = {"I", "S", "A"};
    String[] terminalSetBase = {"I", "S", "A", "Func1", "Func2", "Func3", "Func4", "Func5"};
    Random rand;
}
