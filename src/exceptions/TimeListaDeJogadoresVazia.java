package exceptions;

@SuppressWarnings("serial")
public class TimeListaDeJogadoresVazia extends Exception {
	public TimeListaDeJogadoresVazia() {
		super("Ação inválida. A lista de jogadores está vazia.");
	}
}
