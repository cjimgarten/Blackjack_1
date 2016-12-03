/*
 * Blackjack.java
 * 
 * created: 11-03-2016
 * modified: 12-03-2016
 * 
 * class represents a game of blackjack
 */

package com.cjimgarten;

public class Blackjack {

	// add some attributes
	private Deck deck;
	private User user;
	private Player dealer;
	
	/**
	 * create a game
	 */
	public Blackjack() {
		this.deck = new Deck();
		this.user = new User(10000);
		this.dealer = new Player();
	}
	
	/**
	 * add some get methods
	 */
	public Deck getDeck() {
		return this.deck;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public Player getDealer() {
		return this.dealer;
	}
	
	/**
	 * add a method to deal cards to players
	 */
	public void deal() {
		this.user.addCard(this.deck.getTopCard(true));
		this.user.addCard(this.deck.getTopCard(true));
		this.dealer.addCard(this.deck.getTopCard(false));
		this.dealer.addCard(this.deck.getTopCard(true));
	}
	
	/**
	 * add a method to allow players to hit
	 */
	public void hit(String player) {
		if (player.equals("user")) { // give the user a card
			this.user.addCard(deck.getTopCard(true));
		} else { // give the dealer a card
			this.dealer.addCard(deck.getTopCard(true));
		}
	}
	
	/**
	 * add a method to empty players hands
	 */
	public void clearHands() {
		this.user.clear();
		this.dealer.clear();
	}
	
	/**
	 * add a method to empty players hands
	 */
	public void shuffleDeck() {
		for (int i = 0; i < 5; i++) {
			this.deck.shuffle();
		}
	}
	
	/**
	 * check the amount of cards in the deck; if cards are getting low create a new deck and return true
	 */
	public boolean checkDeckCount() {
		if (this.deck.size() < 10) {
			this.deck.clear();
			this.deck = new Deck();
			return true;
		}
		return false;
	}
	
	/**
	 * pause execution
	 */
	public void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * flip the dealers cards
	 */
	public void flipDealersCards() {
		int handSize = this.dealer.size();
		for (int i = 0; i < handSize; i++) {
			Card c = dealer.get(i);
			if (!c.getVisibility()) {
				dealer.get(i).swapVisibility();
			}
		}
	}
}
