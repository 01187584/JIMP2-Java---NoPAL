import javax.swing.UIManager;

import com.formdev.flatlaf.*;

import observer.LoadMazeEvent;
import observer.MazeEventManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        MazeEventManager MEM = new MazeEventManager();
        //System.out.println("Hello world!");
        Okno OPAL = new Okno(MEM);
        // Wczytanie przykładowego labiryntu
        MEM.notifyListeners(new LoadMazeEvent(MEM, "maze-test.txt"));

        OPAL.wczytajGUI();
        new Terminal(MEM);
    }
}