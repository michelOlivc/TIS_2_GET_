package model;

import java.io.IOException;
import java.util.Random;
import model.enums.*;
import dao.CampeonatoDAO;
import dao.ContadorCartaoDAO;
import dao.EstatisticaDAO;
import dao.FichaMedicaDAO;
import dao.JogadorDAO;
import dao.PartidaDAO;
import dao.TimeDAO;

import java.util.*;

public class SimuladorDePartidas {

	public static void main(String [] args) throws NumberFormatException, IOException {
		try {
			Random random = new Random();
			
			TimeDAO timeDAO = new TimeDAO();
			Time time = timeDAO.get();
			CampeonatoDAO campDAO = new CampeonatoDAO();
			Campeonato camp = campDAO.get(1);
			
			List<Jogador> listJog = time.getListaJogadores();

			int r = random.nextInt(listJog.size()-1);
			Jogador gol1 = listJog.get(r);
			r = random.nextInt(listJog.size()-1);
			Jogador gol2 = listJog.get(r);
			
			Partida part = new Partida();
			
			for(Jogador j : listJog) {
				Estatistica est = new Estatistica();
				est.setJogador(j);
				est.setAssistencias(gerarAssist());
				est.setPasseDeBola(gerarPasses());
				if(j.equals(gol1)||j.equals(gol2)) {
					est.setGols(gerarGols());
				}else {
					est.setGols(0);
				}
				part.inserirEstatistica(est);
			}//for gera passes e assistencias
			
			
			camp.inserirPartida(part);
			
			
			r = random.nextInt(listJog.size()-1);
			Jogador amarelo1 = listJog.get(r);
			r = random.nextInt(listJog.size()-1);
			Jogador amarelo2 = listJog.get(r);
			r = random.nextInt(listJog.size()-1);
			Jogador vermelho = listJog.get(r);
			
			for(Jogador j : listJog) {
				Contadordecartoes cont = new Contadordecartoes(); 
				
				if(j.equals(amarelo1)||j.equals(amarelo2)){
					cont =	contCartao(j,camp);
					cont.insereCartaoAmarelo();
				}else if(j.equals(vermelho)){
					//cont = contCartao(j, c)
				}
			
			}	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Contadordecartoes contCartao(Jogador j, Campeonato c)throws Exception{
		ContadorCartaoDAO contDAO = new ContadorCartaoDAO();
		List<Contadordecartoes> cont = contDAO.getAll();
		Contadordecartoes ret = null;
		
		for(Contadordecartoes coisa : cont){
			if(coisa.getJogador().equals(j)){
				ret = coisa;
				return ret;
			}else {
				return ret;
			}
		}
		return ret;
	}
	
	public static void gerarLesao(FichaMedica ficha, FichaMedicaDAO fichaDAO, int size)throws Exception {
		Random rand = new Random();
		int id = rand.nextInt(size);
		
		ficha = fichaDAO.get(id);
		ficha.setNivelDaLesao(NivelLesao.findByValor(rand.nextInt(3)*15));
		fichaDAO.update(ficha);
		
	}
	
	public static int gerarCartaoVermelho()throws Exception {
		Random rand = new Random();
		int verm = rand.nextInt(1);
		return verm;
	}
	
	public static int gerarCartaoAmarelo()throws Exception {
		Random rand = new Random();
		int amarelo = rand.nextInt(2);
		return amarelo;
	}

	public static int gerarGols()throws Exception {
		Random rand = new Random();
		int gols = rand.nextInt(4);
		return gols;
	}
	
	public static int gerarPasses()throws Exception {
		Random rand = new Random();
		int passes = rand.nextInt(50);
		return passes;
	}
	
	public static int gerarAssist()throws Exception {
		Random rand = new Random();
		int assist = rand.nextInt(30);
		return assist;
	}
	
	public static String lesao (int i) {
		if(i==4) {return "GRAVE";}else if(i==3) {return "MEDIA";}else if(i==2) {return "BAIXA";}else {return "LIBERADO";}
	}




}//class
/*
fichaMedicaDAO
estatisticaDAO
contadorDAO


gerar assist, passe para todos

sortear 2 jogadores lesão (0-4)
sortear 2 jogadores gols (0-4)

sortear 3 jogadores cartão amarelo (0-2)
sortear 1 jogador cartão vermelho (0-1)*/
