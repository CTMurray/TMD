import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


/*******************************************************************************
 * Class that extends the JPanel that will house the movie poster.
 * 
 * @author Kimberlin Steffens, Raymundo Ramirez, Camaal Murray, Nicolas Heady
 * @version First Release
 ******************************************************************************/
public class BackgroundPanel extends JPanel {

  /** The BufferedImage object that holds the poster. */
  private BufferedImage bufferedImage;

  /** The Graphics2D object that manipulates the panel. */
  private Graphics2D graphics;

  /** The String that will be applied to the panel. */
  private String text;

  /** The serialVersionUID. */
  private static final long serialVersionUID = 1L;
  
  /** The size to set text. */
  private final int size = 200;
  
  /** The x dimension. */
  private final int xDim = 350;
  
  /** The y dimension. */
  private final int yDim = 500;

  /*****************************************************************************
   * The constructor for BackgroundPanel.
   ****************************************************************************/
  public BackgroundPanel() {
    text = "Please search for a movie";
    changeBackground(text);
  }

  /*****************************************************************************
   * The method that will apply text to the panel.
   * 
   * @param changedText
   *          the String that will be applied
   ****************************************************************************/
  public final void changeBackground(final String changedText) {
    bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    graphics = bufferedImage.createGraphics();
    FontMetrics fontMetrics = graphics.getFontMetrics();
    graphics.setColor(getForeground());

    int y = (size - (fontMetrics.getHeight() * 2)) / 2;
    int x = (size - fontMetrics.stringWidth(changedText)) / 2;
    graphics.drawString(changedText, x, y + fontMetrics.getAscent());
    graphics.dispose();

  }

  /*****************************************************************************
   * The method that will apply the movie poster the panel.
   * 
   * @param background
   *          a BufferedImage object that will be displayed
   ****************************************************************************/
  public final void changeBackground(final BufferedImage background) {

    bufferedImage = background;
    graphics = bufferedImage.createGraphics();
    graphics.setColor(getForeground());
    graphics.dispose();

  }

  /*****************************************************************************
   * Method to get the preferred size of the panel.
   * 
   * @return Dimension
   ****************************************************************************/
  public final Dimension getPreferredSize() {
    return new Dimension(xDim, yDim);
  }

  /*****************************************************************************
   * Method that updates the graphics of the panel.
   * 
   * @param g
   *          to create the graphics.
   ****************************************************************************/
  protected final void paintComponent(final Graphics g) {
    super.paintComponent(g);
    int x = (getWidth() - bufferedImage.getWidth()) / 2;
    int y = (getHeight() - bufferedImage.getHeight()) / 2;
    g.drawImage(bufferedImage, x, y, this);
  }

}
