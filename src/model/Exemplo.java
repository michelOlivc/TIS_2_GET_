package model;

import java.io.File;

import dao.JogadorDAO;
import model.enums.Escala;

public class Exemplo {
	public static void main(String[] args) {
		
		Jogador j = new Jogador("Roberto Carlos", "Lateral");
		j.setDrible(Escala.TRES);
		j.setFolego(Escala.QUATRO);
		j.setVelocidade(Escala.CINCO);
		
		JogadorDAO dao = new JogadorDAO();
		dao.add(j);
//		System.out.println(new File(".").getAbsolutePath());
		
		System.out.println(dao.get(4).toJson().toString());
		
	}
}
