package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.EscalacaoIncompleta;
import exceptions.EscalacaoTitularCompleta;

public class Escalacao implements JsonFormatter, Comparable<Escalacao> {

	private Integer id;
	private boolean finalizado;
	private List<Jogador> listaJogador;
	private Campeonato campeonato;
	
	static final int LIMITE_ESCALACAO_TITULAR = 11;

	public Escalacao() {
		this.setFinalizado(false);
		this.listaJogador = new ArrayList<Jogador>();
	}

	public void incluirJogador(Jogador jogador) throws EscalacaoTitularCompleta{
		if (listaJogador.size() < 17) {
			this.listaJogador.add(jogador);
		} else {
			throw new EscalacaoTitularCompleta();
		}
	}

	public void removerJogador(Jogador jogador) {
		int index = this.listaJogador.indexOf(jogador);
		
		if(index != -1) {
			this.listaJogador.remove(index);
		}
	}

	public void finalizarEscalacao() throws EscalacaoIncompleta {
		if (listaJogador.size() >= 11)
			setFinalizado(true);
		else
			throw new EscalacaoIncompleta();
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public List<Jogador> getListaJogador() {
		return listaJogador;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public void setListaJogador(List<Jogador> listaJogador) {
		this.listaJogador = listaJogador;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("listaJogadores",toJsonArray());
		obj.put("finalizado", this.finalizado);
		obj.put("campeonato", this.campeonato.toJson());

		return obj;
	}
	
	@Override // exemplo de time
	public JSONArray toJsonArray() {
		JSONArray array = new JSONArray();
		for (Jogador j : this.listaJogador) {
			array.put(j.toJson());
		}
		return array;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id == ((Escalacao) obj).id;
	}

	@Override
	public int compareTo(Escalacao e) {
		if(this.getId() < e.getId())
			return -1;
		if(this.getId() > e.getId())
			return 1;
		return 0;
	}

}
