package service;

import org.simpleframework.http.Request;

import dao.FichaMedicaDAO;
import model.FichaMedica;

public class FichaMedicaService {

	private FichaMedicaDAO fichaDAO = new FichaMedicaDAO();
	
	public String consultarFichaMedicaJogador(Integer id, Request request) {
		try {
			for(FichaMedica ficha : fichaDAO.getAll()) {
				if(ficha.getJogador().getId() == id) {
					return ficha.toJson().toString();
				}
			}
			
			return "<ERRO>Ficha não encontrada";
		} catch (Exception e) {
			e.printStackTrace();
			return "<ERRO>Erro ao consultar Ficha Medica";
		}
	}
	
}
