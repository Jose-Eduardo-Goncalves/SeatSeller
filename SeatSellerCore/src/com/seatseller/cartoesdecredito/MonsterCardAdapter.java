package com.seatseller.cartoesdecredito;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;

class MonsterCardAdapter implements ISistemaDeCartoesDeCreditoAdapter {

	@Override
	public boolean validar(String num, int ccv, int mes, int ano) {
		StringBuilder stb = new StringBuilder();
		String ccvS = stb.append(ccv).toString();
		stb = new StringBuilder();
		String mesS;
		if (mes>10) {
			mesS = stb.append(mes).toString();
		} else {
			mesS = stb.append(0).append(mes).toString();
		}
		stb = new StringBuilder();
		String anoS = stb.append(ano).toString();
		
		Card c = new Card(num,ccvS,mesS,anoS);
		MonsterCardAPI m = new MonsterCardAPI();
		
		return m.isValid(c);
	}

	@Override
	public boolean cativar(String num, int ccv, int mes, int ano, double qt) {
		StringBuilder stb = new StringBuilder();
		String ccvS = stb.append(ccv).toString();
		stb = new StringBuilder();
		String mesS;
		if (mes>10) {
			mesS = stb.append(mes).toString();
		} else {
			mesS = stb.append(0).append(mes).toString();
		}
		stb = new StringBuilder();
		String anoS = stb.append(ano).toString();
		
		Card c = new Card(num,ccvS,mesS,anoS);
		MonsterCardAPI m = new MonsterCardAPI();
		
		return m.block(c, qt);
	}

	@Override
	public boolean retirar(String num, int ccv, int mes, int ano, double qt) {
		StringBuilder stb = new StringBuilder();
		String ccvS = stb.append(ccv).toString();
		stb = new StringBuilder();
		String mesS;
		if (mes>10) {
			mesS = stb.append(mes).toString();
		} else {
			mesS = stb.append(0).append(mes).toString();
		}
		stb = new StringBuilder();
		String anoS = stb.append(ano).toString();
		
		Card c = new Card(num,ccvS,mesS,anoS);
		MonsterCardAPI m = new MonsterCardAPI();
		
		return m.charge(c, qt);
	}

}
