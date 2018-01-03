package Controller;

import Jama.Matrix;
import Model.GlobalData;
import Model.Grid;
import Model.Maths.Equation;
import Model.Maths.IntegralPoints;
import Model.Maths.Solver;
import Model.Node;
import Jama.Matrix;

public class Main {

    public static void main(String[] args) {
        GlobalData x = new GlobalData(true);
//        x.compute();
//        x.gethGlobal().print(10, 15);
//        for(int i = 0; i < tab.length; i++){
//            for(int j = 0; j < tab[i].length; j ++){
//                System.out.printf("%.15f\t", tab[i][j]);
//            }
//            System.out.println("");
//        }

        double[] t;
        for (int itau = 0; itau < x.getTau(); itau += x.getdTau()){
            x.compute();
            t = Solver.gaussElimination(x.getNh(), x.gethGlobal().getArray(), x.getpGlobal());
            for(int i = 0; i < x.getnH(); i++){
                ((Node)(x.getGrid().getND().get(i))).setTemp(t[i]);
            }

            int count = 0;
            for(int i = 0; i < x.getnB(); i++){
                for(int j = 0; j < x.getnH(); j++){
                    double temp = ((Node)(x.getGrid().getND().get(count++))).getTemp();
                    System.out.printf("%.15f\t", temp);

                }
                System.out.println("");
            }
            System.out.println("\n\n");
        }

    }
}
