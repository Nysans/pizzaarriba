package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Bebida;

public interface BebidaDAO {

	public abstract Bebida selecionar(int id) throws PizzariaException;

	public abstract Bebida selecionar(String nome) throws PizzariaException;

	public abstract Bebida getBebida(ArrayList<?> dados, int i) throws PizzariaException;

	public abstract ArrayList<Bebida> listar() throws PizzariaException;

}
