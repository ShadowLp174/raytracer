package programming.redtech.util;

public class Point {
  public double x;
  public double y;
  public double z;
  public Point(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public Vector toVector() {
    return new Vector(this.x, this.y, this.z);
  }
}
