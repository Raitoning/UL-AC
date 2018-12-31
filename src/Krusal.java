import java.util.ArrayList;
import java.util.Random;

public class Krusal {

    public Krusal() {

    }

    public static final Graph krusalApplied(Graph graph) {

        return addWithoutCycle(shuffled(graph), graph);
    }

    private static ArrayList<Edge> shuffled(Graph graph) {

        ArrayList<Edge> sAL = new ArrayList<>();

        Random r = new Random();

        for (Edge e : graph.edges()) {
            if (sAL.size() == 0) {
                sAL.add(e);
            } else {
                sAL.add(r.nextInt(sAL.size()), e);
            }
        }

        return sAL;
    }


    private static Graph addWithoutCycle(ArrayList<Edge> ae, Graph source) {

        Graph g = new Graph(source.vertices());
        g.copyCoordinates(source);


        for (Edge e : ae) {
            g.addEdge(e);
            System.out.println(g.isCyclic());
            if (g.isCyclic()) {
                g.rmEdge(e);
            }
        }

        return g;
    }

}
