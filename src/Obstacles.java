
public class Obstacles {
	Node obsloca;
	int num = 0;
	
	public Obstacles(Node location) {
		this.obsloca = location;
	}
	
	public void addObstacles(int rowNum, int colNum) {
		for(num = 0;num < 20;num++) {
			Node obsloca = new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),4);
			this.obsloca = obsloca;
		}
	}
	
	public Node getLocation() {
		return obsloca;
	}
	
}
