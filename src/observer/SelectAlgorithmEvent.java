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
    private final String selectedAlgorithmString;

    public SelectAlgorithmEvent(MazeEventManager MEM, String selectedAlgorithmString) {
        super(MEM, Type.SELECT_ALGORITHM);
        this.selectedAlgorithmString = selectedAlgorithmString;
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }
    
    @Override
    protected void performAction() {
        super.performAction();
        if (MEM.PathfindingAlgorithms.get(selectedAlgorithmString) == null) {
            setStatus("SELECT_ALGORITHM_NO_SUCH_ALGORITHM");
            return;
        }
        MEM.selectedAlgorithmString = selectedAlgorithmString;
        setStatus("OK", "Wybrano algorytm "+selectedAlgorithmString);
    }

    public String getSelectedAlgorithmString() {
        return selectedAlgorithmString;
    }
}