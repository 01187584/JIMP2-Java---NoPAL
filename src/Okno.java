import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class Okno {
    private final JFrame GUI;
    private final JPanel tenPanel;
    private final Dimension stdRozmPrzycisku = new Dimension(230, 116);
    private PodgladLabiryntu PL;
    public Okno() {
        GUI = new JFrame("WyPAL"); // Wydajny Program Analizujący Labirynt
        //GUI.setSize(1280,720);
        GUI.setPreferredSize(new Dimension(1280,820));
        GUI.setMinimumSize(new Dimension(820,820)); // Aby przyciski się zawsze mieściły
        //GUI.setPreferredSize(new Dimension(1280,800));
        //GUI.setLayout(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //GUI.setResizable(false);
        GUI.setLocationRelativeTo(null);
        this.dodajWidokLabiryntu();
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

    }

    private void dodajWidokLabiryntu()
    {
        JPanel widokLabiryntu = new JPanel();
        widokLabiryntu.setBounds(252, 21, 976, 628);
        widokLabiryntu.setBackground(Color.BLACK);
        GUI.add(widokLabiryntu);
    }

    private void dodajPrzyciskLista()
    {
        JButton oknoLista = new JButton("Pokaż listę kroków");
        //oknoLista.setBounds(26, 21,200,85 );
        oknoLista.setPreferredSize(stdRozmPrzycisku);
        //GUI.add(oknoLista);
        tenPanel.add(oknoLista);
        oknoLista.addActionListener(l -> zapisListy());
    }

    private void zapisListy()
    {

        JDialog mazeOutput = new JDialog(GUI, "Lista kroków");
        mazeOutput.setSize(400,400);
        mazeOutput.setLocationRelativeTo(null);
        mazeOutput.setResizable(false);
        mazeOutput.setLayout(null);

        JButton fileButton = new JButton("Stwórz plik");
        fileButton.setBounds(100,250, 200, 75);
        mazeOutput.add(fileButton);
        fileButton.setVisible(true);

        JTextArea lista = new JTextArea();
        lista.setBackground(Color.WHITE);
        lista.setForeground(Color.BLACK); //czcionka
        lista.setEditable(false);
        lista.setLineWrap(true);
        for(int i=1; i<=15; i++)
        {
            lista.append("Oto " + i + " linijka tekstu.\n");
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(50, 50, 300, 150);
        mazeOutput.add(scroll);

        fileButton.addActionListener(_ -> {
            String tekst = lista.getText();
            poleZapisu(tekst);
        });

        lista.setVisible(true);
        mazeOutput.setVisible(true);

    }

    private void dodajPrzyciskWybierzPlikTXT()
    {
        JButton PrzyciskWybierzPlikTXT = new JButton("Wybierz plik tekstowy");
        //PrzyciskWybierzPlikTXT.setBounds(26, 130,200,85 );
        PrzyciskWybierzPlikTXT.setPreferredSize(stdRozmPrzycisku);
        PrzyciskWybierzPlikTXT.addActionListener(l -> poleWyboruTXT("txt"));
        //GUI.add(PrzyciskWybierzPlikTXT);
        tenPanel.add(PrzyciskWybierzPlikTXT);
    }

    private void dodajPrzyciskWybierzPlikBIN()
    {
        JButton PrzyciskWybierzPlikBIN = new JButton("Wybierz plik binarny");
        //PrzyciskWybierzPlikBIN.setBounds(26, 239,200,85 );
        PrzyciskWybierzPlikBIN.setPreferredSize(stdRozmPrzycisku);
        PrzyciskWybierzPlikBIN.addActionListener(l -> poleWyboruBIN("bin"));
        //GUI.add(PrzyciskWybierzPlikBIN);
        tenPanel.add(PrzyciskWybierzPlikBIN);
    }

    private void poleWyboru(String rozszerzenie)
    {
        JFileChooser wybor = new JFileChooser();
        FileNameExtensionFilter typ = new FileNameExtensionFilter("Pliki o rozszerzeniu " + rozszerzenie, rozszerzenie);
        wybor.setFileFilter(typ);
        int r = wybor.showOpenDialog(GUI);
        if (r == JFileChooser.APPROVE_OPTION)
        {
            System.out.println(wybor.getSelectedFile().getAbsolutePath());
        }
        else
        {
            System.out.println("Brak");
        }
    }

    //należy ręcznie wpisać nazwę pliku tekstowego

    private void poleZapisu(String tekst)
    {
        JFileChooser zapis = new JFileChooser();
        int r = zapis.showSaveDialog(GUI);
        if (r == JFileChooser.APPROVE_OPTION)
        {
            String nazwaPliku = zapis.getSelectedFile().getAbsolutePath() + ".txt";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(nazwaPliku));
                writer.write(tekst);

                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //te guziki jeszcze nie są zaimplementowane

    private void dodajPrzyciskWybierzWejscie()
    {
        JButton PrzyciskWybierzWejscie = new JButton("Wybierz wejście");
        //PrzyciskWybierzWejscie.setBounds(26, 348,200,85 );
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
        //PrzyciskWybierzWyjscie.setBounds(26, 457,200,85 );
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
        //przyciskRozwiaz.setBounds(26, 566,200,85 );
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






