package com.seatseller.core.utilizadores;

import java.util.ArrayList;
import java.util.List;

import com.seatseller.core.pagamentos.CartaoCredito;
import com.seatseller.core.pagamentos.Pagamento;
import com.seatseller.core.reservas.Reserva;

public class ClienteFinal extends Utilizador {
    private List<CartaoCredito> cartoes;
    private List<Pagamento> pagamentos;
    private List<Reserva> reservas;
    
    public ClienteFinal(String u, String p) {
        super(u, p);
        cartoes = new ArrayList<>();
        pagamentos = new ArrayList<>();
        reservas = new ArrayList<>();
    }
    
    /**
     * Verifica se o cliente tem o cartao de credito com
     * o dado numero na sua lista de cartoes de credito
     * @param numero O numero do cartao de credito
     * @return true se o cliente tem o cartao e false caso contrario
     * @requires numero != ""
     */
    public boolean temCC(String numero) {
        
        for(CartaoCredito cc: cartoes) {
            
            if (cc.getNumero().equals(numero)) {
                return true;
            }
        }
        
        return false;
    }
    
    public CartaoCredito getCC(String numero) {
    	
    	for (CartaoCredito cartaoCredito : cartoes) {
			if(cartaoCredito.getNumero().equals(numero)) {
				return cartaoCredito;
			}
		}
    	
    	return null;
    }
    
    /**
     * Cria um cartao de credito que depois eh associado
     * a este cliente
     * @param numero O numero do cartao de credito a ser criado
     * @param ccv Um codigo de seguranca do cartao
     * @param mes O mes em que expira o cartao
     * @param ano O ano em que expira o cartao
     * @return O cartao de credito criado
     */
    public CartaoCredito criaCC(String numero, int ccv, int mes, int ano) {
        CartaoCredito cc = new CartaoCredito(numero, ccv, mes, ano);
        cartoes.add(cc);
        
        return cc;
    }
    /**
     * Regista um novo pagamento feito por este cliente
     * @param pg O pagamento feito a ser registado
     * @requires pg != null
     */
    public void registarPagamento(Pagamento pg) {
        pagamentos.add(pg);
    }

	public void associarReserva(Reserva reserva) {
		reservas.add(reserva);
	}
}