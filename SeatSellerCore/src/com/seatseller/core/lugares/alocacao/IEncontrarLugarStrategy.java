package com.seatseller.core.lugares.alocacao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;

public interface IEncontrarLugarStrategy {
	Optional<Lugar> getLugar(Grelha g, TipoDeLugar tp, LocalDate d, LocalTime t);
}
