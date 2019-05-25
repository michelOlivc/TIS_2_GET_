package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Contadordecartoes;

public class ContadorCartaoDAO implements GenericDAO<Contadordecartoes, Integer> {
	static final String ARQUIVO = "Contador.txt";
	static final String SEQUENCE = "sequence_Contador.txt";
	
	CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
	JogadorDAO jogadorDAO=new JogadorDAO();

	@Override
	public Contadordecartoes get(Integer id) {
		Contadordecartoes retorno = null;
		Contadordecartoes j = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha;

			while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");

				j = new Contadordecartoes();
				j.setId(Integer.parseInt(dados[0]));
				j.setJogador(jogadorDAO.get(Integer.parseInt(dados[1])));
				j.setCampeonato(campeonatoDAO.get(Integer.parseInt(dados[2])));
				j.setSuspenso(Boolean.parseBoolean(dados[3]));
				j.setContAmarelo(Integer.parseInt(dados[4]));
				j.setContVermelho(Integer.parseInt(dados[5]));

				if (id.equals(j.getId())) {
					retorno = j;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("ERRO ao ler o Contador'" + j.getId() + "' do disco rígido!");
			e.printStackTrace();
		}
		return retorno;
	}
	@SuppressWarnings("resource")
	@Override
	public void add(Contadordecartoes t) {
		try {
			BufferedReader bufferInSequence = new BufferedReader(new FileReader(SEQUENCE));
			BufferedWriter bufferOutContadordecartoes = new BufferedWriter(new FileWriter(ARQUIVO, true));

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
			bufferOutContadordecartoes.write(generatedId + separadorDeAtributo);
			bufferOutContadordecartoes.write(t.getJogador().getId() + separadorDeAtributo);
			bufferOutContadordecartoes.write(t.getCampeonato().getId() + separadorDeAtributo);
			bufferOutContadordecartoes.write(t.getContAmarelo() + separadorDeAtributo);
			bufferOutContadordecartoes.write(t.getContVermelho() + separadorDeAtributo);
			bufferOutContadordecartoes.write(t.isSuspenso() + "");
			bufferOutContadordecartoes.write(System.getProperty("line.separator"));
			bufferOutContadordecartoes.flush();

		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Cartao no disco!");
			e.printStackTrace();
		}
	}
	@Override
	public void update(Contadordecartoes t) throws NumberFormatException, IOException {
		List<Contadordecartoes> Contadordecartoes = getAll();
		int index = Contadordecartoes.indexOf(t);
		if (index != -1) {
			Contadordecartoes.set(index, t);
			saveToFile(Contadordecartoes);
		}
	}

	@Override
	public void delete(Contadordecartoes t) throws NumberFormatException, IOException {
		List<Contadordecartoes> Contadordecartoes = getAll();
		int index = Contadordecartoes.indexOf(t);
		if (index != -1) {
			Contadordecartoes.remove(index);
			saveToFile(Contadordecartoes);
		}
	}

	@Override
	public List<Contadordecartoes> getAll() throws NumberFormatException, IOException {
		List<Contadordecartoes> Contadordecartoes = new ArrayList<Contadordecartoes>();
		Contadordecartoes j = null;
		BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO));
		String linha;

		while ((linha = buffer_entrada.readLine()) != null) {
			String[] dados = linha.split(";");

			j = new Contadordecartoes();
			j.setId(Integer.parseInt(dados[0]));
			j.setJogador(jogadorDAO.get(Integer.parseInt(dados[1])));
			j.setCampeonato(campeonatoDAO.get(Integer.parseInt(dados[2])));
			j.setContAmarelo(Integer.parseInt(dados[3]));
			j.setContVermelho(Integer.parseInt(dados[4]));
			j.setSuspenso(Boolean.parseBoolean(dados[5]));
			Contadordecartoes.add(j);
		}

		buffer_entrada.close();
		return Contadordecartoes;
	}

	public void saveToFile(List<Contadordecartoes> list) throws IOException {
		BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false));
		String separador = ";";
		for (Contadordecartoes j : list) {
			buffer_saida.write(j.getId() + separador);
			buffer_saida.write(j.getJogador().getId() + separador);
			buffer_saida.write(j.getCampeonato().getId() + separador);
			buffer_saida.write(j.isSuspenso() + separador);
			buffer_saida.write(j.getContAmarelo() + separador);
			buffer_saida.write(j.getContVermelho() + separador);
			buffer_saida.write(System.getProperty("line.separator"));
			buffer_saida.flush();
		}
		buffer_saida.close();
	}


}
