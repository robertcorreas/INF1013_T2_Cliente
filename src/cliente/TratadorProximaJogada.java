package cliente;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

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
		try {
			InputStream in = socket.getInputStream();
			
			List<Byte> bytes = new ArrayList<Byte>();
			int count = 0;
			while (true) {
				int b = in.read();

				if ((byte)b == Byte.MAX_VALUE) {
					count++;
				} else count = 0;
				
				if (count > Conexao.CODE_SIZE || b < 0) continue;
				else bytes.add((byte) b);
				
				if (count==Conexao.CODE_SIZE) {
					Byte[] messageBytes = new Byte[bytes.size()-Conexao.CODE_SIZE];
					byte[] fullmessageBytes = new byte[bytes.size()-Conexao.CODE_SIZE];
					bytes.subList(0, bytes.size()-Conexao.CODE_SIZE).toArray(messageBytes);
					bytes.clear();
					for(int i = 0; i < messageBytes.length; i++) {
						fullmessageBytes[i] = messageBytes[i];
					}
					setChanged();
					notifyObservers(fullmessageBytes);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
 	}

}
