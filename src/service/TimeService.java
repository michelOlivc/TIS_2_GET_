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
	
	public String atualizarTime(Request request) {
		Query query = request.getQuery();
		
		try {
			Time time = timeDAO.get();
			time.setNome(query.get("nomeTime"));
			
			timeDAO.update(time);
			time = timeDAO.get();
			
			return time.toJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String incluirJogador(Request request) {
		Query query = request.getQuery();
		try {
			Time time = timeDAO.get();
			String idJogadores = query.get("incluirJogadores");
		
			for(String id : idJogadores.split(",")) {
				Jogador jogador = jogadorDAO.get(Integer.parseInt(id));
				time.adicionarJogador(jogador);
			}
		
			timeDAO.update(time);
			time = timeDAO.get();
			
			return time.toJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String removerJogador(Request request) {
		Query query = request.getQuery();
		try {
			Time time = timeDAO.get();
			String idJogadores = query.get("removerJogadores");
		
			for(String id : idJogadores.split(",")) {
				Jogador jogador = jogadorDAO.get(Integer.parseInt(id));
				time.removerJogador(jogador);
			}
		
			timeDAO.update(time);
			time = timeDAO.get();
			
			return time.toJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
}
