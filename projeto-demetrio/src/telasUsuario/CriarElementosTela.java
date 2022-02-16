package telasUsuario;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class CriarElementosTela {
	
	// Cria os atributos 
	private JPanel painel = new JPanel();
	
	// Cria o painel
	private JPanel criarPainel() {
		
		// Passa as configurações do painel
		painel.setBackground(new Color(225, 225, 225));
		painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		painel.setLayout(null);
		
		return painel;

	}
	
	protected JFrame criarTela() {
		
		// Cria a Janela
		JFrame janela = new JFrame("Painel");
		
		// Configurações da janela
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setBackground(new Color(189, 183, 130));
		janela.setBounds(100, 100, 250, 150);
		janela.setResizable(false);
		janela.setContentPane(this.criarPainel());
		janela.setLocationRelativeTo(null);
		
		return janela;
	}
	
	// Cria um botão 
	protected JButton criarBotao(String textoBotao, int x, int y, int l, int a) {

		// Cria o botao
		JButton botao = new JButton(textoBotao);
		botao.setFont(new Font("Verdana", Font.ITALIC, 11));
		botao.setForeground(SystemColor.desktop);
		botao.setBounds(x, y, l, a);
		painel.add(botao);

		return botao;
	}
	
	// Cria e configura os labels
	protected JLabel criarLabel(String textoLabel, int x, int y, int l, int a) {

		// Cria a label
		JLabel label = new JLabel(textoLabel);

		// Adiciona os Labels na tela configurados
		label.setFont(new Font("Verdana", Font.ITALIC, 12));
		label.setForeground(SystemColor.desktop);
		label.setBounds(x, y, l, a);
		painel.add(label);

		// Retorna a label
		return label;
	}
	
	protected JTextField criarMascara(String formato,int x, int y, int l, int a){
		
	    MaskFormatter mask = null;
	    try{
	        mask = new MaskFormatter(formato);                      
	        }catch(java.text.ParseException ex){}
	    
	    JTextField campo = new JFormattedTextField(mask);
	    
		// Configura o campo
	    campo.setFont(new Font("Verdana", Font.ITALIC, 10));
	    campo.setForeground(SystemColor.desktop);
	    campo.setBounds(x, y, l, a);
	    campo.setColumns(10);
		painel.add(campo);
	    
	    return campo;
	}

}
