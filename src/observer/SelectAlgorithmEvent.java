// v2
package observer;

// TODO: obsłużyć tutaj interfejs algorithm.PathfindingAlgorithm i jego algorytmy
public class SelectAlgorithmEvent extends MazeEvent {
    public static final EventStatusSet Statuses = new EventStatusSet(MazeEvent.Statuses);
    static {
        Statuses.put("START", "Wybieranie algorytmu...");
        Statuses.put("PROCESSING", "Wybieranie algorytmu...");
        Statuses.put("OK", null);

        Statuses.put("SELECT_ALGORITHM_NO_SUCH_ALGORITHM", "Aplikacja nie wspiera podanego algorytmu lub podano błędną nazwę algorytmu.", true);
    }


    public SelectAlgorithmEvent(MazeEventManager MEM) {
        super(MEM, Type.SELECT_ALGORITHM);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }
    
}