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
public class PartListTableModel extends AbstractTableModel {

    private int maxRowIndex = -1;
    private int numRow = 500000;
    private int numCol = 5;
    private String[] columnNames = {"รหัสสินค้า", "ชื่อสินค้า", "จำนวน", "พื้นที่จัดเก็บ"};
    private Object[][] data = new Object[numRow][numCol];
    private ArrayList<Product> dataList;

    public PartListTableModel() {
        dataList = new ArrayList<Product>();

    }

    public void addRowDataAt(Product obj) {
        int row = ++maxRowIndex;

        setValueAt(obj.getID(), row, 0);
        setValueAt(obj.getName(), row, 1);
        setValueAt(obj.getQty(), row, 2);
        setValueAt(obj.getLocation(), row, 3);

        dataList.add(obj);
        fireTableDataChanged();
    }

    public void addAllData(ArrayList<Product> obj) {

        dataList = obj;

        for (Product p : dataList) {

            int row = ++maxRowIndex;

            setValueAt(p.getID(), row, 0);
            setValueAt(p.getName(), row, 1);
            setValueAt(p.getQty(), row, 2);
            setValueAt(p.getLocation(), row, 3);

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
        List<Product> listBeforRemove = new ArrayList<Product>(dataList);
        removeAllData();
        listBeforRemove.remove(rows);
        for (Product o : listBeforRemove) {
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

    public List<Product> getDataList() {
        return dataList;
    }

    public Product getOrderDataAt(int row) {
        return dataList.get(row);
    }

}
