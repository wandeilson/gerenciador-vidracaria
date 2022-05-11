package programa;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import adm.JanelaCadastroAdministrador;
import adm.JanelaLogin;
import persistencia.Persistencia;
import persistencia.Usuarios;

/**
 * Classe principal do Projeto
 * 
 * @author Wandeilson Gomes da Silva
 * {@link github.com/wandeilson}
 */
public class Inicio {
	
	/**
	 * Método inicializador do projeto.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		 
		Persistencia p = new Persistencia("usuarios.xml");
		Usuarios usuarios = null;
		boolean jaTemAdm = true;
		try {
		usuarios = (Usuarios) p.recuperarArquivo("usuarios.xml");
		}catch(ClassCastException e) {
			new JanelaCadastroAdministrador();
			jaTemAdm = false;
		}
		if(jaTemAdm)
			new JanelaLogin();
	}
}
