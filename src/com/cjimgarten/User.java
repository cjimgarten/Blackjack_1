/*
 * User.java
 * 
 * created: 12-03-2016
 * modified: 12-03-2016
 * 
 * class adds additional functionality to the Player class
 */

package com.cjimgarten;

public class User extends Player {

	// add some attributes
	private double balance;
	
	/**
	 * create an instance
	 */
	public User(double balance) {
		super();
		this.balance = balance;
	}
	
	/**
	 * get the users' balance
	 */
	public double getBalance() {
		return this.balance;
	}
	
	/**
	 * user wins
	 */
	public void won(double cash) {
		this.balance += cash;
	}
	
	/**
	 * user loses
	 */
	public void lost(double cash) {
		this.balance -= cash;
	}
	
}
