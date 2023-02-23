import java.awt.*;

public class Point {
    public double x;
    public double y;
    public double dx;
    public double dy;
    public boolean collided;
    public Color color;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.dx = (int) (Math.random()*3 + 1) -2;
        this.dy = 0;
        this.collided = false;
    }

    public Point(double x, double y, double dx, double dy, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.collided = false;
        this.color = color;
    }

    public boolean equals(Point that) {
        return this.x == that.x && this.y == that.y;
    }
}
