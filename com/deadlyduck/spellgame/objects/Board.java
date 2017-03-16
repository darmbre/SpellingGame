package com.deadlyduck.spellgame.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

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
	

	
	
	public Board(int width, int height, Graphics2D screen)
	{
		boardWidth=width;
		boardHeight=height-statusBarHeight;
		createBoardLayout(screen);
		
	}	
	
	private void createBoardLayout(Graphics2D screen) {
		
		int x = 1;
		int y = 1;
		
		//It just feels like we'll have at least 16 tiles even given the dynamic logic
		tiles=new ArrayList<Tile>(16);
		slides=new ArrayList<Slide>(16);
		
		Tile tile=new Tile(this, x, y, new Slide());
		int tileWidth=tile.getTileWidth();
		int tileHeight=tile.getTileHeight();
		viewer=new Viewer(tileHeight+15, tileWidth+15, tileHeight*(Tile.numOfTilesHigh-2)-20,tileWidth*(Tile.numOfTilesWide-2)-20);
		
		tile=null;

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
		
		// Create the viewer
		
		
	}

	private void addTileToGame(Graphics2D screen, int x, int y) {
		Tile tile;
		tile = new Tile(this,x,y, new Slide());
		tile.drawGameTile(screen, false);
		tiles.add(tile);
		slides.add(tile.getSlide());
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
		screen.drawString("Width:" + boardWidth, 30, boardHeight - 130);
		screen.drawString("Height:" + boardHeight, 30, boardHeight - 145);
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
	
	public void showSpellingWord(Graphics2D screen, boolean timeForDifferentWord)
	{
		viewer.drawCenteredString(screen, currentSlide, timeForDifferentWord);
	}


}
