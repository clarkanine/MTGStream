package streamsrc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import org.json.JSONException;

import ui.PoolTab;

public class MTGPoolButtonListener implements ActionListener{

	private PoolTab poolTab;

	public MTGPoolButtonListener(PoolTab poolTab)
	{
		this.poolTab = poolTab;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("add_player") ){
        	poolTab.addPlayer();
        }
        
        else if(e.getActionCommand().equals("reset_participants") )
        {
        	poolTab.resetParticipants();
        }
        else if(e.getActionCommand().equals("reset_match_pairings") )
        {
            poolTab.resetMatchPairings();
        }
        else if(e.getActionCommand().equals("pair"))
		{
			poolTab.addPairToList();
		}
        else if(e.getActionCommand().equals("confirm_selection"))
		{
			poolTab.confirmPlayerSelection();
		}
        else if(e.getActionCommand().equals("save_players"))
		{
			try {
				poolTab.savePlayers();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        else if(e.getActionCommand().equals("set_active_match"))
		{
			try {
				poolTab.saveActivePlayerData();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        else if(e.getActionCommand().equals("generate_standings"))
		{
			poolTab.generateStandings();
		}
	}

}
