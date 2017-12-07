package Controller;

import Jama.Matrix;
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

        Matrix matrix = Matrix.random(2,2);
        matrix.print(5, 2);


        Matrix matrix1 = matrix.transpose();
        matrix1.print(5, 2);
        System.out.println(matrix1.det());


    }
}
