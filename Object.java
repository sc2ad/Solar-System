public class Object {
	double posX = 0;
	double posY = 0;
	double vx = 0;
	double vy = 0;
	double ax = 0;
	double ay = 0;
	double mass = 100;
	public static final double KG = 5;

	public Object(double mass, double posX, double posY) {
		this.mass = mass;
		this.posX = posX;
		this.posY = posY;
	}
	/*
	@param: obs, an arraylist of objects that are all of the other objects in the universe
	*/
	public void execute(Object... obs) {
		posX += vx;
		posY += vy;
		vx += ax;
		vy += ay;
		double[] netForce = this.netForces(obs);
		ax += netForce[0] / this.mass;
		ay += netForce[1] / this.mass;
	}
	/*
	@param obs, an arraylist of objects that are all of the other objects in the universe
	returns an array of the X and Y netforces
	KG = Constant of Gravity of the universe
	*/
	public double[] netForces(Object... obs) {
		double[] output = {0,0};
		for (Object o : obs) {
			double[] netForce = this.calcGravity(o);
			output[0] += netForce[0];
			output[1] += netForce[1];
			// Left off
		}
	}
	/*
	Returns x and y forces of gravity from the @param of the object
	*/
	public double[] calcGravity(Object o) {
		double[] xy = new double[2];
		//try
		// Quadrant manipulation
		double angle = Math.arctan(Math.abs(o.posY - this.posY) / Math.abs(o.posX - this.posX)) * 180.0 / Math.PI;
		if (o.posY - this.posY < 0 && o.posX - this.posX > 0) {
			angle = 360 - angle;
		} else if (o.posY - this.posY < 0 && o.posX - this.posX < 0) {
			angle += 180;
		} else if (o.posY - this.posY > 0 && o.posX - this.posX < 0) {
			angle = 180 - angle;
		}
		//catch
		// angle = 90
		double magnitude = (KG * this.mass * o.mass) / (distance(o) * distance(o));
		xy[0] = magnitude * Math.cos(angle * Math.PI / 180.0);
		xy[1] = magnitude * Math.sin(angle * Math.PI / 180.0);
		return xy;
	}
	/*
	Returns distance from one object to another
	*/
	public double distance(Object o) {
		return Math.sqrt(Math.pow(this.posX - o.posX, 2) + Math.pow(this.posY - o.posY, 2));
	}

}