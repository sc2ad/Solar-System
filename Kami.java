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
		JOptionPane.showMessageDialog(null, "Press 'n' to make a new planet, 'p' to pause, 'r' to reset, 'z' to zoom in and out, and 'l' to change vector lengths, 'd' to destroy or un-destroy an obect, 'SHIFT-D' to DELETE and object (forever), 'f' to force an object");
		JOptionPane.showMessageDialog(null, "To make another Universe, press SHIFT-C");
		makeUniverse();
	}
	public static void makeUniverse() {
		// Make a new universe. If you want to make it with different objects, insert them into the obs array
		Object[] obs = {};
		Universe u = new Universe(obs);
		universes.add(u);
		u.initRun();
	}
}
