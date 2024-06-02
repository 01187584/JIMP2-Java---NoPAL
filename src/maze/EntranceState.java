package maze;

import java.awt.Color;
import java.util.HashSet;

import util.ConsoleColors;
import graph_v2.Vertex;

public class EntranceState extends FieldState {
    private static final Color MainColor = Color.green;
    private static final Color PathColor = blend(MainColor, Color.blue , 0.6f);
    protected EntranceState(Field F) {
        super(F);
    }

    @Override
    protected void setState() {
        super.setState();
        refField.setIsStartVertex(true);
    }

    @Override
    protected String toString(boolean includeVertexNum) {
        return ConsoleColors.GREEN_BACKGROUND_BRIGHT+super.toString(includeVertexNum)+ConsoleColors.RESET;
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