package model.enums;

public enum Escala {
	UM(1), 
	DOIS(2), 
	TRES(3), 
	QUATRO(4), 
	CINCO(5);
	
	private Integer valor;
	
	private Escala(Integer valor) {
		this.valor = valor;
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public String toString() {
		return "" + this.valor;
	}
	
	public static Escala findByValor(Integer valor) {
		switch(valor) {
			case 1:
				return Escala.UM;
			case 2:
				return Escala.DOIS;
			case 3:
				return Escala.TRES;
			case 4:
				return Escala.QUATRO;
			case 5:
				return Escala.CINCO;
			default:
				return null;
		}
	}
}
