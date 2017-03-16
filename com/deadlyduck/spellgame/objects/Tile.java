package com.deadlyduck.spellgame.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import com.deadlyduck.spellgame.SpellingGame;

public class Tile 
{
	public final static int numOfTilesHigh=5;
	public final static int numOfTilesWide=6;
	private int tileWidth = 0;
	private int tileHeight = 0;
	private int tileHeightCornerDiameter = 0;
	private int tileWidthCornerDiameter = 0;
	private int tileInnerBoarder=5;
	private int x=0;
	private int y=0;
	private Slide slide=null;	
	
	Font tileFont=new Font("Arial Black", Font.BOLD, 12);

	
	public Tile (Board board, int x, int y, Slide s)
	{
		tileWidth = Board.getBoardWidth() / numOfTilesWide; // Number of tiles accross
		tileHeight = Board.getBoardHeight() / numOfTilesHigh; // tiles up and down
		tileHeightCornerDiameter = tileHeight / 2;
		tileWidthCornerDiameter = tileWidth / 2;
		this.slide=s;
		this.x=x;
		this.y=y;
	}
	
	public void drawGameTile(Graphics2D screen, int x, int y) {
		this.x=x;
		this.y=y;		
	}	
	
	public void drawGameTile(Graphics2D screen, boolean isSelected) {
		screen.setColor(Color.BLACK);
		screen.drawRect(this.x, this.y, tileWidth, tileHeight);
		drawSlide(screen, isSelected);
		screen.setColor(Color.BLACK);
		Rectangle rect=new Rectangle(tileWidth, tileHeight);
		drawCenteredString(screen, slide.getText(), rect, /*SpellingGame.getFrameFont()*/tileFont );
	}
	
	private void drawSlide(Graphics2D screen, boolean isSelected) {
		
		screen.setColor(slide.getColor());
		screen.fillRoundRect(this.x+tileInnerBoarder, this.y+tileInnerBoarder, tileWidth-(tileInnerBoarder*2), tileHeight-(tileInnerBoarder*2), tileWidthCornerDiameter, tileHeightCornerDiameter);

		Stroke prevStroke=screen.getStroke();
		screen.setStroke(new BasicStroke(20));
		if (isSelected)
		{
			screen.setColor(Color.YELLOW);
		}
		else
		{
			screen.setColor(Color.GRAY);
		}
		screen.drawRoundRect(this.x+tileInnerBoarder, this.y+tileInnerBoarder, tileWidth-(tileInnerBoarder*2), tileHeight-(tileInnerBoarder*2), tileWidthCornerDiameter, tileHeightCornerDiameter);
		screen.setStroke(prevStroke);
		
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	public Slide getSlide() {
		return slide;
	}

	public void setSlide(Slide slide) {
		this.slide = slide;
	}
	
	private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {

		Font prevFont=g.getFont();
		// Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, this.x+x, this.y+y);
	    g.setFont(prevFont);
	}

}
