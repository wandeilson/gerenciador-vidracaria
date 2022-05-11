package cadastro;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Orcamento implements Cloneable, Comparable <Orcamento>{
	private ArrayList<Item> itensDoOrcamento = new ArrayList<>();
	private ArrayList<Integer> qtdDosItensDoOrcamento = new ArrayList<Integer>();
	private long idDonoDoOrcamento;
	private long idDoOrcamento;
	private String nomeDoOrcamento;
	private StatusOrcamento status;
	private float valorTotal;
	private Date dataEntrega;
	private String horarioEntrega;
	private ClienteDAO clienteDAO;
	private SimpleDateFormat sdf;
	
	public Orcamento(ArrayList<Item> itensDoOrcamento, ArrayList<Integer> qtdDosItensDoOrcamento,
			long idDonoDoOrcamento, String nomeDoOrcamento) {
		super();
		this.itensDoOrcamento = itensDoOrcamento;
		this.qtdDosItensDoOrcamento = qtdDosItensDoOrcamento;
		this.idDonoDoOrcamento = idDonoDoOrcamento;
		this.nomeDoOrcamento = nomeDoOrcamento;
		this.status = StatusOrcamento.ORCADO;
		setIdDoOrcamento(System.currentTimeMillis());
		setValorTotal();
	}
	
	
	@Override
	public int compareTo (Orcamento orcamento) {
		if(this.getValorTotal() < orcamento.getValorTotal() ) {
			return -1;
		}else if(this.getValorTotal() > orcamento.getValorTotal()) {
			return 1;
		}		
		return 0;
	}
	
	
	
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
		
	}
	
	
	public String toString () {
		return desenhoOrcamento();
	}
	
	
	public String desenhoOrcamento () {
		int cont = 0;
		float preco = 0;
		String documento = "                      Dados orçamento\nNome: " + getNomeDoOrcamento() + "  |  "
				+ "Id dono Orçamento: " + getIdDonoDoOrcamento()
				+ "\n" + "Id do Orçamento: " + getIdDoOrcamento() + "|"
				+ "Status do Orçamento: " + getStatus();
				clienteDAO = new ClienteDAO();
				ArrayList<Cliente> todosClientes = null;
				DadosDoClienteParaOrcamento dadosDoClienteParaOrcamento = null;
				try {
					todosClientes = clienteDAO.recuperarTodosOsClientes();
				} catch (Exception e) {
					todosClientes = new ArrayList<Cliente>();
					e.printStackTrace();
				}
				for(Cliente cli : todosClientes){
					if(cli.getId() == getIdDonoDoOrcamento()) {
						dadosDoClienteParaOrcamento = cli.getDadosDoClienteParaOrcamento();
					}
				}
				documento += "                        Dados cliente\nNome cliente: "+dadosDoClienteParaOrcamento.getNome();
				documento += " | Data nasc.: " + dadosDoClienteParaOrcamento.getDataNascimento();
				documento += "\nEndereço entrega: " + dadosDoClienteParaOrcamento.getEnderedoEntrega();
				documento += "\nTelefone: " +  dadosDoClienteParaOrcamento.getTelefone();
				documento += "\nData e horário da entrega: " + getDataEntrega() +" às "+ getHorarioEntrega();
				
				documento +="\n" + "                      Itens do orçamento: \n _________"
						+ "_____________________________________________________";
		
				for(Item item: itensDoOrcamento) {
					documento += item.toString();
					preco = item.getPreco();
					documento += " Qtd: " + qtdDosItensDoOrcamento.get(cont);
					documento += "\nValor total item: "+qtdDosItensDoOrcamento.get(cont) * preco;
					documento += "\n______________________________________________________________";
					cont++;
				};
				return documento += "Valor total orçamento:$ "+ getValorTotal();
	}

	
	public String getDataEntrega() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(dataEntrega!=null) {
			return (String) sdf.format(dataEntrega);
		}else {
			return "Data não definida";
		}
		
		
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getHorarioEntrega() {
		return horarioEntrega;
	}

	public void setHorarioEntrega(String horarioEntrega) {
		this.horarioEntrega = horarioEntrega;
	}

	
	public boolean removerItem (int index) {
		itensDoOrcamento.remove(index);
		qtdDosItensDoOrcamento.remove(index);
		return true;
	}
	
	public void setValorTotal() {
		valorTotal = 0;
		for(int i = 0; i < itensDoOrcamento.size(); i++) {
			valorTotal += (itensDoOrcamento.get(i).getPreco()*qtdDosItensDoOrcamento.get(i));
		}
	}
	
	
	
	public ArrayList<Item> getItensDoOrcamento() {
		return itensDoOrcamento;
	}
	public void setItensDoOrcamento(ArrayList<Item> itensDoOrcamento) {
		this.itensDoOrcamento = itensDoOrcamento;
	}
	public long getIdDonoDoOrcamento() {
		return idDonoDoOrcamento;
	}
	public void setIdDonoDoOrcamento(long idDonoDoOrcamento) {
		this.idDonoDoOrcamento = idDonoDoOrcamento;
	}
	public ArrayList<Integer> getQtdDosItensDoOrcamento() {
		return qtdDosItensDoOrcamento;
	}
	public void setQtdDosItensDoOrcamento(ArrayList<Integer> qtdDosItensDoOrcamento) {
		this.qtdDosItensDoOrcamento = qtdDosItensDoOrcamento;
	}
	public String getNomeDoOrcamento() {
		return nomeDoOrcamento;
	}
	public void setNomeDoOrcamento(String nomeDoOrcamento) {
		this.nomeDoOrcamento = nomeDoOrcamento;
	}
	public StatusOrcamento getStatus() {
		return status;
	}
	public void setStatus(StatusOrcamento status) {
		this.status = status;
	}
	public float getValorTotal() {
		return valorTotal;
	}

	public long getIdDoOrcamento() {
		return idDoOrcamento;
	}

	public void setIdDoOrcamento(long idDoOrcamento) {
		this.idDoOrcamento = idDoOrcamento;
	}
}
