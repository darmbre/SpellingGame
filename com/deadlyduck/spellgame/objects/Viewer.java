package com.deadlyduck.spellgame.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.deadlyduck.spellgame.SpellingGame;

public class Viewer 
{
	private int borderHeight=0;
	private int borderWidth=0;
	private int width=0;
	private int height=0;
	private Rectangle rect=null;
	
    public ArrayList<String> normalWordList=new ArrayList<String>();
    public ArrayList<String> difficultWordList=new ArrayList<String>();
    public ArrayList<String> vowelWordList=new ArrayList<String>();
    public ArrayList<String> homophoneWordList=new ArrayList<String>();	
    
    int normalWordListIndex=0;
    int difficultWordListIndex=0;
    int vowelWordListIndex=0;
    int homophoneWordListIndex=0;
	
	public Viewer(int widthOfBorder, int heightOfBorder, int width, int height)
	{
		borderHeight=heightOfBorder;
		borderWidth=widthOfBorder;
		this.width=width;
		this.height=height;
		this.rect=new Rectangle(width,height);
		loadWordLists();
	}
	
	public void drawCenteredString(Graphics g, Slide currentSlide, boolean timeForDifferentWord) {

		String text=chooseWord(currentSlide);
		Font font=new Font("Arial Black", Font.BOLD, 60);
		Font prevFont=g.getFont();
		g.setFont(font);		
		// Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = (rect.width - metrics.stringWidth(text)) / 2;
	    
	    //Account for corner slides width??
	    x+=borderWidth*2;
	    
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    
	    Color prevColor=g.getColor();
	    g.setColor(Color.WHITE);
	    g.drawRect(borderHeight, borderWidth, rect.height, rect.width);
	    // Draw the String
	    g.drawString(text, x, y);
	    g.setColor(prevColor);
	    
	    g.setFont(prevFont);
	}
	
	private String chooseWord(Slide slide) {
		Color color=slide.getColor();
		String theWord=null;
		

		if (color==Color.RED) 
		{
			if (SpellingGame.timeForDifferentWord==true) difficultWordListIndex++;
			theWord=difficultWordList.get(difficultWordListIndex);
		}
		else if (color==Color.PINK) 
		{
			if (SpellingGame.timeForDifferentWord==true) vowelWordListIndex++;
			theWord=vowelWordList.get(vowelWordListIndex);
		}
		else if (color==Color.MAGENTA)
		{
			if (SpellingGame.timeForDifferentWord==true) homophoneWordListIndex++;
			theWord=homophoneWordList.get(homophoneWordListIndex);
		}
		else
		{
			if (SpellingGame.timeForDifferentWord==true) normalWordListIndex++;
			theWord=normalWordList.get(normalWordListIndex);
		}
		
		return theWord;
	}

	public void setBlack(Graphics g)
	{
		Color prevColor=g.getColor();
		g.setColor(Color.BLACK);
		g.drawRect(borderWidth, borderHeight, width, height);
		g.setColor(prevColor);
	}
	
	public void loadWordLists()
	{
		normalWordList.add("philenthropic");
		normalWordList.add("combustable");
		normalWordList.add("audacious");
		normalWordList.add("infatuation");
		normalWordList.add("forestry");
		normalWordList.add("tradition");
		
		difficultWordList.add("antediluvian");
		difficultWordList.add("apocryphal");
		difficultWordList.add("camaraderie");
		difficultWordList.add("puerile");
		difficultWordList.add("sanctimonious");
		difficultWordList.add("vicissitude");
		difficultWordList.add("AMOEBAE");
		
		
		

		
		homophoneWordList.add("red");
		homophoneWordList.add("there");
		homophoneWordList.add("to");
		homophoneWordList.add("aisle");
		homophoneWordList.add("aisle");
		homophoneWordList.add("banned");
		homophoneWordList.add("haul");
		homophoneWordList.add("hare");
	}
	

}
