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
			String acao = path.split("/")[2];
			String method = request.getMethod();
			String message;
			String url;
			
			if("GET".equals(method)) {
				if("get".equals(acao)) {
					Integer id = Integer.parseInt(path.split("/")[3]);
					message = service.consultarJogador(id, request);
					
					if(message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
					
				} else if("list".equals(acao)) {
					message = service.listarJogadores(request);
					
					if(message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
					
				} 
			
			} else if("POST".equals(method)) {
				if("add".equals(acao)) {
					url = service.adicionarJogador(request);
					this.redireciona(Status.CREATED, response, url);
				
				} else if("update".equals(acao)) {
					url = service.atualizarJogador(request);
					this.redireciona(Status.OK, response, url);
				
				} else if("delete".equals(acao)) {
					url = service.removerJogador(request);
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
