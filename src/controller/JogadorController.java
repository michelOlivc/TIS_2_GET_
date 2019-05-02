package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.JogadorService;

public class JogadorController implements Controller {
	static JogadorService service = new JogadorService();
	
	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String method = request.getMethod();
			String message;
			
			if("GET".equals(method)) {
				if(path.split("/").length == 3) {
					Integer id = Integer.parseInt(path.split("/")[2]);
					message = service.consultarJogador(id, request);
					
				} else {
					message = service.listarJogadores(request);
				}
				
				this.enviaResposta(Status.OK, response, message);
			
			} else if("POST".equals(method)) {
				message = service.adicionarJogador(request);
				this.enviaResposta(Status.CREATED, response, message);
				
			} else if("PUT".equals(method)) {
				message = service.atualizarJogador(request);
				if (message == null) {
					this.naoEncontrado(response, path);
				} else {
					this.enviaResposta(Status.OK, response, null);
				}
				
			} else if("DELETE".equals(method)) {
				message = service.removerJogador(request);
				if (message == null) {
					this.naoEncontrado(response, path);
				} else {
					this.enviaResposta(Status.NO_CONTENT, response, null);
				}
				
			} else {
				this.naoEncontrado(response, path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
