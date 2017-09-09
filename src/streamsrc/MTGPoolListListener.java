package streamsrc;


import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MTGPoolListListener implements ListSelectionListener
{
    MTGCardFinder data;
    
    public MTGPoolListListener(MTGCardFinder t)
    {
        data = t;
    }
    
    public void valueChanged(ListSelectionEvent e)
    {
        if(!e.getValueIsAdjusting() )
        {
         //   data.updateImage( data.cardlist.getSelectedValue().getSource() );
        }
    }
    
}