package server;

import java.awt.Desktop;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import controller.JogadorController;
import controller.SituacaoMedicaController;
import controller.TimeController;

public class HttpServer implements Container {
	private TimeController timeController = new TimeController();
	private JogadorController jogadorController = new JogadorController();
	private SituacaoMedicaController situacaoController = new SituacaoMedicaController();
	
	@Override
	public void handle(Request request, Response response) {
		String path = request.getPath().getPath();
		
 		if(path.startsWith("/Time")) {
			timeController.rotearRequisicao(request, response);
		} else if(path.startsWith("/Jogador")) {
			jogadorController.rotearRequisicao(request, response);
		} else if(path.startsWith("/SituacaoMedica")) {
			situacaoController.rotearRequisicao(request, response);
		}
	}
	
	public static void main(String[] list) throws Exception {
		// Se voc� receber uma mensagem 
		// "Address already in use: bind error", 
		// tente mudar a porta.
		
		int porta = 8888;

		// Configura uma conex�o soquete para o servidor HTTP.
		Container container = new HttpServer();
		ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
		Connection conexao = new SocketConnection(servidor);
		SocketAddress endereco = new InetSocketAddress(porta);
		conexao.connect(endereco);
		
		//Testa a conex�o abrindo o navegador padr�o.
		Desktop.getDesktop().browse(new URI("http://127.0.0.1:" + porta));

		System.out.println("Tecle ENTER para interromper o servidor...");
		System.in.read();

		conexao.close();
		servidor.stop();
	}
}
