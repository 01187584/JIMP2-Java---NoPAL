package graph;

public class Edge<GraphVertex extends AbstractVertex> extends AbstractEdge<GraphVertex> {
    private final static boolean FastEquals = true; // Nie sprawdzamy, czy da się przerzucić obiekt na odpowiedni typ
    private final int id;
    private static int last_id = 0;
    //private int refGraph;

    public Edge(GraphVertex start, GraphVertex end) {
        //refGraph = G;
        last_id++;
        id = last_id;
        this.start = start;this.end = end;
    }
    public void remove() {
        throw new UnsupportedOperationException("Jeszcze nie zaimplementowane!!!");
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
        return '('+start.toString()+','+end.toString()+')';
    }
}