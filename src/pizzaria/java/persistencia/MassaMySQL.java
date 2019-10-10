package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Massa;

public class MassaMySQL implements MassaDAO{

	@Override
	public Massa selecionar(int id) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Massa WHERE Massa_ID = ?";
		con.prepararPst(sql);

		con.setParam(1, id);
		
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getMassa(dados, 0);
		} else
			throw new PizzariaException("Massa não cadastrado: " + id);
	}

	@Override
	public Massa selecionar(String nome) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Massa WHERE Massa_nome = ?";
		con.prepararPst(sql);

		con.setParam(1, nome);
		
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getMassa(dados, 0);
		} else
			throw new PizzariaException("Massa não cadastrado: " + nome);
	}

	@Override
	public Massa getMassa(ArrayList<?> dados, int i) throws PizzariaException {
		Massa b = new Massa();
		ArrayList<?> li = (ArrayList<?>) dados.get(i);
		
		b.setID(li.get(0).toString());
		b.setNome(li.get(1).toString());
		b.setValor(li.get(2).toString());
		b.setEstoque(li.get(3).toString());
		
		return b;
	}

	@Override
	public ArrayList<Massa> listar() throws PizzariaException{
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Massa ORDER BY Massa_nome";
		con.prepararPst(sql);
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		ArrayList<Massa> resposta = new ArrayList<Massa>();
		for (int i = 0; i < dados.size(); i++) {

			Massa b = getMassa(dados, i);
			resposta.add(b);
		}
		return resposta;
	}

}
