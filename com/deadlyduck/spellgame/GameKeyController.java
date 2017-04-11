package com.deadlyduck.spellgame;

import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Random;

public class GameKeyController implements KeyEventDispatcher {

    private static int state=0;
    
    /*
     * 0 = init
     * 
     * 1 = stopped
     * 2 = spinning
     * 3 = displaying word
     */

	public boolean dispatchKeyEvent(KeyEvent e) {
    	

        assert EventQueue.isDispatchThread();
        Random r=new Random(new Date().getTime());

        int kc = e.getKeyCode();

        if (e.getID() == KeyEvent.KEY_PRESSED) {
        	
        	// Press '<SPACE BAR>' to Spin/Stop the Board
        	if (kc==KeyEvent.VK_F1)
        	{
        		// Start a new game and get the board spinning
        		if (state<=10)
        		{
        			state=20;
        		} else if (state==20)
        		{
        			state=40;
        		}
        		
        	}
        	
        	if (kc==KeyEvent.VK_SPACE)
        	{
        		// In the process of a game (not demo mode)
        		if (state>20)
        		{
        			//If Spinning - stop the board
        			if (state==30)
        			{
        				state=40;
        				
        			}
        			//If Stopped - spin the board
        			else if (state==40 || state==60)
        			{
        				state=30;
        			}        			
        		}

        		
        	}
        	
        	// Press 'W' to display a new word
        	if (kc==KeyEvent.VK_W)
        	{
        		if (this.state==40)
        		{
        			state=50;
        		}
        		
        	}
        	
        	// Press 'W' to display a new word
        	if (kc==KeyEvent.VK_M)
        	{
        		if (this.state==60)
        		{
        			
        			//Randomize between all choices
        			int videoNum=r.nextInt(3)+100;
        			state=videoNum;
        			System.out.println("random="+videoNum);
        			r=null;

        		}
        		
        	}        	

        }


        if (e.getID() == KeyEvent.KEY_RELEASED) {

        }

        return false;
    }

    public static int getState() {
		return state;
	}

	public static void setState(int state) {
		GameKeyController.state = state;
	}


}