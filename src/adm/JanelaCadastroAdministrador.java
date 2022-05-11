package adm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import persistencia.Usuarios;
import persistencia.Persistencia;

public class JanelaCadastroAdministrador extends JFrame{
	Persistencia p;
	Usuarios usuarios = new Usuarios();
	private JLabel lbEmail, lbNome, lbSenha, lbLogin;
	private JButton btCancel, cadastrar;
	private JPasswordField jpassSenha;
	private JTextField textNome, textEmail;
	
	public JanelaCadastroAdministrador() {
		super("Cadastro ADM");
		setSize(300,300);
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
		textEmail.setBounds(73, 115, 200, 30);
		add(textEmail);
		jpassSenha = new JPasswordField();
		jpassSenha.setBounds(73, 153, 200, 30);
		
		add(jpassSenha);
		textNome = new JTextField();
		textNome.setBounds(73, 73, 200, 30);
		add(textNome);
		
	}
	
	private void addButtons() {
		ImageIcon iconCancel = new ImageIcon("icons/cancelButton.png");
		btCancel = new JButton("Cancelar");
		btCancel.setIcon(iconCancel);
		btCancel.setBounds(150, 220, 125, 30);
		btCancel.setFont(new Font("Serif",Font.BOLD,14));
		btCancel.addActionListener(new OuvinteCancelar());
		add(btCancel);
		
		cadastrar = new JButton("Cadastrar");
		ImageIcon iconConfirm = new ImageIcon("icons/confirmButton.png");
		cadastrar.setIcon(iconConfirm);
		cadastrar.setFont(new Font("Serif",Font.BOLD,14));
		cadastrar.setBounds(10, 220, 125, 30);
		cadastrar.addActionListener(new OuvinteBotaoCadastrar());
		
		add(cadastrar);
	}
	
	private class OuvinteBotaoCadastrar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String nome = textNome.getText();
			String senha = jpassSenha.getText();
			String email = textEmail.getText();
			if(nome.isEmpty() || senha.isEmpty() || email.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nenhum campo pode ser vazio. Tente novamente.");
			}else if(nome.trim().isEmpty() || senha.trim().isEmpty() || email.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nenhum campo pode preenchido com espaços em branco. Tente novamente.");
			}else {
				Administrador adm = new Administrador(email, nome, senha);
				cadastrarAdministrador(adm);
			}
		}
		
	}
	
	public boolean cadastrarAdministrador (Administrador adm) {
		p = new Persistencia("usuarios.xml");
		usuarios.setAdm(adm);
		p.salvarArquivo(usuarios);
		JOptionPane.showMessageDialog(null, "Administrador cadastrado. Agora, faça login.");
		dispose();
		new JanelaLogin();
		return true;
	}
	
	
	private class OuvinteCancelar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();	
		}
	}
	
	private void addLabels() {
		ImageIcon iconLogin = new ImageIcon("Icons/login.png");
		lbLogin = new JLabel(iconLogin);
		lbLogin.setBounds(105, 0, 75, 70);
		add(lbLogin);
		lbEmail = new JLabel("E-mail:");
		lbEmail.setBounds(10, 112, 70, 30);
		lbEmail.setFont(new Font("Serif",Font.BOLD,20));
		add(lbEmail);
		lbSenha = new JLabel("Senha:");
		lbSenha.setBounds(10, 150, 70, 30);
		lbSenha.setFont(new Font("Serif",Font.BOLD,20));
		add(lbSenha);
		lbNome = new JLabel("Nome:");
		lbNome.setFont(new Font("Serif",Font.BOLD,20));
		lbNome.setBounds(10, 70, 70, 30);
		add(lbNome);
	}
	
}
