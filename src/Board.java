
public class Board {

	//		  	size	mines	opened
	//	beg 	8x8 	10		54
	//	med 	16x16 	40		216
	//	exp 	30x16 	99		381
	
	Mine board[][];
	int width;
	int height;
	
	public Mine[][] getBoard(){
		
		return board;
	}
	
	public Board(int a, int b){
		
		board = new Mine[a][b];
		
	}

	public void initializeBoard(){

		for(int y=0; y<board[1].length; y++){
			for(int x=0; x<board.length; x++){

				board[x][y] = new Mine (false, 0, false, false, false); 

			}
		}
		placeBombs();
		placeBombsSurrounding();
		printBoard();

	}

	public void placeBombs(){
		int bombCount;

		if(board.length == 8)
			bombCount = 10;
		else if(board.length == 16 && board[1].length == 16)
			bombCount = 40;
		else
			bombCount = 99;

		int count = bombCount;

		while(count > 0){
			int rand1 = (int) (Math.random() * board.length); 
			int rand2 = (int) (Math.random() * board[1].length); //to get the y (rectangle ones)

			if(!board[rand1][rand2].isBomb())
				board[rand1][rand2].setBomb(true);
			count--;
		}
	}

	public void placeBombsSurrounding(){

		for(int y=0; y<board[1].length; y++){
			for(int x=0; x<board.length; x++){

				if(board[x][y].isBomb()){

					for(int j=-1; j<2; j++){			
						for(int i=-1; i<2; i++){

							if(isValid(x+j,y+i)&& !board[x+j][y+i].isBomb())
								board[x+j][y+i].addOneBombSurrounding();
						}
					}
				}
			}
		}
	}

	public boolean isValid(int x, int y){

		boolean valid = false;

		try{
			if(board[x][y]!=null)
				valid = true;
		}
		catch(Exception e){

		}
		return valid;
	}
	
	public void openZeros(int x, int y){

		for(int i=-1; i<2; i++){			
			for(int j=-1; j<2; j++){

				if(isValid(x+i,y+j)){
					board[x+i][y+j].setOpened(true);
					
					if(board[x+i][y+j].getBombsSurrounding()==0){
						
						CheckList check = new CheckList();
						check.enque(x,y);
						
						FullList full = new FullList();
						full.add(x,y);

					}
				}


			}
		}

	}
	
public void openBox(int x, int y){
		
		while(isValid(x,y))
		
		board[x][y].setOpened(true);
		
		if(board[x][y].isBomb()){
			
			for(int i=0; i<board[1].length; i++){
				for(int k=0; k<board.length; k++){
					
					if(board[k][i].isBomb()&&!board[k][i].isFlagged())
						board[k][i].setOpened(true);
					
					if(board[k][i].isBomb()&&!board[k][i].isFlagged())
						board[k][i].setWrong(true);
				}
			}
			printBoard();
			System.out.print("GAME OVER! YOU LOSE!");
		}
			
		if(board[x][y].getBombsSurrounding()==0)
			openZeros(x, y);
		
		}
			
		

	public void printBoard(){

		for(int y=0; y<board[1].length; y++){
			for(int x=0; x<board.length; x++){
				
				//if(board[x][y].isOpened() == true){

				if(board[x][y].isWrong())
					System.out.print("X ");
				else if(board[x][y].isFlagged())
					System.out.print("F ");
				
				else if(board[x][y].isBomb()){
					System.out.print("B ");
				}

				
				else if(board[x][y].getBombsSurrounding()==0){
					System.out.print("0 ");
				}
				
				else
					System.out.print(board[x][y].getBombsSurrounding()+" ");
			}

			//}
			System.out.println();
		}

	}

}
