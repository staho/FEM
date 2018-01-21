package Controller;

import Model.GlobalData;
import Model.Maths.Solver;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        GlobalData globalData = new GlobalData(true);

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("./test1.csv"));

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            double[] t;
            for (int tempTau = 0; tempTau <= globalData.getTau(); tempTau += globalData.getdTau()) {
                long startTime = System.currentTimeMillis();

                globalData.process();
                t = Solver.gaussElimination(globalData.getNh(), globalData.gethGlobal().getArray(), globalData.getpGlobal());
                globalData.getGrid().setTemps(t);
                saveArray(csvPrinter, globalData.getGrid().tempsAsArray());

                long endTime = System.currentTimeMillis();

                System.out.println("That took " + (endTime - startTime) + " milliseconds");

            }
            csvPrinter.flush();
        } catch (IOException e){
            System.out.println("FILE ERROR");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("EXCEPTION");
            e.printStackTrace();
        }


    }

    private static void saveArray(CSVPrinter csvPrinter, double [][] array) throws IOException{
        String rowString = "";
        for (double [] row: array) {

            rowString = Arrays.toString(row);

            rowString = rowString.replace("[","").replace("]","");
            csvPrinter.printRecord(rowString);
            //System.out.println(rowString);
        }
        csvPrinter.printRecord("\n");

    }
}
