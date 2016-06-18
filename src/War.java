import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import cliente.Conexao;
import cliente.TratadorProximaJogada;
import view.Configuracao;


public class War {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Conexao.getInstance();
	
		Configuracao.getInstance();
		
	}

}
