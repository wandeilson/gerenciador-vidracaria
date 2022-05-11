package cadastro;

import java.util.ArrayList;

import persistencia.Persistencia;

public class ItemDAO {
	private ArrayList<Item> todosOsItens = new ArrayList<Item>();
	private Persistencia p = new Persistencia("itens.xml");

	public ItemDAO() {
		try {
			todosOsItens = recuperarTodosOsItens();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Item> recuperarTodosOsItens() throws Exception {
		try {
			todosOsItens = (ArrayList<Item>) p.recuperarArquivo("itens.xml");
			return todosOsItens;
		} catch (ClassCastException e) {
			return new ArrayList<Item>();
		}

	}

	public boolean salvarItem(Item item) {
		todosOsItens.add(item);
		p.salvarArquivo(todosOsItens);
		return true;
	}

}
