// v2
package observer;

import java.util.HashSet;

import maze.MazeReader;

public class LoadMazeEvent extends MazeEvent {
    public static final EventStatusSet Statuses = new EventStatusSet(MazeEvent.Statuses);
    static {
        Statuses.put("START", "Rozpoczęto wczytywanie labiryntu...");
        Statuses.put("PROCESSING", "Trwa wczytywanie labiryntu...");
        Statuses.put("OK", "Udało się wczytać labirynt.");

        Statuses.put("LOAD_MAZE_UNRECOGNISED_FILE_TYPE", "Podany plik z labiryntem ma nierozpoznany format lub rozszerzenie.", true);
        Statuses.put("LOAD_MAZE_OPENING_ERROR", "Nie udało się otworzyć pliku z labiryntem.", true);
        Statuses.put("LOAD_MAZE_FILE_OPENED", "Udało się otworzyć plik z labiryntem.", false);
        Statuses.put("LOAD_MAZE_FORMAT_ERROR", null, true);
        Statuses.put("LOAD_MAZE_FORMAT_VALIDATED","Podany plik z labiryntem ma prawidłowy format", false);
    }
    private final String filePath;
    private final String fileType;
    public static final HashSet<String> validfileTypes = new HashSet<>();
    static {
        validfileTypes.add("txt");
        validfileTypes.add("bin");
        validfileTypes.add("?");
    }

    
    public LoadMazeEvent(MazeEventManager MEM, String filePath) {
        this(MEM, filePath, "?");
    }

    public LoadMazeEvent(MazeEventManager MEM, String filePath, String fileType) {
        super(MEM, Type.LOAD_MAZE);
        //if (!validfileTypes.contains(fileType)) // Sprawdzane przy performAction()
        this.filePath = filePath;
        this.fileType = fileType;
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }

    public String getFilePath() {
        return filePath;
    }
    public String getFileType() {
        return fileType;
    }

    @Override
    protected void performAction() {
        super.performAction();
        switch (fileType) {
            case "txt":
                tryLoadingMaze(MEM.TMR, true);
                break;
            case "bin":
                tryLoadingMaze(MEM.BMR, true);
                break;
            case "?":
                tryLoadingMaze();
                break;
            default:
                setStatus("LOAD_MAZE_UNRECOGNISED_FILE_TYPE");
                break;
                //throw new IllegalArgumentException(); // Tu nie powinniśmy trafić, jest to sprawdzane wcześniej
        }
    }

    private boolean tryLoadingMaze(MazeReader MR, boolean notifyProgress) {
        if (!MR.open(getFilePath())) {
            setStatus("LOAD_MAZE_OPENING_ERROR");
            if (notifyProgress) MEM.notifyListenersInProgress(this);
            return false;
        }
        setStatus("LOAD_MAZE_FILE_OPENED");
        if (notifyProgress) MEM.notifyListenersInProgress(this);
        if (!MR.validateFormat()) {
            MR.close();
            setStatus("LOAD_MAZE_FORMAT_ERROR", MR.getErrorMsg());
            if (notifyProgress) MEM.notifyListenersInProgress(this);
            return false;
        }
        setStatus("LOAD_MAZE_FORMAT_VALIDATED");
        MEM.notifyListenersInProgress(this); // Jeśli format jest prawidłowy, to zawsze powiadom
        MR.read();
        MR.close();
        setStatus("OK");
        return true;
    }

    private boolean tryLoadingMaze() {
        for (MazeReader MR : MEM.MazeReaders) {
            if (tryLoadingMaze(MR, false)) {
                return true;
            }
        }
        setStatus("LOAD_MAZE_UNRECOGNISED_FILE_TYPE");
        return false;
    }
}