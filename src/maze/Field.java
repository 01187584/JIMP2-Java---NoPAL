package maze;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import graph_v2.Vertex;
import graph_v2.SimpleVertex;

public class Field extends SimpleVertex {
    private State currentState;
    private final HashMap<State, FieldState> allStates;
    public enum State {
        WHITE_FIELD,
        BLACK_FIELD,
        ENTRANCE_FIELD,
        EXIT_FIELD
    }
    private boolean isPartOfPath;
    private static final Random RAND = new Random();
    protected static HashSet<Vertex> AdjacentVertices = new HashSet<Vertex>(4); // Użwane bezpośrednio przez klasy dziedziczące po FieldState, a w innych miejscach trzeba użyć getAdjacentVertices
    public Field(Maze M) {
        this(M, State.BLACK_FIELD);        
    }
    public Field(Maze M, State initialState) {
        super(M);
        if (initialState == null) throw new NullPointerException();
        allStates = new HashMap<State, FieldState>();
        allStates.put(State.WHITE_FIELD, new WhiteState(this));
        allStates.put(State.BLACK_FIELD, new BlackState(this));
        allStates.put(State.ENTRANCE_FIELD, new EntranceState(this));
        allStates.put(State.EXIT_FIELD, new ExitState(this));
        this.currentState = initialState;
    }
    public static State getRandomState() {
        /*
         * Losowo zwraca białe lub czarne Pole
         */
        if (randrange(0, 1) > 0) return State.WHITE_FIELD;
        else return State.BLACK_FIELD;
    }
    public void setRandomState() {
        /*
         * Ustawia losowo na białe lub czarne
         */
        setFieldState(getRandomState());
    }

    private static int randrange(int min, int max) {
        return RAND.nextInt(max - min + 1) + min;
    }

    public void setFieldState(State state) {
        if (state == null) throw new NullPointerException();
        currentState = state;
        allStates.get(currentState).setState();
    }

    protected String toString(boolean includeVertexNum) {
        return allStates.get(currentState).toString(includeVertexNum);
    }

    @Override
    public String toString() {
        return toString(true);
    }

    @Override
    public HashSet<Vertex> getAdjacentVetices() {
        return allStates.get(currentState).getAdjacentVetices();
    }

    protected boolean getIsPartOfPath() {
        return isPartOfPath;
    }

    public void setIsPartOfPath(boolean to) { // TODO: Powinno być używane tylko przez SolveMazeEvent!
        isPartOfPath = to;
    }

    protected Maze getRefMaze() { // Do użycia tylko przez klasy dziedziczące po FieldState!
        return (Maze)refGraph;
    }

    public State getCurrentState() { // TODO: zmienić na protected
    //protected State getCurrentState() { // Do użycia tylko przez klasy dziedziczące po FieldState!
        return currentState;
    }

    public void draw(Graphics G, int x, int y, int sizeofone) {
        allStates.get(currentState).draw(G, x, y, sizeofone);
    }

    @Override
    public void connectWith(Vertex V) {
        throw new RuntimeException("Pola labiryntu same łączą się ze sobą na podstawie ich typu przy każdym użyciu metody getAdjacentVetices()");
    }
}