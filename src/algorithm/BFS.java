package algorithm;

import graph_v2.Graph;
import graph_v2.Vertex;

import java.util.*;

public class BFS implements PathfindingAlgorithm {
    private Graph graph;
    private ArrayList<ArrayList<Vertex>> allPaths;
    private List<Vertex> shortestPath;

    @Override
    public void initialize(Graph g) {
        this.graph = g;
        this.allPaths = new ArrayList<ArrayList<Vertex>>();
        this.shortestPath = new ArrayList<>();
    }

    @Override
    public ArrayList<ArrayList<Vertex>> executeAlgorithm(Vertex start, Vertex end) {
        ArrayList<ArrayList<Vertex>> results = new ArrayList<>();
        if (graph == null || start == null || end == null) {
            throw new InvalidSolutionNumberException("Błędne wywołanie algorytmu BFS.");
        }

        Queue<List<Vertex>> queue = new LinkedList<>();
        List<Vertex> initialPath = new ArrayList<>();
        initialPath.add(start);
        queue.add(initialPath);

        while (!queue.isEmpty()) {
            List<Vertex> path = queue.poll();
            Vertex current = path.getLast();

            if (current.equals(end)) {
                results.add(new ArrayList<>(path));
                if (shortestPath.isEmpty() || path.size() < shortestPath.size()) {
                    shortestPath = new ArrayList<>(path);
                }
                continue;
            }

            for (Vertex neighbor : current.getAdjacentVetices()) {
                if (!path.contains(neighbor)) {
                    List<Vertex> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        this.allPaths = results;
        return results;
    }

    @Override
    public int getSolutionAmount() {
        return allPaths.size();
    }

    @Override
    public ArrayList<Vertex> getSolution() {
        return new ArrayList<>(shortestPath);
    }

    @Override
    public ArrayList<Vertex> getSolution(int solutionNumber) {
        if (solutionNumber < 0 || solutionNumber >= allPaths.size()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(allPaths.get(solutionNumber));
    }

    @Override
    public int getSolutionLength() {
        return shortestPath.size();
    }

    @Override
    public int getSolutionLength(int solutionNumber) {
        if (solutionNumber < 0 || solutionNumber >= allPaths.size()) {
            throw new InvalidSolutionNumberException("Nie ma rozwięzania o numerze: " + solutionNumber);
        }
        return allPaths.get(solutionNumber).size();
    }
    public static class InvalidSolutionNumberException extends RuntimeException {
        public InvalidSolutionNumberException(String message) {
            super(message);
        }
    }
    public static class InvalidGraphInvocation extends RuntimeException {
        public InvalidGraphInvocation(String message) {
            super(message);
        }
    }


}
