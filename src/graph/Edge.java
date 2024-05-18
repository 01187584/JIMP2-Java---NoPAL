package graph;

public class Edge <GraphVertex extends AbstractVertex> extends AbstractEdge<GraphVertex> {
    private final static boolean FastEquals = true; // Nie sprawdzamy, czy da się przerzucić obiekt na odpowiedni typ
    private int id;
    private static int last_id = 0;
    //private int refGraph;

    public Edge() {
        id = -1; // Nieprawidłowa krawędź, wykorzystywana tymczasowo
    }
    public Edge(GraphVertex start, GraphVertex end) {
        pseudoconstructor(start,end);
    }
    protected void pseudoconstructor(GraphVertex start, GraphVertex end) {
        this.start = start;this.end = end;
        //refGraph = G;
        last_id++;
        id = last_id;
    }
    protected void remove() {
        start = null;end = null;
    }
    public boolean equals(Object o) {
        if (FastEquals) {
            if (((Edge<GraphVertex>)o).id != id) return false;
            return true;
        }
        if (!(o instanceof TypicalUndirectedGraph)) return false;
        try {
            if (((Edge<GraphVertex>)o).id != id) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        return this.id;
    }
    public String toString() {
        if (start == null || end == null) return "(Nieprawidłowa krawędź)";
        return '('+start.toString()+','+end.toString()+')';
    }
    public GraphVertex getStart() {
        return start;
    }
    public GraphVertex getEnd() {
        return end;
    }
}