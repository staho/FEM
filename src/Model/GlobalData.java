package Model;

import Jama.Matrix;
import Model.Maths.IntegralPoints;
import Model.Maths.Point;
import com.google.gson.Gson;

import java.io.FileReader;
/*
* Numbers in comments are reference to document
* http://home.agh.edu.pl/~pkustra/MES/FEM_transient_2d.pdf
* */

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

    private Matrix hCurrent;
    private Matrix hGlobal;
    private double[] pCurrent;
    private double[] pGlobal;
    private Matrix shapeFunctionsDerEta;
    private Matrix shapeFunctionsDerPsi;
    private Matrix shapeFunctions;
    private Element localElement;

    private static GlobalData globalData;

    private Grid grid;

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

            this.hCurrent = null;
            this.pCurrent = null;
            this.hGlobal = new Matrix(this.nh, this.nh);
            this.pGlobal = new double[this.nh];
            for (double elem: pGlobal) elem = 0.;


            generateDerMatrices();
            generateLocalElement();

            grid = new Grid(this);
            grid.generateGrid();
        }
    }

    private void generateDerMatrices(){
        Point[] points = IntegralPoints.getIntegralPoints();

        shapeFunctionsDerEta = new Matrix(4, 4);
            for(int i = 0; i < 4; i++){
                shapeFunctionsDerEta.set(i,0, ShapeFunctions.shapeFunctionDerivative1Eta(points[i].getX()));
                shapeFunctionsDerEta.set(i,1, ShapeFunctions.shapeFunctionDerivative2Eta(points[i].getX()));
                shapeFunctionsDerEta.set(i,2, ShapeFunctions.shapeFunctionDerivative3Eta(points[i].getX()));
                shapeFunctionsDerEta.set(i,3, ShapeFunctions.shapeFunctionDerivative4Eta(points[i].getX()));
            }

        shapeFunctionsDerPsi = new Matrix(4, 4);
            for(int i = 0; i < 4; i++){
                shapeFunctionsDerPsi.set(i,0, ShapeFunctions.shapeFunctionDerivative1Psi(points[i].getY()));
                shapeFunctionsDerPsi.set(i,1, ShapeFunctions.shapeFunctionDerivative2Psi(points[i].getY()));
                shapeFunctionsDerPsi.set(i,2, ShapeFunctions.shapeFunctionDerivative3Psi(points[i].getY()));
                shapeFunctionsDerPsi.set(i,3, ShapeFunctions.shapeFunctionDerivative4Psi(points[i].getY()));
            }
        shapeFunctions = new Matrix(4,4);
            for(int i = 0; i < 4; i++){
                shapeFunctions.set(i,0, ShapeFunctions.shapeFunction1(points[i].getX(), points[i].getY()));
                shapeFunctions.set(i,1, ShapeFunctions.shapeFunction2(points[i].getX(), points[i].getY()));
                shapeFunctions.set(i,2, ShapeFunctions.shapeFunction3(points[i].getX(), points[i].getY()));
                shapeFunctions.set(i,3, ShapeFunctions.shapeFunction4(points[i].getX(), points[i].getY()));
            }
    }

    private void generateLocalElement(){
        Node [] localNodes = new Node[4];
        Point [] integrationPoints = IntegralPoints.getIntegralPoints();
        for (int i = 0; i < localNodes.length; i++) localNodes[i] = new Node(integrationPoints[i]);

        int [] fakeIds = new int[]{0,0,0,0};
        localElement = new Element(fakeIds, localNodes);


        Surface [] surfaces = new Surface[4];
        surfaces[0] = new Surface(new Node(new Point(-1.0, 0.577)), new Node(new Point(-1.0, -0.577)));
        surfaces[1] = new Surface(new Node(new Point(-0.577, -1)), new Node(new Point(0.577, -1.)));
        surfaces[2] = new Surface(new Node(new Point(1.0, -0.577)), new Node(new Point(1.0, 0.577)));
        surfaces[3] = new Surface(new Node(new Point(0.577, 1.)), new Node(new Point(-0.577, 1.0)));

        localElement.setSurfaces(surfaces);

        for(int i = 0; i < 4; i++){
            double [][] shapeFvals = new double[2][4];
            for(int j = 0; j < 2; j++){
                shapeFvals[j][0] = ShapeFunctions.shapeFunction1(surfaces[i].getSurf()[j].getX(), surfaces[i].getSurf()[j].getY());
                shapeFvals[j][1] = ShapeFunctions.shapeFunction2(surfaces[i].getSurf()[j].getX(), surfaces[i].getSurf()[j].getY());
                shapeFvals[j][2] = ShapeFunctions.shapeFunction3(surfaces[i].getSurf()[j].getX(), surfaces[i].getSurf()[j].getY());
                shapeFvals[j][3] = ShapeFunctions.shapeFunction4(surfaces[i].getSurf()[j].getX(), surfaces[i].getSurf()[j].getY());
            }
            surfaces[i].setShapeFunctionVals(shapeFvals);
        }
    }

    public void process(){
        for(int i = 0; i < pGlobal.length; i++) pGlobal[i] = 0.;
        hGlobal = new Matrix(nh, nh);

        Jacobian jacobian;
        double[] dNdx = new double[4];
        double[] dNdy = new double[4];
        double[] coordsX = new double[4];
        double[] coordsY = new double[4];
        double[] initialTemps = new double[4];
        double tempInterpolated = 0., cMatrixIJ; //tempInterpolated is for temperatures interpolated from nodes, cMatixIJ is cell from 'C' matrix (6.8)
        int id;
        double detJ = 0.;

        for(int elemIter = 0; elemIter < ne; elemIter++){  //iterating through all elements of grid
            Element currentElement = (Element)(grid.getEL().get(elemIter));
            hCurrent = new Matrix(4,4);
            pCurrent = new double[]{0.,0.,0.,0.};

            for(int i = 0; i < 4; i++){
                id = currentElement.getIDArray()[i];                               //universal id of node in current element
                coordsX[i] = ((Node)(grid.getND().get(id))).getX();             //storing nodes coords
                coordsY[i] = ((Node)(grid.getND().get(id))).getY();
                initialTemps[i] = ((Node)(grid.getND().get(id))).getTemp();     //initial temps of nodes
            }

            for (int ipIter = 0; ipIter < 4; ipIter++){
                jacobian = new Jacobian(ipIter, coordsX, coordsY, shapeFunctionsDerEta, shapeFunctionsDerPsi);
                tempInterpolated = 0;

                for(int i = 0; i < 4; i++){
                    dNdx[i] = jacobian.getFinalJacobian().get(0,0) * shapeFunctionsDerPsi.get(ipIter, i)
                            + jacobian.getFinalJacobian().get(0,1) * shapeFunctionsDerEta.get(ipIter, i);

                    dNdy[i] = jacobian.getFinalJacobian().get(1, 0) * shapeFunctionsDerPsi.get(ipIter, i)
                            + jacobian.getFinalJacobian().get(1, 1) * shapeFunctionsDerEta.get(ipIter, i);

                    //interpolating temperatures from element nodes
                    // t = N1*t1 * ... *N4*t4
                    tempInterpolated += initialTemps[i] * this.shapeFunctions.get(ipIter, i);
                }

                detJ = Math.abs(jacobian.getDet());
                //multiplying N by N^T
                //6.8
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        cMatrixIJ = c * ro * shapeFunctions.get(ipIter, i) * shapeFunctions.get(ipIter, j) * detJ;
                        double tempVal = hCurrent.get(i, j) + k * (dNdx[i] * dNdx[j] + dNdy[i] * dNdy[j]) * detJ + cMatrixIJ / dTau;
                        hCurrent.set(i, j, tempVal);
                        tempVal = pCurrent[i] + cMatrixIJ / dTau * tempInterpolated;
                        pCurrent[i] = tempVal;
                    }
                }
            }

            //boundary conditions
            //iterates through number of boundary condition edges
            for(int surfIter = 0; surfIter < currentElement.getNodesOfBorders(); surfIter++) {
                id = currentElement.getIDOfBordersSurfaces().get(surfIter);     //id of border surface in elelemnt
                Surface surface = currentElement.getSurfaceOfId(id);

                detJ = Math.sqrt(Math.pow((surface.getSurf()[0].getX() - surface.getSurf()[1].getX()), 2)
                        + Math.pow((surface.getSurf()[0].getY() - surface.getSurf()[1].getY()), 2)) / 2.0;

                for (int i = 0; i < 2; i++) {       //2 points of integration on surface
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            double tempVal = hCurrent.get(j, k);
                            tempVal += alfa * localElement.getSurfaces()[id].getShapeFunctionVals()[i][j]
                                    * localElement.getSurfaces()[id].getShapeFunctionVals()[i][k] * detJ;
                            hCurrent.set(j, k, tempVal);
                        }
                        pCurrent[j] += alfa * tInf * localElement.getSurfaces()[id].getShapeFunctionVals()[i][j] * detJ;
                    }
                }
            }
            //agregation
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 4; j++){
                    int first = currentElement.getIDArray()[i];
                    int second = currentElement.getIDArray()[j];
                    double tempValue = hGlobal.get(first, second) + hCurrent.get(i,j);
                    hGlobal.set(first, second, tempValue);
                }
                pGlobal[currentElement.getIDArray()[i]] += pCurrent[i];
            }


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

    public Grid getGrid() {
        return grid;
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

    public Matrix gethCurrent() {
        return hCurrent;
    }

    public void sethCurrent(Matrix hCurrent) {
        this.hCurrent = hCurrent;
    }

    public Matrix gethGlobal() {
        return hGlobal;
    }

    public void sethGlobal(Matrix hGlobal) {
        this.hGlobal = hGlobal;
    }

    public double[] getpCurrent() {
        return pCurrent;
    }

    public void setpCurrent(double[] pCurrent) {
        this.pCurrent = pCurrent;
    }

    public double[] getpGlobal() {
        return pGlobal;
    }

    public void setpGlobal(double[] pGlobal) {
        this.pGlobal = pGlobal;
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
