package adm;

public class Administrador {

	private String email;
	private String nomeCompleto;
	private String senha;

	public Administrador(String email, String nomeCompleto, String senha) {
		this.email = email;
		this.nomeCompleto = nomeCompleto;
		this.senha = senha;
	}
	
	
	public String toString() {
		return nomeCompleto + "\n" + email;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
