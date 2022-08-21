package com.seatseller.handlers;

import java.util.Optional;

import com.seatseller.api.ICriarGrelhaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.NonUniqueException;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.TipoDeLugar;

public class CriarGrelhaHandler implements ICriarGrelhaHandler {

	private CatalogoGrelhas grelhas;
	private CatalogoTiposDeLugar tiposLugar;
	private Grelha grelhaCurrente;
	
	public CriarGrelhaHandler(CatalogoGrelhas catG, CatalogoTiposDeLugar catT) {
		grelhas = catG;
		tiposLugar = catT;
		grelhaCurrente = null;
	}
	
	
	//TODO Javadoc
	@Override
	public void iniciarGrelha(String desig, double ind) throws NonUniqueException {
		
		/*Ver se a desig que recebemos é igual 
		à grelha currente ou se já existe no catálogo de grelhas
		*/
		if(grelhaCurrente != null && grelhaCurrente.getDesignacao().equals(desig) || grelhas.existeGrelha(desig)) {
			
			throw new NonUniqueException("A grelha " + desig + " já existe.");
			
		} else {
			
			grelhaCurrente = new Grelha(desig, ind);
			
		}
	}

	//TODO javadoc
	@Override
	public Optional<String> indicarDimensao(int alt, int larg) {

		Optional<TipoDeLugar> padr = tiposLugar.getPadrao(); 
		
		if(padr.isPresent()) {
			
			grelhaCurrente.criaLugares(alt, larg, padr.get());
			return Optional.of(padr.get().getDesig());
			
		} else {
			
			grelhaCurrente.criaLugares(alt, larg, null);
			return Optional.empty();
		}

	}

	//TODO javadoc
	@Override
	public void indicarTipoPadrao(String desig) throws DoesNotExistException {
		
		
		Optional<TipoDeLugar> tp = tiposLugar.getTipo(desig);
		
		if(!tp.isPresent()) {
			
			throw new DoesNotExistException("O tipo de lugar " + desig + 
					" não existe.");
		} else {
			
			grelhaCurrente.defineTipoLugarPadrao(tp.get());
			
		}
		
	}

	//TODO javadoc
	@Override
	public void indicarTipoLugar(int i, int j, String desig) throws DoesNotExistException {
		

		Optional<TipoDeLugar> tp = tiposLugar.getTipo(desig);
		
		if(!tp.isPresent()) {
			
			throw new DoesNotExistException("O tipo de lugar " + desig + 
					" não existe.");
			
		} else {
			
			if(!grelhaCurrente.coordenadasValidas(i, j)) {
				
				throw new DoesNotExistException("As coordenadas x: " + i 
						+ ", e y: " + j + "são inválidas");
						
			} else {
				
				grelhaCurrente.defineTipoLugar(i, j, tp.get());
				
			}
			
			
		}
		
		
	}

	//TODO javadoc
	@Override
	public void terminar() {
		
		grelhas.acrescentaGrelha(grelhaCurrente);
		grelhaCurrente = null;
	}

}
