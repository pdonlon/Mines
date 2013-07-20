
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
	
}
//System.out.println("Please pick coordinates inside the "+array.length+" by "+array[1].length+" dementions");