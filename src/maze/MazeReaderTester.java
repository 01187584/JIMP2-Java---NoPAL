package maze;

public class MazeReaderTester {
    public static void main(String[] args) {
        MazeReader Reader = new TextMazeReader("test");
        Reader.read();
        Reader.close();
    }
}
