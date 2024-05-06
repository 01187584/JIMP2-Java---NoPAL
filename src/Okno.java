import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;

public final class Okno {
    private final JFrame GUI;

    public Okno(){
        GUI = new JFrame("OPAL");
        GUI.setSize(1280,800);
        GUI.setLayout(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.setResizable(false);
        GUI.setLocationRelativeTo(null);
        this.dodajPrzyciski();

    }


    private void dodajPrzyciskLista()
    {
        JButton oknoLista = new JButton("Pokaż listę kroków");
        oknoLista.setBounds(26, 21,200,103 );
        GUI.add(oknoLista);
        oknoLista.addActionListener(_ -> {
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
        //chcę zrobić guzik do utworzenia pliku tekstowego na dole i listę na "środnku
        //ale na razie są problemy,
        fileButton.setVisible(true);
        selectOutputType.setVisible(true);
    }

    private void dodajPrzyciskWybierzPlikTXT()
    {
        JButton PrzyciskWybierzPlikTXT = new JButton("Wybierz plik tekstowy");
        PrzyciskWybierzPlikTXT.setBounds(26, 145,200,103 );
        PrzyciskWybierzPlikTXT.addActionListener(_ -> poleWyboruTXT());
        GUI.add(PrzyciskWybierzPlikTXT);
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
        PrzyciskWybierzPlikBIN.setBounds(26, 269,200,103 );
        PrzyciskWybierzPlikBIN.addActionListener(_ -> poleWyboruBIN());
        GUI.add(PrzyciskWybierzPlikBIN);
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
        PrzyciskWybierzWejscie.setBounds(26, 393,200,103 );
        GUI.add(PrzyciskWybierzWejscie);
    }

    private void dodajPrzyciskWybierzWyjscie()
    {
        JButton PrzyciskWybierzWyjscie = new JButton("Wybierz wyjście");
        PrzyciskWybierzWyjscie.setBounds(26, 517,200,103 );
        GUI.add(PrzyciskWybierzWyjscie);
    }

    private void dodajPrzyciskRozwiaz()
    {
        JButton przyciskRozwiaz = new JButton("Rozwiąż");
        przyciskRozwiaz.setBounds(26, 641,200,103 );
        GUI.add(przyciskRozwiaz);
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






