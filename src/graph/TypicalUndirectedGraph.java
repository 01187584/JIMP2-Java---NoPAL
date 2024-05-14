package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

// W tej implementacji nie można dodawać 2 takich samych krawędzi (z tego samego wierzchołka początkowego i końcowego)
public class TypicalUndirectedGraph<GraphVertex extends Vertex, GraphEdge extends Edge<GraphVertex>> extends AbstractGraph<GraphVertex, GraphEdge, HashSet<GraphVertex>> {
    private final static boolean checkCorrectUse = true; // Nie sprawdzamy, czy da się przerzucić obiekt na odpowiedni typ oraz czy wrzucamy wierzchołki o odpowiednim numerze
    private final int id;
    private static int last_id = 0;
    private HashMap<GraphVertex, HashSet<GraphEdge>> AdjListEdge = new HashMap<GraphVertex, HashSet<GraphEdge>>();

    // Trzeba tu określić jaki rodzaj wierzchołka i krawędzi chcemy użyć, np:
    // GraphVertexClass to np. Vertex.class
    // GraphEdgeClass to np. (Class<Edge<Vertex>>)(Class<?>)Edge.class    (tak, wiem, to obrzydliwe)
    public TypicalUndirectedGraph(Class<GraphVertex> GraphVertexClass, Class<GraphEdge> GraphEdgeClass) {
        super(GraphVertexClass, GraphEdgeClass);
        //V.add(null); // Aby liczyć wierzchołki od 1
        last_id++;
        id = last_id;
        //Graphs.add(this);
    }
    /*public void test() {
        System.out.println(getClass());
        System.out.println(getClass().getGenericSuperclass());
        System.out.println((ParameterizedType)(getClass().getGenericSuperclass()));
        System.out.println(((ParameterizedType)(getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
        Class<GraphVertex> test = (Class<GraphVertex>)(((ParameterizedType)(getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
        System.out.println(test);
    }*/
    public void addVertex(GraphVertex V) {
        if (checkCorrectUse && V.getNum() != lastVertexNum+1) throw new IllegalArgumentException("Numer wierzchołka dodawanego do grafu powinien być o 1 większy niż "+String.valueOf(lastVertexNum)+" a nie "+String.valueOf(V.getNum()));
        lastVertexNum = V.getNum();
        this.V.put((Integer)V.getNum(),V);
        HashSet<GraphVertex> HS = new HashSet<GraphVertex>(1); // Nie wiemy jak wiele może być krawędzi
        AdjacencyList.put(V, HS);
        AdjListEdge.put(V, new HashSet<GraphEdge>(1));
    }
    public GraphVertex addVertex() {
        GraphVertex V;
        try {
            V = GraphVertexClass.newInstance(); // dzięki Javie nie możemy użyć po prostu "new GraphVertex(lastVertexNum)"
            V.pseudoconstructor(lastVertexNum+1);
        } catch (Exception e) {
            System.out.println("Coś jest bardzo nie tak.");
            e.printStackTrace();
            return null;
        }
        addVertex(V);
        return V;
    }
    public void addVertices(int numVertices) {
        for (int i = 0;i < numVertices;i++) {
            addVertex();
        }
    }
    public GraphVertex getVertex(int numVertex) {
        //System.out.printf("AAA: %d\n",numVertex);
        //System.out.println(V.get(numVertex));
        return V.get(numVertex);
    }
    public void addEdge(GraphEdge E) {
        this.E.add(E);
        /*if (AdjacencyList.get(E.start) == null) {
            HashSet<GraphVertex> HS = new HashSet<GraphVertex>(1);
            AdjacencyList.put(E.start, HS);
        }*/
        //System.out.println(E);
        //System.out.println(E.start);
        //System.out.println(E.getstart()); // E.start != E.getstart() ?!?
        //System.out.println(AdjacencyList.get(E.getstart()));
        //System.out.println("Auć");
        AdjacencyList.get(E.start).add(E.end);
        // Graf nieskierowany:
        /*if (AdjacencyList.get(E.end) == null) {
            HashSet<GraphVertex> HS = new HashSet<GraphVertex>(1);
            AdjacencyList.put(E.end, HS);
        }*/
        AdjacencyList.get(E.end).add(E.start);
        AdjListEdge.get(E.start).add(E);
        AdjListEdge.get(E.end).add(E);
    }
    public void addEdge(int numV1, int numV2) {
        addEdge(getVertex(numV1), getVertex(numV2));
    }
    public void addEdge(GraphVertex V1, GraphVertex V2) {
        GraphEdge E;
        try {
            E = GraphEdgeClass.newInstance();
            E.pseudoconstructor(V1, V2);
            // dzięki Javie nie możemy użyć po prostu "new GraphEdge(V1, V2)"
        } catch (Exception e) {
            System.err.println("Coś jest bardzo nie tak.");
            e.printStackTrace();
            E = null;
        }
        addEdge(E);
    }
    public void removeVertex(GraphVertex V) {
        this.V.remove(V.getNum());
        HashSet<GraphEdge> HS = AdjListEdge.get(V);
        HashSet<GraphEdge> newHS = new HashSet<GraphEdge>(HS);
        for (GraphEdge Edg : newHS) {
            removeEdge(Edg);
            HS.remove(Edg);
        }
        // TRZEBA POPRAWIĆ: robimy nowy newHS i potem go nie czyścimy
        AdjListEdge.remove(V);
        startV.remove(V);endV.remove(V); // Jeśli nie ma, to nic się nie stanie\
    }
    public void removeEdge(GraphEdge E) {
        // To powinno działać w miarę szybko w tej implementacji dzięki temu, że
        // AdjacencyListCollection to HashSet<GraphVertex>
        if (E.start == null) return;
        this.E.remove(E);
        AdjacencyList.get(E.start).remove(E.end);
        AdjacencyList.get(E.end).remove(E.start);
        E.remove();
    }
    public void destroy() {
        /*HashSet<Integer> KSet = new HashSet<Integer>(V.keySet());
        for (Integer VerNum : KSet) {
            removeVertex(V.get(VerNum));
        }*/
        /*HashSet<Integer> KSet = new HashSet<Integer>(V.keySet());
        for (Iterator<Integer> i = KSet.iterator(); i.hasNext();) {
            Integer element = i.next();
            //i.remove();
            removeVertex(V.get(element));
        }*/
        HashMap<Integer, GraphVertex> newV = new HashMap<Integer, GraphVertex>(V);

        for(Iterator<Map.Entry<Integer, GraphVertex>> it = newV.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, GraphVertex> entry = it.next();
            //it.remove();
            removeVertex(entry.getValue());
        }
        // TRZEBA POPRAWIĆ : nie usuwamy elementów newV i V
    }
    public HashSet<GraphVertex> getAdjacent(GraphVertex V) {
        return AdjacencyList.get(V);
    }
    public boolean equals(Object o) {
        if (!checkCorrectUse) {
            if (((TypicalUndirectedGraph<GraphVertex, GraphEdge>)o).id != id) return false;
            return true;
        }
        if (!(o instanceof TypicalUndirectedGraph)) return false;
        try {
            if (((TypicalUndirectedGraph<GraphVertex, GraphEdge>)o).id != id) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        return id;
    }
    public String toString() {
        String s = new String();
        s += "V: {";
        for (Integer VerNum : V.keySet()) {
            s += V.get(VerNum).toString();
            s += ',';
        }
        s += "}\nE: {";
        for (GraphEdge Edg : E) {
            s += Edg.toString();
            s += ',';
        }
        s += "}\nLista sąsiedztwa:\n[\n";
        for (GraphVertex Vert : AdjacencyList.keySet()) {
            s += Vert.toString()+" : "+AdjacencyList.get(Vert).toString()+",\n";
        }
        s += "]";
        return s;
    }
    public HashMap<Integer, GraphVertex> getV() {
        return V;
    }
    public HashSet<GraphEdge> getE() {
        return E;
    }
    public HashSet<GraphVertex> getStartV() {
        return startV;
    }
    public HashSet<GraphVertex> getEndV() {
        return endV;
    }
    public int getId() {
        return id;
    }
}