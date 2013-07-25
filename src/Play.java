import java.util.*;

public class Play {

	Board playBoard;
	
	boolean win = false;
	boolean lose = false;
	boolean gameOver = false;
	

	public static void main(String[] args) {
		
		

		Play game = new Play("easy");
		game.playBoard.initializeBoard();

		Scanner scan = new Scanner(System.in);

		System.out.println("Please place your x and y coordinates seperated by a space");

		int x = scan.nextInt();
		int y = scan.nextInt();

		if(game.playBoard.isValid(x-1, y-1)){ //and being opened
			game.playBoard.setStartXandY(x-1,y-1);
			game.playBoard.openBox(x-1, y-1);
		}

		while(!game.gameOver()){

			System.out.println("Please place your x and y coordinates seperated by a space");
			System.out.println(game.playBoard.getOpenedBoxCount());
			if(game.playBoard.isValid(x-1, y-1)){
			
			x = scan.nextInt();
			y = scan.nextInt();

			game.playBoard.openBox(x-1,y-1); //left click -- right click will be markFlagged
			}
		}
		
		System.out.println("GAME OVER!");
		
		if(game.gameOverWin())
			System.out.print("YOU WIN!");
		
		else
			System.out.print("YOU LOSE!");


	}

	public Play(String a){

				if(a.contains("easy")){
					playBoard = new Board(8,8);
				}
					
				if(a.contains("medium")){
					playBoard = new Board(16,16);
			}
					
				if(a.contains("hard")){
					playBoard = new Board(30,16);
				}

	}
	
	public boolean gameOver(){
		
		if(gameOverWin() || gameOverLose())
			
			gameOver = true;
		
		return gameOver;
	}

	public boolean gameOverWin(){
 //try minus one (bug)
		if((playBoard.getOpenedBoxCount() == (playBoard.getTotalBoxes()) - (playBoard.getTotalBombs())))
			win = true;
		
		return win;
	}
	
	public boolean gameOverLose(){
		
		if(playBoard.getTouchedBomb())
			lose = true;
			
		return lose;
	}

}


