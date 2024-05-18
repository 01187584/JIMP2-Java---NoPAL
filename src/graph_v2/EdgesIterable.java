package graph_v2;

import java.util.Iterator;

public class EdgesIterable extends Edges {
    private final Graph refGraph;

    protected EdgesIterable(Graph G) {
        refGraph = G;
    }

    @Override
    public Iterator<Edge> iterator() {
        return new EdgeIterator(refGraph);
    }
    
}
