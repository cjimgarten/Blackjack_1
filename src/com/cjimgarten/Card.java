/*
 * Card.java
 * 
 * created: 11-02-2016
 * modified: 12-03-2016
 * 
 * class represents a playing card
 */

package com.cjimgarten;

public class Card {
	
	// add some attributes
	private final String rank;
	private final String suit;
	private int value;
	private boolean visible;
	
	/**
	 * create a card
	 */
	public Card(String rank, String suit, int value, boolean visible) {
		this.rank = rank;
		this.suit = suit;
		this.value = value;
		this.visible = visible;
	}
	
	/**
	 * add some get methods
	 */
	public String getRank() {
		if (this.visible) {
			return this.rank;
		}
		return "_";
	}
	
	public String getSuit() {
		if (this.visible) {
			return this.suit;
		}
		return "_";
	}
	
	public int getValue() {
		if (this.visible) {
			return this.value;
		}
		return 0;
	}
	
	public boolean getVisibility() {
		return this.visible;
	}
	
	/**
	 * swap the value of an Ace from 11 to 1 or vice versa
	 */
	public void swapAceValue() {
		if (!(this.rank.equals("Ace"))) {
			return;
		}
		if (this.value == 11) {
			this.value = 1;
		} else {
			this.value = 11;
		}
	}
	
	/**
	 * flip the card
	 */
	public void swapVisibility() {
		if (this.visible) {
			this.visible = false;
		} else {
			this.visible = true;
		}
	}
	
	/**
	 * return data about this instance
	 */
	public String toString() {
		if (this.visible) {
			return this.rank + " of " + this.suit + "s: " + this.value;
		}
		return "_";
	}

}
