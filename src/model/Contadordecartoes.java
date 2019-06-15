package model;

import org.json.JSONObject;

public class Contadordecartoes implements JsonFormatter {
	private Jogador jogador;
	private Campeonato campeonato;
	private int contAmarelo;
	private int contVermelho;
	private boolean suspenso;
	private Integer id;
	
	public Contadordecartoes() {

	}

	public Contadordecartoes(int contAmarelo, int contVermelho, Campeonato campeonato, Jogador jogador) {
		this.contAmarelo = 0;
		this.contVermelho = 0;
		this.campeonato = campeonato;
		this.jogador = jogador;
		this.setSuspenso(false);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public boolean isSuspenso() {
		return suspenso;
	}

	public void setSuspenso(boolean suspenso) {
		this.suspenso = suspenso;
	}

	public int getContAmarelo() {
		return contAmarelo;
	}

	public int getContVermelho() {
		return contVermelho;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setContAmarelo(int contAmarelo) {
		this.contAmarelo = contAmarelo;
	}

	public void setContVermelho(int contVermelho) {
		this.contVermelho = contVermelho;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}
	
	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("jogador", this.jogador.toJson());
		obj.put("campeonato", this.campeonato.toJson());
		obj.put("cartoesAmarelos", this.contAmarelo);
		obj.put("cartoesVermelhos", this.contVermelho);
		obj.put("suspenso", this.suspenso);
		
		return obj;
	}
	@Override
	public boolean equals(Object obj) {
		return this.id == ((Contadordecartoes) obj).id;
	}

	private boolean testaQuantAmarelo() {
		if (contAmarelo < 3)
			return false;
		else
			return true;
	}

	private boolean testaQuantVermelho() {
		if (contVermelho < 1)
			return false;
		else
			return true;
	}

	public void suspenderjogador() {
		if (testaQuantAmarelo() == true || testaQuantVermelho() == true)
			setSuspenso(true);
	}

	public void cumprirSuspensao() {
		if (isSuspenso() == true) {
			if (testaQuantVermelho() == true)
				contVermelho = 0;
			if (testaQuantAmarelo() == true)
				contAmarelo = 0;
			
			setSuspenso(false);
		}
	}
}
