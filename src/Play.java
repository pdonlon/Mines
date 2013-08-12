import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Play extends JFrame implements ActionListener, MouseMotionListener, MouseListener{

	Board playBoard;
	CheckList bombs;

	String difficulty;

	boolean gameOver = false;

	public static void main(String[] args) {

		//JFrame secret = new JFrame();

		Menu newGameWindow = new Menu();
		
		while(newGameWindow.getGameDifficulty()==null){
			
			System.out.println("");
			
		}
		
		newGameWindow.dispose();
		
		Play game = new Play(newGameWindow.getGameDifficulty());
		game.playBoard.initializeBoard();
		
		//String path = JOptionPane.showInputDialog("Pop up message text here");

		//JFrame window = new JFrame(""+game.getDifficulty());


	}

	public Play(String difficulty){

		this.difficulty = difficulty;
		
		if(difficulty.contains("EASY")){
			playBoard = new Board(8,8);
			playBoard.setTotalBombs(10);
			
		}

		else if(difficulty.contains("MEDIUM")){
			playBoard = new Board(16,16);
			playBoard.setTotalBombs(40);
		}

		else if(difficulty.contains("HARD")){
			playBoard = new Board(30,16);
			playBoard.setTotalBombs(99);
		}
		
		else{
			//TODO
			
			this.setDifficulty("CUSTOM");
			
			String x = JOptionPane.showInputDialog("Desired width of your board");
			int width = Integer.parseInt(x);
			
			String y = JOptionPane.showInputDialog("Desired height of your board");
			int height = Integer.parseInt(y);
			
			String bombs = JOptionPane.showInputDialog("Desired number of bombs on your board");
			int totalBombs = Integer.parseInt(bombs);
			
			playBoard = new Board(width,height);
			playBoard.setTotalBombs(totalBombs);
		}
		
		playBoard.setUp();

		GameDisplay d = new GameDisplay();
		setTitle(""+this.getDifficulty()+"     Flag Count: "+this.playBoard.getFlagCount());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //EXIT_ON_CLOSE
		this.add(d);
		this.setSize(27*this.playBoard.getWidth()+1,27*this.playBoard.getHeight()+23);
		this.setVisible(true);
		this.setResizable(false);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		//this.playBoard.initializeBoard();
	}

	public String getDifficulty(){

		return difficulty;
	}
	
	public void setDifficulty(String a){
		
		difficulty = a;
	}

	public boolean gameOver(){

		if(playBoard.getWin() || playBoard.getLose())

			gameOver = true;

		return gameOver;
	}
	

	public class GameDisplay extends JPanel{

		public void paintComponent(Graphics g){

			super.paintComponent(g);
			this.setBackground(Color.LIGHT_GRAY);

			playBoard.paintBoard(g);


		}

	}

	public void mouseClicked(MouseEvent e) {

		
	}

	public void mousePressed(MouseEvent e) {
		
		if(SwingUtilities.isLeftMouseButton(e)){
		
		int x = e.getX()/27;
		int y = (e.getY()-26)/27;
		
		playBoard.add(x, y);
		playBoard.press(x,y);
		repaint();
		}

	}

	public void mouseReleased(MouseEvent e) {

		if(!playBoard.isEmpty())
			playBoard.resetPressed();

		int x = e.getX()/27;
		int y = (e.getY()-27)/27;
		
		if(playBoard.isValid(x, y)){

		if(SwingUtilities.isLeftMouseButton(e)){

			if(gameOver)
				resetGame();

			else
				playBoard.open(x, y);

		}
		if(!gameOver()){

			if(!playBoard.getFirstTurn()&&SwingUtilities.isLeftMouseButton(e)){
				if(e.getClickCount() == 2)	{

					if(playBoard.getStartX()==x && playBoard.getStartY()==y)

						resetGame();

					else if(playBoard.unopenedAround(x, y))
						playBoard.fastClick(x,y);

					if(gameOver())
						gameOverTitle();

				}
			}

			if(SwingUtilities.isRightMouseButton(e)){

				playBoard.markFlagged(x, y);
			}
			if(!gameOver)
				updateFlagTitle();

		}
		else
			gameOverTitle();
		if(playBoard.lose){

			anonymous();

		}
		repaint();
		}

	}

	public void mouseEntered(MouseEvent e) {
		

	}

	public void mouseExited(MouseEvent e) {
		
		playBoard.resetPressed();
		repaint();

	}

	public void mouseDragged(MouseEvent e) {
		
		if(SwingUtilities.isLeftMouseButton(e)){
		
		int x = e.getX()/27;
		int y = (e.getY()-26)/27;
		
		
		
		repaint();
		
		if(!playBoard.alreadyThere(x,y)&&playBoard.isValid(x, y)){
			playBoard.resetPressed();
			playBoard.replace(x, y);
			playBoard.press(x,y);
			repaint();
		}
		repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
		

	}

	public void actionPerformed(ActionEvent e) {
		

	}

	public void anonymous(){

		Thread t = new Thread( new Runnable(){
			public void run(){

				int bombsLeft = playBoard.getTotalBombs()-1;
				

				while(bombsLeft>0){

					try {
						Thread.sleep(60);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}

					explosion();

					bombsLeft--;
				}
			}
		});

		t.start();

	}

	public void updateFlagTitle(){

		setTitle(""+getDifficulty()+"     Flag Count: "+playBoard.getFlagCount());

	}

	public void resetGame(){

		playBoard.startup();
		playBoard.wipeBoard();
		gameOver = false;

		repaint();
	}

	public void explosion(){

		playBoard.openBomb();
		repaint();
	}


	public void gameOverTitle(){

		if(playBoard.getWin())

			setTitle("GAME OVER! YOU WIN!");

		else
			setTitle("GAME OVER! YOU LOSE");

	}

}


