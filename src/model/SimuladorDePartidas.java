package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.CampeonatoDAO;
import dao.ContadorCartaoDAO;
import dao.EstatisticaDAO;
import dao.FichaMedicaDAO;
import dao.PartidaDAO;
import dao.TimeDAO;
import model.enums.NivelLesao;

public class SimuladorDePartidas {
	private Random random = new Random();
	private TimeDAO timeDAO = new TimeDAO();
	private CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
	private FichaMedicaDAO fichaMedicaDAO = new FichaMedicaDAO();
	private ContadorCartaoDAO contadorDAO = new ContadorCartaoDAO();
	private EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
	private PartidaDAO partidaDAO = new PartidaDAO();

	public static final int QUANTIDADE_JOGADORES_GOLS = 2;
	public static final int MAX_GOLS = 3;
	public static final int MAX_ASSIST = 15;
	public static final int MAX_PASSES = 40;

	public JSONObject gerarSimulacao(Escalacao escalacao) throws Exception {
		cumprirSuspensoesAfastamentos(escalacao);

		Partida partida = new Partida();
		int max = escalacao.getListaJogador().size() - 1;
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		randomizarParametros(map, max);
		
		List<FichaMedica> fichas = new ArrayList<FichaMedica>();
		List<Contadordecartoes> cartoes = new ArrayList<Contadordecartoes>();
		
		for(Jogador jogador : escalacao.getListaJogador()) {
			int indexJogador = escalacao.getListaJogador().indexOf(jogador);
			
			Estatistica estatistica = gerarEstatistica(indexJogador, map.get("goleadorA"), map.get("goleadorB"));
			estatistica.setJogador(jogador);
			estatistica.setCampeonato(escalacao.getCampeonato());
			
			Integer estatisticaId = estatisticaDAO.saveAndReturnId(estatistica);
			
			if(estatisticaId == null)
				return null;
			
			partida.getEstatisticasJogador().add(estatisticaDAO.get(estatisticaId));

			fichas.add(gerarFichaMedica(indexJogador, jogador, map.get("lesionadoA"), map.get("lesionadoB")));
			cartoes.add(gerarContadorDeCartoes(jogador, escalacao.getCampeonato(), indexJogador, map));
		}
		
		Integer partidaId = partidaDAO.saveAndReturnId(partida);
		
		Campeonato campeonato = campeonatoDAO.get(escalacao.getCampeonato().getId());
		campeonato.getTodasPartidas().add(partidaDAO.lazyGet(partidaId));
		campeonatoDAO.update(campeonato);		
		
		return resultadoEscalacao(fichas, cartoes, partida.getEstatisticasJogador(), campeonato);
	}
	
	private JSONObject resultadoEscalacao(List<FichaMedica> fichas,
			List<Contadordecartoes> cartoes, List<Estatistica> estatisticas, Campeonato campeonato) {
		JSONObject obj = new JSONObject();
		obj.put("campeonato", campeonato.getNome());
		obj.put("rodada", campeonato.getTodasPartidas().size());
		obj.put("fichas", montarFichasJSON(fichas));
		obj.put("cartoes", montarCartoesJSON(cartoes));
		obj.put("estatisticas", montarEstatisticasJSON(estatisticas));
		
		return obj;
	}
	
	private JSONArray montarFichasJSON(List<FichaMedica> fichas) {
		JSONArray array = new JSONArray();
		for(FichaMedica f : fichas) {
			array.put(f.toJson());
		}
		
		return array;
	}
	
	private JSONArray montarCartoesJSON(List<Contadordecartoes> cartoes) {
		JSONArray array = new JSONArray();
		for(Contadordecartoes c : cartoes) {
			array.put(c.toJson());
		}
		
		return array;
	}
	
	private JSONArray montarEstatisticasJSON(List<Estatistica> estatisticas) {
		JSONArray array = new JSONArray();
		for(Estatistica e : estatisticas) {
			array.put(e.toJson());
		}
		
		return array;
	}
	
	private void cumprirSuspensoesAfastamentos(Escalacao escalacao) throws Exception {
		Time time = timeDAO.get();
		
		for(Jogador jogador : time.getListaJogadores()) {
			Contadordecartoes contador = contadorDAO.findByCampeonatoJogador(jogador, escalacao.getCampeonato());
			
			if(contador != null) {
				contador.cumprirSuspensao();
				contadorDAO.update(contador);
			}
			
			FichaMedica ficha = fichaMedicaDAO.findByJogador(jogador);
			
			if(ficha != null) {
				ficha.liberarJogador(jogador);
				fichaMedicaDAO.update(ficha);
			}
		}
	}
	
