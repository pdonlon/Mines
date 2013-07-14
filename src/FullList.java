
public class FullList {

	
	Node head;
	Node tail;
	
	public void add(int x, int y){
		
		Node entering = new Node(x,y);
		
		if(tail!= null)
			tail.setNext(entering);
		
		tail = entering;
		
		if(head == null)
			head = entering;
		
	}
	
	public Node getHead(){
		
		return head;
	}
	
}
