import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

public class MainPanel extends JPanel {

    private Quadtree qtree;
    private ArrayList<Point> points;
    private int radius = 10;

    public MainPanel(Quadtree qtree, ArrayList<Point> points) {
        this.qtree = qtree;
        this.points = points;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 60 fps
        double start = System.currentTimeMillis();
        // update points
        for (Point point : points) {
            point.collided = false;
            point.x = point.x + point.dx;
            point.y = point.y + point.dy;
            if (point.x <= 0 || point.x >= qtree.getBoundary().w - radius) {
                point.dx = -point.dx;
                if (point.x < 0) {
                    point.x = 1 - point.x;
                } else {
                     point.x = -1 + qtree.getBoundary().w - (point.x + radius - qtree.getBoundary().w);
                }
            }
            if (point.y <= 0 || point.y >= qtree.getBoundary().h - radius) {
                point.dy = -point.dy;
                if (point.y < 0) {
                    point.y = 1 - point.y;
                } else {
                    point.y = -1 + qtree.getBoundary().h - (point.y + radius - qtree.getBoundary().h);
                }
            }
        }

        // do collisions
        for (Point point : points) {
            // radius = 10
            Rectangle range = new Rectangle(point.x, point.y, radius, radius);
            Point collider = qtree.query(range, point);
            // resolve collision between point and collider
            if (collider != null) {
                if (!point.collided && !collider.collided) {
                    Point p1 = point;
                    Point p2 = collider;
                    double oldDx = p1.dx;
                    p1.dx = p2.dx;
                    p2.dx = oldDx;
                    double oldDy = p1.dy;
                    p1.dy = p2.dy;
                    p2.dy = oldDy;
                    while (range.contains(p2)) {
                        p2.x += p2.dx;
                        p2.y += p2.dy;
                        System.out.println("correcting?");
                    }
                    point.collided = true;
                    collider.collided = true;
                }
            }
        }

        // remake quadtree
        qtree = new Quadtree(qtree.getBoundary(), qtree.getCapacity());
        for (Point point : points) {
            qtree.insert(point);
        }

        Graphics2D g2 = (Graphics2D) g;
        // g2.setColor(Color.BLACK);
        // g2.fillRect((int) qtree.getBoundary().x, (int) qtree.getBoundary().y, (int) qtree.getBoundary().w, (int) qtree.getBoundary().h);
        drawTree(g2, qtree);

        while ((System.currentTimeMillis()-start)/1000.0 < 1.0/60) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        repaint();
    }

    private void drawTree(Graphics2D g2, Quadtree qtree) {
        int x = (int) qtree.getBoundary().getX();
        int y = (int) qtree.getBoundary().getY();
        int w = (int) qtree.getBoundary().getW();
        int h = (int) qtree.getBoundary().getH();
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.white);
        g2.drawRect(x, y, w, h);
        if (qtree.isDivided()) {
            drawTree(g2, qtree.getTR());
            drawTree(g2, qtree.getTL());
            drawTree(g2, qtree.getBR());
            drawTree(g2, qtree.getBL());
        }
        g2.setStroke(new BasicStroke(2));
        for (Point point : qtree.getPoints()) {
            g2.setColor(point.color);
            g2.fillRect((int) point.x, (int) point.y, radius, radius);
        }
    }
}
