package com.seatseller.core.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import com.seatseller.core.lugares.Lugar;

public class LinhaReserva {

	//TODO
	private LocalDate date;
	private LocalTime time;
	private List<Lugar> lugaresReservados;
	
	public LinhaReserva(LocalDate date, LocalTime time) {
		this.date = date;
		this.time = time;
		lugaresReservados = new LinkedList<>();
	}

	/**
	 * @return the date
	 */
	LocalDate getDate() {
		return date;
	}

	/**
	 * @return the time
	 */
	LocalTime getTime() {
		return time;
	}

	public boolean isDisp(LocalDate data, LocalTime hora, Lugar lugar) {
		
		/*Verificar se a data e hora que a reserva está a querer reservar
			é no futuro
		*/
		if(data.isBefore(LocalDate.now()) 
		|| data.isEqual(LocalDate.now()) && hora.isBefore(LocalTime.now())) {
			
			return false;
			
		}
		
		
		/*Se o lugar não estiver na lista dos lugares reservados
		 * ou a data é diferente da data desta linha de reserva
		 * ou a data é igual mas a hora é diferente da linha de reserva
		 */
		if(!lugaresReservados.contains(lugar)
				|| !data.isEqual(date)
				|| data.isEqual(date) && !hora.equals(time)) return true;

		return false;
	}
	
	public double getPreco() {
		
		double preco = 0;
		
		for (Lugar lugar : lugaresReservados) {
			preco += lugar.getPreco();
		}
		
		return preco;
	}

	public void adicionaLugar(Lugar lugarReservado) {
		lugaresReservados.add(lugarReservado);
	}
	public void notificarGrelha() {
		// TODO Auto-generated method stub
		for (Lugar lugar : lugaresReservados) {
			lugar.notificarGrelha();
		}
	}
}
