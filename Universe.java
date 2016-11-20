import java.util.ArrayList;
public class Universe {
	public static void main(String[] args) {
		// JFRAME STUFF GOES HERE...
		ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(new Object(1000,0,0));
		objects.add(new Object(0.1,1,0));
		for (int i = 0; i < 5; i++) {
			run(objects);
		}
	}
	public run(Object... obs) {
		for (int i = 0; i < obs.size(); i++) {
			Object temp = obs.get(i);
			obs.remove(i);
			temp.execute(obs);
			obs.add(temp, i);
			System.out.println(temp);
		}
	}
}