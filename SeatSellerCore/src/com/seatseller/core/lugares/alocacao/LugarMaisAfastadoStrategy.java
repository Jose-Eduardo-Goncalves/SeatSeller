package com.seatseller.core.lugares.alocacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

public class LugarMaisAfastadoStrategy implements IEncontrarLugarStrategy {

	@Override
	public Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d, LocalTime t) {
		Lugar[][] lugares = g.getLugares();
		List<Lugar> lugaresOcupado = getLugaresOcupados(lugares, tp, d, t);
		List<Lugar> lugaresLivres = getLugaresLivres(lugares, tp, d, t);
		double distMaior = 0;
		double distRelativa = 0;
		Lugar maisAfastado = null;
		
		
		for (Lugar lugarLivre : lugaresLivres) {
			
			distRelativa = 0;
			
			for (Lugar lugarOcupado : lugaresOcupado) {
				distRelativa += distanciaEntreLugares(lugarLivre, lugarOcupado);
			}
			if (distRelativa > distMaior) {
				distMaior = distRelativa;
				maisAfastado = lugarLivre;
			}
		}
		
		return Optional.ofNullable(maisAfastado);
	}

	private double distanciaEntreLugares(Lugar lugarLivre, Lugar lugarOcupado) {
		int lugarLivreX = lugarLivre.getX();
		int lugarLivreY = lugarLivre.getY();
		int lugarOcupadoX = lugarOcupado.getX();
		int lugarOcupadoY = lugarOcupado.getY();
		
		
		return Math.sqrt(Math.pow(lugarOcupadoX - lugarLivreX, 2) + Math.pow(lugarOcupadoY - lugarLivreY, 2));
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

	private List<Lugar> getLugaresOcupados(Lugar[][] lugares,  TipoDeLugar tp, LocalDate d, LocalTime t) {
		List<Lugar> lugaresOcup = new ArrayList<>();
		for (int i = 0; i < lugares.length; i++) {
			for (int j = 0; j < lugares[i].length; j++) {
				if (lugares[i][j].getTipoLugar().getDesig().equals(tp.getDesig())) {
					if (!lugares[i][j].disponivel(d, t)) {
						lugaresOcup.add(lugares[i][j]);
					}
				}
			}
		}
		
		return lugaresOcup;
	}

}
