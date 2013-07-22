import java.util.*;

public class Play {

	boolean gameOver = false; //when gameOver show all flags/open all bombs

	public static void main(String[] args) {
		
		Board ricksters = new Board(16,16);
		ricksters.initializeBoard();
		
		ricksters.markFlagged(7, 7);
		ricksters.openBox(7, 7);
		ricksters.openBox(7, 7);
		
//		for(int y=0; y<8; y++){
//			for(int x=0; x<8; x++){
//				ricksters.markFlagged(x,y);
//			}
//		}
//		ricksters.openBox(3, 3);
//		for(int y=0; y<8; y++){
//			for(int x=0; x<8; x++){
//				ricksters.markFlagged(x,y);
//			}
//		}
		
		
		//if scans in a string such as easy it will create a board with 8,8

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


