package snake;

import java.util.ArrayList;
import java.util.List;

public class Frame{
	public int actualRowNum = 15;
	public int actualColNum = 15;
	public int rowNum = actualRowNum+2;
	public int colNum = actualColNum+2;
	List<Node> nodes = new ArrayList<Node>();
	public Snake snake;
	public Node egg;
	
	
	

	public List<Node> getNodes() {
		return nodes;
	}



	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
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
		
	}

	public void printFrame() {
		for(int i = 0; i < getActualRowNum(); i++) {
			if(i == 0||i == getActualRowNum()-1) {
				for (int j = 0; j < getColNum(); j++) {
					System.out.print("-");
				}
			}else {
				System.out.print("-");
				for (int j = 0; j < actualColNum; j++) {
					System.out.print("*");
				}
				System.out.print("-");
			}
			
			System.out.println();
			
		}
		
	}

	
	public void Initialize() {
		for(int i = 0; i < getActualRowNum(); i++) {
			if(i == 0||i == getActualRowNum()-1) {
				for (int j = 0; j < getColNum(); j++) {
					Node node = new Node(j,i,4);
					nodes.add(node);
					System.out.print(node);
				}
			}else {
				Node firstNode = new Node(0,i,4);
				nodes.add(firstNode);
				for (int j = 0; j < actualColNum; j++) {
					Node node = new Node(j,i,1);
					nodes.add(node);
				}
				Node lastNode = new Node(getColNum(),i,4);
				nodes.add(lastNode);
			}
		}
		for(int i = 0; i < nodes.size() - 1; i ++) {
			if(nodes.get(i).equals(nodes.get(actualColNum))) {
				System.out.println(nodes.get(i));
			}
		}
		
	
	}
	
	public void reAppear() {
//		if (egg.location == snake.location) {
//			snake.addFromTail;
//		}
	}
		
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.Initialize();
//		frame.printFrame();
		
	}
	
}
