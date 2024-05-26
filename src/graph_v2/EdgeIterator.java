package graph_v2;

import java.util.Iterator;

public class EdgeIterator implements Iterator<Edge> {
    private final Graph refGraph;
    private Edge E;
    private Iterator<Integer> OuterVertexIterator;
    private Iterator<Vertex> InnerVertexIterator;
    private Iterator<Edge> InnerEdgeIterator;
    private boolean VertexImplementsGetEdges;

    protected EdgeIterator(Graph G) {
        refGraph = G;
        E = G.getSampleEdge();
        OuterVertexIterator = refGraph.getV().keySet().iterator();
        VertexImplementsGetEdges = refGraph.VertexImplementsGetEdges();
        tryMakingInnerIterator();
    }

    @Override
    public boolean hasNext() {
        //return false;
        if (VertexImplementsGetEdges) {
            if (InnerEdgeIterator == null) return false;
            if (InnerEdgeIterator.hasNext()) return true;
            else return tryMakingInnerIterator();
        } else {
            if (InnerVertexIterator == null) return false;
            if (InnerVertexIterator.hasNext()) return true;
            else return tryMakingInnerIterator();
        }
    }

    @Override
    public Edge next() {
        //return E;
        if (VertexImplementsGetEdges) {
            return InnerEdgeIterator.next();
        } else {
            //System.out.println("efaefef");
            E.setEnd(InnerVertexIterator.next());
            //System.out.println(E);
            return E;
        }
    }
    
    private boolean tryMakingInnerIterator() {
        if (OuterVertexIterator.hasNext()) {
            if (VertexImplementsGetEdges) {
                InnerEdgeIterator = refGraph.getVertex(OuterVertexIterator.next()).getEdges().iterator();
                if (!InnerEdgeIterator.hasNext()) return false;
            } else {
                E.setStart(refGraph.getVertex(OuterVertexIterator.next()));
                InnerVertexIterator = E.getStart().getAdjacentVetices().iterator();
                if (!InnerVertexIterator.hasNext()) return false;
            }
        } else return false;
        return true;
    }
}
