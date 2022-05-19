package com.invoice.model;

import java.util.ArrayList;

public class Invoice {
    private int InvNum;
    private String InvDate;
    private String CustName;
    private ArrayList <InvoiceItem> items;
    //private double totalAmount;
    
     public Invoice() {
    }

      public Invoice(int InvNum, String InvDate, String CustName) {
        this.InvNum = InvNum;
        this.InvDate = InvDate;
        this.CustName = CustName;
    }
      
    public ArrayList <InvoiceItem> getItems() {
        if (items == null){
            items = new ArrayList<> ();
        }
        return items;
    }

    public int getInvNum() {
        return InvNum;
    }
    
    public double getInvoiceTotalAmount(){
        double amount=0.0;
        for (InvoiceItem item : getItems()){
            amount += item.getItemTotalAmount();
        }
        return amount;
    }

    public void setInvNum(int InvNum) {
        this.InvNum = InvNum;
    }

    public String getInvDate() {
        return InvDate;
    }

    public void setInvDate(String InvDate) {
        this.InvDate = InvDate;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String CustName) {
        this.CustName = CustName;
    }

    @Override
    public String toString() {
        return "Invoice{" + "InvNum=" + InvNum + ", InvDate=" + InvDate + ", CustName=" + CustName + '}';
    }
    
    public String getCSVfile (){
        return InvNum+ ","+ InvDate +","+CustName;
    }

}
