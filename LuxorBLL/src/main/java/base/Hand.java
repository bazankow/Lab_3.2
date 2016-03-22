package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import exceptions.DeckException;
import exceptions.HandException;
import exceptions.exHand;
import base.Hand;
import base.Card;
import pokerEnums.eCardNo;
import pokerEnums.eHandScore;
import pokerEnums.eRank;

public class Hand implements Comparable {
	private ArrayList<Card> cardsInHand;
	private eHandScore Handscore;
	private int highHand;
	private int lowHand;
	private ArrayList<Card> kickers;
	private boolean naturalHand;

	public Hand() {
		this.cardsInHand = new ArrayList<Card>();
		this.kickers = new ArrayList<Card>();
		this.highHand = 0;
		this.lowHand = 0;
		this.naturalHand = true;
	}

	private Hand(ArrayList<Card> h) {
		this();
		for (Card card : h) {
			this.cardsInHand.add(card);
		}
	}

	public Hand AddCard(Deck d) throws DeckException {
		cardsInHand.add(d.Draw());
		return this;
	}

	public Hand AddCard(Card c) {
		cardsInHand.add(c);
		return this;
	}

	public ArrayList<Card> getCardsInHand() {
		return cardsInHand;
	}

	public void setCardsInHand(ArrayList<Card> cardsInHand) {
		this.cardsInHand = cardsInHand;
	}

	public eHandScore getHandscore() {
		return Handscore;
	}

	public void setHandscore(eHandScore handscore) {
		Handscore = handscore;
	}

	public int getHighHand() {
		return highHand;
	}

	public void setHighHand(int highHand) {
		this.highHand = highHand;
	}

	public int getLowHand() {
		return lowHand;
	}

	public void setLowHand(int lowHand) {
		this.lowHand = lowHand;
	}

	public ArrayList<Card> getKickers() {
		return kickers;
	}

	public void setKickers(ArrayList<Card> kickers) {
		this.kickers = kickers;
	}

	public boolean isNaturalHand() {
		return naturalHand;
	}

	public void setNaturalHand(boolean naturalHand) {
		this.naturalHand = naturalHand;
	}

	public void sortHand() {
		Collections.sort(this.cardsInHand);
	}

	public int compareTo(Object o) {
		Hand h = (Hand) o;
		return h.Handscore.compareTo(this.Handscore);
	}

	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.getHandscore().getiHandScore() - h1.getHandscore().getiHandScore();

			if (result != 0) {
				return result;
			}

			result = h2.getHighHand() - h1.getHighHand();
			if (result != 0) {
				return result;
			}

			result = h2.getLowHand() - h1.getLowHand();
			if (result != 0) {
				return result;
			}

