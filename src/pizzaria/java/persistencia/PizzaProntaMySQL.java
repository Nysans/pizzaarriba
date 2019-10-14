package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Ingrediente;
import pizzaria.java.modelo.PizzaPronta;

public class PizzaProntaMySQL implements PizzaProntaDAO {

	@Override
	public PizzaPronta selecionar(int id) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();

		String sql = "SELECT * FROM PizzaPronta WHERE PizzaPronta_ID = ?";
		con.prepararPst(sql);

		con.setParam(1, id);

		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getPizza(dados, 0);
		} else
			throw new PizzariaException("Pizza não cadastrada: " + id);
	}

	@Override
	public PizzaPronta selecionar(String nome) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM PizzaPronta WHERE PizzaPronta_nome = ?";
		con.prepararPst(sql);

		con.setParam(1, nome);

		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getPizza(dados, 0);
		} else
			throw new PizzariaException("Pizza não cadastrada: " + nome);
	}

	@Override
	public PizzaPronta getPizza(ArrayList<?> dados, int i) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		PizzaPronta b = new PizzaPronta();
		ArrayList<?> li = (ArrayList<?>) dados.get(i);

		b.setID(li.get(0).toString());
		b.setNome(li.get(1).toString());

		String sql = "SELECT I.* FROM Ingrediente AS I, PizzaPronta_Ingrediente AS Ponte WHERE (Ponte.PizzaPronta_id = ?) AND (I.Ingrediente_id = Ponte.Ingrediente_id)";
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
	public ArrayList<PizzaPronta> listar() throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM PizzaPronta ORDER BY PizzaPronta_Nome";
		con.prepararPst(sql);
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		ArrayList<PizzaPronta> resposta = new ArrayList<>();

		for (int i = 0; i < dados.size(); i++) {

			PizzaPronta b = getPizza(dados, i);
			resposta.add(b);
		}

		return resposta;

	}

	@Override
	public void adicionar(PizzaPronta p) throws PizzariaException {

	}

	@Override
	public void excluir(PizzaPronta p) throws PizzariaException {

	}

}
