package Controller;

import Model.GlobalData;
import Model.Grid;
import Model.Maths.Equation;
import Model.Maths.IntegralPoints;

public class Main {

    public static void main(String[] args) {
        GlobalData x = new GlobalData().readConfig();
        System.out.println("H:" + x.getH());
        System.out.println("B:" + x.getB());

        Grid grid = new Grid(x);

        grid.generateGrid();

        IntegralPoints points = new IntegralPoints();

        double equationSum = 0;

        double [][] tabofPoints = points.getTab2p();

        System.out.println("Maths 2D with 2 points:");
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 2; j++){
                equationSum+= Equation.function(tabofPoints[i][0], tabofPoints[j][0]) * tabofPoints[i][1] * tabofPoints[j][1];
            }
        }
        System.out.println("Sum of integral: " + equationSum);

        equationSum = 0;
        tabofPoints = points.getTab3p();
        System.out.println("Maths 2D with 3 points:");
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                equationSum+= Equation.function(tabofPoints[i][0], tabofPoints[j][0]) * tabofPoints[i][1] * tabofPoints[j][1];
            }
        }
        System.out.println("Sum of integral: " + equationSum);
    }
}
