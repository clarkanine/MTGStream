package streamsrc;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.CardDisplay;

public class MTGListListener implements ListSelectionListener
{
    MTGCardFinder data;
    CardDisplay cardDisplay;
    
    public MTGListListener(MTGCardFinder t)
    {
        data = t;
    }
    
    public MTGListListener(CardDisplay cardDisplay)
    {
    	this.cardDisplay = cardDisplay;
    }
    
    public void valueChanged(ListSelectionEvent e)
    {
        if(!e.getValueIsAdjusting() )
        	if( cardDisplay != null
        		&& cardDisplay.cardlist != null
        		&& cardDisplay.cardlist.getSelectedValue() != null
        		&& cardDisplay.cardlist.getSelectedValue().getSource() != null)
        	{
        		cardDisplay.updateImage( cardDisplay.cardlist.getSelectedValue().getSource() );
        	}
    }
    
}