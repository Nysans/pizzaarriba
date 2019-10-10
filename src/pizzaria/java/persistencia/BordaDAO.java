package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Borda;

public interface BordaDAO {

	public abstract Borda selecionar(int id) throws PizzariaException;
	
	public abstract Borda selecionar(String nome) throws PizzariaException;

	public abstract Borda getBorda(ArrayList<?> dados, int i) throws PizzariaException;
	
	public abstract ArrayList<Borda>listar() throws PizzariaException;
	
}
