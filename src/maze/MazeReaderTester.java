package maze;

public class MazeReaderTester {
    public static void main(String[] args) {
        MazeReader Reader = new TextMazeReader();
        Reader.open("maze-test.txt");
        Maze M = null;
        if (Reader.validateFormat()) {
            M = Reader.read();
        }
        Reader.close();
        if (M != null) System.out.println(M);
    }
}
