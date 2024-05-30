// v1
package observer_v1;

public class MazeEvent {
    /*
     * //////////GET_MAZE_STRING  - bez argumentów
     * SELECT_ALGORITHM - int numerAlgorytmuWMazeEventManager
     * SOLVE_MAZE       - bez argumentów
     * LOAD_MAZE        - String ścieżkaDoPliku (zgaduje, jaki format ma labirynt)
     * LOAD_TEXT_MAZE   - String ścieżkaDoPliku
     * LOAD_BINARY_MAZE - String ścieżkaDoPliku
     * SET_FIELD_TYPE   - int kolumna, int wiersz, int typ (Field.type)
     */
    public static enum Type {
        //GET_MAZE_STRING,  // zwraca stringData[] = {Maze.toString() dla Maze w odpowiednim MazeEventManagerze}
        SELECT_ALGORITHM, // 
        SOLVE_MAZE,       // 
        LOAD_MAZE,        // po sprawdzeniu formatu wydarzenia zwraca intData[] = {liczbaKolumnLabiryntu, liczbaWierszyLabiryntu}
        LOAD_TEXT_MAZE,   // po sprawdzeniu formatu wydarzenia zwraca intData[] = {liczbaKolumnLabiryntu, liczbaWierszyLabiryntu}
        LOAD_BINARY_MAZE, // po sprawdzeniu formatu wydarzenia zwraca intData[] = {liczbaKolumnLabiryntu, liczbaWierszyLabiryntu}
        SET_FIELD_TYPE    // nic nie zwraca
    }
    // Pomysł statusu wydarzenia oparty jest na kodach odpowiedzi HTTP.
    public static enum Status {
        START,
        PROCESSING,
        OK,
        BAD_REQUEST,
        SOLVE_MAZE_NO_SOLUTION,
        LOAD_MAZE_OPENING_ERROR,
        LOAD_MAZE_FILE_OPENED,
        LOAD_MAZE_FORMAT_ERROR,
        LOAD_MAZE_FORMAT_VALIDATED,
        SET_FIELD_TYPE_INVALID_COORDS,
        IM_A_TEAPOT // https://en.wikipedia.org/wiki/HTTP_418
    }
    private final Type type;
    // intData i stringData mogą zmieniać się w trakcie realizacji wydarzenia (np. dla wydarzeń typu LOAD_MAZE)
    private int[] intData;
    private String[] stringData;
    private Status status = Status.START; // status ustawiany jest również przez MazeEventManager
    private String statusMessage = null; // statusMessage ustawiane jest przez MazeEventManager
    
