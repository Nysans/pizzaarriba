package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.PizzaPronta;

public interface PizzaProntaDAO {

	public abstract void adicionar(PizzaPronta p) throws PizzariaException;

	public abstract PizzaPronta selecionar(int id) throws PizzariaException;

	public abstract PizzaPronta selecionar(String nome) throws PizzariaException;

	public abstract PizzaPronta getPizza(ArrayList<?> dados, int i) throws PizzariaException;

	public abstract ArrayList<PizzaPronta> listar() throws PizzariaException;
	
	public abstract void excluir(PizzaPronta p) throws PizzariaException;

}
