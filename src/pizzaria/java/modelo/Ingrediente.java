
package pizzaria.java.modelo;

import pizzaria.java.exception.PizzariaException;

public class Ingrediente extends Produto {

	private int estoque;

	public Ingrediente() {

	}

	@Override
	public String toString() {
		return "Ingrediente{" + "Nome=" + nome + '}';
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public void setEstoque(String estoque) throws PizzariaException {
		try {
			setEstoque(Integer.parseInt(estoque));
		} catch (NumberFormatException e) {
			throw new PizzariaException("Formato inválido");
		}
	}
}
