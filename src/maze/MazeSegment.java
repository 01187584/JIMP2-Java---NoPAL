package maze;

import graph.*;

abstract class AbstractMazeSegment extends Edge<Field> {
    protected AbstractMazeSegment(Field start, Field end) {
        super(start,end);
    }
}

public class MazeSegment extends AbstractMazeSegment {
    public MazeSegment(Field start, Field end) {
        super(start,end);
    }
}