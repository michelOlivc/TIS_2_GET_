package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.CampeonatoService;

public class CampeonatoController implements Controller {
	static CampeonatoService campeonatoService = new CampeonatoService();
	
	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String acao = path.split("/")[2];
			String method = request.getMethod();
			String message;
			String url;

			if ("GET".equals(method)) {
				if ("get".equals(acao)) {
					Integer id = Integer.parseInt(path.split("/")[3]);
					message = campeonatoService.consultarCampeonato(id, request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);

				} else if ("list".equals(acao)) {
					message = campeonatoService.listarCampeonato(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);

				}

			} else if ("POST".equals(method)) {
				if ("add".equals(acao)) {
					url = campeonatoService.adicionarCampeonato(request);
					this.redireciona(Status.CREATED, response, url);

				} else if ("update".equals(acao)) {
					url = campeonatoService.atualizarCampeonato(request);
					this.redireciona(Status.OK, response, url);

				} else if ("delete".equals(acao)) {
					url = campeonatoService.removerCampeonato(request);
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
