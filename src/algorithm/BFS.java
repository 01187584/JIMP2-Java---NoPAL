package algorithm;

import graph_v2.Graph;
import graph_v2.Vertex;

import java.util.*;

public class BFS implements PathfindingAlgorithm {
    // Ta implementacja jest poprawna, ale niestety niewydajna:
    // używa często ArrayList.contains, które działa w czasie liniowym od całego rozmiaru tablicy i 
    // potencjalnie alokuje dużo pamięci na rozgałęziające się ścieżki
    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
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
    public ArrayList<ArrayList<Vertex>> executeAlgorithm() {
        ArrayList<ArrayList<Vertex>> results = new ArrayList<>();
        if (graph == null) {
            throw new InvalidGraphInvocation("Błędne wywołanie algorytmu BFS.");
        }

        Queue<List<Vertex>> queue = new LinkedList<>();
        
        for (Vertex StartVertex : graph.getStartV()) {
            List<Vertex> initialPath = new ArrayList<>();
            initialPath.add(StartVertex);
            queue.add(initialPath);
        }
        
        boolean foundExit = false;
        while (!queue.isEmpty()) {
            List<Vertex> path = queue.poll();
            //Vertex current = path.getLast(); Od JDK 21
            Vertex current = getLast(path); // Mam JDK 20
            for (Vertex EndVertex : graph.getEndV()) {
                if (current.equals(EndVertex)) {
                    results.add(new ArrayList<>(path));
                    if (shortestPath.isEmpty() || path.size() < shortestPath.size()) {
                        shortestPath = new ArrayList<>(path);
                    }
                    foundExit = true;
                }
            }
            if (foundExit) {
                foundExit = false;continue;
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

    private Vertex getLast(List<Vertex> list) {
        return list.get(list.size()-1);
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
    public StringBuilder shortestSolutionToStringBuilder() {
        int temp = 0;
        int kroki = 0;
        Direction obecnyKierunek = null;
        Direction nowyKierunek = null;
        StringBuilder directionList = new StringBuilder("START\n");

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            temp = shortestPath.get(i + 1).getNum() - shortestPath.get(i).getNum();

            if (temp == 1) {
                nowyKierunek = Direction.RIGHT;
            } else if (temp == -1) {
                nowyKierunek = Direction.LEFT;
            } else if (temp > 1) {
                nowyKierunek = Direction.DOWN;
            } else if (temp < -1) {
                nowyKierunek = Direction.UP;
            }

            if (obecnyKierunek == null || obecnyKierunek == nowyKierunek) {
                kroki++;
            } else {
                directionList.append("FORWARD ").append(kroki).append('\n');
                directionList.append(turnDirection(obecnyKierunek, nowyKierunek)).append('\n');
                kroki = 1;
            }
            obecnyKierunek = nowyKierunek;
        }
        directionList.append("FORWARD ").append(kroki).append('\n').append("STOP");
        return directionList;
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
    private String turnDirection(Direction oldDirection, Direction newDirection) {
        switch (oldDirection) {
            case UP:
                switch (newDirection) {
                    case LEFT:
                        return "TURN LEFT";
                    case RIGHT:
                        return "TURN RIGHT";
                }
                break;
            case DOWN:
                switch (newDirection) {
                    case LEFT:
                        return "TURN RIGHT";
                    case RIGHT:
                        return "TURN LEFT";
                }
                break;
            case LEFT:
                switch (newDirection) {
                    case UP:
                        return "TURN RIGHT";
                    case DOWN:
                        return "TURN LEFT";
                }
                break;
            case RIGHT:
                switch (newDirection) {
                    case UP:
                        return "TURN LEFT";
                    case DOWN:
                        return "TURN RIGHT";
                }
                break;
        }
        throw new RuntimeException("Coś poszło nie tak.");
    }

}
