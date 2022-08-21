package com.seatseller.handlers;

import java.util.Optional;

import com.seatseller.api.IAssociarGrelhaHandler;
import com.seatseller.api.INotificacaoReceiver;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.utilizadores.Funcionario;
import com.seatseller.core.utilizadores.Utilizador;

import sun.util.calendar.Gregorian;

public class AssociarGrelhaHandler implements IAssociarGrelhaHandler {

	private CatalogoGrelhas catG;
	private Funcionario utilizador;
	
	public AssociarGrelhaHandler(Utilizador utilizador, CatalogoGrelhas catG) {
		// TODO Auto-generated constructor stub
		this.catG = catG;
		this.utilizador = (Funcionario)utilizador;
	}

	@Override
	public void associarGrelha(String desig, INotificacaoReceiver c){
		// TODO
		//1)
		Optional<Grelha> g = catG.getGrelha(desig);
		//2)
		if(g.isPresent()) {
			//TODO
			utilizador.associarGrelha(g.get(), c);
		}
		
		
	}
}
