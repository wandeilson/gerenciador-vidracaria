package listagem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import adm.JanelaLogin;
import cadastro.Cliente;
import cadastro.ClienteDAO;
import cadastro.DadosDoClienteParaOrcamento;
import cadastro.Item;
import cadastro.Orcamento;
import cadastro.OrcamentoDAO;
import cadastro.StatusOrcamento;
import gerarPDF.GeradorDePDF;

/**
 * Classe responsável por detalhar todo o orçamento e disponibilizar sua edição.
 * @author Wandeilson Gomes da Silva
 *
 */
public class JanelaDetalharOrcamento extends JFrame{
	private JScrollPane painelTabela;
	private JTable tabela;
	private ClienteDAO clienteDAO;
	private OrcamentoDAO orcamentoDAO;
	private JButton btEditarQtd, btRemoverItem, btAdicionarItem, btSalvarAlteraroces,
	btMudarStatus, btVoltar;
	private DadosDoClienteParaOrcamento dadosDoClienteEmEdicao;
	private Cliente clienteComOrcamentoEmEdicao;
	private Orcamento orcamentoEmEdicao;
	private ArrayList<Cliente> todosOsClientes;
	private JLabel lbTitulo, lbTelefone, lbEnderecoEntrega, lbDataNascimento,
	lbNomeDoOrcamento, lbItens, lbValorTotal, lbStatusOrcamento;
	private JTextField textNomeDoOrcamento, textTelefoneCliente, textEnderecoEntregaCliente, textDataNascimentoCliente;
	private  ArrayList<Item> itensOrcamento;
	private  ArrayList<Integer> qtdDosItensDoOrcamento;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private int linhaSelecionada = -1;
	private DefaultTableModel modelo;
	
	/**
	 * Método construtor que recebe um orçamento como argumento de entrada, captura os itens e suas quantidades
	 * para o detalhamento. Também é responsável por definir as configurações da janela.
	 * @param orcamento
	 */
	
