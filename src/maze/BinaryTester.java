package maze;

public class BinaryTester {
    public static void main(String[] args) {
        MazeReader Reader = new BinaryMazeReader();
        Reader.open("maze.bin");
        Maze M = null;
        if (Reader.validateFormat()) {
            M = Reader.read();
        }
        Reader.close();
        if (M != null) {
            System.out.println(M);
        }
    }
}
