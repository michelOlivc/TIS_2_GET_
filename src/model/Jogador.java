package model;

import org.json.JSONObject;

import model.enums.Escala;

public class Jogador implements JsonFormatter {
	private Integer id;
	private String nome;		
	private String posicao;		
	private Escala folego;		
	private Escala velocidade;	
	private Escala drible;
	
	public Jogador() {
		
	}
	
	public Jogador(String nome, String posicao) {
		this.nome = nome;
		this.posicao = posicao;
	}
	
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getPosicao() {
		return posicao;
	}
	public Escala getFolego() {
		return folego;
	}
	public Escala getVelocidade() {
		return velocidade;
	}
	public Escala getDrible() {
		return drible;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}
	public void setFolego(Escala folego) {
		this.folego = folego;
	}
	public void setVelocidade(Escala velocidade) {
		this.velocidade = velocidade;
	}
	public void setDrible(Escala drible) {
		this.drible = drible;
	}
	
	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("nome", this.nome);
		obj.put("posicao", this.posicao);
		obj.put("folego", this.folego);
		obj.put("velocidade", this.velocidade);
		obj.put("drible", this.drible);
		
		return obj;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id == ((Jogador) obj).id;
	}
}
 	