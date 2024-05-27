package algorithm;

import graph_v2.Graph;
import graph_v2.Vertex;
import java.util.List;

public interface PathfindingAlgorithm {
    void initialize(Graph g);
    List<List<Vertex>> executeAlgorithm(Vertex start, Vertex end);
    boolean equals(Object o);
    int hashCode();
    int getSolutionAmount();
    List<Vertex> getSolution();
    List<Vertex> getSolution(int solutionNumber);
    int getSolutionLength();
    int getSolutionLength(int solutionNumber);



    
}