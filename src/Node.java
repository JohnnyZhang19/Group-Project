/**
 * This class is for setting and getting the positions of the nodes for different elements in the game,
 * namely normal, egg and snake positions.
 * @author Ha Do
 *
 */
public class Node {
		private int xCoord;
		private int yCoord;
		private int type;
 
		public Node(int xCoord, int yCoord) {
			this.xCoord = xCoord;
			this.yCoord = yCoord;
			this.type = type;
		}
		
		public int getXCoord() {
			return xCoord;
		}
		
		public int getYCoord() {
			return yCoord;
		}
		
		public int getType() {
			return type;
		}
		
		public void setXCoord(int xCoord) {
			this.xCoord = xCoord;
		}
		
		public void setYCoord(int yCoord) {
			this.yCoord = yCoord;
		}
		
		public void setType(int type) {
			this.type = type;
		}
}
