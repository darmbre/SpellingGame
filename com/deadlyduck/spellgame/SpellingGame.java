package com.deadlyduck.spellgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.deadlyduck.spellgame.objects.Board;

public class SpellingGame extends JPanel {

	private int screenWidth = 0;
	private int screenHeight = 0;	
	private Board gameBoard=null;
	private int state=0; //0 - init, 1 - Main Loop, 2 - Shutdown, 3 - restart?
	private Font font=null;
	
	private InputStreamReader fileInputStream=new InputStreamReader(System.in);
    private BufferedReader keyboard=new BufferedReader(fileInputStream);	
    
	public GameKeyController gkc=null;
    public static boolean timeForDifferentWord=false;


	public SpellingGame()
	{
		// Gets initial size of the screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screen.width;
		screenHeight = screen.height-100;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		getInput();
		
		// Game Initialization Mode
		if (this.state==0) //initialize the game
		{
			// Establish and draw the initial board
			gameBoard=setupTheGame(g2d);
			state=1;
			gkc.setState(1);
		}
		
		//Demo Mode
		if (this.state==1)
		{
			adjustBoard(gameBoard, g2d);
		}
		
		// Start actual Game (add sound, wait for a spin)
		if (this.state==2)
		{
			//Stop the board and wait for a spin
			this.state=4;
			gkc.setState(4);
		}
		
		// Spinning Board
		if (this.state==3)
		{
			//display word
			//gameBoard.showSpellingWord(g2d,timeForDifferentWord);
		}
		
		// Stop board
		if (this.state==4)
		{
			adjustBoard(gameBoard, g2d);
		}
		
		// Display a new word
		if (this.state==5)
		{
			
		}
		
		// End the game
		if (this.state==6)
		{
			state=0;
		}
		gameBoard.showInfo(g2d);
		
		

	}

	private Board setupTheGame(Graphics2D screen) {
		
		Board board=new Board(screenWidth, screenHeight, screen);
		return board;
		
	}


	private void adjustBoard(Board board, Graphics2D screen) {
		board.drawBoard(screen);
		board.shuffle();
		board.setSelectedSquare(screen, state>0);
		
	}

	private void getInput() {
	
		this.state=gkc.getState();

	}
	
	public static void main(String[] args) throws InterruptedException {
		
		SpellingGame game = new SpellingGame();
		game.setBackground(Color.BLACK);
				
		//Creates the GUI to draw on
		JFrame frame = new JFrame("Spelling Bee");
		frame.add(game);
		frame.setSize(game.screenWidth, game.screenHeight);
		frame.setVisible(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Creates the keyboard entry
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		game.gkc=new GameKeyController();
		kfm.addKeyEventDispatcher(game.gkc);
		
		// Gets the default font
		game.setFont(frame.getFont());
		// Game Loop
		while (true) {
			game.repaint();
			Thread.sleep(500);
		}
	}	
	


	
	


}
