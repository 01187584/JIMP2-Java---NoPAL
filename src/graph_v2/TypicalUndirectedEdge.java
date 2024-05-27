package graph_v2;

public class TypicalUndirectedEdge implements Edge {
    protected Vertex Start, End;

    protected TypicalUndirectedEdge() {

    }

    @Override
    public void setStart(Vertex V) {
        Start = V;
    }

    @Override
    public void setEnd(Vertex V) {
        End = V;
    }

    @Override
    public Vertex getStart() {
        return Start;
    }

    @Override
    public Vertex getEnd() {
        return End;
    }

    @Override
    public void destroy() {
        Start = null;End = null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TypicalUndirectedEdge)) return false;
        try {
            TypicalUndirectedEdge oo = ((TypicalUndirectedEdge)o);
            if (oo.Start != Start || oo.End != End) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        // Pewnie dałoby się lepiej
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Start == null) ? 0 : Start.hashCode());
        result = prime * result + ((End == null) ? 0 : End.hashCode());
        return result;
    }

    @Override
    public String toString() {
        if (Start == null || End == null) return "(Nieprawidłowa krawędź)";
        return '('+Start.toString()+','+End.toString()+')';
    }
}
