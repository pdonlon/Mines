import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.color.*;

import javax.swing.JPanel;

public class Board {

	//		  	size	mines	opened
	//	beg 	8x8 	10		54
	//	med 	16x16 	40		216
	//	exp 	30x16 	99		381
	
	CheckList pressed;
	CheckList bombs;
	Mine board[][];

	int width; 
	int height;

	int flagCount;
	int openedBoxCount; 
	int unsafeBombCount;

	int flagLimit;
	int totalBombs;
	int totalBoxes;

	int startX;
	int startY;

	boolean win = false;
	boolean lose = false;

	boolean firstTurn = true;
	boolean touchedBomb = false;
	boolean speedTrick = false;


	public Mine[][] getBoard(){

		return board;
	}

	public Board(int width, int height){

		this.width = width;
		this.height = height;

		board = new Mine[width][height];

		totalBoxes = width*height;

		if(width == 8)
			totalBombs = 10;

		else if(width == 16)
			totalBombs = 40;

		else
			totalBombs = 99;

		flagCount = totalBombs;
		unsafeBombCount = totalBombs;
		flagLimit=flagCount;

		bombs = new CheckList();
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

	public int getFlagCount(){ //Let's play access flags

		return flagCount;
	}

	public boolean getWin(){

		return win;
	}

	public boolean getLose(){

		return lose;
	}

	public boolean getFirstTurn(){

		return firstTurn;
	}

	public int getStartX(){

		return startX;
	}

	public int getStartY(){

		return startY;
	}
	
	public boolean alreadyThere(int x, int y){
		
		return pressed.alreadyHead(x, y);
		
	}
	
	public void replace(int x, int y){
		
		pressed.replaceHead(x, y);
		
	}
	
	public void add(int x, int y){
		
		pressed = new CheckList();
			pressed.enque(x, y);
		
	}
	
	public void press(int x, int y){
		
		board[x][y].setPressed(true);
	}
	
	public void resetPressed(){
		
		if(pressed.getHead()!=null)
			board[pressed.getHead().getXCord()][pressed.getHead().getYCord()].setPressed(false);
		
	}

	public int getUnsafeBombCount(){

		return unsafeBombCount;
	}

	public void wipeBoard(){

		for(int y=0; y<height; y++ ){
			for(int x=0; x<width; x++){

				board[x][y] = new Mine (false, 0, false, false, false, false, false);

			}
		}

	}

	public void startup(){

		bombs.empty();

		openedBoxCount=0; 

		flagCount = totalBombs;
		unsafeBombCount = totalBombs;
		flagLimit=flagCount;

		win = false;
		lose = false;

		firstTurn = true;
		touchedBomb = false;

	}

	public boolean flagged(int x, int y){

		boolean flag = false;
		if(board[x][y].isFlagged())
			flag = true;

		return flag;

	}

	public void open(int x, int y){ //Play can't access Mines so it is a double method

		openBox(x,y);

	}

	public boolean bomb(int x, int y){ //Play can't access Mines so it is a double method

		return board[x][y].isBomb();
	}

	public void initializeBoard(){

		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){

				board[x][y] = new Mine (false, 0, false, false, false, false, false); 

			}
		}


	}

	public void setStartXandY(int x, int y){

		startX = x;
		startY = y;

		placeBombs();
		placeBombsSurrounding();

	}

	public void placeBombs(){

		//bombs = new CheckList();

		int count = totalBombs;

		while(count > 0){
			int rand1 = (int) (Math.random() * width); 
			int rand2 = (int) (Math.random() * height); //to get the y (rectangle ones)

			if(!board[rand1][rand2].isBomb() && notSurroundingStart(rand1,rand2)){
				board[rand1][rand2].setBomb(true);
				bombs.enque(rand1,rand2);
				count--;
			}
		}
	}

	public void placeBombsSurrounding(){

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

	public boolean unopenedAround(int x, int y){

		boolean unopened = false;

		for(int i=-1; i<2; i++){			
			for(int j=-1; j<2; j++){

				if(isValid(x+j,y+i) && !board[x+j][y+i].isOpened())
					unopened = true;

			}
		}

		return unopened;
	}

	public void fastClick(int x, int y){

		for(int i=-1; i<2; i++){			
			for(int j=-1; j<2; j++){

				if(isValid(x+j,y+i) && !board[x+j][y+i].isOpened() && !board[x+j][y+i].isFlagged()){
					
					if(board[x+j][y+i].isBomb())
						bombs.remove(x+j,y+i);
					
				openBox(x+j,y+i);	
				}
			}
		}
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

					if(isValid(tempX+i,tempY+j)&& 
							!full.alreadyInFullList(tempX+i, tempY+j)&&
							!board[tempX+i][tempY+j].isOpened()&&
							!board[tempX+i][tempY+j].isFlagged()){

//						if(board[tempX+i][tempY+j].isFlagged())
//							removeFlag(tempX+i,tempY+j);

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

		if(board[x][y].isFlagged()){

			board[x][y].setFlagged(false);

			flagCount++;

		}

		else if(flagCount>0 && !board[x][y].isOpened()){

			board[x][y].setFlagged(true);

			flagCount--;


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

		if(flagCount<flagLimit){

			board[x][y].setFlagged(false);

			flagCount++;


		}
	}

	public void openBomb(){

		if(board[bombs.getValues()[0]][bombs.getValues()[1]].isBomb())
		board[bombs.getValues()[0]][bombs.getValues()[1]].setOpened(true);
		
//		else
//			board[bombs.getValues()[0]][bombs.getValues()[1]].setWrong(true);
		
		bombs.deque();


	}


	public void openBox(int x, int y){

		if(board[x][y].isFlagged())
			removeFlag(x,y);

		else if(firstTurn){

			setStartXandY(x,y);

			firstTurn = false;
			openBox(x,y);

		}
		else{

			if(!board[x][y].isOpened()){

				board[x][y].setOpened(true);

				if(board[x][y].isBomb()){

					touchedBomb = true;
					lose = true;

					bombs.remove(x,y); //so it won't print twice
					bombs.enque(x, y); //first to print

					endOfGame();


				}

				else if(board[x][y].getBombsSurrounding()==0){
					openedBoxCount++;
					openZeros(x, y);

				}
				else
					openedBoxCount++;
			}
		}
		if(openedBoxCount == totalBoxes - totalBombs)
			win = true;
	}

	public void endOfGame(){

		for(int i=0; i<height; i++){
			for(int k=0; k<width; k++){

				

				if(board[k][i].isFlagged()&&board[k][i].isBomb()){
					bombs.remove(k,i);
//					unsafeBombCount--;
				}

				else if(board[k][i].isFlagged()&&!board[k][i].isBomb()){
					board[k][i].setWrong(true);
				}

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


	public void paintBoard(Graphics g){

		if(speedTrick)
			g.drawString("", 1, 1);
		else
			speedTrick = true;

		int[] xArray = new int[3];
		int[] yArray = new int[3];
				

		int ySpacing = 0;

		for(int y=0; y<height; y++){

			int xSpacing = 0;

			for(int x=0; x<width; x++){

				g.setColor(board[x][y].determineColor());

				if(!board[x][y].isOpened()){

					if(board[x][y].isWrong()){ //draws X					
						g.drawLine(xSpacing+5, ySpacing+5, xSpacing+23, ySpacing+23); //top left/bottom right
						g.drawLine(xSpacing+23, ySpacing+5, xSpacing+5, ySpacing+23);//top right/bottom left
						g.drawRect(xSpacing+1, ySpacing+1, 26, 26);
					}

					else if(board[x][y].isFlagged()){
						
						int[] xLightTri ={xSpacing+1,xSpacing+1,xSpacing+28};
						int[] yLightTri ={ySpacing+1,ySpacing+28,ySpacing+1};
						
						int[] xDarkTri ={xSpacing+28,xSpacing+28,xSpacing+1};
						int[] yDarkTri ={ySpacing+28,ySpacing+1,ySpacing+28};
						
						xArray[0] = xSpacing+10; //top left
						yArray[0] = ySpacing+8;

						xArray[1] = xSpacing+20; //top mid
						yArray[1] = ySpacing+13;

						xArray[2] = xSpacing+10; //bottom right
						yArray[2] = ySpacing+16;
						
						g.setColor(Color.LIGHT_GRAY);
						g.fillPolygon(xLightTri, yLightTri, 3);
						
						g.setColor(Color.BLACK);
						g.fillPolygon(xDarkTri, yDarkTri, 3);
						
						g.setColor(Color.GRAY);
						g.fillRect(xSpacing+1+3, ySpacing+1+3, 27-6, 27-6);	

						g.setColor(Color.RED);
						g.fillPolygon(xArray,yArray, 3);
						g.setColor(Color.BLACK);
						g.drawLine(xSpacing+9, ySpacing+7, xSpacing+9, ySpacing+20);
					}
					
					else if(board[x][y].beingPressed()){
						g.setColor(Color.GRAY);
						g.drawRect(xSpacing+1, ySpacing+1, 26, 26);
						}
					
					else{
					int[] xLightTri ={xSpacing+1,xSpacing+1,xSpacing+28};
					int[] yLightTri ={ySpacing+1,ySpacing+28,ySpacing+1};
					
					int[] xDarkTri ={xSpacing+28,xSpacing+28,xSpacing+1};
					int[] yDarkTri ={ySpacing+28,ySpacing+1,ySpacing+28};
						
					g.setColor(Color.LIGHT_GRAY);
					g.fillPolygon(xLightTri, yLightTri, 3);
					
					g.setColor(Color.BLACK);
					g.fillPolygon(xDarkTri, yDarkTri, 3);
					
					g.setColor(Color.GRAY);
					g.fillRect(xSpacing+1+3, ySpacing+1+3, 27-6, 27-6);	
						
					//g.drawString("", x, y);
//					g.fillRect(xSpacing+1, ySpacing+13, 27, 15);
//					g.setColor(Color.WHITE);
//					g.fillRect(xSpacing+1, ySpacing+1, 26, 23);
//					g.setColor(Color.BLACK);
//					g.drawRect(xSpacing+1, ySpacing+1, 26, 22);
					}

				}
				else{

					if(board[x][y].isBomb()){
						
						
						
						g.setColor(Color.BLACK);
						g.fillOval(xSpacing+9, ySpacing+9, 10, 10);
						g.drawLine(xSpacing+8, ySpacing+8, xSpacing+21, ySpacing+21); //top left/bottom right
						g.drawLine(xSpacing+5, ySpacing+14, xSpacing+23, ySpacing+14);//mid 
						g.drawLine(xSpacing+21, ySpacing+8, xSpacing+8, ySpacing+21);//top right/bottom left
						g.drawLine(xSpacing+14, ySpacing+5, xSpacing+14, ySpacing+23);//top/down
						
						g.setColor(Color.GRAY);
						g.drawRect(xSpacing+1, ySpacing+1, 26, 26);
					}
					else if(board[x][y].getBombsSurrounding()==0){
						
						g.setColor(Color.GRAY);
						g.drawRect(xSpacing+1, ySpacing+1, 26, 26);
					}

					else{ 
						g.fillRect(xSpacing+1, ySpacing+1, 27, 27);
						Font font = new Font("SANS_SERIF", Font.BOLD,10); 
						g.setFont(font);
						g.setColor(Color.BLACK);
						g.drawString(""+board[x][y].getBombsSurrounding(), xSpacing+10, ySpacing+19);
						g.setColor(Color.GRAY);
						g.drawRect(xSpacing+1, ySpacing+1, 26, 26);
					}

//					if(x==startX&&y==startY){
//						g.setColor(Color.YELLOW);
//						g.fillOval(xSpacing+5, ySpacing+5, 18, 18);
//						
//					}
					
				}

				xSpacing += 27;
			}
			ySpacing+= 27;
		}
	}
	//stack of bomb locations when initialized then pops and makes that one red (simulating it's being blown up)
}


