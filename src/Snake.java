
import java.util.ArrayList;
import java.util.List;

public class Snake {
	
	private Node head;
	private Node tail;
	
	// Create a List of type Node
	// List<ClassName> listName;
	private List<Node> body;
	
	
	public Snake() {
		body = new LinkedList<Node>();
		
		// By default there should be something in the snake body, hence we are adding one node. 
		// Starting at 1,1 location.
		Node node = new Node();
		node.setxCoord(1);
		node.setyCoord(1);
		body.add(node);
	}
	
	public void addFromTail(Node node) {
		body.add(node);
	}
	
	public void addFromHead(Node node) {
		//we can pass the index of the element where the node should be inserted
		body.add(0,node);
	}
	
	public void deleteFromTail() {
		body.remove(body.size()-1);
	}
	
	// If the snake touches the boundary. maxX maxY or lowX,lowY (0,0)
	// Snake touches its own body
	
	public boolean checkDead(int maxX, int maxY) {
		// Head is the first position in the body
		head = body.get(0);

		// tail is the last position in the body
		tail = body.get(body.size()-1);
		
		
		if(head.getxCoord()>=maxX || head.getyCoord()>=maxY || head.getxCoord()<0 || head.getyCoord()<0) {
			return true;
		}else {
			
			for(int i=1;i<body.size();i++) {
				Node bodyNode = body.get(i);
				if(bodyNode.getxCoord()==head.getxCoord() && bodyNode.getyCoord()==head.getyCoord()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<Node> getBody() {
		return body;
	}
	
}
