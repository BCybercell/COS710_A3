import java.awt.geom.Arc2D;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticProgram extends Thread{
    GeneticProgram(Toolkit toolkit, long _seed){
        //System.out.println("GP created.");
        seed = _seed;
        tk = toolkit;
        population=new ArrayList<Tree>();
//        tournamentSize = tk.getTourSize();
        tournamentSize = 6; // Carried over from A2
//        populationSize = tk.getPopSize();
        populationSize = 50; // Carried over from A2
    }
    void createInitialPopulation(double _ratio){
        // Param for ratio?
        // Param for max depth
        //System.out.println(populationSize);
        for (int i = 0; i < populationSize; i++) { // populationSize
            if (tk.rand.nextDouble() > _ratio){
                population.add(new Tree(1, initialTreeDepth, tk));

            }
            else {
                population.add(new Tree(0, initialTreeDepth, tk));
                //System.out.println("Final " + population.get(i).getTreeValue());
            }

        }

    }



    public void run(){
        try {
            long startTime = System.nanoTime();


            ratio = 0.00; // Carried over from A2
            maxTreeDepth = 15; // Carried over from A2
            initialTreeDepth = 6; // Carried over from A2
            createInitialPopulation(ratio);

            List<String[]> data = tk.readDataFile("post-operative.data");
            List<String[]> dataFixed = tk.fixClassImbalance(data);
            List<List<String[]>> allData = tk.splitData(dataFixed);
            List<String[]> train = allData.get(0);
            List<String[]> test = allData.get(1);
            FileWriter csvWriter = new FileWriter("Results " + seed + ".csv");

            csvWriter.append("Gen"); // Gen
            csvWriter.append(",");
            csvWriter.append("Raw Fitness");
            csvWriter.append(",");
            csvWriter.append("Adjusted Fitness");
            csvWriter.append(",");
            csvWriter.append("Normalized Fitness");
            csvWriter.append(",");
            csvWriter.append("Hits ratio");
            csvWriter.append(",");
            csvWriter.append("Accuracy");
            csvWriter.append("\n");


            csvWriter.append(Integer.toString(tournamentSize));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(populationSize));
            csvWriter.append(",");
            csvWriter.append(Double.toString(ratio));
            csvWriter.append("\n");
            double tmpAccForPrint = 0.0;
            int numGen = 10000;
            for (int i = 0; i < numGen; i++) {

                if (i % 200 == 0 && i!=0) { // Removes redundant 0 case
                    System.out.println("Gen " + i + " best accuracy: " + tmpAccForPrint);
                }
                double sumAdjFit = 0.0;

                for (Tree t : population
                ) {
                    int correct = 0;
                    double total = 0.0;
                    double rawFitness = 0.0;
                    for (String[] obj : train
                    ) {

                        String temp = t.getTreeValue(obj);
                        if (temp.equals(obj[8])) {
                            rawFitness += 1;

                            correct++;

                        }

                        total++;

                    }
                    double acc = correct / total;
                    rawFitness = total - correct;

                    rawFitness += t.getNumNodes(); // adds this to influence tree depth/ number on nodes
                    t.rawFitness = rawFitness;
                    t.standardizedFitness = rawFitness;
                    double adjustedFitness = 1 / (rawFitness + 1);
                    t.adjustedFitness = adjustedFitness;
                    sumAdjFit += adjustedFitness;
                    t.hitsRatio = correct;
                    t.accuracy = acc;
                    if (correct >= (total - (total * 0.20))) { // TODO check in the next 100 runs (Tune between 80,85,90,95)
                        System.out.println("Early stop");
                        numGen = -1;
                        break;
                    }
                }
                for (Tree t : population
                ) {
                    t.normalizedFitness = t.adjustedFitness / sumAdjFit;
                }
                Tree fittest = getFittest();
                if (fittest != null) {
                    csvWriter.append(Integer.toString(i)); // Gen
                    csvWriter.append(",");
                    csvWriter.append(Double.toString(fittest.rawFitness));
                    csvWriter.append(",");
                    csvWriter.append(Double.toString(fittest.adjustedFitness));
                    csvWriter.append(",");
                    csvWriter.append(Double.toString(fittest.normalizedFitness));
                    csvWriter.append(",");
                    csvWriter.append(Double.toString(fittest.hitsRatio));

                    csvWriter.append(",");
                    csvWriter.append(Double.toString(fittest.accuracy));
                    csvWriter.append("\n");

                    //System.out.println("Gen " + i);
                    //System.out.println("Fittest Hits Ratio: " + fittest.hitsRatio);
//                    System.out.println("Fittest Raw Fitness: " + fittest.rawFitness);
//                    System.out.println("Fittest Adjusted Fitness: " + fittest.adjustedFitness);
//                    System.out.println("Fittest Normalized Fitness: " + fittest.normalizedFitness);
//
//                    System.out.println("Fittest Hits Ratio " + fittest.hitsRatio);
                    tmpAccForPrint = fittest.accuracy;
                } else {
                    System.out.println("Error in fittest");
                }

                for (int j = 0; j < populationSize * 0.5; j++) {

                    double rand = tk.rand.nextDouble();
                    if (rand < 0.1) { // 10% range
                        creation();
                    } else if (rand < 0.45) { // 35% range
                        mutation();
                    } else if (rand < 0.7) { // 35% range
                        crossover();
                    } else if (rand < 0.9) { //20% range
                        reproduction();
                    }


                }
                //System.out.println("Gen " + i);
                //System.out.println("Fittest Hits Ratio: " + fittest.hitsRatio);
//                    System.out.println("Fittest Raw Fitness: " + fittest.rawFitness);
//                    System.out.println("Fittest Adjusted Fitness: " + fittest.adjustedFitness);
//                    System.out.println("Fittest Normalized Fitness: " + fittest.normalizedFitness);
//
//                    System.out.println("Fittest Hits Ratio " + fittest.hitsRatio);
                tmpAccForPrint = fittest.accuracy;
            }




            double sumAdjFit = 0.0;

            for (Tree t : population
            ) {
                int correct = 0;
                double total = 0.0;
                double rawFitness = 0.0;
                for (String[] obj : test
                ) {

                    String temp = t.getTreeValue(obj);
                    if (temp.equals(obj[8])) {
                        rawFitness += 1;

                        correct++;

                    }

                    total++;

                }
                double acc = correct / total;
                rawFitness = total - correct;

//                    if (Double.isNaN(rawFitness) || rawFitness > 1.0E15){
//                        rawFitness = 1.0E15;
//                    }
                rawFitness += t.getNumNodes(); // adds this to influence tree depth/ number on nodes
                t.rawFitness = rawFitness;
                t.standardizedFitness = rawFitness;
                double adjustedFitness = 1 / (rawFitness + 1);
                t.adjustedFitness = adjustedFitness;
                sumAdjFit += adjustedFitness;
                t.hitsRatio = correct;
                t.accuracy = acc;
                if (correct >= (total - (total * 0.15))) { // This is redundant code. From original parameter tuning
                    System.out.println("Early stop");
                    break;
                }
            }
            for (Tree t : population
            ) {
                t.normalizedFitness = t.adjustedFitness / sumAdjFit;
            }
            Tree fittest = getFittest();
            if (fittest != null) {
                csvWriter.append("Final"); // Gen
                csvWriter.append(",");
                csvWriter.append(Double.toString(fittest.rawFitness));
                csvWriter.append(",");
                csvWriter.append(Double.toString(fittest.adjustedFitness));
                csvWriter.append(",");
                csvWriter.append(Double.toString(fittest.normalizedFitness));
                csvWriter.append(",");
                csvWriter.append(Double.toString(fittest.hitsRatio));

                csvWriter.append(",");
                csvWriter.append(Double.toString(fittest.accuracy));
                csvWriter.append("\n");
                long duration = System.nanoTime() - startTime;
                csvWriter.append(Long.toString(duration));
                csvWriter.append("\n");

                csvWriter.flush();
                csvWriter.close();
                System.out.println("[+] GP thread complete");


            } else {
                System.out.println("Error in fittest");
            }
        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught in run");
            System.out.println(e.toString());
        }
    }

    Tree tournamentSelection(){
        List<Tree> tour = new ArrayList<Tree>();
        double highestNorFitness = -1.0;
        int highest = -1;
        for (int i = 0; i <tournamentSize ; i++) {
            int rand = tk.rand.nextInt(populationSize-1);
            if (rand < population.size()){
                Tree randTree = population.get(rand);
                tour.add(randTree);
                if (randTree.normalizedFitness > highestNorFitness){
                    highestNorFitness = randTree.normalizedFitness;
                    highest = i;
                }
            }
            else {
                System.out.println("Error in Tree Tour Selection"+ rand);
            }

        }
        if (highest != -1){
            return tour.get(highest);
        }
        else {
            System.out.println("Error in Tree Tour Selection");
            return null;
        }


    }

    int tournamentSelectionReplace(){
        //List<Tree> tour = new ArrayList<Tree>();
        double lowestNorFitness = Double.MAX_VALUE;
        int lowest = Integer.MAX_VALUE;
        for (int i = 0; i <tournamentSize ; i++) {
            int rand = tk.rand.nextInt(populationSize-1);
            if (rand < population.size()){
                Tree randTree = population.get(rand);
                //tour.add(randTree);
                if (randTree.normalizedFitness < lowestNorFitness){
                    lowestNorFitness = randTree.normalizedFitness;
                    lowest = rand;
                }
            }
            else {
                System.out.println("Err" + rand);
            }

        }
        if (lowest != Integer.MAX_VALUE){
            return lowest;
        }
        else {
            System.out.println("Error in Tournament Selection");
            return -1;
        }


    }

    Tree getFittest(){
        double highestNorFitness = -1.0;
        int highest = -1;
        for (int i = 0; i <populationSize ; i++) {

                Tree randTree = population.get(i);
                if (randTree.normalizedFitness > highestNorFitness){
                    highestNorFitness = randTree.normalizedFitness;
                    highest = i;
                }
            }
        if (highest != -1){
            return population.get(highest);
        }
        else {
            System.out.println("Error in get Fittest");
            return null;
        }
    }



    void reproduction(){
        Tree toReproduce = tournamentSelection();
        int toReplace = tournamentSelectionReplace();
        Tree clone = toReproduce.clone();
        population.set(toReplace,clone);

    }
    void crossover(){

        Tree toReproduce1 = tournamentSelection();
        int numNodes = toReproduce1.getNumNodes();
        int rand1 = tk.rand.nextInt(numNodes);
        Node subtree1 = toReproduce1.getSubtree(rand1);

        Tree toReproduce2 = tournamentSelection();
        numNodes = toReproduce2.getNumNodes();
        int rand2 = tk.rand.nextInt(numNodes);
        Node subtree2 = toReproduce2.getSubtree(rand2);

        Tree clone1 = toReproduce1.clone();
        clone1.setSubtree(rand1, subtree2);

        Tree clone2 = toReproduce1.clone();
        clone2.setSubtree(rand2, subtree1);

        int toReplace = tournamentSelectionReplace();
        if (clone1.getDepth() < maxTreeDepth){
            population.set(toReplace,clone1);
        }


        toReplace = tournamentSelectionReplace();
        if (clone2.getDepth() < maxTreeDepth){
            population.set(toReplace,clone2);
        }

    }
    void mutation(){

        Tree toMutate = tournamentSelection();
        int numNodes = toMutate.getNumNodes();
        int rand = tk.rand.nextInt(numNodes);
        Tree newSubTree = new Tree(1, initialTreeDepth, tk);
        Tree clone = toMutate.clone();
        clone.setSubtree(rand, newSubTree.root);
        int toReplace = tournamentSelectionReplace();
        //System.out.println(clone.getNumNodes()+" "+toMutate.getNumNodes());
        if (clone.getDepth() < maxTreeDepth){
            population.set(toReplace,clone);
        }
    }
    void creation(){

        int toReplace = tournamentSelectionReplace();


        if (tk.rand.nextDouble() > ratio){
            population.set(toReplace, new Tree(1, initialTreeDepth, tk));

        }
        else {
            population.set(toReplace, new Tree(0, initialTreeDepth, tk));
            //System.out.println("Final " + population.get(i).getTreeValue());
        }
    }

    Toolkit tk;
    int tournamentSize;
    int populationSize;
    int initialTreeDepth;
    int maxTreeDepth;
    long seed;
    double ratio;
    List<Tree> population;

}
