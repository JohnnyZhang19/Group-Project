package snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frame{
	public int actualRowNum = 15;
	public int actualColNum = 15;
	public int rowNum = actualRowNum+2;
	public int colNum = actualColNum+2;
	List<Node> nodes = new ArrayList<Node>();
	public Snake snake = new Snake();
	public Egg egg = new Egg();
	int score = 0;
	
	

	public List<Node> getNodes() {
		return nodes;
	}

	public int getActualRowNum() {
		return actualRowNum;
	}

	public void setActualRowNum(int actualRowNum) {
		this.actualRowNum = actualRowNum;
	}

	public int getActualColNum() {
		return actualColNum;
	}

	public void setActualColNum(int actualColNum) {
		this.actualColNum = actualColNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void run() {
		//吃到蛋
		if(snake.getBody().getFirst() == egg.getLocation()) {   
			snake.addFromTail(new Node(3));                    //head, Snake, body，addFromTail() 都在Snake类里面
			score = score + 1;
			Egg location = new Egg();
			location.reAssign();
		}
		if(this.gameOver() == false) {
			System.err.println("GAME OVER!!!");
		}
	}


	public void printFrame() {
//		for(int i = 0; i < getRowNum(); i++) {
//			if(i == 0||i == getRowNum()-1) {
//				for (int j = 0; j < getColNum(); j++) {
//					System.out.print("-");
//				}
//			}else {
//				System.out.print("-");
//				for (int j = 0; j < actualColNum; j++) {
//					System.out.print("*");
//				}
//				System.out.print("-");
//			}
//			
//			System.out.println();
//			
//		}
//		
		int counter = 1;
		
		for (Node node : nodes) {
			if(node.getType()==1) {			// map
				System.out.print("*");
			}else if(node.getType()==2) {  // egg
				System.out.print("o");
			}else if(node.getType()==3) {	// snake body
				System.out.print("+");
			}else if(node.getType()==4){	// wall
				System.out.print("-");
			}else {
				System.out.print("#");		// snake head
			}
			
			if(counter%17==0) {
				System.out.println();
			}
			
			counter++;
		}
	}

	
	public void Initialize() {
		snake = new Snake(new Node(2,4,5),new Node(2,3,3));
		snake.getBody().add(1,new Node(2,2,3));
		egg = new Egg(new Node((int)(Math.random()*15+1),(int)(Math.random()*15+1),2));
		
		for(int i = 0; i < getRowNum(); i++) {
			if(i == 0||i == getRowNum()-1) {
				for (int j = 0; j < getColNum(); j++) {
					Node node = new Node(4);
					nodes.add(node);
					//System.out.print(node);
				}
			}else {
				Node firstNode = new Node(4);
				nodes.add(firstNode);
				for (int j = 0; j < actualColNum; j++) {
					Node node = new Node(1);
					nodes.add(node);
				}
				Node lastNode = new Node(4);
				nodes.add(lastNode);
			}
		}
		
		Node head = snake.getBody().getFirst();
		nodes.set(head.getX() + head.getY() *17,new Node(5));
		
		int snakeBodySize = snake.getBody().size();
		for (int i = 1; i < snakeBodySize; i++) {
			Node node = snake.getBody().get(i);
			int count = node.getX() + node.getY() *17;
			nodes.set(count, new Node(3));
		}
		
		//Egg replacement
		Node eggLocation = egg.getLocation();
		nodes.set(eggLocation.getX() + eggLocation.getY() *17,new Node(2));	
	}
	

	public void reAppear() {
		// 1 egg重新生成（判断新egg是否与蛇现在的位置重合）
		egg.reAssign();
		
		
		
		// 2 蛇往前走一步
		int tailX = snake.getTail().getX();
		int tailY = snake.getTail().getY();
		int secondTailX = snake.getTail().getX();
		int secondTailY = snake.getTail().getY();
		
		if(tailX == secondTailX && secondTailY < tailY) {
			snake.addFromTail(new Node(tailX,tailY + 1,3));
		}else if(tailX == secondTailX && secondTailY > tailY) {
			snake.addFromTail(new Node(tailX,tailY - 1,3));
		}else if(tailY == secondTailY && tailX < secondTailX) {
			snake.addFromTail(new Node(tailX + 1,tailY,3));
		}else {
			snake.addFromTail(new Node(tailX - 1,tailY,3));
		}
			
//			新蛋是否与身体合, 新墙是否和蛇重合，新蛋新墙是否重合
			
//			if(snake.getLocation() == location.reAssign()){          //可以get整个链表的x, y值吗
//				location.reAssign();
//			}
//			Obstacles loca = new Obstacles(null);
//			if(body.getLocation() == loca.addObstacles() || head.getLocation() == loca.addObstacles()) {   //怎么避开x+-1, y+-1
//				loca.addObstacles();
//			}
//			if(loca.addObstacles() = location.reAssign()) {
//				location.reAssign();
//				loca.addObstacles();
//			}
//		}
	}
	
	public boolean gameOver() {
		boolean live = true;
		while(live) {
			if(snake.getHead() != new Node(4) || snake.getHead() != snake.getBody().element()) {
				return true;
			}
		}
	
		return false;
	}

	//加速	
		
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.Initialize();
		frame.printFrame();
		
	}
	
}
