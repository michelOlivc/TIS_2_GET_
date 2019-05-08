package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import model.Jogador;
import model.Time;

public class TimeDAO {
	static final String ARQUIVO = "time.txt";
	private JogadorDAO jogadorDAO = new JogadorDAO();
	
	public Time get() {
		Time retorno = null;
		Time t = null;

		try (BufferedReader buffer_entrada = new BufferedReader(new FileReader(ARQUIVO))) {
			String linha = buffer_entrada.readLine();

			if(linha != null) {
				String [] dados = linha.split(";");
				t = new Time();
				t.setId(Integer.parseInt(dados[0]));
				t.setNome(dados[1]);
				
				if(dados.length > 2 && !dados[2].equals("")) {
					String [] idJogadores = dados[2].split("-");
					for(String s : idJogadores) {
						Integer id = Integer.parseInt(s);
						Jogador jogador = jogadorDAO.get(id);
						
						t.adicionarJogador(jogador);
					}
				}
				retorno = t;
			}
		} catch (Exception e) {
			System.out.println("Erro ao consultar o Time.");
			e.printStackTrace();
		}
		return retorno;
	}

	public void add(Time t) {
		try {
			BufferedWriter bufferOutJogadores = new BufferedWriter(new FileWriter(ARQUIVO, true));
			
			String separadorDeAtributo = ";";
			bufferOutJogadores.write("1" + separadorDeAtributo);
			bufferOutJogadores.write(t.getNome() + separadorDeAtributo);
			
			for(int i = 0; i < t.getListaJogadores().size(); i++) {
				Jogador jogador = t.getListaJogadores().get(i);
				
				if (i != t.getListaJogadores().size() - 1) {
					bufferOutJogadores.write(jogador.getId() + "-");
				} else {
					bufferOutJogadores.write(jogador.getId());
				}
			}
			
			bufferOutJogadores.write(System.getProperty("line.separator"));
			bufferOutJogadores.flush();
			bufferOutJogadores.close();
		} catch (Exception e) {
			System.out.println("Erro ao gravar o Time.");
			e.printStackTrace();
		}
	}

	public void update(Time time) {
		saveToFile(time);
	}

	public void saveToFile(Time t) {
		try (BufferedWriter buffer_saida = new BufferedWriter(new FileWriter(ARQUIVO, false))) {
			String separadorDeAtributo = ";";
			buffer_saida.write("1" + separadorDeAtributo);
			buffer_saida.write(t.getNome() + separadorDeAtributo);
			
			for(int i = 0; i < t.getListaJogadores().size(); i++) {
				Jogador jogador = t.getListaJogadores().get(i);
				
				if (i != t.getListaJogadores().size() - 1) {
					buffer_saida.write(jogador.getId() + "-");
				} else {
					buffer_saida.write(jogador.getId() + "");
				}
			}
			
			buffer_saida.write(System.getProperty("line.separator"));
			buffer_saida.flush();
			
		} catch (Exception e) {
			System.out.println("Erro ao gravar o Time.");
			e.printStackTrace();
		}
	}
}
