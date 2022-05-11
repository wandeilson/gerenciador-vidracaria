package listagem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

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
import cadastro.Orcamento;
import cadastro.OrcamentoDAO;
import cadastro.StatusOrcamento;
import gerarPDF.GeradorDePDF;

public class JanelaListagemOrcamentos extends JFrame {

	private JButton btVoltar, btDetalhar, btDeletar, btClonar, btOrdenar, btGerarPDF;
	private JTable tabela;
	private ArrayList<Orcamento> todosOrcamentos;
	private JScrollPane painelTabela;
	private OrcamentoDAO orcamentoDAO;
	private DefaultTableModel modelo;
	public static String ordenacao;
	private ArrayList<Orcamento> todosOrcamentosContratados;
	private ArrayList<Orcamento> arrayEmUsoAtualmente;
	

	public JanelaListagemOrcamentos() {
		super("Listagem de orçamentos");
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		addLabels();
		addButton();
		

		todosOrcamentos = new ArrayList<>();
		orcamentoDAO = new OrcamentoDAO();
		try {
			todosOrcamentos = orcamentoDAO.recuperarTodosOsOrcamentos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ordenacao != null) {
			if (ordenacao.equals("Valor")) {
				Collections.sort(todosOrcamentos);
			}
		}

		adicionarTabela(todosOrcamentos);
		setVisible(true);
	}

	private void addLabels() {
		JLabel lbTitulo = new JLabel("Orçamentos cadastrados");
		lbTitulo.setFont(new Font("Serif", Font.BOLD, 26));
		lbTitulo.setBounds(190, 1, 300, 24);
		add(lbTitulo);

	}

	private void addButton() {

		btVoltar = new JButton("Voltar");
		ImageIcon iconBack = new ImageIcon("icons/back.png");
		btVoltar.setIcon(iconBack);
		btVoltar.setBounds(555, 600, 125, 30);
		btVoltar.setFont(new Font("Serif", Font.BOLD, 14));
		btVoltar.addActionListener(new OuvinteBotaoVoltar());

		btDetalhar = new JButton("Detalhar");
		ImageIcon iconDetails = new ImageIcon("icons/details.png");
		btDetalhar.setIcon(iconDetails);
		btDetalhar.setBounds(300, 600, 125, 30);
		btDetalhar.setFont(new Font("Serif", Font.BOLD, 14));
		btDetalhar.addActionListener(new OuvinteBotaoDetalhar());

		btDeletar = new JButton("Deletar");
		ImageIcon iconDelete = new ImageIcon("icons/remove.png");
		btDeletar.setIcon(iconDelete);
		btDeletar.setFont(new Font("Serif", Font.BOLD, 14));
		btDeletar.setBounds(170, 600, 125, 30);
		btDeletar.addActionListener(new OuvinteBotaoDeletar());

		btClonar = new JButton("Clonar");
		ImageIcon iconClone = new ImageIcon("icons/copy.png");
		btClonar.setIcon(iconClone);
		btClonar.setFont(new Font("Serif", Font.BOLD, 14));
		btClonar.setBounds(30, 600, 125, 30);
		btClonar.addActionListener(new OuvinteBotaoClonar());

		btOrdenar = new JButton("Ordenar por");
		btOrdenar.setFont(new Font("Serif", Font.BOLD, 14));
		btOrdenar.setBounds(430, 600, 125, 30);
		btOrdenar.addActionListener(new OuvinteBotaoOrdenar());

		btGerarPDF = new JButton("Gerar PDF");
		btGerarPDF.setFont(new Font("Serif", Font.BOLD, 14));
		btGerarPDF.setBounds(555, 5, 125, 30);
		btGerarPDF.addActionListener(new OuvinteBotaoGerarPDF());

		if (!JanelaLogin.fezLogin) {
			add(btGerarPDF);
			add(btVoltar);
		} else {
			add(btOrdenar);
			add(btClonar);
			add(btDeletar);
			add(btDetalhar);
			add(btGerarPDF);
			add(btVoltar);
		}

	}

