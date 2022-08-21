package com.seatseller.core.reservas;

import java.util.HashMap;
import java.util.Map;

public class CatalogoReservas {
	private Map<String, Reserva> reservas = new HashMap<>();
	
		
	public void registarReserva(Reserva reserva) {
		reservas.put(reserva.getCodigo(), reserva);
	}

	public Reserva getReserva(String codigo) {
		return reservas.get(codigo);
	}
}
	
