package graph;

import java.util.HashSet;

// W tej implementacji nie można dodawać 2 takich samych krawędzi (z tego samego wierzchołka początkowego i końcowego)
public class TypicalUndirectedGraph<GraphVertex extends AbstractVertex, GraphEdge extends AbstractEdge<GraphVertex>> extends AbstractGraph<GraphVertex, GraphEdge, HashSet<GraphVertex>> {
    private final static boolean FastEquals = true; // Nie sprawdzamy, czy da się przerzucić obiekt na odpowiedni typ
    private final int id;
    private static int last_id = 0;

    public TypicalUndirectedGraph() {
        //V.add(null); // Aby liczyć wierzchołki od 1
        last_id++;
        id = last_id;
        //Graphs.add(this);
    }
    public void addVertex(GraphVertex V) {
        this.V.add(V);
        HashSet<GraphVertex> HS = new HashSet<GraphVertex>(1); // Nie wiemy jak wiele może być krawędzi
        AdjacencyList.put(V, HS);
    }
    public GraphVertex getVertex(int numVertex) {
        return V.get(numVertex-1);
    }
    public void addEdge(GraphEdge E) {
        this.E.add(E);
        /*if (AdjacencyList.get(E.start) == null) {
            HashSet<GraphVertex> HS = new HashSet<GraphVertex>(1);
            AdjacencyList.put(E.start, HS);
        }*/
        //System.out.println(E);
        //System.out.println(E.start);
        //System.out.println(E.getstart()); // E.start != E.getstart() ?!?
        //System.out.println(AdjacencyList.get(E.getstart()));
        //System.out.println("Auć");
        AdjacencyList.get(E.start).add(E.end);
        // Graf nieskierowany:
        /*if (AdjacencyList.get(E.end) == null) {
            HashSet<GraphVertex> HS = new HashSet<GraphVertex>(1);
            AdjacencyList.put(E.end, HS);
        }*/
        AdjacencyList.get(E.end).add(E.start);
    }
    public void removeVertex(GraphVertex V) {
        throw new UnsupportedOperationException("Jeszcze nie zaimplementowane!!!");
    }
    public void removeEdge(GraphEdge E) {
        throw new UnsupportedOperationException("Jeszcze nie zaimplementowane!!!");
    }
    public void destroy() {
        throw new UnsupportedOperationException("Jeszcze nie zaimplementowane!!!");
    }
    public HashSet<GraphVertex> getAdjacent(GraphVertex V) {
        return AdjacencyList.get(V);
    }
    public boolean equals(Object o) {
        if (FastEquals) {
            if (((TypicalUndirectedGraph<GraphVertex, GraphEdge>)o).id != id) return false;
            return true;
        }
        if (!(o instanceof TypicalUndirectedGraph)) return false;
        try {
            if (((TypicalUndirectedGraph<GraphVertex, GraphEdge>)o).id != id) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        return id;
    }
    public String toString() {
        String s = new String();
        s += "V: {";
        for (GraphVertex Ver : V) {
            s += Ver.toString();
            s += ',';
        }
        s += "}\nE: {";
        for (GraphEdge Edg : E) {
            s += Edg.toString();
            s += ',';
        }
        s += "}\nLista sąsiedztwa:\n[\n";
        for (GraphVertex Vert : AdjacencyList.keySet()) {
            s += Vert.toString()+" : "+AdjacencyList.get(Vert).toString()+",\n";
        }
        s += "]";
        return s;
    }
}