package pizzaria.java.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Pedido;
import pizzaria.java.modelo.Produto;
import pizzaria.java.modelo.tabela.ProdutoTableModel;
import pizzaria.java.persistencia.PedidoMySQL;
import pizzaria.java.visao.CarrinhoGUI;

public class CarrinhoControle {

	JFrame main;
	CarrinhoGUI View;
	Pedido pedido;

	public CarrinhoControle(Pedido pedido, JFrame main) {
		this.main = main;
		this.pedido = pedido;
		View = new CarrinhoGUI();
		listar();
		atualizarPreco();
		configurarListeners();
		setTamanhoTabela();
		View.setVisible(true);
	}

	private void listar() {
		View.getTbProdutos().setModel(new ProdutoTableModel(pedido));
	}

	private void configurarListeners() {
		PrepararTbSelect();
		View.getTbProdutos().getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				tbChange(e);
			}
		});

		View.getBtnAumentar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAumentar(e);
			}
		});
		View.getBtnContinuar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnContinuar(e);
			}
		});
		View.getBtnDiminuir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDiminuir(e);
			}
		});
		View.getBtnFinalizar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnFinalizar(e);
				} catch (PizzariaException e1) {
					JOptionPane.showMessageDialog(View, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		View.getBtnLimpar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLimpar(e);
			}
		});
		View.getBtnRemover().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRemover(e);
			}
		});
	}

	protected void tbChange(TableModelEvent e) {
		atualizarPreco();
	}

	protected void btnRemover(ActionEvent e) {
		ProdutoTableModel model = (ProdutoTableModel) View.getTbProdutos().getModel();
		model.deleteRow(View.getTbProdutos().getSelectedRow());
		PrepararTbSelect();
	}

	protected void btnLimpar(ActionEvent e) {
		if (pedido.getProdutos() != null) {
			ProdutoTableModel model = (ProdutoTableModel) View.getTbProdutos().getModel();
			model.clearTable();
		}
	}

	protected void btnFinalizar(ActionEvent e) throws PizzariaException {
		if (pedido.getProdutos().isEmpty()) {
			JOptionPane.showMessageDialog(View, "Você não tem itens no carrinho", "Compre alguns produtos",
					JOptionPane.WARNING_MESSAGE);
		} else {
			int ret = JOptionPane.showConfirmDialog(View, "Quer confirmar estes produtos?", "Comfirmar Compra",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			pedido.setValor(getPrecoPedido());
			if (ret == JOptionPane.YES_OPTION) {
				
				pedido.setMesa(JOptionPane.showInputDialog(View, "Digite o número da mesa", "Pagamento",
						JOptionPane.QUESTION_MESSAGE));
				JOptionPane.showMessageDialog(View, "Finje que tá pagando");
				new PedidoMySQL().adicionar(pedido);
				JOptionPane.showMessageDialog(View, "Pedido completo!!\nAguarde na sua mesa",
						"Obrigado por comprar :-)", JOptionPane.PLAIN_MESSAGE);
				View.dispose();
				Inicio.reiniciar();
			}
		}
	}

	protected void btnDiminuir(ActionEvent e) {
		ProdutoTableModel model = (ProdutoTableModel) View.getTbProdutos().getModel();
		if ((pedido.getQnt().get(View.getTbProdutos().getSelectedRow())) > 1) {
			pedido.getQnt().set(View.getTbProdutos().getSelectedRow(),
					(pedido.getQnt().get(View.getTbProdutos().getSelectedRow()) - 1));
			model.fireTableCellUpdated(View.getTbProdutos().getSelectedRow(), 2);
			model.fireTableCellUpdated(View.getTbProdutos().getSelectedRow(), 3);
		} else {
			int ret = JOptionPane.showConfirmDialog(View, "Você deseja remover esse produto?", "Tem certeza...",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.YES_OPTION) {
				model.deleteRow(View.getTbProdutos().getSelectedRow());
				PrepararTbSelect();
			}
		}
	}

	protected void btnContinuar(ActionEvent e) {
		View.dispose();
		main.setVisible(true);
	}

	protected void btnAumentar(ActionEvent e) {
		pedido.getQnt().set(View.getTbProdutos().getSelectedRow(),
				(pedido.getQnt().get(View.getTbProdutos().getSelectedRow()) + 1));
		ProdutoTableModel model = (ProdutoTableModel) View.getTbProdutos().getModel();
		model.fireTableCellUpdated(View.getTbProdutos().getSelectedRow(), 2);
		model.fireTableCellUpdated(View.getTbProdutos().getSelectedRow(), 3);
	}

	protected void tbSelect(ListSelectionListener list, ListSelectionEvent e) {
		View.getBtnAumentar().setEnabled(true);
		View.getBtnDiminuir().setEnabled(true);
		View.getBtnRemover().setEnabled(true);
		View.getTbProdutos().getSelectionModel().removeListSelectionListener(list);
	}

	private void setTamanhoTabela() {
		View.getTbProdutos().getColumnModel().getColumn(0).setResizable(false);
		View.getTbProdutos().getColumnModel().getColumn(0).setPreferredWidth(134);
		View.getTbProdutos().getColumnModel().getColumn(1).setResizable(false);
		View.getTbProdutos().getColumnModel().getColumn(1).setPreferredWidth(385);
		View.getTbProdutos().getColumnModel().getColumn(2).setResizable(false);
		View.getTbProdutos().getColumnModel().getColumn(2).setPreferredWidth(75);
		View.getTbProdutos().getColumnModel().getColumn(3).setResizable(false);
		View.getTbProdutos().getColumnModel().getColumn(3).setPreferredWidth(63);
	}

	private void atualizarPreco() {
		if (!pedido.getProdutos().isEmpty()) {
			View.getLbPreco().setText(Produto.getValorString(getPrecoPedido()));
		} else {
			View.getLbPreco().setText(Produto.getValorString(0));
		}
	}
	
	private float getPrecoPedido() {
			float preco = 0;
			ArrayList<Produto> produtos = pedido.getProdutos();
			ArrayList<Integer> qnt = pedido.getQnt();
			for (int i = 0; i < produtos.size(); i++) {
				preco += (produtos.get(i).getValor() * qnt.get(i));
			}
			
			return preco;
	}

	private void PrepararTbSelect() {
		View.getBtnAumentar().setEnabled(false);
		View.getBtnDiminuir().setEnabled(false);
		View.getBtnRemover().setEnabled(false);
		View.getTbProdutos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				tbSelect(this, e);
			}
		});
	}
}