package maze;

import java.awt.Color;
import java.util.HashSet;

import util.ConsoleColors;
import graph_v2.Vertex;

public class BlackState extends FieldState {
    private static final Color MainColor = Color.black;
    private static final Color PathColor = blend(MainColor, Color.blue , 0.6f);
    protected BlackState(Field F) {
        super(F);
    }

    @Override
    public char toChar() {
        return 'X';
    }

    @Override
    protected String toString(boolean includeVertexNum) {
        return ConsoleColors.BLACK_BACKGROUND+super.toString(includeVertexNum)+ConsoleColors.RESET;
    }

    @Override
    public HashSet<Vertex> getAdjacentVetices() {
        return super.getAdjacentVertices(false);
    }

    @Override
    protected Color getMainColor() {
        return MainColor;
    }

    @Override
    protected Color getPathColor() {
        return PathColor;
    }
}