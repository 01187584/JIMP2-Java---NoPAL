package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public abstract class AbstractGraph <GraphVertex extends AbstractVertex, GraphEdge extends AbstractEdge<GraphVertex>, AdjacencyListCollection extends Collection<GraphVertex>> {
    protected final Class<GraphVertex> GraphVertexClass;
    protected final Class<GraphEdge> GraphEdgeClass;
    //public HashSet<GraphVertex> V = new HashSet<GraphVertex>();
    protected HashMap<Integer, GraphVertex> V = new HashMap<Integer, GraphVertex>();  // Zamiast HashSet, bo tak jest to bardziej praktyczne
    protected HashSet<GraphEdge> E = new HashSet<GraphEdge>();
    protected HashMap<GraphVertex, AdjacencyListCollection> AdjacencyList = new HashMap<GraphVertex, AdjacencyListCollection>();
    protected HashSet<GraphVertex> startV = new HashSet<GraphVertex>();
    protected HashSet<GraphVertex> endV = new HashSet<GraphVertex>();
    protected int lastVertexNum = 0;
    //public static ArrayList<AbstractGraph> Graphs;

    protected AbstractGraph(Class<GraphVertex> GraphVertexClass, Class<GraphEdge> GraphEdgeClass) {
        this.GraphVertexClass = GraphVertexClass;
        this.GraphEdgeClass = GraphEdgeClass;
    }
    public abstract GraphVertex addVertex();
    public abstract void addVertex(GraphVertex V);
    public abstract void addVertices(int numVertices);
    public abstract GraphVertex getVertex(int numVertex);
    public abstract void addEdge(int numV1, int numV2);
    public abstract void addEdge(GraphVertex V1, GraphVertex V2);
    public abstract void addEdge(GraphEdge E);
    public abstract void removeVertex(GraphVertex V);
    public abstract void removeEdge(GraphEdge E);
    public abstract void destroy();
    public abstract AdjacencyListCollection getAdjacent(GraphVertex V);
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract String toString();
    public abstract HashMap<Integer, GraphVertex> getV();
    public abstract HashSet<GraphEdge> getE();
    public abstract HashSet<GraphVertex> getStartV();
    public abstract HashSet<GraphVertex> getEndV();
}