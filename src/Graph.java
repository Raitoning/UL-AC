import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

class Graph {
    private ArrayList<Edge>[] adj;
    private int[] coordX;
    private int[] coordY;
    private final int V;
    private int E;

    @SuppressWarnings("unchecked")
    public Graph(int N) {

        this.V = N;
        this.E = 0;
        adj = (ArrayList<Edge>[]) new ArrayList[N];
        for (int v = 0; v < N; v++) {
            adj[v] = new ArrayList<Edge>();
        }
        coordX = new int[N];
        coordY = new int[N];
        for (int v = 0; v < N; v++) {
            coordX[v] = 0;
        }
        for (int v = 0; v < N; v++) {
            coordY[v] = 0;
        }
    }

    static Graph example() {

        Graph g = new Graph(4);
        g.setCoordinate(0, 100, 100);
        g.setCoordinate(1, 300, 300);
        g.setCoordinate(2, 300, 100);
        g.setCoordinate(3, 100, 300);
        g.addEdge(new Edge(0, 1));
        g.addEdge(new Edge(0, 2));
        g.addEdge(new Edge(0, 3));
        g.addEdge(new Edge(1, 2));
        g.addEdge(new Edge(1, 3));
        return g;
    }

    static Graph Grid(int n) {

        Graph g = new Graph(n * n);
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                g.setCoordinate(n * i + j, 50 + (300 * i) / n, 50 + (300 * j) / n);
            }
        }

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i < n - 1) {
                    g.addEdge(new Edge(n * i + j, n * (i + 1) + j));
                }
                if (j < n - 1) {
                    g.addEdge(new Edge(n * i + j, n * i + j + 1));
                }
            }
        }
        return g;
    }

    public int vertices() {

        return V;
    }

    public void setCoordinate(int i, int x, int y) {

        coordX[i] = x;
        coordY[i] = y;
    }

    public void copyCoordinates(Graph g) {

        this.coordX = g.coordX;
        this.coordY = g.coordY;
    }

    public void addEdge(Edge e) {

        int v = e.from;
        int w = e.to;
        adj[v].add(e);
        adj[w].add(e);
    }

    public void rmEdge(Edge e) {

        int v = e.from;
        int w = e.to;
        adj[v].remove(e);
        adj[w].remove(e);
    }

    public ArrayList<Edge> adj(int v) {

        return new ArrayList<Edge>(adj[v]);
    }

    public ArrayList<Edge> edges() {

        ArrayList<Edge> list = new ArrayList<Edge>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj(v)) {
                if (e.from == v) {
                    list.add(e);
                }
            }
        }
        return list;
    }

    public BufferedImage toImage() {

        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, 400, 400);
        g2d.setColor(Color.BLACK);
        BasicStroke bs = new BasicStroke(2);
        g2d.setStroke(bs);
        // dessine les arêtes
        for (Edge e : edges()) {
            int i = e.from;
            int j = e.to;
            if (e.used) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.GRAY);
            }

            g2d.drawLine(coordX[i], coordY[i], coordX[j], coordY[j]);
        }
        // dessine les sommets
        for (int i = 0; i < V; i++) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(coordX[i] - 15, coordY[i] - 15, 30, 30);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(coordX[i] - 15, coordY[i] - 15, 30, 30);
            g2d.drawString(Integer.toString(i), coordX[i], coordY[i]);
        }

        return image;
    }


    public void writeFile(String s) {

        try {
            PrintWriter writer = new PrintWriter(s, "UTF-8");
            writer.println("digraph G{");
            for (Edge e : edges()) {
                writer.println(e.from + "->" + e.to + ";");
            }
            writer.println("}");
            writer.close();
        } catch (IOException e) {
        }
    }

    boolean isCyclic() {

        boolean visited[] = new boolean[vertices()];
        Arrays.fill(visited, false);

        for (int i = 0; i < vertices(); i++) {
            if (!visited[i]) {
                if (isCyclicRecur(i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCyclicRecur(int v, boolean visited[], int parent) {

        visited[v] = true;

        // Mark the current node as visited
        visited[v] = true;
        Edge e;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Edge> it = adj[v].iterator();
        while (it.hasNext()) {
            e = it.next();

            // If an adjacent is not visited, then recur for that
            // adjacent

            int i;
            if (e.to == v) {
                i = e.from;
            } else {
                i = e.to;
            }

            if (!visited[i]) {
                if (isCyclicRecur(i, visited, v))
                    return true;
            }

            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (i != parent)
                return true;
        }
        return false;

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Graph graph = (Graph) o;
        return V == graph.V &&
                E == graph.E &&
                Arrays.equals(adj, graph.adj) &&
                Arrays.equals(coordX, graph.coordX) &&
                Arrays.equals(coordY, graph.coordY);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(V, E);
        result = 31 * result + Arrays.hashCode(adj);
        result = 31 * result + Arrays.hashCode(coordX);
        result = 31 * result + Arrays.hashCode(coordY);
        return result;
    }
}
