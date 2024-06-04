package algorithm;

import graph_v2.Graph;
import graph_v2.Vertex;

import java.util.ArrayList;
import java.util.List;

public interface PathfindingAlgorithm {
    void initialize(Graph g);
    ArrayList<ArrayList<Vertex>> executeAlgorithm(Vertex start, Vertex end);
    int getSolutionAmount();
    ArrayList<Vertex> getSolution();
    ArrayList<Vertex> getSolution(int solutionNumber);
    int getSolutionLength();
    int getSolutionLength(int solutionNumber);
    StringBuilder shortestSolutionToStringBuilder();
}