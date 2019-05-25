package service;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.CampeonatoDAO;
import model.Campeonato;


public class CampeonatoService {
	private CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
	
	public String consultarCampeonato(Integer id, Request request) {
		try {
			Campeonato campeonato = campeonatoDAO.get(id);
			
			return campeonato.toJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Campeonato";
		}
	}
	public String listarCampeonato(Request request) {
		try {
			return listaCampeonatoJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Campeonatos";
		}
	}
	public String adicionarCampeonato(Request request) {
		Query query = request.getQuery();

		try {
			Campeonato campeonato = new Campeonato();
			campeonato.setNome(query.get("nome"));
			campeonato.setJogos(query.getInteger("jogos"));
			campeonatoDAO.add(campeonato);

			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao adicionar Campeonato";
		}
	}
	public String atualizarCampeonato(Request request) {
		Query query = request.getQuery();

		try {
			Campeonato campeonato = new Campeonato();
			campeonato.setId(Integer.parseInt(query.get("id")));
			campeonato.setNome(query.get("nome"));
			campeonato.setJogos(query.getInteger("jogos"));
			campeonatoDAO.update(campeonato);

			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao atualizar o campeonato";
		}
	}
	public String removerCampeonato(Request request) {
		Query query = request.getQuery();
		
		try {
			Campeonato campeonato = new Campeonato();
			campeonato.setId(query.getInteger("id"));
			
			campeonatoDAO.delete(campeonato);
			
			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao excluir Campeonato.";
		}
	}

	private JSONObject listaCampeonatoJSON() throws NumberFormatException, IOException {
		List<Campeonato> listaCampeonatos = campeonatoDAO.getAll();

		JSONArray array = new JSONArray();
		for (Campeonato j : listaCampeonatos) {
			array.put(j.toJson());
		}
		JSONObject obj = new JSONObject();
		obj.put("listaCampeonatos", new JSONArray(listaCampeonatos));

		return obj;
	}
}
