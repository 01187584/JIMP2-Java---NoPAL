package graph;

public abstract class AbstractVertex {
    //public boolean isTraversable;
    protected int VertexNum;
    public boolean isStartVertex;
    public boolean isEndVertex;
    //private int degree; Obecnie niepotzebny stopień wierzchołka
    //private int refGraph;

    /*public AbstractVertex() {
        System.out.println("Tu jestem?");
    }*/
    //public abstract GraphEdge[] getEdges();
    //public abstract void connectWith(AbstractVertex<GraphEdge> V);
    /*public static AbstractVertex newVertex() {
        AbstractVertex V = new AbstractVertex();
        return V;
    }*/
    protected abstract void pseudoconstructor(int VertexNum);
    protected abstract void remove(); // Zamiast tego należy używać removeVertex w klasie dziedziczącej po AbstractGraph
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract String toString();
    public abstract int getNum();
    //public abstract int getDegree();
    //protected abstract int incrementDegree();
    //protected abstract int decrementDegree();
}