package com.invoice.model;

public class InvoiceItem {
    private int num;
    private String item;
    private int count;
    private double price;
    private Invoice invoice;

    public InvoiceItem() {
    }

    /*public InvoiceItem(String item, double price, int count) {
        this.num = num;
        this.item = item;
        this.price = price;
        this.count = count;
    }*/

    public InvoiceItem(String item, int count, double price, Invoice invoice) {
        this.item = item;
        this.count = count;
        this.price = price;
        this.invoice = invoice;
    }

    
    public InvoiceItem(int num, String item, double price, int count, Invoice invoice) {
        this.num = num;
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }


      public double getItemTotalAmount(){
          return price*count;
      }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Invoice getInvoice() {
        return invoice;
    }
    
   
    @Override
    public String toString() {
        return "InvoiceItem{" + "num=" + num + ", item=" + item + ", price=" + price + ", count=" + count + '}';
    }
    
    public String getCSVfile(){
        return invoice.getInvNum() + ", " + item + ", " + price + ", " + count;
    }
    
    
}
