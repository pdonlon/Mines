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


public class Play extends JFrame implements ActionListener, MouseMotionListener, MouseListener, KeyListener{

	JMenuItem g1 = new JMenuItem("Reset");
	JCheckBoxMenuItem g2 = new JCheckBoxMenuItem("Marks (?)");
	JCheckBoxMenuItem g3 = new JCheckBoxMenuItem("Compact");
	JMenuItem g4 = new JMenuItem("Exit");

	JCheckBoxMenuItem m1 = new JCheckBoxMenuItem("Easy");
	JCheckBoxMenuItem m2 = new JCheckBoxMenuItem("Medium");
	JCheckBoxMenuItem m3 = new JCheckBoxMenuItem("Hard");
	JCheckBoxMenuItem m4 = new JCheckBoxMenuItem("Custom");


	JMenuItem h1 = new JMenuItem("About");
	JMenuItem h2 = new JMenuItem("Instructions");
	JMenuItem h3 = new JMenuItem("More Apps...");

	Board playBoard;
	CheckList bombs;
	GameDisplay gameD;

	String difficulty;

	boolean gameOver = false;
	
	

	public static void main(String[] args) {

		//JFrame secret = new JFrame();

		//JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");

		//Menu newGameWindow = new Menu();

		Play game = new Play("MEDIUM");
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
		JMenu mode = new JMenu("Mode");

		gameD = new GameDisplay();
		this.setJMenuBar(menuBar);


		menuBar.add(game);
		menuBar.add(mode);
		menuBar.add(help);

		game.add(g1);
		game.add(g2);
		game.add(g3);
		game.add(g4);

		mode.add(m1);
		mode.add(m2);
		mode.add(m3);
		mode.add(m4);

		help.add(h1);
		help.add(h2);
		help.add(h3);

		g1.addActionListener(this);
		g2.addActionListener(this);
		g3.addActionListener(this);
		g4.addActionListener(this);


		m1.addActionListener(this);
		m2.addActionListener(this);
		m3.addActionListener(this);
		m4.addActionListener(this);

		h1.addActionListener(this);
		h2.addActionListener(this);
		h3.addActionListener(this);

		mode.addActionListener(this);
		game.addActionListener(this);
		help.addActionListener(this);

		m2.setSelected(true);

		this.pack();
		setTitle("Mines");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //EXIT_ON_CLOSE
		this.add(gameD);
		this.setSize(playBoard.getWindowX(),playBoard.getWindowY());
		this.setVisible(true);
		this.setResizable(false);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

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

			int x = (e.getX()-2)/27;
			int y = (e.getY()-26-22)/27;

			playBoard.add(x, y);
			playBoard.press(x,y);
			repaint();
		}

	}

	public void mouseReleased(MouseEvent e) {

		if(!playBoard.isEmpty())
			playBoard.resetPressed();

		int x = (e.getX()-2)/27;
		int y = (e.getY()-26-22)/27;

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


					}
				}

				if(SwingUtilities.isRightMouseButton(e)){

					playBoard.markFlagged(x, y);
				}

			}
			else
				
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

			int x = (e.getX()-2)/27;
			int y = (e.getY()-26-22)/27;



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


		

		if(e.getSource() == g2) //question marks
			questionMarks();

		else if(e.getSource() == g3){
			System.out.print("Well...this is embarassing");
		}

		else if(e.getSource() == g4){
			dispose();
		}

		else if(e.getSource() == g1) //reset
			this.resetGame();
		
		else if(e.getSource() == h1) //about
			JOptionPane.showMessageDialog(null,"Mines\nby Philip Donlon");
		else if(e.getSource() == h2) //instructions
			JOptionPane.showMessageDialog(null, "Open all the tiles without hitting a bomb.\nNumbered tiles indicate how many bombs are touching it.\nLeft click to open a single tile.\nFlag a tile by right clicking.\nDouble click a tile with the correct amount of flags around it for a speed click.\nDouble click the starting tile for a quick reset.");
		else if(e.getSource() == h3) //more apps
			goOnline("http://www.github.com/pdonlon");

		if(e.getSource()==m1 || e.getSource()==m2 || e.getSource()==m3 || e.getSource()==m4){

			if(e.getSource() == m1){
				clearChecks();
				m1.setSelected(true);
				playBoard.setWidth(9);
				playBoard.setHeight(9);
				this.setDifficulty("EASY");
				playBoard.setTotalBombs(10);
			}

			else if(e.getSource() == m2){
				clearChecks();
				m2.setSelected(true);
				playBoard.setWidth(16);
				playBoard.setHeight(16);
				this.setDifficulty("MEDIUM");
				playBoard.setTotalBombs(40);

			}

			else if(e.getSource() == m3){
				clearChecks();
				m3.setSelected(true);
				playBoard.setWidth(30);
				playBoard.setHeight(16);
				this.setDifficulty("Hard");
				playBoard.setTotalBombs(99);
			}
			else if(e.getSource() == m4){//custom

				clearChecks();
				m4.setSelected(true);
				int width = 0, height =0, totalBombs = 0;

				while(width<9){
					String x = JOptionPane.showInputDialog("Desired width of your board");
					if(x==null)
						return;

					try{
						width = Integer.parseInt(x);
						playBoard.setWidth(width);
					}
					catch(Exception ex){
						width = 0;
					}
				}

				while(height<9){
					String y = JOptionPane.showInputDialog("Desired height of your board");
					if(y==null)
						return;

					try{
						height = Integer.parseInt(y);
						playBoard.setHeight(height);
					}
					catch(Exception ex){
						height = 0;
					}
				}


				while(totalBombs<=0 || totalBombs>width*height-9){
					String bombs = JOptionPane.showInputDialog("Desired number of bombs on your board");
					if(bombs==null)
						return;
					try{
						totalBombs = Integer.parseInt(bombs);
						playBoard.setTotalBombs(totalBombs);
					}
					catch(Exception ex){
						totalBombs = 0;
					}
				}

				this.setDifficulty("CUSTOM");
			}



			playBoard.setUp();
			this.setSize(27*this.playBoard.getWidth()+1,27*this.playBoard.getHeight()+23+53);
			resetGame();
			playBoard.initializeBoard();

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

	public void resetGame(){

		playBoard.startup();
		playBoard.wipeBoard();
		gameOver = false;


		repaint();
	}


	public void goOnline(String site){
		try {
			java.awt.Desktop.getDesktop().browse(new URI(site));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void clearChecks(){

		m1.setSelected(false);
		m2.setSelected(false);
		m3.setSelected(false);
		m4.setSelected(false);

	}

	public void questionMarks(){
		if(playBoard.questionMarksEnabled()){
			g2.setSelected(false);
			playBoard.setQuestionMarks(false);
		}
		else{
			g2.setSelected(true);
			playBoard.setQuestionMarks(true);
		}
	}

	public void explosion(){

		playBoard.openBomb();
		repaint();
	}

	public void keyTyped(KeyEvent ev) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent ev) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent ev) {

		int keycode = ev.getKeyCode();

		if(keycode == 67){ //TODO

			playBoard.check();
			System.out.print("pressing c");
			repaint();
		}

	}

}


