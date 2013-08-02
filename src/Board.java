
public class Board {

	//		  	size	mines	opened
	//	beg 	8x8 	10		54
	//	med 	16x16 	40		216
	//	exp 	30x16 	99		381

	Mine board[][];
	int width;
	int height;
	
	int flagCount = 10;
	int openedBoxCount; 
	
	int totalBombs;
	int totalBoxes;
	
	int startX;
	int startY;
	
	boolean touchedBomb = false;


	public Mine[][] getBoard(){

		return board;
	}

	public Board(int width, int height){

		this.width = width;
		this.height = height;
		
		board = new Mine[width][height];

	}
	
	public int getWidth(){
		
		return width;
	}
	
	public int getHeight(){
		
		return height;
	}
	
	public int getOpenedBoxCount(){
		
		return openedBoxCount;
	}
	
	public int getTotalBombs(){
		
		return totalBombs;
	}
	
	public int getTotalBoxes(){
		
		return totalBoxes;
	}
	
	public boolean getTouchedBomb(){
		
		return touchedBomb;
	}

	public void initializeBoard(){
		
		setTotalBoxes();

		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){

				board[x][y] = new Mine (false, 0, false, false, false); 

			}
		}
		printBoard();

	}
	
	public void setTotalBoxes(){
		
		totalBoxes = ((height)*(width));
		
	}

	public void setStartXandY(int x, int y){

		startX = x;
		startY = y;

		placeBombs();
		placeBombsSurrounding();

	}

	public void placeBombs(){

		if(width == 8)
			totalBombs = 10;
		else if(width == 16 && height == 16)
			totalBombs = 40;
		else
			totalBombs = 99;

		int count = totalBombs;

		while(count > 0){
			int rand1 = (int) (Math.random() * width); 
			int rand2 = (int) (Math.random() * height); //to get the y (rectangle ones)

			if(!board[rand1][rand2].isBomb() && notSurroundingStart(rand1,rand2)){
				board[rand1][rand2].setBomb(true);
				count--;
			}
		}
	}

	public void placeBombsSurrounding(){

		//placed around first click 
		//(make first click a 0)
		//TODO

		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){

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

		int tempX = x;
		int tempY = y;

		CheckList check = new CheckList();
		check.enque(x,y);

		FullList full = new FullList();
		full.add(x,y);

		while (check.getHead()!=null){

			check.deque();

			for(int i=-1; i<2; i++){			
				for(int j=-1; j<2; j++){

					if((isValid(tempX+i,tempY+j))&& 
							(!full.alreadyInFullList(tempX+i, tempY+j))&&
							(!board[tempX+i][tempY+j].isOpened())){

						board[tempX+i][tempY+j].setOpened(true);
						openedBoxCount++;
						
						full.add(tempX+i, tempY+j); //add coordinates to not repeat

						if((board[tempX+i][tempY+j].getBombsSurrounding()==0)){ 

							check.enque(tempX+i,tempY+j);

						}
					}
				}

			}
			if(check.getHead()!=null){

				tempX = check.getValues()[0];
				tempY = check.getValues()[1];
			}
		}
	}



	public void markFlagged(int x, int y){

		if(flagCount>0){

			board[x][y].setFlagged(true);

			flagCount--;

			printBoard();
		}
	}

	public boolean notSurroundingStart(int x, int y){

		boolean notSurrounded = true;

		for(int i=-1; i<2; i++){
			for(int j=-1; j<2; j++){

				if(x+j==startX && y+i==startY){

					notSurrounded = false;
					break;
				}

			}
			if(!notSurrounded)
				break;
		}

		return notSurrounded;
	}

	public void removeFlag(int x, int y){

		if(flagCount<11){

			board[x][y].setFlagged(false);

			flagCount++;

			printBoard();
		}
	}

	public void openBox(int x, int y){

		//if not already opened and valid (check before calling openBox)

		if(board[x][y].isFlagged())
			removeFlag(x,y);

		else{
			
				board[x][y].setOpened(true);

				if(board[x][y].isBomb()){

					touchedBomb = true;
					
					for(int i=0; i<height; i++){
						for(int k=0; k<width; k++){

							if(board[k][i].isBomb()&&!board[k][i].isFlagged())
								board[k][i].setOpened(true);

							else if(!board[k][i].isBomb()&& board[k][i].isFlagged())
								board[k][i].setWrong(true);
						}
					}
					printBoard();
				}

				else if(board[x][y].getBombsSurrounding()==0){
					openedBoxCount++;
					openZeros(x, y);
					printBoard();
				}
				else{
					openedBoxCount++;
					printBoard();
				}

		}
	}




	public void printBoard(){

		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){



				if(!board[x][y].isOpened()){

					if(board[x][y].isWrong())
						System.out.print("X ");

					else if(board[x][y].isFlagged())
						System.out.print("F ");

					else
						System.out.print(". ");
				}

				else{

					if(board[x][y].isBomb()){
						System.out.print("B ");
					}


					else if(board[x][y].getBombsSurrounding()==0){
						System.out.print("0 ");
					}

					else
						System.out.print(board[x][y].getBombsSurrounding()+" ");
				}

			}
			System.out.println();
		}
		System.out.println();
	}

}
