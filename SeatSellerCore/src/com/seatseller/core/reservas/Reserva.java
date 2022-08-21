package com.seatseller.core.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.seatseller.core.pagamentos.Pagamento;
import com.seatseller.core.utilizadores.ClienteFinal;
import com.seatseller.core.utilizadores.Utilizador;

public class Reserva {

	private String codigo;
	private List<LinhaReserva> reservas;
	private LinhaReserva reservaCorrente;
	private Utilizador utilizador; //TODO Maybe cliente final?
	private List<Pagamento> pagamentos;
	
	public Reserva(String codigo) {
		
		this.codigo = codigo;
		reservas = new LinkedList<>();
		pagamentos = new ArrayList<>();
	}
	//TODO
	
	public void setCliente(Utilizador utilizador) {
		this.utilizador = utilizador;
	}
	
	public void novaLinha(LocalDate date, LocalTime time) {
		
		reservaCorrente = new LinhaReserva(date, time);
		
	}

	public LocalDate getDataCorrente() {
		return reservaCorrente.getDate();
	}

	public LocalTime getHoraCorrente() {
		return reservaCorrente.getTime();
	}
	
	public LinhaReserva getLinhaReservaCurrente() {
		return reservaCorrente;
	}

	public void finalizar() {
		reservas.add(reservaCorrente);
		reservaCorrente = null;
	}

	public double getPreco() {
		
		double preco = 0;
		
		for (LinhaReserva linhaReserva : reservas) {
			preco += linhaReserva.getPreco();
		}
		
		return preco;
	}

	public void registarPagamento(Pagamento pg) {

		pagamentos.add(pg);
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void notificarGrelhas() {
		// TODO Auto-generated method stub
		for (LinhaReserva linhaReserva : reservas) {
			linhaReserva.notificarGrelha();
		}
	}

	public double getValorEmFalta() {
		double totalAPagar = 0;
		double valorPago = getPagamentosFeitos();
		
		for (LinhaReserva lr: reservas) {
			totalAPagar += lr.getPreco();
		}
		
		return totalAPagar - valorPago;
	}
	
	private double getPagamentosFeitos() {
		double total = 0;
		
		for (Pagamento pg : pagamentos) {
			total += pg.getValorPago();
		}
		
		return total;
	}

	public Utilizador getCliente() {
		// TODO Auto-generated method stub
		return utilizador;
	}
}