	public JanelaDetalharOrcamento(Orcamento orcamento) {
		super("Detalhameto de orçamento.");
		this.orcamentoEmEdicao = orcamento;
		itensOrcamento = orcamento.getItensDoOrcamento();
		qtdDosItensDoOrcamento = orcamento.getQtdDosItensDoOrcamento();
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(true);
		addLabels();
		try {
			addTexts();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		addButton();
		adicionarTabela();
		setVisible(true);
	}

	/**
	 * Método responsável por adicionar os TextFields.
	 * @throws ParseException
	 */
	
	public void addTexts() throws ParseException {
		textNomeDoOrcamento = new JTextField();
		textNomeDoOrcamento.setBounds(190, 29, 200, 25);
		add(textNomeDoOrcamento);
		
		textTelefoneCliente = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		textTelefoneCliente.setBounds(95, 54, 130, 25);
		add(textTelefoneCliente);
		
		textEnderecoEntregaCliente = new JTextField();
		textEnderecoEntregaCliente.setBounds(195, 79, 250, 25);
		add(textEnderecoEntregaCliente);
		
		textDataNascimentoCliente = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textDataNascimentoCliente.setBounds(160, 104, 75, 25);
		add(textDataNascimentoCliente);
		
		preencherTextFields();
	}
	
	/**
	 * Método responsável por capturar os dados do orçamento e 'setar' nos TextFields.
	 * 
	 */
	
	public void preencherTextFields() {
		clienteDAO = new ClienteDAO();
		try {
			todosOsClientes = clienteDAO.recuperarTodosOsClientes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(Cliente cli : todosOsClientes) {
			if(cli.getId() == orcamentoEmEdicao.getIdDonoDoOrcamento()) {
				clienteComOrcamentoEmEdicao = cli;
			}
		}
		
		dadosDoClienteEmEdicao = clienteComOrcamentoEmEdicao.getDadosDoClienteParaOrcamento();
		textNomeDoOrcamento.setText(orcamentoEmEdicao.getNomeDoOrcamento());
//		textDataNascimentoCliente.setText(dadosDoClienteEmEdicao.getDataNascimento());
		textEnderecoEntregaCliente.setText(dadosDoClienteEmEdicao.getEnderedoEntrega());
		textTelefoneCliente.setText(dadosDoClienteEmEdicao.getTelefone());
	}
	
	/**
	 * Método responsável por adicionar os JLabels à tela.
	 */

	private void addLabels() {
		
		lbNomeDoOrcamento = new JLabel("Nome do orçamento:");
		lbNomeDoOrcamento.setFont(new Font ("Serif", Font.BOLD, 20));
		lbNomeDoOrcamento.setBounds(10, 30, 220, 20);
		add(lbNomeDoOrcamento);
		
		lbTitulo = new JLabel("Detalhes do orçamento");
		lbTitulo.setFont((new Font("Serif", Font.BOLD, 26)));
		lbTitulo.setBounds(230, 5, 350, 20);
		add(lbTitulo);
		
		lbTelefone = new JLabel("Telefone:");
		lbTelefone.setFont((new Font("Serif", Font.BOLD, 20)));
		lbTelefone.setBounds(10, 55, 110, 20);
		add(lbTelefone);
		
		lbEnderecoEntrega  = new JLabel("Endereço de entrega:");
		lbEnderecoEntrega.setFont((new Font("Serif", Font.BOLD, 20)));
		lbEnderecoEntrega.setBounds(10, 80, 200, 20);
		add(lbEnderecoEntrega);
		
		lbDataNascimento =  new JLabel("Data nascimento:");
		lbDataNascimento.setFont(new Font ("Serif", Font.BOLD, 20));
		lbDataNascimento.setBounds(10, 105, 200, 20);
		add(lbDataNascimento);
		
		lbItens = new JLabel("Itens do orçamento");
		lbItens.setFont((new Font("Serif", Font.BOLD, 26)));
		lbItens.setBounds(230, 140, 350, 20);
		add(lbItens);
		
		lbValorTotal = new JLabel();
		orcamentoEmEdicao.setValorTotal();
		lbValorTotal.setText("Total: $" + orcamentoEmEdicao.getValorTotal());
		lbValorTotal.setFont((new Font("Serif", Font.BOLD, 22)));
		lbValorTotal.setBounds(400, 420, 350, 30);
		add(lbValorTotal);
		
		lbStatusOrcamento = new JLabel("Status: "+ orcamentoEmEdicao.getStatus());
		lbStatusOrcamento.setFont((new Font("Serif", Font.BOLD, 21)));
		lbStatusOrcamento.setBounds(10, 130, 250, 30);
		add(lbStatusOrcamento);
	}
	
	/**
	 * Método responsável por adicionar os JButton à tela. Em caso de o orçamento em edição
	 * estiver com o Status de entregue ou contratado, este método não irá adicionar determinados Button's a tela
	 * fazendo com que não seja possível sua edição.
	 */

	public void addButton() {
		
		btSalvarAlteraroces = new JButton("Salvar alterações");
		ImageIcon iconSave = new ImageIcon("icons/confirmButton.png");
		btSalvarAlteraroces.setIcon(iconSave);
		btSalvarAlteraroces.setBounds(10, 500, 170, 30);
		btSalvarAlteraroces.setFont(new Font("Serif",Font.BOLD,14));
		btSalvarAlteraroces.addActionListener(new OuvinteSalvarAlteracoes());
		
		btAdicionarItem = new JButton("Adicionar item");
		ImageIcon iconAdd = new ImageIcon("icons/add.png");
		btAdicionarItem.setIcon(iconAdd);
		btAdicionarItem.setFont(new Font("Serif",Font.BOLD,14));
		btAdicionarItem.setBounds(180, 500, 170, 30);
		btAdicionarItem.addActionListener(new OuvinteAdicionarItem());

		btRemoverItem = new JButton("Remover item");
		ImageIcon iconRemove = new ImageIcon("icons/remove.png");
		btRemoverItem.setIcon(iconRemove);
		btRemoverItem.setFont(new Font("Serif",Font.BOLD,14));
		btRemoverItem.setBounds(350, 500, 170, 30);
		btRemoverItem.addActionListener(new OuvinteRemoverItem());
		
		btEditarQtd = new JButton("Editar quantidade");
		ImageIcon iconEdit = new ImageIcon("icons/edit.png");
		btEditarQtd.setIcon(iconEdit);
		btEditarQtd.setBounds(520, 500, 165, 30);
		btEditarQtd.setFont(new Font("Serif",Font.BOLD,14));
		btEditarQtd.addActionListener(new OuvinteBotaoEditarITem());
		
		btMudarStatus = new JButton("Mudar status");
		btMudarStatus.setBounds(520, 140, 130, 20);
		btMudarStatus.addActionListener(new OuvinteMudarStatus());
		
		btVoltar = new JButton("Voltar");
		ImageIcon iconBack = new ImageIcon("icons/back.png");
		btVoltar.setIcon(iconBack);
		btVoltar.setBounds(470, 600, 125, 30);
		btVoltar.setFont(new Font("Serif",Font.BOLD,14));
		btVoltar.addActionListener(new OuvinteBotaoVoltar());
		add(btVoltar);
		
		
		if(orcamentoEmEdicao.getStatus()==StatusOrcamento.CONTRATADO || orcamentoEmEdicao.getStatus()==StatusOrcamento.ENTREGUE) {
			JOptionPane.showMessageDialog(null, "Este orçamento não pode ser mais alterado.");
			textEnderecoEntregaCliente.setEditable(false);
			textEnderecoEntregaCliente.setEditable(false);
			textTelefoneCliente.setEditable(false);
		}else {
			add(btMudarStatus);
			add(btEditarQtd);
			add(btRemoverItem);
			add(btAdicionarItem);	
			add(btSalvarAlteraroces);
		}
		
	}
	
	/**
	 * Ouvinte do botão voltar.
	 * @author Wandeilson Gomes da Silva
	 *
	 */
	
	private class OuvinteBotaoVoltar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaListagemOrcamentos();			
		}
	}
	
