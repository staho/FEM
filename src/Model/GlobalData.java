package Model;

import Jama.Matrix;
import Model.Maths.IntegralPoints;
import Model.Maths.Point;
import com.google.gson.Gson;


import java.io.FileReader;


public class GlobalData {
    private double H;   //H is the height of field
    private double B;   //B is the width of field
    private double dy;
    private double dx;
    private int nH;     //number of nodes on height
    private int nB;     //number of nodes on width
    private int nh;     //number of nodes
    private int ne;     //number of elements

    private Matrix shapeFunctionsDerEta;
    private Matrix shapeFunctionsDerPsi;


    public GlobalData() {

    }

    public GlobalData(boolean x){
        GlobalData tempData = this.readConfig();
        if(tempData != null) {
            this.B = tempData.getB();
            this.H = tempData.getH();
            this.nH = tempData.getnH();
            this.nB = tempData.getnB();


            this.setNh(this.getnH() * this.getnB());
            this.setNe((this.getnB() - 1) * (this.getnH() - 1));
            this.setDx(this.getB() / (this.getnB() - 1));
            this.setDy(this.getH() / (this.getnH() - 1));

            generateDerMatrices();
        }
    }


    private void generateDerMatrices(){
        Point[] points = IntegralPoints.getIntegralPoints();

        shapeFunctionsDerEta = new Matrix(4, 4);
            for(int i = 0; i < 4; i++){
                shapeFunctionsDerEta.set(i,0, ShapeFunctions.shapeFunctionDerivative1Eta(points[i].getY()));
                shapeFunctionsDerEta.set(i,1, ShapeFunctions.shapeFunctionDerivative2Eta(points[i].getY()));
                shapeFunctionsDerEta.set(i,2, ShapeFunctions.shapeFunctionDerivative3Eta(points[i].getY()));
                shapeFunctionsDerEta.set(i,3, ShapeFunctions.shapeFunctionDerivative4Eta(points[i].getY()));
            }

        shapeFunctionsDerPsi = new Matrix(4, 4);
            for(int i = 0; i < 4; i++){
                shapeFunctionsDerPsi.set(i,0, ShapeFunctions.shapeFunctionDerivative1Psi(points[i].getX()));
                shapeFunctionsDerPsi.set(i,1, ShapeFunctions.shapeFunctionDerivative2Psi(points[i].getX()));
                shapeFunctionsDerPsi.set(i,2, ShapeFunctions.shapeFunctionDerivative3Psi(points[i].getX()));
                shapeFunctionsDerPsi.set(i,3, ShapeFunctions.shapeFunctionDerivative4Psi(points[i].getX()));
            }

    }

    private GlobalData readConfig(){
        try {
            Gson gson = new Gson();
            GlobalData tempData = gson.fromJson(new FileReader(System.getProperty("user.dir") + "/data/data.txt"), GlobalData.class);
            return tempData;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Matrix getShapeFunctionsDerEta() {
        return shapeFunctionsDerEta;
    }

    public Matrix getShapeFunctionsDerPsi() {
        return shapeFunctionsDerPsi;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnB() {
        return nB;
    }

    public void setnB(int nB) {
        this.nB = nB;
    }

    public int getNh() {
        return nh;
    }

    public void setNh(int nh) {
        this.nh = nh;
    }

    public int getNe() {
        return ne;
    }

    public void setNe(int ne) {
        this.ne = ne;
    }

    public double getDy() {
        return dy;
    }

    private void setDy(double dy) {
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    private void setDx(double dx) {
        this.dx = dx;
    }
}
