package com.deadlyduck.spellgame.objects;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import com.deadlyduck.spellgame.SpellingGame;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

public class Viewer 
{
	private int viewerX=0;
	private int viewerY=0;
	private int viewerWidth=0;
	private int viewerHeight=0;
	private Rectangle rect=null;
	private EmbeddedMediaPlayer mediaPlayer=null;
	private Canvas canvas=null;
	
    public ArrayList<String> normalWordList=new ArrayList<String>();
    
    int normalWordListIndex=-1;
    
    private static String vowels="aeiouyAEIOUY";

	
	public Viewer(int viewerY, int viewerX, int viewerWidth, int viewerHeight)
	{
		//Get the word list
		File file= new File("C:\\dev\\words.properties");
		Properties properties=null;
		try {
			FileInputStream fileInput = new FileInputStream(file);
			properties = new Properties();
			properties.load(fileInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration<Object> enuKeys = properties.keys();
		while (enuKeys.hasMoreElements())
		{
			normalWordList.add((String)enuKeys.nextElement());
		}

		this.viewerX=viewerX;
		this.viewerY=viewerY;
		this.viewerWidth=viewerWidth;
		this.viewerHeight=viewerHeight;
		this.rect=new Rectangle(viewerWidth,viewerHeight);
		createVideoPlayer();
		//loadWordLists();
	}
	
	public void drawCenteredString(Graphics g, int width, Slide currentSlide, boolean timeForDifferentWord, String customWord) {

		ArrayList words=chooseWord(currentSlide,timeForDifferentWord, customWord);
		String text=null;
		Font font=new Font("Arial Black", Font.BOLD, 60);
		Font prevFont=g.getFont();
		g.setFont(font);		
		Color prevColor=g.getColor();
	    FontMetrics metrics = g.getFontMetrics(font);
	    int yoffset=0;
	    
	    for (int i=0;i<words.size();i++)
	    {
	    	text=(String) words.get(i);
		    int x = (rect.width - metrics.stringWidth(text)) / 2;
		    
		    //Account for corner slides width??
		    x+=viewerY+width;
		    
		    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		    int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent() + yoffset;
		    // Set the font
		    
		    g.setColor(Color.WHITE);
		    	    
		    // Draw the String
		    g.drawString(text, x, y);
		    yoffset+=(metrics.getHeight()*2);
	    }
	    g.setColor(prevColor);
	    
	    g.setFont(prevFont);
	}
	
	private ArrayList chooseWord(Slide slide,boolean isTimeForDifferentWord, String customWord) {
		
		String theWord=null;
		SpellingGame.TIME_LIMIT=SpellingGame.SLOW_TIME;
		ArrayList retVal=new ArrayList(1);
		
		if (customWord!=null)
		{
			theWord=customWord;
		}
		else
		{
			if (isTimeForDifferentWord && customWord==null) 
				normalWordListIndex++;
			theWord=normalWordList.get(normalWordListIndex);
		}
		
		if (slide.getSlideType()==4)
		{
			SpellingGame.TIME_LIMIT=SpellingGame.FAST_TIME;
		} else
		if (slide.getSlideType()==5)
		{
			//theWord=theWord.replaceAll("[aeiouyAEIOUY](?!\\b)", "");
			String disemvoweledWord=theWord.replaceAll("[aeiouyAEIOUY]", "");
			retVal.add(theWord);
			theWord="("+disemvoweledWord+")";
		}
		else if (slide.getSlideType()==6)
		{
			retVal.add(theWord);
			
			StringBuilder sb=new StringBuilder(theWord);
			sb=sb.reverse();
			theWord=sb.toString();
		}
		retVal.add(theWord);
		
		return retVal;
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
		g.drawRect(viewerY, viewerX, viewerWidth, viewerHeight);
		g.setColor(prevColor);
	}
	
	public void loadWordLists()
	{
		normalWordList.add("amoebae");
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
		
		
		normalWordList.add("red");
		normalWordList.add("there");
		normalWordList.add("to");
		normalWordList.add("aisle");
		normalWordList.add("aisle");
		normalWordList.add("banned");
		normalWordList.add("haul");
		normalWordList.add("hare");
	}
	
	private void createVideoPlayer()
	{
		canvas = new Canvas();
		System.out.println("ViewerY="+viewerY);
		System.out.println("ViewerX="+viewerX);
		canvas.setBounds(viewerX, viewerY, viewerHeight, viewerWidth);
		//canvas.setBounds(this.rect);
		mediaPlayer=null;
				
		NativeLibrary.addSearchPath("libvlc", "C:/dev/apps/vlc");
		
		
		MediaPlayerFactory factory = new MediaPlayerFactory ();
		mediaPlayer = factory.newEmbeddedMediaPlayer ();
		mediaPlayer.setRepeat ( false );
		mediaPlayer.setEnableKeyInputHandling ( false );
		mediaPlayer.setEnableMouseInputHandling ( false );
	
		CanvasVideoSurface videoSurface = factory.newVideoSurface ( canvas );
		mediaPlayer.setVideoSurface ( videoSurface );
		//mediaPlayer.playMedia ( "/media/path/" );
	}
	
	public Canvas getCanvas()
	{
		return this.canvas;
	}
	
	public EmbeddedMediaPlayer getVideoPlayer()
	{
		return this.mediaPlayer;
	}
	
	public void setVideoVisible(boolean isVisible)
	{
		this.canvas.setVisible(isVisible);
	}
	

}