/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author pwann
 */
public class productTypeTableModel extends AbstractTableModel {

    private int maxRowIndex = -1;
    private int numRow = 500000;
    private int numCol = 2;
    private String[] columnNames = {"ประเภทย่อย"};
    private Object[][] data = new Object[numRow][numCol];
    private ArrayList<ProductType> dataList;

    public productTypeTableModel() {
        dataList = new ArrayList<ProductType>();

    }

    public void addRowDataAt(ProductType obj) {
        int row = ++maxRowIndex;
        //setValueAt(row + 1, row, 0);
       // setValueAt(obj.getBC(), row, 0);
        setValueAt(obj.getID(), row, 1);
  

        dataList.add(obj);
        fireTableDataChanged();
    }

    public void addAllData(ArrayList<ProductType> obj) {

        dataList = obj;

        for (ProductType p : dataList) {

            int row = ++maxRowIndex;

            //setValueAt(row + 1, row, 0);
//            setValueAt(p.getBC(), row, 0);
            setValueAt(p.getName(), row, 0);
         

            row++;
            fireTableDataChanged();
        }

    }

    public void removeAllData() {
        int rows = maxRowIndex + 1;
        if (maxRowIndex > -1) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columnNames.length; j++) {
                    setValueAt(null, i, j);
                }
                maxRowIndex--;
            }
            dataList.clear();
            fireTableDataChanged();
        }
    }

    public void removeRowAt(int rows) {
        List<ProductType> listBeforRemove = new ArrayList<ProductType>(dataList);
        removeAllData();
        listBeforRemove.remove(rows);
        for (ProductType o : listBeforRemove) {
            addRowDataAt(o);
        }
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setAmount(double amt, int row, int col) {

    }

    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public int getRowCount() {
//        return data.length;
        return maxRowIndex + 1;
    }

    public int getColumnCount() {
//        return columnNames.length;
        return numCol - 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public Class getColumnClass(int c) {
        Object value = getValueAt(0, c);
        if (value == null) {
            return Object.class;
        } else {
            return value.getClass();
        }
    }

    public boolean isCellEditable(int row, int col) {

        return false;
    }

    public List<ProductType> getDataList() {
        return dataList;
    }

    public ProductType getOrderDataAt(int row) {
        return dataList.get(row);
    }

}
