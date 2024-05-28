package graph_v2;

import java.util.HashMap;
import java.util.HashSet;

public class TypicalUndirectedGraph implements Graph {
    //private static final boolean isDirected = false;
    private static int last_id = 0;
    private static Edge SampleEdge = new TypicalUndirectedEdge();
    protected final int id;
    private int LastVertexNum = 0;
    HashMap<Integer, Vertex> V;
    Edges E;
    // TODO - zmienić checkCorrectUse na CHECK_CORRECT_USE, bo to final
    private static final boolean checkCorrectUse = true;
    private HashSet<Vertex> StartV;
    private HashSet<Vertex> EndV;

    public TypicalUndirectedGraph() {
        last_id++;
        id = last_id;
        V = new HashMap<Integer, Vertex>();
        E = new EdgesIterable(this);
        StartV = new HashSet<Vertex>();
        EndV = new HashSet<Vertex>();
    }

    @Override
    public Vertex addVertex() {
        //LastVertexNum++; Przeniesione do Vertex (SimpleVertex)
        //Vertex NV = new SimpleVertex(LastVertexNum);
        //Vertex NV = new SimpleVertex(this);
        //V.put(LastVertexNum, NV); Przeniesione do Vertex (SimpleVertex)
        return new SimpleVertex(this);
    }

    @Override
    public void addVertices(int numVertices) {
        for (int i = 0;i < numVertices;i++) addVertex();
    }

    @Override
    public Vertex getVertex(int numVertex) {
        if (checkCorrectUse) {
            if (V.get(numVertex) == null) throw new IllegalArgumentException("Graf o id "+hashCode()+" nie ma wierzchołka o numerze "+String.valueOf(numVertex));
        }
        return V.get(numVertex);
    }

    @Override
    public void addEdge(Vertex V1, Vertex V2) {
        V1.connectWith(V2); // jednocześnie połączy V2 z V1
    }

    @Override
    public void addEdge(int numV1, int numV2) {
        addEdge(getVertex(numV1), getVertex(numV2));
    }

    @Override
    public void removeVertex(Vertex V) {
        V.destroy();
        //this.V.remove(V.getNum()); Przeniesione do Vertex.destroy() (SimpleVertex.destroy())
    }

    @Override
    public void destroy() {
        removeAllVertices();
        V = null;
        E = null;
        StartV = null;
        EndV = null;
        //id = null //final
    }

    @Override
    public void removeAllVertices() {
        for (Integer numVertex : V.keySet()) {
            getVertex(numVertex).destroy();
        }
        V.clear();
        StartV.clear();
        EndV.clear();
        LastVertexNum = 0;
    }

    @Override
    public HashMap<Integer, Vertex> getV() {
        return V;
    }

    @Override
    public Edges getE() {
        return E;
    }

    @Override
    public HashSet<Vertex> getStartV() {
        return StartV;
    }

    @Override
    public HashSet<Vertex> getEndV() {
        return EndV;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TypicalUndirectedGraph)) return false;
        try {
            TypicalUndirectedGraph oo = ((TypicalUndirectedGraph)o);
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
        //String s = new String();
        StringBuilder sb = new StringBuilder();
        sb.append("Oto graf o id ");sb.append(id);sb.append(":\n");
        sb.append("V: {");
        for (Integer VerNum : V.keySet()) {
            //s += V.get(VerNum).toString();
            //s += ',';
            sb.append(V.get(VerNum).toString());
            sb.append(',');
        }
        sb.append("}\nE: {");
        for (Edge Edg : E) {
            //s += Edg.toString();
            //s += ',';
            sb.append(Edg.toString());
            sb.append(',');
        }
        //s += "}\nLista sąsiedztwa:\n[\n";
        sb.append("}\nLista sąsiedztwa:\n[\n");
        for (Integer VertexNum : V.keySet()) {
            //s += Vert.toString()+" : "+AdjacencyList.get(Vert).toString()+",\n";
            sb.append(V.get(VertexNum));sb.append(" : ");
            sb.append(V.get(VertexNum).toStringBuilder());
            sb.append('\n');
        }
        sb.append("]\n");
        sb.append("StartV: {");
        for (Vertex vertex : StartV) {
            sb.append(vertex.getNum());sb.append(',');
        }
        sb.append("}\nEndV: {");
        for (Vertex vertex : EndV) {
            sb.append(vertex.getNum());sb.append(',');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int getLastVertexNum() {
        return LastVertexNum;
    }

    @Override
    public void incrementLastVertexNum() {
        LastVertexNum++;
    }

    @Override
    public boolean getIsDirected() {
        //return isDirected;
        return false;
    }

    @Override
    public boolean VertexImplementsGetEdges() {
        return false; // SimpleVertex nie implementuje getEdges()
    }

    @Override
    public Edge getSampleEdge() {
        return SampleEdge;
    }
}
