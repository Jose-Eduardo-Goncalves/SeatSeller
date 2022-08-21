package com.seatseller.core.lugares;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import com.seatseller.core.reservas.LinhaReserva;

public class Lugar {

	private int altIndex;
	private int largIndex;
	private Grelha g;
	private TipoDeLugar padr;
	private List<LinhaReserva> linhasReserva;
	
	/**
	 * Cria um lugar
	 * @param altIndex - O Index da altura onde o lugar está
	 * @param largIndex - O Index da largura onde o lugar está
	 * @param g - A grelha onde se encontra
	 * @param padr - O tipo de lugar que este lugar tem. NULL se não tiver
	 * 				um tipo de lugar
	 * @requires altIndex >0 && largIndex > 0 && Grelha != NULL
	 */
	Lugar(int altIndex, int largIndex, Grelha g, TipoDeLugar padr) {
		this.altIndex = altIndex;
		this.largIndex = largIndex;
		this.g = g;
		this.padr = padr;
		linhasReserva = new LinkedList<>();
	}

	//TODO javadoc
	public void definirTipo(TipoDeLugar tipoDeLugar) {
	
		padr = tipoDeLugar;
		
	}

	public boolean disponivel(LocalDate data, LocalTime hora) {
		
		for (LinhaReserva linhaReserva : linhasReserva) {
			if(!linhaReserva.isDisp(data, hora, this)) {
				return false;
			}
		}
		
		return true;
	}

	//TODO public void adicionaLinhaReserva()
	
	public String getDesignacaoTipo() {
		return padr.getDesig();
	}

	public double getPreco() {

		double ind = g.getIndice();
		double preco = padr.getPreco();
		return preco * ind;
	}
	
	public void associaLinhaReserva(LinhaReserva reserva) {
		linhasReserva.add(reserva);
	}
	
	@Override
	public String toString() {
		//TODO check if right
		return padr.getDesig() + " " + padr.getDescricao() + ":" +
				altIndex + "," + largIndex + " em " + g.getDesignacao();
	}

	public void notificarGrelha() {
		// TODO Auto-generated method stub
		g.notificar(this);
	}

	public TipoDeLugar getTipoLugar() {
		// TODO Auto-generated method stub
		return padr;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return largIndex;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return altIndex;
	}
}
