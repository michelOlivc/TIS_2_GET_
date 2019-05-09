package model;

import java.util.Random;

import dao.EstatisticaDAO;
import dao.FichaMedicaDAO;
import dao.JogadorDAO;

public class SimuladorDePartidas {

	public static void main(String [] args) {
		
		Estatistica [] estatisticas = new Estatistica [12];
		
		JogadorDAO player = new JogadorDAO();
		EstatisticaDAO est = new EstatisticaDAO();
		FichaMedicaDAO ficha = new FichaMedicaDAO();
		
		for(int i=0;i<11;i++){
			estatisticas[i] = new Estatistica (player.get(i+1),0,0,0);
		}
		
		Random rand = new Random();
		int p = rand.nextInt(50);
		int g = rand.nextInt(20);
		int a = rand.nextInt(10);
		
		for(int i=0;i<11;i++) {
			
			p = rand.nextInt(50);
		    g = rand.nextInt(10);
			a = rand.nextInt(20);
			
			estatisticas[i].setPasseDeBola(p);
			estatisticas[i].setGols(g);
			estatisticas[i].setAssistencias(a);
			
			//get ficha medica por id=i
			//update com dados aleatorios
			//save to file
			
			//System.out.println(estatisticas[i].toJson().toString());
			//est.add(estatisticas[i]);
		}
		
		
		
	}
	
	
	
	

	
	
}
