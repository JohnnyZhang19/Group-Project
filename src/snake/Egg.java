package snake;


public class Egg {
	Integer score = 1;
	Node location;

	public int getScore() {
		return score;
	}
	public Egg() {
		
	}
	
	public Egg(Node location) {
		this.location = location;
	}

	public void reAssign(int rowNum, int colNum) {
		Node location = new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),2);
		this.location = location;
/**		if(location == Obstacles.addObstacles(int rowNum, int colNum)) {
			location = new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),2);
		}
*/	}
	
	public Node getLocation() {
		return location;
	}
	
	
}
