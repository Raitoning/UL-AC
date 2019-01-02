import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Wilson {

    private Graph graph;
    private boolean run;

    public Wilson(Graph graph) {

        this.graph = graph;

        run = true;
    }

    public Graph randomMarch() {

        Random random = new Random();

        Graph outputGraph = new Graph(graph.vertices());
        outputGraph.copyCoordinates(graph);

        ArrayList<Edge> sourceEdges = graph.edges();
        ArrayList<Edge> availableEdges = new ArrayList<>();
        ArrayList<Integer> history = new ArrayList<>();

        boolean[] visited = new boolean[graph.vertices()];
        Arrays.fill(visited, false);

        int[] numberOfVisits = new int[graph.vertices()];
        Arrays.fill(numberOfVisits, 0);

        int target = random.nextInt(graph.vertices());
        visited[target] = true;

        int vertex = target;

        int loopingVertex;

        while (vertex == target) {

            vertex = random.nextInt(graph.vertices());
        }

        history.add(vertex);
//        visited[target] = true;

        while (run) {

            // Getting all the neighbors.
            for (Edge e : sourceEdges) {

                if (e.from == vertex || e.to == vertex) {

                    availableEdges.add(e);
                }
            }

            // Choosing the next edge randomly.
            Edge randomEdge = availableEdges.get(random.nextInt(availableEdges.size()));

            // Going onto the neighbor.
            if (vertex == randomEdge.from) {

                vertex = randomEdge.to;
            } else {

                vertex = randomEdge.from;
            }

            // Adding the current vertex to the history.
            history.add(vertex);

            // Cleaning all the potential neighbors.
            availableEdges.clear();

            // If we reached the target vertex.
            if (visited[vertex]) {

                run = false;

                Arrays.fill(numberOfVisits, 0);

                // Check if all vertices have been visited only once.
                for (int i = 0; i < history.size(); i++) {

                    // If a vertex hasn't been visited yet, mark it visited.
                    if (numberOfVisits[history.get(i)] == 0) {

                        numberOfVisits[history.get(i)]++;
                    } else {

                        // A vertex has been visited twice, there is a loop.
                        loopingVertex = history.get(i);

                        // Remove the first loop detected
                        removeLoops(history, loopingVertex);

                        // Select a new random vertex that hasn't been visited yet.
                        while (visited[vertex]) {

                            vertex = random.nextInt(graph.vertices());
                        }

                        // Restart the loop checking.
                        i = -1;
                        Arrays.fill(numberOfVisits, 0);
                    }
                }

                // Mark all visited verticex visited.
                for (Integer j : history) {

                    visited[j] = true;
                }

                // Add all the visited edges.
                for (int j = 0; j < history.size() - 1; j++) {

                    for (Edge e : sourceEdges) {

                        if ((e.from == history.get(j) && e.to == history.get(j + 1)) || (e.to == history.get(j) && e.from == history.get(j + 1))) {

                            if (!outputGraph.edges().contains(e)) {

                                outputGraph.addEdge(e);
                            }
                        }
                    }
                }

                // Clear the history of visited vertices.
                history.clear();

                // Checking if all vertices have been visited.
                for (boolean b : visited) {

                    run = false;

                    if (!b) {

                        run = true;
                        break;
                    }
                }
            }
        }

        return outputGraph;
    }

    private void removeLoops(ArrayList<Integer> history, int loopingVertex) {

        // Getting the index of the first occurence of the looping vertex.
        int firstOccurence = history.indexOf(loopingVertex);

        // Getting the index of the last occurence of the looping vertex.
        int lastOccurence = history.lastIndexOf(loopingVertex);

        // Removing all vertices between the first and the last occurence of the looping vertex.
        for (int toRemove = lastOccurence - 1 - firstOccurence; toRemove > -1; toRemove--) {

            history.remove(firstOccurence);
        }
    }
}
