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
			theText="STRAIGHT SPELL";
		}
		else if (color==Color.GREEN)
		{
			slideType=4;
			theText="SPEED SPELL";
		}
		else if (color==Color.PINK) 
		{
			slideType=5;
			theText="NO VOWELS";
		}
		else if (color==Color.ORANGE) 
		{
			slideType=6;
			theText="BACKWARDS";
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