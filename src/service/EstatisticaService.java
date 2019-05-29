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
import dao.EstatisticaDAO;
import dao.JogadorDAO;
import model.Campeonato;
import model.Estatistica;
import model.Jogador;
import model.Partida;

public class EstatisticaService {
	private EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
	private JogadorDAO jogadorDAO = new JogadorDAO();
	private CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
	
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
			
			List<Estatistica> estatisticasJogador = new ArrayList<Estatistica>();
			
			for(Partida p : campeonato.getTodasPartidas()) {
				List<Estatistica> resultados = p.getEstatisticasJogador().stream()
						.filter(e -> jogador.equals(e.getJogador()) && campeonato.equals(e.getCampeonato()))
						.collect(Collectors.toList());
				
				estatisticasJogador.addAll(resultados);
			}
			
			// criar função para tirar a média das estatísticas
			
			return resultado.toJson().toString();
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
