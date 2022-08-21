package com.seatseller.core.reservas;

import com.seatseller.Configuration;

public class ReservaFactory {

	//Seguindo a receita do Chefe Gordon Alcides

	//Passo 1
	private static ReservaFactory INSTANCE;
	private int codigo;
	
	//Passo 2
	public static ReservaFactory getInstace() {
		
		if(INSTANCE == null) {
			INSTANCE = new ReservaFactory();
		}
		
		return INSTANCE;
	}
	
	//Passo 3
	private ReservaFactory() {codigo = 0;};
	
	private Configuration conf = Configuration.getInstance();

	public Reserva getProximaReserva() {

		int codigo = novoCodigo();

		return new Reserva("R" + Integer.toString(codigo));
	}
	
	private int novoCodigo() {
		return codigo++;
	}
	
	//TODO
	
}
