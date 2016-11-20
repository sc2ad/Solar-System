import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Universe {
	public static boolean paused = false;
	public static ArrayList<Object> objects = new ArrayList<Object>();
	public static DrawUniverse universe;
	public static void main(String[] args) {
		// JFRAME STUFF GOES HERE...
		universe = new DrawUniverse(800, 800);
		universe.addKeyListener(new KeyList());
		
		reset();
		
		//objects.add(new Planet("Hi There!", 100, 500,400,50));
		
		
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
			universe.update(objects);
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
	public static void reset() {
		objects.removeAll(objects);
		objects.add(new Star("Star",1000,400,400,100));
		
		objects.add(new Object(0.1,600,400,15,0,5.36));
		
		objects.add(new Object(0.095, 750, 400, 15, 0, 3.5));
		
		universe.d.reset(objects);
		for (int i = 0; i < 12; i++) System.out.println();
		for (int i = 0; i < objects.size(); i++) {
			Object temp = objects.get(i);
			if (!temp.type.equals("star"))
			System.out.println(temp);
		}
	}
}