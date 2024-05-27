import observer.MazeEventManager;
import observer.MazeEvent;
import observer.MazeEventListener;

public class Terminal implements MazeEventListener {
    // TODO - dodać obsługę wejścia dla terminala synchronizującą dane przy użyciu MazeEventManagera i MazeEventów
    // TODO - pozbyć się wszystkich System.out.printcośtam w innych klasach i odpowiednio obsługiwać je w tej klasie
    private MazeEventManager MEM;

    public Terminal(MazeEventManager MEM) {
        // MazeEventManager jest do aktualizowania stanu labiryntu przy użyciu MazeEventów za pomocą terminala
        // MazeEventManager.getMaze() jest tylko do wypisywania labiryntu w postaci tesktowej
        this.MEM = MEM;
        MEM.addEventListener(this);
    }
    public void actionPerformed(MazeEvent event) {
        // TODO - Jakoś to poprawić
        String str = event.getStatusMessage();
        if (str == null && event.getStatus() == MazeEvent.Status.OK) {
            switch (event.getType()) {
                case LOAD_BINARY_MAZE:
                case LOAD_TEXT_MAZE:
                case LOAD_MAZE:
                    System.out.println(MEM.getMaze()); // Wypisywanie labiryntu jest niewydajne!
                    break;
                default:
                    System.out.println("Terminal właśnie teraz powinien wypisać jakiś ciekawy komunikat.");
                    break;
            }
        }
        else if (str != null) System.out.println(str);
    }
}