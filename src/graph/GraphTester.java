package graph;

public class GraphTester {
    public static void main(String [] args) {
        TypicalUndirectedGraph<Vertex, Edge<Vertex>> TestGraph = new TypicalUndirectedGraph<Vertex, Edge<Vertex>>();
        Vertex V1 = new Vertex();Vertex V2 = new Vertex();
        TestGraph.addVertex(V1);
        TestGraph.addVertex(V2);
        System.out.println(TestGraph);
        //TestGraph.addEdge(new Edge<Vertex>(null, null));
        TestGraph.addEdge(new Edge<Vertex>(TestGraph.getVertex(1), TestGraph.getVertex(2)));
        System.out.println(TestGraph);
    }
}