package Model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private List<Node> ND = null;
    private List<Element> EL = null;
    private GlobalData globalData;
    private int nh, ne;

    public Grid(GlobalData globalData) {
        nh = globalData.getNh();
        ne = globalData.getNe();
        this.globalData = globalData;

        ND = new ArrayList<>(nh);
        EL = new ArrayList<>(ne);
    }

    public void setND(List<Node> ND) {
        this.ND = ND;
    }

    public void setEL(List<Element> EL) {
        this.EL = EL;
    }

    public List getND() {
        return ND;
    }

    public List getEL() {
        return EL;
    }

    public void generateGrid(){
        //first generate nodes list
        {
            double dx = globalData.getDx();
            double dy = globalData.getDy();

            for (int i = 0; i < globalData.getnB(); ++i)
                for (int j = 0; j < globalData.getnH(); ++j)
                    ND.add(new Node(i * dx, j * dy, i * globalData.getnH() + j, false, globalData.getT0()));

        }
        //generate element list
        {
            for (int i = 0 ; i < globalData.getnB() - 1; ++i){
                for (int j = 0; j < globalData.getnH() - 1; ++j){

                    int [] tab = new int[4];

                    tab[0] = j + i * globalData.getnH();
                    tab[3] = tab[0] + 1;
                    tab[1] = j + (i+1) * globalData.getnH();
                    tab[2] = tab[1] + 1;

                    Node [] nodes = new Node[4];
                    int z = 0;
                    for(int nodeId: tab){
                        nodes[z] = ND.get(nodeId);
                        z++;
                    }



                    EL.add(new Element(globalData.getShapeFunctionsDerEta(), globalData.getShapeFunctionsDerPsi()).withArray(tab).withNodes(nodes));
                }
            }
            for (Element element : EL){
                element.calculateJacobians();
            }
        }

    }
}
