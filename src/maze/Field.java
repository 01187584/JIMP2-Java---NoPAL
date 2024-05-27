package maze;

import java.util.HashSet;
import java.util.Random;
import util.ConsoleColors;

import graph_v2.Vertex;
import graph_v2.SimpleVertex;

/*abstract class AbstractField extends SimpleVertex {
    protected int type; // Nie ustawiać manualnie atrybutu type, tylko użyć Maze.setFieldType
    public static final int WHITE_FIELD = 0;
    public static final int BLACK_FIELD = 1;
    public static final int ENTRANCE_FIELD = 2;
    public static final int EXIT_FIELD = 3;

    protected AbstractField(Maze M) {
        super(M);
    }
    //public abstract void setRandom();
    public abstract boolean isEntranceField();
    public abstract boolean isExitField();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}*/

public class Field extends SimpleVertex {
    private int type; // Nie ustawiać manualnie atrybutu type, tylko użyć Maze.setFieldType
    public static final int WHITE_FIELD = 0;
    public static final int BLACK_FIELD = 1;
    public static final int ENTRANCE_FIELD = 2;
    public static final int EXIT_FIELD = 3;
    private static final Random rand = new Random();
    private static HashSet<Vertex> AdjacentVertices = new HashSet<Vertex>(4);
    private static int tempintarr[]; //= new int[2];
    public Field(Maze M) {
        this(M, BLACK_FIELD);
    }
    public Field(Maze M, int type) {
        super(M);
        this.type = type;
    }
    public static int getRandomType() {
        if (randrange(0, 1) > 0) return WHITE_FIELD;
        else return BLACK_FIELD;
    }
    public void setRandomType() {
        setFieldType(getRandomType());
    }
    private static int randrange(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
    private Maze getRefMaze() {
        return (Maze)refGraph;
    }
    public void setFieldType(int type) {
        //System.out.println("Ustawiam typ na "+String.valueOf(type));
        //getRefMaze().getFieldCoords(F);
        //int column, row;
        //column = tempintarr[0];row = tempintarr[1];
        //Field F0;

        //if (F.isWhite()) {
        //    // TRZEBA jeszcze usunąć przecież krawędzie!
        //}
        this.type = type;
        
        /*
        if (!F.isBlack()) {
            F0 = getFieldN(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
            F0 = getFieldE(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
            F0 = getFieldS(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
            F0 = getFieldW(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
        }*/
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
    public int getType() {
        return type;
    }

    protected String toString(boolean includeVertexNum) {
        String str;
        if (includeVertexNum)
            str = String.valueOf(VertexNum);
        else
            str = "  ";
        switch (type) {
            case WHITE_FIELD:
                return ConsoleColors.WHITE_BACKGROUND_BRIGHT+str+ConsoleColors.RESET;
            case BLACK_FIELD:
                return ConsoleColors.BLACK_BACKGROUND+str+ConsoleColors.RESET;
            case ENTRANCE_FIELD:
                return ConsoleColors.GREEN_BACKGROUND+str+ConsoleColors.RESET;
            case EXIT_FIELD:
                return ConsoleColors.RED_BACKGROUND+str+ConsoleColors.RESET;
            default:
                throw new IllegalAccessError("Coś poszło nie tak: Pole ma nieznany typ:"+String.valueOf(type));
        }
        //return "A";
        //return String.valueOf(type)+' '+String.valueOf(VertexNum);
    }

    @Override
    public String toString() {
        return toString(true);
    }

    @Override
    public HashSet<Vertex> getAdjacentVetices() {
        AdjacentVertices.clear();
        tempintarr = getRefMaze().getFieldCoords(this);
        int column, row;
        column = tempintarr[0];row = tempintarr[1];
        Field F0;
        F0 = getRefMaze().getFieldN(column, row);
        //System.out.println("Jestem w Polu o numerze "+String.valueOf(getNum())+" o współrzędnych ("+String.valueOf(column)+','+String.valueOf(row)+')');
        if (F0 != null && !F0.isBlack()) AdjacentVertices.add(F0);
        //System.out.println(F0);
        F0 = getRefMaze().getFieldE(column, row);
        if (F0 != null && !F0.isBlack()) AdjacentVertices.add(F0);
        //System.out.println(F0);
        F0 = getRefMaze().getFieldS(column, row);
        if (F0 != null && !F0.isBlack()) AdjacentVertices.add(F0);
        //System.out.println(F0);
        F0 = getRefMaze().getFieldW(column, row);
        if (F0 != null && !F0.isBlack()) AdjacentVertices.add(F0);
        //System.out.println(F0);
        return AdjacentVertices;
    }

    @Override
    public void connectWith(Vertex V) {
        throw new UnsupportedOperationException("Pola labiryntu same łączą się ze sobą na podstawie ich typu przy każdym użyciu metody getAdjacentVetices()");
    }
}