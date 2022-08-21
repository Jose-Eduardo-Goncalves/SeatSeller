package com.seatseller.handlers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.seatseller.Configuration;
import com.seatseller.api.IReservarLugarHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;
import com.seatseller.api.wrappers.Combinacao;
import com.seatseller.cartoesdecredito.ISistemaDeCartoesDeCreditoAdapter;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;
import com.seatseller.core.pagamentos.CartaoCredito;
import com.seatseller.core.pagamentos.Pagamento;
import com.seatseller.core.reservas.CatalogoReservas;
import com.seatseller.core.reservas.Reserva;
import com.seatseller.core.reservas.ReservaFactory;
import com.seatseller.core.utilizadores.CatalogoUtilizadores;
import com.seatseller.core.utilizadores.ClienteFinal;
import com.seatseller.core.utilizadores.Utilizador;

public class ReservarLugarHandler implements IReservarLugarHandler {

	private Utilizador utilizador;
	private CatalogoGrelhas catG;
	private CatalogoTiposDeLugar catT;
	private CatalogoReservas catR;
	private CatalogoUtilizadores catU;
	private ISistemaDeCartoesDeCreditoAdapter sisCC;
	private Reserva reserva;
	private CartaoCredito cc;
	
	public ReservarLugarHandler(Utilizador utilizador, CatalogoGrelhas catG, CatalogoTiposDeLugar catT,
			CatalogoReservas catR, CatalogoUtilizadores catU, ISistemaDeCartoesDeCreditoAdapter sisCC) {
		
		this.utilizador = utilizador;
		this.catG = catG;
		this.catT = catT;
		this.catR = catR;
		this.catU = catU;
		this.sisCC = sisCC;
	}

	//TODO javadoc
	@Override
	public void indicarCliente(String username) throws DoesNotExistException {
		
		Optional<Utilizador> temp = catU.getCliente(username);
		
		if(!temp.isPresent()) {
			throw new DoesNotExistException("O cliente: " + username 
					+ " não existe");
		} else {
			
			utilizador = temp.get();
		}
		
	}


	@Override
	public Iterable<Combinacao> indicarDataHora(LocalDate date, LocalTime time) {
		// TODO
		
		//1)
		if(reserva == null) {
			reserva = ReservaFactory.getInstace().getProximaReserva();
		} 
		
		//2,3)
		reserva.setCliente(utilizador);
		reserva.novaLinha(date, time);
		
		//4)
		List<Combinacao> lComb = catG.getCombinacoes(date, time);
		
		
		return lComb;
	}


	@Override
	public String indicarCombinacao(String grelha, String tipoDeLugar) throws DoesNotExistException {

		if(!catG.existeGrelha(grelha) || !catT.getTipo(tipoDeLugar).isPresent()) {
			throw new DoesNotExistException("TODO");
		}
		
		//1)
		Grelha g = catG.getGrelha(grelha).get();
		
		//2)
		TipoDeLugar t = catT.getTipo(tipoDeLugar).get();
		
		//3)
		LocalDate data = reserva.getDataCorrente();
		
		//4)
		LocalTime hora = reserva.getHoraCorrente();
		
		//5)
		Lugar lugDisp = g.getDisponivel(t, data, hora);
		
		//6) ASSOCIAR A LINHA DE RESERVA AO LUGAR
		lugDisp.associaLinhaReserva(reserva.getLinhaReservaCurrente());
		reserva.getLinhaReservaCurrente().adicionaLugar(lugDisp);
		
		return lugDisp.toString();
	}


	@Override
	public void terminarLugares() {
		
		reserva.finalizar();
		
	}


	@Override
	public double indicarCC(String numero, int ccv, int mes, int ano) throws InvalidCreditCardException {
		
		//1)
		boolean ccValido = sisCC.validar(numero, ccv, mes, ano);
		
		//2)
		ClienteFinal temp = (ClienteFinal)utilizador;
		boolean temCC = temp.temCC(numero);
		
	
		if(!temCC) {
			//3)
			cc = temp.criaCC(numero, ccv, mes, ano);

		} else {
			cc = temp.getCC(numero);
		}
		
		//4)
		return reserva.getPreco();
	
	}


	@Override
	public String confirmarReserva() {
		// TODO
		
		Configuration.getInstance();
		Configuration.
				getInstance();
		Configuration.getInstance();
		//1)
		boolean cativar = Configuration.getInstance().
				cativarDuranteReservas();
		
		//2)
		double valor = Configuration.getInstance().valorDuranteReservas();
		
		//3)
		Pagamento pg = new Pagamento(cativar, valor);
		
		//4)
		ClienteFinal temp = (ClienteFinal)utilizador;
		temp.registarPagamento(pg);
		
		//5)
		reserva.registarPagamento(pg);
		
		//6)
		temp.associarReserva(reserva);
		
		//7)
		catR.registarReserva(reserva);
		
		
		//8)
		String numero = cc.getNumero();
		int ccv = cc.getCcv();
		int mes = cc.getMes();
		int ano = cc.getAno();
		
		//9)
		if(cativar) {
			//9a)
			sisCC.cativar(numero, ccv, mes, ano, valor);
		} else {
			//9b)
			sisCC.retirar(numero, ccv, mes, ano, valor);
		}
		
		//10)
		reserva.notificarGrelhas();
		
		//11)
		String codRes = reserva.getCodigo();
		
		
		return codRes;
	}

}
