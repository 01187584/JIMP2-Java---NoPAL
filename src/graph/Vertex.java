package graph;

public class Vertex extends AbstractVertex {
    private final static boolean FastEquals = true; // Nie sprawdzamy, czy da się przerzucić obiekt na odpowiedni typ
    private int id;
    private static int last_id = 0;
    //public GraphEdge[] getEdges();
    //public void connectWith(AbstractVertex<GraphEdge> V);
    public Vertex() {
        id = -1;
        VertexNum = -1;
    }
    public Vertex(int VertexNum) {
        pseudoconstructor(VertexNum);
    }
    protected void pseudoconstructor(int VertexNum) {
        this.VertexNum = VertexNum;
        last_id++;
        id = last_id;
    }
    /*public Vertex(boolean isTraversable) {
        //refGraph = G;
        last_id++;
        id = last_id;
        this.isTraversable = isTraversable;
    }*/
    /*public static Vertex newVertex() {
        Vertex V = new Vertex();
        return V;
    }*/
    protected void remove() {
        // to nic nie robi, dzięki Javie, która nie pozwoli zwolnić pamięci
    }
    public boolean equals(Object o) {
        if (FastEquals) {
            if (((Vertex)o).id != id) return false;
            return true;
        }
        if (!(o instanceof TypicalUndirectedGraph)) return false;
        try {
            if (((Vertex)o).id != id) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        return id;
    }
    public String toString() {
        return String.valueOf(id);
    }
    public int getNum() {
        return VertexNum;
    }
    public int getId() {
        return id;
    }
}