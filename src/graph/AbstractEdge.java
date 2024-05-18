package graph;

public abstract class AbstractEdge <GraphVertex extends AbstractVertex> {
    protected GraphVertex start;
    protected GraphVertex end;
    //private int refGraph;

    protected AbstractEdge() {

    }
    /*protected AbstractEdge(GraphVertex start, GraphVertex end) {
        this.start = start;this.end = end;
    }*/
    protected abstract void pseudoconstructor(GraphVertex v1, GraphVertex v2);
    protected abstract void remove(); // Zamiast tego należy używać removeEdge w klasie dziedziczącej po AbstractGraph
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract String toString();
    public abstract GraphVertex getStart();
    public abstract GraphVertex getEnd();
}