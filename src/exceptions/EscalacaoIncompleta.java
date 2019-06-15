package exceptions;

@SuppressWarnings("serial")
public class EscalacaoIncompleta extends Exception {
	static final int LIMITE_ESCALACAO_TITULAR = 11;

	public EscalacaoIncompleta(){
		super("Não é possível finalizar a escalação. A escalação precisa ter no mínimo " + LIMITE_ESCALACAO_TITULAR  + " jogadores.");
	}
}
