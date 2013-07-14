
public class CheckList {

	Node head;
	Node tail;

	public void enque(int x, int y){

		Node entering = new Node(x,y);

		if(tail != null)
			tail.setNext(entering);

		tail = entering;

		if(head == null)
			head = entering;
	}

	public int[] deque(){

		int[] array = new int[2];
		
		int tempX = head.getXCord();
		array[0] = tempX;
		int tempY = head.getYCord();
		array[1] = tempY;
		
		head = head.getNext();

		return array;
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