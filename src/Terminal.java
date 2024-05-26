import observer.MazeEventManager;
import observer.MazeEvent;
import observer.MazeEventListener;

public class Terminal implements MazeEventListener {
    // TODO - dodać obsługę wejścia dla terminala synchronizującą dane przy użyciu MazeEventManagera i MazeEventów
    private MazeEventManager MEM;

    public Terminal(MazeEventManager MEM) {
        // MazeEventManager jest do aktualizowania stanu labiryntu przy użyciu MazeEventów za pomocą terminala
        // MazeEventManager.getMaze() jest tylko do wypisywania labiryntu w postaci tesktowej
        this.MEM = MEM;
        MEM.addEventListener(this);
    }
    public void actionPerformed(MazeEvent event) {
        // TODO - Jakoś to poprawić
        System.out.println(event.getStatusMessage());
    }
}