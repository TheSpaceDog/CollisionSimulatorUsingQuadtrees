public class Rectangle {
    public double x;
    public double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public double w;
    public double h;
    // x and y is top left corner of rectangle
    // width is width
    // height is height

    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean contains(Point point) {
        return point.x >= this.x && point.x <= this.x + this.w && point.y >= this.y && point.y <= this.y + this.h;
    }

    public boolean intersects(Rectangle that) {
        boolean xOverlap = (x < that.x + that.w) || (that.x < x + w);
        boolean yOverlap = (y < that.y + that.h) || (that.y < y + h);
        return xOverlap && yOverlap;
    }
}
