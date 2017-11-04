package Model;

public class Node {
    private double x;
    private double y;
    private double result;
    private boolean status;

    public Node(double x, double y, boolean status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        this.status = false;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
