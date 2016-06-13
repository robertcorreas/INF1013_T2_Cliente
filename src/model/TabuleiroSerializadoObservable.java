package model;

import java.util.Observable;
import java.util.Observer;

import cliente.Conexao;
import controller.ControllerTabuleiro;

public class TabuleiroSerializadoObservable extends Observable implements Observer {

	public TabuleiroSerializadoObservable(Observable o) {
		o.addObserver(this);
	}

	@Override
	public void update(Observable o, Object tabuleiro) {
		notifyObservers(((ControllerTabuleiro) tabuleiro).serialize());
	}

}
