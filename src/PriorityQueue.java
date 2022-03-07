import java.util.ArrayList;

public class PriorityQueue {
	private ArrayList<QNode> queue;
	private class QNode{
		private int priority;
		private Street obj;
		public QNode(int priority, Street obj) {
			this.priority = priority;
			this.obj = obj;
		}
		int getPriority() {
			return priority;
		}
		void setPriority(int priority) {
			this.priority = priority;
		}
		Street getObj() {
			return obj;
		}
		void setObj(Street obj) {
			this.obj = obj;
		}
	}
	public PriorityQueue() {
		queue = new ArrayList<QNode>();
	}
	public void add(Street st, int no) {
		QNode node = new QNode(no, st);
		if (queue.isEmpty()) {
			queue.add(node);
		}
		else {
			boolean flag = false;
			for (int i = 0; i < queue.size(); i++) {
				if (no > queue.get(i).getPriority()) {
					queue.add(i,node);
					flag = true;
					break;
				}
			}
			if (!flag) {
				queue.add(node);
			}
		}
	}
	public Street getFirst() {
		return queue.remove(0).getObj();
	}
}
