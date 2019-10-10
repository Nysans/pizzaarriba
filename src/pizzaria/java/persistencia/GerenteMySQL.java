package pizzaria.java.persistencia;

import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Gerente;

public class GerenteMySQL implements GerenteDAO {

	@Override
	public Gerente selecionar(String user) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT * FROM Gerente WHERE Gerente_user = ? ";
		con.prepararPst(sql);
		con.setParam(1, user);
		ArrayList<ArrayList<String>> dados = con.selecionar();
		con.close();
		if (!dados.isEmpty()) {
			return getGerente(dados, 0);
		} else {
			throw new PizzariaException("User Inválido");
		}
	}

	private Gerente getGerente(ArrayList<ArrayList<String>> dados, int i) throws PizzariaException {
		ArrayList<String> li = dados.get(i);
		Gerente g = new Gerente();
		g.setID(li.get(0));
		g.setUser(li.get(1));
		g.setPassword(li.get(2));

		return g;
	}

	@Override
	public void adicionar(Gerente g) throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "INSERT INTO Gerente(Gerente_user, Gerente_password) VALUES (?, ?)";
		con.prepararPst(sql);
		con.setParam(1, g.getUser());
		con.setParam(2, g.getPassword());
		con.executarPst(ConexaoMySQL.ACAO_FECHAR);
	}

	@Override
	public ArrayList<String> listarUsers() throws PizzariaException {
		ConexaoMySQL con = new ConexaoMySQL();
		String sql = "SELECT Gerente_user FROM Gerente ORDER BY Gerente_user";
		con.prepararPst(sql);
		ArrayList<ArrayList<String>> dados = con.selecionar();
		ArrayList<String> li = new ArrayList<>();
		
		for(ArrayList<String> i : dados) {
			li.add(i.get(0));
		}
		
		return li;
	}

}
