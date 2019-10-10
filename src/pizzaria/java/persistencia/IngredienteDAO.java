package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Ingrediente;

public interface IngredienteDAO {

	public abstract Ingrediente selecionar(int id) throws PizzariaException;
	
	public abstract Ingrediente selecionar(String nome) throws PizzariaException;

	public abstract Ingrediente getIngrediente(ArrayList<?> dados, int i) throws PizzariaException;
	
	public abstract ArrayList<Ingrediente> listar() throws PizzariaException;
}
