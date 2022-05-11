package cadastro;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DadosDoClienteParaOrcamento {
	
	public DadosDoClienteParaOrcamento(String nome, String telefone, String enderecoEntrega, Date dataNascimento) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.enderecoEntrega = enderecoEntrega;
		this.dataNascimento = dataNascimento;
	}
	private String nome, telefone, enderecoEntrega;
	private Date dataNascimento;
	private SimpleDateFormat sdf;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEnderedoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderedoEntrega(String enderedoEntrega) {
		this.enderecoEntrega = enderedoEntrega;
	}
	public String getDataNascimento() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(dataNascimento) ;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
}
