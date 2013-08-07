import java.awt.Color;


public class Mine {
	//	  	size	mines	opened
	//beg 	8x8 	10		54
	//med 	16x16 	40		216
	//exp 	30x16 	99		381

	boolean opened;
	int bombsSurrounding;
	boolean flagged;
	boolean bomb;
	boolean wrong;

	int flagCount;
	int openedCount;

	public Mine(boolean opened, int bs, boolean flagged, boolean bomb, boolean wrong){

		this.opened = opened;
		this.bombsSurrounding = bs;
		this.flagged = flagged;
		this.bomb = bomb;
		this.wrong = wrong;

	}

	public void setOpened(boolean a){

		opened = a;
	}

	public boolean isOpened(){

		return opened;
	}

	public void addOneBombSurrounding(){

		bombsSurrounding++;
	}

	public int getBombsSurrounding(){

		return bombsSurrounding;
	}

	public void setFlagged(boolean a){

		flagged = a;
	}

	public boolean isFlagged(){

		return flagged;
	}

	public void setBomb(boolean a){

		bomb = a;

	}

	public boolean isBomb(){

		return bomb;
	}

	public boolean isWrong(){

		return wrong;
	}

	public void setWrong(boolean a){

		wrong = a;
	}

	public Color determineColor(){

		Color color;
		
		if(wrong)
			color = Color.BLACK;
		
		else if(flagged)
			color = Color.RED;

		else if(bomb || !opened)
			color = Color.BLACK;

		else if(bombsSurrounding==0)
			color = Color.WHITE;

		else if(bombsSurrounding==1)
			color = Color.CYAN;

		else if(bombsSurrounding==2)
			color = Color.GREEN;

		else if(bombsSurrounding==3)
			color = Color.RED;
			
		else if(bombsSurrounding==4)
			color = Color.ORANGE;

		else if(bombsSurrounding==5)
			color = Color.MAGENTA;

		else if(bombsSurrounding==6)
			color = Color.YELLOW;

		else if(bombsSurrounding==7)
			color = Color.DARK_GRAY;

		else
			color = Color.GRAY;



		return color;

	}

}
//System.out.println("Please pick coordinates inside the "+array.length+" by "+array[1].length+" dementions");