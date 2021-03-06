package snake;
/**
 * This class is for setting and getting the positions of the nodes for different elements in the game,
 * namely normal, egg and snake positions.
 * @author Ha Do
 *
 */

public class Node {
	public int x;
	public int y;
	public int type;
	
	public Node(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public Node(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
