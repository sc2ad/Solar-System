public class Planet extends Object {
	double radius = 100;
	String name;
	public Planet(String name, double mass, double posX, double posY) {
		super(mass, posX, posY);
		this.name = name;
	}
	public setRadius(double radius) {
		this.radius = radius;
	}
	// Deal with a way to make sure objects stay radius distance away from the planet's center

	@Override
	public String toString() {
		String out = "";
		out += "";
		return out;
	}
}