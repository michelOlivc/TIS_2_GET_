package service;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.JogadorDAO;
import model.Jogador;
import model.enums.Escala;

public class JogadorService {
	private JogadorDAO jogadorDAO = new JogadorDAO();

	public String consultarJogador(Integer id, Request request) {
		try {
			Jogador jogador = jogadorDAO.get(id);
			return jogador.toJson().toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao consultar Jogador";
		}
	}

	public String listarJogadores(Request request) {
		try {
			return listaJogadoresJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao consultar Jogadores";
		}
	}

	public String adicionarJogador(Request request) {
		Query query = request.getQuery();

		try {
			Jogador jogador = new Jogador();
			jogador.setNome(query.get("nome"));
			jogador.setPosicao(query.get("posicao"));
			jogador.setFolego(Escala.findByValor(query.getInteger("folego")));
			jogador.setVelocidade(Escala.findByValor(query.getInteger("velocidade")));
			jogador.setDrible(Escala.findByValor(query.getInteger("drible")));

			jogadorDAO.add(jogador);

			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao adicionar Jogador";
		}
	}

	public String atualizarJogador(Request request) {
		Query query = request.getQuery();

		try {
			Jogador jogador = new Jogador();
			jogador.setId(query.getInteger("id"));
			jogador.setNome(query.get("nome"));
			jogador.setPosicao(query.get("posicao"));
			jogador.setFolego(Escala.findByValor(query.getInteger("folego")));
			jogador.setVelocidade(Escala.findByValor(query.getInteger("velocidade")));
			jogador.setDrible(Escala.findByValor(query.getInteger("drible")));

			jogadorDAO.update(jogador);

			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao atualizar Jogador";
		}
	}

	public String removerJogador(Request request) {
		Query query = request.getQuery();
		
		try {
			Jogador jogador = new Jogador();
			jogador.setId(Integer.parseInt(query.get("id")));
			
			jogadorDAO.delete(jogador);
			
			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao excluir Jogador.";
		}
	}

	private JSONObject listaJogadoresJSON() throws NumberFormatException, IOException {
		List<Jogador> listaJogadores = jogadorDAO.getAll();

		JSONArray array = new JSONArray();
		for (Jogador j : listaJogadores) {
			array.put(j.toJson());
		}
		JSONObject obj = new JSONObject();
		obj.put("listaJogadores", new JSONArray(listaJogadores));

		return obj;
	}
}
