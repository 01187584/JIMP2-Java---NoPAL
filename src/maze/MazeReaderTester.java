package maze;

public class MazeReaderTester {
    // TODO - przekształcić to na test jednostkowy i może przenieść do TextMazeReader
    public static void main(String[] args) {
        MazeReader Reader = new TextMazeReader();
        Reader.open("maze-test.txt");
        Maze M = null;
        if (Reader.validateFormat()) {
            M = Reader.read();
        }
        Reader.close();
        if (M != null) {
            System.out.println(M);
            M.resize(10,10);
            System.out.println(M);
        }
    }
}
