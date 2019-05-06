package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.JogadorService;
import service.TimeService;

public class TimeController implements Controller {
	static TimeService timeService = new TimeService();
	static JogadorService jogadorService = new JogadorService();
	
	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String acao = path.split("/")[2];
			String method = request.getMethod();
			String message;
			String url;
			
			if("GET".equals(method)) {
				if("get".equals(acao)) {
					Integer id = Integer.parseInt(path.split("/")[3]);
					url = jogadorService.consultarJogador(id, request);
					
					if(url != null)
						this.enviaResposta(Status.OK, response, url);
					else
						this.naoEncontrado(response, path);
					
				} else if("load".equals(acao)) {
					message = timeService.carregarTime(request);
					
					if(message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
					
				} else if("list".equals(acao)) {
					message = jogadorService.listarJogadores(request);
					if(message != null && !message.equals(""))
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
				}
			
			} else if("POST".equals(method)) {
				if("include".equals(acao)) {
					url = timeService.incluirJogador(request);
					this.redireciona(Status.CREATED, response, url);
				
				} else if("remove".equals(acao)) {
					url = timeService.removerJogador(request);
					this.redireciona(Status.OK, response, url);
				
				} else if("update".equals(acao)) {
					url = timeService.atualizarNomeDoTime(request);
					this.redireciona(Status.OK, response, url);
				}
			} else {
				this.naoEncontrado(response, path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
