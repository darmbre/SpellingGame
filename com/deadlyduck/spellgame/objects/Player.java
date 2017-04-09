package com.deadlyduck.spellgame.objects;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.deadlyduck.spellgame.SpellingGame;

public class Player {
	
	Clip spinClip=null;
	Clip selectClip=null;
	Clip whammy=null;
	
	
	public Player() {
	    try {
	        AudioInputStream ais1 = AudioSystem.getAudioInputStream(new File(SpellingGame.BASE_DIR+"spin.wav").getAbsoluteFile());
	        spinClip = AudioSystem.getClip();
	        spinClip.open(ais1);
	        
	        AudioInputStream ais2 = AudioSystem.getAudioInputStream(new File(SpellingGame.BASE_DIR+"select.wav").getAbsoluteFile());
	        selectClip = AudioSystem.getClip();
	        selectClip.open(ais2);
	        
	        AudioInputStream ais3 = AudioSystem.getAudioInputStream(new File(SpellingGame.BASE_DIR+"whammy.wav").getAbsoluteFile());
	        whammy = AudioSystem.getClip();
	        whammy.open(ais3);
	        
	        
	        

	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public void startSpinClip()
	{
		if (!spinClip.isActive())
		{
			spinClip.setFramePosition(0);
			spinClip.loop(Clip.LOOP_CONTINUOUSLY);
			//spinClip.start();
		}
		
	}
	
	public void stopSpinClip()
	{
		try
		{
			if (spinClip.isActive())
			{
				spinClip.stop();

			}
		}
		catch(Exception e)
		{
			System.out.println("Error stopping clip");
		}
	}
	
	public void playSelectClip()
	{
		if (!selectClip.isActive())
		{
			selectClip.setFramePosition(0);
			selectClip.start();
		}
		
	}

	public void playWhammyClip()
	{
		whammy.setFramePosition(0);
		whammy.start();
	}
	
}
