package programming.redtech.util;

import java.util.ArrayList;
import java.util.Arrays;

public class Triangle {
  public class Plane {
    public Vector normal;
    public double a;
    public double b;
    public double c;
    public double k;
    public Plane() {}

    public void fromTriangle(Triangle t) {
      Vector v1 = Vector.fromPoints(t.points[0], t.points[1]);
      Vector v2 = Vector.fromPoints(t.points[0], t.points[2]);
      this.normal = v1.crossProduct(v2);

      this.k = this.normal.revert().dotProduct(t.points[0].toVector());
      this.a = this.normal.x;
      this.b = this.normal.y;
      this.c = this.normal.z;
    }
  }
  public Point[] points;
  public Triangle(Point[] points) { // should have the correct order of points
    this.points = points;
  }

  public boolean isPointInside(Point p) { // following https://blog.scottlogic.com/2020/03/10/raytracer-how-to.html
    // TODO: improve algorithm
    double[] mins = {
        Arrays.stream(this.points).map((point) -> point.x).min(Double::compareTo).get(),
        Arrays.stream(this.points).map((point) -> point.y).min(Double::compareTo).get(),
        Arrays.stream(this.points).map((point) -> point.z).min(Double::compareTo).get()
    };
    double[] maxs = {
        Arrays.stream(this.points).map((point) -> point.x).max(Double::compareTo).get(),
        Arrays.stream(this.points).map((point) -> point.y).max(Double::compareTo).get(),
        Arrays.stream(this.points).map((point) -> point.z).max(Double::compareTo).get()
    };
    // easy test
    if (p.x < mins[0] || p.x > maxs[0]) return false;
    if (p.y < mins[1] || p.y > maxs[1]) return false;
    if (p.z < mins[2] || p.z > maxs[2]) return false;

    // full check
    for (int i = 0; i < 3; i++) { // check for each corner if the point is on the same side of the reference vector as the corner
      final Point t1 = this.points[i];
      final Point t2 = this.points[(i + 1) % 3];
      final Point t3 = this.points[(i + 2) % 3];
      final Vector ref = Vector.fromPoints(t2, t3); // ref == reference side of the triangle

      final Vector a = ref.crossProduct(Vector.fromPoints(p, t3));
      final Vector b = ref.crossProduct(Vector.fromPoints(t1, t3));
      final double c = a.dotProduct(b);
      if (c < 0) return false;
    }

    return true;
  }

  public Plane getPlane() {
    Plane plane = new Plane();
    plane.fromTriangle(this);
    return plane;
  }
}
