package model;

import org.json.JSONObject;

public class Estatistica implements JsonFormatter {
	
	private Jogador jogador;
	private int passeDeBola;
	private int gols;
	private int assistencias;
	private Integer id;
	
	
	
	public Estatistica(Jogador jogador, int passeDeBola, int gols, int assistencias) {
	
		this.jogador = jogador;
		this.passeDeBola = passeDeBola;
		this.gols = gols;
		this.assistencias = assistencias;
	}
	
	public Estatistica() {
		
	}


	public Jogador getJogador() {
		return jogador;
	}


	public int getPasseDeBola() {
		return passeDeBola;
	}


	public int getGols() {
		return gols;
	}


	public int getAssistencias() {
		return assistencias;
	}


	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}


	public void setPasseDeBola(int passeDeBola) {
		this.passeDeBola = passeDeBola;
	}


	public void setGols(int gols) {
		this.gols = gols;
	}


	public void setAssistencias(int assistencias) {
		this.assistencias = assistencias;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("jogador", this.jogador.toJson());
		obj.put("passe de bola", this.passeDeBola);
		obj.put("gols", this.gols);
		obj.put("assistencias", this.assistencias);
		
		return obj;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id == ((Estatistica) obj).id;
	}
	
}