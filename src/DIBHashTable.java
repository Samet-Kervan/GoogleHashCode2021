import java.util.ArrayList;

//A hash table class that uses DIB for collision handling.
//This class is written mainly for string data types.
public class DIBHashTable<K,V> extends AbstractHashTable<K,V> {
	public DIBHashTable() {
		super();
	}
	public DIBHashTable(int size) {
		this(size,37);
	}
	public DIBHashTable(int size, int prime) {
		this(size,prime,0.50);
	}
	public DIBHashTable(boolean hashOption, double loadFactor) {
		//For changing between load factors
		this(997,37,loadFactor);
	}
	public DIBHashTable(int size, int prime, double loadFactor) {
		super(size,prime,loadFactor);
	}
	protected void createTable() {
		table = (HashNode<K,V>[]) new HashNode[size];
	}
	protected HashNode<K,V> get(K k) {//Searches the given key in the hash table. 
									  //If the key is not in the table returns a hash node object with null values.
		int key = getKey(k);//Finds the key integer of the given key
		int index = key % size;
		if (index < 0) {//There might be a overflow
			index += size;
		}
		HashNode<K,V> node = table[index];
		while (!(node == null)) {//Searches the key in the table till finding it or when it encounters an empty space
								//or when it reaches the end of the table
			if (node.getkey().equals(k)) {
				return node;
			}
			else {
				index++;
				if (index >= size) {
					return null;
				}
				node = table[index];
			}
		}
		return node;
	}
	protected int getKey(K k) {//Finding keys for string with polynomial hash function
		try {
			char characters[] = ((String) k).toCharArray();
			int key = 0, power = characters.length - 1;
			for (int i = 0; i < characters.length; i++) {
				int  expression = (int) ((characters[i] - 64)* (Math.pow(prime, power)));
				//lower case letters start at the 97th in the ASCII table so -96 gives the letters alphabet location
				key += expression;//When I directly make key += than key does not overflow so I made it this way
				power--;
			}
			return key;
		} catch (ClassCastException e) {
			return (int) k;
		}
	}
	protected void put(K k, V v) {//inserts the given key in table
		HashNode<K,V> arrayNode = get(k);
		HashNode<K,V> node = new HashNode<K,V>(k,v);
		boolean resize = false;//Program might have called the resize function while trying to insert the node
								//so that node has to be inserted to the table again. This is used for checking that
		if (arrayNode == null) {
			int key = getKey(k);
			int index = key % size;
			if (index < 0) {//Might be a overflow so index will be negative. This way it will be positive
				index += size;
			}
			node.setDistanceFromOriginal(0);
			while(true) {
				if (table[index] == null) {//Find an empty spot
					table[index] = node;
					n++;
					break;
				}
				else{
					if (table[index].getDistanceFromOriginal() < node.getDistanceFromOriginal()) {
						//When the node that is inserted in table has lower DIB than the node that has already
						//inserted it changes the nodes and tries to insert the other one.
						HashNode<K,V> tempNode = table[index];
						table[index] = new HashNode<K,V>(node.getkey(), node.getcontent(), node.getDistanceFromOriginal());
						node = tempNode;
					}
					index++;
					node.addDistanceFromOriginal();
					if (index >= size) {//It reached the end of the table
						resize();
						resize = true;
						break;
					}
				}
			}
		}
		if (resize) {//When the key could not be inserted because it reached the end of the table and forced to call
					//the resize function it tries to insert the key in table again
			put(node.getkey(),node.getcontent());
			HashNode<K,V> resizeNode = get(node.getkey());
		}
		double db = (double) n / size;
		if (db >= loadFactor) {//If the table is reached to the load factor. Using a variable because it does not gives an appropriate value otherwise
			resize();
		}
	}
	public V getContent(K key) {
		HashNode<K,V> node = get(key);
		try {
			return node.getcontent();
		} catch (Exception e) {
			return null;
		}
	}
	public void setNodes() {
		for (int i = 0; i < table.length; i++) {
			try {
				Node node = (Node) table[i].getcontent();
				node.set();
			} catch (Exception e) {
			}
		}
	}
	public ArrayList<V> getAll(){
		ArrayList<V> list = new ArrayList<V>();
		for (int i = 0; i < table.length; i++) {
			try {
				V v = table[i].getcontent();
				list.add(v);
			} catch (Exception e) {
			}
		}
		return list;
	}
}
