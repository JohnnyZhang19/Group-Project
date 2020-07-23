
import java.util.LinkedList;

public class Snake {
	private Node head;
	private Node tail;
	private LinkedList<Node> body = new LinkedList<>();
	
	public LinkedList<Node> getBody() {
		return body;
	}
	public void setBody(LinkedList<Node> body) {
		this.body = body;
	}
	public Node getHead() {
		return head;
	}
	public Node getTail() {
		return tail;
	}
	public Snake(Node head, Node tail) {
		this.head = head;
		this.tail = tail;
		body.add(head);
		body.add(tail);
	}
	
	public Snake() {}
	
	public void addFromHead(Node node) {
		body.addFirst(node);
	}
	
	public void addFromTail(Node node) {
		body.addLast(node);
	}
	
	public void deleteFromTail(Node node) {
		body.removeLast();
	}

	
}
