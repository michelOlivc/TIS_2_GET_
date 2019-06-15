package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.CampeonatoDAO;
import dao.EscalacaoDAO;
import dao.JogadorDAO;
import model.Campeonato;
import model.Escalacao;
import model.Jogador;
import model.SimuladorDePartidas;

public class EscalacaoService {
	private EscalacaoDAO escalacaoDAO = new EscalacaoDAO();
	private CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
	private JogadorDAO jogadorDAO = new JogadorDAO();
	
	private Escalacao ultimaNaoFinalizadaPorCampeonato(Campeonato campeonato) {
		try {
			List<Escalacao> escalacoes = escalacaoDAO.getAll().stream()
					.filter(e -> campeonato.equals(e.getCampeonato()) && !e.isFinalizado())
					.collect(Collectors.toList());
			
			if(escalacoes != null && !escalacoes.isEmpty()) {
				if(escalacoes.size() > 1) {
					Collections.sort(escalacoes);
					return escalacoes.get(escalacoes.size() - 1);
				} else {
					return escalacoes.get(escalacoes.size() - 1);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String carregarUltimaEscalacao(Request request) {
		try {
			Query query = request.getQuery();
			
			Integer idCampeonato = Integer.parseInt(query.get("campeonato"));
			Campeonato campeonato = campeonatoDAO.lazyGet(idCampeonato);
			
			Escalacao escalacao = ultimaNaoFinalizadaPorCampeonato(campeonato);
			
			if(escalacao != null) {
				return escalacao.toJson().toString();
			} else {
				escalacao = new Escalacao();
				escalacao.setCampeonato(campeonato);
				
				escalacaoDAO.add(escalacao);
				return ultimaNaoFinalizadaPorCampeonato(campeonato)
						.toJson().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao consultar Escalacao";
		}
	}
	
	public String escalarJogador(Request request) {
		try {
			Query query = request.getQuery();
			
			Integer idJogador = Integer.parseInt(query.get("jogador"));
			Integer idCampeonato = Integer.parseInt(query.get("campeonato"));
			
			Jogador jogador = jogadorDAO.lazyGet(idJogador);
			Campeonato campeonato = campeonatoDAO.lazyGet(idCampeonato);
			
			Escalacao escalacao = ultimaNaoFinalizadaPorCampeonato(campeonato);
			escalacao.incluirJogador(jogador);
			
			escalacaoDAO.update(escalacao);
			
			return carregarUltimaEscalacao(request);		
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao escalar jogador.";
		}
	}
	
	public String removerJogador(Request request) {
		try {
			Query query = request.getQuery();
			
			Integer idJogador = Integer.parseInt(query.get("jogador"));
			Integer idCampeonato = Integer.parseInt(query.get("campeonato"));
			
			Jogador jogador = jogadorDAO.lazyGet(idJogador);
			Campeonato campeonato = campeonatoDAO.lazyGet(idCampeonato);
			
			Escalacao escalacao = ultimaNaoFinalizadaPorCampeonato(campeonato);
			escalacao.removerJogador(jogador);
			
			escalacaoDAO.update(escalacao);
			
			return carregarUltimaEscalacao(request);		
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao escalar jogador.";
		}
	}
	
	public String removerTodosOsJogadores(Request request) {
		try {
			Query query = request.getQuery();
			
			Integer idCampeonato = Integer.parseInt(query.get("campeonato"));
			Campeonato campeonato = campeonatoDAO.lazyGet(idCampeonato);
			
			Escalacao escalacao = ultimaNaoFinalizadaPorCampeonato(campeonato);
			escalacao.setListaJogador(new ArrayList<Jogador>());
			
			escalacaoDAO.update(escalacao);
			
			return carregarUltimaEscalacao(request);		
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao remover jogador.";
		}
	}
	
	public String finalizarEscalacao(Request request) {
		try {
			Query query = request.getQuery();
			
			Integer idCampeonato = Integer.parseInt(query.get("campeonato"));
			Campeonato campeonato = campeonatoDAO.lazyGet(idCampeonato);
			
			Escalacao escalacao = ultimaNaoFinalizadaPorCampeonato(campeonato);
			
			// executar o simulador de partidas
			SimuladorDePartidas simulador = new SimuladorDePartidas();
			JSONObject dadosSimulacao = simulador.gerarSimulacao(escalacao);
			
			escalacao.finalizarEscalacao();
			escalacaoDAO.update(escalacao);

			return dadosSimulacao.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
	