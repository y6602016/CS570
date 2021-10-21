import java.util.*;

public class IDLList<E> {
	
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
	private Node<E> tail;
	private int size;
	private ArrayList<Node<E>> indices;
	
	// constructor to create an empty list
	public IDLList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
		this.indices = new ArrayList<Node<E>>();
	}
	
	// add the node at the given index of the list
	public boolean add (int index, E elem) {
		try {
			if (index == 0) {
				this.add(elem);
			}
			else if (index == this.size) {
				this.append(elem);
			}
			else {
				Node<E> prev_node = this.indices.get(index - 1);
				Node<E> next_node = this.indices.get(index);
				Node<E> node = new Node<E>(elem, prev_node, next_node);
				prev_node.next = node;
				next_node.prev = node;
				this.indices.add(index, node);
				this.size += 1;
			}
			return true;
		}
		// if the index is out of current size or index < 0, throw exception
		catch (Exception excp) {
			return false;
		}
	}
	
	// add the node at the head of the list
	public boolean add (E elem) {
		Node<E> node = new Node<E>(elem);
		try {
			// if the list is empty, both head and tail point to the node
			if (this.size == 0) {
				this.head = node;
				this.tail = node;
				this.indices.add(node);
			}
			// if the list is not empty, replace the original head node
			else {
				this.head.prev = node;
				node.next = this.head;
				this.head = node;
				this.indices.add(0, node);
			}
			this.size += 1;
			return true;
		}
		catch (Exception excp) {
			return false;
		}
	}
	
	// add the node at the tail of the list
	public boolean append (E elem) {
		Node<E> node = new Node<E>(elem);
		try {
			// if the list is empty, both head and tail point to the node
			if (this.size == 0) {
				this.head = node;
				this.tail = node;
				this.indices.add(node);
			}
			// if the list is not empty, replace the original head node
			else {
				this.tail.next = node;
				node.prev = this.tail;
				this.tail = node;
				this.indices.add(node);
			}
			this.size += 1;
			return true;
		}
		catch (Exception excp) {
			return false;
		}
	}
	
	// convert the list to string format
	public String toString() {
		String str_list = new String();
		for (int i = 0; i < this.indices.size(); i++) {
			String convert_data = String.valueOf(this.indices.get(i).data);
			if (i == 0) {
				str_list += convert_data;
			}
			else {
				str_list += (" " + convert_data);
			}
		}
		return str_list;
	}
	
	public int size() {
		return this.size;
	}
	
	public E get(int index){
		try{
			return this.indices.get(index).data;
		}
		// if the index is out of current size or index < 0, throw exception
		catch (Exception excp) {
			return null;
		}
	}
	
	public E getHead() {
		try{
			return this.head.data;
		}
		catch (Exception excp) {
			return null;
		}
	}
	
	public E getLast() {
		try{
			return this.tail.data;
		}
		catch (Exception excp) {
			return null;
		}
	}
	
	// removes and returns the element at the head
	public E remove() {
		try {
			Node<E> removed_head = this.head;
			if (this.size == 0) {
				return null;
			}
			// if there is only one node, reset the head and tail pointers
			else if (this.size == 1) {
				this.head = null;
				this.tail = null;
			}
			// otherwise only need to reset head pointer
			else {
				this.head = this.head.next;
			}	
			this.indices.remove(0);
			this.size -= 1;
			return removed_head.data;
					
		}
		catch (Exception excp) {
			return null;
		}
	}
	
	// removes and returns the element at the tail
	public E removeLast() {
		try {
			Node<E> removed_head = this.tail;
			if (this.size == 0) {
				return null;
			}
			// if there is only one node, reset the head and tail pointers
			else if (this.size == 1) {
				this.head = null;
				this.tail = null;
			}
			// otherwise only need to reset head pointer
			else {
				this.tail = this.tail.prev;
			}	
			this.indices.remove(this.indices.size() - 1);
			this.size -= 1;
			return removed_head.data;
					
		}
		catch (Exception excp) {
			return null;
		}
	}
	
	// removes and returns the element at the index
	public E removeAt(int index){
		try {
			if (index == 0) {
				return this.remove();
			}
			else if (index == this.size - 1) {
				return this.removeLast();
			}
			else {
				Node<E> removed_node = this.indices.get(index);
				Node<E> prev_node = this.indices.get(index - 1);
				Node<E> next_node = this.indices.get(index + 1);
				prev_node.next = next_node;
				next_node.prev = prev_node;
				this.indices.remove(index);
				this.size -= 1;
				return removed_node.data;
			}
		}
		// if the index is out of current size or index < 0, throw exception
		catch (Exception excp) {
			return null;
		}
	}
	
	// removes the first occurrence of element in the list and returns true.
	// Return false if element was not in the list.
	public boolean remove(E elem) {
		try {
			for (int i = 0; i < this.indices.size(); i++) {
				if (this.indices.get(i).data == elem) {
					this.removeAt(i);
					return true;
				}
			}
			return false;
		}
		catch (Exception excp) {
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		IDLList<Integer> list_a = new IDLList<Integer>();
		list_a.append(5);
		System.out.println(list_a.toString());
		list_a.append(9);
		System.out.println(list_a.toString());
		list_a.add(2);
		System.out.println(list_a.toString());
		list_a.append(8);
		System.out.println(list_a.toString());
		list_a.add(2, 32);
		System.out.println(list_a.toString());
		list_a.append(32);
		System.out.println(list_a.toString());
		
//		System.out.println(list_a.size());
//		System.out.println(list_a.get(2));
//		System.out.println(list_a.getHead());
//		System.out.println(list_a.getLast());
		
//		System.out.println(list_a.removeLast());
//		System.out.println(list_a.toString());
//		System.out.println(list_a.remove());
//		System.out.println(list_a.toString());
		
		System.out.println(list_a.remove(32));
		System.out.println(list_a.toString());
		System.out.println(list_a.remove(32));
		System.out.println(list_a.toString());
	}
}
