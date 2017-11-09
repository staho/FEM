package Controller;

import Model.GlobalData;
import Model.Grid;

public class Main {

    public static void main(String[] args) {
        GlobalData x = new GlobalData().readConfig();
        System.out.println("H:" + x.getH());
        System.out.println("B:" + x.getB());

        Grid grid = new Grid(x);

        grid.generateGrid();
    }
}
