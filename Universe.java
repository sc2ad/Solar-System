import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Universe {
	public static void main(String[] args) {
		// JFRAME STUFF GOES HERE...
		ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(new Object(100000,400,400,500));
		objects.add(new Object(0.1,600,400,15));
		objects.add(new Planet("Hi There!", 100, 500,400,50));
		DrawUniverse universe = new DrawUniverse(800, 800);
		
		ScheduledExecutorService x = Executors.newSingleThreadScheduledExecutor();
		
		x.scheduleAtFixedRate(new Runnable() {
	        @Override
	        public void run() {
	            run1(objects, universe);
	        }
	    }, 0, 1, TimeUnit.SECONDS);
		
	}
	public static void run1(ArrayList<Object> objects, DrawUniverse universe) {
		universe.update(objects);
		for (int i = 0; i < objects.size(); i++) {
			Object temp = objects.get(i);
			objects.remove(i);
			temp.execute(objects);
			objects.add(i, temp);
			System.out.println(temp);
		}
	}
}