package cadastro;

import java.util.ArrayList;

import persistencia.Persistencia;

public class ClienteDAO {
	private ArrayList<Cliente> todosOsClientes = new ArrayList<>();
	public ArrayList<Cliente> getTodosOsClientes() {
		return todosOsClientes;
	}

	public void setTodosOsClientes(ArrayList<Cliente> todosOsClientes) {
		this.todosOsClientes = todosOsClientes;
	}

	private Persistencia p = new Persistencia("clientes.xml");

	public ClienteDAO() {
		try {
			todosOsClientes = recuperarTodosOsClientes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean excluirCliente(int index) {
		todosOsClientes.remove(index);
		salvarTodosOsClientes(todosOsClientes);
		return true;
	}

	public ArrayList<Cliente> recuperarTodosOsClientes() throws Exception {
		try {
			todosOsClientes = (ArrayList<Cliente>) p.recuperarArquivo("clientes.xml");
			return todosOsClientes;
		} catch (ClassCastException e) {
			return new ArrayList<Cliente>();
		}

	}
	
	public void salvarClienteQueFoiAlterado(Cliente cliente) {
		for (int i = 0; i< todosOsClientes.size(); i++) {
			if(todosOsClientes.get(i).getId() == cliente.getId()) {
				todosOsClientes.set(i, cliente);
				salvarTodosOsClientes(todosOsClientes);
			}
		}
	}
	
	
	public boolean adicionarCliente(Cliente cliente) {
		for(Cliente c: todosOsClientes) {
			if(c.getEmail().equals(cliente.getEmail())) {
				return false;
			}
		}
		todosOsClientes.add(cliente);
		salvarTodosOsClientes(todosOsClientes);
		return true;
	}
	
	public boolean salvarTodosOsClientes(ArrayList<Cliente> todosOsClientes) {
		p.salvarArquivo(todosOsClientes);
		return true;
	}

	
}
