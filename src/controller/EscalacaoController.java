package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.CampeonatoService;
import service.EscalacaoService;
import service.JogadorService;
import service.TimeService;

public class EscalacaoController implements Controller {
	private EscalacaoService escalacaoService = new EscalacaoService();
	private TimeService timeService = new TimeService();
	private JogadorService jogadorService = new JogadorService();
	private CampeonatoService campeonatoService = new CampeonatoService();
	
	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String acao = path.split("/")[2];
			String method = request.getMethod();
			String message;
			
			if("GET".equals(method)) {
				if("jogador".equals(acao)) {
					Integer id = Integer.parseInt(path.split("/")[3]);
					message = jogadorService.consultarJogador(id, request);
					tratarRetorno(response, path, message);
					
				} else if("jogadores".equals(acao)) {
					message = timeService.carregarTime(request);
					tratarRetorno(response, path, message);
					
				} else if("escalados".equals(acao)) {
					message = escalacaoService.carregarUltimaEscalacao(request);
					tratarRetorno(response, path, message);
					
				} else if("campeonatos".equals(acao)) {
					message = campeonatoService.listarCampeonato(request);
					tratarRetorno(response, path, message);
					
				} else if("include".equals(acao)) {
					message = escalacaoService.escalarJogador(request);
					tratarRetorno(response, path, message);
				
				} else if("remove".equals(acao)) {
					message = escalacaoService.removerJogador(request);
					tratarRetorno(response, path, message);
				
				} else if("clear".equals(acao)) {
					message = escalacaoService.removerTodosOsJogadores(request);
					tratarRetorno(response, path, message);
					
				} else if("finalizar".equals(acao)) {
					message = escalacaoService.finalizarEscalacao(request);
					tratarRetorno(response, path, message);
				}
			} else {
				this.naoEncontrado(response, path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void tratarRetorno(Response response, String path, String retorno) {
		try {
			if(retorno != null)
				this.enviaResposta(Status.OK, response, retorno);
			else
				this.naoEncontrado(response, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
