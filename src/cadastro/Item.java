package cadastro;

public class Item {
	private String nome, unidadeDeMedida, descricao;
	private TipoItem tipoItem;
	private Float preco;
	private long id;

	public Item(String nome, String unidadeDeMedida, String descricao, float preco, TipoItem tipoItem) {
		this.nome = nome;
		this.unidadeDeMedida = unidadeDeMedida;
		this.descricao = descricao;
		this.preco = preco;
		this.tipoItem = tipoItem;
		this.id = System.currentTimeMillis();
	}

	public String toString() {
		return "Nome: " + nome + "\nTipo: " + getTipoItem() + "\nUnd. Medida: " + getUnidadeDeMedida() + "\nDescrição: "
				+ getDescrcao() + "\nPreço $:" + preco + "";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(String unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public String getDescrcao() {
		return descricao;
	}

	public void setDescrcao(String descrcao) {
		this.descricao = descrcao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public String getTipoItem() {
		return tipoItem.name();
	}

	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}

	public long getId() {
		return id;
	}

}
