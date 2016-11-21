import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
public class Universe {
	public static boolean paused = false;
	public static ArrayList<Object> objects = new ArrayList<Object>();
	public static DrawUniverse universe;
	static double z = 1.0;
	public static void main(String[] args) {
		// ADD IN PLANET CREATION
		universe = new DrawUniverse(800, 800);
		universe.addKeyListener(new KeyList());
		
		reset();		
		
		ScheduledExecutorService x = Executors.newSingleThreadScheduledExecutor();
		
		x.scheduleAtFixedRate(new Runnable() {
	        @Override
	        public void run() {
	            run1(objects, universe);
	        }
	    }, 0, 25, TimeUnit.MILLISECONDS);
		
	}
	public static void run1(ArrayList<Object> objects, DrawUniverse universe) {
		if (!paused) {
			universe.update(objects, z);
			for (int i = 0; i < objects.size(); i++) {
				Object temp = objects.get(i);
				objects.remove(i);
				temp.execute(objects);
				objects.add(i, temp);
				if (!temp.type.equals("star"))
				System.out.println(temp);
			}
		}
	}
	public static ArrayList<Object> create() {
		ArrayList<Object> obs = new ArrayList<Object>();
		
		obs.add(new Star("Star",10000,400,400,100));
		
		obs.add(new Object(0.1,600,400,15,0,22.36));
		
		obs.add(new Object(0.095, 750, 400, 15, 0, 20));
		
		obs.add(new Planet("Earth", 10, 1000, 400, 25, 0, 1));
		
		
		
		return obs;
	}
	public static void reset() {
		objects = create();
		
		universe.d.reset(objects);
		for (int i = 0; i < 12; i++) System.out.println();
		for (int i = 0; i < objects.size(); i++) {
			Object temp = objects.get(i);
			if (!temp.type.equals("star"))
			System.out.println(temp);
		}
	}
	public static void zoom() {
		paused = true;
		z = 0;
		while (z < 1 || z > 100) {
			try {
				z = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter zoom factor 1-100"));
			} catch (NumberFormatException e) {
				// Nothing
			} catch (NullPointerException e) {
				// Nothing
			}
		}
		reset();
		paused = false;
		universe.d.scaling = z;
	}
	public static void createPlanet(ArrayList<Object> obs) {
		//https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
		//http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
		//Panel.add(JOptionPane component or whatever...)
	}
}