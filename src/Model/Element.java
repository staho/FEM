package Model;

import Jama.Matrix;
import Model.Maths.IntegralPoints;
import Model.Maths.Point;

import java.util.LinkedList;
import java.util.List;

public class Element {

    private int [] IDArray = null;
    private Node [] nodes = null;
    private Surface [] surfaces = null;
    private final int noOfIDs = 4;
    private int nodesOfBorders = 0;
    private List<Integer> IDOfBordersSurfaces = null;

    public Element(int[] IDArray, Node[] nodes) {
        this.IDArray = IDArray;
        this.nodes = nodes;
        IDOfBordersSurfaces = new LinkedList<>();

        surfaces = new Surface[4];
        surfaces[0] = new Surface(nodes[3], nodes[0]);
        surfaces[1] = new Surface(nodes[0], nodes[1]);
        surfaces[2] = new Surface(nodes[1], nodes[2]);
        surfaces[3] = new Surface(nodes[2], nodes[3]);

        for(Surface surface: surfaces)  //checking if edge is on border
            if(surface.getSurf()[0].isStatus() && surface.getSurf()[1].isStatus()) nodesOfBorders++;

        for(int i = 0; i < 4; i++)
            if(surfaces[i].getSurf()[0].isStatus() && surfaces[i].getSurf()[1].isStatus()) IDOfBordersSurfaces.add(i);
    }

    public int getNodesOfBorders() {
        return nodesOfBorders;
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

    public List<Integer> getIDOfBordersSurfaces() {
        return IDOfBordersSurfaces;
    }

    public Surface[] getSurfaces() {
        return surfaces;
    }

    public Surface getSurfaceOfId(int id){
        if(id >= 0 && id <= surfaces.length) return surfaces[id];
        else return null;
    }

    public void setSurfaces(Surface [] surface){
        surfaces = surface;
    }
}
