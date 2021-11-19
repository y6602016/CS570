import java.util.*;

public class Treap<E extends Comparable <E>> {
	private static class Node<E extends Comparable <E>> {
		public E data;
		public int priority;
		public Node<E> left;
		public Node<E> right;
		
		// constructor
		public Node(E data, int priority) throws Exception {
			if (data == null) {
				throw new IllegalArgumentException("Data can't be null");
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}
		
		
		public Node<E> rotateRight(){
			// if the node has no left child, it can't rotate right 
			if (this.left == null) {
				return null;
			}
			
			// reserve the left child node
			Node<E> leftChild = this.left;
			
			// reserve the left child node's right child
			// no need to check it's null or not, since even it's null
			// we still can set it as the left child of this node
			Node<E> leftChildRight = leftChild.right;
			
			// set this node as the left child's right child 
			leftChild.right = this;
			
			// set left child's right child as this node's left child 
			this.left = leftChildRight;
			
			// return left child node, it's the new root of the corresponding node's position
			return leftChild;
		}
		
		public Node<E> rotateLeft(){
			// if the node has no right child, it can't rotate left
			if (this.right == null) {
				return null;
			}
			
			// reserve the right child node
			Node<E> rightChild = this.right;
			
			// reserve the right child node's left child
			// no need to check it's null or not, since even it's null
			// we still can set it as the right child of this node
			Node<E> rightChildLeft = rightChild.left;
			
			// set this node as the right child's left child 
			rightChild.left = this;
			
			// set right child's left child as this node's right child 
			this.right = rightChildLeft;
			
			// return right child node, it's the new root of the corresponding node's position
			return rightChild;			
		}
		
		// return string of key and priority
		public String toString() {
			return "(key=" + String.valueOf(this.data) + ", priority=" + String.valueOf(this.priority) + ")";
		}
	}
	
	private Random priorityGenerator;
	private Node<E> root;
	
	// constructor creates an empty Treap with random generator
	public Treap() throws Exception{
		priorityGenerator = new Random();
		
	}
	
	// constructor creates an empty Treap with random generator using a single long seed
	public Treap(long seed) throws Exception{
		priorityGenerator = new Random(seed);
	}
	
	public boolean add(E key) {
		// randomGenerator generate a random integer
		int priority = this.priorityGenerator.nextInt();
		// call add helper function, if it returns true, return true
		if (this.add(key, priority)) {
			return true;
		}
		// otherwise, return false
		else {return false;}
	}

	public boolean add(E key, int priority) {
		// if the key already in the Treap, return false
		if (this.find(key)) {
			return false;
		}
		try {
			// create a new node
			Node<E> node = new Node<E>(key, priority);
		
			// if the Treap is empty, just add it
			if (this.root == null) {
				this.root = node;
				return true;
			}
			
			// insert node as a leaf node at appropriate position
			// create a stack and traverse from the root to the correct position
			Stack<Node<E>> stack = new Stack<Node<E>>();
			Node<E> curr = this.root;
			while (curr != null) {
				// put curr to stack
				stack.push(curr);
				
				// compare curr's key and new key
				// node.data < curr.data
				if (curr.data.compareTo(key) > 0) {
					// if curr has no left child, put node as curr's left child
					if (curr.left == null) {
						curr.left = node;
						curr = null;
					}
					else {
						curr = curr.left;
					}
				}
				// node.data > curr.data
				else {
					// if curr has no right child, put node as curr's right child
					if (curr.right == null) {
						curr.right = node;
						curr = null;
					}
					else {
						curr = curr.right;
					}
				}
			}
			
			// now the node has been inserted as a leaf node
			// it's time to call reheap function
			this.reheap(node, stack);
			return true;
		}
		catch (Exception excp) {
			return false;
		}
	}
	
	private void reheap(Node<E> bubble_node, Stack<Node<E>> stack) {
		// start from the stack top node, rotate the node one by one
		while (!stack.empty()) {
			// pop out the node, and peek the parent node
			Node<E> node = stack.pop();
			Node<E> parent = null;
			if (!stack.empty()) {
				parent = stack.peek();
			}
			
			// check that node is parent's left or right child
			// child_flag = 0 means node is parent's left child, otherwise 1
			int child_flag = 0;
			if (parent != null) {
				if (parent.right == node) {
					child_flag = 1;
				}
			}
			
			// compare it's priority with the bubble_node
			// if bubble_node's priority > node's priority, bubble_node needs to be bubble up
			if (node.priority < bubble_node.priority) {
				Node<E> new_root;
				// if bubble_node is node's left child, rotate right
				if (node.left == bubble_node) {
					// new_root is actually bubble_node
					new_root = node.rotateRight();
				}
				// if bubble_node is node's right child, rotate left
				else {
					// new_root is actually bubble_node
					new_root = node.rotateLeft();
				}
				// set new root as parent's child
				if (parent != null) {
					if (child_flag == 0) {
						parent.left = new_root;
					}
					else {
						parent.right = new_root;
					}
				}
				else {
					this.root = new_root;
				}
			}
			else { // no need to bubble up
				break;
			}
		}
	}
	
