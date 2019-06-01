package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import model.Campeonato;
import model.Estatistica;
import model.Partida;

public class ExemploDAO {
	
	public static void main(String[]args) {
		
		try {
			EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
			PartidaDAO partidaDAO = new PartidaDAO();
			CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
			
			Campeonato c = campeonatoDAO.get(1);
			Partida p = new Partida();
			
			List<Estatistica> listaEstatistica = estatisticaDAO.getAll().stream()
					.filter(e -> e.getCampeonato().equals(c))
					.collect(Collectors.toList());
			
			p.setEstatisticasJogador(listaEstatistica);
			partidaDAO.add(p);
			
			for(Partida pa : partidaDAO.getAll()) {
				c.inserirPartida(pa);
			}
			
			campeonatoDAO.update(c);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
