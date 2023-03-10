import java.util.ArrayList;

public class Quadtree {

    private Quadtree TR;
    private Quadtree TL;
    private Quadtree BL;
    private Quadtree BR;
    private Rectangle boundary;
    private int capacity;
    private ArrayList<Point> points;
    private boolean divided;
    private int radius;

    public Quadtree(Rectangle boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>(4);
        this.divided = false;
        this.radius = 10;
    }

    public Quadtree getTR() {
        return TR;
    }

    public Quadtree getTL() {
        return TL;
    }

    public Quadtree getBL() {
        return BL;
    }

    public Quadtree getBR() {
        return BR;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isDivided() {
        return divided;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public Rectangle getBoundary() {
        return boundary;
    }

    public void insert(Point point) {
        if (!boundary.contains(point)) {
            return;
        }
        if (points.size() < capacity) {
            points.add(point);
        } else {
            if (!this.divided) {
                subdivide();
                this.divided = true;
            }
            TL.insert(point);
            TR.insert(point);
            BL.insert(point);
            BR.insert(point);
        }
    }

    public Point query(Rectangle range, Point first) {
        if (!boundary.intersects(range)) {
            return null;
        }
        for (Point point : points) {
            if (!point.equals(first)) {
                if (range.contains(new Point(point.x, point.y)) || range.contains(new Point(point.x, point.y + radius)) || range.contains(new Point(point.x + radius, point.y + radius)) || range.contains(new Point(point.x + radius, point.y))) {
                    return point;
                }
            }
        }
        if (divided) {
            if (TL.query(range, first) != null) {
                return TL.query(range, first);
            }
            if (TR.query(range, first) != null) {
                return TR.query(range, first);
            }
            if (BL.query(range, first) != null) {
                return BL.query(range, first);
            }
            if (BR.query(range, first) != null) {
                return BR.query(range, first);
            }
        }
        return null;
    }

    private void subdivide() {
        TR = new Quadtree(new Rectangle(boundary.x, boundary.y, boundary.w/2, boundary.h/2), capacity);
        TL = new Quadtree(new Rectangle(boundary.x + boundary.w/2, boundary.y, boundary.w/2, boundary.h/2), capacity);
        BR = new Quadtree(new Rectangle(boundary.x, boundary.y + boundary.h/2, boundary.w/2, boundary.h/2), capacity);
        BL = new Quadtree(new Rectangle(boundary.x + boundary.w/2, boundary.y + boundary.h/2, boundary.w/2, boundary.h/2), capacity);
    }
}
