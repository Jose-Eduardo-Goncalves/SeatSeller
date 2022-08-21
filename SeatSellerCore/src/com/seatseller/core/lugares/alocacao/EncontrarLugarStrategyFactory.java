package com.seatseller.core.lugares.alocacao;

import com.seatseller.Configuration;

public class EncontrarLugarStrategyFactory {
	private static EncontrarLugarStrategyFactory INSTANCE;
	private Configuration config = Configuration.getInstance();
	
	public static EncontrarLugarStrategyFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EncontrarLugarStrategyFactory();
		}
		
		return INSTANCE;
	}
	
	/*
	 * Construtor privado da fabrica para garantir 
	 * que apenas existe uma unica instancia
	 */
	private EncontrarLugarStrategyFactory() {}
	
	public IEncontrarLugarStrategy getEncontrarLugarStrategy() {
		return config.getLugarStrategy();
	}
	
}
