package cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class Conexao implements Observer {

	private Socket socket;
	private static Conexao instance;
	private TratadorProximaJogada tratador;
	
	public static Conexao getInstance() {
		if(instance == null) {
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
	
	public void EnviarTabuleiro(byte[] tabuleiro){
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

	@Override
	public void update(Observable o, Object msg) { // envia o tabuleiro para o servidor
		PrintStream saida;
		try {
			System.out.println("Enviei tabuleiro!");
			saida = new PrintStream(this.socket.getOutputStream());
			saida.println((String)msg);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not send information through connection with server.");
		}
	}
}
