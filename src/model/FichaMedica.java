package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.json.JSONObject;

import model.enums.NivelLesao;

public class FichaMedica implements JsonFormatter {
	private int id;
	private Jogador jogador;
	private NivelLesao nivelDaLesao;
	private LocalDateTime dataEntrada;

	public FichaMedica() {
		this.dataEntrada = LocalDateTime.now();
	}
	
	public FichaMedica(NivelLesao nivelDaLesao, Jogador jogador) {
		this.jogador = jogador;
		this.nivelDaLesao = nivelDaLesao;
		this.dataEntrada = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public NivelLesao getNivelDaLesao() {
		return nivelDaLesao;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public void setNivelDaLesao(NivelLesao nivelDaLesao) {
		this.nivelDaLesao = nivelDaLesao;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public void liberarJogador(Jogador jogador){
		if(diasAteLiberar(jogador) == 0)
			this.setNivelDaLesao(NivelLesao.findByValor(0));
    }
	
	public void liberarEspecial(Jogador jogador) {
		if(estaAptoLiberacaoEspecial())
			this.setNivelDaLesao(NivelLesao.findByValor(0));
	}

	private boolean estaAptoLiberacaoEspecial() {
		if(diasAteLiberar(jogador) < 7) {
			return true;
		}
		return false;
	}

	public long diasAteLiberar(Jogador jogador) {
		return this.getDataEntrada().until(LocalDateTime.now(), ChronoUnit.DAYS);
	}
	
	@Override
	public JSONObject toJson() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("jogador", this.jogador.toJson());
		obj.put("nivelDaLesao", this.nivelDaLesao.getValor());
		obj.put("dataEntrada", pattern.format(this.dataEntrada));
		obj.put("diasLiberacao", diasAteLiberar(this.jogador));
		
		return obj;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id == ((FichaMedica) obj).id;
	}

}