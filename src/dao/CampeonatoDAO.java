package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Campeonato;
import model.Partida;


public class CampeonatoDAO  implements GenericDAO<Campeonato, Integer> {
	static final String ARQUIVO = "campeonato.txt";
	static final String SEQUENCE = "sequence_campeonato.txt";
	
	@Override
	public Campeonato get(Integer id) {
		PartidaDAO partidaDAO = new PartidaDAO();
		
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
				
				if(dados.length > 3) {
					if(dados[3] != null && !dados[3].equals("")) {
						for(String idPartida : dados[3].split("-")) {
							Partida partida = partidaDAO.get(Integer.parseInt(idPartida));
							
							if(partida != null)
								c.getTodasPartidas().add(partida);
						}
					}
				}
					
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
	
	public Campeonato lazyGet(Integer id) {
		Campeonato retorno = null;
		Campeonato c = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha;

			while ((linha = buffer_entrada.readLine()) != null) {
				String[] dados = linha.split(";");

				c = new Campeonato();
				c.setId(Integer.parseInt(dados[0]));
				c.setNome(dados[1]);
					
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
			bufferOutCampeonato.write(t.getJogos() + separadorDeAtributo);
			
			if(t.getTodasPartidas() != null && !t.getTodasPartidas().isEmpty()) {
				for(Partida p : t.getTodasPartidas()) {
					if(t.getTodasPartidas().indexOf(p) != -1
							&& t.getTodasPartidas().indexOf(p) != t.getTodasPartidas().size() - 1)
						bufferOutCampeonato.write(p.getId() + "-");
					else
						bufferOutCampeonato.write(p.getId() + "");
				}
			}
			
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
			
			if(dados.length > 3) {
				if(dados[3] != null && !dados[3].equals("")) {
					PartidaDAO partidaDAO = new PartidaDAO();

					for(String id : dados[3].split("-")) {
						Partida partida = partidaDAO.get(Integer.parseInt(id));
						
						if(partida != null)
							j.getTodasPartidas().add(partida);
					}
				}
			}
			
			Campeonato.add(j);
		}

		buffer_entrada.close();
		return 	Campeonato;
	}

	public List<Campeonato> getAllForCombo() throws NumberFormatException, IOException {
		List<Campeonato> Campeonato = new ArrayList<Campeonato>();
		BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO));
		Campeonato j = null;
		String linha;

		while ((linha = buffer_entrada.readLine()) != null) {
			String[] dados = linha.split(";");

			j = new Campeonato();
			j.setId(Integer.parseInt(dados[0]));
			j.setNome(dados[1]);
			
			Campeonato.add(j);
		}

		buffer_entrada.close();
		return Campeonato;
	}

	public void saveToFile(List<Campeonato> list) throws IOException {
		BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false));
		String separador = ";";
		for (Campeonato j : list) {
			buffer_saida.write(j.getId() + separador);
			buffer_saida.write(j.getNome() + separador);
			buffer_saida.write(j.getJogos() + separador);
			
			if(j.getTodasPartidas() != null && !j.getTodasPartidas().isEmpty()) {
				for(Partida p : j.getTodasPartidas()) {
					if(j.getTodasPartidas().indexOf(p) != -1
							&& j.getTodasPartidas().indexOf(p) != j.getTodasPartidas().size() - 1)
						buffer_saida.write(p.getId() + "-");
					else
						buffer_saida.write(p.getId() + "");
				}
			}
			
			buffer_saida.write(System.getProperty("line.separator"));
			buffer_saida.flush();
		}
		buffer_saida.close();
	}

}
