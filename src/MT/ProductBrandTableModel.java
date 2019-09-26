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
public class ProductBrandTableModel extends AbstractTableModel {

    private int maxRowIndex = -1;
    private int numRow = 2000;
    private int numCol = 3;
    private String[] columnNames = {"รหัส", "ชื่อ"};
    private Object[][] data = new Object[numRow][numCol];
    private ArrayList<ProductBrand> dataList;

    public ProductBrandTableModel() {
        dataList = new ArrayList<ProductBrand>();

    }

    public void addAllData(ArrayList<ProductBrand> obj) {

        dataList = obj;

        for (ProductBrand p : dataList) {

            int row = ++maxRowIndex;
            setValueAt(p.getProductBrandID(), row, 0);
            setValueAt(p.getBrandName(), row, 1);

            row++;
            fireTableDataChanged();
        }

    }

//    public void removeAllData() {
//        int rows = maxRowIndex + 1;
//        if (maxRowIndex > -1) {
//            for (int i = 0; i < rows; i++) {
//                for (int j = 0; j < columnNames.length; j++) {
//                    setValueAt(null, i, j);
//                }
//                maxRowIndex--;
//            }
//            dataList.clear();
//            fireTableDataChanged();
//        }
//    }
//
//    public void addRowDataAt(Product obj) {
//        int row = ++maxRowIndex;
//        setValueAt(row + 1, row, 0);
////        setValueAt(obj.getBC(), row, 1);
//        setValueAt(obj.getName(), row, 2);
//        setValueAt(obj.getPart(), row, 3);
//        setValueAt(obj.getPV(), row, 4);
//        setValueAt(obj.getBrand(), row, 5);
//        setValueAt(obj.getLocation(), row, 6);
//        setValueAt(obj.getDate(), row, 7);
//        setValueAt(obj.getQty(), row, 8);
//        setValueAt(obj.getCost(), row, 9);
//        setValueAt(obj.getPrice(), row, 10);
//        setValueAt(obj.getDes(), row, 11);
//
//        dataList.add(obj);
//        fireTableDataChanged();
//    }
//
//    public void removeRowAt(int rows) {
//        List<Product> listBeforRemove = new ArrayList<Product>(dataList);
//        removeAllData();
//        listBeforRemove.remove(rows);
//        for (Product o : listBeforRemove) {
//            addRowDataAt(o);
//        }
//    }

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
