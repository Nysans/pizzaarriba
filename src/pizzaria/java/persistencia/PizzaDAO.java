package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Pizza;

public interface PizzaDAO {

	public abstract void adicionar(Pizza p) throws PizzariaException;
	
	public abstract Pizza getPizza(ArrayList<?> dados, int i) throws PizzariaException;
	
	public abstract void excluir(Pizza p) throws PizzariaException;
	
	public abstract Pizza selecionar(int id) throws PizzariaException;
	
}
