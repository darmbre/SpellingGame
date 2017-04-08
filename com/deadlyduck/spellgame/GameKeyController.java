package com.deadlyduck.spellgame;

import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

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

        int kc = e.getKeyCode();

        if (e.getID() == KeyEvent.KEY_PRESSED) {
        	
        	// Press '<SPACE BAR>' to Spin/Stop the Board
        	if (kc==KeyEvent.VK_F1)
        	{
        		// Start a new game and get the board spinning
        		if (state<2)
        		state=2;
        		
        	}
        	
        	if (kc==KeyEvent.VK_SPACE)
        	{
        		// In the process of a game (not demo mode)
        		if (state>2)
        		{
        			//If Spinning - stop the board
        			if (state==3)
        			{
        				state=4;
        				
        			}
        			//If Stopped - spin the board
        			else if (state==4 || state==6)
        			{
        				state=3;
        			}        			
        		}

        		
        	}
        	
        	// Press 'W' to display a new word
        	if (kc==KeyEvent.VK_W)
        	{
        		if (this.state==4)
        		{
        			state=5;
        		}
        		
        	}
        	
        	// Press 'W' to display a new word
        	if (kc==KeyEvent.VK_M)
        	{
        		if (this.state==6)
        		{
        			state=100;
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