package gui.components;

import javax.swing.table.DefaultTableModel;

/**
 * Custom class to make the JTable cells non-editable
 */
public class NonEditableTableModel extends DefaultTableModel
{	private static final long serialVersionUID = 1L;

	public NonEditableTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public boolean isCellEditable (int row, int column)
	   {
	       return false;
	   }
}
