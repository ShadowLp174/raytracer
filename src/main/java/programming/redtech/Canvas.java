package programming.redtech;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
  BufferedImage image = null;
  public Canvas() {
    super();
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }
  public Dimension getPreferredSize() {
    return new Dimension(250, 200);
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), null);
  }

  public void setPixel(int x, int y, Color color) {
    if (image == null) image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
    image.setRGB(x, y, color.getRGB());
  }
  public void replaceImage(BufferedImage i) {
    this.image = i;
  }
  public void draw() {
    this.repaint();
  }
}
