package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Ingrediente;
import pizzaria.java.modelo.Pizza;

public class PizzaMySQL implements PizzaDAO{

	@Override
	public void adicionar(Pizza p) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "INSERT INTO Pizza(Pizza_nome, Pizza_valor, Borda_id, Massa_id) VALUES (?, ?, ?, ?)";
		con.prepararPst(sql);
		con.setParam(1, p.getNome());
		con.setParam(2, p.getValor());
		con.setParam(3, p.getBorda().getId());
		con.setParam(4, p.getMassa().getId());
		con.executarPst(ConexaoMySQL.ACAO_EXECUTAR);
		con.confirmar();
		
		sql = "SELECT MAX(Pizza_id) FROM Pizza";
		con.prepararPst(sql);
		p.setId(Integer.parseInt(con.selecionar().get(0).get(0)));
		
		for(Ingrediente i : p.getIngredientes()) {
			sql = "INSERT INTO Pizza_Ingrediente VALUES (?,?)";
			con.prepararPst(sql);
			con.setParam(1, p.getId());
			con.setParam(2, i.getId());
			con.executarPst(ConexaoMySQL.ACAO_CONFIRMAR);
		}
		con.close();
	}

	@Override
	public Pizza getPizza(ArrayList<?> dados, int i) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		Pizza b = new Pizza();
		ArrayList<?> li = (ArrayList<?>) dados.get(i);

		b.setID(li.get(0).toString());
		b.setNome(li.get(1).toString());
		b.setValor(li.get(2).toString());
		
		String sql = "SELECT * FROM Borda WHERE (Borda_id = ?)";
		con.prepararPst(sql);
		con.setParam(1, li.get(3).toString());
		dados = con.selecionar();
		BordaMySQL d = new BordaMySQL();
		b.setBorda(d.getBorda(dados, 0));
		
		sql = "SELECT * FROM Massa WHERE (Massa_id = ?)";
		con.prepararPst(sql);
		con.setParam(1, li.get(4).toString());
		dados = con.selecionar();
		MassaMySQL e = new MassaMySQL();
		b.setMassa((e.getMassa(dados, 0)));

		sql = "SELECT I.* FROM Ingrediente AS I, Pizza_ingrediente AS Ponte WHERE (Ponte.Pizza_id = ?) AND (I.Ingrediente_id = Ponte.Ingrediente_id)";
		con.prepararPst(sql);
		con.setParam(1, b.getId());
		ArrayList<ArrayList<String>> ingrediente = con.selecionar();
		con.close();
		ArrayList<Ingrediente> co_ingrediente = new ArrayList<>();

		for (i = 0; i < ingrediente.size(); i++) {

			IngredienteMySQL t = new IngredienteMySQL();
			Ingrediente temp = t.getIngrediente(ingrediente, i);
			co_ingrediente.add(temp);
		}

		b.setIngredientes(co_ingrediente);
		

		return b;
	}

	@Override
	public void excluir(Pizza p) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		

		String sql = "DELETE FROM Pizza_Ingrediente WHERE (Pizza_id = ?)";
		con.prepararPst(sql);
		con.setParam(1, p.getId());
		con.executarPst(ConexaoMySQL.ACAO_EXECUTAR);

		sql = "DELETE FROM Pizza WHERE (Pizza_id = ?)";
		con.prepararPst(sql);
		con.setParam(1, p.getId());
		con.executarPst(ConexaoMySQL.ACAO_FECHAR);
	}

	@Override
	public Pizza selecionar(int id) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Pizza WHERE Pizza_id = ?";
		con.prepararPst(sql);

		con.setParam(1, id);

		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getPizza(dados, 0);
		} else
			throw new PizzariaException("Pizza não cadastrado: " + id);
	}

}
