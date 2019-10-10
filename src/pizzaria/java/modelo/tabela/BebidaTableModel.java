package pizzaria.java.modelo.tabela;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import pizzaria.java.modelo.Bebida;

@SuppressWarnings("serial")
public class BebidaTableModel extends AbstractTableModel {

	private ArrayList<Bebida> dados;
	private ArrayList<Boolean> colunaCheckbox;
	private String[] colunas = { " ", "Nome", "Preço" };
	
	public BebidaTableModel(ArrayList<Bebida> dados) {
		this.dados = dados;
		colunaCheckbox = new ArrayList<>();
		for (int i = 0; i < this.dados.size(); i++) {
			colunaCheckbox.add(Boolean.FALSE);
		}
	}
	
	public BebidaTableModel() {
		
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
			return dados.get(rowIndex).getValorString();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	public void addRow(Bebida bebida) {
		dados.add(bebida);
		colunaCheckbox.add(Boolean.FALSE);
	}

	public void setDados(ArrayList<Bebida> dados) {
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
		if (columnIndex == 0)
			return true;
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			this.colunaCheckbox.set(rowIndex, (Boolean) aValue);
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}

	public ArrayList<Bebida> getDados() {
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

}