	private class OuvinteBotaoGerarPDF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada != -1) {
				Orcamento orSelecionado = arrayEmUsoAtualmente.get(linhaSelecionada);
				GeradorDePDF g = new GeradorDePDF();
				if (g.gerarRelatorio(orSelecionado)) {
					JOptionPane.showMessageDialog(null, "Orçamento gerado com sucesso.");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Selecione um orçamento.");
			}

		}

	}

	private class OuvinteBotaoOrdenar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String tipoOrdenacao[] = { "Valor", };
			String novoStatus = (String) JOptionPane.showInputDialog(null, "Escolha o tipo de ordenação", "Ordenacao",
					JOptionPane.QUESTION_MESSAGE, null, tipoOrdenacao, tipoOrdenacao[0]);
			if (novoStatus != null) {
				if (novoStatus.equals("Valor")) {
					JanelaListagemOrcamentos.ordenacao = "Valor";
					dispose();
					new JanelaListagemOrcamentos();

				}
			}

		}

	}

	private class OuvinteBotaoClonar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada != -1) {
				Orcamento orSelecionado = todosOrcamentos.get(linhaSelecionada);
				Orcamento orcamentoClonado = null;
				try {
					orcamentoClonado = (Orcamento) orSelecionado.clone();
				} catch (CloneNotSupportedException e2) {
					e2.printStackTrace();
				}

				orcamentoClonado.setStatus(StatusOrcamento.ORCADO);
				orcamentoClonado.setIdDoOrcamento(System.currentTimeMillis());
				orcamentoDAO.adicionarOrcamento(orcamentoClonado);
				JOptionPane.showMessageDialog(null, "Orçamento clonado.");
				try {
					todosOrcamentos = orcamentoDAO.recuperarTodosOsOrcamentos();
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
				tabela.repaint();

			} else {
				JOptionPane.showMessageDialog(null, "Selecione um orçamento.");
			}

		}

	}

	private class OuvinteBotaoDeletar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada != -1) {
				Orcamento orSelecionado = todosOrcamentos.get(linhaSelecionada);
				if (orSelecionado.getStatus() == StatusOrcamento.ENTREGUE
						|| orSelecionado.getStatus() == StatusOrcamento.CONTRATADO) {
					JOptionPane.showMessageDialog(null, "Este orçamento NÃO pode ser deletado.");
				} else {
					int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este orçamento ?",
							"Excluir orçamento", JOptionPane.YES_NO_OPTION);
					if (opcao == JOptionPane.YES_OPTION) {
						todosOrcamentos.remove(linhaSelecionada);
						orcamentoDAO.salvarTodosOsOrcamentos(todosOrcamentos);
						modelo.removeRow(linhaSelecionada);
						tabela.repaint();
						JOptionPane.showMessageDialog(null, "Orçamento excluído.");
					} else if (opcao == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(null, "Orçamento não excluído");
					} else if (opcao == JOptionPane.CLOSED_OPTION) {
						JOptionPane.showMessageDialog(null, "Orçamento não excluído");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Selecione um orçamento.");
			}
		}
	}

	private class OuvinteBotaoVoltar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			new JanelaPrincipal();

		}

	}

	private class OuvinteBotaoDetalhar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada != -1) {
				Orcamento oSelecionado = todosOrcamentos.get(linhaSelecionada);
				new JanelaDetalharOrcamento(oSelecionado);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Selecione um orçamento.");
			}

		}

	}

	public void adicionarTabela(ArrayList<Orcamento> todosOrcamentos) {

		modelo = new DefaultTableModel();
		// Definir as colunas
		modelo.addColumn("Id: ");
		modelo.addColumn("Status");
		modelo.addColumn("Nome");
		modelo.addColumn("ID proprietário");
		modelo.addColumn("Valor total R$");

		
		// Definir as linhas

		if (JanelaLogin.fezLogin) {

			arrayEmUsoAtualmente = todosOrcamentos;

			for (Orcamento orcamento : todosOrcamentos) {
				Object[] linha = new Object[6];
				linha[0] = orcamento.getIdDoOrcamento();
				linha[1] = orcamento.getStatus();
				linha[2] = orcamento.getNomeDoOrcamento();
				linha[3] = orcamento.getIdDonoDoOrcamento();
				linha[4] = orcamento.getValorTotal();
				modelo.addRow(linha);
			}

		} else {

			arrayEmUsoAtualmente = todosOrcamentosContratados;

			for (Orcamento orcamento : todosOrcamentos) {
				if (orcamento.getStatus() == StatusOrcamento.CONTRATADO) {
					todosOrcamentosContratados = new ArrayList<>();
					todosOrcamentosContratados.add(orcamento);
				}

			}

			arrayEmUsoAtualmente = todosOrcamentosContratados;

			for (Orcamento orcamento : todosOrcamentosContratados) {
				Object[] linha = new Object[6];
				linha[0] = orcamento.getIdDoOrcamento();
				linha[1] = orcamento.getStatus();
				linha[2] = orcamento.getNomeDoOrcamento();
				linha[3] = orcamento.getIdDonoDoOrcamento();
				linha[4] = orcamento.getValorTotal();
				modelo.addRow(linha);

			}

		}

		tabela = new JTable(modelo);
//		tabela.setEnabled(false);
		painelTabela = new JScrollPane(tabela);
		painelTabela.setBounds(10, 35, 665, 550);
		add(painelTabela);
	}

	public ArrayList<Orcamento> getDados() {
		return todosOrcamentos;
	}

	public void setDados(ArrayList<Orcamento> dados) {
		this.todosOrcamentos = dados;
	}


}
