package com.seatseller.pagamentos;

public class Pagamento {
	private int valorPago;
	private String tipoPagamento;
	//TODO Ver porque no UC7 o construtor tem false e concluir
	
	public Pagamento (String tipo, int valor) {
		valorPago = valor;
		tipoPagamento = tipo;
	}
}
