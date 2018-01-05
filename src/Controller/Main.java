package Controller;

import Model.GlobalData;
import Model.Maths.Solver;

public class Main {

    public static void main(String[] args) {
        GlobalData globalData = new GlobalData(true);

        double[] t;
        for (int tempTau = 0; tempTau <= globalData.getTau(); tempTau += globalData.getdTau()) {
            globalData.process();
            t = Solver.gaussElimination(globalData.getNh(), globalData.gethGlobal().getArray(), globalData.getpGlobal());
            globalData.getGrid().setTemps(t);
            globalData.getGrid().printNodesTemps();
        }

    }
}
