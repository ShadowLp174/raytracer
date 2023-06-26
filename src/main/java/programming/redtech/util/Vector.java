package programming.redtech.util;

public class Vector {
  public double x = 0;
  public double y = 0;
  public double z = 0;
  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public Vector() {}

  public static Vector fromPoints(Point start, Point end) {
    return new Vector(end.x - start.x, end.y - start.y, end.z - start.z);
  }

  public Vector crossProduct(Vector v) {
    return new Vector(
      this.y * v.z - this.z * v.y,
      this.z * v.x - this.x * v.z,
      this.x * v.y - this.y * v.x
    );
  }
  public double dotProduct(Vector v) {
    return this.x * v.x + this.y * v.y + this.z * v.z;
  }
  public Vector scale(double s) {
    return new Vector(this.x * s, this.y * s, this.z * s);
  }
  public Vector add(Vector v) {
    return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
  }
  public Vector subtract(Vector v) {
    v = v.revert();
    return this.add(v);
  }

  public Point toPoint() {
    return new Point(this.x, this.y, this.z);
  }

  public Vector revert() {
    return new Vector(-this.x, -this.y, -this.z);
  }
}
