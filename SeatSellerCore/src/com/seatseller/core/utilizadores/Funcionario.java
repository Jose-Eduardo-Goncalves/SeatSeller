package com.seatseller.core.utilizadores;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.seatseller.api.INotificacaoReceiver;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;

public class Funcionario extends Utilizador implements Observer {
	
	
	// Hora de início de turnos
	private LocalTime start;
	// Hora de final de turnos
	private LocalTime end;
	private List<Grelha> grelhasObserving;
	private List<INotificacaoReceiver> iNotRec;
	
	public Funcionario(String u, String p, LocalTime start, LocalTime end) {
		super(u, p);
		this.start = start;
		this.end = end;
		grelhasObserving = new ArrayList<>();
		iNotRec = new ArrayList<>();
	}
	
	/**
	 * Deve fazser login apenas se a password estiver correta, 
	 * e se a hora actual estiver entre as horas de inicio e final
	 * de turnos deste Funcionario.
	 * 
	 * @see com.seatseller.core.utilizadores.Utilizador#tryLogin(java.lang.String)
	 */
	@Override
	public boolean tryLogin(String pw) {
		//Usado para obter o tempo em que o funcion�rio est� a fazer login
		LocalTime agora = LocalTime.now();
		
		/*Aproveitando o m�todo j� definido em Utilizador.java (tryLogin(String p)). 
		isAfter e isBefore verificam se o tempo em agora est� depois do inicio do turno e
		antes do fim do turno respetivamente.
		*/
		return super.tryLogin(pw) && agora.isAfter(this.start) && agora.isBefore(this.end); 
	}

	public void associarGrelha(Grelha grelha, INotificacaoReceiver c) {
		// TODO Auto-generated method stub
		grelhasObserving.add(grelha);
		grelha.addObserver(this);
		iNotRec.add(c);
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (o instanceof Grelha && arg instanceof Lugar) {
			for (INotificacaoReceiver notification : iNotRec) {
				notification.notificar(((Grelha)o).toString(), 
						((Lugar)arg).toString());
			} 
		}
	}

	public void desassociarGrelha(Grelha grelha, INotificacaoReceiver c) {
		// TODO Auto-generated method stub
		grelhasObserving.remove(grelha);
		grelha.deleteObserver(this);
		iNotRec.remove(c);
	}

}
