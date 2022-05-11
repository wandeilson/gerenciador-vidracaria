package cadastro;

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
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import adm.JanelaLogin;
import adm.JanelaPrincipal;

import listagem.JanelaItensParaIncluirNoOrcamento;

public class JanelaCriacaoOrcamento extends JFrame {
	public static ArrayList<Item> itensDoOrcamento = new ArrayList<Item>();
	public static ArrayList<Integer> qtdDosItensDoOrcamento = new ArrayList<Integer>();
	private JLabel lbTitulo, lbNomeOrcamento, lbItensOrcamento, lbDonoDoOrcamento, lbDadosDoCliente;
	private JLabel lbTelefone, lbEnderecoEntrega, lbDataNascimento;
	private JButton btAdicionarItens, btSalvarOrcamento, btCancel;
	private JTextField textNomeDoOrcamento, textTelefoneCliente, textEnderecoEntregaCliente, textDataNascimentoCliente;
	private ClienteDAO clienteDAO;
	private DadosDoClienteParaOrcamento dadosDoClienteParaOrcamento;
	private Cliente clienteDonoDoOrcamento;
	private String clienteEscolhido;
	private ArrayList<Cliente> todosClientes;
	private OrcamentoDAO orcamentoDAO;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public JanelaCriacaoOrcamento() {
		super("Novo orçamento");

		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		lerDonoDoOrcamento();
		if(clienteEscolhido != null) {
			addLabels();
			addButtons();
			try {
				addTexts();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			jaFezOrcamento();

			setVisible(true);
			
		}else {
			JOptionPane.showMessageDialog(null, "É necessário escolher um cliente antes de iniciar um orçamento.");
			dispose();
			new JanelaPrincipal();
		}
		
		
	}

	private void addTexts() throws ParseException {
		textNomeDoOrcamento = new JTextField();
		textNomeDoOrcamento.setBounds(70, 39, 200, 30);
		add(textNomeDoOrcamento);

		textTelefoneCliente = new JFormattedTextField(new MaskFormatter("(##) #####-####"));

		textTelefoneCliente.setBounds(95, 189, 250, 30);
		add(textTelefoneCliente);

		textEnderecoEntregaCliente = new JTextField();
		textEnderecoEntregaCliente.setBounds(170, 219, 250, 30);
		add(textEnderecoEntregaCliente);

		textDataNascimentoCliente = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textDataNascimentoCliente.setBounds(158, 250, 80, 30);
		add(textDataNascimentoCliente);

	}

	private void addButtons() {
		btAdicionarItens = new JButton("Adicionar itens");
		ImageIcon iconAddItens = new ImageIcon("icons/add.png");
		btAdicionarItens.setIcon(iconAddItens);
		btAdicionarItens.setFont(new Font("Serif", Font.BOLD, 14));
		btAdicionarItens.setBounds(205, 70, 160, 30);
		btAdicionarItens.addActionListener(new OuvinteBotaoAddItens());
		add(btAdicionarItens);

		btSalvarOrcamento = new JButton("Salvar orçamento");
		ImageIcon iconSalvarOrcamento = new ImageIcon("icons/confirmButton.png");
		btSalvarOrcamento.setIcon(iconSalvarOrcamento);
		btSalvarOrcamento.setFont(new Font("Serif", Font.BOLD, 14));
		btSalvarOrcamento.setBounds(50, 300, 200, 30);
		btSalvarOrcamento.addActionListener(new OuvinteBotaoSalvarOrcamento());
		add(btSalvarOrcamento);
		
		ImageIcon iconCancel = new ImageIcon("icons/cancelButton.png");
		btCancel = new JButton("Cancelar");
		btCancel.setIcon(iconCancel);
		btCancel.setBounds(360, 300, 125, 30);
		btCancel.setFont(new Font("Serif",Font.BOLD,14));
		btCancel.addActionListener(new OuvinteCancelar());
		add(btCancel);	
	}
	
	private class OuvinteCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaPrincipal();
			
		}
		
	}
	

	private class OuvinteBotaoSalvarOrcamento implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				lerDados();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		}

	}

	public void lerDados() throws ParseException {
		String nomeDoOrcamento = textNomeDoOrcamento.getText();
		String telefone = textTelefoneCliente.getText();
		String enderecoEntrega = textEnderecoEntregaCliente.getText();
		Date dataNascimento = null;
		try {
			dataNascimento = sdf.parse(textDataNascimentoCliente.getText());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Data inválida");
		}
		if (enderecoEntrega.isEmpty() || dataNascimento == null || nomeDoOrcamento.isEmpty()
				|| telefone.equals("(  )      -    ")) {
			JOptionPane.showMessageDialog(null, "Por favor, preecnha todos os campos.");
		} else if (enderecoEntrega.trim().isEmpty() || nomeDoOrcamento.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nenhum campo pode conter apenas espaços vazio. Tente novamente.");
		} else {

			dadosDoClienteParaOrcamento = new DadosDoClienteParaOrcamento(clienteDonoDoOrcamento.getNome(), telefone,
					enderecoEntrega, dataNascimento);
			clienteDonoDoOrcamento.setDadosDoClienteParaOrcamento(dadosDoClienteParaOrcamento);
			Orcamento novoOrcamento = new Orcamento(itensDoOrcamento, qtdDosItensDoOrcamento,
					clienteDonoDoOrcamento.getId(), nomeDoOrcamento);
			orcamentoDAO = new OrcamentoDAO();
			orcamentoDAO.adicionarOrcamento(novoOrcamento);
			JOptionPane.showMessageDialog(null, "Orçamento salvo com sucesso");
			for (Cliente c : todosClientes) {
				if (c.getEmail().equals(clienteDonoDoOrcamento.getEmail())) {
					c = clienteDonoDoOrcamento;
				}
			}
			clienteDAO.salvarTodosOsClientes(todosClientes);

		}

	}

	private class OuvinteBotaoAddItens implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new JanelaItensParaIncluirNoOrcamento(null,itensDoOrcamento, qtdDosItensDoOrcamento);
		}

	}

	private void addLabels() {
		lbTitulo = new JLabel("Novo orçamento");
		lbTitulo.setFont((new Font("Serif", Font.BOLD, 26)));
		lbTitulo.setBounds(200, 5, 200, 20);
		add(lbTitulo);

		lbNomeOrcamento = new JLabel("Nome:");
		lbNomeOrcamento.setFont((new Font("Serif", Font.BOLD, 20)));
		lbNomeOrcamento.setBounds(10, 40, 60, 20);
		add(lbNomeOrcamento);

		lbItensOrcamento = new JLabel("Itens do orçamento:");
		lbItensOrcamento.setFont((new Font("Serif", Font.BOLD, 20)));
		lbItensOrcamento.setBounds(10, 70, 190, 20);
		add(lbItensOrcamento);

		lbDonoDoOrcamento = new JLabel();
		lbDonoDoOrcamento.setFont((new Font("Serif", Font.BOLD, 20)));
		lbDonoDoOrcamento.setBounds(10, 100, 400, 30);
		lbDonoDoOrcamento.setText("Dono: " + clienteEscolhido);
		add(lbDonoDoOrcamento);

		lbDadosDoCliente = new JLabel("Dados do cliente");
		lbDadosDoCliente.setFont((new Font("Serif", Font.BOLD, 24)));
		lbDadosDoCliente.setBounds(200, 130, 200, 20);
		add(lbDadosDoCliente);

		lbTelefone = new JLabel("Telefone: ");
		lbTelefone.setFont((new Font("Serif", Font.BOLD, 20)));
		lbTelefone.setBounds(10, 190, 100, 20);
		add(lbTelefone);

		lbEnderecoEntrega = new JLabel("Endereço entrega:");
		lbEnderecoEntrega.setFont((new Font("Serif", Font.BOLD, 20)));
		lbEnderecoEntrega.setBounds(10, 220, 160, 20);
		add(lbEnderecoEntrega);

		lbDataNascimento = new JLabel("Data nascimento:");
		lbDataNascimento.setFont((new Font("Serif", Font.BOLD, 20)));
		lbDataNascimento.setBounds(10, 250, 180, 20);
		add(lbDataNascimento);

	}

	public void lerDonoDoOrcamento() throws NullPointerException {
		todosClientes = new ArrayList<Cliente>();
		clienteDAO = new ClienteDAO();
		try {
			todosClientes = clienteDAO.recuperarTodosOsClientes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] clientes = new String[todosClientes.size()];
		for (int i = 0; i < todosClientes.size(); i++) {
			clientes[i] = todosClientes.get(i).getEmail();
		}
		try {
			clienteEscolhido = (String) JOptionPane.showInputDialog(null, "Escolha o cliente",
					"Cliente dono do orçamento", JOptionPane.QUESTION_MESSAGE, null, clientes, clientes[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Antes de iniciar um orçamento, realize o cadastro de algum cliente.");
		}
		
		

		for (Cliente c : todosClientes) {
			if (c.getEmail().equals(clienteEscolhido)) {
				clienteDonoDoOrcamento = c;
			}
		}
	}

	public void jaFezOrcamento() throws NullPointerException {
		try {
			if (clienteDonoDoOrcamento.getDadosDoClienteParaOrcamento() != null) {
//				textNomeCliente.setText(clienteDonoDoOrcamento.getNome());
				textTelefoneCliente.setText(clienteDonoDoOrcamento.getDadosDoClienteParaOrcamento().getTelefone());
				textEnderecoEntregaCliente
						.setText(clienteDonoDoOrcamento.getDadosDoClienteParaOrcamento().getEnderedoEntrega());
				textDataNascimentoCliente.setText(clienteDonoDoOrcamento.getDadosDoClienteParaOrcamento().getDataNascimento());

			} else {
				JOptionPane.showMessageDialog(null, "Informe os dados complementares para o orçamento.");
			}
		} catch (NullPointerException e) {

		}

	}

	public ArrayList<Item> getItensDoOrcamento() {
		return itensDoOrcamento;
	}

	public static ArrayList<Integer> getQtdDosItensDoOrcamento() {
		return qtdDosItensDoOrcamento;
	}

	public static void setQtdDosItensDoOrcamento(ArrayList<Integer> qtdDosItensDoOrcamento) {
		JanelaCriacaoOrcamento.qtdDosItensDoOrcamento = qtdDosItensDoOrcamento;
	}

}
