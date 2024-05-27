package observer;

import java.util.HashSet;
import java.util.InputMismatchException;

import maze.Maze;
import maze.MazeReader;
import maze.TextMazeReader;
import maze.BinaryMazeReader;

// Zarządza labiryntem (swoim obiektem klasy Maze)
// Zarządza obiektami klas dziedziczących po MazeReader
// Do notifyListeners powinny być wysyłane wydarzenia PRZED ich realizacją
// Wysyła wydarzenia PO ich realizacji (bądź próbie realizacji)
public class MazeEventManager {
    private final Maze M;
    private final MazeReader[] MazeReaders;
    private final TextMazeReader TMR;
    private final BinaryMazeReader BMR;
    private HashSet<MazeEventListener> listeners;

    public MazeEventManager() {
        this(new Maze(10, 10)); // Tu mogłaby być dowolna liczba wierszy i kolumn
    }
    public MazeEventManager(Maze M) {
        this.M = M;
        TMR = new TextMazeReader(M);
        BMR = new BinaryMazeReader(M);
        MazeReaders = new MazeReader[2];
        MazeReaders[0] = TMR;MazeReaders[1] = BMR;
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
            if (notifyProgress) notifyListeners(event);
            return false;
        }
        event.setStatus(MazeEvent.Status.LOAD_MAZE_FILE_OPENED);
        if (notifyProgress) notifyListeners(event);
        if (!MR.validateFormat()) {
            event.setStatus(MazeEvent.Status.LOAD_MAZE_FORMAT_ERROR, MR.getFormatErrorMsg());
            if (notifyProgress) notifyListeners(event);
            return false;
        }
        event.setStatus(MazeEvent.Status.LOAD_MAZE_FORMAT_VALIDATED);
        notifyListeners(event); // Jeśli format jest prawidłowy, to zawsze powiadom
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
            case MazeEvent.Type.SELECT_ALGORITHM:
                // TODO: ustalić interfejs algorithm.PathfindingAlgorithm i go tutaj obsłużyć
                break;
            case MazeEvent.Type.SOLVE_MAZE:
                // TODO: ustalić interfejs algorithm.PathfindingAlgorithm i go tutaj obsłużyć
                break;
            case MazeEvent.Type.LOAD_MAZE:
                tryLoadingMaze(event);
            case MazeEvent.Type.LOAD_TEXT_MAZE:
                tryLoadingMaze(event, TMR, true);
            case MazeEvent.Type.LOAD_BINARY_MAZE:
                tryLoadingMaze(event, BMR, true);
            case MazeEvent.Type.SET_FIELD_TYPE:
                try {
                    // TODO: obsłużyć błąd - można podać nieprawidłowy typ Pola
                    M.getField(event.getIntData()[0], event.getIntData()[1]).setFieldType(event.getIntData()[2]);
                    event.setStatus(MazeEvent.Status.OK);
                } catch (Exception e) {
                    event.setStatus(MazeEvent.Status.SET_FIELD_TYPE_INVALID_COORDS);
                }
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