	/**
	 * Ouvinte do botão mudar status. Está classe é responsável por alterar o status do orçamento
	 * caso a mudança seja para o status 'Contratado', o ouvinte pede uma data e um horário para a entrega do orçamento 
	 * e caso seja salva as alterações, o e-mail com o orçamento é enviado para o cliente.
	 * @author Wandeilson Gomes da Silva
	 *
	 */
	private class OuvinteMudarStatus implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String status [] = {"Orçado", "Contratado", "Recusado", "Entregue"};
			String novoStatus = (String) JOptionPane.showInputDialog(null, "Escolha o status",
					"Novo status", JOptionPane.QUESTION_MESSAGE, null, status, status[0]);
			if(novoStatus!=null) {
				if(novoStatus.equals("Orçado")) {
					orcamentoEmEdicao.setStatus(StatusOrcamento.ORCADO);
				}else if(novoStatus.equals("Contratado")) {
					orcamentoEmEdicao.setStatus(StatusOrcamento.CONTRATADO);
					Date dataEntrega = null;
					String dataEntregaStr = (String) JOptionPane.showInputDialog("Informe uma data de entrega 'dd/MM/yyyy'");
					try {
						dataEntrega = sdf.parse(dataEntregaStr);
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(null, "Data inválida");
					}
					if(dataEntrega!=null) {
						orcamentoEmEdicao.setDataEntrega(dataEntrega);
					}
					
					String horarioEntrega = (String) JOptionPane.showInputDialog("Informe um horário para entrega");
					if(horarioEntrega!=null) {
						orcamentoEmEdicao.setHorarioEntrega(horarioEntrega);
					}
					
					JOptionPane.showMessageDialog(null, "Assim que salvas as alterações, será enviado um e-mail para o cliente com orçamento contratado.");
				}else if(novoStatus.equals("Recusado")) {
					orcamentoEmEdicao.setStatus(StatusOrcamento.RECUSADO);
				}else if(novoStatus.equals("Entregue") && orcamentoEmEdicao.getStatus()!= StatusOrcamento.CONTRATADO) {
					JOptionPane.showMessageDialog(null, "Antes de mudar para 'Entregue', o orçamento deve ser contratado. ");
					
				}else {
					orcamentoEmEdicao.setStatus(StatusOrcamento.ENTREGUE);
				}
			}
			
