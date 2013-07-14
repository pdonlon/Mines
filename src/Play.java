import java.util.*;

public class Play {

	static boolean gameOver = false; //when gameOver show all bombs/flags

	public static void main(String[] args) {
		
		Board.initializeBoard(Board.getBegBoard());
		//Board.initializeBoard(Board.getMedBoard());
		//Board.initializeBoard(Board.getExpBoard());
		
//		while(getGameOver()!=true){
//			scanner stuff
//		}
	
	}
	
	public static void setGameOver(boolean over){
		
		gameOver = over;
	}
	
	public static boolean getGameOver(){
		
		return gameOver;
	}
	
	public static void openBox(Mine[][] array, int x, int y){
		
		while(Board.isValid(array,x,y)==false)
			System.out.println("Please pick coordinates inside the "+array.length+" by "+array[1].length+" dementions");
		
		array[x][y].setOpened(true);
		if(array[x][y].isBomb()==true)
			setGameOver(true);
		if(array[x][y].getBombsSurrounding()==0)
			Board.openZeros(array, x, y);
		else{
			
		}
			
	}

}
