package service;

import java.io.IOException;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import dao.FichaMedicaDAO;
import dao.JogadorDAO;
import model.FichaMedica;
import model.Jogador;
import model.enums.Escala;

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
			return fichaDAO.toString();//provavel de estar errado -> return listaJogadoresJSON().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao consultar Ficha Medica";
		}
	}//listar as fichas
	
	/*
	 * Inserir ficha, deve comunicar com inserir jogador também?
	 * */
	
}
