// HW3, CWID: 10675677, Name: Qi-Rui Hong

public class IDLListTest {
	public static void main(String[] args) {
		try {
			IDLList<Integer> list_a = new IDLList<Integer>();
			list_a.append(1);
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
			list_a.append(32);
			System.out.println(list_a.toString());
			
			System.out.println(list_a.size());
			System.out.println(list_a.get(2));
			System.out.println(list_a.getHead());
			System.out.println(list_a.getLast());
			
			System.out.println(list_a.removeLast());
			System.out.println(list_a.toString());
			System.out.println(list_a.removeAt(1));
			System.out.println(list_a.toString());
			
			System.out.println(list_a.remove(32));
			System.out.println(list_a.toString());
			System.out.println(list_a.remove(32));
			System.out.println(list_a.toString());
			
		}
		catch (Exception excp) {
			System.out.print(excp.getMessage());
		} 

	}
}
