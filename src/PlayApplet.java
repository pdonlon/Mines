import javax.swing.JApplet;

public class PlayApplet extends JApplet{

	GameDisplay gameD;

	public PlayApplet(){

		gameD = new GameDisplay("Hard");
		this.setJMenuBar(gameD.getJMenuBar());

		this.add(gameD);
		this.setSize(gameD.playBoard.getWindowX(),gameD.playBoard.getWindowY());
		this.setVisible(true);
		this.setFocusable(true);
	}


}
