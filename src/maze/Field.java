package maze;

import java.util.Random;

import graph.*;;

abstract class AbstractField extends Vertex {
    public int type;
    public static final int WHITE_FIELD = 0;
    public static final int BLACK_FIELD = 1;
    public static final int ENTRANCE_FIELD = 2;
    public static final int EXIT_FIELD = 3;

    protected AbstractField(int numField) {
        super(numField);
    }
    public abstract void setRandom();
    public abstract boolean isEntranceField();
    public abstract boolean isExitField();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}

public class Field extends AbstractField {
    private static final Random rand = new Random();
    public Field(int numField) {
        this(numField, WHITE_FIELD);
    }
    public Field(int numField, int type) {
        super(numField);
        //System.out.println(VertexNum);
        this.type = type;
    }
    public void setRandom() {
        if (randrange(0, 1) > 0) type = WHITE_FIELD;
        else type = BLACK_FIELD;
    }
    private int randrange(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
    public boolean isEntranceField() {
        return type == ENTRANCE_FIELD;
    }
    public boolean isExitField() {
        return type == EXIT_FIELD;
    }
    public boolean isWhite() {
        return type == WHITE_FIELD;
    }
    public boolean isBlack() {
        return type == BLACK_FIELD;
    }
}