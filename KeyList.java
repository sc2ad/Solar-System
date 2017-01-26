import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * KeyListener for the graphical display. Allows much more friendly user interaction.
 * By Sc2ad
 */
public class KeyList implements KeyListener {

	Universe u;
	public KeyList(Universe u) {
		this.u = u;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// Not needed
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String key = KeyEvent.getKeyText(keyCode);
		// Get the letter that was pressed
		if (key.equalsIgnoreCase("p")) {
			u.paused = !u.paused;
		}
		if (key.equalsIgnoreCase("r")) {
			u.reset();
		}
		if (key.equalsIgnoreCase("z")) {
			u.zoom();
		}
		if (key.equalsIgnoreCase("l")) {
		    u.changeLineLength();
		}
		if (key.equalsIgnoreCase("n")) {
		    u.createPlanet();
		}
		if (key.equalsIgnoreCase("d") && !e.isShiftDown()) {
			u.destroy();
		}
		if (key.equalsIgnoreCase("d") && e.isShiftDown()) {
			u.delete();
		}
		if (key.equalsIgnoreCase("c") && e.isShiftDown()) {
			Kami.makeUniverse();
		}
		if (key.equalsIgnoreCase("f")) {
			u.force();
		}
		if (key.equalsIgnoreCase("h")) {
			u.help();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Not needed
	}
	
}