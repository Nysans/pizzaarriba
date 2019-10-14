package pizzaria.java.modelo.tabela;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import pizzaria.java.modelo.Pedido;
import pizzaria.java.modelo.Pizza;
import pizzaria.java.modelo.Produto;

public class ProdutoTableModel extends AbstractTableModel {

    private Pedido pedido;
    private ArrayList<Produto> dados;
    private String[] colunas = {"Produto", "Descrição", "Quantidade", "Preço"};

    public ProdutoTableModel(Pedido pedido) {
        this.pedido = pedido;
        dados = pedido.getProdutos();
    }

    @Override
    public int getRowCount() {
        if (dados != null) {
            return dados.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            	String s = "";
            	s += dados.get(rowIndex).getNome();
            	if (dados.get(rowIndex).getClass() == Pizza.class) {
            		Pizza p = (Pizza) dados.get(rowIndex);
            		s += ' ' + p.getMassa().getNome();
            	}
                return s;
            case 1:
                if (dados.get(rowIndex).getClass() == Pizza.class) {
                    Pizza p = (Pizza) dados.get(rowIndex);
                    return (p.getIngredientesString() + ", " + p.getBorda().getNome());
                } else {
                    return "Sem descrição";
                }
            case 2:
                return pedido.getQnt().get(rowIndex);
            case 3:
                return Produto.getValorString((dados.get(rowIndex).getValor() * pedido.getQnt().get(rowIndex)));
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    public String[] getColunas() {
        return colunas;
    }

    public void deleteRow(int rowIndex) {
        dados.remove(rowIndex);
        pedido.getQnt().remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void clearTable() {
        pedido.getProdutos().clear();
        pedido.getQnt().clear();
        fireTableDataChanged();
    }
}
