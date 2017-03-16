package com.deadlyduck.spellgame;

import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class GameKeyController implements KeyEventDispatcher {

    private static int state=0;
    /*
     * 0 = init
     * 1 = stopped
     * 2 = spinning
     * 3 = displaying word
     */

	public boolean dispatchKeyEvent(KeyEvent e) {
    	

        assert EventQueue.isDispatchThread();

        int kc = e.getKeyCode();

        if (e.getID() == KeyEvent.KEY_PRESSED) {
        	if (kc==KeyEvent.VK_S)
        	{
        		// Stop the board 
        		state=1;
        	}
        	
        	if (kc==KeyEvent.VK_G)
        	{
        		// Spinning
        		state=2;
        	}
        	
        	
        	if (kc==KeyEvent.VK_W)
        	{
        		// Display Word
        		if (state==1)
        		{
        			state=3;
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

