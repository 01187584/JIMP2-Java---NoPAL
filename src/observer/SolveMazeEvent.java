// v2
package observer;

import java.util.ArrayList;

import algorithm.PathfindingAlgorithm;
import graph_v2.Vertex;
import maze.Field;

public class SolveMazeEvent extends MazeEvent {
    public static final EventStatusSet Statuses = new EventStatusSet(MazeEvent.Statuses);
    static {
        //Statuses.put("START", "Uruchamianie algorytmu rozwiązującego...");
        Statuses.put("START", "Trwa rozwiązywanie labiryntu...");
        Statuses.put("PROCESSING", "Trwa rozwiązywanie labiryntu...");
        Statuses.put("OK", null);

        //Statuses.put("SOLVE_MAZE_NO_ENTRANCE", "Podany labirynt nie ma jakiegokolwiek Pola wejścia.", true);
        //Statuses.put("SOLVE_MAZE_NO_EXIT", "Podany labirynt nie ma jakiegokolwiek Pola wyjścia.", true);
        Statuses.put("SOLVE_MAZE_NO_ENTRANCE", "Podany labirynt nie ma Pola wejścia.", true);
        Statuses.put("SOLVE_MAZE_NO_EXIT", "Podany labirynt nie ma Pola wyjścia.", true);
        Statuses.put("SOLVE_MAZE_NO_SOLUTION", "Podany labirynt nie ma jakiejkolwiek ścieżki od dowolnego Pola wejścia do dowolnego Pola wyjścia.", true);
    }
    private final PathfindingAlgorithm selectedAlgorithm;
    private String shortestSolutionString;

    public SolveMazeEvent(MazeEventManager MEM) {
        super(MEM, Type.SOLVE_MAZE);
        if (MEM.selectedAlgorithmString == null) throw new RuntimeException("Algorytm powinien już być wcześniej wybrany poprzez użycie wydarzenia SelectAlgorithmEvent.");
        selectedAlgorithm = MEM.PathfindingAlgorithms.get(MEM.selectedAlgorithmString);
        if (selectedAlgorithm == null) throw new RuntimeException("Wydarzenie SelectAlgorithmEvent działa niepoprawnie.");
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }

    public String getShortestSolutionString() {
        if (shortestSolutionString == null) throw new NullPointerException("shortestSolutionString jest null!");
        return shortestSolutionString;
    }

    @Override
    protected void performAction() {
        super.performAction();
        if (MEM.getMaze().getStartV().isEmpty()) {
            setStatus("SOLVE_MAZE_NO_ENTRANCE");return;
        }
        if (MEM.getMaze().getEndV().isEmpty()) {
            setStatus("SOLVE_MAZE_NO_EXIT");return;
        }
        selectedAlgorithm.initialize(MEM.getMaze()); // Aktualizacja zmian w labiryncie
        //selectedAlgorithm.executeAlgorithm(MEM.getMaze().getStartV().iterator().next(), MEM.getMaze().getEndV().iterator().next());
        selectedAlgorithm.executeAlgorithm();
        shortestSolutionString = selectedAlgorithm.shortestSolutionToStringBuilder().toString();
        //System.out.println(MEM.getMaze().getStartV().iterator().next());
        //System.out.println(MEM.getMaze().getEndV().iterator().next());
        MEM.getMaze().setIsSolved(true);
        // TODO: zmienić getSolutionAmount na getNumSolutions
        if (selectedAlgorithm.getSolutionAmount() == 0) {
            setStatus("SOLVE_MAZE_NO_SOLUTION");return;
        }
        for (Vertex v : selectedAlgorithm.getSolution()) {
            // TODO: poprawić to, bo to dziwne
            ((Field)v).setIsPartOfPath(true);
        }
        // TODO sprecyzować liczbę znalezionych ścieżek lub to usunąć
        setStatus("OK", "Znaleziono najkrótszą ścieżkę od wejścia do wyjścia");
    }

    // TODO: zamienić to w jakiś sposób na możliwość zwrócenia PathfindingAlgorithm w wersji tylko do odczytu danych 
    ArrayList<Vertex> getSolution() {
        return selectedAlgorithm.getSolution();
    }
}