import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class main {
    public static void main(String[] args) throws FileNotFoundException {

        long seed = System.currentTimeMillis();
        System.out.println("[*] Seed: " + seed);
        Toolkit globalTk = new Toolkit(seed);
/*
        //long seed = 1598863542640L; // test seed
        String[] Filenames = {"Results -3573351038917656240", "Results -4449638075418798789",
                "Results -8178107658683298112", "Results 1099457484034633866",
                "Results 5379349931881047926","Results 5991784342999417928"
                ,"Results 7343749473141872078"};
        // USED FOR CSV FORMATTING
        try {
            for (String filename:Filenames
                 ) {
                BufferedReader csvReader = new BufferedReader(new FileReader("Results/Parameter tuning/Parameter tuning run 4/"+filename + ".csv"));
                FileWriter csvWriter = new FileWriter("Results/Parameter tuning/Formatted "+filename+" Average Accuracy.csv");

                //Tuning: Gen,Raw Fitness,Adjusted Fitness,Normalized Fitness,Hits ratio,Accuracy
                //Gen,Raw Fitness,Adjusted Fitness,Normalized Fitness,Hits ratio,Best MSE,Average MSE
                String row = csvReader.readLine(); //First line. Headings

                String[] data = row.split(",");
                csvWriter.append(data[0]); //Gen
                csvWriter.append(";");
                csvWriter.append(data[5]); //Accuracy
                csvWriter.append("\n");

                row = csvReader.readLine(); //Second line. Extra data
                row = csvReader.readLine(); //Third to use

                data = row.split(",");

                csvWriter.append(data[0]); //Gen
                csvWriter.append(";");
                csvWriter.append(Double.toString(Double.parseDouble(data[5])*100)); //Accuracy
                csvWriter.append("\n");




                int count = 1;

                while ((row = csvReader.readLine()) != null) {
                    data = row.split(",");
                    if (data.length > 2 && !data[0].equals("Final")){ // last line, time...

                        if (count == 200 || data[0].equals("9999")){

                            count =0;

                            csvWriter.append(data[0]); //Gen
                            csvWriter.append(";");
                            csvWriter.append(Double.toString(Double.parseDouble(data[5])*100)); //Accuracy


                            csvWriter.append("\n");

                        }
                        count ++;

                    }
                }
                csvWriter.flush();
                csvWriter.close();
                csvReader.close();
            }

        }
        catch (Exception e){
            System.out.println(e.toString());
        }
/**/

        int n = 25;
        for (int i=0; i<n; i++)
        {

            long lSeed = globalTk.rand.nextLong();
            Toolkit localTk = new Toolkit(lSeed);
            System.out.println("[*] Seed "+i+": " + lSeed);
            GeneticProgram gp = new GeneticProgram(localTk, lSeed);
            gp.start();
        }

    }

}
