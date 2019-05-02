package model;

public class Contadordecartoes {
	private Jogador jogador;
	private Campeonato campeonato;
	private int contAmarelo;
	private int contVermelho;
	private boolean suspenso;
	
    public ContadorDeCartoes() {
    	
    }
    
	public Contadordecartoes(int contAmarelo, int contVermelho, Campeonato campeonato, Jogador jogador,boolean suspenso) {
		this.contAmarelo = 0;
		this.contVermelho = 0;
		this.campeonato = campeonato;
		this.jogador = jogador;
		this.setSuspenso(false);
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

	public void insereCartaoAmarelo() {
		if (contAmarelo < 3) {
			this.contAmarelo++;
		} else if (contAmarelo == 3) {
			this.contAmarelo = 0;
		}
	}

	public void insereCartaoVermelho() {
		if (contVermelho < 1) {
			this.contVermelho++;
		} else if (contVermelho == 1) {
			this.contVermelho = 0;
		}
	}

	public boolean testaQuantAmarelo() {
		if (contAmarelo < 3)
			return false;
		else
			return true;
	}

	public boolean testaQuantVemerlho() {
		if (contVermelho < 1)
			return false;
		else
			return true;
	}

	public void suspenderjogador() {
		if(testaQuantAmarelo()==true || testaQuantVemerlho()==true )
			set Suspenso(true);
	}

	public void cumprirSuspensao() {
		if(suspenderjogador()==true) {
		if(testaQuantVemerlho==true) {
			contVermelho==0
		}
		if(testaQuantAmarelo==true) {
				contAmerelo==0
		}
		set Suspenso(false);
	}
	}
}
