package pizzaria.java.persistencia;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import pizzaria.java.exception.PizzariaException;

public class ConexaoMySQL {

	public static final int ACAO_EXECUTAR = 0;
	public static final int ACAO_CONFIRMAR = 1;
	public static final int ACAO_FECHAR = 2;

	private Connection con;
	private PreparedStatement pst;

	public ConexaoMySQL() throws PizzariaException {

		String server, database, url, usuario, senha;

		server = "localhost";
		database = "Pizzaria";
		usuario = "Pizzaria";
		senha = "stanloona";

		url = "jdbc:mysql://" + server + "/" + database + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

		try {
			con = DriverManager.getConnection(url, usuario, senha);
			con.setAutoCommit(false);
		} catch (SQLException sql) {
			String msg = "Não foi possível abrir a conexão com o banco" + "\nMensagem original: " + sql.getMessage();
                        throw new PizzariaException(msg);
		}
	}

	public void prepararPst(String sql) throws PizzariaException {
		try {
			pst = con.prepareStatement(sql);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void executarPst(int acao) throws PizzariaException {
		try {
			pst.execute();
			if (acao > 0)
				con.commit();
			if (acao > 1)
				con.close();
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void setParam(int i, int p) throws PizzariaException {
		try {
			pst.setInt(i, p);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void setParam(int i, InputStreamReader p) throws PizzariaException {
		try {
			pst.setClob(i, p);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void setParam(int i, double p) throws PizzariaException {
		try {
			pst.setDouble(i, p);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void setParam(int i, float p) throws PizzariaException {
		try {
			pst.setFloat(i, p);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void setParam(int i, String p) throws PizzariaException {
		try {
			pst.setString(i, p);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void setParam(int i, Date p) throws PizzariaException {
		try {
			pst.setDate(i, p);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void setParam(int i, Time p) throws PizzariaException {
		try {
			pst.setTime(i, p);
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void confirmar() throws PizzariaException {
		try {
			con.commit();
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public ArrayList<ArrayList<String>> selecionar() throws PizzariaException {
		try {
			ResultSet rs = pst.executeQuery();
			if (rs == null)
				throw new PizzariaException("Resultado vazio");
			ResultSetMetaData md = rs.getMetaData();
			ArrayList<ArrayList<String>> dados = new ArrayList<ArrayList<String>>();
			while (rs.next()) {
				ArrayList<String> linha = new ArrayList<String>();
				for (int i = 1; i <= md.getColumnCount(); i++)
					linha.add(rs.getString(i));
				dados.add(linha);
			}
			return dados;
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}

	public void close() throws PizzariaException {
		try {
			con.close();
		} catch (SQLException se) {
			throw new PizzariaException("Erro de persistência" + "\nMensagem original:  " + se.getMessage());
		}
	}
}