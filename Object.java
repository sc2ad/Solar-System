import java.util.ArrayList;

public class Object implements GravitableObject {
	double posX = 0;
	double posY = 0;
	double vx = 0;
	double vy = 0;
	double ax = 0;
	double ay = 0;
	double mass = 100;
	double radius = 0;
	String type = "object";
	
	boolean exist = true;

	// Different constructors to have initial velocities
	public Object(double mass, double posX, double posY, double radius) {
		this.mass = mass;
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
	}
	public Object(double mass, double posX, double posY, double radius, double vx, double vy) {
		this.mass = mass;
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
		this.vx = vx;
		this.vy = vy;
	}
	public Object(Object o) {
		mass = o.mass;
		posX = o.posX;
		posY = o.posY;
		radius = o.radius;
		vx = o.vx;
		vy = o.vy;
	}
	/*
	@param: obs, an arraylist of objects that are all of the other objects in the universe
	*/
	public void main(ArrayList<Object> objects) {
		// Updates all the positions, accelerations and velocities as well as checks if the planet no longer exists
		double[] netForce = this.netForces(objects);
		ax = netForce[0] * KA / this.mass;
		ay = netForce[1] * KA / this.mass;
		vx += ax;
		vy += ay;
		posX += vx;
		posY += vy;
		for (Object o : objects) {
			if (this.distance(o) + o.radius * KDESTRUCTIONPERCENT < o.radius && exist && o.exists()) {
				// Simple collisions
				if (this.mass > o.mass) {
					o.setExistence(false);
					this.mass += o.mass;
				} else {
					exist = false;
					o.mass += this.mass;
				}
			}
		}
	}
	public void main() {
		vx += ax;
		vy += ay;
		posX += vx;
		posY += vy;
	}
	public boolean exists() {
		return exist;
	}
	public void setExistence(boolean b) {
		exist = b;
	}
	public void execute(ArrayList<Object> objects) {
		main(objects);
	}
	public void execute() {
		main();
	}
	/*
	@param obs, an arraylist of objects that are all of the other objects in the universe
	returns an array of the X and Y netforces
	KG = Constant of Gravity of the universe
	*/
	public double[] netForces(ArrayList<Object> objects) {
		double[] output = {0,0};
		for (Object o : objects) {
			if (o.exists()) {
				double[] netForce = this.calcGravity(o);
				output[0] += netForce[0];
				output[1] += netForce[1];
			}
		}
		if (output[0] == 0 && output[1] == 0) {
			output[0] = -ax * this.mass / KA;
			output[1] = -ay * this.mass / KA;
			// Reset system so it doesn't accelerate to infinity when there are no objects left
		}
		return output;
	}
	/*
	Returns x and y forces of gravity from the @param of the object
	*/
	public double[] calcGravity(Object o) {
		double[] xy = new double[3];
		// Quadrant manipulation
		double angle = Math.atan(Math.abs(o.posY - this.posY) / Math.abs(o.posX - this.posX)) * 180.0 / Math.PI;
		if (o.posX - this.posX < 0 && o.posY - this.posY < 0) {
			angle = 180 - angle;
		} 
		if (o.posX - this.posX < 0 && o.posY - this.posY >= 0) {
			angle += 180;
		}
		if (o.posX - this.posX > 0 && o.posY - this.posY > 0) {
			angle = 360 - angle;
		}
		double magnitude = (KG * o.mass) / (distance(o) * distance(o));
		// Makes sure the magnitude is a realistic number
		if (Double.isInfinite(magnitude)) {
			// Checked itself
			magnitude = 0;
		}
		if (Double.isNaN(angle)) {
			angle = 0;
		}
		xy[0] = magnitude * Math.cos(angle * Math.PI / 180.0);
		xy[1] = -magnitude * Math.sin(angle * Math.PI / 180.0);
		xy[2] = angle;
		return xy;
	}
	/*
	Returns distance from one object to another
	*/
	public double distance(Object o) {
		return Math.sqrt(Math.pow(this.posX - o.posX, 2) + Math.pow(this.posY - o.posY, 2));
	}
	/*
	 * Pushes the object in a certain direction, applies a force
	 */
	public void force(double x, double y) {
		ax += x / this.mass;
		ay += y / this.mass;
	}
	public String toString() {
		// Redundant because display exists
		String out = "";
		if (exist) {
			out += "Mass: " + mass + "\tPosition: (" + posX + ", " + posY + ")\tVelocity: (" + vx + ", " + vy + ")\tAcceleration: (" + ax + ", " + ay + ")";
		} else {
			out += "Object with mass: "+mass+" no longer exists!";
		}
		return out;
	}
}