package gui.components;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class JTableX extends JTable
{
    protected CellEditorModel rm;

    public JTableX()
     {
         super();
         rm = null;
     }

     public JTableX(TableModel tm)
     {
         super(tm);
        rm = null;
     }

     public JTableX(TableModel tm, TableColumnModel cm)
    {
         super(tm,cm);
         rm = null;
     }

     public JTableX(TableModel tm, TableColumnModel cm,
      ListSelectionModel sm)
     {
         super(tm,cm,sm);
         rm = null;
     }

     public JTableX(int rows, int cols)
     {
         super(rows,cols);
         rm = null;
     }

     @SuppressWarnings("rawtypes")
	public JTableX(final Vector rowData, final Vector columnNames)
     {
         super(rowData, columnNames);
         rm = null;
    }
     public JTableX(final Object[][] rowData, final Object[] colNames)
     {
         super(rowData, colNames);
         rm = null;
     }
     public JTableX(TableModel tm, CellEditorModel rm)
     {
         super(tm,null,null);
        this.rm = rm;
     }

     public void setRowEditorModel(CellEditorModel rm)
     {
         this.rm = rm;
     }

    public CellEditorModel getRowEditorModel() {
         return rm;
     }

     public TableCellEditor getCellEditor(int row, int col)
     {
         TableCellEditor tmpEditor = null;
         if (rm!=null)
             tmpEditor = rm.getEditor(row,col);
         if (tmpEditor!=null)
             return tmpEditor;
         return super.getCellEditor(row,col);
     }
 }


 