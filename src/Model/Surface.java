package Model;

public class Surface {
    private final Node[] surf;

    public Surface(Node node1, Node node2) {
        this.surf = new Node[2];
        this.surf[0] = node1;
        this.surf[1] = node2;
    }

    public Node[] getSurf() {
        return surf;
    }
}
