package listagem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import cadastro.Item;
import cadastro.JanelaCriacaoOrcamento;

public class JanelaItensParaIncluirNoOrcamento extends JanelaListagemItens {

	private JButton btAdicionar;
	private JButton btFinalizar;
	private boolean detalharOrcamentoIsNull;
	private JanelaDetalharOrcamento jDetalharOrcamento;
	private ArrayList<Item>  itensDoOrcamento;
	private ArrayList<Integer> qtdDosItensDoOrcamento;
	public JanelaItensParaIncluirNoOrcamento(JanelaDetalharOrcamento jDetalharOrcamento, ArrayList<Item>  itensDoOrcamento, ArrayList<Integer> qtdDosItensDoOrcamento) {
		setSize(600, 350);
		setLocationRelativeTo(null);
		getPainelTabela().setBounds(16, 35, 550, 200);
		configButton();
		isNull(jDetalharOrcamento);
		setVisible(true);
	}
	
	
	public boolean isNull(JanelaDetalharOrcamento jDetalharOrcamento) {
		if (jDetalharOrcamento == null) {
			this.qtdDosItensDoOrcamento = new ArrayList<>();
			this.itensDoOrcamento = new ArrayList<>();
			detalharOrcamentoIsNull = true;
			return true;
		}else {
			this.jDetalharOrcamento = jDetalharOrcamento;
			itensDoOrcamento = jDetalharOrcamento.getItensOrcamento();
			qtdDosItensDoOrcamento = jDetalharOrcamento.getQtdDosItensDoOrcamento();
			detalharOrcamentoIsNull = false;
			return false;
		}
	}
	
	

	private void configButton() {
		btAdicionar = new JButton("Adicionar item");
		btAdicionar.setBounds(50, 250, 170, 30);
		btAdicionar.setIcon(new ImageIcon("icons/add.png"));
		btAdicionar.addActionListener(new OuvinteBotaoAdicionar());
		add(btAdicionar);

		btFinalizar = new JButton("Finalizar");
		btFinalizar.setBounds(300, 250, 170, 30);
		btFinalizar.setIcon(new ImageIcon("icons/concluido.png"));
		btFinalizar.addActionListener(new OuvinteBotaoVoltar());
		add(btFinalizar);
		getBtVoltar().setEnabled(false);
		getBtEditar().setEnabled(false);
	}

	private class OuvinteBotaoVoltar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			
		}
	}
	
	private class OuvinteBotaoAdicionar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = getTabela().getSelectedRow();
			int qtdDoItem = 0;
			String qtdItemStr;
			boolean isNumeric = false;
			if (linhaSelecionada != -1) {
				Item itemSelecionado = getDados().get(linhaSelecionada);
				if(itemSelecionado.getUnidadeDeMedida().equals("M")) {
					qtdItemStr = JOptionPane.showInputDialog("Quantos metros você deseja?");
					if(qtdItemStr!=null)
						isNumeric =  qtdItemStr.matches("[+-]?\\d*(\\.\\d+)?");
				}else if(itemSelecionado.getUnidadeDeMedida().equals("M²")) {
					qtdItemStr = JOptionPane.showInputDialog("Quantos metros quadrados você deseja?");
					if(qtdItemStr!=null)
						isNumeric =  qtdItemStr.matches("[+-]?\\d*(\\.\\d+)?");
				}else {
					qtdItemStr = JOptionPane.showInputDialog("Informe quantas unidades você deseja:");
					if(qtdItemStr!=null)
						isNumeric =  qtdItemStr.matches("[+-]?\\d*(\\.\\d+)?");
				}
				
				if(!isNumeric) {
					JOptionPane.showMessageDialog(null, "Informe apenas NÚMEROS VÁLIDOS.");
				}else {
					qtdDoItem = Integer.parseInt(qtdItemStr);
					itensDoOrcamento.add(itemSelecionado);
					
					qtdDosItensDoOrcamento.add(qtdDoItem);
					if(detalharOrcamentoIsNull) {
						//faz nada
					}else {
						jDetalharOrcamento.adicionarTabela();
						jDetalharOrcamento.getTabela().repaint();
						
						
						JOptionPane.showMessageDialog(null, "Item adicionado.");
						jDetalharOrcamento.getOrcamentoEmEdicao().setItensDoOrcamento(itensDoOrcamento);
						jDetalharOrcamento.getOrcamentoEmEdicao().setQtdDosItensDoOrcamento(qtdDosItensDoOrcamento);
						dispose();
						jDetalharOrcamento.dispose();
						new JanelaDetalharOrcamento(jDetalharOrcamento.getOrcamentoEmEdicao());
						
					}
				
					JanelaCriacaoOrcamento.itensDoOrcamento = itensDoOrcamento;
					JanelaCriacaoOrcamento.qtdDosItensDoOrcamento = qtdDosItensDoOrcamento;					
				}
				
				
			} else {
				JOptionPane.showMessageDialog(null, "Selecione um item.");
			}

		}

	}

}
