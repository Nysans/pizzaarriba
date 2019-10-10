package pizzaria.java.exception;

public class PizzariaException extends Exception {
	private static final long serialVersionUID = 1L;

	public PizzariaException() {
		super("Nenhuma mensagem foi especificada");
	}

	public PizzariaException(String msg) {
		super(msg);
	}
}