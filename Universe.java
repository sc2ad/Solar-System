import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
public class Universe {
    public static boolean paused = false;
    public static ArrayList<Object> objects = new ArrayList<Object>();
    public static ArrayList<Object> initObs = new ArrayList<Object>();
    public static DrawUniverse universe;
    static double z = 1.0;
    public static int lineLength = 20;
    
    public static void main(String[] args) {
        // ADD IN PLANET CREATION
        universe = new DrawUniverse(800, 800);
        universe.addKeyListener(new KeyList());
        initObs.add(new Star("Star",10000,400,400,100));
        
        initObs.add(new Object(0.1,600,400,15,0,22.36));
        
        initObs.add(new Object(0.095, 750, 400, 15, 0, 20));
        
        initObs.add(new Planet("Earth", 10, 1000, 400, 25, 0, 1));
        
        initObs.add(new Object(1, 12000, 400, 15, -8, 1));
        
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
        for (Object o : initObs) {
        	obs.add(new Object(o));
        }
        return obs;
    }
    public static void reset() {
        objects = new ArrayList<Object>(create());
        
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
    public static void createPlanet() {
        //https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
        //http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
        //Panel.add(JOptionPane component or whatever...)
        paused = true;
        double mass, r, vx, vy;
        int px, py;
        try {
            mass = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the mass of new planet:"));
            px = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the x position:"));
            py = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the y position:"));
            vx = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the initial x velocity:"));
            vy = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the initial y velocity:"));
            r = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the initial radius:"));
            if (r < 1 || mass <= 0) {
                throw new NumberFormatException();
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "You input the wrong type (String or a double for an int or out of range)");
            return;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "You canceled planet creation.");
            return;
        }
        initObs.add(new Planet(mass, px, py, r, vx, vy));
        reset();
        JOptionPane.showMessageDialog(null, "Planet created!");
        paused = false;
   }
    public static void changeLineLength() {
        lineLength = -1;
        paused = true;
        while (lineLength < 0 || lineLength > 100000) {
            try {
                lineLength = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter force line factor 0-100000"));
            } catch (NumberFormatException e) {
                // Nothing
            } catch (NullPointerException e) {
                // Nothing
            }
        }
        paused = false;
    }
}