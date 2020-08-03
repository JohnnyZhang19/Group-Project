package snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * this frame class is to compile all other class, and to make the game run
 * @author Zonglin Zhang UCID: 30020965
 *
 */
public class Frame{
	public int actualRowNum = 30;
	public int actualColNum = 30;
	public int rowNum = actualRowNum+2;
	public int colNum = actualColNum+2;
	List<Node> nodes = new ArrayList<Node>();
	public Snake snake = new Snake();
	public Egg egg = new Egg();
	public Obstacles obs = new Obstacles();
	int score = 0;
	int listLength = 10;
	Node[] obsList = new Node[listLength];
	
	

	public int getScore() {
		return score;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public int getActualRowNum() {
		return actualRowNum;
	}

	public void setActualRowNum(int actualRowNum) {
		this.actualRowNum = actualRowNum;
	}


	public int getListLength() {
		return listLength;
	}

	public void setListLength(int listLength) {
		this.listLength = listLength;
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
		boolean run = true;
		while (run) {
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
				score++;

			}
			
			/**
			 * When the location of snake head move to the wall the console 
			 * will appear "Game Over!!"
			 */
			printFrame();
			if((snake.getHead().getX() == 0 || snake.getHead().getX() == colNum-1) || (snake.getHead().getY() == 0 || snake.getHead().getY() == rowNum-1)) {
				System.out.println("Game Over!!");
				run = false;
			}
//			}else if(snake.getHead().getX() == obs.getLocationO().getX() && snake.getHead().getY() == obs.getLocationO().getY()) {
//				System.out.println("Game Over!!");
//				break;
			for(int i = 0; i < obsList.length; i ++) {
				if(snake.getHead().getX() == obsList[i].getX() && snake.getHead().getY() == obsList[i].getY()) {
					System.out.println("Game Over!!");
					run = false;
				}
			}
			int snakeBodySize = snake.getBody().size();
			for (int i = 1; i < snakeBodySize; i++) {
				Node bodyNode = snake.getBody().get(i);
				if(snake.getHead().getX() == bodyNode.getX() && snake.getHead().getY() == bodyNode.getY()) {
					System.out.println("Game Over!!");
					run = false;
				}
			}
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
		nodes.set(head.getX() + head.getY() *rowNum,new Node(5));

		int snakeBodySize = snake.getBody().size();
		for (int i = 1; i < snakeBodySize; i++) {
			Node node = snake.getBody().get(i);
			int count = node.getX() + node.getY() *rowNum;
			nodes.set(count, new Node(3));
		}

		//Egg replacement
		Node eggLocation = egg.getLocation();
		nodes.set(eggLocation.getX() + eggLocation.getY() *rowNum,new Node(2));
		
		for(int i = 0; i < obsList.length; i ++) {
			Node obsLocation = obsList[i];
			nodes.set(obsLocation.getX() + obsLocation.getY() *rowNum,new Node(4));
		}
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
		/**
		 * to print score
		 */
		System.out.println(score);
	}

	/**
	 * To initialize the position of snake and egg.
	 */
	public void Initialize() {
		snake = new Snake(new Node(rowNum/2,colNum/2,5),new Node(rowNum/2,colNum/2 - 1,3));
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
		nodes.set(head.getX() + head.getY() *rowNum,new Node(5));
		
		int snakeBodySize = snake.getBody().size();
		for (int i = 1; i < snakeBodySize; i++) {
			Node node = snake.getBody().get(i);
			int count = node.getX() + node.getY() *rowNum;
			nodes.set(count, new Node(3));
		}
		
		//Egg replacement
		if(egg.getLocation().getX() == snake.getBody().element().getX() && egg.getLocation().getY() == snake.getBody().element().getY()) {
			egg = new Egg(new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),2));
		}
		Node eggLocation = egg.getLocation();
		nodes.set(eggLocation.getX() + eggLocation.getY() *rowNum,new Node(2));	
		
		for(int i = 0; i < obsList.length; i ++) {
			obs = new Obstacles(new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),4));
			if(!(obs.getLocationO().getX() == snake.getBody().element().getX() && obs.getLocationO().getY() == snake.getBody().element().getY()) ||
					!(obs.getLocationO().getX() == egg.getLocation().getX() && obs.getLocationO().getX() == egg.getLocation().getX())) {
				Node obsLocation = obs.getLocationO();
				nodes.set(obsLocation.getX() + obsLocation.getY() *rowNum,new Node(4));
				obsList[i] = obsLocation;
			}
		}
	}
	
	/**
	 * To reappear a egg in another random position when snake eat egg.
	 */
	public void reAppear() {

		// 2 snake moving

		snake.eat(hasDirection());
		
		//Egg replacement
		egg.reAssign(rowNum,colNum);
		if(egg.getLocation().getX() == snake.getBody().element().getX() && egg.getLocation().getY() == snake.getBody().element().getY()) {
			egg.reAssign(rowNum, colNum);
		}
		Node eggLocation = egg.getLocation();
		nodes.set(eggLocation.getX() + eggLocation.getY() *rowNum,new Node(2));	
		
		//Obstacles shows
		for(int i = 0; i < 5; i ++) {
			obs = new Obstacles(new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),4));
			if(!(obs.getLocationO().getX() == snake.getBody().element().getX() && obs.getLocationO().getY() == snake.getBody().element().getY()) ||
					!(obs.getLocationO().getX() == egg.getLocation().getX() && obs.getLocationO().getX() == egg.getLocation().getX())) {
				Node obsLocation = obs.getLocationO();
				nodes.set(obsLocation.getX() + obsLocation.getY() *rowNum,new Node(4));
				obsList[i] = obsLocation;
			}
		}
	}
	
}
