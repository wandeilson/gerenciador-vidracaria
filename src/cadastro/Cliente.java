package cadastro;

public class Cliente {
	private String nome, email, endereco;
	private long id;
	private DadosDoClienteParaOrcamento dadosDoClienteParaOrcamento;
	
	
	public String toString() {
		return "Email: "+ email;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}

	

	public Cliente(String nome, String email, String endereco) {
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.id = System.currentTimeMillis();
	}
	

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public long getId() {
		return id;
	}


	public DadosDoClienteParaOrcamento getDadosDoClienteParaOrcamento() {
		return dadosDoClienteParaOrcamento;
	}


	public void setDadosDoClienteParaOrcamento(DadosDoClienteParaOrcamento dadosDoClienteParaOrcamento) {
		this.dadosDoClienteParaOrcamento = dadosDoClienteParaOrcamento;
	}
}
