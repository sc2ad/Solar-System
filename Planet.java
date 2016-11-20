public class Planet extends Object {
	double radius = 100;
	String name;
	public Planet(String name, double mass, double posX, double posY, double radius) {
		super(mass, posX, posY);
		this.name = name;
	}
	public setRadius(double radius) {
		this.radius = radius;
	}
	// Deal with a way to make sure objects stay radius distance away from the planet's center
	@Override
	public void execute(Object... obs) {
		this.main(obs);
		for (Object o : obs) {
			if (this.distance(o) <= radius) {
				// Push the object out with a force of gravity (normal force)
				double[] xy = o.calcGravity(this);
				xy[0] = -xy[0];
				xy[1] = -xy[1];
				// Reverse forces to push out the object
				o.force(xy[0], xy[1]);
				// o needs to have its position set to the proper distance from the object
			}
		}
	}
	@Override
	public String toString() {
		String out = "";
		out += name + ":\n";
		out += "Mass: " + mass + "\tPosition: (" + posX + ", " + posY + ")\tVelocity: (" + vx + ", " + vy + ")\tAcceleration: (" + ax + ", " + ay + ")";
		return out;
	}
}