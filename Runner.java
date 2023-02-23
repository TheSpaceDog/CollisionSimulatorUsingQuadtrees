import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Runner {
    public static void main(String[] args) {
        int radius = 10;
        Rectangle boundary = new Rectangle(0, 0, 800, 800);
        Quadtree qtree = new Quadtree(boundary, 4);

        JFrame frame = new JFrame();
        frame.setTitle("Point-Region Quadtree");
        frame.setSize(800, 828);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(false);

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            Color c = Color.getHSBColor((int) (Math.random() * 3000), (int) (Math.random() * 3000), (int) (Math.random() * 3000));
            points.add(new Point((int) (Math.random() * 800) - radius, (int) (Math.random() * 800) - radius, Math.random()*3 - 3, Math.random()*3 - 3, c));
        }

        MainPanel panel = new MainPanel(qtree, points);
        frame.add(panel);
        frame.setVisible(true);
    }
}
