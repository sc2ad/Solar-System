import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawUniverse extends JFrame {
	DrawPane d;
	
	
	public DrawUniverse(int x, int y) {
        super("The Universe");

        //you can set the content pane of the frame 
        //to your custom class.

        d = new DrawPane(x,y);
        setContentPane(d);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(x+200, y+200);

        setVisible(true); 
        
   }
	public void update(ArrayList<Object> obs, double scaling) {
		d.update(obs);
		d.scaling = scaling;
	}

    //create a component that you can actually draw on.
}
class DrawPane extends JPanel {
	int width;
	int height;
	ArrayList<Object> objects;
	ArrayList<Double> posX = new ArrayList<Double>();
	ArrayList<Double> posY = new ArrayList<Double>();
	double scaling = 1.0;
	
	public static final int KMAXBLIPSIZE = 1000;
	public static final int KLINELENGTH = 20;
	// Maximum blips for each object
	
	public DrawPane(int w, int h) {
		width = w;
		height = h;
		Thread animationThread = new Thread(new Runnable() {
			public void run() {
                while (true) {
                    repaint();
                    try {Thread.sleep(10);} catch (Exception ex) {}
                }
            }
		});
		animationThread.start();
	}
	public void update(ArrayList<Object> objects) {
		this.objects = objects;
	}
	public void reset(ArrayList<Object> objects) {
		posX.removeAll(posX);
		posY.removeAll(posY);
		update(objects);
	}
    public void paintComponent(Graphics g) {
    	//draw on g here e.g.
        //g.fillRect(0, 0, width, height);
    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //TODO: ADD IN SCALING, ZOOM, AND PLANET CREATION
        try {
	        for (Object o : objects) {
	        	if (o.exists) {
	        		double x = (o.posX - o.radius/2 - 400) / scaling + 400;
	        		double y = (o.posY - o.radius/2 - 400) / scaling + 400;
	        		double cx = (o.posX - 400) / scaling + 400;
	        		double cy = (o.posY - 400) / scaling + 400;
	        		
	        		g.setColor(Color.BLUE);
		        	g.fillOval((int)(x), (int)(y), (int)(o.radius / scaling), (int)(o.radius / scaling));
		        	
		        	g.setColor(Color.RED);
	        		for (int i = 0; i < posX.size(); i++) {
		        		g.fillOval((int)(posX.get(i).intValue()), (int)(posY.get(i).intValue()), 2, 2);
		        		
		        	}
	        		g.setColor(Color.ORANGE);
	        		for (int i = 0; i < objects.size(); i++) {
	        			Object o2 = objects.get(i);
	        			if (o2.exists) {
	        				double[] grav = o.calcGravity(o2);
	        				//System.out.println((int)(o.posX + grav[0]));
	        				g.drawLine((int)(cx), (int)(cy), (int)(cx + grav[0] * KLINELENGTH), (int)(cy + grav[1] * KLINELENGTH));
	        				
	        				//double[] net = o.netForces(objects);
	        				//g.drawLine((int)(o.posX), (int)(o.posY), (int)(o.posX + net[0]), (int)(o.posY + net[1]));
	        			}
	        		}
	        		if (!Universe.paused) {
	        			// Add MULTICOLORS!!!
			        	posX.add(cx);
			        	posY.add(cy);
			        	if (posX.size() > KMAXBLIPSIZE * objects.size()) {
			        		posX.remove(0);
			        		posY.remove(0);
			        	}
	        		}
		        	
	        	}
	        	
	        }
        } catch (ConcurrentModificationException ex) {
        	
        }
    }
}
