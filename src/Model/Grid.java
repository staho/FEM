package Model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private List<Node> ND = null;
    private List<Element> EL = null;
    private GlobalData globalData;
    private int nh, ne;

    private static Grid grid = null;

    public Grid(GlobalData globalData) {
        this.globalData = globalData;
        nh = globalData.getNh();
        ne = globalData.getNe();

        ND = new ArrayList<>(nh);
        EL = new ArrayList<>(ne);
    }

    public void generateGrid(){
        //first generate nodes list
        double dx = globalData.getDx();
        double dy = globalData.getDy();

        for (int i = 0; i < globalData.getnB(); ++i)
            for (int j = 0; j < globalData.getnH(); ++j){
                double x = i * dx;
                double y = j * dy;
                boolean status = false;
                if(y == globalData.getH()){ //x == 0.0 || y == 0.0 || x == globalData.getB() ||
                    status = true;
                    //System.out.println("x: " + x + " y: " + y + " status: " + Boolean.toString(status));
                }
                ND.add(new Node(x, y, i * globalData.getnH() + j, status, globalData.getT0()));

            }
        //generate element list
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
                EL.add(new Element(tab, nodes));
            }
        }
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

    public boolean setTemps(double [] temps){
        if (temps.length == ND.size()){
            for(int i = 0; i < globalData.getNh(); i++){
                ND.get(i).setTemp(temps[i]);
            }
            return true;
        }
        return false;
    }

    public void printNodesTemps(){
        int nodeIter = 0;
        for(int i = 0; i < globalData.getnB(); i++){
            for(int j = 0; j < globalData.getnH(); j++){
                double temp = ND.get(nodeIter++).getTemp();
                System.out.printf("%.15f\t", temp);
            }
            System.out.println("");
        }
        System.out.println("\n\n");
    }
}
