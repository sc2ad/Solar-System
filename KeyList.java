import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyList implements KeyListener {

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int keyCode = arg0.getKeyCode();
		String key = KeyEvent.getKeyText(keyCode);
		if (key.equalsIgnoreCase("p")) {
			Universe.paused = !Universe.paused;
		}
		if (key.equalsIgnoreCase("r")) {
			Universe.reset();
		}
		if (key.equalsIgnoreCase("z")) {
			Universe.zoom();
		}
		if (key.equalsIgnoreCase("l")) {
		    Universe.changeLineLength();
		}
		if (key.equalsIgnoreCase("n")) {
		    Universe.createPlanet();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
}