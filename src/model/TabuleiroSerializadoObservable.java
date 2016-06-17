package model;

import java.util.Observable;
import java.util.Observer;

import controller.ControllerTabuleiro;

public class TabuleiroSerializadoObservable extends Observable implements Observer {

	@Override
	public void update(Observable o, Object tabuleiro) {
		if(tabuleiro != null) {
			System.out.println("tabuleiro não é nulo!");
			ControllerTabuleiro controller = (ControllerTabuleiro) tabuleiro;
			if(!controller.serializerIgnore) {
				setChanged();
				notifyObservers(controller.serialize());
			}
			controller.serializerIgnore = false;
		} else {
			System.out.println("Tabuleiro é nulo!");
		}
	}

}
