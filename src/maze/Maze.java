package maze;

import java.util.HashSet;

import graph.*;

abstract class AbstractMaze extends TypicalUndirectedGraph<Field, Edge<Field>> {
    protected final boolean checkCorrectUse = true; // Sprawdzamy, czy używamy labiryntu poprawnie
    protected int num_cols;
    protected int num_rows;
    protected int max_cols;
    protected int max_rows;

    protected AbstractMaze() {
        super(Field.class,(Class<Edge<Field>>)(Class<?>)Edge.class);
        //super(Field.class, MazeSegment.class);
    }
    public abstract void addField();
    public abstract void addField(int type);
    public abstract void setFieldType(Field F, int type);
    public abstract Field getField(int column, int row);
    // Poniższe 4 zwracają null jeśli nie istnieje takie Pole
    public abstract Field getFieldN(int column, int row);
    public abstract Field getFieldE(int column, int row);
    public abstract Field getFieldS(int column, int row);
    public abstract Field getFieldW(int column, int row);

    public abstract int[] getFieldCoords(Field F); // (kolumna, wiersz)
    public abstract boolean areFieldsAdjacent(Field F1, Field F2);
    public abstract void addMazeSegment(Field F1, Field F2);
    public abstract int getCols();
    public abstract int getRows();
    public abstract void resize(int new_cols, int new_rows);
    public abstract boolean checkBounds(int column, int row); // throwException == false
    public abstract boolean checkBounds(int column, int row, boolean throwException);
}

public class Maze extends AbstractMaze {
    private int tempintarr[] = new int[2];

