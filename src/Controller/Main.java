package Controller;

import Model.GlobalData;
import Model.Grid;
import Model.Maths.Equation;
import Model.Maths.IntegralPoints;

public class Main {

    public static void main(String[] args) {
        GlobalData x = new GlobalData(true);
        System.out.println("H:" + x.getH());
        System.out.println("B:" + x.getB());

        Grid grid = new Grid(x);

        grid.generateGrid();


    }
}
