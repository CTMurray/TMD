import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.Test;

public class TestBackgroundPanel {
	
	
	/** The BufferedImage object that holds the poster */
	private BufferedImage bufferedImage;

	/** The Graphics2D object that manipulates the panel */
	private Graphics2D graphics;
	
	
	/***
	 * Test that the object is created with its construct call.
	 */
	@Test
	public void testBackgroundPanel() {
		BackgroundPanel b = new BackgroundPanel();
		assertNotNull(b); 
		
	}
	
		
	/***
	 * Testing that the method doesn't create an invalid null state
	 * with the object.
	 */

	@Test
	public void testchangeBackground() {
		BackgroundPanel b = new BackgroundPanel();
		bufferedImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
		
		b.changeBackground(bufferedImage);
		
		assertNotNull(bufferedImage);
		
		
	}

	/***
	 * Testing getPreferredSize method
	 */
	
	@Test
	public void testGetPreferredSize1() {
		
		BackgroundPanel b = new BackgroundPanel();
		Dimension d = new Dimension(350, 500);
		assertEquals(d.getSize(), b.getPreferredSize());
		//fail("Not yet implemented");
	}
	
	
	
	

}

/*
public Dimension getPreferredSize() {
	return new Dimension(350, 500);
}
*/