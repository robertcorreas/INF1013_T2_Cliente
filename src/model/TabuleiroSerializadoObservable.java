package model;

import java.util.Observable;
import java.util.Observer;

import cliente.Conexao;
import controller.ControllerTabuleiro;

public class TabuleiroSerializadoObservable extends Observable implements Observer {

	@Override
	public void update(Observable o, Object tabuleiro) { // controller notifica
		if(tabuleiro!=null) {
			System.out.println("tabuleiro não é nulo!");
			ControllerTabuleiro controller = (ControllerTabuleiro) tabuleiro;
			if(!controller.serializerIgnore) {
				Conexao.getInstance().EnviarTabuleiro(controller.serialize());
				//setChanged();
				//notifyObservers(controller.serialize());
				
			}
			controller.serializerIgnore = false;
		} else {
			System.out.println("Tabuleiro é nulo!");
		}
	}

}
