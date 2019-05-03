package model;


public class Estatistica {
	
	private Jogador jogador;
	private int passeDeBola;
	private int gols;
	private int assistencias;
	
	
	public Estatistica(Jogador jogador, int passeDeBola, int gols, int assistencias) {
	
		this.jogador = jogador;
		this.passeDeBola = passeDeBola;
		this.gols = gols;
		this.assistencias = assistencias;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
