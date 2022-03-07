import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TrafficControl {
	private String fileName;
	private int time, nodeSize, edgeSize, carSize, point;
	private DIBHashTable<String, Street> streets;
	private DIBHashTable<String, Node> nodes, oneWays;
	private DIBHashTable<Integer, Car> cars;
	private ArrayList<Car> carsList, carsList2;
	private ArrayList<String> write;
	public TrafficControl(String fileName) {
		this.fileName = fileName;
		streets = new DIBHashTable<String, Street>();
		nodes = new DIBHashTable<String, Node>();
		cars = new DIBHashTable<Integer, Car>();
		carsList = new ArrayList<Car>();
		carsList2 = new ArrayList<Car>();
		write = new ArrayList<String>();
		readFile();
		oneWays = new DIBHashTable<String, Node>();
		setC();
		ArrayList<Node> nd = nodes.getAll();
		for (int i = 0; i < nd.size(); i++) {
			nd.get(i).arrange();
		}
		oneWay();
		writeFile(fileName.charAt(0) + "o.txt");
	}
	private void readFile() {
		File file = new File(fileName);
		try {
			Scanner sc = new Scanner(file);
			String read = sc.nextLine();
			String[] array = read.split(" ");
			time = Integer.parseInt(array[0]);
			nodeSize = Integer.parseInt(array[1]);
			edgeSize = Integer.parseInt(array[2]);
			carSize = Integer.parseInt(array[3]);
			point = Integer.parseInt(array[4]);
			for (int i = 0; i < edgeSize; i++) {
				read = sc.nextLine();
				array = read.split(" ");
				Node startNode = nodeToTable(array[0]);
				Node endNode = nodeToTable(array[1]);
				Street street = new Street(startNode, endNode, array[2], Integer.parseInt(array[3]));
				streets.put(array[2], street);
				endNode.addStreet(street);
			}
			for (int i = 0; i < carSize; i++) {
				read = sc.nextLine();
				array = read.split(" ");
				Car car = new Car(i, Integer.parseInt(array[0]));
				for (int j = 0; j < Integer.parseInt(array[0]); j++) {
					Street street = streets.getContent(array[1 + j]);
					car.addStreet(street);
				}
				cars.put(i, car);
				carsList.add(car);
			}
			nodes.setNodes();
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File does not exists. Try after fixing the problem");
			System.exit(0);
		}
	}
	private Node nodeToTable(String name) {
		Node node = nodes.getContent(name);
		if (node == null) {
			node = new Node(name);
			nodes.put(name, node);
		}
		return node;
	}
	private void setC() {
		for (int i = 0; i < carsList.size(); i++) {
			Car car = carsList.get(i);
			Street street = car.getCurrent();
			Node node = street.getEndNode();
			node.addCar(street.getStartNode());
		}
	}
	private void setCars(int currentTime) {
		ArrayList<Integer> ended = new ArrayList<Integer>();
		for (int i = 0; i < carsList2.size(); i++) {
			Car car = carsList2.get(i);
			if (car.isDone()) {
				Street street = car.getCurrent();
				Node node = street.getEndNode();
				node.addCar(street.getStartNode());
				ended.add(i);
			}
		}
		for (int i = 0; i < ended.size(); i++) {
			carsList2.remove(ended.get(i) - i);
		}
	}
	private void oneWay() {
		ArrayList<Node> nodeList = nodes.getAll();
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			if (node.streetNo() == 1) {
				String toWrite = node.getName() + "\n1\n" + node.getStreetName(0) + " 1\n";
				write.add(toWrite);
				node.change(0);
				oneWays.put(node.getName(), node);
			}
			else {
				String toWrite = node.getName() + "\n" + node.streetNo() + "\n";
				for (int j = 0; j < node.streetNo(); j++) {
					toWrite += node.getFirst() + " 1\n";
				}
				write.add(toWrite);
			}
		}
	}
	private void writeFile(String fileName) {
		try {
			File file = new File(fileName);
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(write.size() + "\n");
			for (int i = 0; i < write.size(); i++) {
				String st = write.get(i);
				writer.write(st);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("There was a problem in writing");
			System.exit(0);
		}
	}
}
