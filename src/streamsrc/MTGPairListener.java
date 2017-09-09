package streamsrc;

import java.awt.event.ActionListener;

import ui.PoolTab;

import java.awt.event.ActionEvent;

public class MTGPairListener implements ActionListener{

	MTGCardFinder myRoot;
	PoolTab poolTab;
	
	public MTGPairListener(MTGCardFinder root)
	{
		myRoot = root;
	}
	
	public MTGPairListener(PoolTab poolTab)
	{
		this.poolTab = poolTab;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("pair"))
		{
//			myRoot.addPairToList();
			poolTab.addPairToList();
		}
	}
}
