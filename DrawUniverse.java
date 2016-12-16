import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Creates the Graphical window and deals with all of the redrawing when objects are updated
 * By Sc2ad
 */
public class DrawUniverse extends JFrame {
	DrawPane d;
	
	
	public DrawUniverse(int x, int y, Universe u) {
        super("The Universe"); // Sets the title

        d = new DrawPane(x,y,u); // Creates the DrawPane class below
        setContentPane(d); // Sets it as the background

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes sure it can close

        setSize(x+200, y+200); // Sets the size to be a bit bigger than needed

        setVisible(true);  // Makes sure the window is visible
        
   }
	public void update(ArrayList<Object> obs, double scaling) {
		d.update(obs);
		d.scaling = scaling;
	}
}
class DrawPane extends JPanel {
	int width;
	int height;
	ArrayList<Object> objects;
	// ArrayLists of positions of the objects as they move
	ArrayList<Double> posX = new ArrayList<Double>();
	ArrayList<Double> posY = new ArrayList<Double>();
	Universe u;
	double scaling = 1.0; // Default scaling
	
	
	public static final int KMAXBLIPSIZE = 100;
	// Maximum blips for each object
	
	public DrawPane(int w, int h, Universe u) {
		width = w;
		height = h;
		this.u = u;
		// Creates thread for graphical repainting
		Thread animationThread = new Thread(new Runnable() {
			public void run() {
                do {
                    repaint();
                    try {Thread.sleep(10);} catch (Exception ex) {}
                } while (true);
            }
		});
		animationThread.start();
	}
	public void update(ArrayList<Object> objects) {
		// Updates the ArrayList for updated objects
		this.objects = objects;
	}
	public void reset(ArrayList<Object> objects) {
		// Resets all items
		posX.removeAll(posX);
		posY.removeAll(posY);
		update(objects);
	}
    public void paintComponent(Graphics g) {
    	//draw on g here e.g.
        //g.fillRect(0, 0, width, height);
    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Makes the sky BLACK
        int KLINELENGTH = Universe.lineLength;

        //TODO: ADD IN SCALING, ZOOM, AND PLANET CREATION
        try {
	        for (Object o : objects) {
	        	if (o.exists()) {
	        		double x = (o.posX - o.radius/2 - getWidth()/2) / scaling + getWidth()/2;
	        		double y = (o.posY - o.radius/2 - getHeight()/2) / scaling + getHeight()/2;
	        		double cx = (o.posX - getWidth()/2) / scaling + getWidth()/2;
	        		double cy = (o.posY - getHeight()/2) / scaling + getHeight()/2;
	        		// Sets important information here
	        		
	        		g.setColor(Color.BLUE);
		        	g.fillOval((int)(x), (int)(y), (int)(o.radius / scaling), (int)(o.radius / scaling));
		        	// Creates ovals of the correct size to represnt objects
		        	
		        	g.setColor(Color.RED);
	        		for (int i = 0; i < posX.size(); i++) {
		        		g.fillOval((int)(posX.get(i).intValue()), (int)(posY.get(i).intValue()), 2, 2);	
		        	}
	        		// Creates ovals of last positions
	        		g.setColor(Color.ORANGE);
	        		for (int i = 0; i < objects.size(); i++) {
	        			// Draws vectors in orange
	        			Object o2 = objects.get(i);
	        			if (o2.exists()) {
	        				double[] grav = o.calcGravity(o2);
	        				//System.out.println((int)(o.posX + grav[0]));
	        				g.drawLine((int)(cx), (int)(cy), (int)(cx + grav[0] * KLINELENGTH), (int)(cy + grav[1] * KLINELENGTH));
	        				
	        				//double[] net = o.netForces(objects);
	        				//g.drawLine((int)(o.posX), (int)(o.posY), (int)(o.posX + net[0]), (int)(o.posY + net[1]));
	        			}
	        		}
	        		if (!u.paused) {
	        			// Add MULTICOLORS!!!
			        	posX.add(cx);
			        	posY.add(cy);
			        	// Makes sure there is a maximum amount of blips on the screen before removing the oldest ones
			        	if (posX.size() > KMAXBLIPSIZE * objects.size()) {
			        		posX.remove(0);
			        		posY.remove(0);
			        	}
	        		}
		        	
	        	}
	        	
	        }
        } catch (ConcurrentModificationException ex) {
        	// Catch an error that occurs when looping and modifying an arraylist on a different thread (the repaint thread, in fact)
        }
    }
}
