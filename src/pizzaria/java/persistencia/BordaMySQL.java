package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Borda;

public class BordaMySQL implements BordaDAO{

	@Override
	public Borda selecionar(int id) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Borda WHERE Borda_ID = ?";
		con.prepararPst(sql);

		con.setParam(1, id);
		
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getBorda(dados, 0);
		} else
			throw new PizzariaException("Borda não cadastrado: " + id);
	}

	@Override
	public Borda selecionar(String nome) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Borda WHERE Borda_nome = ?";
		con.prepararPst(sql);

		con.setParam(1, nome);
		
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (dados.size() > 0) {
			return getBorda(dados, 0);
		} else
			throw new PizzariaException("Ingrediente não cadastrado: " + nome);
	}

	@Override
	public Borda getBorda(ArrayList<?> dados, int i) throws PizzariaException {
		Borda b = new Borda();
		ArrayList<?> li = (ArrayList<?>) dados.get(i);
		
		b.setID(li.get(0).toString());
		b.setNome(li.get(1).toString());
		b.setValor(li.get(2).toString());
		b.setEstoque(li.get(3).toString());
		
		return b;
	}

	@Override
	public ArrayList<Borda> listar() throws PizzariaException {
			ConexaoMySQL con = new ConexaoMySQL();
			String sql = "SELECT * FROM Borda ORDER BY Borda_nome";
			con.prepararPst(sql);
			ArrayList<ArrayList<String>> dados = con.selecionar();
			con.close();
			ArrayList<Borda> resposta = new ArrayList<Borda>();
			for (int i = 0; i < dados.size(); i++) {

				Borda b = getBorda(dados, i);
				resposta.add(b);
			}
			return resposta;
		}
	}
