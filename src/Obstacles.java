import java.util.Random;

public class Obstacles {
	int obswidth = 3;
	int obsheigt = 2;
	Node loca;
	Egg score;
	
	public Obstacles(Node location) {
		this.loca = location;
	}
	
	public void addObstacles() {
		if(score.getScore()%5 == 0) {
			Random rand = new Random();
			Node loca = new Node(rand.nextInt(15), rand.nextInt(15),2);
			this.loca = loca;
		}else
			return;
		
	}

}
