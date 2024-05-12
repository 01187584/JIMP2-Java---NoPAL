package graph;

public abstract class AbstractVertex {
    //public boolean isTraversable; 
    public boolean isStartVertex; 
    public boolean isEndVertex; 
    //private int refGraph;
    //public abstract GraphEdge[] getEdges();
    //public abstract void connectWith(AbstractVertex<GraphEdge> V);
    public abstract void remove();
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract String toString();
}