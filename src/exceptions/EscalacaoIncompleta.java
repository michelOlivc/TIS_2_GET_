package exceptions;

@SuppressWarnings("serial")
public class EscalacaoIncompleta extends Exception {
	static final int LIMITE_ESCALACAO_TITULAR = 11;

	public EscalacaoIncompleta(){
		super("N�o � poss�vel finalizar a escala��o. A escala��o precisa ter no m�nimo " + LIMITE_ESCALACAO_TITULAR  + " jogadores.");
	}
}
