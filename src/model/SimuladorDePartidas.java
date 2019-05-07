package model;

import java.util.Random;

import dao.JogadorDAO;

public class SimuladorDePartidas {

	public static void main(String [] args) {
		
		Estatistica [] estatisticas = new Estatistica [12];
		
		JogadorDAO player = new JogadorDAO();
		
		estatisticas[0] = new Estatistica (player.get(1),0,0,0);
		estatisticas[1] = new Estatistica (player.get(2),0,0,0);
		estatisticas[2] = new Estatistica (player.get(3),0,0,0);
		estatisticas[3] = new Estatistica (player.get(4),0,0,0);
		estatisticas[4] = new Estatistica (player.get(5),0,0,0);
		estatisticas[5] = new Estatistica (player.get(6),0,0,0);
		estatisticas[6] = new Estatistica (player.get(7),0,0,0);
		estatisticas[7] = new Estatistica (player.get(8),0,0,0);
		estatisticas[8] = new Estatistica (player.get(9),0,0,0);
		estatisticas[9] = new Estatistica (player.get(10),0,0,0);
		estatisticas[10] = new Estatistica (player.get(11),0,0,0);
		
		Random rand = new Random();
		int p = rand.nextInt(50);
		int g = rand.nextInt(50);
		int a = rand.nextInt(50);
		
		for(int i=0;i<11;i++) {
			
			p = rand.nextInt(50);
		    g = rand.nextInt(50);
			a = rand.nextInt(50);
			
			estatisticas[i].setPasseDeBola(p);
			estatisticas[i].setGols(g);
			estatisticas[i].setAssistencias(a);
		}
	}
	
	
	
	

	
	
}
