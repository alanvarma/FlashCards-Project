package model;

import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;


public class ItemTableModel extends AbstractTableModel implements
		ChangeListener {
	

	private String[] columnNames = { "Word", "Description"};
	

	private List<Card> items;
	
	public ItemTableModel(List<Card> items) {
		this.items = items;
	}


	public void stateChanged(ChangeEvent arg0) {
		fireTableDataChanged();
	}


	public String getColumnName(int col) {
		return columnNames[col];
	}


	public int getColumnCount() {
		return columnNames.length;
	}


	public void add(Card a) {
		if (a != null) {
			items.add(a);
			fireTableRowsInserted(items.size() - 1, items.size() - 1);
			fireTableDataChanged();
		}
	}

	
	public void delete(int index) {
		if (index < 0 || index >= items.size()) {
			return;
		}
		items.remove(index);
		fireTableDataChanged();

	}

	
	public void clear(){
		items.clear();
		fireTableDataChanged();
	}


	public int getRowCount() {
		return items.size();
	}


	public Object getValueAt(int row, int col) {
		Object val = null;
		switch (col) {

		case 0:
			val = items.get(row).getWord();
			break;
		case 1:
			val = items.get(row).getDescription();
			break;
		}
		return val;
	}
}
