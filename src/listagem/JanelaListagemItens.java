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
import cadastro.Item;
import cadastro.ItemDAO;
import edicao.JanelaEditarCliente;


public class JanelaListagemItens extends JFrame{
	private JButton btEditar, btVoltar;
	protected JTable tabela;
	private ArrayList<Item> dados;
	protected JScrollPane painelTabela;
	
	public JanelaListagemItens() {
		super("Listagem de itens");
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		addLabels();
		addButton();
		adicionarTabela();
		setVisible(true);
	}
	
	public ArrayList<Item> getDados() {
		return dados;
	}

	public void setDados(ArrayList<Item> dados) {
		this.dados = dados;
	}
	
	
	public JTable getTabela() {
		return tabela;
	}

	public void setTabela(JTable tabela) {
		this.tabela = tabela;
	}

	public JButton btAdicionar() {
		return btEditar;
	}

	public void setBtEditar(JButton btEditar) {
		this.btEditar = btEditar;
	}

	public JButton getBtVoltar() {
		return btVoltar;
	}

	public void setBtVoltar(JButton btVoltar) {
		this.btVoltar = btVoltar;
	}

	
	public JScrollPane getPainelTabela() {
		return painelTabela;
	}

	public void setPainelTabela(JScrollPane painelTabela) {
		this.painelTabela = painelTabela;
	}
	
	public void addLabels() {
		JLabel lbTitulo = new JLabel("Itens cadastrados");
		lbTitulo.setFont(new Font("Serif",Font.BOLD,26));
		lbTitulo.setBounds(250, 1, 200, 24);
		add(lbTitulo);
		
	}
	
	public void addButton() {
		btEditar = new JButton("Editar");
		ImageIcon iconEdit = new ImageIcon("icons/edit.png");
		btEditar.setIcon(iconEdit);
		btEditar.setBounds(90, 600, 125, 30);
		btEditar.setFont(new Font("Serif",Font.BOLD,14));
		btEditar.addActionListener(new OuvinteBotaoEditar());
		
		btVoltar = new JButton("Voltar");
		ImageIcon iconBack = new ImageIcon("icons/back.png");
		btVoltar.setIcon(iconBack);
		btVoltar.setBounds(470, 600, 125, 30);
		btVoltar.setFont(new Font("Serif",Font.BOLD,14));
		btVoltar.addActionListener(new OuvinteBotaoVoltar());
		add(btVoltar);
	}
	
	
	public JButton getBtEditar() {
		return btEditar;
	}


	class OuvinteBotaoEditar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = tabela.getSelectedRow();
			if(linhaSelecionada != -1) {
				
				dispose();
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
	
	public void adicionarTabela() {
		DefaultTableModel modelo = new DefaultTableModel();
		//Definir as colunas
		modelo.addColumn("Tipo");
		modelo.addColumn("Nome");
		modelo.addColumn("Und. Medida");
		modelo.addColumn("Preço");
		modelo.addColumn("Descrição");
		
		//Definir as linhas
		dados = new ArrayList<>();
		ItemDAO itemDAO = new ItemDAO();
		try {
			dados = itemDAO.recuperarTodosOsItens();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		for(Item item: dados) {
			Object [] linha = new Object[5];
			linha[0] = item.getTipoItem();
			linha[1] = item.getNome();
			linha[2] = item.getUnidadeDeMedida();
			linha[3] = item.getPreco();
			linha[4] = item.getDescrcao();
			modelo.addRow(linha);
		}
		
		tabela = new JTable(modelo);
		painelTabela = new JScrollPane(tabela);
		painelTabela.setBounds(10, 35, 665, 550);
		add(painelTabela);		
	}
	
	public static void main(String[] args) {
		new JanelaListagemItens();
	}
	
}

