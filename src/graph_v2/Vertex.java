package graph_v2;

import java.util.HashSet;

public interface Vertex {
    /*protected int VertexNum;
    protected boolean isStartVertex;
    protected boolean isEndVertex;*/
    //protected void pseudoconstructor(int VertexNum);
    public void connectWith(Vertex V);
    //public void remove(); - zastąpione przez destroy()
    public boolean equals(Object o);
    public int hashCode();
    public String toString();
    public int getNum();
    public HashSet<Vertex> getAdjacentVetices();
    public void remove(); // powinno się używać zamiast destroy() poza tym packagem
    public void destroy(); // powinno być protected, ale Java...
    public EdgesIterable getEdges(); // niepolecane, może nie zostać zaimplementowane
    public boolean getIsStartVertex();
    public boolean getIsEndVertex();
    public void setIsStartVertex(boolean to);
    public void SetIsEndVertex(boolean to);
    public StringBuilder toStringBuilder();
}