    public Maze(int num_cols, int num_rows) {
        super();
        this.num_cols = num_cols;
        this.num_rows = num_rows;
        this.max_cols = num_cols;
        this.max_rows = num_rows;
        for (int r = 0;r < num_rows;r++) {
            for (int c = 0;c < num_cols;c++) {
                addField();
                //System.out.println(this);
            }
        }
    }
    public void addField() {
        addField(Field.WHITE_FIELD);
    }
    public void addField(int type) {
        //Field F = new Field(lastVertexNum+1, type);
        Field F = new Field(lastVertexNum+1);
        addVertex(F);
        setFieldType(F, type); // ustawia odpowiednio sąsiednie odcinki labiryntu
    }
    public void setFieldType(Field F, int type) {
        //System.out.println("Ustawiam typ na "+String.valueOf(type));
        F.type = type;
        getFieldCoords(F, false);
        int column, row;
        column = tempintarr[0];row = tempintarr[1];
        Field F0;
        if (!F.isBlack()) {
            F0 = getFieldN(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
            F0 = getFieldE(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
            F0 = getFieldS(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
            F0 = getFieldW(column, row);
            if (F0 != null && !F0.isBlack()) addMazeSegment(F, F0, false);
        }
    }
    public Field getField(int column, int row) {
        if (!checkCorrectUse || checkBounds(column, row, true))
            return getVertex((row-1)*num_cols+column);
        return getVertex((row-1)*num_cols+column);
    }
    public Field getFieldN(int column, int row) {
        if (checkBounds(column, row-1)) return getField(column, row-1);
        return null; // Tu możemy dojść, jeśli nie istnieje takie Pole
    }
    public Field getFieldE(int column, int row) {
        if (checkBounds(column+1, row)) return getField(column+1, row);
        return null; // Tu możemy dojść, jeśli nie istnieje takie Pole
    }
    public Field getFieldS(int column, int row) {
        if (checkBounds(column, row+1)) return getField(column, row+1);
        return null; // Tu możemy dojść, jeśli nie istnieje takie Pole
    }
    public Field getFieldW(int column, int row) {
        if (checkBounds(column-1, row)) return getField(column-1, row);
        return null; // Tu możemy dojść, jeśli nie istnieje takie Pole
    }
    public int[] getFieldCoords(Field F) {
        return getFieldCoords(F, checkCorrectUse);
    }
    private int[] getFieldCoords(Field F, boolean checkCorrectUse) {
        if (checkCorrectUse) {
            if (F == null) throw new IllegalArgumentException("Podane Pole nie może być nullem");
            tempintarr[0] = 1+(F.getNum()-1)%num_cols;
            tempintarr[1] = 1+(F.getNum()-1)/num_cols;
            checkBounds(tempintarr[0], tempintarr[1], true);
            return tempintarr;
        }
        tempintarr[0] = 1+(F.getNum()-1)%num_cols;
        tempintarr[1] = 1+(F.getNum()-1)/num_cols;
        return tempintarr;
    }
    public boolean areFieldsAdjacent(Field F1, Field F2) {
        return areFieldsAdjacent(F1, F2, checkCorrectUse);
    }
    private boolean areFieldsAdjacent(Field F1, Field F2, boolean checkCorrectUse) {
        int column, row;
        if (checkCorrectUse) {
            if (F1 == null || F2 == null) throw new IllegalArgumentException("Jedno z podanych Pól to null.");
            getFieldCoords(F1, true); // Czy współrzędne mają sens
            column = tempintarr[0];row = tempintarr[1];
            getFieldCoords(F2, true); // Czy współrzędne mają sens
            return areFieldsAdjacent(column, row, tempintarr[0], tempintarr[1]);
        }
        getFieldCoords(F1, false);
        column = tempintarr[0];row = tempintarr[1];
        getFieldCoords(F2, false);
        return areFieldsAdjacent(column, row, tempintarr[0], tempintarr[1]);
    }
    private boolean areFieldsAdjacent(int c1, int r1, int c2, int r2) {
        // Tu zakładam, że argumenty mają sens
        if ((c1 == c2+1 && r1 == r2) || (c1+1 == c2 && r1 == r2) || (c1 == c2 && r1 == r2+1) || (c1 == c2 && r1+1 == r2)) return true;
        return false;
    }
    public void addMazeSegment(Field F1, Field F2) {
        addMazeSegment(F1, F2, checkCorrectUse);
    }
    private void addMazeSegment(Field F1, Field F2, boolean checkCorrectUse) {
        //System.out.printf("Dodaję odcinek labiryntu z %s do %s.\n",F1,F2);
        if (checkCorrectUse) {
            if (!areFieldsAdjacent(F1, F2, true)) throw new IllegalArgumentException("Pola powinny sąsiadować ze sobą, aby utworzyć Odcinek Labiryntu.");
        }
        addEdge(F1, F2);
    }
    public int getCols() {
        return num_cols;
    }
    public int getRows() {
        return num_rows;
    }
    public void resize(int new_cols, int new_rows) {
        // UWAGA: w obecnej implementacji wywołanie resize może spowodować utatę informacji o Polach i Odcinkach labiryntu
        // tzn. wszystkie Pola zostaną zresetowwane do białych
        // oraz jest niewydajne pamięciowo i czasowo
        if (new_cols > max_cols || new_rows > max_rows) {
            if (new_cols > max_cols) max_cols = new_cols;
            if (new_rows > max_rows) max_rows = new_rows;
            HashSet<Integer> KSet = new HashSet<Integer>(V.keySet());
            for (Integer FNum : KSet) {
                removeVertex(V.get(FNum));
            }
            num_cols = new_cols;num_rows = new_rows;
            for (int r = 0;r < num_rows;r++) {
                for (int c = 0;c < num_cols;c++) {
                    addField();
                }
            }
        } else {
            num_cols = new_cols;num_rows = new_rows;
        }
    }
    public boolean checkBounds(int column, int row) {
        return (column > 0 && column < num_cols+1 && row > 0 && row < num_rows+1);
    }
    public boolean checkBounds(int column, int row, boolean throwException) {
        if (throwException) {
            if (column > num_cols || column < 1) throw new IllegalArgumentException("Kolumna "+String.valueOf(column)+" jest poza labiryntem.");
            if (row > num_rows || row < 1) throw new IllegalArgumentException("Wiersz "+String.valueOf(row)+"jest poza labiryntem.");
            return true;
        }
        return checkBounds(column, row);
    }
}