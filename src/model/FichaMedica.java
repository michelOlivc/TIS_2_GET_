package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import model.enums.NivelLesao;

public class FichaMedica implements JsonFormatter {
	private int id;
	private Jogador jogador;
	private NivelLesao nivelDaLesao;
	private LocalDateTime dataEntrada;

	public FichaMedica() {}
	
	public FichaMedica(int id, NivelLesao nivelDaLesao, LocalDateTime dataEntrada) {
		this.id = id;
		this.nivelDaLesao = nivelDaLesao;
		this.dataEntrada = dataEntrada;
	}

	public int getId() {
		return id;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public NivelLesao getNivelDaLesao() {
		return nivelDaLesao;
	}

	public String getDataEntrada() {
		return dataEntrada.toString();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public void setNivelDaLesao(NivelLesao nivelDaLesao) {
		this.nivelDaLesao = nivelDaLesao;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public boolean liberarJogador(Jogador jogador){
      /*fazer um if para cada nivel de lesao e comparar com a data de entrada
       * se a data para cada nivel for maior que 30 proporcionalmente retorna true
       * if(nivel lesao = 1 && data entrada > 30) return true*/
		
		if(nivelDaLesao.getValor() > diasAteLiberar(jogador)){
			return true;
		}else {
			return false;
		}
   }

	public void estarAptoLiberacaoEspecial() {
		/*retorna true se tal jogador pode ser liberado especificamente*/
	}

	public int diasAteLiberar(Jogador jogador) {
		/*pegar a current data time e retornar de acorod com o nivel da lesao quanto
		 * falta para liberação*/
		
		LocalDateTime dataAtual =  LocalDateTime.now();
		int atual = dataAtual.getHour()*24;
		int entrada = dataEntrada.getHour()*24;
		
		return ((nivelDaLesao.getValor()+entrada)-atual);
	}
	
	@Override
	public JSONObject toJson() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
		obj.put("jogador", this.jogador.toJson());
		obj.put("nivelDaLesao", this.nivelDaLesao.getValor());
		obj.put("dataEntrada", pattern.format(this.dataEntrada));
		
		return obj;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.id == ((FichaMedica) obj).id;
	}

}