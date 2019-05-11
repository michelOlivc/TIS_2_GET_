package service;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.JogadorDAO;
import dao.TimeDAO;
import model.Jogador;
import model.Time;

public class TimeService {
	private TimeDAO timeDAO = new TimeDAO();
	private JogadorDAO jogadorDAO = new JogadorDAO();
	
	public String carregarTime(Request request) {
		Time time = timeDAO.get();
		return time.toJson().toString();
	}
	
	public String atualizarNomeDoTime(Request request) {
		Query query = request.getQuery();
		
		try {
			Time time = timeDAO.get();
			time.setNome(query.get("nome"));
			
			timeDAO.update(time);
			
			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String incluirJogador(Request request) {
		Query query = request.getQuery();
		try {
			Time time = timeDAO.get();
			String id = query.get("id");
		
			Jogador jogador = jogadorDAO.get(Integer.parseInt(id));
			time.adicionarJogador(jogador);
		
			timeDAO.update(time);
			
			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String removerJogador(Request request) {
		Query query = request.getQuery();
		try {
			Time time = timeDAO.get();
			String id = query.get("id");
		
			Jogador jogador = jogadorDAO.get(Integer.parseInt(id));
			time.removerJogador(jogador);
		
			timeDAO.update(time);
			
			return query.get("url");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
