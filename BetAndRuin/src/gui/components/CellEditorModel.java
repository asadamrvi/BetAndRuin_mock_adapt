package gui.components;

import javax.swing.table.TableCellEditor;

public class CellEditorModel
{
	//private Hashtable data;
	private TableCellEditor[][] matrix;
	
	public CellEditorModel(int rows,int cols)      {
		//data = new Hashtable();
		matrix = new TableCellEditor[rows][cols];
	}
	public void addEditorForRow(int row, int col,TableCellEditor e )
	{
		//data.put(new Integer(row), e);
		matrix[row][col] = e;
	}
	public void removeEditorForCell(int row, int col)
	{
		//data.remove(new Integer(row));
		matrix[row][col] = null;
	}
	public TableCellEditor getEditor(int row, int col)
	{
		return matrix[row][col];
				//(TableCellEditor)data.get(new Integer(row));
	}

}
