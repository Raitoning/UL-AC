import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * @author PELTE Gaëtan
 */
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

        while (run) {

            // Prendre tout les voisins.
            for (Edge e : sourceEdges) {

                if (e.from == vertex || e.to == vertex) {

                    availableEdges.add(e);
                }
            }

            // Choisir une arête au hazard.
            Edge randomEdge = availableEdges.get(random.nextInt(availableEdges.size()));

            // Se déplacer sur le voisin.
            if (vertex == randomEdge.from) {

                vertex = randomEdge.to;
            } else {

                vertex = randomEdge.from;
            }

            // Ajouter le noeud courant à l'historique.
            history.add(vertex);

            // Nettoyer les voisins disponibles
            availableEdges.clear();

            // Si on as atteint un noeud marqué visité.
            if (visited[vertex]) {

                run = false;

                Arrays.fill(numberOfVisits, 0);

                // Si tous les noeuds ont été visité qu'une seule fois
                for (int i = 0; i < history.size(); i++) {

                    // Si un noeud n'as pas été compté visité.
                    if (numberOfVisits[history.get(i)] == 0) {

                        numberOfVisits[history.get(i)]++;
                    } else {

                        // Si le noeud à déjà été compté visité, il y à une boucle.
                        loopingVertex = history.get(i);

                        // Enlever la première boucle détectée.
                        removeLoops(history, loopingVertex);

                        // Choisir un noeud qui n'as pas déjà été visité.
                        while (visited[vertex]) {

                            vertex = random.nextInt(graph.vertices());
                        }

                        // Redémarrer la vérification de boucles.
                        i = -1;
                        Arrays.fill(numberOfVisits, 0);
                    }
                }

                // Marquer tous les noeuds de l'historique visités.
                for (Integer j : history) {

                    visited[j] = true;
                }

                // Ajouter toutes les arrêtes visitées.
                for (int j = 0; j < history.size() - 1; j++) {

                    for (Edge e : sourceEdges) {

                        if ((e.from == history.get(j) && e.to == history.get(j + 1)) || (e.to == history.get(j) && e.from == history.get(j + 1))) {

                            if (!outputGraph.edges().contains(e)) {

                                outputGraph.addEdge(e);
                            }
                        }
                    }
                }

                // Nettoyer les voisins disponibles
                history.clear();

                // Regarder si tous les noeuds on étés visités.
                for (boolean b : visited) {

                    run = false;

                    // Si non, on relance l'algorithme.
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

        // Obtenir l'index de la première occurence du noeud générant la boucle
        int firstOccurence = history.indexOf(loopingVertex);

        // Obtenir l'index de la dernière occurence du noeud générant la boucle
        int lastOccurence = history.lastIndexOf(loopingVertex);

        // Supprimer tous les noeuds entre la première et la dernière occurence du noeud générant la boucle.
        for (int toRemove = lastOccurence - 1 - firstOccurence; toRemove > -1; toRemove--) {

            history.remove(firstOccurence);
        }
    }
}
