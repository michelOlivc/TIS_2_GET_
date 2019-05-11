package model;
import java.util.ArrayList;
import java.util.List;

public class Campeonato {
	private String nome;
	private int jogos;
	private int cont;
	
	private List<Partida> partidas;

	
	
	
	public Campeonato(String nome, int jogos) {
		
		this.nome = nome;
		this.jogos = jogos;
		this.cont=0;
		this.partidas = new ArrayList <Partida>();

	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getJogos() {
		return jogos;
	}

	public void setJogos(int jogos) {
		this.jogos = jogos;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	public void setUmaPartidas(List<Estatistica> partida,int id) {
		this.partidas.get(id).estatisticasJogador = partida ;
	}
	
	public void inserirPartida(Partida partida){
		if(cont<jogos) {
			this.partidas.add(partida);
			this.cont ++;
		}
	}
}
