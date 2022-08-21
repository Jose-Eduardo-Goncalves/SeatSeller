package com.seatseller.core.lugares;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import com.seatseller.api.wrappers.Combinacao;

public class CatalogoGrelhas {
	
	private Map<String, Grelha> grelhas = new HashMap<>();
	
	public Optional<Grelha> getGrelha(String design) {
		
			return Optional.ofNullable(grelhas.get(design));
	}
	
	//TODO javadoc
	public boolean existeGrelha(String desig) {
		
		return grelhas.containsKey(desig);
	}
	
	//TODO javadoc
	public void acrescentaGrelha(Grelha grelhaCurrente) {
		
		grelhas.put(grelhaCurrente.getDesignacao(), grelhaCurrente);
	}

	public List<Combinacao> getCombinacoes(LocalDate data, LocalTime hora) {
		// TODO Auto-generated method stub
		
		//4.1)
		List<Combinacao> lComb = new LinkedList<>();
		
		
		//4.2)
		for (Entry<String, Grelha> g : grelhas.entrySet()) {
			
			List<Combinacao> lc = g.getValue().getCombinacoes(data, hora);
			
			//4.3)
			lComb.addAll(lc);
			
		}; 
		
		
		return lComb;
	}
	
}
