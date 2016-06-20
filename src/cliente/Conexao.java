package cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import controller.ControllerTabuleiro;

public class Conexao implements Observer {
	private final static String SERVER_HOST = "127.0.0.1";
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
			return new Socket(SERVER_HOST, 5500);
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
			ControllerTabuleiro controller = (ControllerTabuleiro) tabuleiro;
			if (!controller.serializerIgnore) {
				this.sendTabuleiro(tabuleiroAsMessage(controller));
			}
			controller.serializerIgnore = false;
		}
	}
	
	private byte[] tabuleiroAsMessage(ControllerTabuleiro controller) {
		byte[] tabuleiroBytes = controller.serialize();
		byte[] messageBytes = new byte[tabuleiroBytes.length + CODE_SIZE];
		for (int i = 0; i < tabuleiroBytes.length; i++) {
			messageBytes[i] = tabuleiroBytes[i];
		}
		for (int i = 0; i < CODE_SIZE; i++) {
			messageBytes[tabuleiroBytes.length + i] = Byte.MAX_VALUE;
		}
		return messageBytes;
	}

	private void sendTabuleiro(byte[] tabuleiro){
		PrintStream saida;
		try {
			saida = new PrintStream(this.socket.getOutputStream());
			saida.write(tabuleiro);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not send information through connection with server.");
		}
	}

}
