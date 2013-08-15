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
	boolean explode;
	boolean wrong;
	boolean pressed;
	boolean questionMarked;

	int flagCount;
	int openedCount;

	public Mine(boolean opened, int bs, boolean flagged, boolean bomb, boolean explode, boolean wrong, boolean pressed, boolean questionMarked){

		this.opened = opened;
		this.bombsSurrounding = bs;
		this.flagged = flagged;
		this.bomb = bomb;
		this.explode = explode;
		this.wrong = wrong;
		this.pressed = pressed;
		this.questionMarked = questionMarked;

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
	
	public boolean exploded(){
		
		return explode;
	}

	public void setExplode(boolean a){
		
		explode = a;
	}
	
	public boolean isWrong(){

		return wrong;
	}

	public void setWrong(boolean a){

		wrong = a;
	}
	
	public boolean beingPressed(){
		
		return pressed;
	}
	
	public void setPressed(boolean a){
		
		pressed = a;
	}
	
	public boolean isQuestionMarked(){
		
		return questionMarked;
	}

	public void setQuestionMarked(boolean a){
		
		questionMarked = a;
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
			color = Color.BLUE;

		else
			color = Color.BLACK;



		return color;

	}

}
//System.out.println("Please pick coordinates inside the "+array.length+" by "+array[1].length+" dementions");