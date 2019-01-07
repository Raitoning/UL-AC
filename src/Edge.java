import java.util.Objects;

class Edge {

    int from;
    int to;
    boolean used;

    Edge(int x, int y) {

        this.from = x;
        this.to = y;
        this.used = false;
    }

    final int other(int v) {

        if (this.from == v) {
            return this.to;
        } else {
            return this.from;
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge edge = (Edge) o;
        return from == edge.from &&
                to == edge.to &&
                used == edge.used;
    }

    @Override
    public int hashCode() {

        return Objects.hash(from, to, used);
    }
}