			if (h2.getKickers().size() > 0) {
				if (h1.getKickers().size() > 0) {
					result = h2.getKickers().get(eCardNo.FirstCard.getCardNo()).getCardRank().getiCardNumber()
							- h1.getKickers().get(eCardNo.FirstCard.getCardNo()).getCardRank().getiCardNumber();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getKickers().size() > 1) {
				if (h1.getKickers().size() > 1) {
					result = h2.getKickers().get(eCardNo.SecondCard.getCardNo()).getCardRank().getiCardNumber()
							- h1.getKickers().get(eCardNo.SecondCard.getCardNo()).getCardRank().getiCardNumber();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getKickers().size() > 2) {
				if (h1.getKickers().size() > 2) {
					result = h2.getKickers().get(eCardNo.ThirdCard.getCardNo()).getCardRank().getiCardNumber()
							- h1.getKickers().get(eCardNo.ThirdCard.getCardNo()).getCardRank().getiCardNumber();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getKickers().size() > 3) {
				if (h1.getKickers().size() > 3) {
					result = h2.getKickers().get(eCardNo.FourthCard.getCardNo()).getCardRank().getiCardNumber()
							- h1.getKickers().get(eCardNo.FourthCard.getCardNo()).getCardRank().getiCardNumber();
				}
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}
	};

	public static Hand evaluateHand(Hand h) throws HandException {
		h.sortHand();
		int nbrJokers = 0;
		for (Card card : h.getCardsInHand()) {
			if (card.getCardRank() == eRank.JOKER) {
				nbrJokers += 1;
			}
		}
		System.out.println(nbrJokers);
		if (h.getCardsInHand().size() != 5) {
			throw new HandException(h);
		}

		if (nbrJokers == 5) {
			h.setNaturalHand(false);
			h.setHandscore(eHandScore.JOKERROYALFLUSH);
			return h;
		}

		if (nbrJokers == 4) {
			h.setNaturalHand(false);
			if (((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank().getiCardNumber()) >= eRank.TEN
					.getiCardNumber())
					&& ((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank()
							.getiCardNumber()) <= eRank.ACE.getiCardNumber())) {
				h.setHandscore(eHandScore.JOKERROYALFLUSH);
				return h;
			} else {
				h.setHandscore(eHandScore.STRAIGHTFLUSH);
				h.setHighHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank().getiCardNumber() + 4);
				return h;
			}
		}

		if (nbrJokers == 3) {
			h.setNaturalHand(false);

			int diff1 = (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank().getiCardNumber())
					- (h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank().getiCardNumber());

			if (h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardSuit().getiCardSuit() == h.getCardsInHand()
					.get(eCardNo.FourthCard.getCardNo()).getCardSuit().getiCardSuit()) {
				if (((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank().getiCardNumber())) != ((h
						.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank().getiCardNumber()))) {
					if (((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank()
							.getiCardNumber()) >= eRank.TEN.getiCardNumber())
							&& ((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank()
									.getiCardNumber()) <= eRank.ACE.getiCardNumber())) {
						h.setHandscore(eHandScore.JOKERROYALFLUSH);
						return h;
					}
					if (diff1<= 4){
						h.setHandscore(eHandScore.STRAIGHTFLUSH);
						return h;
					}

				}

			}


			if (((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank().getiCardNumber()) == ((h
					.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank().getiCardNumber())))) {
				h.setHandscore(eHandScore.FIVEOFAKIND);
				return h;
			} else {
				h.setHandscore(eHandScore.FOUROFAKIND);
				return h;
			}
		}

		if (nbrJokers == 2) {
			h.setHandscore(eHandScore.HIGHCARD);
			for (eRank rank1 : eRank.values()) {
				for (eRank rank2 : eRank.values()) {
					Hand tempHand = new Hand(h.getCardsInHand());
					tempHand.getCardsInHand().remove(0);
					tempHand.getCardsInHand().remove(0);
					tempHand.getCardsInHand().add(
							new Card(rank1, h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardSuit(), 0));
					tempHand.getCardsInHand().add(
							new Card(rank2, h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardSuit(), 0));
					tempHand.sortHand();

					if (Hand.isJokerRoyalFlush(tempHand) || Hand.isStraightFlush(tempHand)
							|| Hand.isFiveOfAKind(tempHand) || Hand.isFourOfAKind(tempHand)
							|| Hand.isFullHouse(tempHand) || Hand.isFlush(tempHand) || Hand.isStraight(tempHand)
							|| Hand.isThreeOfAKind(tempHand) || Hand.isTwoPair(tempHand) || Hand.isOnePair(tempHand)
							|| Hand.isHighCard(tempHand)) {
					}

					if (tempHand.getHandscore().getiHandScore() >= h.getHandscore().getiHandScore()) {
						h.setHandscore(tempHand.getHandscore());
						if (tempHand.getHighHand() > h.getHighHand()) {
							h.setHighHand(tempHand.getHighHand());
						}
						if (tempHand.getLowHand() > h.getLowHand()) {
							h.setLowHand(tempHand.getLowHand());
						}
						h.setKickers(tempHand.getKickers());
					}
					tempHand = null;
				}
			}
			return h;
		}

		/*
		 * if(nbrJokers == 2){ h.setNaturalHand(false);
		 * 
		 * int diff =
		 * (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank()
		 * .getiCardNumber())-
		 * (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).getCardRank().
		 * getiCardNumber());
		 * 
		 * int diff2 =
		 * (h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank().
		 * getiCardNumber())-
		 * (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).getCardRank().
		 * getiCardNumber());
		 * 
		 * int diff3 =
		 * (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank()
		 * .getiCardNumber())-
		 * (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).getCardRank().
		 * getiCardNumber());
		 * 
		 * for (eRank rank1: eRank.values()){ h.sortHand(); for (eRank rank2:
		 * eRank.values()){ Hand tempHand = new Hand(h.getCardsInHand());
		 * tempHand.getCardsInHand().remove(0);
		 * tempHand.getCardsInHand().add(new Card(rank2,
		 * h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardSuit(),
		 * 0)); tempHand.getCardsInHand().remove(0);
		 * tempHand.getCardsInHand().add(new Card(rank2,
		 * h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).getCardSuit(),0
		 * )); tempHand.sortHand();
		 * 
		 * add(rank1, setCardSuit(), 0); add(rank2, setCardSuit(), 0);
		 * 
		 * if(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardSuit(
		 * ).getiCardSuit() ==
		 * h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardSuit().
		 * getiCardSuit() ||
		 * h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardSuit().
		 * getiCardSuit() ==
		 * h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardSuit().
		 * getiCardSuit()){
		 * if(((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).
		 * getCardRank().getiCardNumber())) !=
		 * ((h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank(
		 * ).getiCardNumber()))){
		 * if(((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).
		 * getCardRank().getiCardNumber()) >= eRank.TEN .getiCardNumber()) &&
		 * ((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank()
		 * .getiCardNumber()) <= eRank.ACE.getiCardNumber())){
		 * h.setHandscore(eHandScore.JOKERROYALFLUSH); return h; } else
		 * if((diff1 <=4) || (diff2 <= 5) || (diff3 <= 3)){
		 * h.setHandscore(eHandScore.STRAIGHTFLUSH);
		 * h.setHighHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).
		 * getCardRank().getiCardNumber() + 4); return h; } else
		 * if(((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).
		 * getCardRank().getiCardNumber())==
		 * ((h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank(
		 * ).getiCardNumber()))) &&
		 * ((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank()
		 * .getiCardNumber())==
		 * ((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).getCardRank()
		 * .getiCardNumber())))){ h.setHandscore(eHandScore.FIVEOFAKIND); return
		 * h; } else if(((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).
		 * getCardRank().getiCardNumber())==
		 * ((h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).getCardRank(
		 * ).getiCardNumber()))) &&
		 * ((h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardRank()
		 * .getiCardNumber())!=
		 * ((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).getCardRank()
		 * .getiCardNumber())))){ h.setHandScore(eHandScore.FOUROFAKIND); return
		 * h; } } }
		 * 
		 * }
		 */

		if (nbrJokers == 1) {
			h.setHandscore(eHandScore.HIGHCARD);
			for (eRank rank : eRank.values()) {
				Hand tempHand = new Hand(h.getCardsInHand());
				tempHand.getCardsInHand().remove(0);
				tempHand.getCardsInHand()
						.add(new Card(rank, h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).getCardSuit(), 0));
				tempHand.sortHand();

				if (Hand.isJokerRoyalFlush(tempHand) || Hand.isStraightFlush(tempHand) || Hand.isFiveOfAKind(tempHand)
						|| Hand.isFourOfAKind(tempHand) || Hand.isFullHouse(tempHand) || Hand.isFlush(tempHand)
						|| Hand.isStraight(tempHand) || Hand.isThreeOfAKind(tempHand) || Hand.isTwoPair(tempHand)
						|| Hand.isOnePair(tempHand) || Hand.isHighCard(tempHand)) {
				}

				if (tempHand.getHandscore().getiHandScore() >= h.getHandscore().getiHandScore()) {
					h.setHandscore(tempHand.getHandscore());
					if (tempHand.getHighHand() > h.getHighHand()) {
						h.setHighHand(tempHand.getHighHand());
					}
					if (tempHand.getLowHand() > h.getLowHand()) {
						h.setLowHand(tempHand.getLowHand());
					}
					h.setKickers(tempHand.getKickers());
				}
				tempHand = null;
			}
			return h;
		}

		if (nbrJokers == 0) {
			if (Hand.isNaturalRoyalFlush(h) || Hand.isStraightFlush(h) || Hand.isFiveOfAKind(h) || Hand.isFourOfAKind(h)
					|| Hand.isFullHouse(h) || Hand.isFlush(h) || Hand.isStraight(h) || Hand.isThreeOfAKind(h)
					|| Hand.isTwoPair(h) || Hand.isOnePair(h) || Hand.isHighCard(h)) {

			}

		}
		return h;
	}

