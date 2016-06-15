package model;

import java.util.Observable;
import java.util.Observer;

import cliente.Conexao;
import controller.ControllerTabuleiro;

public class TabuleiroSerializadoObservable extends Observable implements Observer {

	@Override
	public void update(Observable o, Object tabuleiro) {
		if(tabuleiro != null) {
			System.out.println("tabuleiro não é nulo!");
			setChanged();
			notifyObservers(((ControllerTabuleiro) tabuleiro).serialize());
		} else {
			System.out.println("Tabuleiro é nulo!");
		}
	}

}
