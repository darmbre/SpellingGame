package com.deadlyduck.spellgame.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import com.deadlyduck.spellgame.GameKeyController;
import com.deadlyduck.spellgame.SpellingGame;

public class Board {
	
	private static int boardWidth=0;
	private static int boardHeight=0;
	
	private static int statusBarHeight=70;
	
	private ArrayList<Tile> tiles=null;
	private ArrayList<Slide> slides=null;
	private Random randomFactory=new Random(new Date().getTime());
	private int selectedTileNumber=1;
	
	private Viewer viewer=null;
	private Slide currentSlide=null;
	private int slidePtr=0;
	
	private int totalSlides=36;
	private int totalTiles=18;
	
	//Weights of Slides (needs to add up to 36)
	int numOfSlideType1=4;
	int numOfSlideType2=4;
	int numOfSlideType3=4;
	int numOfSlideType4=4;
	int numOfSlideType5=4;
	int numOfSlideType6=4;
	int numOfSlideType7=3;
	int numOfSlideType8=3;
	int numOfSlideType9=3;
	int numOfSlideType10=3;

	
	public Board(int width, int height, Graphics2D screen)
	{
		boardWidth=width;
		boardHeight=height-statusBarHeight;
		createBoardLayout(screen);
		
	}
	
	private void createSlides()
	{
		for (int i=0;i<numOfSlideType1;i++)
		{
			Slide s=new Slide(Color.BLUE);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType2;i++)
		{
			Slide s=new Slide(Color.RED);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType3;i++)
		{
			Slide s=new Slide(Color.CYAN);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType4;i++)
		{
			Slide s=new Slide(Color.GREEN);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType5;i++)
		{
			Slide s=new Slide(Color.PINK);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType6;i++)
		{
			Slide s=new Slide(Color.ORANGE);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType7;i++)
		{
			Slide s=new Slide(Color.DARK_GRAY);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType8;i++)
		{
			Slide s=new Slide(Color.LIGHT_GRAY);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType9;i++)
		{
			Slide s=new Slide(Color.MAGENTA);
			slides.add(s);
		}
		
		for (int i=0;i<numOfSlideType10;i++)
		{
			Slide s=new Slide(Color.GRAY);
			slides.add(s);
		}			
	}
	
	private void createBoardLayout(Graphics2D screen) {
		
		int x = 1;
		int y = 1;
		
		tiles=new ArrayList<Tile>(totalTiles);
		slides=new ArrayList<Slide>(totalSlides);
		
		// This is a dummy tile to get measurements
		Tile dummyTile=new Tile(this, x, y, null,null,0);
		int tileWidth=dummyTile.getTileWidth();
		int tileHeight=dummyTile.getTileHeight();
		dummyTile=null;
		
		createSlides();
		int viewerX=tileHeight+15;
		int viewerY=tileWidth+15;
		int viewerHeight=tileHeight*(Tile.numOfTilesHigh-2)-20;
		int viewerWidth=tileWidth*(Tile.numOfTilesWide-3)-20;//longer screens 3
		viewer=new Viewer(viewerX, viewerY, viewerHeight,viewerWidth);

		// Draw the top horizontal
		while (x < boardWidth - tileWidth) {
			addTileToGame(screen, x, y);
			x += tileWidth;
			
		}
		
		// Draw the right side
		x-=tileWidth;
		y=tileHeight;
		
		while (y < boardHeight) {
			addTileToGame(screen, x, y);
			y += tileHeight;
		}

		// Draw the bottom
		y-=tileHeight;
		x-=tileWidth;
		while (x >= 0)
		{
			addTileToGame(screen, x, y);
			x-=tileWidth;
		}
		
		// Draw the left side
		x+=tileWidth;
		y-=tileHeight;
		while (y >= tileHeight)
		{
			addTileToGame(screen, x, y);
			y-=tileHeight;
		}
	}

	private void addTileToGame(Graphics2D screen, int x, int y) {
		Tile tile;
		tile = new Tile(this,x,y, slides.get(slidePtr++),SpellingGame.FONT_NAME,SpellingGame.FONT_SIZE);
		tile.drawGameTile(screen, false);
		tiles.add(tile);
	}
	
	public void drawBoard(Graphics2D screen)
	{
		Iterator<Tile> i = tiles.iterator();
		
		// Draw the top horizontal
		while(i.hasNext())
		{
			i.next().drawGameTile(screen,false);
		}
	}

	public void showInfo(Graphics2D screen)
	{
		//screen.drawString("Width:" + boardWidth, 30, boardHeight - 130);
		//screen.drawString("Height:" + boardHeight, 30, boardHeight - 145);
		//screen.drawString("State:"+ GameKeyController.getState(), 30, boardHeight - 115);
	}
	
	public static int getBoardWidth() {
		return boardWidth;
	}

	public static void setBoardWidth(int boardWidth) {
		Board.boardWidth = boardWidth;
	}

	public static int getBoardHeight() {
		return boardHeight;
	}

	public static void setBoardHeight(int boardHeight) {
		Board.boardHeight = boardHeight;
	}
	

	
	public void shuffle()
	{
		Collections.shuffle(slides);
		Collections.shuffle(slides);
		for(int i=0;i<tiles.size();i++)
		{
			tiles.get(i).setSlide(slides.get(i));
		}
	}

	public void setSelectedSquare(Graphics2D screen, boolean isSpinning) {
		
		if (isSpinning)
		{
			selectedTileNumber=randomFactory.nextInt(tiles.size());
		}
		Tile tile=tiles.get(selectedTileNumber);
		currentSlide=tile.getSlide();
		tile.drawGameTile(screen,true);
	}
	
	public void showSpellingWord(Graphics2D screen, boolean timeForDifferentWord, String word)
	{
		viewer.drawCenteredString(screen, tiles.get(0).getTileWidth(), currentSlide, timeForDifferentWord, word);
	}
	
	public boolean isWhammy()
	{
		boolean whammy=false;
		if (this.currentSlide.getSlideType()==2)
		{
			whammy = true;
		}
		return whammy;
		
	}
	
	public Viewer getViewer()
	{
		return this.viewer;
	}

}