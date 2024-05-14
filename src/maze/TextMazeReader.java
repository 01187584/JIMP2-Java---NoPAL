package maze;

import java.io.FileReader;
import java.util.InputMismatchException;

public class TextMazeReader extends MazeReader {
    public static final char WHITE_FIELD = ' ';
    public static final char BLACK_FIELD = 'X';
    public static final char ENTRANCE_FIELD = 'P';
    public static final char EXIT_FIELD = 'K';
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
            e.printStackTrace();
            //fileIsOpen = false // Już jest false
        }
        return fileIsOpen;
    }

    public void close() {
        tryClose();
        System.out.println("Zamykamy plik.");
        try {
            file.close();
        } catch (Exception e) {
            System.out.println("Wystąpił błąd przy zamykaniu pliku, ale uznajmy, że się zamknął.");
        }
    }

    public Maze read() {
        tryRead();
        System.out.println("Czytamy labirynt.");
        if (M == null) {
            M = new Maze(columns, rows);
        } else {
            System.out.println("Robię resize.");
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
                        M.setFieldType(M.getField(cur_column, cur_row), Field.WHITE_FIELD);
                        break;
                    case BLACK_FIELD:
                        M.setFieldType(M.getField(cur_column, cur_row), Field.BLACK_FIELD);
                        break;
                    case ENTRANCE_FIELD:
                        M.setFieldType(M.getField(cur_column, cur_row), Field.ENTRANCE_FIELD);
                        break;
                    case EXIT_FIELD:
                        M.setFieldType(M.getField(cur_column, cur_row), Field.EXIT_FIELD);
                        break;
                    case 10: // LF
                        if (!CR) {
                            LF = true;
                            cur_row++;cur_column = 0;
                        }
                        break;
                    case 15: // CR
                        if (!LF) {
                            CR = true;
                            cur_row++;cur_column = 0;
                        }
                        break;
                    default:
                        System.out.println("Coś jest bardzo nie tak we wczytywaniu.");
                        throw new IllegalStateException();
                }
                cur_column++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Po wczytaniu:");
        System.out.println(M);
        return M;
    }

    // Sprawdza, czy format pliku z labiryntem jest poprawny
    public boolean validateFormat() {
        tryValidateFormat();
        System.out.println("Sprawdzamy format pliku z labiryntem.");
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
                        break;
                    case BLACK_FIELD:
                        break;
                    case ENTRANCE_FIELD:
                        break;
                    case EXIT_FIELD:
                        break;
                    case 10: // LF
                        if (!CR) {
                            LF = true;
                            if (max_cols == 0) max_cols = cur_column;
                            //else if (max_cols != cur_column) throw new InputMismatchException();
                            else if (max_cols != cur_column) throw new InputMismatchException("Nie zgadza się liczba max_cols z cur_column: "+String.valueOf(max_cols)+" "+String.valueOf(cur_column));
                            cur_row++;cur_column = 0;
                        }
                        break;
                    case 15: // CR
                        if (!LF) {
                            CR = true;
                            if (max_cols == 0) max_cols = cur_column;
                            //else if (max_cols != cur_column) throw new InputMismatchException();
                            else if (max_cols != cur_column) {
                                formatErrorMsg = "Nie zgadza się liczba max_cols z cur_column: "+String.valueOf(max_cols)+" "+String.valueOf(cur_column);
                                throw new InputMismatchException(formatErrorMsg);
                            }
                            cur_row++;cur_column = 0;
                        }
                        break;
                    default:
                        //throw new InputMismatchException();
                        formatErrorMsg = "Nierozpoznane Pole: "+character+" ("+(char)character+")";
                        throw new InputMismatchException(formatErrorMsg);
                }
                cur_column++;
            }
        } catch (Exception e) {
            System.out.println("Wykryto, że format pliku z labiryntem jest nieprawidłowy.");
            System.out.println("Przyczyna:");
            e.printStackTrace();
            formatIsValid = false;
        }
        columns = max_cols-1;rows = cur_row-1;
        // Resetujemy pozycję pliku
        try {
            file.reset();
        } catch (Exception e) {
            System.out.println("Oj niestety plik.reset() się nie udało, robimy nowy FileReader.");
            try {
                file.close();
                file = new FileReader(filePath);
            } catch (Exception e2) {
                System.out.println("Nie udało się otworzyć pliku z labiryntem?");
                e2.printStackTrace();
                fileIsOpen = false;
            }
        }
        return formatIsValid;
    }
    
}