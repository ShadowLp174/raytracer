package programming.redtech;

import programming.redtech.util.Point;
import programming.redtech.util.Square;
import programming.redtech.util.Triangle;
import programming.redtech.util.Vector;

import javax.swing.*;
import java.awt.*;

public class UI {
  private Canvas canvas = new Canvas();
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        UI ui = new UI();
        ui.createAndShowGUI();
      }
    });
  }
  public void createAndShowGUI() { // based on https://blog.scottlogic.com/2020/03/10/raytracer-how-to.html
    JFrame f = new JFrame("Ray tracing test");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(this.canvas);
    f.pack();
    f.setVisible(true);

    final Triangle t = new Triangle(new Point[] {
      new Point(-1, 4, 0.5),
      new Point(1, 6, 0.5),
      new Point(0, 4, -0.5)
    });
    final Square s = new Square(new Point(0.2, 2.5, 0.55), 0.2);

    final Camera c = new Camera();
    c.addTriangle(t);
    c.addShape(s);

    this.canvas.replaceImage(c.render(this.canvas.image, new Color(255, 255, 255, 0)));
    this.canvas.draw();
  }
}
