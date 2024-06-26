package maze;

import java.io.FileReader;
import java.util.InputMismatchException;

// TODO - ustandaryzować komunikaty, przenieść je do MazeReader, nie wypisawać od razu na ekran (tym zajmie się Terminal)
public class TextMazeReader extends MazeReader {
    // TODO - przenieść poniższe 4 do odpowiedniego enum w Field i używać ich zamiast tych 4
    public static final char WHITE_FIELD = ' ';
    public static final char BLACK_FIELD = 'X';
    public static final char ENTRANCE_FIELD = 'P';
    public static final char EXIT_FIELD = 'K';
    
    public static final char SOLUTION_FIELD = 'R';
    private FileReader file;
    public TextMazeReader() {
        super();
    }
    public TextMazeReader(Maze M) {
        super(M);
    }

    public boolean open(String filePath) {
        System.out.println("Otwieramy plik.");
        tryOpen();
        try {
            file = new FileReader(filePath);
            this.filePath = filePath;
            fileIsOpen = true;
        } catch (Exception e) {
            System.out.println("Nie udało się otworzyć pliku z labiryntem.");
            System.out.println(e.getMessage());
            //e.printStackTrace();
            //fileIsOpen = false // Już jest false
        }
        return fileIsOpen;
    }

    public void close() {
        tryClose();
        System.out.println("Zamykam plik.");
        try {
            file.close();
        } catch (Exception e) {
            //System.out.println("Wystąpił błąd przy zamykaniu pliku, ale uznajmy, że się zamknął.");
        }
    }

    public Maze read() {
        tryRead();
        System.out.println("Czytam labirynt.");
        if (M == null) {
            M = new Maze(columns, rows);
        } else {
            //System.out.println("Robię resize.");
            M.resize(columns, rows);
        }
        int character, cur_column, cur_row;
        cur_column = 1;cur_row = 1;
        boolean CR = false;
        boolean LF = false;
        try {
            while ((character = file.read()) != -1) {
                switch (character) {
                    case WHITE_FIELD:
                        M.getField(cur_column, cur_row).setFieldState(Field.State.WHITE_FIELD);
                        break;
                    case BLACK_FIELD:
                        M.getField(cur_column, cur_row).setFieldState(Field.State.BLACK_FIELD);
                        break;
                    case ENTRANCE_FIELD:
                        M.getField(cur_column, cur_row).setFieldState(Field.State.ENTRANCE_FIELD);
                        break;
                    case EXIT_FIELD:
                        M.getField(cur_column, cur_row).setFieldState(Field.State.EXIT_FIELD);
                        break;
                    case SOLUTION_FIELD:
                        M.getField(cur_column, cur_row).setFieldState(Field.State.WHITE_FIELD);
                        break;
                    case 10: // LF
                        if (!CR) {
                            LF = true;
                            cur_row++;cur_column = 0;
                        } else cur_column--; // jest później zwiększana, a chcemy zignorować
                        break;
                    case 13: // CR
                        if (!LF) {
                            CR = true;
                            cur_row++;cur_column = 0;
                        } else cur_column--; // jest później zwiększana, a chcemy zignorować
                        break;
                    default:
                        System.out.println("Coś jest bardzo nie tak we wczytywaniu.");
                        throw new IllegalStateException();
                        //break;
                }
                cur_column++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Po wczytaniu:");
        return M;
    }

    // Sprawdza, czy format pliku z labiryntem jest poprawny
    public boolean validateFormat() {
        tryValidateFormat();
        System.out.println("Sprawdzam format pliku z labiryntem.");
        formatIsValid = true;
        int max_cols = 0;
        int character, cur_column, cur_row;
        cur_column = 1;cur_row = 1;
        boolean CR = false;
        boolean LF = false;
        try {
            while ((character = file.read()) != -1) {
                switch (character) {
                    case WHITE_FIELD:
                    case BLACK_FIELD:
                    case ENTRANCE_FIELD:
                    case EXIT_FIELD:
                    case SOLUTION_FIELD:
                        break;
                    case 10: // LF
                        if (!CR) {
                            LF = true;
                            if (max_cols == 0) max_cols = cur_column;
                            //else if (max_cols != cur_column) throw new InputMismatchException();
                            else if (max_cols != cur_column) throw new InputMismatchException("Nie zgadza się liczba max_cols z cur_column: "+String.valueOf(max_cols)+" "+String.valueOf(cur_column));
                            cur_row++;cur_column = 0;
                        } else cur_column--; // jest później zwiększana, a chcemy zignorować
                        break;
                    case 13: // CR
                        if (!LF) {
                            CR = true;
                            if (max_cols == 0) max_cols = cur_column;
                            //else if (max_cols != cur_column) throw new InputMismatchException();
                            else if (max_cols != cur_column) {
                                ErrorMsg = "Liczba kolumn nie jest stała: zamiast "+String.valueOf(max_cols)+" jest "+String.valueOf(cur_column);
                                throw new InputMismatchException(ErrorMsg);
                            }
                            cur_row++;cur_column = 0;
                        } else cur_column--; // jest później zwiększana, a chcemy zignorować
                        break;
                    default:
                        //throw new InputMismatchException();
                        ErrorMsg = "Nierozpoznane Pole: linia "+cur_row+", znak "+cur_column+" ("+(char)character+")";
                        throw new InputMismatchException(ErrorMsg);
                        //break;
                }
                //System.out.println("DEBUG"+String.valueOf(character)+' '+String.valueOf((char)character)+' '+cur_column);
                cur_column++;
            }
        } catch (Exception e) {
            System.out.println("Wykryto, że format pliku z labiryntem jest nieprawidłowy.");
            System.out.println("Przyczyna:");
            System.out.println(e.getMessage());
            //e.printStackTrace();
            formatIsValid = false;
        }
        columns = max_cols-1;rows = cur_row-1;
        // Resetujemy pozycję pliku
        try {
            file.reset();
        } catch (Exception e) {
            //System.out.println("Oj niestety plik.reset() się nie udało, robimy nowy FileReader.");
            try {
                file.close();
                file = new FileReader(filePath);
            } catch (Exception e2) {
                //System.out.println("Nie udało się otworzyć pliku z labiryntem?");
                //e2.printStackTrace();
                fileIsOpen = false;
            }
        }
        return formatIsValid;
    }
    
}