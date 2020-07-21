import java.util.ArrayList;
import java.util.List;

public class Snake {
	private Node head;
	private Node tail;
	private List<Node> body;
	
	public Snake() {
		body = new ArrayList<Node>();
	}
	
	public void addFromTail(Node node) {
		body.add(node);
	}
	
	public void addFromHead(Node node) {
		body.add(0,node);
	}
	
	public void deleteFromTail(Node node) {
		body.remove(body.size()-1);
	}
	
	public boolean checkDead(int maxX, int maxY) {
		head = body.get(0);
		tail = body.get(body.size()-1);
		
		if(head.getxCoord()>=maxX || head.getyCoord()>=maxY) {
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
