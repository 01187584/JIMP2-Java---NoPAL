import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

//import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//import maze.BinaryMazeReader;
//import maze.Maze;
//import maze.TextMazeReader;
//import maze.MazeReader;
import observer.MazeEventManager;
import observer.MazeEventListener;
import observer.MazeEvent;

public final class Okno implements MazeEventListener {
    //private Maze M;
    private MazeEventManager MEM;
    private final JFrame GUI;
    private final JPanel tenPanel;
    private final Dimension stdRozmPrzycisku = new Dimension(230, 120);
    private PodgladLabiryntu PL;
    private JScrollPane ScrollPL;
    JTextPane komunikaty; // Do komunikatów
    public Okno(MazeEventManager MEM) {
        this.MEM = MEM;
        MEM.addEventListener(this);
        /*MazeReader Reader = new TextMazeReader(M);
        Reader.open("maze-test.txt");
        M = null;
        if (Reader.validateFormat()) {
            M = Reader.read();
        }
        Reader.close();
        if (M != null) {
            //System.out.println(M);
        }*/
        GUI = new JFrame("WyPAL"); // Wydajny Program Analizujący Labirynt
        //GUI.setSize(1280,720);
        GUI.setPreferredSize(new Dimension(1280,360+170));
        GUI.setMinimumSize(new Dimension(820,520+180));// Aby przyciski się zawsze mieściły
        //GUI.setPreferredSize(new Dimension(1280,800));
        //GUI.setLayout(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //GUI.setResizable(false);
        GUI.setLocationRelativeTo(null);
        tenPanel = new JPanel();
        GUI.setLayout(new BorderLayout());
        this.dodajPodgladLabiryntu();
        //GUI.add(ScrollPL);
        //GUI.add(ScrollPL, BorderLayout.CENTER);
        FlowLayout layout = new FlowLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        tenPanel.setLayout(layout);
        GUI.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = GUI.getSize();
                layout.setVgap((size.height-520)/5);
                tenPanel.revalidate();
            }
        });
        tenPanel.setPreferredSize(new Dimension(stdRozmPrzycisku.width+10,820)); //height nie wpływa na nic
        this.dodajPrzyciski();
        GUI.add(tenPanel, BorderLayout.WEST);
        GUI.pack();
    }

    @Override
    public void actionPerformed(MazeEvent event) {
        switch (event.getType()) {
            case SELECT_ALGORITHM:
                handle_SELECT_ALGORITHM(event);
                break;
            case SOLVE_MAZE:
                handle_SOLVE_MAZE(event);
                break;
            case LOAD_TEXT_MAZE: // celowo bez break
            case LOAD_BINARY_MAZE:
            case LOAD_MAZE:
                handle_LOAD_MAZE(event);
                break;
            case SET_FIELD_TYPE:
                handle_SET_FIELD_TYPE(event);
                break;
        }
    }
    private void handle_SELECT_ALGORITHM(MazeEvent event) {
        // TODO: dodać obsługę wybierania algorytmu
    }
    private void handle_SOLVE_MAZE(MazeEvent event) {
        // TODO: dodać obsługę rozwiązywania labiryntu
    }
    private void handle_LOAD_MAZE(MazeEvent event) {
        switch (event.getStatus()) {
            case START:
                komunikaty.setText("Otwieranie pliku...");
            case LOAD_MAZE_OPENING_ERROR:
                komunikaty.setText("Wczytywanie labiryntu...");
                break;
            case LOAD_MAZE_FILE_OPENED:
                komunikaty.setText("Sprawdzanie formatu pliku...");
                break;
            case LOAD_MAZE_FORMAT_ERROR:
                komunikaty.setText(event.getStatusMessage());
                break;
            case LOAD_MAZE_FORMAT_VALIDATED:
                komunikaty.setText(event.getStatusMessage());
                break;
            case OK:
                komunikaty.setText("Rysowanie labiryntu...");
                komunikaty.paintImmediately(komunikaty.getVisibleRect());
                zaktualizujRozmiarPodgladuLabiryntu();
                // *Chyba* może się okazać, że labirynt zostanie narysowany 2 razy
                ScrollPL.revalidate();
                PL.repaint();
                komunikaty.setText("Udało się wczytać labirynt."); // TODO - poprawić ten komunikat np. przy użyciu wydarzenia wysłanego do obserwatora MazeEventManager przez PodgladLabiryntu, które zostałoby tu obsłużone - obecnie ze względu na asynchroniczność Swinga wyświetli się to w złym momencie (przed narysowaniem)
                break;
            default:
                break;
        }
        komunikaty.paintImmediately(komunikaty.getVisibleRect());
    }
    private void handle_SET_FIELD_TYPE(MazeEvent event) {
        // TODO: obsłużyć błąd - można podać nieprawidłowy typ Pola
        switch (event.getStatus()) {
            case SET_FIELD_TYPE_INVALID_COORDS:
                komunikaty.setText(event.getStatusMessage());
                break;
            case OK:
                // TODO
                komunikaty.setText("Ustawiono Pole ### na ###");
                PL.repaint();
                break;
            default:
                break;
        }
        return;
    }
    private void dodajPodgladLabiryntu()
    {
        /*JPanel widokLabiryntu = new JPanel();
        widokLabiryntu.setBounds(252, 21, 976, 628);
        widokLabiryntu.setBackground(Color.BLACK);
        GUI.add(widokLabiryntu);
        */
        //PL = new PodgladLabiryntu(26+200, 21, 1280-26-200, 820-21, 10, 10);
        //PL = new PodgladLabiryntu(0, 5, 1260-1-stdRozmPrzycisku.width, 761-1, 150, 50);
        

        //PL = new PodgladLabiryntu(0-PodgladLabiryntu.minSize, 0-PodgladLabiryntu.minSize, 1260-1-stdRozmPrzycisku.width, 761-1, M);
        PL = new PodgladLabiryntu(0, 0, 1260-1-stdRozmPrzycisku.width, 761-1, MEM);
        //PL = new PodgladLabiryntu(0, 0, 1023, 754, 10, 10);
        GUI.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //System.out.printf("(%d, %d)\n",GUI.getBounds().width,GUI.getBounds().height);
                zaktualizujRozmiarPodgladuLabiryntu();
            }
        });
        ScrollPL = new JScrollPane(PL);
        //PL.setSize(1280-26-200,820-21);
        //PL.setSize(1054-500,799);
        GUI.add(ScrollPL, BorderLayout.CENTER);
    }

    private void zaktualizujRozmiarPodgladuLabiryntu() {
        System.out.printf("Okno ma wymiary (%s, %s)\n", String.valueOf(GUI.getBounds().width), String.valueOf(GUI.getBounds().height));
        // Aktualizujemyy ile miejsca może maksymalnie zająć Podgląd Labiryntu
        PL.setMaxDimensions(GUI.getBounds().width-37-stdRozmPrzycisku.width-PodgladLabiryntu.minSize,GUI.getBounds().height-37-25-PodgladLabiryntu.minSize);
        //PL.setMaxDimensions(GUI.getBounds().width-27-stdRozmPrzycisku.width,GUI.getBounds().height-37);
        //PL.setMaxDimensions(GUI.getBounds().width-100-stdRozmPrzycisku.width,GUI.getBounds().height-180);
        //PL.setMaxDimensions(GUI.getBounds().width-100-stdRozmPrzycisku.width,GUI.getBounds().height-180);
        //PL.setMaxDimensions(GUI.getBounds().width-301-stdRozmPrzycisku.width,GUI.getBounds().height-167);
        //PL.setMaxDimensions(GUI.getBounds().width-21-stdRozmPrzycisku.width,GUI.getBounds().height-60);
    }

    private void dodajPrzyciskLista()
    {
        JButton oknoLista = new JButton("Pokaż listę kroków");
        //oknoLista.setBounds(26, 21,200,85 );
        oknoLista.setPreferredSize(stdRozmPrzycisku);
        //GUI.add(oknoLista);
        oknoLista.addActionListener(l -> zapisListy());
        tenPanel.add(oknoLista);
    }

    private void zapisListy()
    {
        //System.out.println("TU JESTEM");
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

        fileButton.addActionListener(l -> {
            String tekst = lista.getText();
            poleZapisu(tekst);
        });

        lista.setVisible(true);
        mazeOutput.setVisible(true);

    }

    private void dodajPrzyciskWybierzPlik()
    {
        JButton PrzyciskWybierzPlik = new JButton("Wybierz plik z labiryntem");
        PrzyciskWybierzPlik.setPreferredSize(stdRozmPrzycisku);
        PrzyciskWybierzPlik.addActionListener(l -> poleWyboru());
        tenPanel.add(PrzyciskWybierzPlik);
    }


    private void poleWyboru()
    {
        JFileChooser wybor = new JFileChooser();
        FileNameExtensionFilter typ = new FileNameExtensionFilter("Pliki o rozszerzeniu ", "txt", "bin");
        wybor.setFileFilter(typ);
        int r = wybor.showOpenDialog(GUI);
        if (r == JFileChooser.APPROVE_OPTION)
        {
            /*
            int dotIndex = wybor.getSelectedFile().getAbsolutePath().lastIndexOf('.');
            String extension = (dotIndex > 0) ? wybor.getSelectedFile().getAbsolutePath().substring(dotIndex + 1) : "";
            System.out.println(wybor.getSelectedFile().getAbsolutePath());
            MazeReader Reader;
            if(extension.equals("bin"))
                Reader = new BinaryMazeReader(M);
            else
                Reader = new TextMazeReader(M);
            komunikaty.setText("Otwieranie pliku...");
            komunikaty.paintImmediately(komunikaty.getVisibleRect());
            if (Reader.open(wybor.getSelectedFile().getAbsolutePath())) {
                //System.out.println("DEBUG Wczytywanie labiryntu: sprawdzanie formatu pliku.");
                komunikaty.setText("Sprawdzanie formatu pliku...");
                komunikaty.paintImmediately(komunikaty.getVisibleRect());
                if (Reader.validateFormat()) {
                    komunikaty.setText("Wczytywanie labiryntu...");
                    komunikaty.paintImmediately(komunikaty.getVisibleRect());
                    Reader.read();
                    //PL.M = M; // Już niepotrzebne, PL.M == M
                    zaktualizujRozmiarPodgladuLabiryntu();
                    komunikaty.setText("Rysowanie labiryntu...");
                    komunikaty.paintImmediately(komunikaty.getVisibleRect());
                    PL.repaint();
                    komunikaty.setText("Udało się wczytać labirynt.");
    
                } else {
                    komunikaty.setText("Format pliku z labiryntem jest nieprawidłowy: "+Reader.getFormatErrorMsg());
                }
                Reader.close();
            } else {
                komunikaty.setText("Nie udało się otworzyć pliku z labiryntem!");
            }*/
            int dotIndex = wybor.getSelectedFile().getAbsolutePath().lastIndexOf('.');
            String extension = (dotIndex > 0) ? wybor.getSelectedFile().getAbsolutePath().substring(dotIndex + 1) : "";
            System.out.println(wybor.getSelectedFile().getAbsolutePath());
            //MazeReader Reader;
            MazeEvent.Type eventType;
            if(extension.equals("bin"))
                //Reader = new BinaryMazeReader(M);
                eventType = MazeEvent.Type.LOAD_BINARY_MAZE;
            else
                //Reader = new TextMazeReader(M);
                eventType = MazeEvent.Type.LOAD_TEXT_MAZE;
            MEM.notifyListeners(new MazeEvent(eventType, new String[] {wybor.getSelectedFile().getAbsolutePath()}));
        }
        else
        {
            System.out.println("Brak");
        }
    }
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
        //PL.selectingEntrance = true; Niepotrzebne, rozróżniamy po kliknięciu LPM lub PPM
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
        //PL.selectingEntrance = false; Niepotrzebne, rozróżniamy po kliknięciu LPM lub PPM
    }

    private void dodajPrzyciskRozwiaz()
    {
        JButton przyciskRozwiaz = new JButton("Rozwiąż");
        //przyciskRozwiaz.setBounds(26, 566,200,85 );
        przyciskRozwiaz.setPreferredSize(stdRozmPrzycisku);
        //GUI.add(przyciskRozwiaz);
        tenPanel.add(przyciskRozwiaz);
    }


    private void dodajKomunikaty()
    {
        JTextPane komunikaty = new JTextPane();
        this.komunikaty = komunikaty;
        komunikaty.setBackground(new Color(60,63,65));
        komunikaty.setForeground(Color.WHITE); //czcionka
        //komunikaty.setCaret(null);
        komunikaty.setCaretColor(new Color(60,63,65));
        komunikaty.setFont(new Font("Helvetica", Font.BOLD, 16));
        StyledDocument doc = komunikaty.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        komunikaty.setText("Pole na komunikaty.");
        komunikaty.setEditable(false);
        komunikaty.setPreferredSize(stdRozmPrzycisku);
        tenPanel.add(komunikaty);
    }

    private void dodajPrzyciski()
    {
        this.dodajKomunikaty();
        this.dodajPrzyciskRozwiaz();
        //Niepotrzebne, rozróżniamy po kliknięciu LPM lub PPM:
        //this.dodajPrzyciskWybierzWyjscie();
        //this.dodajPrzyciskWybierzWejscie();
        this.dodajPrzyciskWybierzPlik();
        this.dodajPrzyciskLista();

    }

    public void wczytajGUI()
    {
        GUI.setVisible(true);
    }
}






