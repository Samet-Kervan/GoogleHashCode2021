
public class Car {
	private int no, maxStreet, currentStreet, count, timer;
	private Street[] streetToCross;
	public Car(int no, int maxStreet) {
		this.no = no;
		this.maxStreet = maxStreet;
		currentStreet = 0;
		count = 0;
		this.timer = 0;
		streetToCross = new Street[maxStreet];
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getMaxStreet() {
		return maxStreet;
	}
	public void setMaxStreet(int maxStreet) {
		this.maxStreet = maxStreet;
	}
	public int getCurrentStreet() {
		return currentStreet;
	}
	public void setCurrentStreet(int currentStreet) {
		this.currentStreet = currentStreet;
	}
	public void addStreet(Street street) {
		streetToCross[count] = street;
		count++;
	}
	public Street getCurrent() {
		return streetToCross[currentStreet];
	}
	public boolean isDone() {
		if (timer >= streetToCross[currentStreet].getTime()) {
			timer = 0;
			currentStreet++;
			return true;
		}
		else {
			timer++;
			return false;
		}
	}
}
