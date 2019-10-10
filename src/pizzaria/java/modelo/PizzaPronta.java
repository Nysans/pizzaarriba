package pizzaria.java.modelo;

import java.util.ArrayList;

public class PizzaPronta extends Produto{

	protected ArrayList<Ingrediente> ingredientes;

	public PizzaPronta() {
	}

	public ArrayList<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getIngredientesString() {
		String s = "";
		for (int i = 0; i < ingredientes.size(); i++) {
			s += ingredientes.get(i).getNome();
			if (i < (ingredientes.size() - 1)) {
				s += ", ";
			}
		}
		return s;
	}

	public boolean hasEstoque() {
		boolean ret = true;
		for (Ingrediente i : ingredientes) {
			if (i.getEstoque() == 0) {
				ret = false;
				break;
			}
		}
		return ret;
	}

	public String getValorString(Massa massa) {
		String valor = (this.getValor() + massa.getValor()) + "";
		String s = "";
		char[] charArray = valor.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '.') {
				s = "R$ " + valor.substring(0, i) + ",";
				if (valor.substring((i + 1), charArray.length).length() == 1) {
					s += valor.substring((i + 1), charArray.length) + "0";
				} else if (valor.substring((i + 1), charArray.length).length() == 2) {
					s += valor.substring((i + 1), charArray.length);
				} else if (valor.substring((i + 1), charArray.length).length() > 2) {
					String temp = valor.substring((i + 1), charArray.length);
					int a = Integer.parseInt(temp.substring(2, 3));
					int b = Integer.parseInt(temp.substring(0, 2));
					if (a >= 5) {
						b += 1;
					}
					if ((b + "").length() == 1) {
						s += "0" + b;
					} else {
						s += b + "";
					}
				}
			}
		}
		return s;
	}

	public String getValorString(Massa massa, Borda borda) {
		String valor = (this.getValor() + massa.getValor() + borda.getValor()) + "";
		String s = "";
		char[] charArray = valor.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '.') {
				s = "R$ " + valor.substring(0, i) + ",";
				if (valor.substring((i + 1), charArray.length).length() == 1) {
					s += valor.substring((i + 1), charArray.length) + "0";
				} else if (valor.substring((i + 1), charArray.length).length() == 2) {
					s += valor.substring((i + 1), charArray.length);
				} else if (valor.substring((i + 1), charArray.length).length() > 2) {
					String temp = valor.substring((i + 1), charArray.length);
					int a = Integer.parseInt(temp.substring(2, 3));
					int b = Integer.parseInt(temp.substring(0, 2));
					if (a >= 5) {
						b += 1;
					}
					if ((b + "").length() == 1) {
						s += "0" + b;
					} else {
						s += b + "";
					}
				}
			}
		}
		return s;
	}
	
	@Override
	public float getValor() {
		float valor = 0;
		for (Ingrediente i : ingredientes) {
			valor += i.getValor();
		}
		return valor;
	}

}
