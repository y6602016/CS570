public class LinkedListSize<E> {
	private class Node<E> {
		E data;
		Node<E> next;
		Node<E> prev;
		
		public Node(E elem) {
			this.data = elem;
			this.next = null;
			this.prev = null;
		}
		public Node(E elem, Node<E> prev, Node<E> next) {
			this.data = elem;
			this.next = next;
			this.prev = prev;
		}
	}
	private Node<E> head;
	
	public LinkedListSize() {
		this.head = null;
	}
	
	private int traverse(Node<E> node) {
		if (node == null) {
			return 0;
		}
		return traverse(node.next) + 1;
	}
	
	public int getSize () {
		return this.traverse(this.head.next);
	}
}
