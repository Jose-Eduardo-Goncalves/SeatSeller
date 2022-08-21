package com.seatseller.core.lugares.alocacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

public class PrimeiroLugarStrategy implements IEncontrarLugarStrategy {

	@Override
	public Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d, LocalTime t) {
		Lugar[][] lugares = g.getLugares();
		for (int i = 0; i < lugares.length; i++) {
			for (int j = 0; j < lugares[i].length; j++) {
				if (lugares[i][j].disponivel(d, t) && 
					lugares[i][j].getTipoLugar().getDesig().equals(tp.getDesig())) {
					return Optional.ofNullable(lugares[i][j]);
				}
			}
		}
		
		return null;
	}

}
