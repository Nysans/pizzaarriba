package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Ingrediente;

public class IngredienteMySQL implements IngredienteDAO{

	@Override
	public Ingrediente selecionar(int id) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Ingrediente WHERE Ingrediente_ID = ?";
		con.prepararPst(sql);

		con.setParam(1, id);
		
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getIngrediente(dados, 0);
		} else
			throw new PizzariaException("Ingrediente não cadastrado: " + id);
	}

	@Override
	public Ingrediente selecionar(String nome) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Ingrediente WHERE Ingrediente_nome = ?";
		con.prepararPst(sql);

		con.setParam(1, nome);
		
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getIngrediente(dados, 0);
		} else
			throw new PizzariaException("Ingrediente não cadastrado: " + nome);
	}

	@Override
	public Ingrediente getIngrediente(ArrayList<?> dados, int i) throws PizzariaException {
		Ingrediente b = new Ingrediente();
		ArrayList<?> li = (ArrayList<?>) dados.get(i);
		
		b.setID(li.get(0).toString());
		b.setNome(li.get(1).toString());
		b.setValor(li.get(2).toString());
		b.setEstoque(li.get(3).toString());
		
		return b;
	}

	@Override
	public ArrayList<Ingrediente> listar() throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Ingrediente ORDER BY Ingrediente_nome";
		con.prepararPst(sql);
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		ArrayList<Ingrediente> resposta = new ArrayList<Ingrediente>();
		for (int i = 0; i < dados.size(); i++) {

			Ingrediente b = getIngrediente(dados, i);
			resposta.add(b);
		}
		return resposta;
	}
}
