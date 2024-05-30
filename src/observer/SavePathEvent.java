// v2
package observer;

// TODO: obsłużyć tutaj interfejs algorithm.PathfindingAlgorithm i jego algorytmy
public class SavePathEvent extends MazeEvent {
    public static final EventStatusSet Statuses = new EventStatusSet(MazeEvent.Statuses);
    static {
        Statuses.put("START", "Uruchamianie algorytmu rozwiązującego...");
        Statuses.put("PROCESSING", "Trwa rozwiązywanie labiryntu...");
        Statuses.put("OK", null);

        Statuses.put("SAVE_PATH_MAZE_NOT_SOLVED", "Aby można było zapisać rozwiązanie (ścieżkę w formacie tekstowym) do pliku należy najpierw je znaleźć.", true);
        Statuses.put("SAVE_PATH_NO_SUCH_PATH", "Podano nieprawidłowy numer ścieżki.", true);
    }


    public SavePathEvent(MazeEventManager MEM) {
        super(MEM, Type.SOLVE_MAZE);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }
    
}