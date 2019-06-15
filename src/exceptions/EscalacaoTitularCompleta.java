package exceptions;

@SuppressWarnings("serial")
public class EscalacaoTitularCompleta extends Exception {

	static final int LIMITE_ESCALACAO_TITULAR = 17;

	public EscalacaoTitularCompleta(){
		super("Não é possível inserir mais jogadores pois o limite de "+ LIMITE_ESCALACAO_TITULAR  +"foi excedido");
	}
}
