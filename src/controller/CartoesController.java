package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.CampeonatoService;
import service.ContadorService;
import service.JogadorService;

public class CartoesController implements Controller {
	static ContadorService contadorService = new ContadorService();
	static JogadorService jogadorService = new JogadorService();
	static CampeonatoService campeonatoService = new CampeonatoService();

	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String acao = path.split("/")[2];
			String method = request.getMethod();
			String message;
//			String url;

			if ("GET".equals(method)) {
				if ("get".equals(acao)) {
					message = contadorService.consultarContador(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);

				} else if ("list".equals(acao)) {
					message = jogadorService.listarJogadores(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
				} else if ("listCampeonato".equals(acao)) {
					message = campeonatoService.listarCampeonato(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
