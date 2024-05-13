package maze;

public class TextMazeReader extends MazeReader {
    /*TextMazeReader(MazeReader Reader) {
        System.out.println("Tworzymy TextMazeReader z MazeReader.");
    }*/

    public TextMazeReader(String filePath) {

        System.out.println("Otwieramy plik.");
    }

    public void close() {
        System.out.println("Zamykamy plik.");
    }

    public Maze read() {
        checkValidation();
        System.out.println("Czytamy labirynt.");
        return null; // placeholder
    }

    // Sprawdza, czy format pliku z labiryntem jest poprawny
    public boolean validateFormat() {
        System.out.println("Sprawdzamy format pliku z labiryntem.");
        formatValidated = true;
        return formatIsValid;
    }
    
}