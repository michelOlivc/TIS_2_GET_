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

import controller.CampeonatoController;
import controller.CartoesController;
import controller.JogadorController;
import controller.SituacaoMedicaController;
import controller.TimeController;

public class HttpServer implements Container {
	private TimeController timeController = new TimeController();
	private JogadorController jogadorController = new JogadorController();
	private SituacaoMedicaController situacaoController = new SituacaoMedicaController();
	private CartoesController cartoesController = new CartoesController();
	private CampeonatoController campeonatoController = new CampeonatoController();
	
	@Override
	public void handle(Request request, Response response) {
		String path = request.getPath().getPath();
		
 		if(path.startsWith("/Time")) {
			timeController.rotearRequisicao(request, response);
		} else if(path.startsWith("/Jogador")) {
			jogadorController.rotearRequisicao(request, response);
		} else if(path.startsWith("/SituacaoMedica")) {
			situacaoController.rotearRequisicao(request, response);
		} else if(path.startsWith("/Cartoes")) {
			cartoesController.rotearRequisicao(request, response);
		} else if(path.startsWith("/Campeonato")) {
			campeonatoController.rotearRequisicao(request, response);
		}
	}
	
	public static void main(String[] list) throws Exception {
		// Se você receber uma mensagem 
		// "Address already in use: bind error", 
		// tente mudar a porta.
		
		int porta = 8888;

		// Configura uma conexão soquete para o servidor HTTP.
		Container container = new HttpServer();
		ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
		Connection conexao = new SocketConnection(servidor);
		SocketAddress endereco = new InetSocketAddress(porta);
		conexao.connect(endereco);
		
		//Testa a conexão abrindo o navegador padrão.
		String url = Class.forName("server.HttpServer").getClassLoader().getResource("").getPath();
		String pathIndex = "file://" + url.substring(0, url.length() - 4) + "view/html/index.html";
		
		Desktop.getDesktop().browse(new URI(pathIndex));

		System.out.println("Tecle ENTER para interromper o servidor...");
		System.in.read();

		conexao.close();
		servidor.stop();
	}
}
