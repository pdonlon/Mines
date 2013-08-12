import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame implements ActionListener, MouseMotionListener, MouseListener{
	
	Play play;
	Menu menu;
	int blockSize = 100;
	
	String[][] options = new String[2][2];
	
	public Menu(){

		MenuDisplay d = new MenuDisplay();
		this.setTitle("MINESWEEPER   PLEASE SELECT A DIFFICULTY");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(d);
		this.setSize(300,300);
		this.setVisible(true);
		this.setResizable(false);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		options[0][0] = "EASY";
		options[0][1] = "MEDIUM";
		options[1][0] = "HARD";
		
		
	}
	
	public void paintMenu(Graphics f){
		
		
		
		int ySpacing = 0;
		
		for(int y=0; y<2; y++){
			
			int xSpacing =0;
			
			for(int x=0; x<2; x++){
				
				f.setColor(Color.BLACK);
				f.drawRect(xSpacing, ySpacing, blockSize, blockSize);
				
				xSpacing+=(blockSize)+1;
			}
			ySpacing+=(blockSize)+1;
		}
		
	}

	public class MenuDisplay extends JPanel{

		public void paintComponent(Graphics f){

			super.paintComponent(f);
			this.setBackground(Color.WHITE);

			paintMenu(f);

		}

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		
		int x = e.getX()/blockSize;
		int y = (e.getY()-26)/blockSize; //23 is the title bar size
		
		play = new Play(options[x][y]);
		
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}