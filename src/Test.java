//package graphe;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * Décommenter les fonctions à exécuter/tester.
 */
public class Test {

    private static final int ALGORITHM_ITERATION = 1000000;
    private static final int LABYRINTH_SIZE = 20;
    private static final int LABYRINTH_ITERATION = 1000;


    private static void printLaby(Graph G, int size, String file) {

        {
            /* suppose que G est une grille de taille size x size et
           crée un .tex qui contient le labyrinthe correspondant */

            try {
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.println("\\documentclass{article}\\usepackage{tikz}\\begin{document}");
                writer.println("\\begin{tikzpicture}");

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i, j));
                        writer.println("\\draw (0.1,0.1) -- (0.4,0.1);");
                        writer.println("\\draw (0.6,0.1) -- (0.9,0.1);");
                        writer.println("\\draw (0.1,0.9) -- (0.4,0.9);");
                        writer.println("\\draw (0.6,0.9) -- (0.9,0.9);");
                        writer.println("\\draw (0.1,0.1) -- (0.1, 0.4);");
                        writer.println("\\draw (0.1,0.6) -- (0.1, 0.9);");
                        writer.println("\\draw (0.9,0.1) -- (0.9,0.4);");
                        writer.println("\\draw (0.9,0.6) -- (0.9,0.9);");
                        writer.println("\\end{scope}");
                    }
                }

                /* bord */
                for (int i = 0; i < size; i++) {
                    writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i, 0));
                    writer.println("\\draw(0.4,0.1) -- (0.6, 0.1);");
                    writer.println("\\end{scope}");
                    writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i, size - 1));
                    writer.println("\\draw(0.4,0.9) -- (0.6, 0.9);");
                    writer.println("\\end{scope}");
                    if (i > 0) {
                        writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", 0, i));
                        writer.println("\\draw(0.1,0.4) -- (0.1, 0.6);");
                        writer.println("\\end{scope}");

                    }
                    if (i < size - 1) {
                        writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", size - 1, i));
                        writer.println("\\draw(0.9,0.4) -- (0.9, 0.6);");
                        writer.println("\\end{scope}");

                    }
                    writer.println("\\draw (0,0.4) -- (0.1, 0.4);");
                    writer.println("\\draw (0,0.6) -- (0.1, 0.6);");
                    writer.println(String.format(Locale.US, "\\draw (%d, %d) ++ (0, 0.4)  -- ++ (-0.1, 0); ", size, size - 1));
                    writer.println(String.format(Locale.US, "\\draw (%d, %d) ++ (0, 0.6)  -- ++ (-0.1, 0); ", size, size - 1));

                }


                for (Edge e : G.edges()) {
                    int i = e.from % size;
                    int j = e.from / size;
                    writer.println(String.format(Locale.US, "\\begin{scope}[xshift=%dcm, yshift=%dcm]", i, j));
                    if (e.to == e.from + size) {
                        /* arête verticale */
                        if (!e.used) {
                            writer.println("\\draw (0.4,0.9) -- (0.6,0.9);");
                            writer.println("\\draw (0.4,1.1) -- (0.6,1.1);");
                        } else {
                            writer.println("\\draw (0.4,0.9) -- (0.4,1.1);");
                            writer.println("\\draw (0.6,0.9) -- (0.6,1.1);");
                        }
                    } else {
                        /* arête horizontale */

                        if (!e.used) {
                            writer.println("\\draw (0.9,0.4) -- (0.9,0.6);");
                            writer.println("\\draw (1.1,0.4) -- (1.1,0.6);");
                        } else {
                            writer.println("\\draw (0.9,0.4) -- (1.1,0.4);");
                            writer.println("\\draw (0.9,0.6) -- (1.1,0.6);");
                        }
                    }
                    writer.println("\\end{scope}");
                }
                writer.println("\\end{tikzpicture}");
                writer.println("\\end{document}");
                writer.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) {

        int size = 4;
        Graph graph = new Graph(0);
        Display d = new Display();

        HashMap<Graph, Integer> repartition = new HashMap<>();

        // Arbres couvrants
        // NOTE: La répartion contient 36 graphes couvrants et non 8.
        // Il doit s'agir de plusieurs les mêmes graphes mais avec l'ordre et/ou le sens des arêtes différents.

        // Algorithme de Kruskal
//        for (int i = 0; i < ALGORITHM_ITERATION; i++) {
//
//            // System.out.println("Execution #" + i);
//
//            graph = Kruskal.kruskalApplied(Graph.example());
//
//            if ((graph.isCyclic()) || (graph.edges().size() != 3)) {
//
//                System.out.println("ERROR");
//                break;
//            }
//
//            if(repartition.containsKey(graph)) {
//
//                repartition.replace(graph, repartition.get(graph) + 1);
//            } else {
//
//                repartition.put(graph, 0);
//            }
//        }

        // Algorithme d'Aldous-Broder
//        for (int i = 0; i < ALGORITHM_ITERATION; i++) {
//
//            // System.out.println("Execution #" + i);
//
//            AldousBroder aldousBroder = new AldousBroder(Graph.example());
//            graph = aldousBroder.randomMarch();
//
//            if((graph.isCyclic()) || (graph.edges().size() != 3)) {
//
//                System.out.println("ERROR");
//                break;
//            }
//
//            if(repartition.containsKey(graph)) {
//
//                repartition.replace(graph, repartition.get(graph) + 1);
//            } else {
//
//                repartition.put(graph, 0);
//            }
//        }


        // Algorithme de Wilson
        for (int i = 0; i < ALGORITHM_ITERATION; i++) {

            // System.out.println("Execution #" + i);

            Wilson wilson = new Wilson(Graph.example());
            graph = wilson.randomMarch();

            if ((graph.isCyclic()) || (graph.edges().size() != 3)) {

                System.out.println("ERROR");
                break;
            }

            if (repartition.containsKey(graph)) {

                repartition.replace(graph, repartition.get(graph) + 1);
            } else {

                repartition.put(graph, 0);
            }
        }

        // Génération de labyrinthe

        Graph labyrinthe = new Graph(0);

        // Algorithme de Kruskal
//        for (int i = 0; i < LABYRINTH_ITERATION; i++) {
//
//            labyrinthe = Graph.Grid(LABYRINTH_SIZE);
//            graph = Kruskal.kruskalApplied(Graph.Grid(LABYRINTH_SIZE));
//
//            // Pour certaines raisons, l'affichage dans le fichier LaTeX est inversé.
//            // Il faut prendre un nouveau graphe grille et retirer les arêtes de l'arbre couvrant
//            // Pour avoir un affichage correct.
//            for (Edge e: graph.edges()) {
//
//                for (Edge ee: labyrinthe.edges()) {
//
//                    if((e.from == ee.from && e.to == ee.to) || (e.from == ee.to && e.to == ee.from)) {
//
//                        labyrinthe.rmEdge(ee);
//                    }
//                }
//
//            }
//        }

        // Algorithme d'Aldous Broder
//        for (int i = 0; i < LABYRINTH_ITERATION; i++) {
//
//            labyrinthe = Graph.Grid(LABYRINTH_SIZE);
//            AldousBroder aldousBroder = new AldousBroder(Graph.Grid(LABYRINTH_SIZE));
//            graph = aldousBroder.randomMarch();
//
//            // Pour certaines raisons, l'affichage dans le fichier LaTeX est inversé.
//            // Il faut prendre un nouveau graphe grille et retirer les arêtes de l'arbre couvrant
//            // Pour avoir un affichage correct.
//            for (Edge e : graph.edges()) {
//
//                for (Edge ee : labyrinthe.edges()) {
//
//                    if ((e.from == ee.from && e.to == ee.to) || (e.from == ee.to && e.to == ee.from)) {
//
//                        labyrinthe.rmEdge(ee);
//                    }
//                }
//
//            }
//        }


        // Vérifier si toutes les probabilités d'apparition sont bien +- égales à 100 (imprécision conversion int/float)
        // Permet aussi d'affier les probabilités d'apparition.

        System.out.println("Nombre d'arbres couvrants: " + repartition.size());

        for (Graph g : repartition.keySet()) {

            System.out.println(((float) repartition.get(g) / ALGORITHM_ITERATION) * 100 + "%");
        }

        // Afficher un graphe
//        d.setImage(graph.toImage());

        // Afficher un labyrinthe
//        d.setImage(labyrinthe.toImage());

        // Enregister un graphe
//        printLaby(graph, size, "toto.tex");


        // Enregistrer un labyrinthe
//        printLaby(graph, size, "toto.tex");

        System.out.println("appuyez sur une touche");

        new Scanner(System.in).nextLine();
        d.close();
    }
}
