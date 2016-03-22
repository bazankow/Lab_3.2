package exceptions;

import base.Hand;

public class exHand extends Exception {
	private Hand hand1;
	private Hand hand2;
	
	public exHand(Hand newHand1, Hand newHand2){
		super("Hands have tied");
		this.hand1 = newHand1;
		this.hand2 = newHand1;		
	}

	public Hand getHand1() {
		return hand1;
	}

	public Hand getHand2() {
		return hand2;
	}
}
