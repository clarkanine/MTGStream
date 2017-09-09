package data;

import java.util.Comparator;
import enums.PlayerStatus;

public class Player implements Comparable<Player>{
	private String firstName;
	private String lastName;
	private int wins;
	private int losses;
	private int draws;
	private int health;
	private int poison;
	private String deck;
	private PlayerStatus status;
	
	public Player()
	{
		status = PlayerStatus.Unpaired;
	}

	@Override
	public int compareTo(Player otherPlayer) {
		return this.getPoints() - otherPlayer.getPoints();
	}
	
	public String toString()
	{
		return firstName + " " + lastName;
	}
	
	public int getPoints()
	{
		return 3 * wins + draws;
	}
	
	public void resetScore()
	{
		wins = losses = draws = 0;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getDraws() {
		return draws;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public String getDeck() {
		return deck;
	}

	public void setDeck(String deck) {
		this.deck = deck;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPoison() {
		return poison;
	}

	public void setPoison(int poison) {
		this.poison = poison;
	}

	public PlayerStatus getPlayerStatus() {
		return status;
	}

	public void setPlayerStatus(PlayerStatus status) {
		this.status = status;
	}
	
	public String getRecord()
	{
		String str = "";
		str += wins + "-" + losses;
		if(draws > 0) {
			str += "-" + draws;
		}
		return str;
	}
}
