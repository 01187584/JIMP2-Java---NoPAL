package graph_v2;

import java.util.Iterator;

public class EdgesIterable extends Edges {

    protected EdgesIterable(Graph G) {
        super(G);
    }

    @Override
    public Iterator<Edge> iterator() {
        return new EdgeIterator(refGraph);
    }
    
}
