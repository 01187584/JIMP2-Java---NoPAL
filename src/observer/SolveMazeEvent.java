// v2
package observer;

// TODO: obsłużyć tutaj interfejs algorithm.PathfindingAlgorithm i jego algorytmy
public class SolveMazeEvent extends MazeEvent {
    public static final EventStatusSet Statuses = new EventStatusSet(MazeEvent.Statuses);
    static {
        Statuses.put("START", "Uruchamianie algorytmu rozwiązującego...");
        Statuses.put("PROCESSING", "Trwa rozwiązywanie labiryntu...");
        Statuses.put("OK", null);

        Statuses.put("SOLVE_MAZE_NO_SOLUTION", "Podany labirynt nie ma jakiejkolwiek ścieżki od dowolnego Pola wejścia do dowolnego Pola wyjścia.", true);
    }


    public SolveMazeEvent(MazeEventManager MEM) {
        super(MEM, Type.SOLVE_MAZE);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }
    
}