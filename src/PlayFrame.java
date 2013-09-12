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


public class PlayFrame extends JFrame{

	GameDisplay gameD;

	public static void main(String[] args) {

		PlayFrame mainGame = new PlayFrame("Medium");

	}

	public PlayFrame(String difficulty){

		gameD = new GameDisplay("Hard",this);
		this.setJMenuBar(gameD.getJMenuBar());


		this.pack();
		setTitle("Mines");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //EXIT_ON_CLOSE
		this.add(gameD);
		this.setSize(gameD.playBoard.getWindowX(),gameD.playBoard.getWindowY());
		this.setVisible(true);
		this.setResizable(false);

	}

}




