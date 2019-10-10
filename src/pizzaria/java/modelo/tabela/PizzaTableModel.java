package pizzaria.java.modelo.tabela;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import pizzaria.java.modelo.Massa;
import pizzaria.java.modelo.PizzaPronta;

@SuppressWarnings("serial")
public class PizzaTableModel extends AbstractTableModel {

	private ArrayList<PizzaPronta> dados;
	private ArrayList<Boolean> colunaCheckbox;
	private String[] colunas = { "", "Sabor", "Ingredientes", "Preço" };
	Massa massa;

	public PizzaTableModel(ArrayList<PizzaPronta> dados, Massa massa) {
		this.dados = dados;
		this.massa = massa;
		colunaCheckbox = new ArrayList<>();
		for (int i = 0; i < this.dados.size(); i++) {
			colunaCheckbox.add(Boolean.FALSE);
		}
	}

	public PizzaTableModel() {
	}

	@Override
	public int getRowCount() {
		return dados.size();
	}

	@Override
	public int getColumnCount() {
		return this.colunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return colunaCheckbox.get(rowIndex);
		case 1:
			return dados.get(rowIndex).getNome();
		case 2:
			return dados.get(rowIndex).getIngredientesString();
		case 3:
			return dados.get(rowIndex).getValorString(massa);
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	public void setDados(ArrayList<PizzaPronta> dados) {
		this.dados = dados;
		for (int i = 0; i < dados.size(); i++) {
			colunaCheckbox.add(Boolean.FALSE);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		 return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			if (colunaCheckbox.get(rowIndex) == true) {
				return true;
			} else {
				int cont = 0;
				for (boolean i : colunaCheckbox) {
					if (i == true)
						cont++;
				}
				if (cont <= 1) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			this.colunaCheckbox.set(rowIndex, (Boolean) aValue);
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}

	public ArrayList<PizzaPronta> getDados() {
		return dados;
	}

	public ArrayList<Boolean> getColunaCheckbox() {
		return colunaCheckbox;
	}

	@Override
	public int findColumn(String columnName) {
		for (int i = 0; i < colunas.length; i++) {
			if (colunas[i].equals(columnName))
				return i;
		}
		return -1;
	}

	public void atualizarMassa(Massa massa) {
		this.massa = massa;
		fireTableDataChanged();
	}
}
