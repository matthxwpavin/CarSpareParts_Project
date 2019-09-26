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
public class CarListTableModel extends AbstractTableModel {

    private int maxRowIndex = -1;
    private int numRow = 2000;
    private int numCol = 3;
    private String[] columnNames = {"รุ่น", "เครื่องยนต์"};
    private Object[][] data = new Object[numRow][numCol];
    private ArrayList<CarClass> dataList;

    public CarListTableModel() {
        dataList = new ArrayList<CarClass>();

    }

    public void addAllData(ArrayList<CarClass> obj) {

        dataList = obj;

        for (CarClass p : dataList) {

            int row = ++maxRowIndex;
            setValueAt(p.getGen(), row, 0);
            setValueAt(p.getEngine(), row, 1);

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

    public void addRowDataAt(CarClass obj) {
        int row = ++maxRowIndex;
        setValueAt(row + 1, row, 0);
//        setValueAt(obj.getBC(), row, 1);

        dataList.add(obj);
        fireTableDataChanged();
    }

    public void removeRowAt(int rows) {
        List<CarClass> listBeforRemove = new ArrayList<CarClass>(dataList);
        removeAllData();
        listBeforRemove.remove(rows);
        for (CarClass o : listBeforRemove) {
            addRowDataAt(o);
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
//        return data.length;
        return maxRowIndex + 1;
    }

    @Override
    public int getColumnCount() {
//        return columnNames.length;
        return numCol - 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public Class getColumnClass(int c) {
        Object value = getValueAt(0, c);
        if (value == null) {
            return Object.class;
        } else {
            return value.getClass();
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}
