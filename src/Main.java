import javax.swing.UIManager;

import com.formdev.flatlaf.*;

import observer.LoadMazeEvent;
import observer.MazeEventManager;
import observer.SelectAlgorithmEvent;

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
        MEM.notifyListeners(new SelectAlgorithmEvent(MEM, "BFS"));
        if (args.length > 1) MEM.notifyListeners(new LoadMazeEvent(MEM, args[0], args[1]));
        else if (args.length > 0) MEM.notifyListeners(new LoadMazeEvent(MEM, args[0]));
        else MEM.notifyListeners(new LoadMazeEvent(MEM, "maze-test.txt"));
        
        

        OPAL.wczytajGUI();
        new Terminal(MEM);
        
    }
}