package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Bebida;
import pizzaria.java.modelo.Pedido;
import pizzaria.java.modelo.Pizza;
import pizzaria.java.modelo.Produto;
import pizzaria.java.persistencia.BebidaMySQL;
import pizzaria.java.persistencia.ConexaoMySQL;

public class PedidoMySQL implements PedidoDAO {

	@Override
	public Pedido selecionar(int id) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();

		String sql = "SELECT * FROM Pedido WHERE Pizza_ID = ?";
		con.prepararPst(sql);

		con.setParam(1, id);

		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getPedido(dados, 0);
		} else
			throw new PizzariaException("Pedido não cadastrada: " + id);
	}

	@Override
	public Pedido selecionar(String nome) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Pedido WHERE Pizza_nome = ?";
		con.prepararPst(sql);

		con.setParam(1, nome);

		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getPedido(dados, 0);
		} else
			throw new PizzariaException("Pedido não cadastrado: " + nome);
	}

	@Override
	public Pedido getPedido(ArrayList<?> dados, int i) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		Pedido b = new Pedido();
		ArrayList<?> li = (ArrayList<?>) dados.get(i);

		b.setID(li.get(0).toString());
		b.setValor(li.get(1).toString());
		b.setMesa(li.get(2).toString());

		String sql = "SELECT P.* FROM Pizza AS P, Pedido_Pizza AS Ponte WHERE (Ponte.Pedido_id = ?) AND (P.Pizza_id = Ponte.Pizza_id)";
		con.prepararPst(sql);
		con.setParam(1, b.getID());
		ArrayList<ArrayList<String>> pizza = con.selecionar();

		for (i = 0; i < pizza.size(); i++) {

			PizzaMySQL t = new PizzaMySQL();
			Pizza p = t.getPizza(pizza, i);
			b.getProdutos().add(p);

			sql = "SELECT (Ponte.Pedido_Pizza_qnt) FROM Pedido_Pizza AS Ponte WHERE Ponte.Pizza_id = ?";
			con.prepararPst(sql);
			con.setParam(1, p.getId());

			b.getQnt().add((b.getProdutos().size() - 1), Integer.parseInt(con.selecionar().get(0).get(0)));
		}

		sql = "SELECT B.* FROM Bebida AS B, Pedido_Bebida AS Ponte WHERE (Ponte.Pedido_id = ?) AND (B.Bebida_id = Ponte.Bebida_id)";
		con.prepararPst(sql);
		con.setParam(1, b.getID());
		ArrayList<ArrayList<String>> bebida = con.selecionar();

		for (i = 0; i < bebida.size(); i++) {

			BebidaMySQL t = new BebidaMySQL();
			Bebida p = t.getBebida(bebida, i);
			b.getProdutos().add(p);

			sql = "SELECT (Ponte.Pedido_Bebida_qnt) FROM Pedido_Bebida AS Ponte WHERE Ponte.Bebida_id = ?";
			con.prepararPst(sql);
			con.setParam(1, p.getId());

			b.getQnt().add((b.getProdutos().size() - 1), Integer.parseInt(con.selecionar().get(0).get(0)));
		}
		con.close();
		return b;
	}

	@Override
	public ArrayList<Pedido> listar() throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Pedido ORDER BY Pedido_id ASC";
		con.prepararPst(sql);
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		ArrayList<Pedido> resposta = new ArrayList<Pedido>();

		for (int i = 0; i < dados.size(); i++) {

			Pedido b = getPedido(dados, i);
			resposta.add(b);
		}

		return resposta;

	}

	@Override
	public void adicionar(Pedido p) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "INSERT INTO Pedido(Pedido_Valor, Pedido_mesa) VALUES (?, ?)";
		con.prepararPst(sql);
		con.setParam(1, p.getValor());
		con.setParam(2, p.getMesa());

		con.executarPst(ConexaoMySQL.ACAO_EXECUTAR);

		sql = "SELECT MAX(Pedido_id) FROM Pedido";
		con.prepararPst(sql);
		p.setID(Integer.parseInt(con.selecionar().get(0).get(0)));
		con.confirmar();
		con.close();
		con = new ConexaoMySQL();
		
		for (int i = 0; i < p.getProdutos().size(); i++) {
			if (p.getProdutos().get(i).getClass() == Pizza.class) {
				Pizza t = (Pizza) p.getProdutos().get(i);

				PizzaMySQL d = new PizzaMySQL();
				d.adicionar(t);

				sql = "SELECT MAX(Pizza_id) FROM Pizza";
				con.prepararPst(sql);
				t.setId(Integer.parseInt(con.selecionar().get(0).get(0)));

				sql = "INSERT INTO Pedido_Pizza(Pedido_id, Pizza_id, Pedido_Pizza_qnt) VALUES (?, ?, ?)";
				con.prepararPst(sql);
				con.setParam(1, p.getID());
				con.setParam(2, t.getId());
				con.setParam(3, p.getQnt().get(i));
				con.executarPst(ConexaoMySQL.ACAO_CONFIRMAR);

			} else {
				Bebida t = (Bebida) p.getProdutos().get(i);
				sql = "INSERT INTO Pedido_Bebida(Pedido_id, Bebida_id, Pedido_Bebida_qnt) VALUES (?, ?, ?)";
				con.prepararPst(sql);
				con.setParam(1, p.getID());
				con.setParam(2, t.getId());
				con.setParam(3, p.getQnt().get(i));
				con.executarPst(ConexaoMySQL.ACAO_CONFIRMAR);
			}

		}
		con.confirmar();
		con.close();
	}

	@Override
	public void excluir(Pedido p) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "DELETE FROM Pedido_Pizza WHERE Pedido_id = ?";
		con.prepararPst(sql);
		con.setParam(1, p.getID());
		con.executarPst(ConexaoMySQL.ACAO_EXECUTAR);
		
		sql = "DELETE FROM Pedido_Bebida WHERE Pedido_id = ?";
		con.prepararPst(sql);
		con.setParam(1, p.getID());
		con.executarPst(ConexaoMySQL.ACAO_EXECUTAR);
		con.confirmar();
		
		ArrayList<Produto> produtos = p.getProdutos();
		
		for(Produto i : produtos) {
			if(i.getClass() == Pizza.class)
				new PizzaMySQL().excluir((Pizza) i);
		}
		
		sql = "DELETE FROM Pedido WHERE Pedido_id = ?";
		con.prepararPst(sql);
		con.setParam(1, p.getID());
		con.executarPst(ConexaoMySQL.ACAO_FECHAR);
	}
}
