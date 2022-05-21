package com.invoice.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoicesTMmodel extends AbstractTableModel {
    private ArrayList <Invoice> invoices;
    private String[] columns = {"No.", "Date", "Customer", "Total"};

    
    public InvoicesTMmodel(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
    

    @Override
    public int getRowCount() {
        return invoices.size();
    }
    
    public String getColumnName(int column){
        return columns[column];
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice inv = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return inv.getInvNum();
            case 1: return inv.getInvDate();
            case 2: return inv.getCustName();
            case 3: return inv.getInvoiceTotalAmount();
            default : return "";
        }
    }
}
