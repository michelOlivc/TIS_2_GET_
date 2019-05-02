package model;
import java.util.ArrayList;

public class Campeonato {
	private String nome;
	private int jogos;
	private int cont;
	
	 List<Partida> partidas;

	
	
	
	public Campeonato(String nome, int jogos) {
		
		this.nome = nome;
		this.jogos = jogos;
		this.cont=0;
		this.partidas=new ArrayList <Partida>();

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



	public ArrayList<Partida> getTodasPartidas() {
		return partidas;
	}
	public Partida getPartida(int jogos) {
		return partidas.get(jogos);
	}
	public void setUmaPartidas(List<Estatistica> partida,int id) {
		this.partidas.get(id).estatisticasJogador = partida ;
	}
	public void inserirPartida(Partida partida){
		if(cont<jogos){
		this.partidas.add(partida);
		this.cont ++;
	}
	}
}
