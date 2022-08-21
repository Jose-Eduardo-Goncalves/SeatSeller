package com.seatseller.core.lugares.alocacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

public class LugarAleatorioStrategy implements IEncontrarLugarStrategy {
	private Random lugarAleatorio;
	
	public LugarAleatorioStrategy() {
		lugarAleatorio = new Random();
	}

	@Override
	public Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d, LocalTime t) {
		Lugar[][] lugares = g.getLugares();
		List<Lugar> lugaresLivres = getLugaresLivres(lugares, tp, d, t);
		
		return Optional.ofNullable(lugaresLivres.get(lugarAleatorio.nextInt(lugaresLivres.size())));
	}
	
	private List<Lugar> getLugaresLivres(Lugar[][] lugares, TipoDeLugar tp, LocalDate d, LocalTime t) {
		List<Lugar> lugaresOcup = new ArrayList<>();
		for (int i = 0; i < lugares.length; i++) {
			for (int j = 0; j < lugares[i].length; j++) {
				if (lugares[i][j].getTipoLugar().getDesig().equals(tp.getDesig())) {
					if (lugares[i][j].disponivel(d, t)) {
						lugaresOcup.add(lugares[i][j]);
					}
				}
			}
		}
		
		return lugaresOcup;
	}

}