			lbStatusOrcamento.setText("Status: "+ orcamentoEmEdicao.getStatus());
			
		}
		
	}
	
	/**
	 * Ouvinte responsável por remover algum item do orçamento.
	 * @author Wandeilson Gomes da Silva
	 *
	 */
	
	private class OuvinteRemoverItem implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			linhaSelecionada = tabela.getSelectedRow();
			if(linhaSelecionada != -1) {
				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este item?", "Excluir item", JOptionPane.YES_NO_OPTION);
				if(opcao == JOptionPane.YES_OPTION) {
					modelo.removeRow(linhaSelecionada);
					tabela.repaint();
					itensOrcamento.remove(linhaSelecionada);
					qtdDosItensDoOrcamento.remove(linhaSelecionada);
					orcamentoEmEdicao.setItensDoOrcamento(itensOrcamento);
					orcamentoEmEdicao.setQtdDosItensDoOrcamento(qtdDosItensDoOrcamento);
					orcamentoEmEdicao.setValorTotal();
					lbValorTotal.setText("Total: $" + orcamentoEmEdicao.getValorTotal());
					JOptionPane.showMessageDialog(null, "Item excluído.");
				}else if(opcao == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Item não excluído");
				}
			
			}else {
				JOptionPane.showMessageDialog(null, "Selecione um item.");
			}
			
		}
		
	}
	
	/**
	 * Ouvinte responsável por editar a quantidade de unidades de algum item.
	 * @author Wandeilson Gomes da Silva
	 *
	 */
	
	private class OuvinteBotaoEditarITem implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = tabela.getSelectedRow();
			boolean isNumeric = false;
			if(linhaSelecionada != -1) {
				
				int novaQtd = 0;
				String novaQtdStr = JOptionPane.showInputDialog("Informe a nova quantidade para esse item");
				if(novaQtdStr!=null) {
					isNumeric = novaQtdStr.matches("[+-]?\\d*(\\.\\d+)?");
				}
				if(isNumeric) {
					novaQtd = Integer.parseInt(novaQtdStr);
					if(novaQtd <= 0) {
						JOptionPane.showMessageDialog(null, "Quantidade não pode ser menor ou igual a zero.");
					}else {
						qtdDosItensDoOrcamento.set(linhaSelecionada, novaQtd);

						JOptionPane.showMessageDialog(null, "Quantidade alterada.");
						orcamentoEmEdicao.setQtdDosItensDoOrcamento(qtdDosItensDoOrcamento);
						orcamentoEmEdicao.setItensDoOrcamento(itensOrcamento);
						dispose();
						new JanelaDetalharOrcamento(orcamentoEmEdicao);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Informe apenas NÚMEROS VÁLIDOS.");
				}
			
			}else {
				JOptionPane.showMessageDialog(null, "Selecione um item.");
			}
		}
		
	}
	
	/**
	 * Ouvinte responsável por salvar todas as alterações realizadas no orçamento.
	 * @author Wandeilson Gomes da Silva
	 *
	 */
	
	private class OuvinteSalvarAlteracoes implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String nomeDoOrcamento = textNomeDoOrcamento.getText();
			String telefone = textTelefoneCliente.getText();
			String enderecoEntrega = textEnderecoEntregaCliente.getText();
			Date dataNascimento = null;
			try {
				dataNascimento = sdf.parse(textDataNascimentoCliente.getText());
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "Data inválida");
			}
			if (enderecoEntrega.isEmpty() || dataNascimento == null || nomeDoOrcamento.isEmpty()
					|| telefone.equals("(  )      -    ")) {
				JOptionPane.showMessageDialog(null, "Por favor, preecnha todos os campos.");
			} else if (enderecoEntrega.trim().isEmpty() || nomeDoOrcamento.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nenhum campo pode conter apenas espaços vazio. Tente novamente.");
			}else {
				orcamentoEmEdicao.setNomeDoOrcamento(nomeDoOrcamento);
				dadosDoClienteEmEdicao.setTelefone(telefone);
				dadosDoClienteEmEdicao.setEnderedoEntrega(enderecoEntrega);
				dadosDoClienteEmEdicao.setDataNascimento(dataNascimento);
				clienteComOrcamentoEmEdicao.setDadosDoClienteParaOrcamento(dadosDoClienteEmEdicao);
				
				for(Cliente c: todosOsClientes ) {
					if(c.getId() == clienteComOrcamentoEmEdicao.getId()) {
						c = clienteComOrcamentoEmEdicao;
					}
				}
				clienteDAO.salvarTodosOsClientes(todosOsClientes);

				// Parte final
				orcamentoDAO = new OrcamentoDAO();
				ArrayList<Orcamento> todosOsOrcamentos = null;
				try {
					todosOsOrcamentos = orcamentoDAO.recuperarTodosOsOrcamentos();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				for(int i = 0; i <todosOsOrcamentos.size() ; i++ ) {
					if(todosOsOrcamentos.get(i).getIdDoOrcamento() == orcamentoEmEdicao.getIdDoOrcamento()) {
						todosOsOrcamentos.set(i, orcamentoEmEdicao);
					}
				}
				orcamentoDAO.salvarTodosOsOrcamentos(todosOsOrcamentos);
				GeradorDePDF g = new GeradorDePDF();
				if(orcamentoEmEdicao.getStatus() == StatusOrcamento.CONTRATADO) {
					if(g.gerarRelatorio(orcamentoEmEdicao)) {
						JOptionPane.showMessageDialog(null, "Foi gerado um arquivo PDF do orçamento.");
					}
					
				}
				
				JOptionPane.showMessageDialog(null, "Alterações salvas.");
			}
		
		}
		
	}
	
	/**
	 * Ouvinte responsável por 'Chamar' a Janela responsável para incluir novos itens no orçamento.
	 * @author Wandeilson Gomes da Silva
	 *
	 */
	
	private class OuvinteAdicionarItem implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaItensParaIncluirNoOrcamento(new JanelaDetalharOrcamento(orcamentoEmEdicao), itensOrcamento, qtdDosItensDoOrcamento);
		}
		
	}
	
	/**
	 * Método responsável por adicionar a tabela na janela
	 */

	public  void adicionarTabela() {
		modelo = new DefaultTableModel();
		//Definir as colunas
		modelo.addColumn("Tipo");
		modelo.addColumn("Nome");
		modelo.addColumn("Qtd");
		modelo.addColumn("Und. Medida");
		modelo.addColumn("Preço");;
		modelo.addColumn("Valot total item");
		modelo.addColumn("Descrição");
		
		//Definir as linhas
	
		int cont = 0;
		for(Item item: itensOrcamento) {
			
			Object [] linha = new Object[7];
			linha[0] = item.getTipoItem();
			linha[1] = item.getNome();
			linha[2] = qtdDosItensDoOrcamento.get(cont);
			linha[3] = item.getUnidadeDeMedida();
			linha[4] = item.getPreco();
			linha[5] = qtdDosItensDoOrcamento.get(cont) * item.getPreco();
			linha[6] = item.getDescrcao();
			modelo.addRow(linha);
			cont++;
		}
		
		tabela  = new JTable(modelo);
		painelTabela = new JScrollPane(tabela);
		painelTabela.setBounds(10, 170, 665, 250);
		
		orcamentoEmEdicao.setItensDoOrcamento(itensOrcamento);
		orcamentoEmEdicao.setQtdDosItensDoOrcamento(qtdDosItensDoOrcamento);
	
		orcamentoEmEdicao.setValorTotal();
		lbValorTotal.setText("Total: $" + orcamentoEmEdicao.getValorTotal());	
		
		add(painelTabela);
		
	}
	
	
	public JTable getTabela() {
		return tabela;
	}

	public Orcamento getOrcamentoEmEdicao() {
		return orcamentoEmEdicao;
	}

	public void setOrcamentoEmEdicao(Orcamento orcamentoEmEdicao) {
		this.orcamentoEmEdicao = orcamentoEmEdicao;
	}
	
	
	public void setTabela(JTable tabela) {
		this.tabela = tabela;
	}

	public DefaultTableModel getModelo() {
		return modelo;
	}

	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
	}

	public ArrayList<Item> getItensOrcamento() {
		return itensOrcamento;
	}

	public void setItensOrcamento(ArrayList<Item> itensOrcamento) {
		this.itensOrcamento = itensOrcamento;
	}

	public ArrayList<Integer> getQtdDosItensDoOrcamento() {
		return qtdDosItensDoOrcamento;
	}

	public void setQtdDosItensDoOrcamento(ArrayList<Integer> qtdDosItensDoOrcamento) {
		this.qtdDosItensDoOrcamento = qtdDosItensDoOrcamento;
	}
}
