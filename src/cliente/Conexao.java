package cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import controller.ControllerTabuleiro;

public class Conexao implements Observer {

	public final static int CODE_SIZE = 20;
	private Socket socket;
	private static Conexao instance;
	private TratadorProximaJogada tratador;
	
	public static Conexao getInstance() {
		if (instance == null) {
			instance = new Conexao(getSocket());
		}
		return instance;
	}

	private static Socket getSocket() {
		try {
			return new Socket("127.0.0.1", 5500);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Could not establish connection with server.");
	}

	private Conexao(Socket socket) {
		this.socket = socket;
		this.tratador = TratadorProximaJogada.start(socket);
	}

	public void addParaSincronizar(Observer o) {
		tratador.addObserver(o);
	}
	
	@Override
	public void update(Observable o, Object tabuleiro) {
		if (tabuleiro!=null) {
			System.out.println("tabuleiro não é nulo!");
			ControllerTabuleiro controller = (ControllerTabuleiro) tabuleiro;
			if (!controller.serializerIgnore) {
				this.enviarTabuleiro(tabuleiroAsMessage(controller));
			}
			controller.serializerIgnore = false;
		} else {
			System.out.println("Tabuleiro é nulo!");
		}
	}
	
	private byte[] tabuleiroAsMessage(ControllerTabuleiro controller) {
		byte[] boarr = controller.serialize();
		byte[] serialized = new byte[boarr.length + CODE_SIZE];
		for (int i = 0; i < boarr.length; i++) {
			serialized[i] = boarr[i];
		}
		for (int i = 0; i < CODE_SIZE; i++) {
			serialized[boarr.length + i] = Byte.MAX_VALUE;
		}
		return serialized;
	}

	private void enviarTabuleiro(byte[] tabuleiro){
		PrintStream saida;
		try {
			System.out.println("Enviei tabuleiro!");
			saida = new PrintStream(this.socket.getOutputStream());
			saida.write(tabuleiro);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not send information through connection with server.");
		}
	}

}
