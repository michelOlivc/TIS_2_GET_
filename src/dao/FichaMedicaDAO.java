package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.FichaMedica;
import model.enums.NivelLesao;
	
	public class FichaMedicaDAO implements GenericDAO<FichaMedica, Integer> {
		static final String ARQUIVO = "fichasMedicas.txt";
		static final String SEQUENCE = "sequence_fichas.txt";
		
		JogadorDAO jogDAO = new JogadorDAO();

		@Override
		public FichaMedica get(Integer id) {
			FichaMedica retorno = null;
			FichaMedica f = null;

			try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
				String linha;

				while ((linha = buffer_entrada.readLine()) != null) {
					String[] dados = linha.split(";");

					f = new FichaMedica();
					f.setId(Integer.parseInt(dados[0]));
					f.setJogador(jogDAO.get(Integer.parseInt(dados[1])));
					f.setNivelDaLesao(NivelLesao.findByValor(Integer.parseInt(dados[2])));
					f.setDataEntrada(LocalDateTime.parse(dados[3]));

					if (id.equals(f.getId())) {
						retorno = f;
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("ERRO ao ler o Jogador '" + f.getId() + "' do disco rígido!");
				e.printStackTrace();
			}
			return retorno;
		}

		@SuppressWarnings("resource")
		@Override
		public void add(FichaMedica f) {
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
				bufferOutJogadores.write(f.getJogador().getId() + separadorDeAtributo);
				bufferOutJogadores.write(f.getNivelDaLesao().getValor() + separadorDeAtributo);
				bufferOutJogadores.write(f.getDataEntrada().toString() + separadorDeAtributo);
				bufferOutJogadores.write(System.getProperty("line.separator"));
				bufferOutJogadores.flush();

			} catch (Exception e) {
				System.out.println("ERRO ao gravar o Jogador no disco!");
				e.printStackTrace();
			}
		}

		@Override
		public void update(FichaMedica f) throws NumberFormatException, IOException {
			List<FichaMedica> fichas = getAll();
			int index = fichas.indexOf(f);
			if (index != -1) {
				fichas.set(index, f);
				saveToFile(fichas);
			}
		}

		@Override
		public void delete(FichaMedica f) throws NumberFormatException, IOException {
			List<FichaMedica> fichas = getAll();
			int index = fichas.indexOf(f);
			if (index != -1) {
				fichas.remove(index);
				saveToFile(fichas);
			}
		}

		@Override
		public List<FichaMedica> getAll() throws NumberFormatException, IOException {
			List<FichaMedica> fichas = new ArrayList<FichaMedica>();
			FichaMedica f = null;
			BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO));
			String linha;

			while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");

				f = new FichaMedica();
				f.setId(Integer.parseInt(dados[0]));
				f.setJogador(jogDAO.get(Integer.parseInt(dados[1])));
				f.setNivelDaLesao(NivelLesao.findByValor(Integer.parseInt(dados[2])));
				f.setDataEntrada(LocalDateTime.parse(dados[3]));
				
				fichas.add(f);
			}

			buffer_entrada.close();
			return fichas;
		}

		public void saveToFile(List<FichaMedica> list) throws IOException {
			BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false));
			String separador = ";";
			for (FichaMedica f : list) {
				buffer_saida.write(f.getId() + separador);
				buffer_saida.write(f.getJogador().getId() + separador);
				buffer_saida.write(f.getNivelDaLesao().getValor() + separador);
				buffer_saida.write(f.getDataEntrada().toString() + separador);
				buffer_saida.write(System.getProperty("line.separator"));
				buffer_saida.flush();
			}
			buffer_saida.close();
		}
}
