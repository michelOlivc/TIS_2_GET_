package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Campeonato;


public class CampeonatoDAO  implements GenericDAO<Campeonato, Integer> {
	static final String ARQUIVO = "campeonato.txt";
	static final String SEQUENCE = "sequence_campeonato.txt";

	@Override
	public Campeonato get(Integer id) {
		Campeonato retorno = null;
		Campeonato c = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha;

			while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");

				c = new Campeonato();
				c.setId(Integer.parseInt(dados[0]));
				c.setNome(dados[1]);
				c.setJogos(Integer.parseInt(dados[2]));
	
				if (id.equals(c.getId())) {
					retorno = c;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("ERRO ao ler o campeonato '" + c.getId() + "' do disco rígido!");
			e.printStackTrace();
		}
		return retorno;
	}
	@SuppressWarnings("resource")
	@Override
	public void add(Campeonato t) {
		try {
			BufferedReader bufferInSequence = new BufferedReader(new FileReader(SEQUENCE));
			BufferedWriter bufferOutCampeonato = new BufferedWriter(new FileWriter(ARQUIVO, true));

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
			bufferOutCampeonato.write(generatedId + separadorDeAtributo);
			bufferOutCampeonato.write(t.getNome() + separadorDeAtributo);
			bufferOutCampeonato.write(t.getJogos()+"");
			bufferOutCampeonato.write(System.getProperty("line.separator"));
			bufferOutCampeonato.flush();

		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Campeonato no disco!");
			e.printStackTrace();
		}
	}
	@Override
	public void update(Campeonato t) throws NumberFormatException, IOException {
		List<Campeonato> Campeonato = getAll();
		int index = Campeonato.indexOf(t);
		if (index != -1) {
			Campeonato.set(index, t);
			saveToFile(Campeonato);
		}
	}

	@Override
	public void delete(Campeonato t) throws NumberFormatException, IOException{
		List<Campeonato> Campeonato = getAll();
		int index = Campeonato.indexOf(t);
		if (index != -1) {
			Campeonato.remove(index);
			saveToFile(Campeonato);
		}
	}

	@Override
	public List<Campeonato> getAll() throws NumberFormatException, IOException {
		List<Campeonato> Campeonato = new ArrayList<Campeonato>();
		Campeonato j = null;
		BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO));
		String linha;

		while ((linha = buffer_entrada.readLine()) != null) {
			String[] dados = linha.split(";");

			j = new Campeonato();
			j.setId(Integer.parseInt(dados[0]));
			j.setNome(dados[1]);
			j.setJogos(Integer.parseInt(dados[2]));
			Campeonato.add(j);
		}

		buffer_entrada.close();
		return 	Campeonato;
	}

	public void saveToFile(List<Campeonato> list) throws IOException {
		BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false));
		String separador = ";";
		for (Campeonato j : list) {
			buffer_saida.write(j.getId() + separador);
			buffer_saida.write(j.getNome() + separador);
			buffer_saida.write(j.getJogos()+"");
			buffer_saida.write(System.getProperty("line.separator"));
			buffer_saida.flush();
		}
		buffer_saida.close();
	}


}
