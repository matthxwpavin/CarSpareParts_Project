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
public class InvoiceTableModel extends AbstractTableModel {

    private int maxRowIndex = -1;
    private int numRow = 2000;
    private int numCol = 13;
    private String[] columnNames = {"รหัสสินค้า", "หมวดสินค้า", "ประเทสินค้า", "ชื่อสินค้า", "ยี่ห้อ", "เลขพาร์ท", "เลขพาร์ททั่วไป", "รายละเอียด", "สถานที่จัดเก็บ", "จำนวน", "หน่วยละ", "จำนวนเงืน"};
    private Object[][] data = new Object[numRow][numCol];
    private ArrayList<ProductSupply> dataListP;
    private ArrayList<Product> dataList;

    public InvoiceTableModel() {

    }

    public void addAllData(ArrayList<Product> obj) {

        dataList = obj;

        for (Product p : dataList) {

            int row = ++maxRowIndex;
            setValueAt(p.getID(), row, 0);
            setValueAt(p.getGroupName(), row, 1);
            setValueAt(p.getTypeName(), row, 2);
            setValueAt(p.getName(), row, 3);
            setValueAt(p.getBrand(), row, 4);
            setValueAt(p.getPart(), row, 5);
            setValueAt(p.getPV(), row, 6);
            setValueAt(p.getDes(), row, 7);
            setValueAt(p.getLocation(), row, 8);
            setValueAt(p.getQty(), row, 9);
            setValueAt(p.getCost2(), row, 10);
            setValueAt(p.getAmount(), row, 11);

            row++;
            fireTableDataChanged();
        }

    }

    public void addEachData(ProductSupply obj, int row) {

        setValueAt(obj.getID(), row, 0);
        setValueAt(obj.getProductName(), row, 1);
        setValueAt(obj.getQty(), row, 2);
        setValueAt(obj.getPricePerUnit(), row, 3);
        setValueAt("", row, 4);
        setValueAt(obj.getPricePerUnit() * obj.getQty(), row, 5);

        fireTableDataChanged();

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

    public void addRowDataAt(String ServiceName, Double ServiceCost) {
        int row = ++maxRowIndex;
        setValueAt(ServiceName, row, 3);
        setValueAt(ServiceCost, row, 11);

        fireTableDataChanged();
    }

    public void removeRowAt(int rows) {
        List<Product> listBeforRemove = new ArrayList<Product>(dataList);
        removeAllData();
        listBeforRemove.remove(rows);
        for (Product o : listBeforRemove) {
            //addRowDataAt(o);
        }
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
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
}
