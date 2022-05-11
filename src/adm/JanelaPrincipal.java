package adm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import GerarPlanilha.GerarPlanilhaDeItens;
import cadastro.JanelaCadastroCliente;
import cadastro.JanelaCadastroItens;
import cadastro.JanelaCriacaoOrcamento;
import listagem.JanelaListagemClientes;
import listagem.JanelaListagemItens;
import listagem.JanelaListagemOrcamentos;

public class JanelaPrincipal extends JFrame{
	
	
	public JanelaPrincipal() {
		super("Janlea Principal");
		setSize(700,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		desenharJanela();
		setVisible(true);
		
	}
	
	
	
	public void desenharJanela() {
		//Cria a barra de Menu
		JMenuBar menuPrincipal = new JMenuBar();
		//Adiciona a barra de Menu a Janela
		setJMenuBar(menuPrincipal);
		//Cria e adiciona itens a barra de Menu
		JMenu clienteMenu = new JMenu("Cliente");
		JMenu itemMenu = new JMenu("Item");
		JMenu orcamentoMenu = new JMenu("Orçamentos");
		
		menuPrincipal.add(itemMenu);
		menuPrincipal.add(orcamentoMenu);
		menuPrincipal.add(clienteMenu);
		
		//Cria os sub-menus
		
		JMenuItem cadastrarItem = new JMenuItem("Novo item");
		cadastrarItem.addActionListener(new OuvinteMenuCadastrarItem() );
		JMenuItem cadastrarCliente = new JMenuItem("Novo cliente");
		cadastrarCliente.addActionListener(new OuvinteMenuCadastrarCliente());
		
		JMenuItem listarItens = new JMenuItem("Listar todos itens");
		listarItens.addActionListener(new OuvinteMenuListarItens());
		
		JMenuItem listarClientes = new JMenuItem("Listar todos os clientes");
		listarClientes.addActionListener(new OuvinteMenuListarClientes());
		
		JMenuItem novoOrcamento = new JMenuItem("Novo orçamento");
		novoOrcamento.addActionListener(new OuvinteMenuNovoOrcamento ());
		
		JMenuItem listarTodosOrcamento = new JMenuItem("Listar todos orçamentos");
		listarTodosOrcamento.addActionListener(new OuvinteListarOrcamentos ());
		
		JMenuItem gerarRelatorioItens = new JMenuItem("Gerar .xlsx de todos itens");
		gerarRelatorioItens.addActionListener(new OuvinteGerador());
		
		//Adiciona os sub-menus ao seu respectivo Menu
		
		if(JanelaLogin.fezLogin) {
			itemMenu.add(cadastrarItem);
			itemMenu.add(listarItens);
			clienteMenu.add(cadastrarCliente);
			clienteMenu.add(listarClientes);
			orcamentoMenu.add(novoOrcamento);
			orcamentoMenu.add(listarTodosOrcamento);
			itemMenu.add(gerarRelatorioItens);
		}else {
			orcamentoMenu.add(listarTodosOrcamento);
		}
		
		
	}
	
	private class OuvinteGerador implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			GerarPlanilhaDeItens g = new GerarPlanilhaDeItens();
			g.recuperarItens();
			try {
				g.gerarRelatorio();
			} catch (InvalidFormatException e1) {
			
				e1.printStackTrace();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
		}
		
	}
	
	private class OuvinteListarOrcamentos implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaListagemOrcamentos();
			
		}
		
	}
	
	
	private class OuvinteMenuNovoOrcamento implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new  JanelaCriacaoOrcamento();
			
		}
		
	}
	
	private class OuvinteMenuListarClientes implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaListagemClientes();
		}
		
	}
	
	private class OuvinteMenuListarItens implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaListagemItens();
		}
	}
	
	private class OuvinteMenuCadastrarItem implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaCadastroItens();
		}
	}

	private class OuvinteMenuCadastrarCliente implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaCadastroCliente();
			
		}
		
	}
	

}
