import java.util.ArrayList;

public class Object {
	double posX = 0;
	double posY = 0;
	double vx = 0;
	double vy = 0;
	double ax = 0;
	double ay = 0;
	double mass = 100;
	double radius = 0;
	String type = "object";
	
	boolean exists = true;
	public static final double KG = 1;
	public static final double KA = 0.1;
	public static final double KDESTRUCTIONPERCENT = 0.6;

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
	/*
	@param: obs, an arraylist of objects that are all of the other objects in the universe
	*/
	public void main(ArrayList<Object> objects) {
		double[] netForce = this.netForces(objects);
		ax = netForce[0] * KA / this.mass;
		ay = netForce[1] * KA / this.mass;
		vx += ax;
		vy += ay;
		posX += vx;
		posY += vy;
		for (Object o : objects) {
			if (this.distance(o) + o.radius * KDESTRUCTIONPERCENT < o.radius && this.exists && o.exists) {
				// Simple collisions
				if (this.mass > o.mass) {
					o.exists = false;
					this.mass += o.mass;
				} else {
					this.exists = false;
					o.mass += this.mass;
				}
			}
		}
	}
	public void execute(ArrayList<Object> objects) {
		main(objects);
	}
	/*
	@param obs, an arraylist of objects that are all of the other objects in the universe
	returns an array of the X and Y netforces
	KG = Constant of Gravity of the universe
	*/
	public double[] netForces(ArrayList<Object> objects) {
		double[] output = {0,0};
		for (Object o : objects) {
			if (o.exists) {
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
		double[] xy = new double[2];
		//try
		// Quadrant manipulation
		double angle = Math.atan(Math.abs(o.posY - this.posY) / Math.abs(o.posX - this.posX)) * 180.0 / Math.PI;
		if (o.posX - this.posX < 0 && o.posY - this.posY < 0) {
			angle = 180 - angle;
			System.out.println("180-"+type);
		} 
		if (o.posX - this.posX < 0 && o.posY - this.posY >= 0) {
			angle += 180;
			System.out.println("180+"+type);
		}
		if (o.posX - this.posX > 0 && o.posY - this.posY > 0) {
			angle = 360 - angle;
			System.out.println("360-"+type);
		}
		//System.out.println(angle);
		double magnitude = (KG * o.mass) / (distance(o) * distance(o));
		xy[0] = magnitude * Math.cos(angle * Math.PI / 180.0);
		xy[1] = -magnitude * Math.sin(angle * Math.PI / 180.0);
		return xy;
	}
	/*
	Returns distance from one object to another
	*/
	public double distance(Object o) {
		return Math.sqrt(Math.pow(this.posX - o.posX, 2) + Math.pow(this.posY - o.posY, 2));
	}
	public void force(double x, double y) {
		ax += x / this.mass;
		ay += y / this.mass;
	}
	public String toString() {
		String out = "";
		if (exists) {
			out += "Mass: " + mass + "\tPosition: (" + posX + ", " + posY + ")\tVelocity: (" + vx + ", " + vy + ")\tAcceleration: (" + ax + ", " + ay + ")";
		} else {
			out += "Object with mass: "+mass+" no longer exists!";
		}
		return out;
	}
}