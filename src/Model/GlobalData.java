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
    private int nH;         //number of nodes on height
    private int nB;         //number of nodes on width
    private int nh;         //number of nodes
    private int ne;         //number of elements
    private double t0;      //starting temperature
    private double tau;     //time of process
    private double dTau;    //time differential between each step
    private double tInf;    //temperature of infinitive
    private double alfa;    //heat exchange ratio
    private double c;       //specific heat
    private double k;       //heat conduction ratio
    private double ro;      //density

    private Matrix HCurrent;
    private Matrix HGlobal;
    private double[] Pcurrent;
    private double[] Pglobal;
    private Matrix shapeFunctionsDerEta;
    private Matrix shapeFunctionsDerPsi;

    private static GlobalData globalData;


    public GlobalData() {

    }

    public GlobalData(boolean x){
        GlobalData tempData = this.readConfig();
        if(tempData != null) {
            this.B = tempData.getB();
            this.H = tempData.getH();
            this.nH = tempData.getnH();
            this.nB = tempData.getnB();
            this.t0 = tempData.getT0();
            this.tau = tempData.getTau();
            this.dTau = tempData.getdTau();
            this.tInf = tempData.gettInf();
            this.alfa = tempData.getAlfa();
            this.c = tempData.getC();
            this.k = tempData.getK();
            this.ro = tempData.getRo();


            this.setNh(this.getnH() * this.getnB());
            this.setNe((this.getnB() - 1) * (this.getnH() - 1));
            this.setDx(this.getB() / (this.getnB() - 1));
            this.setDy(this.getH() / (this.getnH() - 1));

            this.HCurrent = new Matrix(4,4);
            this.Pcurrent = new double[4];
            this.HGlobal = new Matrix(this.nh, this.nh);
            this.Pglobal = new double[]{0.,0.,0.,0.};

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

    public void compute(){
        for(double x : Pglobal) x = 0.;

        Grid grid = Grid.getInstance();
        Jacobian jacobian;
        

    }

    public static GlobalData getInstance(){
        if (globalData == null){
            globalData = new GlobalData(true);
        }
        return globalData;
    }

    public double getTau() {
        return tau;
    }

    public void setTau(double tau) {
        this.tau = tau;
    }

    public double getdTau() {
        return dTau;
    }

    public void setdTau(double dTau) {
        this.dTau = dTau;
    }

    public double gettInf() {
        return tInf;
    }

    public void settInf(double tInf) {
        this.tInf = tInf;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getRo() {
        return ro;
    }

    public void setRo(double ro) {
        this.ro = ro;
    }

    public Matrix getHCurrent() {
        return HCurrent;
    }

    public void setHCurrent(Matrix HCurrent) {
        this.HCurrent = HCurrent;
    }

    public Matrix getHGlobal() {
        return HGlobal;
    }

    public void setHGlobal(Matrix HGlobal) {
        this.HGlobal = HGlobal;
    }

    public double[] getPcurrent() {
        return Pcurrent;
    }

    public void setPcurrent(double[] pcurrent) {
        Pcurrent = pcurrent;
    }

    public double[] getPglobal() {
        return Pglobal;
    }

    public void setPglobal(double[] pglobal) {
        Pglobal = pglobal;
    }

    public void setShapeFunctionsDerEta(Matrix shapeFunctionsDerEta) {
        this.shapeFunctionsDerEta = shapeFunctionsDerEta;
    }

    public void setShapeFunctionsDerPsi(Matrix shapeFunctionsDerPsi) {
        this.shapeFunctionsDerPsi = shapeFunctionsDerPsi;
    }

    public double getT0() {
        return t0;
    }

    public void setT0(double t0) {
        this.t0 = t0;
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
