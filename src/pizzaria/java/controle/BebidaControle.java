package pizzaria.java.controle;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Bebida;
import pizzaria.java.modelo.Pedido;
import pizzaria.java.modelo.Produto;
import pizzaria.java.modelo.tabela.BebidaTableModel;
import pizzaria.java.persistencia.BebidaMySQL;
import pizzaria.java.visao.BebidaPanelGUI;

public class BebidaControle {

	private BebidaPanelGUI View;
	private ArrayList<Bebida> ListaBebida;
	private Pedido pedido;

	public BebidaControle(Pedido pedido) throws PizzariaException {
		this.pedido = pedido;
		View = new BebidaPanelGUI();

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
		listar();
		View.getTbBebidas().getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				tbBebidas(e);
			}
		});
		atualizarPreco();
	}

	protected void tbBebidas(TableModelEvent e) {
		atualizarPreco();
	}

	private void atualizarPreco() {
		float preco = 0;
		BebidaTableModel model = (BebidaTableModel) View.getTbBebidas().getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getColunaCheckbox().get(i) == true) {
				preco += model.getDados().get(i).getValor();
			}
		}
		View.getLbPreco().setText(Produto.getValorString(preco));
	}

	protected void btnCancelar() {
		JFrame frame = (JFrame) View.getParent().getParent().getParent().getParent().getParent().getParent();
		frame.dispose();
		Inicio.reiniciar();
	}

	protected void btnAdicionar() {
		BebidaTableModel model = (BebidaTableModel) View.getTbBebidas().getModel();
		ArrayList<Boolean> ColunaCheckBox = model.getColunaCheckbox();
		ArrayList<Bebida> BebidasSelecionadas = new ArrayList<>();

		for (int i = 0; i < ColunaCheckBox.size(); i++) {
			if (ColunaCheckBox.get(i) == true) {
				BebidasSelecionadas.add(ListaBebida.get(i));
			}
		}
		if (!BebidasSelecionadas.isEmpty()) {
			pedido.getProdutos().addAll(BebidasSelecionadas);
			for(int i = 0 ; i < BebidasSelecionadas.size(); i++) {
				pedido.getQnt().add(1);
			}
			JFrame frame = (JFrame) View.getParent().getParent().getParent().getParent().getParent().getParent();
			new CarrinhoControle(pedido, frame);
			frame.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(View, "Escolha pelo menos uma bebida", "Pera aí",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void listar() throws PizzariaException {
		BebidaMySQL IngredienteDAO = new BebidaMySQL();
		ListaBebida = IngredienteDAO.listar();
		for (int i = 0; i < ListaBebida.size(); i++) {
			if (ListaBebida.get(i).getEstoque() == 0) {
				ListaBebida.remove(i);
			}
		}
		BebidaTableModel modelo = new BebidaTableModel(ListaBebida);
		View.getTbBebidas().setModel(modelo);
		setTamanhoTabela();

	}

	private void setTamanhoTabela() {
		View.getTbBebidas().getColumnModel().getColumn(0).setResizable(false);
		View.getTbBebidas().getColumnModel().getColumn(0).setPreferredWidth(34);
		View.getTbBebidas().getColumnModel().getColumn(1).setResizable(false);
		View.getTbBebidas().getColumnModel().getColumn(1).setPreferredWidth(348);
		View.getTbBebidas().getColumnModel().getColumn(2).setResizable(false);
		View.getTbBebidas().getColumnModel().getColumn(2).setPreferredWidth(60);
	}

	public BebidaPanelGUI getView() {
		return View;
	}
}
