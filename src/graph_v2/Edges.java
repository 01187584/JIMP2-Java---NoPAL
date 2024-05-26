package graph_v2;

import java.util.Iterator;

public abstract class Edges implements Iterable<Edge> {
    protected final Graph refGraph;

    protected Edges(Graph G) {
        refGraph = G;
    }
    public abstract Iterator<Edge> iterator();
}
