package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public abstract class AbstractGraph<GraphVertex extends AbstractVertex, GraphEdge extends AbstractEdge<GraphVertex>, AdjacencyListCollection extends Collection<GraphVertex>> {
    //public HashSet<GraphVertex> V = new HashSet<GraphVertex>();
    public ArrayList<GraphVertex> V = new ArrayList<GraphVertex>(); // Zamiast HashSet, bo tak jest to bardziej praktyczne
    public HashSet<GraphEdge> E = new HashSet<GraphEdge>();
    protected HashMap<GraphVertex, AdjacencyListCollection> AdjacencyList = new HashMap<GraphVertex, AdjacencyListCollection>();
    public HashSet<GraphVertex> startV = new HashSet<GraphVertex>();
    public HashSet<GraphVertex> endV = new HashSet<GraphVertex>();
    //public static ArrayList<AbstractGraph> Graphs;

    public abstract void addVertex(GraphVertex V);
    public abstract void addEdge(GraphEdge E);
    public abstract void removeVertex(GraphVertex V);
    public abstract void removeEdge(GraphEdge E);
    public abstract void destroy();
    public abstract AdjacencyListCollection getAdjacent(GraphVertex V);
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract String toString();
}