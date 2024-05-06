import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;

import java.io.File;
import javax.swing.*;
import java.awt.*;
//import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.io.*; // printf

import java.awt.*;
import java.util.List;
import java.util.*;

public final class Okno {
    private final JFrame GUI;
    private final JPanel tenPanel;
    private final Dimension stdRozmPrzycisku = new Dimension(230, 116);
    private PodgladLabiryntu PL;
    public Okno() {
        GUI = new JFrame("WyPAL"); // Wydajny Program Analizujący Labirynt
        //GUI.setSize(1280,800);
        GUI.setPreferredSize(new Dimension(1280,820));
        GUI.setMinimumSize(new Dimension(820,820)); // Aby przyciski się zawsze mieściły
        //GUI.setPreferredSize(new Dimension(1280,800));
        //GUI.setLayout(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //GUI.setResizable(false);
        GUI.setLocationRelativeTo(null);
        //PL = new PodgladLabiryntu(26+200, 21, 1280-26-200, 820-21, 10, 10);
        PL = new PodgladLabiryntu(0, 0, 1260-1-stdRozmPrzycisku.width, 761-1, 1000, 1000);
        //PL = new PodgladLabiryntu(0, 0, 1023, 754, 10, 10);
        GUI.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //System.out.printf("(%d, %d)\n",GUI.getBounds().width,GUI.getBounds().height);
                PL.setMaxDimensions(GUI.getBounds().width-21-stdRozmPrzycisku.width,GUI.getBounds().height-60);
            }
        });
        JScrollPane ScrollPL = new JScrollPane(PL);
        PL.setSize(1280-26-200,820-21);
        //GUI.add(ScrollPL);
        tenPanel = new JPanel();
        GUI.setLayout(new BorderLayout());
        FlowLayout layout = new FlowLayout();
        layout.setHgap(10);              
        layout.setVgap(10);
        tenPanel.setLayout(layout);
        tenPanel.setPreferredSize(new Dimension(stdRozmPrzycisku.width+10,820));
        this.dodajPrzyciski();
        GUI.add(ScrollPL, BorderLayout.CENTER);
        GUI.add(tenPanel, BorderLayout.WEST);
        GUI.pack();
    }


    private void dodajPrzyciskLista()
    {
        JButton oknoLista = new JButton("Pokaż listę kroków");
        //oknoLista.setBounds(26, 21,200,103 );
        oknoLista.setPreferredSize(stdRozmPrzycisku);
        //GUI.add(oknoLista);
        tenPanel.add(oknoLista);
        oknoLista.addActionListener(l -> {
            zapisListy();
        });
    }

    private void zapisListy()
    {
        JDialog selectOutputType = new JDialog(GUI, "Lista kroków");
        selectOutputType.setSize(400,400);
        selectOutputType.setLocationRelativeTo(null);
        selectOutputType.setResizable(false);
        selectOutputType.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton fileButton = new JButton("Stwórz plik");
        selectOutputType.add(fileButton);
        // Chcę zrobić guzik do utworzenia pliku tekstowego na dole i listę na środku,
        // ale na razie są problemy.
        fileButton.setVisible(true);
        selectOutputType.setVisible(true);
    }

    private void dodajPrzyciskWybierzPlikTXT()
    {
        JButton PrzyciskWybierzPlikTXT = new JButton("Wybierz plik tekstowy");
        //PrzyciskWybierzPlikTXT.setBounds(26, 145,200,103 );
        PrzyciskWybierzPlikTXT.setPreferredSize(stdRozmPrzycisku);
        PrzyciskWybierzPlikTXT.addActionListener(l -> poleWyboruTXT());
        //GUI.add(PrzyciskWybierzPlikTXT);
        tenPanel.add(PrzyciskWybierzPlikTXT);
    }

    private void poleWyboruTXT()
    {
        JFileChooser wyborTXT = new JFileChooser(FileSystemView.getFileSystemView());
        FileNameExtensionFilter typ = new FileNameExtensionFilter("Pliki tekstowe", "txt");
        wyborTXT.setFileFilter(typ);
        int r = wyborTXT.showOpenDialog(GUI);
        if (r == JFileChooser.APPROVE_OPTION)
        {
            System.out.println(wyborTXT.getSelectedFile().getAbsolutePath());
        }
        else
        {
            System.out.println("Brak");
        }

    }

    private void dodajPrzyciskWybierzPlikBIN()
    {
        JButton PrzyciskWybierzPlikBIN = new JButton("Wybierz plik binarny");
        //PrzyciskWybierzPlikBIN.setBounds(26, 269,200,103 );
        PrzyciskWybierzPlikBIN.setPreferredSize(stdRozmPrzycisku);
        PrzyciskWybierzPlikBIN.addActionListener(l -> poleWyboruBIN());
        //GUI.add(PrzyciskWybierzPlikBIN);
        tenPanel.add(PrzyciskWybierzPlikBIN);
    }

    private void poleWyboruBIN()
    {
        JFileChooser wyborBIN = new JFileChooser();
        FileNameExtensionFilter typ = new FileNameExtensionFilter("Pliki binarne", "bin");
        wyborBIN.setFileFilter(typ);
        int r = wyborBIN.showOpenDialog(GUI);
        if (r == JFileChooser.APPROVE_OPTION)
        {
            System.out.println(wyborBIN.getSelectedFile().getAbsolutePath());
        }
        else
        {
            System.out.println("Brak");
        }
    }



    private void dodajPrzyciskWybierzWejscie()
    {
        JButton PrzyciskWybierzWejscie = new JButton("Wybierz wejście");
        //PrzyciskWybierzWejscie.setBounds(26, 393,200,103 );
        PrzyciskWybierzWejscie.addActionListener(l -> WybierzWejscie());
        PrzyciskWybierzWejscie.setPreferredSize(stdRozmPrzycisku);
        //GUI.add(PrzyciskWybierzWejscie);
        tenPanel.add(PrzyciskWybierzWejscie);
    }

    private void WybierzWejscie() {
        PL.selectingEntrance = true;
    }

    private void dodajPrzyciskWybierzWyjscie()
    {
        JButton PrzyciskWybierzWyjscie = new JButton("Wybierz wyjście");
        //PrzyciskWybierzWyjscie.setBounds(26, 517,200,103 );
        PrzyciskWybierzWyjscie.addActionListener(l -> WybierzWyjscie());
        PrzyciskWybierzWyjscie.setPreferredSize(stdRozmPrzycisku);
        //GUI.add(PrzyciskWybierzWyjscie);
        tenPanel.add(PrzyciskWybierzWyjscie);
    }

    private void WybierzWyjscie() {
        PL.selectingEntrance = false;
    }

    private void dodajPrzyciskRozwiaz()
    {
        JButton przyciskRozwiaz = new JButton("Rozwiąż");
        //przyciskRozwiaz.setBounds(26, 641,200,103 );
        przyciskRozwiaz.setPreferredSize(stdRozmPrzycisku);
        //GUI.add(przyciskRozwiaz);
        tenPanel.add(przyciskRozwiaz);
    }

    private void dodajPrzyciski()
    {
        this.dodajPrzyciskRozwiaz();
        this.dodajPrzyciskWybierzWyjscie();
        this.dodajPrzyciskWybierzWejscie();
        this.dodajPrzyciskWybierzPlikBIN();
        this.dodajPrzyciskWybierzPlikTXT();
        this.dodajPrzyciskLista();
    }

    public void wczytajGUI()
    {
        GUI.setVisible(true);
    }
}






