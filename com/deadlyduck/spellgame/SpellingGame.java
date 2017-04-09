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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

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
	
	private long startTime=0;
	private long currentTime=0;
	private long endTime=60;
	
	private InputStreamReader fileInputStream=new InputStreamReader(System.in);
    private BufferedReader keyboard=new BufferedReader(fileInputStream);	
    
	public GameKeyController gkc=null;
	private Player player=null;
    //public static boolean timeForDifferentWord=false;
	
	
	public static long SLOW_TIME=0;
	public static long FAST_TIME=0;
	public static long TIME_LIMIT=SLOW_TIME;
	public static String VLC_LIB=null;
	public static String VLC_ROOT=null;
	
	public static final String BASE_DIR="C:\\dev\\";


	public SpellingGame()
	{
		File file= new File(BASE_DIR+"game.properties");
		Properties properties=null;
		try {
			FileInputStream fileInput = new FileInputStream(file);
			properties = new Properties();
			properties.load(fileInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VLC_LIB=(String)properties.get("VLC_LIB");
		VLC_ROOT=(String)properties.get("VLC_ROOT");
		SLOW_TIME=Long.parseLong((String)properties.get("SLOW_TIME"));
		FAST_TIME=Long.parseLong((String)properties.get("FAST_TIME"));

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

			
		} else
		
		// Display a new word
		if (this.state==5)
		{
			startTime=new Date().getTime();
			gameBoard.showSpellingWord(g2d, true, null);
			this.state=6;
			gkc.setState(6);
			gameBoard.drawBoard(g2d);
			gameBoard.setSelectedSquare(g2d, false);
		} else
			
		if (this.state==6)
		{
			currentTime=new Date().getTime();
			endTime=startTime+TIME_LIMIT;
			gameBoard.showSpellingWord(g2d, false, null);
			gameBoard.drawBoard(g2d);
			gameBoard.setSelectedSquare(g2d, false);
			if (currentTime>=endTime)
			{
				player.playWhammyClip();
				//Stop the board and wait for a spin
				gameBoard.showSpellingWord(g2d, false, "Time's Up!!!");
				this.state=4;
				gkc.setState(4);
			}
		} else

		if (this.state==8)
		{
			state=0;
		} else
		// End the game
		if (this.state==100)
		{
			gameBoard.getViewer().getCanvas().setVisible(true);
			gameBoard.getViewer().getVideoPlayer().playMedia(BASE_DIR+"test.mpg");
			try {
				Thread.currentThread().sleep(30000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			this.state=4;
			gkc.setState(4);
			gameBoard.getViewer().getCanvas().setVisible(false);
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