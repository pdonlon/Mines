

public class CheckList {

	Node head;
	Node tail;

	public Node getHead(){

		return head;
	}

	public boolean alreadyInCheckList(int x, int y){

		boolean already = false;
		Node pointer = head;

		while(pointer.getNext()!=null){

			if(pointer.getXCord()==x && pointer.getYCord()==y)
				already = true;
			pointer = pointer.getNext();

		}
		return already;
	}

	public boolean remove(int x, int y){
		
		boolean found = false;

		Node pointer = head;
		Node tempPointer;

		if(head.getXCord()==x && head.getYCord()==y)
			head = head.next;

		else{
			
			while(pointer!=null){
				
				tempPointer = pointer.getNext();
				
				if(tempPointer!=null && tempPointer.getXCord()==x && tempPointer.getYCord()==y){
					pointer.setNext(tempPointer.getNext());
					found = true;
					break;
				}
				
				pointer = pointer.next;
			}
			
		}
		return found;
	}

	public void enque(int x, int y){

		Node entering = new Node(x,y);

		if(tail != null)
			tail.setNext(entering);

		tail = entering;

		if(head == null)
			head = entering;
	}

	public int[] getValues(){

		int[] array = new int [2];

		int tempX = head.getXCord();
		array[0] = tempX;
		int tempY = head.getYCord();
		array[1] = tempY;

		return array;

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

	public void deque(){ //not a classic deque (doesn't return anything)

		head = head.getNext();

	}
	
	public void empty(){
		
		head = null;
	}
	
}

class Node{

	Node next;
	int xCord;
	int yCord;

	public Node(int x,int y){
		xCord = x;
		yCord = y;

	}

	public int getXCord(){
		return xCord;
	}

	public int getYCord(){
		return yCord;
	}

	public void setNext(Node a){
		next = a;
	}
	public Node getNext(){
		return next;
	}

}
