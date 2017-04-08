package com.deadlyduck.spellgame.objects;

import java.awt.Color;
import java.util.Date;
import java.util.Random;

public class Slide {

	private int slideType=-1;
	private Color color=null;
	private String text=null;	
	private Random randomFactory=new Random(new Date().getTime());
	
	public Slide(Color c)
	{
		
		this.color=c;
		this.text=selectMyText();
		
	}
	
	private String selectMyText()
	{
		String theText=null;
		if (this.color==Color.BLUE)
		{
			slideType=1;
			theText="CAST A SPELL";
		}
		else if (color==Color.RED) 
		{
			slideType=2;
			theText="WHAMMY";
		}
		else if (color==Color.CYAN)
		{
			slideType=3;
			//theText="STRAIGHT SPELL";
			theText="PLAIN-O";
		}
		else if (color==Color.GREEN)
		{
			slideType=4;
			theText="HALF TIME";
		}
		else if (color==Color.PINK) 
		{
			slideType=5;
			theText="VOWEL-LESS";
		}
		else if (color==Color.ORANGE) 
		{
			slideType=6;
			theText="BACKWARDS";
		}
		else if (color==Color.DARK_GRAY) 
		{
			slideType=7;
			theText="COMEBACK";
		}
		else if (color==Color.LIGHT_GRAY) 
		{
			slideType=8;
			theText="LOUD MUSIC";
		}
		else if (color==Color.MAGENTA) 
		{
			slideType=9;
			theText="BLOWER";
		}
		else if (color==Color.GRAY) 
		{
			slideType=10;
			theText="TRIVIA";
		}
		else theText="DEFAULT";
		
		return theText;
	}
	


	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public int getSlideType() {
		return slideType;
	}

	public void setSlideType(int slideType) {
		this.slideType = slideType;
	}
	
}