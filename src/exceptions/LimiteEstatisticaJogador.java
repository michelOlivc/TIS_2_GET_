package exceptions;

@SuppressWarnings("serial")
public class LimiteEstatisticaJogador extends Exception{
	private final static Integer LIMITE_ESTATISTICA_JOGADOR = 18;
	
	public LimiteEstatisticaJogador() {
		super("N�o � poss�vel inserir mais estatistica pois o limite de "+ LIMITE_ESTATISTICA_JOGADOR+"foi excedido");
	}
}
