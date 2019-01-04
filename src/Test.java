//package graphe;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

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

        Graph graph = new Graph(size);
        Display d = new Display();


        // NOTE: Pour questions 3 et 4
//        for (int i = 0; i < ALGORITHM_ITERATION; i++) {
//
//            System.out.println("Execution #" + i);
//
//            graph = Krusal.krusalApplied(Graph.example());
//
//            if((graph.isCyclic()) || (graph.edges().size() != 3)) {
//
//                System.out.println("ERROR");
//                break;
//            }
//        }

//        for (int i = 0; i < ALGORITHM_ITERATION; i++) {
//
//            System.out.println("Execution #" + i);
//
//            AldousBroder aldousBroder = new AldousBroder(Graph.example());
//            graph = aldousBroder.randomMarch();
//
//            if((graph.isCyclic()) || (graph.edges().size() != 3)) {
//
//                System.out.println("ERROR");
//                break;
//            }
//        }


//        for (int i = 0; i < ALGORITHM_ITERATION; i++) {
//
//            System.out.println("Execution #" + i);
//
//            Wilson wilson = new Wilson(Graph.example());
//            graph = wilson.randomMarch();
//
//            if ((graph.isCyclic()) || (graph.edges().size() != 3)) {
//
//                System.out.println("ERROR");
//                break;
//            }
//        }

        Graph labyrinthe = new Graph(0);

//        for (int i = 0; i < LABYRINTH_ITERATION; i++) {
//
//            labyrinthe = Graph.Grid(LABYRINTH_SIZE);
//            graph = Krusal.krusalApplied(Graph.Grid(LABYRINTH_SIZE));
//
//            // For some reasons, the display in the LaTeX file is inverted.
//            // We have to take a full grid graph and remove all edges int the covering tree
//            // To get the proper display.
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

        for (int i = 0; i < LABYRINTH_ITERATION; i++) {

            labyrinthe = Graph.Grid(LABYRINTH_SIZE);
            AldousBroder aldousBroder = new AldousBroder(Graph.Grid(LABYRINTH_SIZE));
            graph = aldousBroder.randomMarch();

            // For some reasons, the display in the LaTeX file is inverted.
            // We have to take a full grid graph and remove all edges int the covering tree
            // To get the proper display.
            for (Edge e : graph.edges()) {

                for (Edge ee : labyrinthe.edges()) {

                    if ((e.from == ee.from && e.to == ee.to) || (e.from == ee.to && e.to == ee.from)) {

                        labyrinthe.rmEdge(ee);
                    }
                }

            }
        }

        d.setImage(labyrinthe.toImage());

        printLaby(labyrinthe, size, "toto.tex");

        System.out.println("appuyez sur une touche");

        new Scanner(System.in).nextLine();
        d.close();
    }
}
