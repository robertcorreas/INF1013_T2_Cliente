package cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;

public class TratadorProximaJogada extends Observable implements Runnable {

	private Socket socket;

	public TratadorProximaJogada(Socket socket) {
		this.socket = socket;
	}
	
	public static TratadorProximaJogada start(Socket socket) {
		TratadorProximaJogada tratador = new TratadorProximaJogada(socket);
		new Thread(tratador).start();
		return tratador;
	}

	@Override
	public void run() {
		Scanner in_serv;
		try {
			in_serv = new Scanner(socket.getInputStream());
			while (in_serv.hasNextLine()) {
				System.out.println("Recebi tabuleiro!");
				notifyObservers(in_serv.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
