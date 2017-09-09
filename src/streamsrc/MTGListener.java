package streamsrc;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;

import ui.CardDisplay;
import ui.ScoreDisplay;

import java.awt.event.ActionEvent;

public class MTGListener implements ActionListener
{
    MTGCardFinder data;
    CardDisplay cardDisplay;
    ScoreDisplay scoreDisplay;
    
    static MTGListener instance = null;
    
    private MTGListener()
    {
    }
    
    public static MTGListener getInstance()
    {
    	if(instance == null) {
    		instance = new MTGListener();
    	}
    	
    	return instance;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("update") ) {
        	try {
				scoreDisplay.update();
				cardDisplay.update();
			} catch (FileNotFoundException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
        else if(e.getActionCommand().equals("search") )
        {
            if(cardDisplay.cardlist != null)
            {
                cardDisplay.remove(cardDisplay.cardlist);
            }
            cardDisplay.search();
        }
        else if(e.getActionCommand().equals("reset_life") )
        {
            if(scoreDisplay != null) {
            	scoreDisplay.resetLife();
            }
        }
        else if(e.getActionCommand().equals("reset_all") )
        {
            if(scoreDisplay != null) {
            	scoreDisplay.resetLife();
            	scoreDisplay.resetScores();
            }
        }
        
    }
    
    public void setScoreDisplay(ScoreDisplay scoreDisplay)
    {
    	this.scoreDisplay = scoreDisplay;
    }
    
    public void setCardDisplay(CardDisplay cd)
    {
    	cardDisplay = cd;
    }
    
}