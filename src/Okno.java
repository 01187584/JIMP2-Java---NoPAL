import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;

public final class Okno {
    private final JFrame GUI;

    public Okno(){
        GUI = new JFrame("OPAL");
        GUI.setSize(1280,720);
        GUI.setLayout(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI.setResizable(false);
        GUI.setLocationRelativeTo(null);
        this.dodajWidokLabiryntu();
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
        oknoLista.setBounds(26, 21,200,85 );
        GUI.add(oknoLista);
        oknoLista.addActionListener(_ -> zapisListy());
    }

    private void zapisListy()
    {
        JDialog selectOutputType = new JDialog(GUI, "Lista kroków");
        selectOutputType.setSize(400,400);
        selectOutputType.setLocationRelativeTo(null);
        selectOutputType.setResizable(false);
        selectOutputType.setLayout(null);
        JButton fileButton = new JButton("Stwórz plik");
        selectOutputType.add(fileButton);
        fileButton.setBounds(100,250, 200, 250);
        fileButton.setVisible(true);
        selectOutputType.setVisible(true);
    }

    private void dodajPrzyciskWybierzPlikTXT()
    {
        JButton PrzyciskWybierzPlikTXT = new JButton("Wybierz plik tekstowy");
        PrzyciskWybierzPlikTXT.setBounds(26, 130,200,85 );
        PrzyciskWybierzPlikTXT.addActionListener(_ -> poleWyboru("txt"));
        GUI.add(PrzyciskWybierzPlikTXT);
    }

    private void dodajPrzyciskWybierzPlikBIN()
    {
        JButton PrzyciskWybierzPlikBIN = new JButton("Wybierz plik binarny");
        PrzyciskWybierzPlikBIN.setBounds(26, 239,200,85 );
        PrzyciskWybierzPlikBIN.addActionListener(_ -> poleWyboru("bin"));
        GUI.add(PrzyciskWybierzPlikBIN);
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

    private void dodajPrzyciskWybierzWejscie()
    {
        JButton PrzyciskWybierzWejscie = new JButton("Wybierz wejście");
        PrzyciskWybierzWejscie.setBounds(26, 348,200,85 );
        GUI.add(PrzyciskWybierzWejscie);
    }

    private void dodajPrzyciskWybierzWyjscie()
    {
        JButton PrzyciskWybierzWyjscie = new JButton("Wybierz wyjście");
        PrzyciskWybierzWyjscie.setBounds(26, 457,200,85 );
        GUI.add(PrzyciskWybierzWyjscie);
    }

    private void dodajPrzyciskRozwiaz()
    {
        JButton przyciskRozwiaz = new JButton("Rozwiąż");
        przyciskRozwiaz.setBounds(26, 566,200,85 );
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