	public static Hand PickBestHand(ArrayList<Hand> Hands) throws exHand, HandException {
		for (Hand h : Hands) {
			evaluateHand(h);
		}
		Collections.sort(Hands, HandRank);
		if (Hands.get(0).getCardsInHand() == Hands.get(1).getCardsInHand()) {
			throw new exHand(Hands.get(0), Hands.get(1));
		} else {
			return Hands.get(0);
		}
	}

	public static boolean isJokerRoyalFlush(Hand h) {
		int[] values = { 14, 13, 12, 11, 10 };
		for (int x = 0; x < 5; x++) {
			if (h.getCardsInHand().get(x).getCardRank().getiCardNumber() != values[x]) {
				return false;
			}
			if (h.getCardsInHand().get(x).getCardSuit().getiCardSuit() != h.getCardsInHand().get(0).getCardSuit()
					.getiCardSuit()) {
				return false;
			}
		}
		h.setHandscore(eHandScore.JOKERROYALFLUSH);
		return true;
	}

	public static boolean isNaturalRoyalFlush(Hand h) {
		int[] values = { 14, 13, 12, 11, 10 };
		for (int x = 0; x < 5; x++) {
			if (h.getCardsInHand().get(x).getCardRank().getiCardNumber() != values[x]) {
				return false;
			}
			if (h.getCardsInHand().get(x).getCardSuit().getiCardSuit() != h.getCardsInHand().get(0).getCardSuit()
					.getiCardSuit()) {
				return false;
			}
		}
		h.setHandscore(eHandScore.ROYALFLUSH);
		return true;
	}

