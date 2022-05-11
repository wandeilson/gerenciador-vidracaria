package cadastro;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import adm.JanelaLogin;
import adm.JanelaPrincipal;

public class JanelaCadastroCliente extends JFrame {
	private JLabel lbEmail, lbNome, lbEndereco, lbTitulo;
	private JButton btCancel, cadastrar;
	private JTextField textNome, textEmail, textEndereco;

	public JanelaCadastroCliente() {

		super("Cadastro Cliente");
		setSize(300, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		addLabels();
		addButtons();
		addTexts();

		setVisible(true);

	}

	private void addTexts() {
		textEmail = new JTextField();
		textEmail.setBounds(73, 85, 200, 30);
		add(textEmail);
		textEndereco = new JTextField();
		textEndereco.setBounds(100, 125, 174, 30);

		add(textEndereco);
		textNome = new JTextField();
		textNome.setBounds(73, 44, 200, 30);
		add(textNome);

	}

	private void addButtons() {
		ImageIcon iconCancel = new ImageIcon("icons/cancelButton.png");
		btCancel = new JButton("Cancelar");
		btCancel.setIcon(iconCancel);
		btCancel.setBounds(150, 170, 125, 30);
		btCancel.setFont(new Font("Serif", Font.BOLD, 14));
		btCancel.addActionListener(new OuvinteCancelar());
		add(btCancel);

		cadastrar = new JButton("Cadastrar");
		ImageIcon iconConfirm = new ImageIcon("icons/confirmButton.png");
		cadastrar.setIcon(iconConfirm);
		cadastrar.setFont(new Font("Serif", Font.BOLD, 14));
		cadastrar.setBounds(10, 170, 125, 30);
		cadastrar.addActionListener(new OuvinteBotaoCadastrar());

		add(cadastrar);

	}

	private class OuvinteBotaoCadastrar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String endereco, nome, email;
			endereco = textEndereco.getText();
			nome = textNome.getText();
			email = textEmail.getText();

			if (endereco.isEmpty() || nome.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
			} else if (endereco.trim().isEmpty() || nome.trim().isEmpty() || email.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nenhum campo pode conter apenas espaços vazios. Tente novamente.");
			} else {
				Cliente cliente = new Cliente(nome, email, endereco);
				ClienteDAO clienteDAO = new ClienteDAO();
				if (clienteDAO.adicionarCliente(cliente)) {
					JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
					textEmail.setText("");
					textEndereco.setText("");
					textNome.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"Cadastro não  realizado. Email já utilizado por outro usuário.");
				}

			}

		}

	}

	private class OuvinteCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaPrincipal();
		}
	}

	private void addLabels() {

		lbTitulo = new JLabel("Cadastro de clientes");
		lbTitulo.setFont(new Font("Serif", Font.BOLD, 24));
		lbTitulo.setBounds(34, 1, 230, 24);
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
