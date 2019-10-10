package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Pedido;

public interface PedidoDAO {

	public abstract void adicionar(Pedido p) throws PizzariaException;
	
	public abstract void excluir(Pedido p) throws PizzariaException;
	
	public abstract Pedido selecionar(int id) throws PizzariaException;

	public abstract Pedido selecionar(String nome) throws PizzariaException;

	public abstract Pedido getPedido(ArrayList<?> dados, int i) throws PizzariaException;

	public abstract ArrayList<Pedido> listar() throws PizzariaException;

}
