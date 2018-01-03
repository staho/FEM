package Model;

import Jama.Matrix;

public class Jacobian {
    private Matrix J;
    private Matrix jInverted;
    private int integrationPoint; //in range [0,3]
    private Matrix shapeFunctionsDerEta;
    private Matrix shapeFunctionsDerPsi;
    private double det;

    public Jacobian(int integrationPoint, double x[], double y[], Matrix shapeFunctionsDerEta, Matrix shapeFunctionsDerPsi){
        this.shapeFunctionsDerEta = shapeFunctionsDerEta;
        this.shapeFunctionsDerPsi = shapeFunctionsDerPsi;
        this.integrationPoint = integrationPoint;

        J = new Matrix(2,2);

        double dxDpsi = this.shapeFunctionsDerPsi.get(integrationPoint, 0) * x[0] +
                this.shapeFunctionsDerPsi.get(integrationPoint, 1) * x[1] +
                this.shapeFunctionsDerPsi.get(integrationPoint, 2) * x[2] +
                this.shapeFunctionsDerPsi.get(integrationPoint, 3) * x[3];
        double dyDpsi = this.shapeFunctionsDerPsi.get(integrationPoint, 0) * y[0] +
                this.shapeFunctionsDerPsi.get(integrationPoint, 1) * y[1] +
                this.shapeFunctionsDerPsi.get(integrationPoint, 2) * y[2] +
                this.shapeFunctionsDerPsi.get(integrationPoint, 3) * y[3];
        double dxDeta = this.shapeFunctionsDerEta.get(integrationPoint, 0) * x[0] +
                this.shapeFunctionsDerEta.get(integrationPoint, 1) * x[1] +
                this.shapeFunctionsDerEta.get(integrationPoint, 2) * x[2] +
                this.shapeFunctionsDerEta.get(integrationPoint, 3) * x[3];
        double dyDeta = this.shapeFunctionsDerEta.get(integrationPoint, 0) * y[0] +
                this.shapeFunctionsDerEta.get(integrationPoint, 1) * y[1] +
                this.shapeFunctionsDerEta.get(integrationPoint, 2) * y[2] +
                this.shapeFunctionsDerEta.get(integrationPoint, 3) * y[3];

        J.set(0,0,dxDpsi);
        J.set(0,1,dyDpsi);
        J.set(1,0,dxDeta);
        J.set(1,1,dyDeta);

        det = J.det();

        jInverted = J.transpose();
        jInverted.set(0,1, jInverted.get(0,1)*(-1.0));
        jInverted.set(1,0, jInverted.get(1,0)*(-1.0));
    }

    public Matrix getjInverted() {
        return jInverted;
    }

    public double getDet() {
        return det;
    }
}
