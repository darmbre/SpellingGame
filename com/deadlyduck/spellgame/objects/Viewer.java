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
    
    int normalWordListIndex=-1;
    
    private static String vowels="aeiouyAEIOUY";

	
	public Viewer(int widthOfBorder, int heightOfBorder, int width, int height)
	{
		borderHeight=heightOfBorder;
		borderWidth=widthOfBorder;
		this.width=width;
		this.height=height;
		this.rect=new Rectangle(width,height);
		loadWordLists();
	}
	
	public void drawCenteredString(Graphics g, int width, Slide currentSlide, boolean timeForDifferentWord) {

		String text=chooseWord(currentSlide,timeForDifferentWord);
		Font font=new Font("Arial Black", Font.BOLD, 60);
		Font prevFont=g.getFont();
		g.setFont(font);		
		
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = (rect.width - metrics.stringWidth(text)) / 2;
	    
	    //Account for corner slides width??
	    x+=borderWidth+width;
	    
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    
	    Color prevColor=g.getColor();
	    g.setColor(Color.WHITE);
	    //g.drawRect(borderHeight, borderWidth, rect.height, rect.width);
	    
	    // Draw the String
	    g.drawString(text, x, y);
	    g.setColor(prevColor);
	    
	    g.setFont(prevFont);
	}
	
	private String chooseWord(Slide slide,boolean isTimeForDifferentWord) {
		
		String theWord=null;
		
		if (isTimeForDifferentWord) normalWordListIndex++;
		theWord=normalWordList.get(normalWordListIndex);
		if (slide.getSlideType()==5)
		{
			char[] buffer=new char[theWord.length()];
			StringBuilder sb=new StringBuilder(theWord);
			//ArrayList list=createVowelWords(sb);
			//sb.indexOf(str);
		}
		else if (slide.getSlideType()==6)
		{
			StringBuilder sb=new StringBuilder(theWord);
			sb=sb.reverse();
			theWord=sb.toString();
		}
		
		return theWord;
	}

	private ArrayList createVowelWords(StringBuffer sb) {
		int index=0;
		ArrayList retVal=new ArrayList();
		int start=index;
		int end=index;
		
		while(!vowels.contains(Character.toString(sb.charAt(index))))
		{
			sb.substring(start, end);
		}
		
		return null;
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
		
		normalWordList.add("antediluvian");
		normalWordList.add("apocryphal");
		normalWordList.add("camaraderie");
		normalWordList.add("puerile");
		normalWordList.add("sanctimonious");
		normalWordList.add("vicissitude");
		normalWordList.add("amoebae");
		
		normalWordList.add("red");
		normalWordList.add("there");
		normalWordList.add("to");
		normalWordList.add("aisle");
		normalWordList.add("aisle");
		normalWordList.add("banned");
		normalWordList.add("haul");
		normalWordList.add("hare");
	}
	

}