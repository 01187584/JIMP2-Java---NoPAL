package graph_v2;

public class GraphTester {
    public static void main(String [] args) {
        //TypicalUndirectedGraph<Vertex, Edge<Vertex>> TestGraph = new TypicalUndirectedGraph<Vertex, Edge<Vertex>>(Vertex.class, (Class<Edge<Vertex>>)(Class<?>)Edge.class);
        TypicalUndirectedGraph TestGraph = new TypicalUndirectedGraph();
        //Vertex V1 = new Vertex();Vertex V2 = new Vertex();
        //TestGraph.test();
        //TestGraph.addVertex(V1);
        //TestGraph.addVertex(V2);
        TestGraph.addVertex();TestGraph.addVertex();
        System.out.println(TestGraph);
        //TestGraph.addEdge(new Edge<Vertex>(null, null));
        //TestGraph.addEdge(new Edge<Vertex>(TestGraph.getVertex(1), TestGraph.getVertex(2)));
        //TestGraph.addEdge(TestGraph.getVertex(1), TestGraph.getVertex(2));
        //TestGraph.addVertices(10);
        TestGraph.addEdge(1,2);
        System.out.println(TestGraph);
        TestGraph.destroy();
        System.out.println(TestGraph);
    }
}