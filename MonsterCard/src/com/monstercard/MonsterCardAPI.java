package com.monstercard;

import java.util.logging.Logger;

public class MonsterCardAPI {
	
	private static Logger LOGGER = Logger.getLogger(MonsterCardAPI.class.getName());
	
	/*
	 * Checks if a Credit Card is valid
	 */
	public boolean isValid(Card c) {
		LOGGER.info("A validar cartao " + c);
		return c.validate();
	}
	
	/*
	 * Removes amount from credit card c.
	 */
	public boolean charge(Card c, double amount) {
		if (c.getCCV() % 2 == 1)
			return false;		
		LOGGER.info("A cobrar cartao " + c + " " + amount + "euros");
		return true;
	}
	
	
	/*
	 * Blocks amount from credit card c.
	 */
	public boolean block(Card c, double amount) {
		if (c.getCCV() % 2 == 1)
			return false;
		LOGGER.info("A cativar cartao " + c + " " + amount + "euros");
		return true;
	}
	
}
