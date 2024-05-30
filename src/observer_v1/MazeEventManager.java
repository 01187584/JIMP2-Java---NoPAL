// v1
package observer_v1;

import java.util.HashSet;

import maze.Maze;
import maze.MazeReader;
import maze.TextMazeReader;
import maze.BinaryMazeReader;
import algorithm.*;

// Zarządza labiryntem (swoim obiektem klasy Maze)
// Zarządza obiektami klas dziedziczących po MazeReader
// Do notifyListeners powinny być wysyłane wydarzenia PRZED ich realizacją
// Wysyła wydarzenia PO ich realizacji (bądź próbie realizacji)
public class MazeEventManager {
    private final Maze M;
    private final TextMazeReader TMR;
    private final BinaryMazeReader BMR;
    private final MazeReader[] MazeReaders;
    private HashSet<MazeEventListener> listeners;

    public MazeEventManager() {
        this(new Maze(10, 10)); // Tu mogłaby być dowolna liczba wierszy i kolumn
    }
    public MazeEventManager(Maze M) {
        this.M = M;
        listeners = new HashSet<MazeEventListener>();
        TMR = new TextMazeReader(M);
        BMR = new BinaryMazeReader(M);
        MazeReaders = new MazeReader[] {TMR, BMR};
    }
    public void addEventListener(MazeEventListener listener) {
        listeners.add(listener);
    }
    public void removeEventListener(MazeEventListener listener) {
        listeners.remove(listener);
    }
    private boolean tryLoadingMaze(MazeEvent event, MazeReader MR, boolean notifyProgress) {
        if (!MR.open(event.getStringData()[0])) {
            event.setStatus(MazeEvent.Status.LOAD_MAZE_OPENING_ERROR);
            if (notifyProgress) notifyListenersInProgress(event);
            return false;
        }
        event.setStatus(MazeEvent.Status.LOAD_MAZE_FILE_OPENED);
        if (notifyProgress) notifyListenersInProgress(event);
        if (!MR.validateFormat()) {
            event.setStatus(MazeEvent.Status.LOAD_MAZE_FORMAT_ERROR, MR.getFormatErrorMsg());
            if (notifyProgress) notifyListenersInProgress(event);
            return false;
        }
        event.setStatus(MazeEvent.Status.LOAD_MAZE_FORMAT_VALIDATED);
        notifyListenersInProgress(event); // Jeśli format jest prawidłowy, to zawsze powiadom
        MR.read();
        MR.close();
        event.setStatus(MazeEvent.Status.OK);
        return true;
    }
    private boolean tryLoadingMaze(MazeEvent event) {
        for (MazeReader MR : MazeReaders) {
            if (tryLoadingMaze(event, MR, false)) {
                return true;
            }
        }
        return false;
    }
    public void notifyListeners(MazeEvent event) {
        /*
         * Realizuje podane wydarzenie
         * i powiadamia swoich subskrybentów (nasłuchujących)
         * o realizacji wydarzenia
         * wysyła powiadomienie PRZED i PO realizacji wydarzenia
         * może wysyłać powiadomienia o statusie wydarzenia również W TRAKCIE realizacji (np. w przypadku wczytywania labiryntu)
         */
        notifyListenersInProgress(event); // przed
        performingAction(event); // w trakcie
        notifyListenersInProgress(event); // po
    }
    private void performingAction(MazeEvent event) {
        event.setStatus(MazeEvent.Status.PROCESSING);
        switch (event.getType()) {
            case SELECT_ALGORITHM:
                break;
            case SOLVE_MAZE:
                break;
            case LOAD_MAZE:
                tryLoadingMaze(event);
                break;
            case LOAD_TEXT_MAZE:
                tryLoadingMaze(event, TMR, true);
                break;
            case LOAD_BINARY_MAZE:
                tryLoadingMaze(event, BMR, true);
                break;
            case SET_FIELD_TYPE:
                try {
                    M.getField(event.getIntData()[0], event.getIntData()[1]).setFieldType(event.getIntData()[2]);
                    event.setStatus(MazeEvent.Status.OK);
                } catch (Exception e) {
                    event.setStatus(MazeEvent.Status.SET_FIELD_TYPE_INVALID_COORDS);
                }
                break;
            default:
                throw new UnsupportedOperationException("MazeEventManager nie potrafi obsłużyć wydarzeń podanego typu: "+event.getType());
        }
    }
    private void notifyListenersInProgress(MazeEvent event) {
        for (MazeEventListener listener : listeners) {
            listener.actionPerformed(event);
        }
    }
    public Maze getMaze() {
        // Powinno być używane TYLKO do odczytu
        return M;
    }
}