//An abstract hash table for general use
public abstract class AbstractHashTable<K,V> {
	protected int n = 0;//Number of elements in the table
	protected int prime, size;//Prime is for PAF hash function
	protected double loadFactor;//States when the resize function should be called
	protected HashNode<K,V>[] table;
	protected class HashNode<A,B> {//Nested node class for elements
		private A key;
		private B content;
		private int distanceFromOriginal;
		//Constructors
		protected HashNode(A key, B content) {//Constructor without a DIB value
			this(key, content, 0);
		}
		protected HashNode(A key, B content, int distanceFromOriginal) {
			this.key = key;
			this.content = content;
			this.distanceFromOriginal = distanceFromOriginal;
		}
		//end of constructors
		protected A getkey() {
			return key;
		}
		protected void setkey(A key) {
			this.key = key;
		}
		protected B getcontent() {
			return content;
		}
		protected void setcontent(B content) {
			this.content = content;
		}
		public int getDistanceFromOriginal() {
			return distanceFromOriginal;
		}
		public void setDistanceFromOriginal(int distanceFromOriginal) {
			this.distanceFromOriginal = distanceFromOriginal;
		}
		public void addDistanceFromOriginal() {
			distanceFromOriginal++;
		}
	}//End of nested class
	public AbstractHashTable(){
		this(997);
	}
	public AbstractHashTable(int size){
		this(size,37,0.5);
	}
	public AbstractHashTable(int size, int prime, double loadFactor){
		this.size = size;
		this.prime = prime;
		this.loadFactor = loadFactor;
		createTable();
	}
	public int getSize() {
		return size;
	}
	public int getPrime() {
		return prime;
	}
	public double getLoadFactor() {
		return loadFactor;
	}
	public void setLoadFactor(double newLoadFactor) {
		this.loadFactor = newLoadFactor;
	}
	//Abstract functions for extended classes
	protected abstract void createTable();
	protected abstract HashNode<K,V> get(K k);
	protected abstract int getKey(K k);
	protected abstract void put(K k, V v);
	protected void resize() {//Creates a new table with doubled size.
		HashNode<K,V>[] buffer = (HashNode<K,V>[]) new HashNode[size];
		for (int i = 0; i < buffer.length; i++) {
			//backups the nodes
			buffer[i] = table[i];
		}
		n = 0;//Number of elements are zero because everything will be added again
		size = size * 2;//Doubles the size
		createTable();//Creates a new table
		for (int i = 0; i < buffer.length; i++) {
			//Puts the old nodes to the table again using the backup
			try {
				put(buffer[i].getkey(),buffer[i].getcontent());
			} catch (NullPointerException e) {//Some indexes of the buffer will be null. 
			}
		}
	}
}
