package model;

import java.io.IOException;
import java.util.Random;
import model.enums.*;

import dao.EstatisticaDAO;
import dao.FichaMedicaDAO;
import dao.JogadorDAO;
import dao.TimeDAO;

public class SimuladorDePartidas {

	public static void main(String [] args) throws NumberFormatException, IOException {
		
		Estatistica [] estatisticas = new Estatistica [12];
		
		JogadorDAO player = new JogadorDAO();
		TimeDAO timeDAO = new TimeDAO();
		EstatisticaDAO est = new EstatisticaDAO();
		FichaMedicaDAO fichaDAO = new FichaMedicaDAO();
		FichaMedica ficha = new FichaMedica();
		
		for(int i=0;i<11;i++){
			estatisticas[i] = new Estatistica (player.get(i+1),0,0,0);
		}
		
		Random rand = new Random();
		int passeDeBola = rand.nextInt(50);
		int gols = rand.nextInt(10);
		int assistencias = rand.nextInt(20);
		int lesao = rand.nextInt(4);
		
		for(int i=0;i<11;i++) {
			
			passeDeBola = rand.nextInt(50);
		    
			assistencias = rand.nextInt(20);
			
			estatisticas[i].setPasseDeBola(passeDeBola);
			estatisticas[i].setGols(gols);
			estatisticas[i].setAssistencias(assistencias);
			
			//System.out.println(estatisticas[i].toJson().toString());
			//est.add(estatisticas[i]);
		}
		
		
		
		
	}
	
	
	
	

	
	
}
