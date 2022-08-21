package com.seatseller.core.pagamentos;

public class Pagamento {
	private double valorPago;
	private boolean cativar;
	//False quer dizer retirar
	
	/**
	 * Permite criar um pagamento com o tipo "cativar" ou "retirar" e com um
	 * dado valor
	 * @param tipo True se for "cativar" ou false se for "retirar"
	 * @param valor O valor que foi pago
	 * @requires valor >= 0 
	 */
	public Pagamento (boolean tipo, double valor) {
		valorPago = valor;
		cativar = tipo;
	}

	public double getValorPago() {
		
		return valorPago;
	}
}
