package telasUsuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import scrapper.RotinaRequests;
import scrapper.RotinaSelenium;

public class TelaUsuario {

	// Cria o contrutor da classe
	public TelaUsuario() {
		
		CriarElementosTela elemento = new CriarElementosTela();
		
		// Cria a Janela
		JFrame janela = elemento.criarTela();

		// Cria os elementos da tela - Parâmetros de entradas no site
		JLabel cnpj = elemento.criarLabel("CNPJ", 25, 5, 100, 15);
		JTextField txtCnpj = elemento.criarMascara("##.###.###/####-##", 25, 20, 115, 20);

		JLabel data = elemento.criarLabel("Data", 150, 5, 100, 15);
		JTextField txtData = elemento.criarMascara("##/####", 150, 20, 50, 20);

		JButton botaoProcesso = elemento.criarBotao("Gerar Arquivo", 25, 50, 115, 20);

		JLabel status = elemento.criarLabel("", 25, 80, 150, 15);

		// Cria a ação do botão
		botaoProcesso.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				status.setText("");

				// Verifica o cnpj e a data
				if (verificarCNPJ(txtCnpj.getText()) && verificarData(txtData.getText())) {
					
					status.setText("Abrindo Navegador");
					
					try {
						
						RotinaRequests requisicao = new RotinaRequests();
						//RotinaSelenium navegador = new RotinaSelenium();

						status.setText("Passando parâmetros");
						requisicao.setCnpj(txtCnpj.getText());
						requisicao.setData(txtData.getText());

						try {
							status.setText("Fazendo Processo de extração");
							requisicao.Processo();
							status.setText("Arquivo Gerado");
						} catch (Exception e1) {
							status.setText("Erro no Processo");
						}

					} catch (BiffException e1) {
						status.setText("Erro - BiffException");
					} catch (WriteException e1) {
						status.setText("Erro - WriteException");
					} catch (IOException e1) {
						status.setText("Erro - IOException");
					} catch (Exception e1) {
						status.setText("Erro - Exception");

					}

				}else {
					status.setText("");
					JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
				}

			}
		});

		// Deixa a janela visivel
		janela.setVisible(true);

	}

	private boolean verificarData(String data) {

		// Remove os caracteres
		String validar = data.replaceAll("[^0-9]", "");

		// Valida se é um cnpj válido
		if (validar.length() == 6) {
			return true;
		} else {
			return false;
		}

	}
	private boolean verificarCNPJ(String cnpj) {

		// Remove os caracteres
		String validar = cnpj.replaceAll("[^0-9]", "");

		// Valida se é um cnpj válido
		if (validar.length() == 14) {
			return true;
		} else {
			return false;
		}

	}

}
