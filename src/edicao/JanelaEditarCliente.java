package edicao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cadastro.Cliente;
import cadastro.ClienteDAO;
import listagem.JanelaListagemClientes;

public class JanelaEditarCliente extends JFrame {
	private int linha;
	private long IdAntigo;
	private ClienteDAO clienteDAO;
	private ArrayList<Cliente> todosOsClientes;
	private JTextField textNome, textEmail, textEndereco;
	private JButton btCancel, btSalvar;
	private JLabel lbEmail, lbNome, lbEndereco, lbTitulo;

	public JanelaEditarCliente(int linha, ArrayList<Cliente> todosOsClientes) {
		super("Edição de Cliente");
		setSize(300, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		this.linha = linha;
		this.todosOsClientes = todosOsClientes;
		addTexts();
		addLabels();
		addButtons();
		dadosClienteASerEditado();

		setVisible(true);
	}

	private void dadosClienteASerEditado() {
		Cliente cliente = todosOsClientes.get(linha);
		textNome.setText(cliente.getNome());
		textEmail.setText(cliente.getEmail());
		textEndereco.setText(cliente.getEndereco());
		IdAntigo = cliente.getId();

	}

	private void addTexts() {
		textEmail = new JTextField();
		textEmail.setBounds(73, 85, 200, 30);
		textEmail.setToolTipText("Insira o novo e-mail");
		add(textEmail);
		textEndereco = new JTextField();
		textEndereco.setBounds(100, 125, 174, 30);
		textEndereco.setToolTipText("Insira o novo endereço");

		add(textEndereco);
		textNome = new JTextField();
		textNome.setBounds(73, 44, 200, 30);
		textNome.setToolTipText("Insira o novo nome");
		add(textNome);

	}

	private void addButtons() {
		ImageIcon iconCancel = new ImageIcon("icons/cancelButton.png");
		btCancel = new JButton("Cancelar");
		btCancel.setIcon(iconCancel);
		btCancel.setBounds(150, 170, 125, 30);
		btCancel.setFont(new Font("Serif", Font.BOLD, 14));
		btCancel.addActionListener(new OuvinteBotaoCancelar());
		add(btCancel);

		btSalvar = new JButton("Salvar");
		ImageIcon iconConfirm = new ImageIcon("icons/confirmButton.png");
		btSalvar.setIcon(iconConfirm);
		btSalvar.setFont(new Font("Serif", Font.BOLD, 14));
		btSalvar.setBounds(10, 170, 125, 30);
		btSalvar.addActionListener(new OuvinteBotaoSalvar());

		add(btSalvar);

	}

	private class OuvinteBotaoCancelar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaListagemClientes();

		}

	}

	private class OuvinteBotaoSalvar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String nome = textNome.getText();
			String email = textEmail.getText();
			String endereco = textEndereco.getText();

			if (endereco.isEmpty() || nome.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
			} else if (endereco.trim().isEmpty() || nome.trim().isEmpty() || email.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nenhum campo pode conter apenas espaços vazios. Tente novamente.");
			} else {
				Cliente clienteEditado = new Cliente(nome, email, endereco);
				clienteEditado.setId(IdAntigo);
				clienteDAO = new ClienteDAO();

				try {
					todosOsClientes = clienteDAO.recuperarTodosOsClientes();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				clienteEditado
						.setDadosDoClienteParaOrcamento(todosOsClientes.get(linha).getDadosDoClienteParaOrcamento());
				todosOsClientes.set(linha, clienteEditado);
				clienteDAO.salvarTodosOsClientes(todosOsClientes);
				JOptionPane.showMessageDialog(null, "Alterações salvas");
				dispose();
				new JanelaListagemClientes();
			}

		}

	}

	private void addLabels() {

		lbTitulo = new JLabel("Edição de cliente");
		lbTitulo.setFont(new Font("Serif", Font.BOLD, 24));
		lbTitulo.setBounds(50, 1, 230, 24);
		add(lbTitulo);
		lbEmail = new JLabel("E-mail:");
		lbEmail.setBounds(10, 82, 70, 30);
		lbEmail.setFont(new Font("Serif", Font.BOLD, 20));
		add(lbEmail);

		lbEndereco = new JLabel("Endereço:");
		lbEndereco.setBounds(10, 120, 100, 30);
		lbEndereco.setFont(new Font("Serif", Font.BOLD, 20));
		add(lbEndereco);

		lbNome = new JLabel("Nome:");
		lbNome.setFont(new Font("Serif", Font.BOLD, 20));
		lbNome.setBounds(10, 40, 70, 30);
		add(lbNome);

	}
}
