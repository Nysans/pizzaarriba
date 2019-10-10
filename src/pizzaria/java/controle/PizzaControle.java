package pizzaria.java.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Borda;
import pizzaria.java.modelo.Massa;
import pizzaria.java.modelo.Pedido;
import pizzaria.java.modelo.Pizza;
import pizzaria.java.modelo.PizzaPronta;
import pizzaria.java.modelo.Produto;
import pizzaria.java.modelo.tabela.PizzaTableModel;
import pizzaria.java.persistencia.BordaMySQL;
import pizzaria.java.persistencia.MassaMySQL;
import pizzaria.java.persistencia.PizzaProntaMySQL;
import pizzaria.java.visao.PizzaPanelGUI;

public class PizzaControle {

	private PizzaPanelGUI View;
	private ArrayList<PizzaPronta> ListaPizza;
	private ArrayList<Borda> ListaBorda;
	private ArrayList<Massa> ListaMassa;
	private Pedido pedido;

	public PizzaControle(Pedido pedido) throws PizzariaException {
		this.pedido = pedido;
		View = new PizzaPanelGUI();

		View.getBtnAdicionar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAdicionar();
			}
		});
		View.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCancelar(e);
			}

		});
		View.getLstMassa().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lstMassa(e);
			}
		});
		View.getLstBorda().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lstBorda(e);
			}
		});

		listar();

		View.getTbSabores().getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				tbSabores(e);
			}
		});

		atualizarPreco();
	}

	protected void lstBorda(ActionEvent e) {
		atualizarPreco();

	}

	protected void tbSabores(TableModelEvent e) {
		atualizarPreco();
	}

	protected void lstMassa(ActionEvent e) {
		PizzaTableModel model = (PizzaTableModel) View.getTbSabores().getModel();
		model.atualizarMassa(ListaMassa.get(View.getLstMassa().getSelectedIndex()));
	}

	protected void btnCancelar(ActionEvent e) {
		JFrame frame = (JFrame) View.getParent().getParent().getParent().getParent().getParent().getParent();
		frame.dispose();
		Inicio.reiniciar();

	}

	private void atualizarPreco() {
		float preco = 0;
		PizzaTableModel model = (PizzaTableModel) View.getTbSabores().getModel();
		ArrayList<PizzaPronta> PizzasSelecionadas = new ArrayList<>();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getColunaCheckbox().get(i) == true)
				PizzasSelecionadas.add(model.getDados().get(i));
		}
		if (PizzasSelecionadas.size() == 1) {
			preco += PizzasSelecionadas.get(0).getValor();
			preco += ListaMassa.get(View.getLstMassa().getSelectedIndex()).getValor();
		} else if (PizzasSelecionadas.size() == 2) {
			preco += ((PizzasSelecionadas.get(0).getValor() + PizzasSelecionadas.get(1).getValor()) / 2);
			preco += ListaMassa.get(View.getLstMassa().getSelectedIndex()).getValor();
		}
		preco += ListaBorda.get(View.getLstBorda().getSelectedIndex()).getValor();
		View.getLbPreco().setText(Produto.getValorString(preco));
	}

	protected void btnAdicionar() {

		PizzaTableModel model = (PizzaTableModel) View.getTbSabores().getModel();
		ArrayList<Boolean> ColunaCheckBox = model.getColunaCheckbox();
		ArrayList<PizzaPronta> PizzasSelecionadas = new ArrayList<>();
		for (int i = 0; i < ColunaCheckBox.size(); i++) {
			if (ColunaCheckBox.get(i) == true) {
				PizzasSelecionadas.add(model.getDados().get(i));
			}
		}
		if (!PizzasSelecionadas.isEmpty()) {
			Pizza p = new Pizza();
			p.setMassa(ListaMassa.get(View.getLstMassa().getSelectedIndex()));
			p.setBorda(ListaBorda.get(View.getLstBorda().getSelectedIndex()));
			float valorB = p.getBorda().getValor() + p.getMassa().getValor();
			if (PizzasSelecionadas.size() == 1) {
				p.setNome(PizzasSelecionadas.get(0).getNome());
				p.setValor(valorB + PizzasSelecionadas.get(0).getValor());
				p.setIngredientes(PizzasSelecionadas.get(0).getIngredientes());
			} else if (PizzasSelecionadas.size() == 2) {
				p.setNome(PizzasSelecionadas.get(0).getNome() + " e " + PizzasSelecionadas.get(1).getNome());
				p.setValor(
						valorB + ((PizzasSelecionadas.get(0).getValor() + PizzasSelecionadas.get(1).getValor()) / 2));
				p.setIngredientes(PizzasSelecionadas.get(0).getIngredientes());
				p.getIngredientes().addAll(PizzasSelecionadas.get(1).getIngredientes());
			}

			pedido.getProdutos().add(p);
			pedido.getQnt().add(1);
			JFrame frame = (JFrame) View.getParent().getParent().getParent().getParent().getParent().getParent();
			new CarrinhoControle(pedido, frame);
			frame.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(View, "Escolha um sabor...", "Pera aí...", JOptionPane.WARNING_MESSAGE);
		}

	}

	private void listar() throws PizzariaException {
		listarCbMassa();
		listarTbPizza();
		listarCbBorda();
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
			linhas[i] = ListaMassa.get(i).getNome();
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(linhas);
		View.getLstMassa().setModel(model);
	}

	public PizzaPanelGUI getView() {
		return View;
	}

	private void listarTbPizza() throws PizzariaException {
		PizzaProntaMySQL PizzaDAO = new PizzaProntaMySQL();
		ListaPizza = PizzaDAO.listar();
		for (int i = 0; i < ListaPizza.size(); i++) {
			if (!ListaPizza.get(i).hasEstoque()) {
				ListaPizza.remove(i);
			}
		}
		PizzaTableModel model = new PizzaTableModel(ListaPizza, ListaMassa.get(View.getLstMassa().getSelectedIndex()));
		View.getTbSabores().setModel(model);
		setTamanhoTabela();

	}

	private void setTamanhoTabela() {
		View.getTbSabores().getColumnModel().getColumn(0).setResizable(false);
		View.getTbSabores().getColumnModel().getColumn(0).setPreferredWidth(34);
		View.getTbSabores().getColumnModel().getColumn(1).setResizable(false);
		View.getTbSabores().getColumnModel().getColumn(1).setPreferredWidth(120);
		View.getTbSabores().getColumnModel().getColumn(2).setResizable(false);
		View.getTbSabores().getColumnModel().getColumn(2).setPreferredWidth(228);
		View.getTbSabores().getColumnModel().getColumn(3).setResizable(false);
		View.getTbSabores().getColumnModel().getColumn(3).setPreferredWidth(60);
	}
}
