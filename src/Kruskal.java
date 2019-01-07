import java.util.ArrayList;
import java.util.Collections;

/**
 * @author VIRTE Bryan
 */
public class Kruskal {

    static Graph kruskalApplied(Graph graph) {

//        return addWithoutCycle(shuffled(graph), graph);

        ArrayList<Edge> edges = graph.edges();
        Collections.shuffle(edges);

        return addWithoutCycle(edges, graph);
    }

    // NOTE: Remplacé par Collections.shuffle();
//    private static ArrayList<Edge> shuffled(Graph graph) {
//
//        ArrayList<Edge> sAL = new ArrayList<>();
//
//        Random r = new Random();
//
//        for (Edge e : graph.edges()) {
//
//            if (sAL.size() == 0) {
//
//                sAL.add(e);
//            } else {
//
//                sAL.add(r.nextInt(sAL.size()), e);
//            }
//        }
//
//        return sAL;
//    }

    private static Graph addWithoutCycle(ArrayList<Edge> edges, Graph source) {

        Graph g = new Graph(source.vertices());
        g.copyCoordinates(source);

        for (Edge e : edges) {

            g.addEdge(e);

            if (g.isCyclic()) {

                g.rmEdge(e);
            }
        }

        return g;
    }
}
