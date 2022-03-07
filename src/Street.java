
public class Street {
	private String name;
	private Node startNode, endNode;
	private int time, carSize;
	private boolean green;
	public Street(Node startNode, Node endNode, String name, int time) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.name = name;
		this.time = time;
		this.carSize = 0;
		this.green = false;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	Node getStartNode() {
		return startNode;
	}
	void setStartNode(Node startNode) {
		this.startNode = startNode;
	}
	Node getEndNode() {
		return endNode;
	}
	void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	int getTime() {
		return time;
	}
	void setTime(int time) {
		this.time = time;
	}
	public int getCarSize() {
		return carSize;
	}
	public void addCar(){
		carSize++;
	}
	public void removeCar() {
		carSize--;
	}
	public boolean isGreen() {
		return green;
	}
	public void change() {
		if (green == false) {
			green = true;
		}
		else {
			green = false;
		}
	}
}
