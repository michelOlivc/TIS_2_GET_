package exceptions;

@SuppressWarnings("serial")
public class JogadorNaoEncontrado extends Exception {
	public JogadorNaoEncontrado() {
		super("Jogador não encontrado");
	}
}
