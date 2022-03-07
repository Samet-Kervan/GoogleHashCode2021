import java.util.ArrayList;

public class Node {
	private String name;
	private ArrayList<Street> streets;
	private int carCount;
	private PriorityQueue q;
	private int[] carCounts;
	public Node(String name) {
		this.name = name;
		streets = new ArrayList<Street>();
		this.carCount = 0;
		q = new PriorityQueue();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void addStreet(Street street) {
		streets.add(street);
	}
	public void set() {
		carCounts = new int[streets.size()];
		for (int i = 0; i < carCounts.length; i++) {
			carCounts[i] = 0;
		}
	}
	public void addCar(Node end) {
		int index = -1;
		for (int i = 0; i < streets.size(); i++) {
			Street street = streets.get(i);
			if (street.getStartNode() == end) {
				index = i;
			}
		}
		carCounts[index]++;
		carCount++;
	}
	public int streetNo() {
		return streets.size();
	}
	public String getStreetName(int index) {
		return streets.get(index).getName();
	}
	public void change(int index) {
		streets.get(index).change();
	}
	public void arrange() {
		for (int i = 0; i < streets.size(); i++) {
			q.add(streets.get(i), carCounts[i]);
		}
	}
	public String getFirst() {
		return q.getFirst().getName();
	}
}
