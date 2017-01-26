import java.util.ArrayList;

import javax.swing.JOptionPane;

/*
 * Kami aka God class, handles construction of one (or more) universes
 * By Sc2ad
 */
public class Kami {
	public static ArrayList<Universe> universes;
	public static void main(String[] args) {
		// Creation of EVERYTHING, RUNS IT ALL
		universes = new ArrayList<Universe>();
		
		makeUniverse();
	}
	public static void makeUniverse() {
		// Make a new universe. If you want to make it with different objects, insert them into the obs array
		Object[] obs = {};
		Universe u = new Universe(obs);
		universes.add(u);
		u.help();
		u.initRun();
	}
}
