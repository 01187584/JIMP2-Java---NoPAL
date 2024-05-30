// v2
package observer;

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
    protected final TextMazeReader TMR;
    protected final BinaryMazeReader BMR;
    protected final MazeReader[] MazeReaders;
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
    public void notifyListeners(MazeEvent event) {
        /*
         * Realizuje podane wydarzenie
         * i powiadamia swoich subskrybentów (nasłuchujących)
         * o realizacji wydarzenia
         * wysyła powiadomienie PRZED i PO realizacji wydarzenia
         * może wysyłać powiadomienia o statusie wydarzenia również W TRAKCIE realizacji (np. w przypadku wczytywania labiryntu)
         */
        notifyListenersInProgress(event); // przed
        event.performAction(); // w trakcie
        notifyListenersInProgress(event); // po
    }
    protected void notifyListenersInProgress(MazeEvent event) {
        for (MazeEventListener listener : listeners) {
            listener.actionPerformed(event);
        }
    }
    public Maze getMaze() {
        // Powinno być używane TYLKO do odczytu
        return M;
    }
}