	public boolean delete(E key) {
		// if the key not in the Treap, return false
		if (!this.find(key)) {
			return false;
		}
		
		Node<E> curr = this.root;
		Node<E> parent = null;
		int child_flag = 0; // 0 = left child, 1 = right child
		// find the node whose child is key node
		while (curr != null) {
			if (curr.data.compareTo(key) == 0) { // curr.data = key, find it!
				break;
			} 
			else if (curr.data.compareTo(key) > 0) { // key < curr.data, then explore left child
				parent = curr;
				child_flag = 0;
				curr = curr.left;
			}
			else { // key > curr.data, then explore right child
				parent = curr;
				child_flag = 1;
				curr = curr.right;
			}
		}
		
		// now the curr node is the node to be deleted and parent is it's parent node
		// rotate the curr node down to the leaf level
		while (curr.left != null || curr.right != null) {
			Node<E> new_root;
			// if curr has both children, check which is larger
			if (curr.left != null && curr.right != null) {
				// if left child's priority > right child's priority, rotate right
				if (curr.left.priority > curr.right.priority) {
					new_root = curr.rotateRight();
				}// if left child's priority < right child's priority, rotate left
				else {
					new_root = curr.rotateLeft();
				}
			}
			// if curr only has left child, rotate right
			else if (curr.left != null) {
				new_root = curr.rotateRight();
			}
			// if curr only has right child, rotate left
			else {
				new_root = curr.rotateLeft();
			}
			
			// reconnect parent to new_root
			if (parent != null) {
				if (child_flag == 0) {
					parent.left = new_root;
				}
				else {
					parent.right = new_root;
				}
			}
			// if parent == null. means curr is root, set root as new_root
			else {
				this.root = new_root;
			}
			
			// update parent and child flag
			parent = new_root;
			if (parent.left == curr) {
				child_flag = 0;
			}
			else {
				child_flag = 1;
			}
		}
		
		// now curr is a leaf node, disconnect it from parent
		if (parent != null) {
			if (parent.left == curr) {
				parent.left = null;
				curr = null;
			}
			else {
				parent.right = null;
				curr = null;
			}
		}
		else {
			this.root = null;
			curr = null;
		}
		return true;
	}
	
	
	// convert the Treap to String
	public String toString() {
		Node<E> curr = this.root;
		// use a stack to do preorder traverse
		Stack<Node<E>> stack = new Stack<Node<E>>();
		// use a stack to record the indentation
		Stack<Integer> indent_stack = new Stack<Integer>();
		int indent = 0;
		
		// initialize both stacks
		stack.push(curr);
		indent_stack.push(indent);
		String output = "";
		
		while (!stack.empty()) {
			// pop out the node and the indentation
			curr = stack.pop();
			indent = indent_stack.pop();
			
			// if node exists, convert to string
			if (curr != null) {
				// process indentation
				for (int j = 0; j < indent; j++) {
					output += "  ";
				}
				output += curr.toString();
				output += "\n";
				
				// increase indent for children
				indent++;
				
				// push right child first since it will popped out later
				stack.push(curr.right);
				indent_stack.push(indent);
				
				stack.push(curr.left);
				indent_stack.push(indent);
			}
			else {
				// if node not exist, print null
				for (int j = 0; j < indent; j++) {
					output += "  ";
				}
				output += "null\n";
			}
		}
		return output;
	}
	
	
	// search for the key from the given root
	private boolean find(Node<E> root, E key) {
		// if the given root is null, return false
		if (root == null) {
			return false;
		}
		
		Node<E> curr = root;
		while (curr != null) {
			if (curr.data.compareTo(key) == 0) { // curr.data = key, find it!
				return true;
			} 
			else if (curr.data.compareTo(key) > 0) { // key < curr.data, then explore left child
				curr = curr.left;
			}
			else { // key > curr.data, then explore right child
				curr = curr.right;
			}
		}
		return false;		
	}
	
	// search for the key in the whole tree
	public boolean find(E key) {
		// if the Treap is empty, return false
		if (this.root == null) {
			return false;
		}
		
		Node<E> curr = this.root;
		while (curr != null) {
			if (curr.data.compareTo(key) == 0) { // curr.data = key, find it!
				return true;
			} 
			else if (curr.data.compareTo(key) > 0) { // key < curr.data, then explore left child
				curr = curr.left;
			}
			else { // key > curr.data, then explore right child
				curr = curr.right;
			}
		}
		return false;	
	}

	public static void main(String[] args) {
		try {
			Treap<Character> testTree = new Treap<Character>();
			testTree.add('c' ,19); 
			testTree.add('b' ,31);
			testTree.add('d' ,70);
			testTree.add('a' ,84);
			testTree.add('e' ,12); 
			testTree.add('o' ,82);
			testTree.add('m' ,26);
			testTree.add('h', 83);
			testTree.delete('a');
			testTree.delete('h');
			System.out.print(testTree);
		}
		catch (Exception excp) {
			System.out.print(excp.getMessage());
		} 
	}
}
