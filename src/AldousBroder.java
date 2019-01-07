import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author PELTE Gaëtan
 */
public class AldousBroder {

    private Graph graph;
    private boolean run;

    public AldousBroder(Graph graph) {

        this.graph = graph;

        run = true;
    }

    public Graph randomMarch() {

        Random random = new Random();

        Graph outputGraph = new Graph(graph.vertices());
        outputGraph.copyCoordinates(graph);

        ArrayList<Edge> sourceEdges = graph.edges();

        int vertex = random.nextInt(graph.vertices());

        ArrayList<Edge> availableEdges = new ArrayList<>();

        boolean[] visited = new boolean[graph.vertices()];

        Arrays.fill(visited, false);

        while (run) {

            // Prendre tout les voisins.
            for (Edge e : sourceEdges) {

                if (e.from == vertex || e.to == vertex) {

                    availableEdges.add(e);
                }
            }

            // Choisir une arête au hazard.
            Edge randomEdge = availableEdges.get(random.nextInt(availableEdges.size()));

            // L'ajouter au nouveau graphe
            // Et la marquer visitée sur le graphe d'origine.
            for (Edge e : sourceEdges) {

                if (e.from == randomEdge.from && e.to == randomEdge.to) {

                    outputGraph.addEdge(e);

                    // Regarder si le nouveau graphe est cyclique ou non.
                    if (outputGraph.isCyclic()) {

                        outputGraph.rmEdge(e);
                    }
                    break;
                }
            }

            visited[vertex] = true;

            // Se déplacer sur le voisin.
            if (vertex == randomEdge.from) {

                vertex = randomEdge.to;
            } else {

                vertex = randomEdge.from;
            }

            // Nettoyer les voisins disponibles
            availableEdges.clear();

            // Regarder si tous les noeuds on étés visités.
            for (boolean b : visited) {

                // Si non, on relance l'algorithme.
                if (!b) {

                    run = true;
                    break;
                } else {

                    // Si oui, on arrête l'algorithme.
                    run = false;
                }
            }
        }

        return outputGraph;
    }
}
