package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.EstatisticaService;
import service.TimeService;

public class EstatisticaController implements Controller {
	static EstatisticaService estatisticaService = new EstatisticaService();
	static TimeService timeService = new TimeService();
			
	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String acao = path.split("/")[2];
			String method = request.getMethod();
			String message;

			if ("GET".equals(method)) {
				if ("get".equals(acao)) {
					message = estatisticaService.consultarEstatisticaPorJogador(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);

				} else if ("suspensos".equals(acao)) {
					message = estatisticaService.consultarSuspensosTimePorCampeonato(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
				} else if ("lesionados".equals(acao)) {
					message = estatisticaService.consultarLesionadosTime(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
				} else if ("campeonatos".equals(acao)) {
					message = estatisticaService.carregarCampeonatos(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
				} else if ("jogadores".equals(acao)) {
					message = timeService.carregarTime(request);

					if (message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
				}
			} else {
				this.naoEncontrado(response, path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