	private void randomizarParametros(Map<String, Integer> map, int max) {
		Integer goleadorA = random.nextInt(max);
		Integer goleadorB = random.nextInt(max);
		
		while(goleadorA == goleadorB)
			goleadorB = random.nextInt(max);
		
		map.put("goleadorA", goleadorA);
		map.put("goleadorB", goleadorB);
		
		Integer lesionadoA = random.nextInt(max);
		Integer lesionadoB = random.nextInt(max);
		
		while(lesionadoB == lesionadoA)
			lesionadoB = random.nextInt(max);
		
		map.put("lesionadoA", lesionadoA);
		map.put("lesionadoB", lesionadoB);
		
		Integer cartaoAmareloA = random.nextInt(max);
		Integer cartaoAmareloB = random.nextInt(max);
		
		while(cartaoAmareloB == cartaoAmareloA)
			cartaoAmareloB = random.nextInt(max);
		
		map.put("cartaoAmareloA", cartaoAmareloA);
		map.put("cartaoAmareloB", cartaoAmareloB);
		
		Integer cartaoVermelho = random.nextInt(max);
		
		map.put("cartaoVermelho", cartaoVermelho);
		
	}
	
	private Estatistica gerarEstatistica(int indexJogador, int goleadorA, int goleadorB) {
		Estatistica estatistica = new Estatistica();
		
		if(indexJogador == goleadorA || indexJogador == goleadorB)
			estatistica.setGols(random.nextInt(MAX_GOLS));
		else
			estatistica.setGols(0);
		
		estatistica.setAssistencias(random.nextInt(MAX_ASSIST));
		estatistica.setPasseDeBola(random.nextInt(MAX_PASSES));
		
		return estatistica;
	}
	
	private FichaMedica gerarFichaMedica(int indexJogador, Jogador jogador,int lesionadoA, int lesionadoB) throws Exception {
		FichaMedica ficha = fichaMedicaDAO.findByJogador(jogador);
		
		if(ficha != null) {
			if(indexJogador == lesionadoA || indexJogador == lesionadoB) {
				int indexLesao = NivelLesao.values().length - 1;
				ficha.setNivelDaLesao(NivelLesao.findByValor(random.nextInt(indexLesao) * 30));
				fichaMedicaDAO.update(ficha);
			}
		} else {
			ficha = new FichaMedica();
			ficha.setJogador(jogador);
			
			if(indexJogador == lesionadoA || indexJogador == lesionadoB) {
				int indexLesao = NivelLesao.values().length - 1;
				ficha.setNivelDaLesao(NivelLesao.findByValor(indexLesao));
			} else {
				ficha.setNivelDaLesao(NivelLesao.findByValor(0));
			}
			
			fichaMedicaDAO.add(ficha);
		}
		
		return ficha;
	}
	
	private Contadordecartoes gerarContadorDeCartoes(Jogador jogador, Campeonato campeonato, 
			int indexJogador, Map<String, Integer> map) throws Exception {
		
		Contadordecartoes contador = contadorDAO.findByCampeonatoJogador(jogador, campeonato);
		
		if(contador != null) {
			if(indexJogador == map.get("cartaoAmareloA") || indexJogador == map.get("cartaoAmareloB")) {
				contador.setContAmarelo(random.nextInt(2));
			} else if(indexJogador == map.get("cartaoVermelho")) {
				contador.setContVermelho(random.nextInt(1));
			}
			
			contador.suspenderjogador();
			contadorDAO.update(contador);
		} else {
			contador = new Contadordecartoes();
			contador.setJogador(jogador);
			contador.setCampeonato(campeonato);
			
			if(indexJogador == map.get("cartaoAmareloA") || indexJogador == map.get("cartaoAmareloB")) {
				contador.setContAmarelo(random.nextInt(2));
			} else if(indexJogador == map.get("cartaoVermelho")) {
				contador.setContVermelho(random.nextInt(1));
			}
			
			contador.suspenderjogador();
			contadorDAO.add(contador);
		}
		
		return contador;
	}
}