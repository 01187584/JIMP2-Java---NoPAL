package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import graph_v2.Vertex;

public abstract class FieldState {
    protected final Field refField;
    protected static String str;
    protected static int tempintarr[]; //= new int[2];
    protected FieldState(Field F) {
        /*
         * FieldState powinno być tworzone tylko przez Field przy inicjacji!
         */
        refField = F;
    }
    protected void setState() {
        /*
         * Ustawia stan refField na ten stan
         */
        refField.setIsStartVertex(false);
        refField.setIsEndVertex(false);
    }
    protected Field getRefField() {
        return refField;
    }
    /*protected void setRefField(Field F) {
        refField = F;
    }*/
    protected String toString(boolean includeVertexNum) {
        if (includeVertexNum)
            str = String.valueOf(refField.getNum());
        else
            str = "  ";
        return str;
    }
    protected abstract HashSet<Vertex> getAdjacentVetices();
    protected HashSet<Vertex> getAdjacentVertices(boolean isTraversable) {
        Field.AdjacentVertices.clear();
        if (isTraversable) {
            tempintarr = refField.getRefMaze().getFieldCoords(refField);
            int column, row;
            column = tempintarr[0];row = tempintarr[1];
            Field F0;
            F0 = refField.getRefMaze().getFieldN(column, row);
            //System.out.println("Jestem w Polu o numerze "+String.valueOf(getNum())+" o współrzędnych ("+String.valueOf(column)+','+String.valueOf(row)+')');
            if (F0 != null && !(F0.getCurrentState() == Field.State.BLACK_FIELD)) Field.AdjacentVertices.add(F0);
            //System.out.println(F0);
            F0 = refField.getRefMaze().getFieldE(column, row);
            if (F0 != null && !(F0.getCurrentState() == Field.State.BLACK_FIELD)) Field.AdjacentVertices.add(F0);
            //System.out.println(F0);
            F0 = refField.getRefMaze().getFieldS(column, row);
            if (F0 != null && !(F0.getCurrentState() == Field.State.BLACK_FIELD)) Field.AdjacentVertices.add(F0);
            //System.out.println(F0);
            F0 = refField.getRefMaze().getFieldW(column, row);
            if (F0 != null && !(F0.getCurrentState() == Field.State.BLACK_FIELD)) Field.AdjacentVertices.add(F0);
            //System.out.println(F0);
        }
        return Field.AdjacentVertices;
    }

    //protected static abstract Color getMainColor();
    protected abstract Color getMainColor();
    protected abstract Color getPathColor();

    public void draw(Graphics G, int x, int y, int sizeofone) {
        if (refField.getIsPartOfPath()) G.setColor(getPathColor());
        else G.setColor(getMainColor());
        G.fillRect(x, y, sizeofone, sizeofone);
    }

    protected static Color blend( Color c1, Color c2, float ratio ) {
        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;
    
        int i1 = c1.getRGB();
        int i2 = c2.getRGB();
    
        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);
    
        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);
    
        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));
    
        return new Color( a << 24 | r << 16 | g << 8 | b );
    }
}