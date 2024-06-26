import java.util.Scanner;

import observer.MazeEventManager;
import observer.SelectAlgorithmEvent;
import observer.SetFieldTypeEvent;
import observer.SolveMazeEvent;
import observer.LoadMazeEvent;
import observer.MazeEvent;
import observer.MazeEventListener;

public class Terminal implements MazeEventListener {
    // TODO - pozbyć się wszystkich System.out.printcośtam w innych klasach i odpowiednio obsługiwać je w tej klasie
    private MazeEventManager MEM;
    private boolean expectingEvent = false;
    private Scanner input;

    public Terminal(MazeEventManager MEM) {
        // MazeEventManager jest do aktualizowania stanu labiryntu przy użyciu MazeEventów za pomocą terminala
        // MazeEventManager.getMaze() jest tylko do wypisywania labiryntu w postaci tesktowej
        this.MEM = MEM;
        MEM.addEventListener(this);
        System.out.print(">>> ");
    }
    public void start() {
        input = new Scanner(System.in);
        while (true) {
            readUserInput();
        }
    }
    public void actionPerformed(MazeEvent event) {
        // TODO - Jakoś to poprawić
        String str = event.getStatusMessage();
        /*if (str == null && event.getStatus().equals("OK")) {
            switch (event.getType()) {
                case LOAD_MAZE:
                    System.out.println(MEM.getMaze()); // Wypisywanie labiryntu jest niewydajne!
                    break;
                default:
                    System.out.println("Terminal właśnie teraz powinien wypisać jakiś ciekawy komunikat.");
                    break;
            }
        }*/
        if (!expectingEvent && event.getStatus().equals("START")) {
            expectingEvent = true;
            switch (event.getType()) {
                case LOAD_MAZE:
                    LoadMazeEvent LOAD_MAZE_event = (LoadMazeEvent)event;
                    if (LOAD_MAZE_event.getFileType().equals("?")) System.out.printf("wczytaj %s\n",LOAD_MAZE_event.getFilePath());
                    else System.out.printf("wczytaj %s %s\n",LOAD_MAZE_event.getFilePath(),LOAD_MAZE_event.getFileType());
                    break;
                case SET_FIELD_TYPE:
                    SetFieldTypeEvent SET_FIELD_TYPE_event = (SetFieldTypeEvent)event;
                    System.out.printf("ustaw_pole %s %s %c\n",SET_FIELD_TYPE_event.getColumn(),SET_FIELD_TYPE_event.getRow(),SET_FIELD_TYPE_event.getFieldType());
                    break;
                case SELECT_ALGORITHM:
                    SelectAlgorithmEvent SELECT_ALGORITHM_event = (SelectAlgorithmEvent)event;
                    System.out.printf("wybierz_algorytm %s\n", SELECT_ALGORITHM_event.getSelectedAlgorithmString());
                    break;
                case SOLVE_MAZE:
                    SolveMazeEvent SOLVE_MAZE_event = (SolveMazeEvent)event;
                    System.out.printf("znajdz_najkrotsza_sciezke\n");
                    break;
                default:
                    throw new RuntimeException("NIEZNANY TYP WYDARZENIA");
            }
        }
        if (str == null && event.getStatus().equals("OK")) {
            System.out.println("UWAGA: Terminal właśnie teraz powinien wypisać jakiś ciekawy komunikat.");
        }
        else if (str != null) System.out.println(str);
        if (event.getStatus().equals("OK")) {
            switch (event.getType()) {
                case SOLVE_MAZE:
                    SolveMazeEvent SOLVE_MAZE_event = (SolveMazeEvent)event;
                    System.out.println(SOLVE_MAZE_event.getShortestSolutionString());
                    break;
                default:
                    break;
            }
        }
        if (event.isCurrentStatusFinal()) System.out.print(">>> ");
        expectingEvent = false;
    }
    private void readUserInput() {
        MazeEvent ME = null;
        try {
            switch (input.next()) {
        //switch (input.nextLine()) {
                case "wczytaj":
                    ME = wczytaj();
                    break;
                case "ustaw_pole":
                    ME = ustaw_pole();
                    break;
                case "wybierz_algorytm":
                    ME = wybierz_algorytm();
                    break;
                case "znajdz_najkrotsza_sciezke":
                case "rozwiaz":
                    ME = znajdz_najkrotsza_sciezke();
                    break;
                default:
                    System.out.print("Nierozpoznane polecenie.\n>>> ");
                    break;
            }
            if (ME == null) expectingEvent = false;
            else {
                expectingEvent = true;
                MEM.notifyListeners(ME);
            }
        } catch (java.util.NoSuchElementException E) {
            // Celowe przerwanie działania programu
        }
        
    }
    private LoadMazeEvent wczytaj() {
        try {
            String toSplit = input.nextLine();
            String[] arr = toSplit.split(" ");
            if (arr.length != 2 && arr.length != 3) throw new IllegalArgumentException();
            String filePath = arr[1];
            if (arr.length == 3) {
                return new LoadMazeEvent(MEM, filePath, arr[2]);
            } else {
                return new LoadMazeEvent(MEM, filePath);
            }
        } catch (Exception e) {
            System.out.println("Nieprawidłowy format polecenia wczytaj.");
            e.printStackTrace();
        }
        return null;
    }
    private SetFieldTypeEvent ustaw_pole() {
        int column, row;char fieldType;
        try {
            column = input.nextInt();
            row = input.nextInt();
            fieldType = input.next().charAt(0);
            if (fieldType == 'O') fieldType = ' ';
            return new SetFieldTypeEvent(MEM, column, row, fieldType);
        } catch (Exception e) {
            System.out.println("Nieprawidłowy format polecenia ustaw_pole.");
        }
        return null;
    }
    private SelectAlgorithmEvent wybierz_algorytm() {
        String selectedAlgorithmString;
        try {
            selectedAlgorithmString = input.next();
            return new SelectAlgorithmEvent(MEM, selectedAlgorithmString);
        } catch (Exception e) {
            System.out.println("Nieprawidłowy format polecenia wybierz_algorytm.");
        }
        return null;
    }
    private SolveMazeEvent znajdz_najkrotsza_sciezke() {
        return new SolveMazeEvent(MEM);
    }
}