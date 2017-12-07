package Model.Maths;

import java.util.Vector;

public class IntegralPoints {
    private int dimmension;
    private int noOfPoints;

    private double [][] tab2p = null;
    private double [][] tab3p = null;


    public IntegralPoints() {

        tab2p = new double[2][2];
        tab2p[0][0] = -0.577;
        tab2p[0][1] = 1.;
        tab2p[1][0] = 0.577;
        tab2p[1][1] = 1.;


        tab3p = new double[3][2];
        tab3p[0][0] = -0.7745;
        tab3p[0][1] = 5/(double)9;
        tab3p[1][0] = 0.;
        tab3p[1][1] = 8/(double)9;
        tab3p[2][0] = 0.7745;
        tab3p[2][1] = 5/(double)9;

    }

    public double[][] getTab2p() {
        return tab2p;
    }

    public double[][] getTab3p() {
        return tab3p;
    }

    public static double[][] getIntegralPointsTab(){
        double [][] points = new double[][]{
                {0.577, 0.577},
                {-0.577, 0.577},
                {0.577, -0.577},
                {-0.577, -0.577}
        };
        return points;
    }

    public static Point[] getIntegralPoints(){
        Point [] points = new Point[4];

        points[0].setX(0.577); points[0].setY(0.577);
        points[1].setX(-0.577); points[1].setY(0.577);
        points[2].setX(0.577); points[2].setY(-0.577);
        points[3].setX(-0.577); points[3].setY(-0.577);

        return points;

    }

}
