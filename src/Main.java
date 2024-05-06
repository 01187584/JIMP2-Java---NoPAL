import com.formdev.flatlaf.*;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        //System.out.println("Hello world!");
        Okno OPAL = new Okno();

        OPAL.wczytajGUI();
    }
}