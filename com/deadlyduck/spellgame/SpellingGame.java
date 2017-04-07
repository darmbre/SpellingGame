package com.deadlyduck.spellgame;

import java.awt.Canvas;
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
import com.deadlyduck.spellgame.objects.Player;
import com.deadlyduck.spellgame.objects.Viewer;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class SpellingGame extends JPanel {

	private int screenWidth = 0;
	private int screenHeight = 0;	
	private Board gameBoard=null;
	private int state=0; //0 - init, 1 - Main Loop, 2 - Shutdown, 3 - restart?
	private Font font=null;
	private boolean isNewTurn=true;
	
	private InputStreamReader fileInputStream=new InputStreamReader(System.in);
    private BufferedReader keyboard=new BufferedReader(fileInputStream);	
    
	public GameKeyController gkc=null;
	private Player player=null;
    //public static boolean timeForDifferentWord=false;


	public SpellingGame()
	{
		// Gets initial size of the screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screen.width;
		screenHeight = screen.height-100;
		player=new Player();
		this.setLayout(null);
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
			Canvas videoCanvas=getBoard().getViewer().getCanvas();
			this.add(videoCanvas);
			////////////////////////////////////////////////////////////
			videoCanvas.setVisible(false);
			state=1;
			gkc.setState(1);
		}
		
		//Demo Mode
		if (this.state==1)
		{
			adjustBoard(gameBoard, g2d);
		} else
		
		// Start actual Game (add sound, wait for a spin)
		if (this.state==2)
		{
			//Stop the board and wait for a spin
			this.state=4;
			gkc.setState(4);
		} else
		
		// Spinning Board
		if (this.state==3)
		{
			isNewTurn=true;
			player.startSpinClip();
			gameBoard.drawBoard(g2d);
			gameBoard.shuffle();
			gameBoard.setSelectedSquare(g2d, state>0);
		} else
		
		// Stop board
		if (this.state==4)
		{
			player.stopSpinClip();
			
			if (isNewTurn)
			{
				if (gameBoard.isWhammy())
				{
					player.playWhammyClip();
				}
				else
				{
					player.playSelectClip();
				}
				
				isNewTurn=false;
			}			
			
			gameBoard.drawBoard(g2d);
			gameBoard.setSelectedSquare(g2d, false);
//			gameBoard.getViewer().getVideoPlayer().playMedia("C:\\dev\\test.mpg");
//			try {
//				Thread.currentThread().sleep(15000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		} else
		
		// Display a new word
		if (this.state==5)
		{
			gameBoard.showSpellingWord(g2d, true);
			this.state=6;
			gkc.setState(6);
			gameBoard.drawBoard(g2d);
			gameBoard.setSelectedSquare(g2d, false);
		} else
			
		if (this.state==6)
		{
			gameBoard.showSpellingWord(g2d, false);
			gameBoard.drawBoard(g2d);
			gameBoard.setSelectedSquare(g2d, false);
		} else
		
		// End the game
		if (this.state==7)
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
	
	public Board getBoard()
	{
		return this.gameBoard;
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