package cadastro;

import java.util.ArrayList;

import persistencia.Persistencia;

public class OrcamentoDAO {
	private ArrayList<Orcamento> todosOsOrcamentos = new ArrayList<>();
	private Persistencia p = new Persistencia("orcamentos.xml");

	public OrcamentoDAO() {
		try {
			todosOsOrcamentos = recuperarTodosOsOrcamentos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Orcamento> recuperarTodosOsOrcamentos() throws Exception {
		try {
			todosOsOrcamentos = (ArrayList<Orcamento>) p.recuperarArquivo("orcamentos.xml");
			return todosOsOrcamentos;
		} catch (ClassCastException e) {
			return new ArrayList<Orcamento>();
		}

	}
	
	public boolean adicionarOrcamento(Orcamento orcamento) {
		todosOsOrcamentos.add(orcamento);
		salvarTodosOsOrcamentos(todosOsOrcamentos);
		return true;
	}
	
	
	public boolean salvarTodosOsOrcamentos(ArrayList<Orcamento> todosOsOrcamentos) {
		p.salvarArquivo(todosOsOrcamentos);
		return true;
	}

}
