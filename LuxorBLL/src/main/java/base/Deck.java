package base;

import java.util.ArrayList;
import java.util.Collections;

import exceptions.DeckException;
import base.Card;
import pokerEnums.*;

public class Deck {

	private ArrayList<Card> deckOfCards = new ArrayList<Card>();

	public Deck() {
		int cardNumber = 1;
		for (eRank rank : eRank.values()) {
			for (eSuit suit : eSuit.values()) {
				if ((rank != eRank.JOKER) && (suit != eSuit.JOKER)) {
					deckOfCards.add(new Card(rank, suit, cardNumber++));
				}
			}
		}
		Collections.shuffle(this.deckOfCards);
	}

	public Deck(int nbrJokers){
		this();
		for(int i = 0; i < nbrJokers; i++){
			this.deckOfCards.add(new Card(eRank.JOKER, eSuit.JOKER, 53));
		}
		Collections.shuffle(this.deckOfCards);
	}
	
	public Deck (int NbrOfJokers, ArrayList<Card> wilds)
	{
		this(NbrOfJokers);
		
		for(Card wild : wilds){
			for(Card card : deckOfCards){
				if (card == wild){
					card.setbWild(true);
				}
			}
		}
		
	}
	
	public void shuffle() {
		Collections.shuffle(this.deckOfCards);
	}

	private int GetDeckSize() {
		return deckOfCards.size();
	}

	public Card Draw() throws DeckException {
		if (deckOfCards.size() == 0) {
			throw new DeckException(this);
		}
		return deckOfCards.remove(0);
	}
}
