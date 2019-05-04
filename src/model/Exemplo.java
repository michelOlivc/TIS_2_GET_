package model;

import java.util.ArrayList;

import dao.TimeDAO;

public class Exemplo {
	public static void main(String[] args) {
		Time time = new Time();
		
		time.setId(1);
		time.setNome("Clube Atlético Mineiro");
		time.setListaJogadores(new ArrayList<Jogador>());
		
		TimeDAO dao = new TimeDAO();
		dao.add(time);
		
		System.out.println(dao.get().toJson().toString());
		
		//FichaMedica teste = new FichaMedica(1, joao, GRAVE, );
	}
}
