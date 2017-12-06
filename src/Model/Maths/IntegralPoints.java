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

}
