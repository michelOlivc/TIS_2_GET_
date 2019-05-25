package service;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.CampeonatoDAO;
import dao.ContadorCartaoDAO;
import dao.JogadorDAO;
import model.Contadordecartoes;

public class ContadorService {
	private ContadorCartaoDAO contadorDAO = new ContadorCartaoDAO();
	private JogadorDAO jogadorDAO = new JogadorDAO();
	private CampeonatoDAO campeonatoDAO=new CampeonatoDAO();

	public String consultarContador(Request request) {
		Query query = request.getQuery();
		
		try {
			Integer idJogador = Integer.parseInt(query.get("jogador"));
			Integer idCampeonato = Integer.parseInt(query.get("campeonato"));
			
			for(Contadordecartoes c : contadorDAO.getAll()) {
				if (c.getJogador().getId() == idJogador
						&& c.getCampeonato().getId() == idCampeonato) {
					return c.toJson().toString();
				}
			}
			return "<ERRO>O registro de cartões do jogador não foi encontrado";
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Contador";
		}
	}

	public String listarContadores(Request request) {
		try {
			return listaContadoresJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Contadores";
		}
	}
	public String adicionarContador(Request request) {
		Query query = request.getQuery();

		try {
			Contadordecartoes contador = new Contadordecartoes();
			contador.setJogador(jogadorDAO.get(Integer.parseInt( query.get("Jogador"))));
			contador.setCampeonato(campeonatoDAO.get(Integer.parseInt(query.get("Campeonato"))));
			contador.setContAmarelo(query.getInteger("Amarelos"));
			contador.setContVermelho(query.getInteger("Vermelhos"));
			contador.setSuspenso(query.getBoolean("Suspenso"));

			contadorDAO.add(contador);

			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao adicionar Contador";
		}
	}
	public String atualizarContador(Request request) {
		Query query = request.getQuery();

		try {
			Contadordecartoes contador = new Contadordecartoes();
			contador.setId(query.getInteger("id"));
			contador.setJogador(jogadorDAO.get(Integer.parseInt( query.get("Jogador"))));
			contador.setCampeonato(campeonatoDAO.get(Integer.parseInt(query.get("Campeonato"))));
			contador.setContAmarelo(query.getInteger("Amarelos"));
			contador.setContVermelho(query.getInteger("Vermelhos"));
			contador.setSuspenso(query.getBoolean("Suspenso"));

			contadorDAO.update(contador);

			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao atualizar Contador";
		}
	}	
	public String removerContador(Request request) {
		Query query = request.getQuery();
		
		try {
			Contadordecartoes contador = new Contadordecartoes();
			contador.setId(query.getInteger("id"));
			
			contadorDAO.delete(contador);
			
			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao excluir Contador.";
		}
	}

	private JSONObject listaContadoresJSON() throws NumberFormatException, IOException {
		List<Contadordecartoes> listacontadores = contadorDAO.getAll();

		JSONArray array = new JSONArray();
		for (Contadordecartoes j : listacontadores) {
			array.put(j.toJson());
		}
		JSONObject obj = new JSONObject();
		obj.put("listacontadores", new JSONArray(listacontadores));

		return obj;
	}
}

