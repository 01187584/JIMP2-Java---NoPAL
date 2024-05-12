package graph;

public abstract class AbstractEdge<GraphVertex extends AbstractVertex> {
    public GraphVertex start;
    public GraphVertex end;
    //private int refGraph;

    public abstract void remove();
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract String toString();
}