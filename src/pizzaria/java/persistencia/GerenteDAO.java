package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Gerente;

public interface GerenteDAO {

	public abstract Gerente selecionar(String user) throws PizzariaException;

	public abstract void adicionar(Gerente g) throws PizzariaException;
	
	public abstract ArrayList<String> listarUsers() throws PizzariaException;

}
