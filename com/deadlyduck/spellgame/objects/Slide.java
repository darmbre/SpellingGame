package com.deadlyduck.spellgame.objects;

import java.awt.Color;
import java.util.Date;
import java.util.Random;

public class Slide {

	private Color color=null;
	private String text=null;	
	private Random randomFactory=new Random(new Date().getTime());
	
	public Slide(Color c)
	{
		this.color=c;
		this.text=selectText(c);
		
	}
	
	public Slide()
	{
		this.color=getRandomColor();
		this.text=selectText(this.color);

	}
	
	private String selectText(Color color)
	{
		String theText=null;
		if (color==Color.BLUE) theText="CAST A SPELL";
		else if (color==Color.RED) theText="WHAMMY";
		else if (color==Color.CYAN) theText="STRAIGHT SPELL";
		else if (color==Color.GREEN) theText="SPEED SPELL";
		else if (color==Color.PINK) theText="NO VOWELS";
		else if (color==Color.ORANGE) theText="BACKWARDS";
		else if (color==Color.MAGENTA) theText="HOMOPHONE";
		else theText="DEFAULT";
		
		return theText;
	}
	
	public Color getRandomColor()
	{
		Color retVal=Color.BLACK;
		int randomInt=randomFactory.nextInt(6);
		switch(randomInt)
		{
		case 0:
			retVal=Color.BLUE;
			break;

		case 1:
			retVal=Color.RED;
			break;
					
		case 2:
			retVal=Color.CYAN;
			break;
		
		case 3:
			retVal=Color.GREEN;
			break;
		
		case 4:
			retVal=Color.PINK;
			break;
		
		case 5:
			retVal=Color.ORANGE;
			break;
					
		default:
			retVal=Color.BLACK;
			break;
		}
		return retVal;
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
	
}