    public MazeEvent(Type eventType) {
        type = eventType;
        intData = null;
        stringData = null;
    }
    public MazeEvent(Type eventType, int[] intData) {
        type = eventType;
        this.intData = intData;
        stringData = null;
        if (intData == null) throwException(-1);
        switch (type) {
            case SELECT_ALGORITHM:
                if (intData.length != 1) throwException(1);
            case SOLVE_MAZE:
                throwException(0);
            case LOAD_MAZE:
                throwException(-4);
                break;
            case LOAD_TEXT_MAZE:
                throwException(-4);
                break;
            case LOAD_BINARY_MAZE:
                throwException(-4);
                break;
            case SET_FIELD_TYPE:
            if (intData.length != 3) throwException(3);
                break;
            default:
                throwException(-5);
        }
    }
    public MazeEvent(Type eventType, String[] stringData) {
        type = eventType;
        intData = null;
        this.stringData = stringData;
        if (stringData == null || stringData[0] == null) throwException(-1);
        switch (type) {
            case SELECT_ALGORITHM:
                throwException(-3);
            case SOLVE_MAZE:
                throwException(0);
            case LOAD_MAZE:
                if (stringData.length != 1) throwException(1);
                break;
            case LOAD_TEXT_MAZE:
                if (stringData.length != 1) throwException(1);
                break;
            case LOAD_BINARY_MAZE:
                if (stringData.length != 1) throwException(1);
                break;
            case SET_FIELD_TYPE:
                throwException(-3);
                break;
            default:
                throwException(-5);
        }
    }
    public Type getType() {
        return type;
    }
    public int[] getIntData() {
        return intData;
    }
    public String[] getStringData() {
        return stringData;
    }
    protected void setStatus(Status newStatus) {
        status = newStatus;
        statusMessage = getDefaultStatusMessage(newStatus);
    }
    protected void setStatus(Status newStatus, String Message) {
        setStatus(newStatus);
        statusMessage = Message;
    }
    public static String getDefaultStatusMessage(Status status) {
        switch (status) {
            case BAD_REQUEST:
                return "Wysłano nieprawidłowe żądanie realizacji wydarzenia.";
            case SOLVE_MAZE_NO_SOLUTION:
                return "Podany labirynt nie ma jakiejkolwiek ścieżki od dowolnego Pola wejścia do dowolnego Pola wyjścia.";
            case LOAD_MAZE_OPENING_ERROR:
                return "Nie udało się otworzyć pliku z labiryntem.";
            case LOAD_MAZE_FILE_OPENED:
                return "Udało się otworzyć plik z labiryntem.";
            case LOAD_MAZE_FORMAT_ERROR:
                return null; // To zależy od błędu
            case LOAD_MAZE_FORMAT_VALIDATED:
                return "Podany plik z labiryntem ma prawidłowy format";
            case SET_FIELD_TYPE_INVALID_COORDS:
                return "Pole o podanych współrzędnych jest poza labiryntem.";
            case IM_A_TEAPOT:
                return null;
            default:
                return null;
        }
    }
    public Status getStatus() {
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
    private void throwException(int exceptionType) {
        status = Status.BAD_REQUEST;
        switch (exceptionType) {
            case -1:
                throw new IllegalArgumentException("Argumenty wydarzenia intData nie mogą być puste.");
            case -2:
                throw new IllegalArgumentException("Argumenty wydarzenia stringData nie mogą być puste.");
            case -3:
                throw new IllegalArgumentException("Typ wydarzenia "+type+" nie przyjmuje argumentów typu String.");
            case -4:
                throw new IllegalArgumentException("Typ wydarzenia "+type+" nie przyjmuje argumentów typu int.");
            case -5:
                throw new IllegalArgumentException("MazeEvent nie obsługuje wydarzenia typu "+type);
            //case -6:
            //    throw new UnsupportedOperationException("Aby prawidłowo użyć metody getStatusFailReason należy wcześniej sprawdzić, czy błąd wystąpił przy użyciu getStatus. Jednak jeśli szukasz błędu, to go masz :)");
            case 0:
                throw new IllegalArgumentException("Typ wydarzenia "+type+" nie przyjmuje dodatkowych argumentów.");
            default:
                if (exceptionType > 0) {
                    switch (exceptionType) {
                        case 1:
                            throw new IllegalArgumentException("Typ wydarzenia "+type+" przyjmuje dokładnie "+exceptionType+" dodatkowy argument.");
                        case 2:
                            throw new IllegalArgumentException("Typ wydarzenia "+type+" przyjmuje dokładnie "+exceptionType+" dodatkowe argumenty.");
                        case 3:
                            throw new IllegalArgumentException("Typ wydarzenia "+type+" przyjmuje dokładnie "+exceptionType+" dodatkowe argumenty.");
                        case 4:
                            throw new IllegalArgumentException("Typ wydarzenia "+type+" przyjmuje dokładnie "+exceptionType+" dodatkowe argumenty.");
                        default:
                            throw new IllegalArgumentException("Typ wydarzenia "+type+" przyjmuje dokładnie "+exceptionType+" dodatkowych argumentów.");
                    }
                } else {
                    status = Status.IM_A_TEAPOT;
                    throw new UnsupportedOperationException("Nieznany błąd.");
                }
        }
    }
}