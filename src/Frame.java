
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	/**
	 * To make the snake move and make players decide the 
	 * direction 
	 */
	public void run() {
		Initialize();
		printFrame();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			/**
			 * Enter a direction to decide where the snake should go
			 */
			System.out.println("enter a direction( w: up, d: right, s: down,a: left )");
			String direction = scanner.nextLine();
			if (!(direction.equals("a") || direction.equals("s") || direction.equals("d")  || direction.equals("w"))) {
				continue;
			}
			
			int headDirection = hasDirection();
			
			/**
			 * To ensure the snake will not move opposite
			 */
			if((direction.equals("a") && headDirection == 2) || (direction.equals("s") && headDirection == 1) || 
					(direction.equals("w") && headDirection == 3) || (direction.equals("d") && headDirection == 4)) {
				
				continue;
			}
			
			if(direction.equals("a")) {
				move(4);
			}else if(direction.equals("s")){
				move(3);
			}else if(direction.equals("w")) {
				move(1);
			}else {
				move(2);
			}
			/**
			 * To eat eggs when the snake head move to the location of eggs
			 */
			if(snake.getHead().getX() == egg.getLocation().getX() && snake.getHead().getY() == egg.getLocation().getY()) {
				if(direction.equals("a")) {
					eat(4);
				}else if(direction.equals("s")){
					eat(3);
				}else if(direction.equals("w")) {
					eat(1);
				}else {
					eat(2);
				}
			}
			/**
			 * When the location of snake head move to the wall the console 
			 * will appear "Game Over!!"
			 */
			printFrame();
			if((snake.getHead().getX() == 0 || snake.getHead().getX() == colNum-1) || (snake.getHead().getY() == 0 || snake.getHead().getY() == rowNum-1)) {
				System.out.println("Game Over!!");
				break;
			}
//			for(int i = 1; i <= snake.getBody().size(); i ++) {
//				int bodyX = snake.getBody().get(i).getX();
//				int bodyY = snake.getBody().get(i).getY();
//				if(snake.getHead().getX() == bodyX && snake.getHead().getY() == bodyY) {
//					break;
//				}
//			}
			
		}
		scanner.close();
		
	}
	
	
	/**
	 * To declare the direction by certain numbers.
	 * @param direction(1: up, 2: right, 3: down, 4:left)
	 */
	private void move(int direction) {
		if (direction==1) {
			snake.up();
		}else if (direction==2) {
			snake.right();
		}else if (direction==3) {
			snake.down();
		}else {
			snake.left();
		}
		refresh();
		
	}
	
	/**
	 * Every time the snake move and the egg appear, this frame will 
	 * print once to refresh location of the nodes of snakes and egg.
	 */
	private void refresh() {
		nodes.clear();

		for(int i = 0; i < getRowNum(); i++) {
			if(i == 0||i == getRowNum()-1) {
				for (int j = 0; j < getColNum(); j++) {
					Node node = new Node(4);
					nodes.add(node);
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
		nodes.set(eggLocation.getX() + eggLocation.getY() *rowNum,new Node(2));	
	}

	/**
	 * When the location of snake head is same as the egg.
	 * @param direction
	 */
	private void eat(int direction) {
		snake.eat(direction);
		reAppear();
		refresh();
	}
	
	/**
	 * To get the opposite direction of the snake and avoid that 
	 * direction.
	 * @return 1: up, 2: right, 3: down, 4:left
	 */
	private int hasDirection(){
		int headX = snake.getHead().getX();
		int headY = snake.getHead().getY();
		int secondX = snake.getBody().get(1).getX();
		int secondY = snake.getBody().get(1).getY();
		if(headX == secondX) {
			// vertical 
			if(headY < secondY) {
				//head-up
				return 1;
			}else {
				//head-down
				return 3;
			}
		}else {
			// Horizontal
			if(headX < secondX) {
				//head-left
				return 4;
			}else {
				//head-right
				return 2;
			}
		}
	}

	/**
	 * To print the map.
	 */
	public void printFrame() {
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
			
			if(counter%rowNum==0) {
				System.out.println();
			}
			
			counter++;
		}
	}

	
	/**
	 * To initialize the position of snake and egg.
	 */
	public void Initialize() {
		snake = new Snake(new Node(2,4,5),new Node(2,3,3));
		egg = new Egg(new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),2));
		
		for(int i = 0; i < getRowNum(); i++) {
			if(i == 0||i == getRowNum()-1) {
				for (int j = 0; j < getColNum(); j++) {
					Node node = new Node(4);
					nodes.add(node);
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
	

	/**
	 * To reappear a egg in another random position when snake eat egg.
	 */
	public void reAppear() {
		// 1 egg
		egg.reAssign(rowNum,colNum);

		// 2 snake moving

		snake.eat(hasDirection());
		
		//Egg replacement
		Node eggLocation = egg.getLocation();
		nodes.set(eggLocation.getX() + eggLocation.getY() *rowNum,new Node(2));	
			
	}
	
	public boolean gameOver() {
		boolean live = false;
		
		while(live) {
			if(!(snake.getHead().getX() == 0 || snake.getHead().getX() == actualColNum) || !(snake.getHead().getY() == 0 || snake.getHead().getY() == actualRowNum)) {
				live = true;
			}
			for(int i = 1; i <= snake.getBody().size(); i ++) {
				if(!(snake.getHead().getX() == snake.getBody().get(i).getX() || snake.getHead().getY() == snake.getBody().get(i).getY())) {
					live = true;
				}
			}
		}
		return live;
	}
		
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.run(); 
		
	}
	
}
