package Model;

public class Element {

    private int [] IDArray = null;
    private Node [] nodes = null;
    private double [] pow = null;
    private final int noOfIDs = 4;
    private int elementID = 0;


    public Element() {
        IDArray = new int[this.noOfIDs];
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
