
public class FullList {

	
	Node head;
	Node tail;
	
	public boolean alreadyInFullList(int x, int y){
		
		boolean already = false;
		Node pointer = head;
		
		while(pointer.getNext()!=null){
			
			if(pointer.getXCord()==x && pointer.getYCord()==y)
				already = true;
			pointer = pointer.getNext();
			
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
	
	public String toString()
	{
		String s = "";

		Node ptr = head;
		while (ptr != null)
		{
			s += ptr.xCord+""+ptr.yCord+" ";
			ptr = ptr.getNext();
		}

		return s;
	}
	
	public Node getHead(){
		
		return head;
	}
	
}
