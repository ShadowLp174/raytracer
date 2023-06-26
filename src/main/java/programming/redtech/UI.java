package programming.redtech;

import programming.redtech.util.Point;
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
      new Point(-1, 2, 0.5),
      new Point(1, 2, 0.5),
      new Point(0, 2, -0.5)
    });
    final Point camP = new Point(0, 0, 0);

    final int columns = 11; // temporary test view plane
    final int rows = 11;
    final double viewPlaneWidth = 0.5;
    final double cmax = viewPlaneWidth / columns * ((columns - 1) / 2);
    final double rmax = viewPlaneWidth / columns * ((rows - 1) / 2);
    final Point vp = new Point(camP.x, camP.y + 1, camP.z); // vp == view plane center

    final Triangle.Plane p = t.getPlane();

    for (double c = -cmax; c <= cmax; c += viewPlaneWidth / columns) {
      for (double r = -rmax; r <= rmax; r += viewPlaneWidth / rows) {
        final Vector rayAngle = new Vector(vp.x + c, 1, vp.z + r); // the view plane is 1 unit away from the camera; in this case, the ray is cast 0.1 units to the right
        // distance to the intersection with the plane
        // abort if distance is negative (because it is behind the camera)
        final double distance = -((p.a * camP.x) + (p.b * camP.y) + (p.c * camP.z) + p.k) / (p.a * rayAngle.x + p.b * rayAngle.y + p.c * rayAngle.z);
        // s is the origin of the ray (camera)
        // i == intersection
        final Vector i = rayAngle.scale(distance).add(camP.toVector());
        System.out.println("Intersection point: " + i.x + ", " + i.y + ", " + i.z + "; column: " + (int) c + "; row: " + (int) r);
        if (t.isPointInside(i.toPoint())) {
          System.out.println("inside");
          this.canvas.setPixel(10 + (int) (10 * c + vp.x), 10 + (int) (10 * r + vp.z), new Color(255, 0, 0, (int) (200 * 1/distance)));
        } else {
          System.out.println("outside");
        }
      }
    }
    this.canvas.draw();
  }
}
