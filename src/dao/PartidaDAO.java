package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Estatistica;
import model.Partida;

public class PartidaDAO implements GenericDAO<Partida, Integer> {
	static final String ARQUIVO = "partida.txt";
	static final String SEQUENCE = "sequence_partida.txt";

	@Override
	public Partida get(Integer id) {
		EstatisticaDAO estatisticaDAO = new EstatisticaDAO();

		Partida retorno = null;
		Partida p = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha;

			while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");

				p = new Partida();
				p.setId(Integer.parseInt(dados[0]));
				List<Estatistica> estatisticasJogador = new ArrayList<Estatistica>();

				if (dados.length > 1 && !dados[1].equals("")) {
					String[] idJogadores = dados[1].split("-");
					for (String s : idJogadores) {
						Integer idEstatistica = Integer.parseInt(s);

						Estatistica estatistica = estatisticaDAO.get(idEstatistica);
						estatisticasJogador.add(estatistica);
					}
					p.setEstatisticasJogador(estatisticasJogador);
				}
				retorno = p;

				if (id.equals(p.getId())) {
					retorno = p;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("ERRO ao ler a Partida '" + p.getId() + "' do disco rígido!");
			e.printStackTrace();
		}
		return retorno;
	}

	public Partida lazyGet(Integer id) {
		Partida retorno = null;
		Partida p = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha;

			while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");

				p = new Partida();
				p.setId(Integer.parseInt(dados[0]));

				retorno = p;

				if (id.equals(p.getId())) {
					retorno = p;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("ERRO ao ler a Partida '" + p.getId() + "' do disco rígido!");
			e.printStackTrace();
		}
		return retorno;
	}
	
	@SuppressWarnings("resource")
	@Override
	public void add(Partida t) {
		try {
			BufferedReader bufferInSequence = new BufferedReader(new FileReader(SEQUENCE));
			BufferedWriter bufferOutEstatisticas = new BufferedWriter(new FileWriter(ARQUIVO, true));
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
			bufferOutEstatisticas.write(generatedId + separadorDeAtributo);

			for (int i = 0; i < t.getEstatisticasJogador().size(); i++) {
				Estatistica estatisticas = t.getEstatisticasJogador().get(i);

				if (i != t.getEstatisticasJogador().size() - 1) {
					bufferOutEstatisticas.write(estatisticas.getId() + "-");
				} else {
					bufferOutEstatisticas.write(estatisticas.getId() + "");
				}
			}

			bufferOutEstatisticas.write(System.getProperty("line.separator"));
			bufferOutEstatisticas.flush();
		} catch (Exception e) {
			System.out.println("Erro ao gravar a Partida.");
			e.printStackTrace();
		}
	}

	@Override

	public void update(Partida q) throws NumberFormatException, IOException {
		List<Partida> partidas = getAll();
		int index = partidas.indexOf(q);
		if (index != -1) {
			partidas.set(index, q);
			saveToFile(partidas);
		}
	}

	@Override
	public void delete(Partida t) throws NumberFormatException, IOException {
		List<Partida> partida = getAll();
		int index = partida.indexOf(t);
		if (index != -1) {
			partida.remove(index);
			saveToFile(partida);
		}
	}

	@Override
	public List<Partida> getAll() throws FileNotFoundException, NumberFormatException, IOException {
		EstatisticaDAO estatisticaDAO = new EstatisticaDAO();

		List<Partida> partida = new ArrayList<Partida>();
		Partida j = null;
		BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO));
		String linha;

		while ((linha = buffer_entrada.readLine()) != null) {
			String[] dados = linha.split(";");

			j = new Partida();
			j.setId(Integer.parseInt(dados[0]));

			if (dados.length > 1 && !dados[1].equals("")) {
				String[] idJogadores = dados[1].split("-");
				for (String s : idJogadores) {
					Integer idEstatistica = Integer.parseInt(s);

					Estatistica estatistica = estatisticaDAO.get(idEstatistica);
					j.getEstatisticasJogador().add(estatistica);
				}
			}
			partida.add(j);
		}
		buffer_entrada.close();
		return partida;
	}

	public void saveToFile(List<Partida> list) throws IOException {

		BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false));
		String separador = ";";
		for (Partida p : list) {
			buffer_saida.write(p.getId() + separador);
			for (int i = 0; i < p.getEstatisticasJogador().size(); i++) {
				Estatistica estatistica = p.getEstatisticasJogador().get(i);

				if (i != p.getEstatisticasJogador().size() - 1) {
					buffer_saida.write(estatistica.getId() + "-");
				} else {
					buffer_saida.write(estatistica.getId() + "");
				}
			}

			buffer_saida.write(System.getProperty("line.separator"));
			buffer_saida.flush();
		}
		buffer_saida.close();

	}

	public Partida ultimaPartidaSalva() throws Exception {
		List<Partida> partidas = getAll();
		Collections.sort(partidas);

		return partidas.get(partidas.size() - 1);

	}

	public Integer saveAndReturnId(Partida p) {
		try {
			BufferedReader bufferInSequence = new BufferedReader(new FileReader(SEQUENCE));

			Integer generatedId;
			String linha = bufferInSequence.readLine();
			
			if (linha != null) {
				generatedId = Integer.parseInt(linha);
				bufferInSequence.close();
			} else {
				generatedId = 1;
			}
			
			add(p);
			return generatedId;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
