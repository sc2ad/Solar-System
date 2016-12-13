
public class Kami {
	public static void main(String[] args) {
		Object[] obs = {new Planet("Earth", 400, 400, 400, 200, 0, 0), new Craft(20, 550, 400, 50)};
		Universe u = new Universe(obs);
		u.initRun();
	}
}
