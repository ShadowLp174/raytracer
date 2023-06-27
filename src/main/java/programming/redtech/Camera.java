package programming.redtech;

import programming.redtech.util.Point;
import programming.redtech.util.Triangle;
import programming.redtech.util.Vector;
import programming.redtech.util.Shape;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Camera {
  public Point position = new Point(0, 0, 0);
  int columns = 101; // temporary test view plane
  int rows = 101;
  double viewPlaneWidth = 0.5;
  double cmax = viewPlaneWidth / columns * ((columns - 1) / 2);
  double rmax = viewPlaneWidth / columns * ((rows - 1) / 2);
  Point vp = new Point(this.position.x, this.position.y + 1, this.position.z); // the center of the view plane
  ArrayList<Triangle> triangles = new ArrayList<>();

  public Camera() {

  }

  public void addTriangle(Triangle t) {
    this.triangles.add(t);
  }
  public void addShape(Shape s) {
    this.triangles.addAll(Arrays.stream(s.triangulate()).toList());
  }

  private Triangle.Plane[] getPlanes() {
    return Arrays.stream(this.triangles.toArray()).map(t -> ((Triangle) t).getPlane()).toArray(Triangle.Plane[]::new);
  }
  private Color castRay(Vector rayAngle, Triangle[] triangles, Color defaultColor) {
    double currDistance = Double.MAX_VALUE;
    Color currColor = defaultColor;
    for (Triangle t : triangles) {
      final Triangle.Plane p = t.getPlane();
      // distance to the intersection with the plane
      // abort if distance is negative (because it is behind the camera)
      final double distanceScalar = -((p.a * position.x) + (p.b * position.y) + (p.c * position.z) + p.k) / (p.a * rayAngle.x + p.b * rayAngle.y + p.c * rayAngle.z);
      if (distanceScalar < 0) continue;
      // s is the origin of the ray (camera)
      // i == intersection
      final Vector i = rayAngle.scale(distanceScalar).add(position.toVector());
      if (!t.isPointInside(i.toPoint())) continue;
      final double distance = Vector.fromPoints(position, i.toPoint()).magnitude();
      if (distance < currDistance) {
        currDistance = distance;
        currColor = new Color(255, 0, 0, (int) (255/distance));
      }
    }
    return currColor;
  }
  public Color castRay(Vector rayAngle, Triangle[] triangles) {
    return castRay(rayAngle, triangles, new Color(255, 255, 255, 0)); // transparent white
  }
  public Color[] castRays(Color defaultCol) { // render the scene
    ArrayList<Color> resCols = new ArrayList<>();
    double c = -cmax;
    for (int col = 0; col < columns; col++) {
      double r = -rmax;
      for (int row = 0; row < rows; row++) {
        final Vector rayAngle = new Vector(vp.x + c, 1, vp.z + r); // the view plane is 1 unit away from the camera; in this case, the ray is cast 0.1 units to the right

        final Color color = castRay(rayAngle, triangles.toArray(Triangle[]::new), defaultCol);
        resCols.add(color);

        System.out.println("column: " + ((int) (col + vp.x)) + "; row: " + ((int) (row + vp.z)));
        r += viewPlaneWidth / rows;
      }
      c += viewPlaneWidth / columns;
    }
    return resCols.toArray(Color[]::new);
  }
  public BufferedImage render(BufferedImage img, Color defaultCol) {
    if (img == null) img = new BufferedImage(columns, rows, BufferedImage.TYPE_INT_ARGB);
    double c = -cmax;
    for (int col = 0; col < columns; col++) {
      double r = -rmax;
      for (int row = 0; row < rows; row++) {
        final Vector rayAngle = new Vector(vp.x + c, 1, vp.z + r); // the view plane is 1 unit away from the camera; in this case, the ray is cast 0.1 units to the right

        final Color color = castRay(rayAngle, triangles.toArray(Triangle[]::new), defaultCol);
        img.setRGB(col, row, color.getRGB());

        System.out.println("column: " + col + "; row: " + row);
        r += viewPlaneWidth / rows;
      }
      c += viewPlaneWidth / columns;
    }
    return img;
  }
}
