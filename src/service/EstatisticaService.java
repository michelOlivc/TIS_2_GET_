package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONArray;
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
	
	public String consultarEstatisticaPorJogador(Request request) {
		try {
			Query query = request.getQuery();
			
			Jogador jogador = jogadorDAO.get(Integer.parseInt(query.get("idJogador")));
			Campeonato campeonato = campeonatoDAO.get(Integer.parseInt(query.get("idCampeonato")));
			
			List<Estatistica> resultados = estatisticaDAO.getAll().stream()
					.filter(e -> jogador.equals(e.getJogador()) 
							&& campeonato.equals(e.getCampeonato()))
					.collect(Collectors.toList());
			
			return resultados.get(resultados.size() - 1)
					.toJson()
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar estatística";
		}
	}
	
	public String consultarMediaEstatisticasPorCampeonato(Request request) {
		try {
			Query query = request.getQuery();
			
			Jogador jogador = jogadorDAO.get(Integer.parseInt(query.get("idJogador")));
			Campeonato campeonato = campeonatoDAO.get(Integer.parseInt(query.get("idCampeonato")));
			
			String json = mediaToJson(jogador, campeonato);
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar estatística";
		}
	}
	
	public String listarEstatistica(Request request) {
		try {
			return listaEstatisticaJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Estatisticas";
		}
	}
	
	private JSONObject listaEstatisticaJSON() throws NumberFormatException, IOException {
		List<Estatistica> listaEstatisticas = estatisticaDAO.getAll();

		JSONArray array = new JSONArray();
		for (Estatistica j : listaEstatisticas) {
			array.put(j.toJson());
		}
		JSONObject obj = new JSONObject();
		obj.put("listaEstatisticas", new JSONArray(listaEstatisticas));

		return obj;
	}

	public String suspensosTimePorCampeonato(Request request) {
		try {
			Query query = request.getQuery();
			
			Campeonato campeonato = campeonatoDAO.get(Integer.parseInt(query.get("idCampeonato")));
			double suspensos = 0.0, comUmAmarelo = 0.0, comDoisAmarelos = 0.0, comUmVermelho = 0.0;
			List<Jogador> jogadores =  timeDAO.get().getListaJogadores();
			List<Contadordecartoes> contadoresCartao = contadorDAO.getAll();
			
			for(Jogador jogador : jogadores) {
				for(Contadordecartoes contador : contadoresCartao) {
					if(jogador.equals(contador.getJogador())
							&& campeonato.equals(contador.getCampeonato())) {
						
						if(contador.isSuspenso()) 
							suspensos += 1;
						if(contador.getContAmarelo() == 1)
							comUmAmarelo += 1;
						if(contador.getContAmarelo() == 2)
							comDoisAmarelos += 1;
						if(contador.getContVermelho() == 1)
							comUmVermelho += 1;
					}
				}
			}
			
			StringBuilder json = new StringBuilder();
			json.append("{ ");
			json.append(("\"suspensos\" : \"" + suspensos * 100 / jogadores.size() + "%\", "));
			json.append(("\"comUmAmarelo\" : \"" + comUmAmarelo * 100 / jogadores.size() + "%\", "));
			json.append(("\"comDoisAmarelos\" : \"" + comDoisAmarelos * 100 / jogadores.size() + "%\", "));
			json.append(("\"comUmVermelho\" : \"" + comUmVermelho * 100 / jogadores.size() + "%\""));
			json.append(" }");
			
			return json.toString();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			return "<ERRO>Houve um problema ao carregar as estatisticas.";
		}
	}
	
	public String lesionadosTime(Request request) {
		Query query = request.getQuery();
		
		List<Jogador> jogadores =  timeDAO.get().getListaJogadores();
		
		try {
			List<FichaMedica> fichasMedicas = fichaDAO.getAll();
			
			double suspensos = 0.0, comUmAmarelo = 0.0, comDoisAmarelos = 0.0, comUmVermelho = 0.0;
			
			for(Jogador jogador : jogadores) {
				for(Contadordecartoes contador : contadoresCartao) {
					if(jogador.equals(contador.getJogador())
							&& campeonato.equals(contador.getCampeonato())) {
						
						// usar stream para contar quantos suspensos, com um amarelo....
						
						/*
						 * Exemplo:
						 * 
						 * public int totalEmEstoque() {
								return listaDeProdutos.stream()
								.mapToInt(Produto::getQuant)
								.reduce(0, (x,y) −> x + y);
						   }
						 * 
						 */
						
						if(contador.isSuspenso()) 
							suspensos += 1;
						if(contador.getContAmarelo() == 1)
							comUmAmarelo += 1;
						if(contador.getContAmarelo() == 2)
							comDoisAmarelos += 1;
						if(contador.getContVermelho() == 1)
							comUmVermelho += 1;
					}
				}
			}
			
			StringBuilder json = new StringBuilder();
			json.append("{ ");
			json.append(("\"suspensos\" : \"" + suspensos * 100 / jogadores.size() + "%\", "));
			json.append(("\"comUmAmarelo\" : \"" + comUmAmarelo * 100 / jogadores.size() + "%\", "));
			json.append(("\"comDoisAmarelos\" : \"" + comDoisAmarelos * 100 / jogadores.size() + "%\", "));
			json.append(("\"comUmVermelho\" : \"" + comUmVermelho * 100 / jogadores.size() + "%\""));
			json.append(" }");
			
			return json.toString();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			return "<ERRO>Houve um problema ao carregar as estatisticas.";
		}
	}
	
	public String mediaToJson(Jogador jogador, Campeonato campeonato) {
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
		
		StringBuilder json = new StringBuilder("{ \"tipo\" : \"media\", ");
		json.append("\"passeDeBola\" : \"" + passe + "\", ");
		json.append("\"gols\" : \"" + gols + "\", ");
		json.append("\"assistencias\" : \"" + assist + "\" }");
		
		return json.toString();
	}
	
	
	public <T> Collector<T, ?, T> toElement() {
	    return Collectors.collectingAndThen(
	            Collectors.toList(),
	            list -> {
	                if (list.size() != 1) {
	                    throw new IllegalStateException();
	                }
	                return list.get(0);
	            }
	    );
	}
}
