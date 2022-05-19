package com.invoice.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ItemsTMmodel extends AbstractTableModel{
    
    private ArrayList<InvoiceItem> items;
    private String[] columns= {"No.", "Item Name", "Price", "Count", "Item Total"};

    public ItemsTMmodel(ArrayList<InvoiceItem> items) {
        this.items = items;
    }
    
    public ArrayList <InvoiceItem> getItems(){
        return items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }
    
    public String getColumnName(int col) {
        return columns[col];
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItem item = items.get(rowIndex);
        
        switch(columnIndex){
            case 0: return item.getInvoice().getInvNum();
            case 1: return item.getItem();
            case 2: return item.getPrice();
            case 3: return item.getCount();
            case 4: return item.getItemTotalAmount();
            default : return "";
        }
    }
    
}
