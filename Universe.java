import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/*
 * Universe class for a realistic force simulation (using physics and math :D)
 * By Sc2ad
 */
public class Universe {
    public boolean paused = false; // Initially running
    public static ArrayList<Object> objects = new ArrayList<Object>();
    public static ArrayList<Object> initObs = new ArrayList<Object>();
    public static DrawUniverse universe;
    static double z = 1.0; // Initial zoom
    public static int lineLength = (int)(Math.random() * 20 + 1); // random force vector line length
    public Universe() {
    	// 0 arg constructor needs nothing
    }
    public Universe(Object[] obs) {
    	initObs = new ArrayList<Object>(Arrays.asList(obs));
    }
    
    public void initRun() {
    	// ADD IN PLANET CREATION
        universe = new DrawUniverse(800, 800, this);
        universe.addKeyListener(new KeyList(this));
        // Default arraylist if none is passed through
        if (initObs.size() == 0) {
	        initObs.add(new Star("Star",10000,400,400,100));
	        
	        initObs.add(new Object(0.1,600,400,15,0,22.36));
	        
	        initObs.add(new Object(0.095, 750, 400, 15, 0, 20));
	        
	        initObs.add(new Planet("Earth", 10, 1000, 400, 25, 0, 1));
	        
	        initObs.add(new Object(1, 12000, 400, 15, -8, 1));
        }
        reset();        
        
        ScheduledExecutorService x = Executors.newSingleThreadScheduledExecutor();
        
        x.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                run1(objects, universe);
            }
        }, 0, 25, TimeUnit.MILLISECONDS);
    }
    public void run1(ArrayList<Object> objects, DrawUniverse universe) {
    	// Will redraw objects and deal with forces if NOT paused
        if (!paused) {
            universe.update(objects, z);
            for (int i = 0; i < objects.size(); i++) {
            	// Pops an item to get all the forces acting on it by all the other objects
                Object temp = objects.get(i);
                objects.remove(i);
                temp.execute(objects);
                objects.add(i, temp);
                if (!temp.type.equals("star"))
                System.out.println(temp);
            }
        }
    }
    public ArrayList<Object> create() {
        ArrayList<Object> obs = new ArrayList<Object>();
        for (Object o : initObs) {
        	obs.add(new Object(o));
        }
        return obs;
    }
    public void reset() {
    	// Resets the universe to it's standard state
        objects = new ArrayList<Object>(create());
        
        universe.d.reset(objects);
        for (int i = 0; i < 12; i++) System.out.println();
        // For readability
        for (int i = 0; i < objects.size(); i++) {
            Object temp = objects.get(i);
            if (!temp.type.equals("star"))
            System.out.println(temp);
        }
    }
    public void zoom() {
    	// Changes the zoom of the overall window
        paused = true;
        z = 0;
        while (z < 1 || z > 100) {
            try {
                z = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter zoom factor 1-100"));
            } catch (NumberFormatException e) {
	    		// Didn't enter a number
	        	JOptionPane.showMessageDialog(null, "You didn't type a number!");
	        	continue;
	        } catch (NullPointerException e) {
	            // Cancelled
	        	JOptionPane.showMessageDialog(null, "Cancelled!");
	        	return;
	        }
        }
        reset();
        paused = false;
        universe.d.scaling = z;
    }
    public void createPlanet() {
        // Allows (basic) creation of planets
        paused = true;
        String name = "Star";
        double mass, r, vx, vy;
        int px, py;
        try {
            mass = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the mass of new planet:"));
            px = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the x position:"));
            py = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the y position:"));
            vx = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the initial x velocity:"));
            vy = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the initial y velocity:"));
            r = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the initial radius:"));
            int type = (int)(Math.random() * 3);
            
            switch (type) {
            case 0: name = "Star"; break;
            case 1: name = "Planet"; break;
            case 2: name = "Craft"; break;
            case 3: name = "Object"; break;
            }
 
            // Add in better planet creation
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
        switch (name) {
        case "Star": initObs.add(new Star(name, mass, px, py, r)); break;
        case "Planet": initObs.add(new Planet(name, mass, px, py, r, vx, vy)); break;
        case "Craft": initObs.add(new Craft(mass, px, py, r)); break;
        case "Object": initObs.add(new Object(mass, px, py, r, vy, vy)); break;
        }
 
        reset();
        JOptionPane.showMessageDialog(null, "Planet created!");
        paused = false;
   }
    public void changeLineLength() {
    	// Changes the vector lines of each object
        lineLength = -1;
        paused = true;
        while (lineLength < 0 || lineLength > 100000) {
            try {
                lineLength = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter force line factor 0-100000"));
            } catch (NumberFormatException e) {
                // Didn't enter a number
            	JOptionPane.showMessageDialog(null, "You didn't type a number!");
            } catch (NullPointerException e) {
                // Cancelled
            	JOptionPane.showMessageDialog(null, "Cancelled!");
            	lineLength = 2;
            	break;
            }
        }
        paused = false;
    }
    public void destroy() {
    	// Destroys an object by getting rid of it's existence
    	paused = true;
    	int ob = -1;
    	while (ob < 0 || ob > initObs.size()-1) {
    		try {
    			ob = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter an index for an object to destroy (until reset)"));
    		} catch (NumberFormatException e) {
	    		// Didn't enter a number
	        	JOptionPane.showMessageDialog(null, "You didn't type a number!");
	        	continue;
	        } catch (NullPointerException e) {
	            // Cancelled
	        	JOptionPane.showMessageDialog(null, "Cancelled!");
	        	return;
	        }
    		if (ob < 0 || ob > initObs.size()-1)
        	JOptionPane.showMessageDialog(null, "You entered an incorrect index!");
    	}
    	objects.get(ob).setExistence(!objects.get(ob).exists()); //Toggles the existence of the object
    	paused = false;
    }
    public void delete() {
    	// Deletes an object from the universe
    	paused = true;
    	int ob = -1;
    	while (ob < 0 || ob > initObs.size()-1) {
    		try {
    			ob = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter an index for an object to delete (forever)"));
    		} catch (NumberFormatException e) {
	    		// Didn't enter a number
	        	JOptionPane.showMessageDialog(null, "You didn't type a number!");
	        	continue;
	        } catch (NullPointerException e) {
	            // Cancelled
	        	JOptionPane.showMessageDialog(null, "Cancelled!");
	        	return;
	        }
    		if (ob < 0 || ob > initObs.size()-1)
    		JOptionPane.showMessageDialog(null, "You entered an incorrect index!");
    	}
    	objects.remove(ob);
    	initObs.remove(ob);
    	// Deletes the object entirely (no coming back)
    	paused = false;
    }
    public void force() {
    	// Forces an object from user input (for fun :) )
    	paused = true;
    	int ob = -1;
    	while (ob < 0 || ob > initObs.size()-1) {
    		try {
    			ob = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter an index for an object to force"));
    		} catch (NumberFormatException e) {
	    		// Didn't enter a number
	        	JOptionPane.showMessageDialog(null, "You didn't type a number!");
	        	continue;
	        } catch (NullPointerException e) {
	            // Cancelled
	        	JOptionPane.showMessageDialog(null, "Cancelled!");
	        	return;
	        }
    		if (ob < 0 || ob > initObs.size()-1)
    		JOptionPane.showMessageDialog(null, "You entered an incorrect index!");
    	}
    	// No error catching here yet
    	String forceVal = JOptionPane.showInputDialog(null, "Enter a force to push object "+ob+" (type: xforce yforce)");
    	String forceX = forceVal.substring(0,forceVal.indexOf(" "));
    	String forceY = forceVal.substring(forceVal.indexOf(" "));
    	objects.get(ob).force(Integer.parseInt(forceX), Integer.parseInt(forceY));
    }
}