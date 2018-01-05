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
        GlobalData globalData = new GlobalData(true);

        double[] t;
        for (int tempTau = 0; tempTau < globalData.getTau(); tempTau += globalData.getdTau()) {
            globalData.compute();
            t = Solver.gaussElimination(globalData.getNh(), globalData.gethGlobal().getArray(), globalData.getpGlobal());
            globalData.getGrid().setTemps(t);
            globalData.getGrid().printNodesTemps();
        }

    }
}
