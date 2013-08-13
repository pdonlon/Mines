import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Play extends JFrame implements ActionListener, MouseMotionListener, MouseListener{

	JMenuItem g1 = new JMenuItem("Reset");
	JMenuItem g2 = new JMenuItem("Easy");
	JMenuItem g3 = new JMenuItem("Medium");
	JMenuItem g4 = new JMenuItem("Hard");
	JMenuItem g5 = new JMenuItem("Custom");
	JMenuItem g6 = new JMenuItem("Exit");

	JMenuItem h1 = new JMenuItem("Instructions");
	JMenuItem h2 = new JMenuItem("About");

	Board playBoard;
	CheckList bombs;
	GameDisplay gameD;
	JComboBox box;

	String difficulty;

	boolean gameOver = false;

	public static void main(String[] args) {

		//JFrame secret = new JFrame();

		//JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");

		//Menu newGameWindow = new Menu();

		Play game = new Play("HARD");
		//game.playBoard.initializeBoard();

		//String path = JOptionPane.showInputDialog("Pop up message text here");

		//JFrame window = new JFrame(""+game.getDifficulty());


	}

	public Play(String difficulty){

		this.difficulty = difficulty;

		if(difficulty.contains("EASY")){
			playBoard = new Board(9,9);
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

			this.setDifficulty("CUSTOM"); //TODO prevent user from doing dumb shit

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

		JMenu help = new JMenu("Help");
		JMenuBar menuBar = new JMenuBar();
		JMenu game = new JMenu("Game");

		gameD = new GameDisplay();
		this.setJMenuBar(menuBar);


		menuBar.add(game);
		menuBar.add(help);

		game.add(g1);
		game.add(g2);
		game.add(g3);
		game.add(g4);
		game.add(g5);
		game.add(g6);

		help.add(h1);
		help.add(h2);

		g1.addActionListener(this);
		g2.addActionListener(this);
		g3.addActionListener(this);
		g4.addActionListener(this);
		g5.addActionListener(this);
		g6.addActionListener(this);
		
		h1.addActionListener(this);
		h2.addActionListener(this);

		game.addActionListener(this);
		help.addActionListener(this);

		this.pack();
		setTitle(""+this.getDifficulty()+"     Flag Count: "+this.playBoard.getFlagCount());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //EXIT_ON_CLOSE
		this.add(gameD);
		this.setSize(27*this.playBoard.getWidth()+1,27*this.playBoard.getHeight()+23+53);
		this.setVisible(true);
		this.setResizable(false);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.playBoard.initializeBoard();
	}

	public GameDisplay getGameD(){

		return gameD;
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
			int y = (e.getY()-26-53)/27;

			playBoard.add(x, y);
			playBoard.press(x,y);
			repaint();
		}

	}

	public void mouseReleased(MouseEvent e) {

		if(!playBoard.isEmpty())
			playBoard.resetPressed();

		int x = e.getX()/27;
		int y = (e.getY()-26-53)/27;

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
			int y = (e.getY()-26-53)/27;



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

		if(e.getSource() == g2){
			playBoard.setWidth(9);
			playBoard.setHeight(9);
			this.setDifficulty("EASY");
			playBoard.setTotalBombs(10);
		}

		else if(e.getSource() == g3){
			playBoard.setWidth(16);
			playBoard.setHeight(16);
			this.setDifficulty("MEDIUM");
			playBoard.setTotalBombs(40);
			resetGame();
		}
		
		else if(e.getSource() == g4){
			playBoard.setWidth(30);
			playBoard.setHeight(16);
			this.setDifficulty("Hard");
			playBoard.setTotalBombs(99);
		}
		else if(e.getSource() == g5){//custom
			
		String x = JOptionPane.showInputDialog("Desired width of your board");
		int width = Integer.parseInt(x);
		playBoard.setWidth(width);
		
		String y = JOptionPane.showInputDialog("Desired height of your board");
		int height = Integer.parseInt(y);
		playBoard.setHeight(height);

		String bombs = JOptionPane.showInputDialog("Desired number of bombs on your board");
		int totalBombs = Integer.parseInt(bombs);

		playBoard.setTotalBombs(totalBombs);
			
			this.setDifficulty("CUSTOM");
		}
		
		playBoard.setUp();
		this.setTitle(""+this.getDifficulty()+"     Flag Count: "+this.playBoard.getFlagCount());
		this.setSize(27*this.playBoard.getWidth()+1,27*this.playBoard.getHeight()+23+53);
		resetGame();
		playBoard.initializeBoard();
		
		
		if(e.getSource() == g1) //reset
			this.resetGame();

		else if(e.getSource() == g6) //Exit
			this.dispose();
			
		else if(e.getSource() == h1) //instructions
			this.resetGame();
		else if(e.getSource() == h2) //about
		{ //TODO
			
			JOptionPane.showMessageDialog(null,"Mines\nby Philip Donlon");
			
			}
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


