import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Runner {
    public static void main(String[] args) {
        Rectangle boundary = new Rectangle(0, 0, 800, 800);
        Quadtree qtree = new Quadtree(boundary, 4);

        JFrame frame = new JFrame();
        frame.setTitle("Point-Region Quadtree");
        frame.setSize(800, 828);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);

        ArrayList<Point> points = new ArrayList<>();
        /*
        points.add(new Point(115, 100,  0, 1, Color.blue));
        points.add(new Point(100, 150, 0, -1, Color.red));
        points.add(new Point(600, 600,  -1, 0, Color.green));
        points.add(new Point(350, 700, -4, -1, Color.magenta));
        points.add(new Point(200, 200,  1, -1, Color.blue));
        points.add(new Point(350, 100, -2, -3, Color.red));
        points.add(new Point(100, 600,  -1, 0, Color.green));
        points.add(new Point(50, 700, -1.1, -1, Color.magenta));
         */
        for (int i = 0; i < 32; i++) {
            Color c = Color.getHSBColor((int) (Math.random() * 2400), (int) (Math.random() * 2400), (int) (Math.random() * 2400));
            points.add(new Point((int) (Math.random() * 800) - 10, (int) (Math.random() * 800) - 10, Math.random()*2 - 2, Math.random()*2 - 2, c));
        }

        MainPanel panel = new MainPanel(qtree, points);
        frame.add(panel);
        frame.setVisible(true);
    }
}
