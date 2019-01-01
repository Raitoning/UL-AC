import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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

            // Getting all the neighbors.
            for (Edge e : sourceEdges) {

                if (e.from == vertex || e.to == vertex) {

                    availableEdges.add(e);
                }
            }

            // Choosing the next edge randomly.
            Edge randomEdge = availableEdges.get(random.nextInt(availableEdges.size()));

            // Adding the selected edge to the new graph
            // and marking it visited on the source graph.
            for (Edge e : sourceEdges) {

                if (e.from == randomEdge.from && e.to == randomEdge.to) {

                    outputGraph.addEdge(e);

                    // Checking if the output graph if now cyclic or not.
                    if (outputGraph.isCyclic()) {

                        outputGraph.rmEdge(e);
                    }
                    break;
                }
            }

            visited[vertex] = true;

            // Going onto the neighbor.
            if (vertex == randomEdge.from) {

                vertex = randomEdge.to;
            } else {

                vertex = randomEdge.from;
            }

            // Cleaning all the potential neighbors.
            availableEdges.clear();

            // Checking if all vetices have been visited.
            for (boolean b : visited) {

                // If not, continue to run the algorithm.
                if (!b) {

                    run = true;
                    break;
                } else {

                    // Else, stop the algorithm.
                    run = false;
                }
            }
        }

        return outputGraph;
    }
}
