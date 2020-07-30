package snake;

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
		head = body.getFirst();
		
	}
	
	public void addFromTail(Node node) {
		body.addLast(node);
		tail = body.getLast();
	}
	
	public void deleteFromTail() {
		body.removeLast();
		tail = body.getLast();
	}
	
	public void move(int direction) {
		// TODO Auto-generated method stub

	}
	
	public void right() {
		addFromHead(new Node(getHead().getX()+1, getHead().getY(),5));
		deleteFromTail();
	}

	public void up() {
		addFromHead(new Node(getHead().getX(),getHead().getY()-1,5));
		deleteFromTail();
	}

	public void down() {
		addFromHead(new Node(getHead().getX(), getHead().getY()+1,5));
		deleteFromTail();
	}

	public void left(){
		addFromHead(new Node(getHead().getX()-1, getHead().getY(),5));
		deleteFromTail();
	}
	
	/**
	 * 
	 * @param direction(1: up, 2: right, 3: down, 4:left) 
	 */
	public void eat(int direction) {
		if (direction==1) {
			addFromHead(new Node(getHead().getX(),getHead().getY()-1,5));
		}else if (direction==2) {
			addFromHead(new Node(getHead().getX()+1, getHead().getY(),5));
		}else if (direction==3) {
			addFromHead(new Node(getHead().getX(), getHead().getY()+1,5));
		}else {
			addFromHead(new Node(getHead().getX()-1, getHead().getY(),5));
		}
	}

	
}
