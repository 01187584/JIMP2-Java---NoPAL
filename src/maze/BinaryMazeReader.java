package maze;
import java.io.*;
import java.nio.*;

// TODO - ustandaryzować komunikaty, przenieść je do MazeReader, nie wypisawać od razu na ekran (tym zajmie się Terminal)
public class BinaryMazeReader extends MazeReader {

    private FileInputStream file;
    private int counter;
    private byte path;
    private byte wall;
    private short entryX = 0;
    private short entryY = 0;
    private short exitX = 0;
    private short exitY = 0;
    public BinaryMazeReader() {
        super();
    }
    public BinaryMazeReader(Maze M) {
        super(M);
    }


    public boolean open(String filePath) {
        System.out.println("Otwieramy plik.");
        tryOpen();
        try {
            file = new FileInputStream(filePath);
            this.filePath = filePath;
            fileIsOpen = true;
        } catch (Exception e) {
            System.out.println("Nie udało się otworzyć pliku z labiryntem.");
            e.printStackTrace();
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
        int cur_column = 1;
        int cur_row = 1;
        byte value;
        byte count;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            fis.skip(40);
            byte[] buffer = fis.readAllBytes();
            ByteBuffer bb = ByteBuffer.wrap(buffer);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            for(int i=0; i<counter; i++)
            {
                bb.get(); //pomijam separator
                value=bb.get();
                count=bb.get();
                for(int j=0; j<unsignedByteToInt(count)+1; j++)
                {
                    if(value==wall)
                        M.getField(cur_column, cur_row).setFieldState(Field.State.BLACK_FIELD);
                    else if(value==path)
                        M.getField(cur_column, cur_row).setFieldState(Field.State.WHITE_FIELD);
                    else throw new InvalidFileFormatException("Pomiędzy sprawdzeniem pliku a odczytaniem go zaszły w nim zmiany, albo coś się bardzo popsuło...");
                    cur_column++;
                    if(cur_column>columns){
                        cur_column=1;
                        cur_row++;
                    }
                }
            }
            M.getField(entryX, entryY).setFieldState(Field.State.ENTRANCE_FIELD);
            M.getField(exitX, exitY).setFieldState(Field.State.EXIT_FIELD);

        }
        catch(IOException e){
            e.printStackTrace();
        }
        return M;
    }

    public boolean validateFormat() {
        tryValidateFormat();
        System.out.println("Sprawdzamy format pliku z labiryntem.");
        formatIsValid = true;
        int fileID;
        byte escape;
        byte [] reserved = new byte[12];
        int solutionOffset;
        byte separator;
        int i;
        byte value;
        try {
            byte[] buffer = file.readAllBytes();
            ByteBuffer bb = ByteBuffer.wrap(buffer);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            fileID = bb.getInt();
            if (fileID != 0x52524243) {
                throw new InvalidFileFormatException(String.format("Niepoprawne fileID: 0x%08X, oczekiwano 0x52524243", fileID));
            }
            escape =  bb.get();
            if (escape != 0x1B) {
                throw new InvalidFileFormatException(String.format("Niepoprawna wartość escape: 0x%02X, oczekiwano 0x1B", escape));
            }
            columns = bb.getShort();
            rows = bb.getShort();
            entryX = bb.getShort();
            entryY = bb.getShort();
            if(entryX<1 || entryY<1 || entryX>columns || entryY>rows)
            {
                throw new InvalidFileFormatException("Wejście znajduje się poza labiryntem, koordynaty (" + entryX + ", " + entryY + ")");
            }
            exitX = bb.getShort();
            exitY = bb.getShort();
            if(exitX<1 || exitY<1 || exitX>columns || exitY>rows)
            {
                throw new InvalidFileFormatException("Wejście znajduje się poza labiryntem, koordynaty (" + exitX + ", " + exitY + ")");
            }
            bb.get(reserved);
            counter = bb.getInt();
            solutionOffset = bb.getInt(); //możnaby sprawdzić, czy nie "najeżdża" na sekcję z labiryntem
            separator = bb.get();
            wall = bb.get();
            path = bb.get();
            for(i=0; i<counter; i++)
            {
                value = bb.get();
                if(value!=separator)
                {
                    throw new InvalidFileFormatException("Separator słowa kodowego numer " + i + 1 + " jest nieprawdłowy: oczekiwano " + separator + ", otrzymano " + value);
                }
                value = bb.get();
                if(value!=wall && value!=path)
                {
                    throw new InvalidFileFormatException("Wartość słowa kodowego " + value + " o numerze " + i + 1 + " nie odpowiada ani ścianie (" + wall + "), ani ścieżce (" + path + ")");
                }
                bb.get(); //nie ma co sprawdzać poprawności wartości
            }
            bb.rewind();
                    /*
            System.out.printf("Identyfikator: 0x%08X%n", fileID);
            System.out.println("Escape: " + escape);
            System.out.println("Liczba kolumn: " + columns);
            System.out.println("Liczba wierszy: " + rows);
            System.out.println("Położenie wejścia (X, Y): " + entryX + ", " + entryY);
            System.out.println("Położenie wyjścia (X, Y): " + exitX + ", " + exitY);
            System.out.print("Reserved: ");
            for (byte b : reserved) {
                System.out.printf("%02X ", b);
            }
            System.out.println();
            System.out.println("Licznik: " + counter);
            System.out.println("Offset rozwiązania: " + solutionOffset);
            System.out.println("Separator (ściana): " + (char) separator + " " + separator);
            System.out.println("Ściana: " + (char) wall + " " + wall);
            System.out.println("Ścieżka: " + (char) path + " " + path);
            */
        } catch (IOException e){
            System.out.println("Wystąpił błąd!");
            e.printStackTrace();
            formatIsValid = false;
        }


        if(formatIsValid)System.out.println("Format prawidłowy.");
        return formatIsValid;
    }
    public static int unsignedByteToInt(byte b) {
        return b & 0xFF;
    }

    public static class InvalidFileFormatException extends IOException {
        // TODO - przenieść InvalidFileFormatException do MazeReader, zmienić TextMazeReader, aby tego używał
        public InvalidFileFormatException(String message) {
            super(message);
        }
    }
}
