package service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.FichaMedicaDAO;
import dao.JogadorDAO;
import model.FichaMedica;
import model.Jogador;
import model.enums.Escala;
import model.enums.NivelLesao;

public class FichaMedicaService {

	private FichaMedicaDAO fichaDAO = new FichaMedicaDAO();
	private JogadorDAO jogadorDAO = new JogadorDAO();
	
	public String consultarFichaMedica(Integer id, Request request) {
		try {
			FichaMedica ficha = fichaDAO.get(id);
			return ficha.toJson().toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao consultar Ficha Medica";
		}
	}//consultar
	
	public String listarFichasMedicas(Request request) {
		try {
			return fichaDAO.toJSON().toString();//provavel de estar errado -> return listaJogadoresJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao consultar Ficha Medica";
		}
	}//listar as fichas
	
	public String adicionarFichaMedica(Request request) {
		Query query = request.getQuery();

		try {
			
			FichaMedica f = new FichaMedica();
		
			f.setJogador(jogadorDAO.get(Integer.parseInt(query.get("id"))));
			f.setNivelDaLesao(NivelLesao.findByValor(Integer.parseInt(query.get("nivel"))));
			f.setDataEntrada(LocalDateTime.parse());//converter texto para data
					
			fichaDAO.add(f);

			return listaJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao adicionar Jogador";
		}
	}
	
	/*
	 * Inserir ficha, deve comunicar com inserir jogador também?
	 * */
	
}
