
package pizzaria.java.modelo;

import pizzaria.java.exception.PizzariaException;

public class Massa extends Produto {
	
	private int estoque;

	public Massa() {
		
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
