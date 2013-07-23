import java.util.*;

public class Play {

	static boolean gameOver = false; //when gameOver show all flags/open all bombs

	public static void main(String[] args) {

		Board ricksters = new Board(16,16);
		ricksters.initializeBoard();

		Scanner scan = new Scanner(System.in);

		System.out.println("Please place your x and y coordinates seperated by a space");

		int x = scan.nextInt();
		int y = scan.nextInt();

		if(ricksters.isValid(x-1, y-1)){
			ricksters.setStartXandY(x-1,y-1);
			ricksters.openBox(x-1, y-1);
		}

		while(!gameOver){

			System.out.println("Please place your x and y coordinates seperated by a space");

			if(ricksters.isValid(x-1, y-1)){
			
			x = scan.nextInt();
			y = scan.nextInt();

			ricksters.openBox(x-1,y-1); //left click -- right click will be markFlagged
			}
		}


	}

	public Play(String a){
		//
		//		if(a.contains("easy")){
		//			new Board(8,8);
		//		}
		//			
		//		if(a.contains("medium")){
		//			new Board(16,16);
		//	}
		//			
		//		if(a.contains("hard")){
		//			new Board(30,16);
		//		}

	}

	public void setGameOver(boolean over){

		gameOver = over;
	}

	public boolean getGameOver(){

		return gameOver;
	}

}


