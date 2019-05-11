package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.JogadorNaoEncontrado;
import exceptions.TimeLimiteExcedido;
import exceptions.TimeListaDeJogadoresVazia;

public class Time implements JsonFormatter {
	static final int LIMITE_JOGADORES = 33;
	
	private Integer id;
	private String nome;
	private List<Jogador> listaJogadores;
	
	public Time() {
		this.listaJogadores = new ArrayList<Jogador>();
	}
	
	public Time(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public List<Jogador> getListaJogadores() {
		return listaJogadores;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setListaJogadores(List<Jogador> listaJogadores) {
		this.listaJogadores = listaJogadores;
	}
	
	public void adicionarJogador(Jogador jogador) throws TimeLimiteExcedido {
		if(this.listaJogadores.size() <= LIMITE_JOGADORES) {
			this.listaJogadores.add(jogador);
		} else {
			throw new TimeLimiteExcedido();
		}
	}
	
	public void removerJogador(Jogador jogador) throws TimeListaDeJogadoresVazia, JogadorNaoEncontrado {
		if(!this.listaJogadores.isEmpty()) {
			int index = this.listaJogadores.indexOf(jogador);
			if(index != -1) {
				this.listaJogadores.remove(index);
			} else {
				throw new JogadorNaoEncontrado();
			}
		} else {
			throw new TimeListaDeJogadoresVazia();
		}
	}
	
	@Override
	public JSONArray toJsonArray() {
		JSONArray array = new JSONArray();
		for(Jogador j : this.listaJogadores) {
			array.put(j.toJson());
		}
		
		return array;
	}
	
	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("nome", this.nome);
		
		if(this.listaJogadores != null && !this.listaJogadores.isEmpty()) {
			obj.put("listaJogadores", toJsonArray());
		}
		
		return obj;
	}
}
