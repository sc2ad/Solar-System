public class Star extends Object {
	double radius = 10000;
	String name;
	public Star(String name, double mass, double posX, double posY, double radius) {
		super(mass, posX, posY, radius);
		this.name = name;
		this.radius = radius;
	}
}