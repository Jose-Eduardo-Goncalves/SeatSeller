package com.seatseller.cartoesdecredito;

import pt.portugueseexpress.InvalidCardException;
import pt.portugueseexpress.PortugueseExpress;

public class PortugueseExpressAdapter implements ISistemaDeCartoesDeCreditoAdapter {

	@Override
	public boolean validar(String num, int ccv, int mes, int ano) {
		PortugueseExpress api = new PortugueseExpress();
		api.setNumber(num);
		api.setCcv(ccv);
		api.setMonth(mes);
		api.setYear(ano);
		
		return api.validate();
	}

	@Override
	public boolean cativar(String num, int ccv, int mes, int ano, double qt) {
		PortugueseExpress api = new PortugueseExpress();
		api.setNumber(num);
		api.setCcv(ccv);
		api.setMonth(mes);
		api.setYear(ano);
		
		try {
			api.block(qt);
		} catch (InvalidCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean retirar(String num, int ccv, int mes, int ano, double qt) {
		PortugueseExpress api = new PortugueseExpress();
		api.setNumber(num);
		api.setCcv(ccv);
		api.setMonth(mes);
		api.setYear(ano);
		
		try {
			api.charge(qt);
		} catch (InvalidCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
