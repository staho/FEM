package Model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

@XmlRootElement(name = "globaldata")
public class GlobalData {
    private double H;   //H is the height of field
    private double B;   //B is the width of field
    private double dy;
    private double dx;
    private int nH;     //number of nodes on height
    private int nB;     //number of nodes on width
    private int nh;     //number of nodes
    private int ne;     //number of elements

    public GlobalData() { //TODO: constructor should read data from .xml file

    }

    public GlobalData readConfig(){
        try {
            JAXBContext context = JAXBContext.newInstance(GlobalData.class);

            Unmarshaller m = context.createUnmarshaller();
            GlobalData tempData = (GlobalData) m.unmarshal(new File(System.getProperty("user.dir") + "/data/data.xml"));


            tempData.setNh( tempData.getnH() * tempData.getnB() );
            tempData.setNe( ( tempData.getnB() - 1 ) * ( tempData.getnH() - 1 ) );
            tempData.setDx(tempData.getB()/( tempData.getnB() - 1));
            tempData.setDy(tempData.getH()/( tempData.getnH() - 1));


            return tempData;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public double getH() {
        return H;
    }

    @XmlElement(name = "H")
    public void setH(double h) {
        H = h;
    }

    public double getB() {
        return B;
    }

    @XmlElement(name = "B")
    public void setB(double b) {
        B = b;
    }

    public int getnH() {
        return nH;
    }

    @XmlElement(name = "nH")
    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnB() {
        return nB;
    }

    @XmlElement(name = "nB")
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
