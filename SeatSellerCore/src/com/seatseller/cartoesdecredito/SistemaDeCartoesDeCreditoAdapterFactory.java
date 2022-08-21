package com.seatseller.cartoesdecredito;

import com.seatseller.Configuration;

public class SistemaDeCartoesDeCreditoAdapterFactory {
	private static SistemaDeCartoesDeCreditoAdapterFactory INSTANCE;
	private Configuration config = Configuration.getInstance();
	
	public static SistemaDeCartoesDeCreditoAdapterFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SistemaDeCartoesDeCreditoAdapterFactory();
		}
		
		return INSTANCE;
	}
	
	/*
	 * Construtor privado da fabrica para garantir 
	 * que apenas existe uma unica instancia
	 */
	private SistemaDeCartoesDeCreditoAdapterFactory() {}
	
	/**
	 * Pede a instancia de configuration o adapter de sistema de cartoes
	 * de credito que se pretende usar
	 * @return O adapter para o sistema de cartoes de credito
	 */
	public ISistemaDeCartoesDeCreditoAdapter getSistemaDeCartoesDeCreditoAdapter() {
		return config.getSCCAdapter();
		
	}
}
