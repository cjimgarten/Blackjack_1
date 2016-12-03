/*
 * Blackjack_1.java
 * 
 * created: 12-03-2016
 * modified: 12-03-2016
 * 
 * blackjack command-line application
 */

package com.cjimgarten;

import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack_1 {
	
	// add some attributes
	private Blackjack game;
	private Scanner in;

	// define a few constants
	private final int STD_PAUSE = 500;
	private final String WIN = "win";
	private final String LOSE = "lose";
	
	/**
	 * create an instance
	 */
	public Blackjack_1() {
		this.game = new Blackjack();
		this.in = new Scanner(System.in);
	}
	
	/**
	 * start the application
	 */
	public void startApplication() {
		boolean playAgainBool;
		User user = this.game.getUser();
		Player dealer = this.game.getDealer();
		String readyStr = this.greet();
		do {
			playAgainBool = false;
			if (!(user.isEmpty() || dealer.isEmpty())) { 
				this.game.clearHands();
			}
			if (!this.processReadyStr(readyStr)) {
				break;
			}
			if (!this.hasMoney()) {
				System.out.println("You're out of money!");
				this.game.pause(this.STD_PAUSE);
				break;
			}
			System.out.print("Awesome! ");
			this.game.pause(this.STD_PAUSE);
			System.out.println("Let's play!");
			double wager = this.placeBet();
			while (!this.validateWager(wager)) {
				System.out.println("Not valid");
				this.game.pause(this.STD_PAUSE);
				System.out.println("You must bet at least 10 dollars, but no more than your current balance.");
				wager = this.placeBet();
			}
			this.deal();
			String hitOrStayStr = this.hitOrStay();
			while (this.processHitOrStayStr(hitOrStayStr)) {
				this.game.hit("user");
				this.showCards();
				if (this.overTwentyOne("user")) {
					System.out.println("YOU WENT OVER!\n");
					this.game.getUser().lost(wager);
					this.game.pause(this.STD_PAUSE);
					String playAgainStr = this.playAgain();
					playAgainBool = this.processPlayAgain(playAgainStr);
					break;
				}
				hitOrStayStr = this.hitOrStay();
			}
			if (playAgainBool) {
				continue;
			}
			this.game.flipDealersCards();
			this.dealersTurn();
			this.showCards();
			if (this.overTwentyOne("dealer")) {
				System.out.println("DEALER WENT OVER!\n");
				this.game.getUser().won(wager);
				this.game.pause(this.STD_PAUSE);
				String playAgainStr = this.playAgain();
				playAgainBool = this.processPlayAgain(playAgainStr);
				continue;
			}
			String resultStr = this.compareHands();
			if (resultStr.equals(this.WIN)) {
				System.out.println("YOU WIN!\n");
				this.game.getUser().won(wager);
			} else if (resultStr.equals(this.LOSE)) {
				System.out.println("YOU LOSE!\n");
				this.game.getUser().lost(wager);
			} else {
				System.out.println("YOU TIED!\n");
			}
			this.game.pause(this.STD_PAUSE);
			String playAgainStr = this.playAgain();
			playAgainBool = this.processPlayAgain(playAgainStr);
		} while (playAgainBool);
		
		// user quits
		String[] farewell = { "Boo", "Bye", "See ya" };
		int r = (int) (Math.random() * 9) % 3;
		System.out.println(farewell[r] + "!");
		this.game.pause(this.STD_PAUSE);
		System.exit(0);
	}
	
	/**
	 * greet the user
	 */
	public String greet() {
		System.out.println("Hello blackjack player!");
		this.game.pause(this.STD_PAUSE);
		System.out.println("Are you ready to play (Y/N)?");
		String readyStr = this.in.next();
		this.game.pause(this.STD_PAUSE);
		return readyStr;
	}
	
	/**
	 * process users' input string
	 */
	public boolean processReadyStr(String readyStr) {
		boolean readyBool = false;
		String upperCaseStr = readyStr.toUpperCase();
		if (upperCaseStr.equals("YES") || upperCaseStr.equals("Y")) {
			readyBool = true;
		}
		return readyBool;
	}
	
	/**
	 * ensure the user has money
	 */
	public boolean hasMoney() {
		boolean hasMoney = true;
		double currentBalance = this.game.getUser().getBalance();
		if (currentBalance <= 0.00) {
			hasMoney = false;
		}
		return hasMoney;
	}
	
	/**
	 * place a bet
	 */
	public double placeBet() {
		double currentBalance = this.game.getUser().getBalance();
		this.game.pause(this.STD_PAUSE);
		System.out.println("You currently have " + currentBalance + " dollars.");
		this.game.pause(this.STD_PAUSE);
		System.out.println("How much would you like to bet?");
		double wager = 0.00;
		try {
			wager = this.in.nextDouble();
		} catch (Exception e) {
			this.in.next();
			this.game.pause(this.STD_PAUSE);
			return wager;
		}
		this.game.pause(this.STD_PAUSE);
		return wager;
	}
	
	/**
	 * ensure the user enters a valid wager
	 */
	public boolean validateWager(double wager) {
		boolean valid = false;
		double currentBalance = this.game.getUser().getBalance();
		if (wager <= currentBalance && wager >= 10) {
			valid = true;
		}
		return valid;
	}
	
	/**
	 * deal the cards
	 */
	public void deal() {
		this.game.shuffleDeck();
		this.game.deal();
		this.showCards();
	}
	
	/**
	 * show the users' and dealers' cards
	 */
	public void showCards() {
		System.out.println("Here are your cards:\n");
		this.game.pause(this.STD_PAUSE);
		ArrayList<Card> userCards = this.game.getUser();
		for (int i = 0; i < userCards.size(); i++) {
			System.out.println(userCards.get(i));
			this.game.pause(this.STD_PAUSE);
		}
		int userValue = this.game.getUser().getValue();
		System.out.println("Value: " + userValue);
		this.game.pause(this.STD_PAUSE);
		System.out.println("\nHere are the dealers cards:\n");
		this.game.pause(this.STD_PAUSE);
		ArrayList<Card> dealerCards = this.game.getDealer();
		for (int i = 0; i < dealerCards.size(); i++) {
			System.out.println(dealerCards.get(i));
			this.game.pause(this.STD_PAUSE);
		}
		int dealerValue = this.game.getDealer().getValue();
		System.out.println("Value: " + dealerValue + "\n");
		this.game.pause(this.STD_PAUSE);
	}
	
	/**
	 * ask the user to hit or stay
	 */
	public String hitOrStay() {
		System.out.println("Hit or stay (H/S)?");
		String hitOrStayStr = this.in.next();
		this.game.pause(this.STD_PAUSE);
		return hitOrStayStr;
	}
	
	/**
	 * process users' input string
	 */
	public boolean processHitOrStayStr(String hitOrStayStr) {
		boolean hitBool = false;
		String upperCaseStr = hitOrStayStr.toUpperCase();
		if (upperCaseStr.equals("HIT") || upperCaseStr.equals("H")) {
			hitBool = true;
		}
		return hitBool;
	}
	
	/**
	 * check the value of the users' hand
	 */
	public boolean overTwentyOne(String player) {
		boolean overTwentyOne = false;
		int value = 0;
		if (player.equals("user")) {
			value = this.game.getUser().getValue();
		} else {
			value = this.game.getDealer().getValue();
		}
		if (value > 21) {
			overTwentyOne = true;
		}
		return overTwentyOne;
	}
	
	/**
	 * ask the user if they want to play again
	 */
	public String playAgain() {
		System.out.println("Would you like to play again (Y/N)?");
		String playAgainStr = this.in.next();
		this.game.pause(this.STD_PAUSE);
		return playAgainStr;
	}
	
	/**
	 * process users' input string
	 */
	public boolean processPlayAgain(String playAgainStr) {
		boolean playAgainBool = false;
		String upperCaseStr = playAgainStr.toUpperCase();
		if (upperCaseStr.equals("YES") || upperCaseStr.equals("Y")) {
			playAgainBool = true;
		}
		return playAgainBool;
	}
	
	/**
	 * dealers turn to play
	 */
	public void dealersTurn() {
		int value = this.game.getDealer().getValue();
		while (value < 17) {
			this.game.hit("dealer");
			value = this.game.getDealer().getValue();
		}
	}
	
	/**
	 * compare hands to see who wins
	 */
	public String compareHands() {
		String resultStr = "";
		int userValue = this.game.getUser().getValue();
		int dealerValue = this.game.getDealer().getValue();
		if (userValue > dealerValue) {
			resultStr = this.WIN;
		} else if (userValue < dealerValue) {
			resultStr = this.LOSE;
		}
		return resultStr;
	}
}

