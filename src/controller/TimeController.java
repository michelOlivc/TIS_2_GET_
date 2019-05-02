package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.TimeService;

public class TimeController implements Controller {
	static TimeService service = new TimeService();
	
	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String method = request.getMethod();
			String message;
			
			if("GET".equals(method)) {
				message = service.carregarTime(request);
				this.enviaResposta(Status.OK, response, message);
				
			} else if("POST".equals(method)) {
				message = service.incluirJogador(request);
				this.enviaResposta(Status.CREATED, response, message);
				
			} else if("PUT".equals(method)) {
				message = service.atualizarTime(request);
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
