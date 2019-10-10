package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Massa;

public interface MassaDAO {

public abstract Massa selecionar(int id) throws PizzariaException;
	
	public abstract Massa selecionar(String nome) throws PizzariaException;

	public abstract Massa getMassa(ArrayList<?> dados, int i) throws PizzariaException;
	
	public abstract ArrayList<Massa> listar() throws PizzariaException;
	
}
