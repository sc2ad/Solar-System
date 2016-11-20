import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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
	public void update(ArrayList<Object> obs) {
		d.update(obs);
	}

    //create a component that you can actually draw on.
}
class DrawPane extends JPanel {
	int width;
	int height;
	ArrayList<Object> objects;
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
    public void paintComponent(Graphics g) {
    	//draw on g here e.g.
        //g.fillRect(0, 0, width, height);
    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLUE);
        
        for (Object o : objects) {
        	if (o.exists)
        	g.fillOval((int)(o.posX - o.radius/2), (int)(o.posY - o.radius/2), (int)(o.radius), (int)(o.radius));
        }
    }
}
