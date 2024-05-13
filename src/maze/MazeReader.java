package maze;

import java.util.InputMismatchException;

public abstract class MazeReader {
    protected Maze M;
    protected boolean formatValidated = false; // Czy wywołano validateFormat
    protected boolean formatIsValid = true; // Czy validateFormat zwrócił prawdę

    //public MazeReader(String filePath);
    public abstract void close();

    abstract public Maze read();

    // Sprawdza, czy format pliku z labiryntem jest poprawny
    abstract public boolean validateFormat();
    
    // Jeśli wcześniej nie sprawdzono formatu (formatValidated == false)
    // to wywołuje validateFormat
    // jeśli po pierwszym wywołaniu validateFormat zwróci fałsz
    // to wywołanie tej metody zwróci błąd
    public void checkValidation() {
        if (!formatValidated) validateFormat();
        if (!formatIsValid) throw new InputMismatchException("Nieprawidłowy format pliku z labiryntem.");
    }
}