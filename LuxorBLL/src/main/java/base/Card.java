package base;

import pokerEnums.*;

public class Card implements Comparable {
	private eRank cardRank;
	private eSuit cardSuit;
	private int iCardNumber;
	private boolean bWild;
	
	public Card(eRank cardRank, eSuit cardSuit, int iCardNumber) {
		super();
		this.cardRank = cardRank;
		this.cardSuit = cardSuit;
		this.iCardNumber = iCardNumber;
		this.bWild = false;
	}

	public eRank getCardRank() {
		return cardRank;
	}

	public eSuit getCardSuit() {
		return cardSuit;
	}

	public int getiCardNumber() {
		return iCardNumber;
	}

	public int compareTo(Object o){
		Card c = (Card) o;
		return c.cardRank.compareTo(this.cardRank);
	}

	public boolean isbWild() {
		return bWild;
	}

	public void setbWild(boolean bWild) {
		this.bWild = bWild;
	}
	
	

}
