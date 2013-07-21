
public class FullList {

	
	Node head;
	Node tail;
	
	public boolean alreadyInList(int x, int y){
		
		boolean already = false;
		
		while(head.getNext()!=null){
			
			if(head.getXCord()==x && head.getYCord()==y)
				already = true;
			head = head.getNext();
			
		}
		return already;
	}
	
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
