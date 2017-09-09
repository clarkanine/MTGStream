package ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.Match;
import data.Player;

import java.awt.Dimension;
import java.util.ArrayList;

public class PlayerPool extends JList<Player> {
	DefaultListModel<Player> list;
	
	public PlayerPool()
	{
		super();
		list = new DefaultListModel<Player>();
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void setupPlayerPool(DefaultListModel<Player> players)
	{
		list = players;
		if(players != null) {
			this.setModel(list);
		}
	}
	
	public void reset()
	{
		list = new DefaultListModel<Player>();
		this.setModel(list);
	}
	
	public void addPlayer(Player player)
	{
		DefaultListModel<Player> updatedList = new DefaultListModel<Player>();
		
		int i;
		for(i = 0; i < this.getModel().getSize(); i++) {
			updatedList.addElement(this.getModel().getElementAt(i));
		}
		updatedList.addElement(player);
		this.setModel(updatedList);
		list = updatedList;
	}
}
