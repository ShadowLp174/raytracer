package programming.redtech.util;

public class Square implements Shape {
  public Point[] points = new Point[4];
  public Square(Point topLeft, double height) {
    final double x = topLeft.x;
    final double y = topLeft.y;
    final double z = topLeft.z;
    points[0] = new Point(x, y, z); // top left
    points[1] = new Point(x + height, y, z); // top right
    points[2] = new Point(x, y, z - height); // bottom left
    points[3] = new Point(x + height, y, z - height); // bottom right
  }
  public Triangle[] triangulate() {
    return new Triangle[]{new Triangle(new Point[]{
        points[0],
        points[1],
        points[2]
    }), new Triangle(new Point[]{
        points[1],
        points[2],
        points[3]
    })};
  }
}
