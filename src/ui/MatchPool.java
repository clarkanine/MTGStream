package ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import data.Match;
import enums.PlayerStatus;

public class MatchPool extends JList<Match> {
	DefaultListModel<Match> list;
	
	public MatchPool()
	{
		super();
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list = new DefaultListModel<Match>();
	}
	
	public void setupMatchPool(DefaultListModel<Match> matches)
	{
		list = matches;
		this.setModel(matches);
	}
	
	public void reset()
	{
		for(int i = 0; i < list.getSize(); i++){
			list.getElementAt(i).getP1().setPlayerStatus(PlayerStatus.Unpaired);
			list.getElementAt(i).getP2().setPlayerStatus(PlayerStatus.Unpaired);
		}
		list = new DefaultListModel<Match>();
		this.setModel(list);
	}
	
	public void addMatch(Match m)
	{
		DefaultListModel<Match> updatedList = new DefaultListModel<Match>();
		
		int i;
		for(i = 0; i < list.size(); i++) {
			updatedList.addElement(list.getElementAt(i));
		}
		updatedList.addElement(m);
		this.setModel(updatedList);
		list = updatedList;
	}
}
