package Model;

public class Element {
    private int [] IDArray = null;
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
}
