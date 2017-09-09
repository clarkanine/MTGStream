package data;

import enums.PlayerStatus;

public class Match {
	private Player p1;
	private Player p2;
	private int p1Wins;
	private int p2Wins;
	
	public Match() 
	{
		
	}
	
	public void setupMatch(Player p1, Player p2)
	{
		this.p1 = p1;
		this.p2 = p2;
		
		p1Wins = p2Wins = 0;
	}
	
	public String toString()
	{
		String formatString = String.format("%5s vs %5s %d-%d", p1.toString(), p2.toString(), p1.getPoints(), p2.getPoints());
		return formatString;
	}
	
	public Player getP1() {
		return p1;
	}
	
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	
	public Player getP2() {
		return p2;
	}
	
	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public int getP1Wins() {
		return p1Wins;
	}

	public void setP1Wins(int p1Wins) {
		this.p1Wins = p1Wins;
	}

	public int getP2Wins() {
		return p2Wins;
	}

	public void setP2Wins(int p2Wins) {
		this.p2Wins = p2Wins;
	}
	
	public String generateMatchStanding()
	{
		String p1str = "";
		String p2str = "";
		
		if(p1.getPlayerStatus() == PlayerStatus.Bye){
			p1str = "***Bye***";
		}
		else {
			p1str += p1.getFirstName() + " " + p1.getLastName() + " " + p1.getRecord();
		}
		if(p2.getPlayerStatus() == PlayerStatus.Bye){
			p2str = "***Bye***";
		}
		else {
			p2str += p2.getFirstName() + " " + p2.getLastName() + " " + p2.getRecord();
		}
		
		return p1str + " vs " + p2str + "    |    ";
	}
}
