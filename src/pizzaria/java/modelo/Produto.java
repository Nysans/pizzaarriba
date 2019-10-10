package pizzaria.java.modelo;

import pizzaria.java.exception.PizzariaException;

public class Produto {

	protected int id;
    protected float valor;
    protected String nome;

    public Produto() {
    }


	public Produto(int id, float valor, String nome) {
		this.id = id;
		this.valor = valor;
		this.nome = nome;
	}



	public float getValor() {
        return valor;
    }

    public void setValor(float Valor) {
        this.valor = Valor;
    }

    public void setValor(String valor) throws PizzariaException {
        try {
            setValor(Float.parseFloat(valor));
        } catch (NumberFormatException e) {
            throw new PizzariaException("Formato inválido");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public void setID(String ID) throws PizzariaException {
        try {
            setId(Integer.parseInt(ID));
        } catch (NumberFormatException e) {
            throw new PizzariaException("Formato inválido");
        }
    }

    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValorString() {
        String valor = this.valor + "";
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
	
	public static String getValorString(float valorI) {
        String valor = valorI + "";
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
}
