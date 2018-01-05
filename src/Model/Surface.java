package Model;

public class Surface {
    private final Node[] surf;
    private double [][] shapeFunctionVals;
    private double alfa;

    public Surface(Node node1, Node node2) {
        this.surf = new Node[2];
        this.surf[0] = node1;
        this.surf[1] = node2;
    }

    public Surface(Node node1, Node node2, double alfa) {
        this.surf = new Node[2];
        this.surf[0] = node1;
        this.surf[1] = node2;
        this.alfa = alfa;
    }

    public void setShapeFunctionVals(double[][] shapeFunctionVals) {
        this.shapeFunctionVals = shapeFunctionVals;
    }

    public double[][] getShapeFunctionVals() {
        return shapeFunctionVals;
    }

    public Node[] getSurf() {
        return surf;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }
}
