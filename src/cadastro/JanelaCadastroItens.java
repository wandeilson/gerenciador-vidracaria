package cadastro;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import adm.JanelaLogin;
import adm.JanelaPrincipal;

public class JanelaCadastroItens extends JFrame {
	private JLabel lbTipoDeItem, lbTitulo, lbNome, lbUnidadeDeMedida, lbPreco, lbDescricao;
	private JRadioButton rbMQuadrado, rbMetro, rbUnidade, rbProduto, rbServico;
	private ButtonGroup groupUnidadesDeVendas, groupTipoItem;
	private JButton btCancel, btCadastrar;
	private JTextField textNome, textPreco;
	private JTextArea textAreaDescricao;
	private ItemDAO itemDAO;

	public JanelaCadastroItens() {
		super("Cadastro Produto");
		setSize(350, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		addLabels();
		addButtons();
		addTexts();

		setVisible(true);
	}

	public void addLabels() {
		lbTitulo = new JLabel("Cadastro de Produtos/Serviços");
		lbTitulo.setFont((new Font("Serif", Font.BOLD, 20)));
		lbTitulo.setBounds(35, 5, 300, 20);
		add(lbTitulo);

		lbTipoDeItem = new JLabel("Tipo de item:");
		lbTipoDeItem.setFont(new Font("Serif", Font.ITALIC, 14));
		lbTipoDeItem.setBounds(10, 30, 90, 20);
		add(lbTipoDeItem);

		rbProduto = new JRadioButton("Produto");
		rbProduto.setBounds(86, 32, 70, 20);
		add(rbProduto);
		rbServico = new JRadioButton("Serviço");
		rbServico.setBounds(180, 32, 70, 20);
		add(rbServico);

		groupTipoItem = new ButtonGroup();
		groupTipoItem.add(rbServico);
		groupTipoItem.add(rbProduto);

		lbNome = new JLabel("Nome:");
		lbNome.setFont((new Font("Serif", Font.BOLD, 14)));
		lbNome.setBounds(10, 70, 50, 20);
		add(lbNome);

		lbUnidadeDeMedida = new JLabel("Unidade de medida:");
		lbUnidadeDeMedida.setFont(new Font("Serif", Font.BOLD, 14));
		lbUnidadeDeMedida.setBounds(10, 120, 150, 20);
		add(lbUnidadeDeMedida);

		rbMetro = new JRadioButton("M");
		rbMetro.setBounds(10, 145, 35, 20);
		add(rbMetro);

		rbMQuadrado = new JRadioButton("M²");
		rbMQuadrado.setBounds(50, 145, 40, 20);
		add(rbMQuadrado);

		rbUnidade = new JRadioButton("Und");
		rbUnidade.setBounds(90, 145, 50, 20);
		add(rbUnidade);

		groupUnidadesDeVendas = new ButtonGroup();
		groupUnidadesDeVendas.add(rbMQuadrado);
		groupUnidadesDeVendas.add(rbMetro);
		groupUnidadesDeVendas.add(rbUnidade);

		lbPreco = new JLabel("Preço:");
		lbPreco.setFont(new Font("Serif", Font.BOLD, 14));
		lbPreco.setBounds(10, 190, 60, 20);
		add(lbPreco);

		lbDescricao = new JLabel("Descrição:");
		lbDescricao.setBounds(10, 230, 70, 20);
		lbDescricao.setFont(new Font("Serif", Font.BOLD, 14));
		add(lbDescricao);

	}

	public void addTexts() {
		textNome = new JTextField();
		textNome.setBounds(52, 68, 200, 30);
		add(textNome);

		textPreco = new JTextField();
		textPreco.setBounds(52, 187, 80, 30);
		add(textPreco);

		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(10, 250, 200, 50);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setWrapStyleWord(true);
//		textAreaDescricao.getCaret().setDot( Integer.MAX_VALUE );
		add(textAreaDescricao);

	}

	public void addButtons() {
		ImageIcon iconCancel = new ImageIcon("icons/cancelButton.png");
		btCancel = new JButton("Cancelar");
		btCancel.setIcon(iconCancel);
		btCancel.setBounds(170, 350, 125, 30);
		btCancel.setFont(new Font("Serif", Font.BOLD, 14));
		btCancel.addActionListener(new OuvinteBotaoCancelar());
		add(btCancel);
		btCadastrar = new JButton("Cadastrar");
		ImageIcon iconConfirm = new ImageIcon("icons/confirmButton.png");
		btCadastrar.setIcon(iconConfirm);
		btCadastrar.setFont(new Font("Serif", Font.BOLD, 14));
		btCadastrar.setBounds(30, 350, 125, 30);
		btCadastrar.addActionListener(new OvinteBotaoCadastrar());
		add(btCadastrar);

	}

	private class OuvinteBotaoCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaPrincipal();

		}

	}

	private class OvinteBotaoCadastrar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Item novoItem = null;
			TipoItem tipo = null;
			String nome = textNome.getText();
			String unidadeDeMedida = null;
			Float preco = null;
			boolean undMedidaOK = false;
			boolean tipoItemOK = false;
			String precoStr = textPreco.getText();
			String descricao = textAreaDescricao.getText();
			if (nome.isEmpty() || precoStr.isEmpty() || descricao.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, preecnha todos os campos.");

			} else if (nome.trim().isEmpty() || precoStr.trim().isEmpty() || descricao.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nenhum campo pode conter apenas espaços vazio. Tente novamente.");
			} else {
				preco = Float.parseFloat(precoStr);

				if (rbMetro.isSelected()) {
					unidadeDeMedida = "M";
					undMedidaOK = true;
				} else if (rbMQuadrado.isSelected()) {
					unidadeDeMedida = "M²";
					undMedidaOK = true;
				} else if (rbUnidade.isSelected()) {
					unidadeDeMedida = "Und";
					undMedidaOK = true;
				}

				if (undMedidaOK == true) {
					if (rbProduto.isSelected()) {
						tipo = TipoItem.PRODUTO;
						novoItem = new Produto(nome, unidadeDeMedida, descricao, preco, tipo);
						tipoItemOK = true;

					} else if (rbServico.isSelected()) {
						tipo = TipoItem.SERVIÇO;
						novoItem = new Servico(nome, unidadeDeMedida, descricao, preco, tipo);
						tipoItemOK = true;

					} else {
						JOptionPane.showMessageDialog(null, "Selecione o tipo de item.");
					}

					if (tipoItemOK) {
						itemDAO = new ItemDAO();
						itemDAO.salvarItem(novoItem);
						JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso.");
						textAreaDescricao.setText("");
						textNome.setText("");
						textPreco.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o item. Verifique os campos.");
					}

				}

			}

		}
	}

}
