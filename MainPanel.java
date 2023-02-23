import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

public class MainPanel extends JPanel {

    private Quadtree qtree;
    private ArrayList<Point> points;
    private int radius;

    public MainPanel(Quadtree qtree, ArrayList<Point> points) {
        this.qtree = qtree;
        this.points = points;
        this.radius = 10;
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
                    double oldDx = point.dx;
                    point.dx = collider.dx;
                    collider.dx = oldDx;
                    double oldDy = point.dy;
                    point.dy = collider.dy;
                    collider.dy = oldDy;
                    while (range.contains(collider)) {
                        point.x += point.dx;
                        point.y += point.dy;
                        collider.x += collider.dx;
                        collider.y += collider.dy;
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
        // Draw quadtree
        Graphics2D g2 = (Graphics2D) g;
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
        g2.setStroke(new BasicStroke(2));
        if (qtree.isDivided()) {
            drawTree(g2, qtree.getTR());
            drawTree(g2, qtree.getTL());
            drawTree(g2, qtree.getBR());
            drawTree(g2, qtree.getBL());
        }
        for (Point point : qtree.getPoints()) {
            g2.setColor(point.color);
            g2.fillRect((int) point.x, (int) point.y, radius, radius);
        }
    }
}
