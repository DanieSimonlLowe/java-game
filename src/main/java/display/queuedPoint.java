package display;

public class queuedPoint implements Comparable<queuedPoint> {
    final protected int x;
    final protected int y;
    final protected int proirty;


    protected queuedPoint(int x, int y, int proirty) {
        this.x = x;
        this.y = y;
        this.proirty = proirty;
    }

    @Override
    public int compareTo(queuedPoint o) {
        return this.proirty - o.proirty;
    }
}
