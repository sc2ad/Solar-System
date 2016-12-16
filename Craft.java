import java.util.ArrayList;

public class Craft extends Object {

	public Craft(double mass, double posX, double posY, double radius) {
		super(mass, posX, posY, radius);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void main(ArrayList<Object> objects) {
		double[] netForce = this.netForces(objects);
		ax = netForce[0] * KA / this.mass;
		ay = netForce[1] * KA / this.mass;
		vx += ax;
		vy += ay;
		posX += vx;
		posY += vy;
		// Doesn't get destroyed by other objects
	}

}
