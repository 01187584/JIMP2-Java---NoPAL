import javax.swing.UIManager;

import com.formdev.flatlaf.*;

import observer.LoadMazeEvent;
import observer.MazeEventManager;
import observer.SelectAlgorithmEvent;
import observer.SolveMazeEvent;

public class Main {
    public static void main(String[] args) {
        for (String string : args) {
            System.out.println(string);
        }
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        MazeEventManager MEM = new MazeEventManager();
        //System.out.println("Hello world!");
        Okno OPAL = new Okno(MEM);
        Terminal CLI = new Terminal(MEM);
        // Wczytanie przykładowego labiryntu
        MEM.notifyListeners(new SelectAlgorithmEvent(MEM, "BFS"));
        if (args.length > 0) {
            if (args.length > 1) MEM.notifyListeners(new LoadMazeEvent(MEM, args[0], args[1]));
            else if (args.length > 0) MEM.notifyListeners(new LoadMazeEvent(MEM, args[0]));
            MEM.notifyListeners(new SolveMazeEvent(MEM));
        }
        else MEM.notifyListeners(new LoadMazeEvent(MEM, "maze-test.txt"));
        
        

        OPAL.wczytajGUI();
        CLI.start();
        
    }
}