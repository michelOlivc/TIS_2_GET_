package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.EscalacaoTitularCompleta;
import model.Escalacao;
import model.Jogador;

public class EscalacaoDAO {
	static final String ARQUIVO = "escalacao.txt";
	static final String SEQUENCE = "sequence_escalacao.txt";

	JogadorDAO jogadorDAO = new JogadorDAO();
	CampeonatoDAO campeonatoDAO = new CampeonatoDAO();

	public Escalacao get(Integer id) {
		Escalacao retorno = null;
		Escalacao e = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha = buffer_entrada.readLine();
			if (linha != null) {
				while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");
				e = new Escalacao();
				e.setId(Integer.parseInt(dados[0]));
				e.setFinalizado(Boolean.parseBoolean(dados[1]));
				
				if (dados.length > 2 && !dados[2].equals("")) {
					String[] idJogadores = dados[2].split("-");
					for (String s : idJogadores) {
						Integer iD = Integer.parseInt(s);
						Jogador jogador = jogadorDAO.get(iD);

						e.incluirJogador(jogador);
					}
				}
				e.setCampeonato(campeonatoDAO.get(Integer.parseInt(dados[3]))); 

				if (id.equals(e.getId())) {
					retorno = e;
					break;
				}

			}
			}} catch (Exception ex) {
			System.out.println("Erro ao consultar o Escalação.");
			ex.printStackTrace();
		}
		return retorno;
	}
	
	
	@SuppressWarnings("resource")
	public void add(Escalacao t) {

		try {
			BufferedReader bufferInSequence = new BufferedReader(new FileReader(SEQUENCE));
			BufferedWriter bufferOutEscalacao = new BufferedWriter(new FileWriter(ARQUIVO, true));

			Integer generatedId;
			String linha = bufferInSequence.readLine();
			if (linha != null) {
				generatedId = Integer.parseInt(linha);
				bufferInSequence.close();

				BufferedWriter bufferOutSequence = new BufferedWriter(new FileWriter(SEQUENCE, false));
				bufferOutSequence.write(Integer.toString(generatedId + 1));
				bufferOutSequence.flush();
			} else {
				generatedId = 1;

				BufferedWriter bufferOutSequence = new BufferedWriter(new FileWriter(SEQUENCE, false));
				bufferOutSequence.write(Integer.toString(generatedId + 1));
				bufferOutSequence.flush();
			}

			String separadorDeAtributo = ";";
			bufferOutEscalacao.write(generatedId + separadorDeAtributo);
			bufferOutEscalacao.write(false + separadorDeAtributo);

			for (int i = 0; i < t.getListaJogador().size(); i++) {
				Jogador jogador = t.getListaJogador().get(i);
				if (i != t.getListaJogador().size() - 1) {
					bufferOutEscalacao.write(jogador.getId() + "-");
				} else {
					bufferOutEscalacao.write(jogador.getId() + "");
				}
				
			}
			bufferOutEscalacao.write(separadorDeAtributo);
			bufferOutEscalacao.write(t.getCampeonato().getId() + "");

			
			

			bufferOutEscalacao.write(System.getProperty("line.separator"));
			bufferOutEscalacao.flush();
			bufferOutEscalacao.close();

		} catch (Exception e) {
			System.out.println("Erro ao Adicionar o Jogador");
			e.printStackTrace();
		}
	}

	public void update(Escalacao t) throws NumberFormatException, IOException, EscalacaoTitularCompleta {
		List<Escalacao> escalacoes = getAll();
		int index = escalacoes.indexOf(t);
		if (index != -1) {
			escalacoes.set(index, t);
			saveToFile(escalacoes);
		}
	}

	public void delete(Escalacao t) throws NumberFormatException, IOException, EscalacaoTitularCompleta {
		List<Escalacao> escalacoes = getAll();
		int index = escalacoes.indexOf(t);
		if (index != -1) {
			escalacoes.remove(index);
			saveToFile(escalacoes);
		}
	}

	

	public List<Escalacao> getAll() throws FileNotFoundException, NumberFormatException, IOException, EscalacaoTitularCompleta {

		List<Escalacao> escalacao = new ArrayList<Escalacao>();
		Escalacao e = null;
		BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO));
		String linha;

		while ((linha = buffer_entrada.readLine()) != null) {
			String[] dados = linha.split(";");

			e = new Escalacao();
			e.setId(Integer.parseInt(dados[0]));
			e.setFinalizado(Boolean.parseBoolean(dados[1]));

			if (dados.length > 2 && !dados[2].equals("")) {
				String[] idJogadores = dados[2].split("-");
				for (String s : idJogadores) {
					Integer iD = Integer.parseInt(s);
					Jogador jogador = jogadorDAO.get(iD);

					e.incluirJogador(jogador);
				}
			}
			e.setCampeonato(campeonatoDAO.get(Integer.parseInt(dados[3]))); 
			escalacao.add(e);
		}
		buffer_entrada.close();
		return escalacao;

	}

	public void saveToFile(List<Escalacao> e) throws IOException {

		try (BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false))) {
			String separador = ";";
			for(Escalacao escalacao : e  ) {
				buffer_saida.write(escalacao.getId() + separador);
				buffer_saida.write(escalacao.isFinalizado() + separador);
				for (int i = 0; i < escalacao.getListaJogador().size(); i++) {
					Jogador jogador = escalacao.getListaJogador().get(i);
					if (i != escalacao.getListaJogador().size() - 1) {
						buffer_saida.write(jogador.getId() + "-");
					} else {
						buffer_saida.write(jogador.getId() + "");
					}
					
				}
				buffer_saida.write(separador);
				buffer_saida.write(escalacao.getCampeonato().getId() + separador);
				
				buffer_saida.write(System.getProperty("line.separator"));
				buffer_saida.flush();
			}
		} catch (Exception f) {
			System.out.println("Erro ao gravar a Escalação.");
			f.printStackTrace();

		}
	}
}
