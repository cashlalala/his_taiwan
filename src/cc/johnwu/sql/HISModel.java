/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.johnwu.sql;

import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author john
 */
public class HISModel {
	
	public static class EditableTableModel extends DefaultTableModel {
	    private boolean[] editable_columns; // 2d array to represent rows and columns
	    private EditableTableModel(Object[][] rowData, String[] columnName) { // constructor
	        super(rowData, columnName);
	        this.editable_columns = new boolean[columnName.length];
	        Arrays.fill(editable_columns, Boolean.FALSE);
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
	       return this.editable_columns[column];
	    }
	    public void setCellEditable(int col, boolean value) {
	    	if(col > this.getColumnCount() - 1) return;
	        this.editable_columns[col] = value; // set cell true/false
	        for(int i = 0; i < this.getRowCount(); i++)
            	this.fireTableCellUpdated(i, col);
	    }
	}
	
    /** 將ResultSet轉換為DefaultTableModel,startingRow設定起始的資料行,顯示全部*/
    public static EditableTableModel getModel(ResultSet rs){
        return getModel(rs, null, 1, 0, 0, false);
    }

    public static EditableTableModel getModel(ResultSet rs, boolean showLineNumber){
        return getModel(rs, null, 1, 0, 0, showLineNumber);
    }

    /** 將ResultSet轉換為DefaultTableModel,startingRow設定起始的資料行,顯示全部*/
    public static EditableTableModel getModel(ResultSet rs, String[] columnName){
        return getModel(rs, columnName, 1, 0, 0);
    }

    /** 將ResultSet轉換為DefaultTableModel,startingRow設定起始的資料行,顯示全部*/
    public static EditableTableModel getModel(ResultSet rs, String[] columnName, int maxColumnCount){
        return getModel(rs, columnName, 1, 0, maxColumnCount);
    }

    /** 將ResultSet轉換為DefaultTableModel,startingRow設定起始的資料行,maxRowCount設定最多存放幾筆。*/
    public static EditableTableModel getModel(ResultSet rs, int startingRow, int maxRowCount){
        return getModel(rs, null, startingRow, maxRowCount, 0);
    }

    public static EditableTableModel getModel(ResultSet rs, int showColumnCount){
        return getModel(rs, null, 1, 0, 0, false);
    }

    public static EditableTableModel getModel(ResultSet rs, int showColumnCount, boolean showLineNumber){
        return getModel(rs, null, 1, 0, showColumnCount, showLineNumber);
    }

    /** 將ResultSet轉換為DefaultTableModel,startingRow設定起始的資料行,maxRowCount設定最多存放幾筆。*/
    public static EditableTableModel getModel(ResultSet rs, String[] columnName, int startingRow, int maxRowCount){
        return getModel(rs, columnName, startingRow, maxRowCount, 0);
    }

    public static EditableTableModel getModel(ResultSet rs, String[] columnName, int startingRow, int maxRowCount, int showColumnCount){
        return getModel(rs, columnName, startingRow, maxRowCount, showColumnCount, false);
    }

    /** 將ResultSet轉換為DefaultTableModel,startingRow設定起始的資料行,maxRowCount設定最多存放幾筆。*/
    public static EditableTableModel getModel(ResultSet rs, String[] columnName, int startingRow, int maxRowCount, int showColumnCount, boolean showLineNumber){
        
    	Object[][] rowData = null;
        boolean creatDefaultColumnName = false;
        int row=0, col=0, rowCount=0;
        try {
            if(showColumnCount==0) showColumnCount = rs.getMetaData().getColumnCount();
            if(showLineNumber) showColumnCount++;
            rs.last();
            rowCount = (rs.getRow()-startingRow+1);
            rowCount = rowCount>maxRowCount?maxRowCount==0?rowCount:maxRowCount:rowCount;
            rowData = new Object[rowCount][showColumnCount];
            if(columnName == null){
                creatDefaultColumnName = true;
                columnName = new String[showColumnCount];
            }            
            
            //set row data
            if(rs.absolute(startingRow))    
            do {
                col = 0;
                if(showLineNumber) rowData[row][col++] = ""+(row+1);
                for (; col < showColumnCount; col++) {
                    rowData[row][col] = rs.getString(showLineNumber?col:(col+1));
                }
                row++;
            } while (row<rowCount && rs.next());


            //set column title
            col = 0;
            if(showLineNumber) columnName[col++] = "";
            if(creatDefaultColumnName)
            for (; col < showColumnCount; col++) {
                columnName[col] = rs.getMetaData().getColumnName(showLineNumber?col:(col+1));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new Frame(), ex);
        }
        return new EditableTableModel(rowData, columnName);
    }

}
