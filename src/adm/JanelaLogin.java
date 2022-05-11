package adm;

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

import persistencia.Usuarios;
import persistencia.Persistencia;

public class JanelaLogin extends JFrame{
	private Persistencia p;
	private Usuarios usuarios;
	private JLabel lbEmail, lbSenha;
	private JButton btCancel, btLogin;
	private JTextField textEmail;
	private JPasswordField jpassSenha;
	private JLabel lbLogin;
	
	public static boolean fezLogin;
	
	public JLabel getLbLogin() {
		return lbLogin;
	}

	public void setLbLogin(JLabel lbLogin) {
		this.lbLogin = lbLogin;
	}
	

	public JanelaLogin() {
		super("Login");
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
		textEmail.setBounds(73, 103, 200, 30);
		add(textEmail);
		jpassSenha = new JPasswordField();
		jpassSenha.setBounds(73, 153, 200, 30);
		
		add(jpassSenha);
		
	}

	private void addButtons() {
		ImageIcon iconCancel = new ImageIcon("icons/cancelButton.png");
		btCancel = new JButton("Cancelar");
		btCancel.setIcon(iconCancel);
		btCancel.setBounds(150, 220, 125, 30);
		btCancel.setFont(new Font("Serif",Font.BOLD,14));
		btCancel.addActionListener(new OuvinteCancelar());
		add(btCancel);
		
		btLogin = new JButton("Login");
		ImageIcon iconConfirm = new ImageIcon("icons/confirmButton.png");
		btLogin.setIcon(iconConfirm);
		btLogin.setFont(new Font("Serif",Font.BOLD,14));
		btLogin.setBounds(10, 220, 125, 30);
		btLogin.addActionListener(new OuvinteLogin());
		
		add(btLogin);
	}
	
	private class OuvinteLogin implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			p = new Persistencia("usuarios.xml");
			usuarios = (Usuarios) p.recuperarArquivo("usuarios.xml");
			Administrador admCadastrado = usuarios.getAdm();
			if(admCadastrado.getEmail().equals(textEmail.getText())
					&& admCadastrado.getSenha().equals(jpassSenha.getText())) {
				JOptionPane.showMessageDialog(null, "Login Realizado.");
				JanelaLogin.fezLogin = true;
				dispose();
				new JanelaPrincipal();
			}else {
				JOptionPane.showMessageDialog(null, "Dados de login não conferem.");
				JanelaLogin.fezLogin = false;
				int	opcao = JOptionPane.showConfirmDialog(null, "Deseja entrar sem realizar login?", null, JOptionPane.YES_NO_OPTION);
				if(opcao == JOptionPane.YES_OPTION) {
					dispose();
					new JanelaPrincipal();
				}
			}
			
		}
		
	}

	private class OuvinteCancelar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			
		}
		
	}
	
	private void addLabels() {
		ImageIcon iconLogin = new ImageIcon("icons/login.png");
		lbLogin = new JLabel(iconLogin);
		lbLogin.setBounds(105, 20, 75, 70);
		add(lbLogin);
		lbEmail = new JLabel("E-mail:");
		lbEmail.setBounds(10, 100, 70, 30);
		lbEmail.setFont(new Font("Serif",Font.BOLD,20));
		add(lbEmail);
		lbSenha = new JLabel("Senha:");
		lbSenha.setBounds(10, 150, 70, 30);
		lbSenha.setFont(new Font("Serif",Font.BOLD,20));
		add(lbSenha);
	}
}
