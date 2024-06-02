package maze;

import java.awt.Color;
import java.util.HashSet;

import util.ConsoleColors;
import graph_v2.Vertex;

public class ExitState extends FieldState {
    private static final Color MainColor = Color.red;
    private static final Color PathColor = blend(MainColor, Color.blue , 0.6f);
    protected ExitState(Field F) {
        super(F);
    }

    @Override
    protected void setState() {
        super.setState();
        refField.setIsEndVertex(true);
    }

    @Override
    protected String toString(boolean includeVertexNum) {
        return ConsoleColors.RED_BACKGROUND+super.toString(includeVertexNum)+ConsoleColors.RESET;
    }

    @Override
    public HashSet<Vertex> getAdjacentVetices() {
        return super.getAdjacentVertices(true);
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