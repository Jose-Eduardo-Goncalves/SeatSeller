package com.seatseller.handlers;

import com.seatseller.api.IConcluirReservaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;
import com.seatseller.cartoesdecredito.ISistemaDeCartoesDeCreditoAdapter;
import com.seatseller.core.pagamentos.Pagamento;
import com.seatseller.core.reservas.CatalogoReservas;
import com.seatseller.core.reservas.Reserva;
import com.seatseller.core.utilizadores.ClienteFinal;
import com.seatseller.core.utilizadores.Utilizador;

public class ConcluirReservaHandler implements IConcluirReservaHandler {

	private Utilizador utilizador;
	private CatalogoReservas catReservas;
	private ISistemaDeCartoesDeCreditoAdapter sisCC;
	private Reserva res;
	private double valorFalta;
	
	public ConcluirReservaHandler(Utilizador u, CatalogoReservas catRes,
			ISistemaDeCartoesDeCreditoAdapter sisCC) {
		utilizador = u;
		catReservas = catRes;
		this.sisCC = sisCC;
	}
	
	@Override
	public double confirmarValorEmFalta(String codigo) throws DoesNotExistException {
		
		if ((res = catReservas.getReserva(codigo)) == null) {			
			throw new DoesNotExistException("A reserva com o codigo " + codigo +
					" nao existe.");
		}
	
		return valorFalta = res.getValorEmFalta();
	}

	@Override
	public void indicarCC(String numero, int ccv, int mes, int ano) throws InvalidCreditCardException {
		
		if (!sisCC.validar(numero, ccv, mes, ano)) {
			throw new InvalidCreditCardException("O cartao de credito com o numero " + 
					numero + " nao eh valido.");
		} 
		
		ClienteFinal cli = (ClienteFinal)res.getCliente();
		Pagamento pg = new Pagamento(false, valorFalta);
		
		if (!cli.temCC(numero)) {
			/*
			 * No diagrama mostra que isto � guardado numa variavel ou atributo mas
			 * n�o percebo o prop�sito neste caso de uso
			 */
			cli.criaCC(numero, ccv, mes, ano);
		}
		
		cli.registarPagamento(pg);
		res.registarPagamento(pg);
		
		sisCC.retirar(numero, ccv, mes, ano, valorFalta);
		
	}

}
