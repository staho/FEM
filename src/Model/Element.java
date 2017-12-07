package Model;

import Jama.Matrix;
import Model.Maths.IntegralPoints;
import Model.Maths.Point;

public class Element {

    private int [] IDArray = null;
    private Node [] nodes = null;
    private double [] pow = null;
    private final int noOfIDs = 4;
    private int elementID = 0;

    private Matrix [] jacobian;
    private double [] jacobianDets;
    private Matrix shapeFunctionsDerEta;
    private Matrix shapeFunctionsDerPsi;



    public Element(Matrix shapeFunctionsDerEta, Matrix shapeFunctionsDerPsi) {
        IDArray = new int[this.noOfIDs];
        this.shapeFunctionsDerEta = shapeFunctionsDerEta;
        this.shapeFunctionsDerPsi = shapeFunctionsDerPsi;

        jacobian = new Matrix[4];
        jacobianDets = new double[4];

    }


    //XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
    public void calculateJacobians(){

        Point[] points= IntegralPoints.getIntegralPoints();

        for(int i = 0; i < 4; i++) {
            double dxDpsi = 0;
            double dxDeta = 0;
            double dyDpsi = 0;
            double dyDeta = 0;

            for(int j = 0; j < 4; j++) {
                dxDeta += shapeFunctionsDerEta.get(i, j) * nodes[j].getX();
                dxDpsi += shapeFunctionsDerPsi.get(i, j) * nodes[j].getX();
                dyDeta += shapeFunctionsDerEta.get(i, j) * nodes[j].getY();
                dyDpsi += shapeFunctionsDerPsi.get(i, j) * nodes[j].getY();
            }

            jacobian[i].set(0,0, dxDeta);
            jacobian[i].set(0,1, dyDeta);
            jacobian[i].set(1,0, dxDpsi);
            jacobian[i].set(0,1, dyDpsi);

            jacobianDets[i] = jacobian[i].det();
            jacobian[i].transpose();
        }
    }

    public int[] getIDArray() {
        return IDArray;
    }

    public void setIDArray(int[] IDArray) {
        this.IDArray = IDArray;
    }

    public void setIdOfIndex(int index, int id){
        IDArray[index] = id;
    }

    public Element withArray(int[] IDArray){
        this.IDArray = IDArray;
        return this;
    }

    public Element withNodes(Node [] nodes){
        this.nodes = nodes;

        for (Node node: nodes){
            System.out.println("ID: " + node.getUid() +  " x: " + node.getX() + " y:" + node.getY());
        }
        System.out.println();

        return this;
    }
}
