package maze;

public class MazeTester {
    // TODO - przekształcić to na test jednostkowy i może przenieść do Maze
    public static void main(String [] args) {
        Maze TestMaze = new Maze(3,3);
        //TestMaze.addVertex();TestMaze.addVertex();
        System.out.println(TestMaze);
        //TestGraph.addEdge(1,2);
        TestMaze.getField(1, 1).setFieldType(Field.ENTRANCE_FIELD);
        TestMaze.getField(3, 3).setFieldType(Field.EXIT_FIELD);
        System.out.println(TestMaze);
        TestMaze.resize(4, 4);
        TestMaze.getField(1, 1).setFieldType(Field.ENTRANCE_FIELD);
        TestMaze.getField(4, 4).setFieldType(Field.EXIT_FIELD);
        //TestMaze.destroyMaze();
        System.out.println(TestMaze);
    }
}