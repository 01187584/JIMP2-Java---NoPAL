// v2
package observer;

public abstract class MazeEvent {
    /*
     * //////////GET_MAZE_STRING  - bez argumentów
     * SELECT_ALGORITHM
     * SOLVE_MAZE
     * LOAD_MAZE
     * SET_FIELD_TYPE
     */
    public static enum Type {
        //GET_MAZE_STRING,  // zwraca stringData[] = {Maze.toString() dla Maze w odpowiednim MazeEventManagerze}
        SELECT_ALGORITHM, // TODO
        SOLVE_MAZE,       // TODO
        LOAD_MAZE,
        SAVE_MAZE,        // TODO
        SET_FIELD_TYPE,   // nic nie zwraca
        SAVE_PATH
    }
    private static boolean CHECK_CORRECT_USE = true;
    protected final MazeEventManager MEM;
    // Pomysł statusu wydarzenia oparty jest na kodach odpowiedzi HTTP.
    public static final EventStatusSet Statuses = new EventStatusSet();
    static {
        Statuses.put("START", null, false);
        Statuses.put("PROCESSING", null, false);
        Statuses.put("OK", null, true);
        Statuses.put("BAD_REQUEST","Wysłano nieprawidłowe żądanie realizacji wydarzenia.", true);
    }
    private final Type type;
    // intData i stringData mogą zmieniać się w trakcie realizacji wydarzenia (np. dla wydarzeń typu LOAD_MAZE)
    //private int[] intData;
    //private String[] stringData;
    private String status = "START"; // status ustawiany jest również przez MazeEventManager
    private String statusMessage = null; // statusMessage ustawiane jest przez MazeEventManager
    private boolean statusIsFinal = false;
    
    //public MazeEvent() {
        //type = eventType;
        //intData = null;
        //stringData = null;
    //}
    public MazeEvent(MazeEventManager MEM, Type type) {
        if (MEM == null) throw new NullPointerException("MazeEventManager nie może być nullem - każde wydarzenie musi być przypisane do swojego MazeEventManagera.");
        if (type == null) throw new NullPointerException("type nie może być nullem - każde wydarzenie musi być jakegoś typu.");
        this.MEM = MEM;
        this.type = type;
        //MEM.notifyListeners(this);
        //throw new UnsupportedOperationException("Każde wydarzenie dziedziczące po MazeEvent powinno przykrywać konstruktor MazeEvent(MazeEventManager), a najwyraźniej tak nie jest.");
    }
    protected void performAction() {
        // Wywoływane przez MazeEventManagera przy użyciu MazeEventManager.notifyListeners(this)
        if (!status.equals("START")) throw new UnsupportedOperationException("Wydarzenie już jest w trakcie wykonywania.");
        setStatus("PROCESSING");
    }
    public Type getType() {
        return type;
    }
    public abstract void setStatus(String newStatus);
    protected void setStatus(EventStatusSet Statuses, String newStatus) {
        if (CHECK_CORRECT_USE) {
            if (!Statuses.contains(newStatus)) throw new IllegalArgumentException("Nieprawidłowy status wydarzenia: "+newStatus);
        }
        status = newStatus;
        statusMessage = Statuses.get(newStatus).defaultMessage;
        statusIsFinal = Statuses.get(newStatus).isFinal;
    }
    protected void setStatus(String newStatus, String Message) {
        setStatus(newStatus);
        statusMessage = Message;
    }
    public String getStatus() {
        // Zwraca aktualny status wydarzenia
        // status pozwala stwierdzić czy wystąpił błąd w trakcie realizacji wydarzenia
        // START, PROCESSING, OK - brak błędu
        // OK - wydarzenie zakończyło się sukcesem
        return status;
    }
    public String getStatusMessage() {
        // Zwraca komunikat, który wystąpił w trakcie realizacji wydarzenia
        // po sprawdzeniu, czy wysłano prawidłowe żądanie realizacji wydarzenia
        //if (status == Status.OK) throwException(-6);
        return statusMessage;
    }
    public boolean isCurrentStatusFinal() {
        return statusIsFinal;
    }
}