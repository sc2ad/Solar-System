import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyList implements KeyListener {

	Universe u;
	public KeyList(Universe u) {
		this.u = u;
	}
	
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
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
}