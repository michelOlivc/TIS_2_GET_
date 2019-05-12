package model.enums;

public enum NivelLesao {
	LIBERADO(0),
	BAIXA(30), 
	MEDIA(60), 
	GRAVE(90);
	
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
	
	public static NivelLesao findByValor(Integer valor) {
		switch(valor) {
			case 0:
				return NivelLesao.LIBERADO;
			case 30:
				return NivelLesao.BAIXA;
			case 60:
				return NivelLesao.MEDIA;
			case 90:
				return NivelLesao.GRAVE;

			default:
				return null;
		}
	}
	
}
