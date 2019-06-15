package service;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.JogadorDAO;
import dao.PartidaDAO;
import model.Jogador;
import model.Partida;
import model.enums.Escala;

public class PartidaService {
	private PartidaDAO partidaDAO = new PartidaDAO();

	public String consultarPartida(Integer id, Request request) {
		try {
			Partida partida = partidaDAO.get(id);
			return partida.toJson().toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao consultar Partida";
		}
	}
	
//	public String listarPartida(Request request) {
//		try {
//			return listaPartidaJSON().toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Erro ao consultar Partida";
//		}
//	}

	
//	public String adicionarPartida(Request request) {
//		Query query = request.getQuery();
//
//		try {
//			Partida partida = new Partida();
//			partida.setId(Integer.parseInt(query.get("id")));
//			partida.setEstatisticasJogador(query.get("Partida"));
//			partidaDAO.add(partida);
//
//			return listaPartidaJSON().toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Erro ao adicionar Partida";
//		}
//	}
	
//	public String atualizarPartida(Request request) {
//		Query query = request.getQuery();
//
//		try {
//			Partida partida = new Partida();
//			partida.setId(query.getInteger("id"));
//			partida.setEstatisticasJogador(query.get("Partida"));
//			
//			partidaDAO.update(partida);
//
//			return listaPartidaJSON().toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Erro ao atualizar partida";
//		}
//	}
	
//	public String removerPartida(Request request) {
//		Query query = request.getQuery();
//		
//		try {
//			Partida partida = new Partida();
//			partida.setId(query.getInteger("id"));
//			
//			partidaDAO.delete(partida);
//			
//			return listaPartidaJSON().toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Erro ao excluir Partida.";
//		}
//	}
	
//	private JSONObject listaPartidaJSON() throws NumberFormatException, IOException {
//		List<Partida> listaPartida = PartidaDAO.getAll();
//
//		JSONArray array = new JSONArray();
//		for (Partida j : listaPartida) {
//			array.put(j.toJson());
//		}
//		JSONObject obj = new JSONObject();
//		obj.put("listaPartida", new JSONArray(listaPartida));
//
//		return obj;
//	}
}
	