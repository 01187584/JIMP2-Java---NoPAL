package graph_v2;

import java.util.HashSet;

public class SimpleVertex implements Vertex {
    private static int last_id = 0;
    protected final int id;
    protected HashSet<Vertex> AdjacentVertices;
    protected final int VertexNum;
    private boolean IsStartVertex;
    private boolean IsEndVertex;
    private final Graph refGraph;

    protected SimpleVertex(Graph G) {
        refGraph = G;
        //this.VertexNum = VertexNum;
        G.incrementLastVertexNum();
        VertexNum = G.getLastVertexNum();
        AdjacentVertices = new HashSet<>();
        last_id++;
        id = last_id;
        G.getV().put(VertexNum, this);
    }

    @Override
    public void connectWith(Vertex V) {
        AdjacentVertices.add(V);
        if (!refGraph.getIsDirected()) {
            V.getAdjacentVetices().add(this);
        }
    }

    @Override
    public int getNum() {
        return VertexNum;
    }

    @Override
    public HashSet<Vertex> getAdjacentVetices() {
        return AdjacentVertices;
    }

    @Override
    public void remove() {
        destroy();
        refGraph.getV().remove(VertexNum);
        refGraph.getStartV().remove(this);
        refGraph.getEndV().remove(this);
    }

    @Override
    public void destroy() {
        //id = -1; // final
        //VertexNum = -1; // final
        //refGraph = -1; // final
        AdjacentVertices.clear();
    }

    @Override
    public EdgesIterable getEdges() {
        throw new UnsupportedOperationException("Nie można uzyskać poszczególnych krawędzi wychodzących z wierzchołka typu SimpleVertex, a jedynie sąsiadujące wierzchołki: użyj getAdjacentVetices()");
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimpleVertex)) return false;
        try {
            SimpleVertex oo = ((SimpleVertex)o);
            if (oo.id != id) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(VertexNum);
    }

    @Override
    public StringBuilder toStringBuilder() {
        // Zwraca StringBuilder zawierający zbiór sąsiadujących wierzchołków
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Vertex lastVertex = null;
        for (Vertex vertex : AdjacentVertices) {
            if (lastVertex != null) {
                sb.append(lastVertex.getNum());
                sb.append(", ");
            }
            lastVertex = vertex;
        }
        if (lastVertex != null)
            sb.append(lastVertex.getNum());
        sb.append('}');
        return sb;
    }

    @Override
    public boolean GetIsStartVertex() {
        return IsStartVertex;
    }

    @Override
    public boolean GetIsEndVertex() {
        return IsEndVertex;
    }

    @Override
    public void SetIsStartVertex(boolean to) {
        IsStartVertex = to;
        if (to) {
            refGraph.getStartV().add(this);
        } else {
            refGraph.getStartV().remove(this);
        }
    }

    @Override
    public void SetIsEndVertex(boolean to) {
        IsEndVertex = to;
        if (to) {
            refGraph.getEndV().add(this);
        } else {
            refGraph.getEndV().remove(this);
        }
    }
}
