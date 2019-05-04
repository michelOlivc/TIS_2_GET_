package model.enums;

public enum NivelLesao {

	Baixa(30), 
	Media(60), 
	Grave(90);
	
private Integer valor;
	
	private NivelLesao(Integer valor) {
		this.valor = valor;
	}
	
	public Integer getValor() {
		return valor;
	}
	
	@Override
	public String toString() {
		return this.valor.toString();
	}
	
}
