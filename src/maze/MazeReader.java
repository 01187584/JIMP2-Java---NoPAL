package maze;

import java.util.InputMismatchException;

public abstract class MazeReader {
    protected Maze M;
    protected int columns = 0, rows = 0; // Liczba kolumny i wierszy labiryntu odczytane z pliku
    protected boolean formatValidated = false; // Czy wywołano validateFormat
    protected boolean formatIsValid = false; // Czy validateFormat zwrócił prawdę
    protected boolean wasFileOpened = false; // Czy próbowano otworzyć plik
    protected boolean fileIsOpen = false; // Czy udało się otworzyć plik
    protected boolean wasFileRead = false; // Czy przeczytano już plik
    protected String filePath;

    // Czy udało się otworzyć plik
    public abstract boolean open(String filePath);
    //public MazeReader(String filePath);
    public abstract void close();

    abstract public Maze read();

    // Sprawdza, czy format pliku z labiryntem jest poprawny
    abstract public boolean validateFormat();
    
    // Sprawdza, czy można użyć open
    protected void tryOpen() {
        if (fileIsOpen) throw new IllegalStateException("Otwarty plik musi zostać zamknięty, aby można go odczytać nowy.");
        wasFileOpened = true;
    }
    // Sprawdza, czy można użyć close
    protected void tryClose() {
        if (!fileIsOpen) throw new IllegalStateException("Plik musi być otwarty, aby można go było zamknąć.");
        fileIsOpen = false;
        wasFileOpened = false;
        formatValidated = false;
        formatIsValid = false;
        columns = 0;
        rows = 0;
    }
    // Sprawdza, czy można użyć read
    protected void tryRead() {
        if (wasFileRead) throw new InputMismatchException("Nie ma sensu czytać drugi raz tego samego pliku.");
        if (!formatValidated) validateFormat();
        if (!formatIsValid) throw new InputMismatchException("Nieprawidłowy format pliku z labiryntem.");
        wasFileRead = true;
    }
    // Sprawdza, czy można użyć validateFormat
    protected void tryValidateFormat() {
        if (!wasFileOpened) open(filePath);
        if (!fileIsOpen) throw new IllegalAccessError("Nie udało się otworzyć pliku z labiryntem.");
        formatValidated = true;
    }
}