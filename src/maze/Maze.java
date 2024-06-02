package maze;

//import java.util.HashMap;
//import java.util.HashSet;

import graph_v2.*;

//import maze.TextMazeReader; // Aby uzyskać TextMazeReader.WHITE_FIELD itd. do reprezentacji tekstowej dla toString()
//import java.util.InputMismatchException;
//import java.util.Iterator;
//import java.util.Map;

/*abstract class AbstractMaze extends TypicalUndirectedGraph<Field, Edge<Field>> {
    protected final static boolean checkCorrectUse = true; // Sprawdzamy, czy używamy labiryntu poprawnie
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
}*/

//public class Maze extends AbstractMaze {
public class Maze extends TypicalUndirectedGraph {
    // TODO - zmienić checkCorrectUse na CHECK_CORRECT_USE, bo jest final
    private final static boolean checkCorrectUse = true; // Sprawdzamy, czy używamy labiryntu poprawnie
    private int num_cols;
    private int num_rows;
    private int max_cols;
    private int max_rows;
    private int tempintarr[] = new int[2];
    private static Edge SampleMazeSegment = new MazeSegment();

    public Maze(int num_cols, int num_rows) {
        super();
        buildMaze(num_cols, num_rows);
    }

    private void buildMaze(int num_cols, int num_rows) { // Obecnie używane przez konstruktor i resize
        //TODO: do poprawy - nie powinno być używane przez resize do robienia całego labiryntu od nowa
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
    public void destroyMaze() {
        destroy();
        num_cols = -1;
        num_rows = -1;
        max_cols = -1;
        max_rows = -1;
        tempintarr[0] = -1;
        tempintarr[1] = -1;
        tempintarr = null;
    }

    @Override
    public Vertex addVertex() {
        return new Field(this);
    }

    public void addField() {
        addField(Field.WHITE_FIELD);
    }
    public void addField(int type) {
        //Field F = new Field(lastVertexNum+1, type);
        //Field F = new Field(lastVertexNum+1);
        //addVertex(F);
        //setFieldType(F, type); // ustawia odpowiednio sąsiednie odcinki labiryntu
        ((Field)addVertex()).setFieldType(type); // ustawia odpowiednio sąsiednie odcinki labiryntu
    }

    public Field getField(int column, int row) {
        if (!checkCorrectUse || checkBounds(column, row, true))
            return (Field)getVertex((row-1)*num_cols+column);
        return (Field)getVertex((row-1)*num_cols+column);
    }
    public Field getFieldN(int column, int row) {
        if (checkBounds(column, row-1)) return getField(column, row-1);
        return null; // Zwraca null, jeśli nie istnieje takie Pole
    }
    public Field getFieldE(int column, int row) {
        if (checkBounds(column+1, row)) return getField(column+1, row);
        return null; // Zwraca null, jeśli nie istnieje takie Pole
    }
    public Field getFieldS(int column, int row) {
        if (checkBounds(column, row+1)) return getField(column, row+1);
        return null; // Zwraca null, jeśli nie istnieje takie Pole
    }
    public Field getFieldW(int column, int row) {
        if (checkBounds(column-1, row)) return getField(column-1, row);
        return null; // Zwraca null, jeśli nie istnieje takie Pole
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
    /*
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
    private void removeMazeSegment(Field F1, Field F2, boolean checkCorrectUse) {
        if (checkCorrectUse) {
            if (!areFieldsAdjacent(F1, F2, true)) throw new IllegalArgumentException("Pola powinny sąsiadować ze sobą, aby utworzyć Odcinek Labiryntu.");
        }
        
    }*/
    public int getCols() {
        return num_cols;
    }
    public int getRows() {
        return num_rows;
    }
    /*public void resize(int new_cols, int new_rows) {
        // UWAGA: w obecnej implementacji wywołanie resize może spowodować utatę informacji o Polach i Odcinkach labiryntu
        // tzn. wszystkie Pola zostaną zresetowwane do białych
        // oraz jest niewydajne pamięciowo i czasowo
        System.out.println("RESIZE: "+getId());
        if (new_cols > max_cols || new_rows > max_rows) {
            if (new_cols > max_cols) max_cols = new_cols;
            if (new_rows > max_rows) max_rows = new_rows;
            /*HashSet<Integer> KSet = new HashSet<Integer>(V.keySet());
            for (Integer FNum : KSet) {
                removeVertex(V.get(FNum));
            }*/
            //destroy(); // Nie działa...
            // destroy:
            //HashMap<Integer, Field> newV = new HashMap<Integer, Field>(V);
            //HashMap<Integer, Field> newV = (HashMap<Integer, Field>) V.clone();
            /*HashMap<Integer, Field> newV = new HashMap<Integer, Field>();
            for (Integer FieldNum : V.keySet()) {
                newV.put(FieldNum, V.get(FieldNum));
            }
            for (Integer FieldNum : newV.keySet()) {
                //System.out.println("Próbuję usunąć Pole "+newV.get(FieldNum));
                removeVertex(newV.get(FieldNum));
            }*/
            /*for(Iterator<Map.Entry<Integer, Field>> it = newV.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Integer, Field> entry = it.next();
                //it.remove();
                System.out.println("Próbuję usunąć Pole "+entry.getValue());
                removeVertex(entry.getValue());
            }*/
            // TRZEBA POPRAWIĆ : nie usuwamy elementów newV i V

            /*lastVertexNum = 0; // Ostrożnie z tym

            num_cols = new_cols;num_rows = new_rows;
            for (int r = 0;r < num_rows;r++) {
                for (int c = 0;c < num_cols;c++) {
                    addField();
                }
            }
        } else {
            num_cols = new_cols;num_rows = new_rows;
        }
    }*/
    public void resize(int new_cols, int new_rows) {
        //UWAGA: w obecnej implementacji wywołanie resize usunie wszystkie Pola i OdcinkiLabiryntu oraz jest niewydajny!
        //TODO: do poprawy - resize nie powinno robić całego labiryntu od nowa
        removeAllVertices();
        buildMaze(new_cols, new_rows);
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
    // UWAGA: Wypisywanie labiryntu jest niewydajne!
    /*public String toString() {
        //String str = new String();
        System.gc(); // Tymczasowo, zwolnimy trochę pamięci
        StringBuilder str = new StringBuilder();
        str.append("Oto tekstowa reprezentacja labiryntu o id ");str.append(getId());str.append(":\n");
        char FieldText;
        for (int r = 1;r <= num_rows;r++) {
            for (int c = 1;c <= num_cols;c++) {
                switch (getField(c, r).getType()) {
                    case Field.WHITE_FIELD:
                        FieldText = TextMazeReader.WHITE_FIELD;
                        break;
                    case Field.BLACK_FIELD:
                        FieldText = TextMazeReader.BLACK_FIELD;
                        break;
                    case Field.ENTRANCE_FIELD:
                        FieldText = TextMazeReader.ENTRANCE_FIELD;
                        break;
                    case Field.EXIT_FIELD:
                        FieldText = TextMazeReader.EXIT_FIELD;
                        break;
                    default:
                        System.out.println("Nieznane Pole: "+getField(c, r).getType());
                        throw new InputMismatchException();
                }
                str.append(FieldText);
                //str += FieldText;
            }
            if (r != num_rows)
                str.append('\n');
                //str += '\n';
        }
        return str.toString();
    }*/
    public String toString() {
        //System.gc(); // Tymczasowo, zwolnimy trochę pamięci
        StringBuilder str = new StringBuilder();
        str.append("Oto tekstowo-graficzna reprezentacja labiryntu o id ");str.append(hashCode());str.append(":\n");
        for (int r = 1;r <= num_rows;r++) {
            for (int c = 1;c <= num_cols;c++) {
                str.append(getField(c, r).toString(false));
            }
            str.append('\n');
        }
        return str.toString();
    }

    @Override
    public Edge getSampleEdge() {
        return SampleMazeSegment;
    }
}