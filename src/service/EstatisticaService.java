package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.CampeonatoDAO;
import dao.ContadorCartaoDAO;
import dao.EstatisticaDAO;
import dao.FichaMedicaDAO;
import dao.JogadorDAO;
import dao.TimeDAO;
import model.Campeonato;
import model.Contadordecartoes;
import model.Estatistica;
import model.FichaMedica;
import model.Jogador;
import model.Partida;

public class EstatisticaService {
	private EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
	private JogadorDAO jogadorDAO = new JogadorDAO();
	private CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
	private TimeDAO timeDAO = new TimeDAO();
	private ContadorCartaoDAO contadorDAO = new ContadorCartaoDAO();
	private FichaMedicaDAO fichaDAO = new FichaMedicaDAO();
	
	private CampeonatoService campeonatoService = new CampeonatoService();
	
	public String consultarEstatisticaPorJogador(Request request) {
		try {
			Query query = request.getQuery();
			
			Integer idJogador = Integer.parseInt(query.get("idJogador"));
			Integer idCampeonato = Integer.parseInt(query.get("idCampeonato"));
			
			Jogador jogador = jogadorDAO.get(idJogador);
			Campeonato campeonato = campeonatoDAO.lazyGet(idCampeonato);
			
			List<Estatistica> resultados = estatisticaDAO.getAll().stream()
					.filter(e -> jogador.equals(e.getJogador()) 
							&& campeonato.equals(e.getCampeonato()))
					.collect(Collectors.toList());
			
			StringBuilder json = new StringBuilder();
			json.append("{ \"ultima\" : " + resultados.get(resultados.size() - 1).toJson() + ",")
				.append("\"media\" : " + mediaToJson(jogador, campeonatoDAO.get(idCampeonato)) + " }");
			
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar estatatística";
		}
	}
	
	public String carregarCampeonatos(Request request) {
		return campeonatoService.listarCampeonato(request);
	}
	
	public String consultarSuspensosTimePorCampeonato(Request request) {
		try {
			Query query = request.getQuery();
			
			Campeonato campeonato = campeonatoDAO.get(Integer.parseInt(query.get("campeonato")));
			double numSuspensos = 0.0, comUmAmarelo = 0.0, comDoisAmarelos = 0.0, comUmVermelho = 0.0;
			
			List<Jogador> jogadores =  timeDAO.get().getListaJogadores();
			List<Contadordecartoes> contadoresCartao = contadorDAO.getAll();
			
			for(Jogador jogador : jogadores) {
				for(Contadordecartoes contador : contadoresCartao) {
					if(jogador.equals(contador.getJogador())
							&& campeonato.equals(contador.getCampeonato())) {
						if(contador.isSuspenso()) 
							numSuspensos += 1;
						if(contador.getContAmarelo() == 1)
							comUmAmarelo += 1;
						if(contador.getContAmarelo() == 2)
							comDoisAmarelos += 1;
						if(contador.getContVermelho() == 1)
							comUmVermelho += 1;
					}
				}
			}
			
			JSONObject suspensos = new JSONObject();
			suspensos.put("numero", numSuspensos);
			suspensos.put("porcentagem", numSuspensos * 100 / jogadores.size() + "%");
			
			JSONObject umAmarelo = new JSONObject();
			umAmarelo.put("numero", comUmAmarelo);
			umAmarelo.put("porcentagem", comUmAmarelo * 100 / jogadores.size() + "%");
			
			JSONObject doisAmarelo = new JSONObject();
			doisAmarelo.put("numero", comDoisAmarelos);
			doisAmarelo.put("porcentagem", comDoisAmarelos * 100 / jogadores.size() + "%");
			
			JSONObject umVermelho = new JSONObject();
			umVermelho.put("numero", comUmVermelho);
			umVermelho.put("porcentagem", comUmVermelho * 100 / jogadores.size() + "%");
			
			JSONObject obj = new JSONObject();
			obj.put("campeonato", campeonato.getNome());
			obj.put("suspensos", suspensos);
			obj.put("comUmAmarelo", umAmarelo);
			obj.put("comDoisAmarelos", doisAmarelo);
			obj.put("comUmVermelho", umVermelho);
			
			return obj.toString();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			return "<ERRO>Houve um problema ao carregar as estatisticas.";
		}
	}
	
	public String consultarLesionadosTime(Request request) {
		try {
			List<Jogador> jogadores =  timeDAO.get().getListaJogadores();
			List<FichaMedica> fichasMedicas = fichaDAO.getAll();
			
			int lesionados = 0;
			
			for(Jogador jogador : jogadores) {
				for(FichaMedica ficha : fichasMedicas) {
					if(jogador.equals(ficha.getJogador()) && ficha.getNivelDaLesao().getValor() != 0) {
						lesionados++;
					}
				}
			}
			
			JSONObject obj = new JSONObject();
			obj.put("numLesionados", lesionados);
			obj.put("porcentoLesionados", lesionados * 100 / jogadores.size() + "%");
			
			return obj.toString();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			return "<ERRO>Houve um problema ao carregar as estatisticas.";
		}
	}
	
	private String mediaToJson(Jogador jogador, Campeonato campeonato) {
		List<Estatistica> estatisticasJogador = new ArrayList<Estatistica>();
		
		for(Partida p : campeonato.getTodasPartidas()) {
			List<Estatistica> resultados = p.getEstatisticasJogador()
					.stream()
					.filter(e -> jogador.equals(e.getJogador()) && campeonato.equals(e.getCampeonato()))
					.collect(Collectors.toList());
			
			estatisticasJogador.addAll(resultados);
		}
		
		double gols = estatisticasJogador.stream()
				.mapToInt(Estatistica::getGols)
				.average()
				.getAsDouble();
		
		double assist = estatisticasJogador.stream()
				.mapToInt(Estatistica::getAssistencias)
				.average()
				.getAsDouble();
		
		double passe = estatisticasJogador.stream()
				.mapToInt(Estatistica::getPasseDeBola)
				.average()
				.getAsDouble();
		
		JSONObject obj = new JSONObject();
		obj.put("jogador", jogador.toJson());
		obj.put("passes", passe);
		obj.put("gols", gols);
		obj.put("assistencias", assist);
		
		return obj.toString();
	}
}