	public static boolean isStraightFlush(Hand h) {
		int lc = h.getCardsInHand().get(4).getCardRank().getiCardNumber();
		int[] values = { lc + 4, lc + 3, lc + 2, lc + 1, lc };
		for (int x = 0; x < 5; x++) {
			if (h.getCardsInHand().get(x).getCardRank().getiCardNumber() != values[x]) {
				return false;
			}
			if (h.getCardsInHand().get(x).getCardSuit().getiCardSuit() != h.getCardsInHand().get(0).getCardSuit()
					.getiCardSuit()) {
				return false;
			}
		}
		h.setHandscore(eHandScore.STRAIGHTFLUSH);
		h.setHighHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
		return true;
	}

	public static boolean isFiveOfAKind(Hand h) {
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).getCardRank().getiCardNumber() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).getCardRank().getiCardNumber()) {
			h.setHandscore(eHandScore.FIVEOFAKIND);
			return true;
		}
		return false;
	}

	public static boolean isFourOfAKind(Hand h) {
		if (h.getCardsInHand().get(0).getCardRank().getiCardNumber() == h.getCardsInHand().get(3).getCardRank()
				.getiCardNumber()) {
			h.setHandscore(eHandScore.FOUROFAKIND);
			h.setHighHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(4));
			h.setKickers(kickers);
			return true;

		} else if (h.getCardsInHand().get(1).getCardRank().getiCardNumber() == h.getCardsInHand().get(4).getCardRank()
				.getiCardNumber()) {
			h.setHandscore(eHandScore.FOUROFAKIND);
			h.setHighHand(h.getCardsInHand().get(1).getCardRank().getiCardNumber());
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(0));
			h.setKickers(kickers);
			return true;
		}
		return false;
	}

	public static boolean isFullHouse(Hand h) {
		if ((h.getCardsInHand().get(0).getCardRank().getiCardNumber() == h.getCardsInHand().get(2).getCardRank()
				.getiCardNumber())
				&& (h.getCardsInHand().get(3).getCardRank().getiCardNumber() == h.getCardsInHand().get(4).getCardRank()
						.getiCardNumber())) {
			h.setHandscore(eHandScore.FULLHOUSE);
			h.setHighHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
			h.setLowHand(h.getCardsInHand().get(4).getCardRank().getiCardNumber());
			return true;
		} else if ((h.getCardsInHand().get(0).getCardRank().getiCardNumber() == h.getCardsInHand().get(1).getCardRank()
				.getiCardNumber())
				&& (h.getCardsInHand().get(2).getCardRank().getiCardNumber() == h.getCardsInHand().get(4).getCardRank()
						.getiCardNumber())) {
			h.setHandscore(eHandScore.FULLHOUSE);
			h.setHighHand(h.getCardsInHand().get(4).getCardRank().getiCardNumber());
			h.setLowHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
			return true;
		} else {
			return false;

		}
	}

	public static boolean isFlush(Hand h) {
		for (int x = 0; x < 5; x++) {
			if (h.getCardsInHand().get(x).getCardSuit().getiCardSuit() != h.getCardsInHand().get(0).getCardSuit()
					.getiCardSuit()) {
				return false;
			}
		}
		h.setHandscore(eHandScore.FLUSH);
		h.setHighHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
		ArrayList<Card> kickers = new ArrayList<Card>();
		for (int i = 1; i < 5; i++) {
			kickers.add(h.getCardsInHand().get(i));
		}
		h.setKickers(kickers);
		return true;
	}

	public static boolean isStraight(Hand h) {
		int lc = h.getCardsInHand().get(4).getCardRank().getiCardNumber();
		int[] values = { lc + 4, lc + 3, lc + 2, lc + 1, lc };
		if (h.getCardsInHand().get(0).getCardRank() == eRank.ACE) {
			for (int x = 1; x < 5; x++) {
				if (h.getCardsInHand().get(x).getCardRank().getiCardNumber() != values[x]) {
					return false;
				}
			}
		} else {
			for (int x = 0; x < 5; x++) {
				if (h.getCardsInHand().get(x).getCardRank().getiCardNumber() != values[x]) {
					return false;
				}
			}
		}

		h.setHandscore(eHandScore.STRAIGHT);
		h.setHighHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
		return true;
	}

	public static boolean isThreeOfAKind(Hand h) {
		int card1 = h.getCardsInHand().get(0).getCardRank().getiCardNumber();
		int card2 = h.getCardsInHand().get(1).getCardRank().getiCardNumber();
		int card3 = h.getCardsInHand().get(2).getCardRank().getiCardNumber();
		int card4 = h.getCardsInHand().get(3).getCardRank().getiCardNumber();
		int card5 = h.getCardsInHand().get(4).getCardRank().getiCardNumber();

		if ((card1 == card3) && (card4 != card5)) {
			h.setHandscore(eHandScore.THREEOFAKIND);
			h.setHighHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(3));
			kickers.add(h.getCardsInHand().get(4));
			h.setKickers(kickers);
			return true;
		} else if ((card2 == card4)) {
			h.setHandscore(eHandScore.THREEOFAKIND);
			h.setHighHand(h.getCardsInHand().get(2).getCardRank().getiCardNumber());
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(0));
			kickers.add(h.getCardsInHand().get(4));
			h.setKickers(kickers);
			return true;
		} else if ((card3 == card5) && (card1 != card2)) {
			h.setHandscore(eHandScore.THREEOFAKIND);
			h.setHighHand(h.getCardsInHand().get(4).getCardRank().getiCardNumber());
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(0));
			kickers.add(h.getCardsInHand().get(1));
			h.setKickers(kickers);
			return true;
		} else {
			return false;
		}
	}

	public static boolean isTwoPair(Hand h) {

		int card1 = h.getCardsInHand().get(0).getCardRank().getiCardNumber();
		int card2 = h.getCardsInHand().get(1).getCardRank().getiCardNumber();
		int card3 = h.getCardsInHand().get(2).getCardRank().getiCardNumber();
		int card4 = h.getCardsInHand().get(3).getCardRank().getiCardNumber();
		int card5 = h.getCardsInHand().get(4).getCardRank().getiCardNumber();
		ArrayList<Card> kickers = new ArrayList<Card>();

		if ((card1 == card2) && (card3 == card4) && (card3 != card5)) {
			h.setHandscore(eHandScore.TWOPAIR);
			h.setHighHand(card1);
			h.setLowHand(card3);
			kickers.add(h.getCardsInHand().get(4));
			h.setKickers(kickers);
			return true;
		} else if ((card1 == card2) && (card4 == card5) && (card3 != card4)) {
			h.setHandscore(eHandScore.TWOPAIR);
			h.setHighHand(card1);
			h.setLowHand(card4);
			kickers.add(h.getCardsInHand().get(2));
			h.setKickers(kickers);
			return true;
		} else if ((card1 != card2) && (card2 == card3) && (card4 == card5)) {
			h.setHandscore(eHandScore.TWOPAIR);
			h.setHighHand(card1);
			h.setLowHand(card3);
			kickers.add(h.getCardsInHand().get(4));
			h.setKickers(kickers);
			return true;
		} else {
			return false;
		}

	}

	public static boolean isOnePair(Hand h) {
		int numPairs = 0;
		int posOfPair = 0;
		for (int i = 0; i < 4; i++) {
			if (h.getCardsInHand().get(i).getCardRank().getiCardNumber() == h.getCardsInHand().get(i + 1).getCardRank()
					.getiCardNumber()) {
				numPairs += 1;
				posOfPair = i;
			}
		}
		if (numPairs == 1) {
			h.setHandscore(eHandScore.ONEPAIR);
			h.setHighHand(h.getCardsInHand().get(posOfPair).getCardRank().getiCardNumber());
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get((posOfPair + 2) % 5));
			kickers.add(h.getCardsInHand().get((posOfPair + 3) % 5));
			kickers.add(h.getCardsInHand().get((posOfPair + 4) % 5));
			Collections.sort(kickers);
			h.setKickers(kickers);
			return true;
		} else {
			return false;
		}
	}

	public static boolean isHighCard(Hand h) {
		int numPairs = 0;
		for (int i = 0; i < 4; i++) {
			if (h.getCardsInHand().get(i).getCardRank().getiCardNumber() == h.getCardsInHand().get(i + 1).getCardRank()
					.getiCardNumber()) {
				numPairs += 1;
			}
		}
		if (numPairs == 0) {
			int numOfSameSuit = 0;
			for (int x = 0; x < 5; x++) {
				if (h.getCardsInHand().get(x).getCardSuit().getiCardSuit() == h.getCardsInHand().get(0).getCardSuit()
						.getiCardSuit()) {
					numOfSameSuit += 1;
				}
				if (numOfSameSuit == 5) {
					return false;
				}
			}
			h.setHandscore(eHandScore.HIGHCARD);
			h.setHighHand(h.getCardsInHand().get(0).getCardRank().getiCardNumber());
			ArrayList<Card> kickers = new ArrayList<Card>();
			for (int i = 1; i < 5; i++) {
				kickers.add(h.getCardsInHand().get(i));
			}
			h.setKickers(kickers);
			return true;
		} else {
			return false;
		}
	}

}