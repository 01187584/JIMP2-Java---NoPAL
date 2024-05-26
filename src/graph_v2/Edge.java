package graph_v2;

public interface Edge {
    //protected void pseudoconstructor(GraphVertex, GraphVertex);
    //public void remove();
    public boolean equals(Object o);
    public int hashCode();
    public String toString();
    public Vertex getStart();
    public Vertex getEnd();
    public void setStart(Vertex V);
    public void setEnd(Vertex V);
    public void destroy();
}
