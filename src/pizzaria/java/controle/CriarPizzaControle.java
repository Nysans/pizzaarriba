package pizzaria.java.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Borda;
import pizzaria.java.modelo.Ingrediente;
import pizzaria.java.modelo.Massa;
import pizzaria.java.modelo.Pedido;
import pizzaria.java.modelo.Pizza;
import pizzaria.java.modelo.Produto;
import pizzaria.java.modelo.tabela.IngredienteTableModel;
import pizzaria.java.persistencia.BordaMySQL;
import pizzaria.java.persistencia.IngredienteMySQL;
import pizzaria.java.persistencia.MassaMySQL;
import pizzaria.java.visao.CriarPizzaPanelGUI;

public class CriarPizzaControle {

	private CriarPizzaPanelGUI View;
	private ArrayList<Ingrediente> ListaIngrediente;
	private ArrayList<Borda> ListaBorda;
	private ArrayList<Massa> ListaMassa;
	private Pedido pedido;

	public CriarPizzaControle(Pedido pedido) throws PizzariaException {
		this.pedido = pedido;
		View = new CriarPizzaPanelGUI();
		View.getBtnAdicionar().addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAdicionar();
			}
		});

		View.getBtnCancelar().addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnCancelar();
			}

		});
		;

		View.getLstBorda().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lstBorda(e);

			}
		});

		View.getLstMassa().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lstMassa(e);
			}
		});
		listar();

		View.getTbIngredientes().getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				tbIngredientes(e);
			}
		});
		atualizarPreco();
	}

	protected void tbIngredientes(TableModelEvent e) {
		atualizarPreco();
	}

	private void atualizarPreco() {
		float preco = 0;
		IngredienteTableModel model = (IngredienteTableModel) View.getTbIngredientes().getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getColunaCheckbox().get(i) == true)
				preco += model.getDados().get(i).getValor();
		}
		preco += ListaBorda.get(View.getLstBorda().getSelectedIndex()).getValor();
		preco += ListaMassa.get(View.getLstMassa().getSelectedIndex()).getValor();
		View.getLbPreco().setText(Produto.getValorString(preco));
	}

	protected void lstMassa(ActionEvent e) {
		atualizarPreco();
	}

	protected void lstBorda(ActionEvent e) {
		atualizarPreco();
	}

	protected void btnCancelar() {
		JFrame frame = (JFrame) View.getParent().getParent().getParent().getParent().getParent().getParent();
		frame.dispose();
		Inicio.reiniciar();
	}

	private void listar() throws PizzariaException {
		listarCbMassa();
		listarTbIngredientes();
		listarCbBorda();
	}

	private void listarTbIngredientes() throws PizzariaException {
		IngredienteMySQL IngredienteDAO = new IngredienteMySQL();
		ListaIngrediente = IngredienteDAO.listar();
		for (int i = 0; i < ListaIngrediente.size(); i++) {
			if (ListaIngrediente.get(i).getEstoque() == 0) {
				ListaIngrediente.remove(i);
			}
		}
		IngredienteTableModel modelo = new IngredienteTableModel(ListaIngrediente);
		View.getTbIngredientes().setModel(modelo);
		setTamanhoTabela();

	}

	private void setTamanhoTabela() {
		View.getTbIngredientes().getColumnModel().getColumn(0).setResizable(false);
		View.getTbIngredientes().getColumnModel().getColumn(0).setPreferredWidth(34);
		View.getTbIngredientes().getColumnModel().getColumn(1).setResizable(false);
		View.getTbIngredientes().getColumnModel().getColumn(1).setPreferredWidth(348);
		View.getTbIngredientes().getColumnModel().getColumn(2).setResizable(false);
		View.getTbIngredientes().getColumnModel().getColumn(2).setPreferredWidth(60);
	}

	private void listarCbBorda() throws PizzariaException {
		BordaMySQL BordaDAO = new BordaMySQL();
		ListaBorda = BordaDAO.listar();
		for (int i = 0; i < ListaBorda.size(); i++) {
			if (ListaBorda.get(i).getEstoque() == 0) {
				if (ListaBorda.get(i).getNome().equals("Sem Borda")) {
					Borda b = ListaBorda.get(i);
					ListaBorda.add(0, b);
					ListaBorda.remove(i + 1);
				} else {
					ListaBorda.remove(i);
				}
			}
		}
		String[] linhas = new String[ListaBorda.size()];
		for (int i = 0; i < linhas.length; i++) {
			linhas[i] = ListaBorda.get(i).getNome() + " - " + ListaBorda.get(i).getValorString();
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(linhas);
		View.getLstBorda().setModel(model);
	}

	private void listarCbMassa() throws PizzariaException {
		MassaMySQL MassaDAO = new MassaMySQL();
		ListaMassa = MassaDAO.listar();
		for (int i = 0; i < ListaMassa.size(); i++) {
			if (ListaMassa.get(i).getEstoque() == 0) {
				ListaMassa.remove(i);
			}
		}
		String[] linhas = new String[ListaMassa.size()];
		for (int i = 0; i < linhas.length; i++) {
			linhas[i] = (ListaMassa.get(i).getNome() + " - " + ListaMassa.get(i).getValorString());
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(linhas);
		View.getLstMassa().setModel(model);
	}

	protected void btnAdicionar() {
		IngredienteTableModel model = (IngredienteTableModel) View.getTbIngredientes().getModel();
		ArrayList<Boolean> ColunaCheckBox = model.getColunaCheckbox();
		ArrayList<Ingrediente> IngredientesSelecionados = new ArrayList<>();
		for(int i = 0; i < ColunaCheckBox.size(); i ++) {
			if (ColunaCheckBox.get(i) == true) {
				IngredientesSelecionados.add(model.getDados().get(i));
			}
		}
		if(!IngredientesSelecionados.isEmpty()) {
			Pizza p = new Pizza();
			p.setIngredientes(IngredientesSelecionados);
			float valor = 0;
			p.setMassa(ListaMassa.get(View.getLstMassa().getSelectedIndex()));
			valor += p.getMassa().getValor();
			p.setBorda(ListaBorda.get(View.getLstBorda().getSelectedIndex()));
			valor += p.getBorda().getValor();
			valor += p.geValorIngredientes();
			p.setValor(valor);
			p.setNome("À Moda do Cliente");
			
			pedido.getProdutos().add(p);
			pedido.getQnt().add(1);
			JFrame frame = (JFrame) View.getParent().getParent().getParent().getParent().getParent().getParent();
			new CarrinhoControle(pedido, frame);
			frame.setVisible(false);
		}
	}

	public CriarPizzaPanelGUI getView() {
		return View;
	}
}
