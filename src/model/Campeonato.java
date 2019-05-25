package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Campeonato implements JsonFormatter {
	private String nome;
	private int jogos;
	private int cont;
	private Integer id;
	private List<Partida> partidas;

	public Campeonato() {

	}

	public Campeonato(String nome, int jogos) {
		this.nome = nome;
		this.jogos = jogos;
		this.cont = 0;
		this.partidas = new ArrayList<Partida>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public int getJogos() {
		return jogos;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setJogos(int jogos) {
		this.jogos = jogos;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("nome", this.nome);
		obj.put("jogos", this.jogos);
		obj.put("cont", this.cont);
		return obj;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id == ((Campeonato) obj).id;
	}

	public List<Partida> getTodasPartidas() {
		return partidas;
	}

	public Partida getPartida(int jogos) {
		return partidas.get(jogos);
	}

	public void inserirPartida(Partida partida) {
		if (cont < jogos) {
			this.partidas.add(partida);
			this.cont++;
		}
	}
}
