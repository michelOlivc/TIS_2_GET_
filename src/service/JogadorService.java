package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.ContadorCartaoDAO;
import dao.EstatisticaDAO;
import dao.FichaMedicaDAO;
import dao.JogadorDAO;
import dao.TimeDAO;
import model.Contadordecartoes;
import model.Estatistica;
import model.FichaMedica;
import model.Jogador;
import model.enums.Escala;

public class JogadorService {
	private JogadorDAO jogadorDAO = new JogadorDAO();
	private FichaMedicaDAO fichaDAO = new FichaMedicaDAO();
	private ContadorCartaoDAO contadorDAO = new ContadorCartaoDAO();
	private EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
	private TimeDAO timeDAO = new TimeDAO();

	public String consultarJogador(Integer id, Request request) {
		try {
			Jogador jogador = jogadorDAO.get(id);
			return jogador.toJson().toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Jogador";
		}
	}

	public String listarJogadores(Request request) {
		try {
			return listaJogadoresJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Jogadores";
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
			return "<ERRO>Erro ao adicionar Jogador";
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
			return "<ERRO>Erro ao atualizar Jogador";
		}
	}

	public String removerJogador(Request request) {
		Query query = request.getQuery();
		
		try {
			Jogador jogador = new Jogador();
			jogador.setId(Integer.parseInt(query.get("id")));
			
			if(possuiVinculo(jogador)) {
				return "<ERRO>O jogador não pode ser excluído porque possui vínculo com outros registros do sistema.";
			} else {
				jogadorDAO.delete(jogador);
			}
			
			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao excluir Jogador.";
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
	
	private boolean possuiVinculo(Jogador j) throws NumberFormatException, FileNotFoundException, IOException {
		boolean possui = false;
		
		for(FichaMedica f : fichaDAO.getAll()) {
			possui = possui || f.getJogador().equals(j);
		}
		
		for(Contadordecartoes c : contadorDAO.getAll()) {
			possui = possui || c.getJogador().equals(j);
		}
		
		for(Estatistica e : estatisticaDAO.getAll()) {
			possui = possui || e.getJogador().equals(j);
		}
		
		for(Jogador jogadorTime : timeDAO.get().getListaJogadores()) {
			possui = possui || jogadorTime.equals(j);
		}
		
		return possui;
	}
}
