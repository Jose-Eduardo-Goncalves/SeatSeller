package com.seatseller.core.lugares;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import com.seatseller.api.INotificacaoReceiver;
import com.seatseller.api.wrappers.Combinacao;
import com.seatseller.core.lugares.alocacao.EncontrarLugarStrategyFactory;
import com.seatseller.core.lugares.alocacao.IEncontrarLugarStrategy;

public class Grelha extends Observable {

	private String designacao;
	private double ind;

	private Lugar lugares[][];

	/**
	 * Cria uma Grelha
	 * @param designacao - A designação da grelha
	 * @param ind - O índice do preço associado à grelha
	 * @requires ind > 0 && designacao != NULL
	 */

	public Grelha(String designacao, double ind) {

		this.designacao = designacao;
		this.ind = ind;

	}

	/**
	 * Cria os lugares de uma grelha
	 * @param alt - A altura da grelha
	 * @param larg - A largura da grelha
	 * @param padr - O tipo de lugar padrão da grelha
	 * @requires alt > 0 && larg > 0
	 */
	public void criaLugares(int alt, int larg, TipoDeLugar padr) {

		lugares = new Lugar[alt][larg];

		for (int y = 0; y < lugares.length; y++) {

			for (int x = 0; x < lugares[y].length; x++) {

				lugares[y][x] = new Lugar(y, x, this, padr);

			}
		}

	}

	//TODO Javadoc
	public void defineTipoLugarPadrao(TipoDeLugar tipoDeLugar) {

		for (int y = 0; y < lugares.length; y++) {

			for (int x = 0; x < lugares[y].length; x++) {

				lugares[y][x].definirTipo(tipoDeLugar);

			}
		}


	}

	//TODO 
	public boolean coordenadasValidas(int y, int x) {
		
		return y >= 0 && x >= 0 && y < lugares.length && x < lugares[y].length ;
	}

	//TODO javadoc
	public void defineTipoLugar(int i, int j, TipoDeLugar tp) {
		
		lugares[i][j].definirTipo(tp);
	}

	/**
	 * @return the designacao
	 */
	public String getDesignacao() {
		return designacao;
	}

	public double getIndice() {
		return ind;
	}
	
	public List<Combinacao> getCombinacoes(LocalDate data, LocalTime hora) {
		// TODO 
		
		//4.2.0)
		String desig = getDesignacao();
		
		//4.2.1)
		List<Combinacao> lc = new LinkedList<>();
		
		//4.2.2)
		for (Lugar[] arrLugar : lugares) {
			for (Lugar lugar : arrLugar) {
				
				boolean disp = lugar.disponivel(data, hora);
				
				//4.2.3)
				if(disp) {
					String tp = lugar.getDesignacaoTipo();
					
					//4.2.4)
					verificaTipoIgual(tp, lc);
					
					//4.2.5)
					double preco = lugar.getPreco();
					
					//4.2.6) verificar se existe alguma combinação
					//para o tipo de lugar tp
					
					boolean existeComb = existeCombinacao(lc, tp);
					
					//4.2.7)
					if(!existeComb) {
						Combinacao novaC = new Combinacao(desig, tp, preco);
						lc.add(novaC);
						
					}
					
				}
			}
		}
		
		
		
		return lc;
	}
	
	
	
	private boolean existeCombinacao(List<Combinacao> lc, String tp) {

		if(lc.isEmpty()) {
			return false;
		}
		
		for (Combinacao combinacao : lc) {
			if(combinacao.getTipoDeLugar().equals(tp)) {
				return true;
			}
		}
		
		return false;
	}

	private void verificaTipoIgual(String tp, List<Combinacao> lc) {
		
		for (Combinacao combinacao : lc) {
			//4.2.4.1)
			String tl = combinacao.getTipoDeLugar();
			
			//4.2.4.2)
			if(tp.equals(tl)) {
				combinacao.aumentaDisponibilidade();
			}
		}
		
	}

	public void notificar(Lugar lugar) {
		// TODO Auto-generated method stub
		this.setChanged();
		this.notifyObservers(lugar);
	}

	public Lugar getDisponivel (TipoDeLugar tp, LocalDate d, LocalTime t) {
		IEncontrarLugarStrategy strat = EncontrarLugarStrategyFactory
										.getInstance().getEncontrarLugarStrategy();
		
		return strat.getLugar(this, tp, d, t).get();
		
	}
	
	public Lugar[][] getLugares () {
		return lugares;
	}
	
	
}
