
public class Board {

	//		  	size	mines	opened
	//	beg 	8x8 	10		54
	//	med 	16x16 	40		216
	//	exp 	30x16 	99		381

	static Mine begBoard[][] = new Mine[8][8];
	static Mine medBoard[][] = new Mine[16][16];
	static Mine expBoard[][] = new Mine[30][16];

	public static Mine[][] getBegBoard(){

		return begBoard;
	}

	public static Mine[][] getMedBoard(){

		return medBoard;
	}

	public static Mine[][] getExpBoard(){

		return expBoard;
	}

	public static void initializeBoard(Mine[][] array){

		for(int y=0; y<array[1].length; y++){
			for(int x=0; x<array.length; x++){

				array[x][y] = new Mine (false, 0, false, false); 

			}
		}
		placeBombs(array);
		placeBombsSurrounding(array);
		printBoard(array);

	}

	public static void placeBombs(Mine[][] array){
		int bombCount;

		if(array.length == 8)
			bombCount = 10;
		else if(array.length == 16 && array[1].length == 16)
			bombCount = 40;
		else
			bombCount = 99;

		int count = bombCount;

		while(count > 0){
			int rand1 = (int) (Math.random() * array.length); 
			int rand2 = (int) (Math.random() * array[1].length); //to get the y (rectangle ones)

			if(array[rand1][rand2].isBomb()==false)
				array[rand1][rand2].setBomb(true);
			count--;
		}
	}

	public static void placeBombsSurrounding(Mine[][] array){

		for(int y=0; y<array[1].length; y++){
			for(int x=0; x<array.length; x++){

				if(array[x][y].isBomb()){

					for(int j=-1; j<2; j++){			
						for(int i=-1; i<2; i++){

							if(isValid(array,x+j,y+i)==true&& array[x+j][y+i].isBomb()==false)
								array[x+j][y+i].addOneBombSurrounding();
						}
					}
				}
			}
		}
	}

	public static boolean isValid(Mine[][] array, int x, int y){

		boolean valid = false;

		try{
			if(array[x][y]!=null)
				valid = true;
		}
		catch(Exception e){

		}
		return valid;
	}

	public static void openZeros(Mine[][]array, int x, int y){

		for(int i=-1; i<2; i++){			
			for(int j=-1; j<2; j++){

				if(isValid(array,x+i,y+j)){
					array[x+i][y+j].setOpened(true);
					
					if(array[x+i][y+j].getBombsSurrounding()==0){
						
						CheckList check = new CheckList();
						check.enque(x,y);
						
						FullList full = new FullList();
						full.add(x,y);

					}
				}


			}
		}

	}

	public static void printBoard(Mine[][] array){

		for(int y=0; y<array[1].length; y++){
			for(int x=0; x<array.length; x++){

				if(array[x][y].isBomb()){
					System.out.print("B ");
				}

				//if(array[x][y].isOpened() == true){
				else if(array[x][y].getBombsSurrounding()==0){
					System.out.print("0 ");
				}
				else
					System.out.print(array[x][y].getBombsSurrounding()+" ");
			}

			//}
			System.out.println();
		}

	}

}
