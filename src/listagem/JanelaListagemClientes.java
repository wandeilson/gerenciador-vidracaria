package listagem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import adm.JanelaLogin;
import adm.JanelaPrincipal;
import cadastro.Cliente;
import cadastro.ClienteDAO;
import edicao.JanelaEditarCliente;


public class JanelaListagemClientes extends JFrame{
	private ArrayList<Cliente> dados;
	private ClienteDAO clienteDAO;
	private JTable tabela;
	private DefaultTableModel modelo;
	private JButton btEditar, btVoltar, btExcluir;
	private int linhaSelecionada;
	
	
	public JanelaListagemClientes() {
		super("Listagem de clientes");
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		addLabels();
		addButton();
		adicionarTabela();
		setVisible(true);
	}

	private void addButton() {
		btEditar = new JButton("Editar");
		ImageIcon iconEdit = new ImageIcon("icons/edit.png");
		btEditar.setIcon(iconEdit);
		btEditar.setBounds(90, 600, 125, 30);
		btEditar.setFont(new Font("Serif",Font.BOLD,14));
		btEditar.addActionListener(new OuvinteBotaoEditar());
		
		add(btEditar);
		
		btVoltar = new JButton("Voltar");
		ImageIcon iconBack = new ImageIcon("icons/back.png");
		btVoltar.setIcon(iconBack);
		btVoltar.setBounds(470, 600, 125, 30);
		btVoltar.setFont(new Font("Serif",Font.BOLD,14));
		btVoltar.addActionListener(new OuvinteBotaoVoltar());
		add(btVoltar);
		
		btExcluir = new JButton("Excluir");
		ImageIcon iconDeleteUser = new ImageIcon("icons/delete-user.png");
		btExcluir.setIcon(iconDeleteUser);
		btExcluir.setFont(new Font("Serif",Font.BOLD,14));
		btExcluir.setBounds(278, 600, 125, 30);
		btExcluir.addActionListener(new OuvinteBotaoExcluir());
		add(btExcluir);
		

	}
	
	private class OuvinteBotaoExcluir implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			linhaSelecionada = tabela.getSelectedRow();
			if(linhaSelecionada != -1) {
				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este cliente?", "Excluir cliente", JOptionPane.YES_NO_OPTION);
				if(opcao == JOptionPane.YES_OPTION) {
					if(clienteDAO.excluirCliente(linhaSelecionada)) {
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.");
					dispose();
					new JanelaListagemClientes();
					}
				}else if(opcao == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Cliente não excluído");
				}
			}else {
				JOptionPane.showMessageDialog(null, "Selecione um cliente.");
			}
			
		}
		
	}
	
	
	
	private class OuvinteBotaoVoltar implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaPrincipal();
			
		}
		
	}
	
	private class OuvinteBotaoEditar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			linhaSelecionada = tabela.getSelectedRow();
			if(linhaSelecionada != -1) {
				new JanelaEditarCliente(linhaSelecionada, dados);
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "Selecione um cliente.");
			}
			
		}
		
	}

	private void adicionarTabela() {
		modelo = new DefaultTableModel();
		//Definir as colunas
		modelo.addColumn("Id");
		modelo.addColumn("Nome");
		modelo.addColumn("Email");
		modelo.addColumn("Endereço");
		
		//Definir as linhas
		dados = new ArrayList<>();
		clienteDAO = new ClienteDAO();
		try {
			dados = clienteDAO.recuperarTodosOsClientes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		for(Cliente cliente: dados) {
			Object [] linha = new Object[4];
			linha[0] = cliente.getId();
			linha[1] = cliente.getNome();
			linha[2] = cliente.getEmail();
			linha[3] = cliente.getEndereco();
			modelo.addRow(linha);
		}
		
		tabela = new JTable(modelo);

		JScrollPane painelTabela = new JScrollPane(tabela);
	
		painelTabela.setBounds(10, 35, 665, 550);

		add(painelTabela);	
		
	}

	private void addLabels() {
		JLabel lbTitulo = new JLabel("Clientes cadastrados");
		lbTitulo.setFont(new Font("Serif",Font.BOLD,26));
		lbTitulo.setBounds(224, 1, 250, 24);
		add(lbTitulo);
		
	}
	
}
