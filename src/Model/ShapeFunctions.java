package Model;
/*
 * All functions and it's derivatives are described in
 * http://home.agh.edu.pl/~pkustra/MES/Jakobian.pdf
 */
public class ShapeFunctions {
    public static double shapeFunction1(double psi, double eta){
        return shapeFunctionBase(psi, eta, -1., -1.);
    }

    public static double shapeFunction2(double psi, double eta){
        return shapeFunctionBase(psi, eta, 1., -1.);
    }

    public static double shapeFunction3(double psi, double eta){
        return shapeFunctionBase(psi, eta, 1., 1.);
    }

    public static double shapeFunction4(double psi, double eta){
        return shapeFunctionBase(psi, eta, -1., 1.);
    }

    private static double shapeFunctionBase(double psi, double eta, double etaSign, double psiSign){
        return 0.25 * ((1 + etaSign * eta) * (1 + psiSign * psi));
    }

    //Eta derr section
    //********************************************************************
    public static double shapeFunctionDerivative1Eta(double psi){
        return -0.25 * (1 - psi);
    }

    public static double shapeFunctionDerivative2Eta(double psi) {
        return -0.25 * (1 + psi);
    }

    public static double shapeFunctionDerivative3Eta(double psi){
        return 0.25 * (1 + psi);
    }

    public static double shapeFunctionDerivative4Eta(double psi){
        return 0.25 * (1 - psi);
    }

    //Psi derr section
    //********************************************************************
    public static double shapeFunctionDerivative1Psi(double eta){
        return -0.25 * (1 - eta);
    }

    public static double shapeFunctionDerivative2Psi( double eta) {
        return -1. * shapeFunctionDerivative1Psi(eta);
    }

    public static double shapeFunctionDerivative3Psi(double eta){
        return 0.25 * (1 + eta);
    }

    public static double shapeFunctionDerivative4Psi(double eta){
        return -1. * shapeFunctionDerivative3Psi(eta);
    }
}
