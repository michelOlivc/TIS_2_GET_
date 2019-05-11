package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Jogador;
import model.enums.Escala;

public class JogadorDAO implements GenericDAO<Jogador, Integer> {
	static final String ARQUIVO = "jogadores.txt";
	static final String SEQUENCE = "sequence_jogadores.txt";

	@Override
	public Jogador get(Integer id) {
		Jogador retorno = null;
		Jogador j = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha;

			while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");

				j = new Jogador();
				j.setId(Integer.parseInt(dados[0]));
				j.setNome(dados[1]);
				j.setPosicao(dados[2]);
				j.setFolego(Escala.findByValor(Integer.parseInt(dados[3])));
				j.setVelocidade(Escala.findByValor(Integer.parseInt(dados[4])));
				j.setDrible(Escala.findByValor(Integer.parseInt(dados[5])));

				if (id.equals(j.getId())) {
					retorno = j;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("ERRO ao ler o Jogador '" + j.getId() + "' do disco rígido!");
			e.printStackTrace();
		}
		return retorno;
	}

	@SuppressWarnings("resource")
	@Override
	public void add(Jogador t) {
		try {
			BufferedReader bufferInSequence = new BufferedReader(new FileReader(SEQUENCE));
			BufferedWriter bufferOutJogadores = new BufferedWriter(new FileWriter(ARQUIVO, true));

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
			bufferOutJogadores.write(generatedId + separadorDeAtributo);
			bufferOutJogadores.write(t.getNome() + separadorDeAtributo);
			bufferOutJogadores.write(t.getPosicao() + separadorDeAtributo);
			bufferOutJogadores.write(t.getFolego() + separadorDeAtributo);
			bufferOutJogadores.write(t.getVelocidade() + separadorDeAtributo);
			bufferOutJogadores.write(t.getDrible() + separadorDeAtributo);
			bufferOutJogadores.write(System.getProperty("line.separator"));
			bufferOutJogadores.flush();

		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Jogador no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public void update(Jogador t) throws NumberFormatException, IOException {
		List<Jogador> jogadores = getAll();
		int index = jogadores.indexOf(t);
		if (index != -1) {
			jogadores.set(index, t);
			saveToFile(jogadores);
		}
	}

	@Override
	public void delete(Jogador t) throws NumberFormatException, IOException {
		List<Jogador> jogadores = getAll();
		int index = jogadores.indexOf(t);
		if (index != -1) {
			jogadores.remove(index);
			saveToFile(jogadores);
		}
	}

	@Override
	public List<Jogador> getAll() throws NumberFormatException, IOException {
		List<Jogador> jogadores = new ArrayList<Jogador>();
		Jogador j = null;
		BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO));
		String linha;

		while ((linha = buffer_entrada.readLine()) != null) {
			String[] dados = linha.split(";");

			j = new Jogador();
			j.setId(Integer.parseInt(dados[0]));
			j.setNome(dados[1]);
			j.setPosicao(dados[2]);
			j.setFolego(Escala.findByValor(Integer.parseInt(dados[3])));
			j.setVelocidade(Escala.findByValor(Integer.parseInt(dados[4])));
			j.setDrible(Escala.findByValor(Integer.parseInt(dados[5])));
			jogadores.add(j);
		}

		buffer_entrada.close();
		return jogadores;
	}

	public void saveToFile(List<Jogador> list) throws IOException {
		BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false));
		String separador = ";";
		for (Jogador j : list) {
			buffer_saida.write(j.getId() + separador);
			buffer_saida.write(j.getNome() + separador);
			buffer_saida.write(j.getPosicao() + separador);
			buffer_saida.write(j.getFolego() + separador);
			buffer_saida.write(j.getVelocidade() + separador);
			buffer_saida.write(j.getDrible() + separador);
			buffer_saida.write(System.getProperty("line.separator"));
			buffer_saida.flush();
		}
		buffer_saida.close();
	}

}
