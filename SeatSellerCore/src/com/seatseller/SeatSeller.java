package com.seatseller;

import java.util.Optional;

import com.seatseller.api.IRegistarUtilizadorHandler;
import com.seatseller.api.ISeatSeller;
import com.seatseller.api.ISessao;
import com.seatseller.cartoesdecredito.ISistemaDeCartoesDeCreditoAdapter;
import com.seatseller.cartoesdecredito.SistemaDeCartoesDeCreditoAdapterFactory;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.reservas.CatalogoReservas;
import com.seatseller.core.utilizadores.CatalogoUtilizadores;
import com.seatseller.handlers.RegistarUtilizadorHandler;
import com.seatseller.handlers.Sessao;

public class SeatSeller implements ISeatSeller {

	private CatalogoUtilizadores catU = new CatalogoUtilizadores();
	private CatalogoTiposDeLugar catT = new CatalogoTiposDeLugar();
	private CatalogoGrelhas catG = new CatalogoGrelhas();
	private CatalogoReservas catR = new CatalogoReservas();
	/* 
	 * Pede a factory de cartoes de credito a sua unica instancia e 
	 * depois pede uma instancia do adaptador do sistema de cartoes de credito 
	 */
	private ISistemaDeCartoesDeCreditoAdapter sisCC = SistemaDeCartoesDeCreditoAdapterFactory
													  .getInstance().getSistemaDeCartoesDeCreditoAdapter();
	
	@Override
	public IRegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return new RegistarUtilizadorHandler(catU);
	}

	@Override
	public Optional<ISessao> autenticar(String u, String p) {
		boolean autenticado = catU.autenticar(u, p);
		
		if (!autenticado) return Optional.empty();
		return Optional.of(new Sessao(u, catG, catU, catT, catR, sisCC));
	}

}
