package com.seatseller.core.pagamentos;

import java.util.ArrayList;
import java.util.List;

public class CartaoCredito {
	private List<Pagamento> pagamentos;
	private String numero;
	private int ccv;
	private int mes;
	private int ano;
	
	public CartaoCredito(String num, int ccv, int mes, int ano) {
		pagamentos = new ArrayList<>();
		numero = num;
		this.ccv = ccv;
		this.mes = mes;
		this.ano = ano;
	}
	
	public String getNumero () {
		return numero;
	}

	/**
	 * @return the ccv
	 */
	public int getCcv() {
		return ccv;
	}

	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}

	/**
	 * @return the ano
	 */
	public int getAno() {
		return ano;
	}
}
