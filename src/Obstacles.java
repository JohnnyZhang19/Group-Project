import java.util.Random;

public class Obstacles {
	int obswidth = 3;
	int obsheigt = 2;
	Node loca;
	
	public Obstacles(Node location) {
		this.loca = location;
	}
	
	public void addObstacles() {
			Random rand = new Random();
			Node loca = new Node(rand.nextInt(15), rand.nextInt(15),2);
			this.loca = loca;
	}
	public Node getLocation() {
		
		return loca;
	}
	
}
