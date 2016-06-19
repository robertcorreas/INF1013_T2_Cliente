package cliente;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class TratadorProximaJogada extends Observable implements Runnable {

	private static final int CODE_SIZE = 20;
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
				//if (b == -1) continue;
				if ((byte)b == Byte.MAX_VALUE) {
					count++;
				} else count = 0;
				
				if (count > CODE_SIZE || b < 0) continue;
				else bytes.add((byte) b);
				
				if (count==CODE_SIZE) {
					Byte[] bytesarr = new Byte[bytes.size()-CODE_SIZE];
					byte[] bytesarr2 = new byte[bytes.size()-CODE_SIZE];
					bytes.subList(0, bytes.size()-CODE_SIZE).toArray(bytesarr);
					bytes.clear();
					for(int i = 0; i < bytesarr.length; i++) {
						bytesarr2[i] = bytesarr[i];
					}
					System.out.println("Recebi tabuleiro!");
					setChanged();
					notifyObservers(bytesarr2);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		
// 		Scanner in_serv;
// 		try {
// 			in_serv = new Scanner(socket.getInputStream());
// 			while (in_serv.hasNextLine()) {
// 				System.out.println("Recebi tabuleiro!");
//				setChanged();
// 				notifyObservers(in_serv.nextLine());
// 			}
// 		} catch (IOException e) {
// 			
// 		}
 	}

}
