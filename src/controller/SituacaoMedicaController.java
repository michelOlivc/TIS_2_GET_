package controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import service.FichaMedicaService;
import service.JogadorService;

public class SituacaoMedicaController implements Controller {
	static FichaMedicaService fichaService = new FichaMedicaService();
	static JogadorService jogadorService = new JogadorService();
	
	@Override
	public void rotearRequisicao(Request request, Response response) {
		try {
			String path = request.getPath().getPath();
			String acao = path.split("/")[2];
			String method = request.getMethod();
			String message;
//			String url;
			
			if("GET".equals(method)) {
				if("get".equals(acao)) {
					Integer id = Integer.parseInt(path.split("/")[3]);
					message = fichaService.consultarFichaMedicaJogador(id, request);
					
					if(message != null)
						this.enviaResposta(Status.OK, response, message);
					else
						this.naoEncontrado(response, path);
					
				} else if("list".equals(acao)) {
					message = jogadorService.listarJogadores(request);
					
					if(message != null)
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
