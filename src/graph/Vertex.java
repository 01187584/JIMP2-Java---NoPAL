package graph;

public class Vertex extends AbstractVertex {
    private final static boolean FastEquals = true; // Nie sprawdzamy, czy da się przerzucić obiekt na odpowiedni typ
    private final int id;
    private static int last_id = 0;
    //public GraphEdge[] getEdges();
    //public void connectWith(AbstractVertex<GraphEdge> V);
    public Vertex() {
        last_id++;
        id = last_id;
        //throw new UnsupportedOperationException("Nie używaj tego konstruktora!!!");
    }
    /*public Vertex(boolean isTraversable) {
        //refGraph = G;
        last_id++;
        id = last_id;
        this.isTraversable = isTraversable;
    }*/
    public void remove() {
        throw new UnsupportedOperationException("Jeszcze nie zaimplementowane!!!");
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
}