package graph_v2;

import algorithm.BFS;

public class GraphTester {
    public static void main(String [] args) {
        TypicalUndirectedGraph TestGraph = new TypicalUndirectedGraph();
        TestGraph.addVertex();
        TestGraph.addVertex();
        TestGraph.addVertex();
        TestGraph.addVertex();
        TestGraph.addVertex();
        TestGraph.addVertex();
        TestGraph.addVertex();
        TestGraph.addVertex();
        TestGraph.addEdge(1,2);
        TestGraph.addEdge(3,1);
        TestGraph.addEdge(3,4);
        TestGraph.addEdge(2,4);
        TestGraph.addEdge(5,4);
        TestGraph.addEdge(5,6);
        TestGraph.addEdge(6,7);
        TestGraph.addEdge(7,8);
        TestGraph.addEdge(8,4);
        //Vertex startVertex = TestGraph.getVertex(1);
        //Vertex endVertex = TestGraph.getVertex(8);
        TestGraph.getVertex(1).setIsStartVertex(true);
        TestGraph.getVertex(8).setIsEndVertex(true);
        System.out.println(TestGraph);

        BFS bfsAlgorithm = new BFS();
        bfsAlgorithm.initialize(TestGraph);
        bfsAlgorithm.executeAlgorithm(TestGraph.getStartV().iterator().next(), TestGraph.getEndV().iterator().next());

        System.out.println("Znalezione rozwiązania: " + bfsAlgorithm.getSolutionAmount());
        System.out.println("Najkrótsza droga: " + bfsAlgorithm.getSolution());
        System.out.println("Długość najkrótszej drogi: " + bfsAlgorithm.getSolutionLength());
        for(int i=0; i<bfsAlgorithm.getSolutionAmount(); i++)
        {
            System.out.println("Droga " + i + ":" +  bfsAlgorithm.getSolution(i));
        }

        //TestGraph.destroy();
    }
}