package graph_v2;

import java.util.HashMap;
import java.util.HashSet;

public interface Graph {
    //public static final boolean isDirected; // No tak, Java...

    public Vertex addVertex();
    //public void addVertex(Vertex V);
    public void addVertices(int numVertices);
    public Vertex getVertex(int numVertex);
    public void addEdge(int numV1, int numV2);
    public void addEdge(Vertex V1, Vertex V2); 
    //void addEdge(Edge E);
    public void removeVertex(Vertex V);
    //void removeEdge(Edge E); 
    public void destroy();
    public void removeAllVertices();
    //getAdjacent(Vertex V);
    public boolean equals(Object o);
    public int hashCode();
    public String toString();
    public HashMap<Integer, Vertex> getV();
    //HashSet<Edge> getE(); - zamiast tego jest Edges getE()
    public Edges getE();
    public HashSet<Vertex> getStartV();
    public HashSet<Vertex> getEndV();
    public void addStartVertex(Vertex v);
    public void addEndVertex(Vertex v);
    public void clearStartVertices();
    public void clearEndVertices();
    public int getLastVertexNum();
    public void incrementLastVertexNum(); // powinno być protected, ale Java...
    public boolean getIsDirected();
    public boolean VertexImplementsGetEdges(); // Czy wierzchołek grafu implementuje metodę getEdges(). Wszystkie wierzchołki w danym grafie są obiektami tej samej klasy.
    public Edge getSampleEdge(); // Przykładowa krawędź w tym grafie (nie musi łączyć istniejących wierzchołków) - jest używana przez EdgesIterable do iteracji po krawędziach
}
