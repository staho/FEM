package Model;

import Jama.Matrix;
import Model.Maths.IntegralPoints;
import Model.Maths.Point;

public class Element {

    private int [] IDArray = null;
    private Node [] nodes = null;
    private Surface [] surfaces = null;
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
        for(int i = 0; i < 4; i++){
            jacobian[i] = new Matrix(2,2);
        }
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
            jacobian[i].set(0,1, jacobian[i].get(0,1)*(-1.0));
            jacobian[i].set(1,0, jacobian[i].get(1,0)*(-1.0));

            jacobian[i] = jacobian[i].times(1.0/(jacobianDets[i]));

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

//        for (Node node: nodes){
//            System.out.println("ID: " + node.getUid() +  " x: " + node.getX() + " y:" + node.getY() + " temp:" + node.getTemp());
//        }
//        System.out.println();

        this.surfaces = new Surface[4];
        this.surfaces[0] = new Surface(nodes[3], nodes[0]);
        this.surfaces[1] = new Surface(nodes[0], nodes[1]);
        this.surfaces[2] = new Surface(nodes[1], nodes[2]);
        this.surfaces[3] = new Surface(nodes[2], nodes[3]);

        return this;
    }
}
