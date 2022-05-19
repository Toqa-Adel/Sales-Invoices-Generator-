package com.invoice.control;

import com.invoice.display.InvoiceDialogBox;
import com.invoice.display.InvoiceMainFrame;
import com.invoice.display.ItemDialogBox;
import com.invoice.model.Invoice;
import com.invoice.model.InvoiceItem;
import com.invoice.model.InvoicesTMmodel;
import com.invoice.model.ItemsTMmodel;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class controller implements ActionListener, ListSelectionListener {
    
    private InvoiceMainFrame frame;
    private InvoiceDialogBox invDialog;
    private ItemDialogBox itemDialog;

    public controller(InvoiceMainFrame frame) {
        this.frame= frame;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand =  e.getActionCommand();
        System.out.println("Excuted Action: " + actionCommand);
        
        switch (actionCommand){
        case "Load File":
            LoadFile();
        break;
        
        case "Save File":
            SaveFile();
        break;
        
        case "New Invoice":
            NewInvoice();
        break;
        
        case "Delete Selected Invoice":
            DeleteSelectedInvoice();
        break;
        
        case "New Selected Item":
            NewItem();
        break;
        
        case "Delete Item":
            DeleteItem();
        break;
        case "NewInvoiceOk":
            NewInvoiceOk();
        break;
        
        case "NewInvoiceCancel":
        NewInvoiceCancel();
        break;
        
        case "createNewLineOK":
        createNewLineOK();
        break;
        
        case "createNewLineCancel":
        createNewLineCancel();
        break;
        
       }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowSelected = frame.getInvoicesTable().getSelectedRow();
        if (rowSelected != -1){
        System.out.println("the selected invoice number: "+ rowSelected);
        Invoice currInv = frame.getInvoices().get(rowSelected);
        frame.getInvNOLabel().setText(""+currInv.getInvNum());
        frame.getInvDateLabel().setText(currInv.getInvDate());
        frame.getCustNameLabel().setText(currInv.getCustName());
        frame.getDatalabel4().setText(""+ currInv.getInvoiceTotalAmount());
        ItemsTMmodel itemsTM = new ItemsTMmodel(currInv.getItems());
        frame.getItemsTable().setModel(itemsTM);
        itemsTM.fireTableDataChanged();
        }
    }
    
    private void LoadFile() {
        JFileChooser f = new JFileChooser();
        try {
        int selection = f.showOpenDialog(frame);
            if(selection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = f.getSelectedFile();
            Path selectedPath = Paths.get(selectedFile.getAbsolutePath());
                List<String> headerItems= Files.readAllLines(selectedPath);
                System.out.println("file is read");
                
                ArrayList <Invoice> invoicesArray = new ArrayList <>();
                for (String headerItem: headerItems){
                    String [] headerSections = headerItem.split(",");
                    int invoiceNum= Integer.parseInt(headerSections[0]);
                    String invoiceDate = headerSections[1];
                    String CustomerName = headerSections[2];
                    
                    Invoice invoice = new Invoice (invoiceNum, invoiceDate, CustomerName);
                    invoicesArray.add(invoice);
                }
                   System.out.println("break point");
                   selection = f.showOpenDialog(frame);
                   if (selection == JFileChooser.APPROVE_OPTION) {
                       File itemFile = f.getSelectedFile();
                       Path itemPath = Paths.get(itemFile.getAbsolutePath());
                       List<String> itemLines = Files.readAllLines(itemPath);
                       System.out.println("lines are read");
                       for (String itemLine : itemLines) {
                           String itemSections [] = itemLine.split(",");
                           int invoiceNum = Integer.parseInt(itemSections[0]);
                           String itemName = itemSections[1];
                           double itemPrice = Double.parseDouble(itemSections[2]);
                           int count = Integer.parseInt(itemSections[3]);
                           Invoice voice = null;
                           for (Invoice invoice : invoicesArray) {
                              if (invoice.getInvNum() == invoiceNum){
                                  voice = invoice;
                                  break;
                              }
      
                           }
                        InvoiceItem invoicevoice = new InvoiceItem(invoiceNum, itemName,itemPrice, count, voice);
                        voice.getItems().add(invoicevoice);
                       }
                       System.out.println("this is to perview");
                   }
                   frame.setInvoices(invoicesArray);
                   InvoicesTMmodel invoicesTableObj = new InvoicesTMmodel(invoicesArray);
                   frame.setInvTable(invoicesTableObj);
                   frame.getInvoicesTable().setModel(invoicesTableObj);
                   frame.getInvTable().fireTableDataChanged();
                   
            }
        } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    

    private void SaveFile() {
        ArrayList<Invoice> invoices = frame.getInvoices();
        String headers = "";
        String items = "";
        for (Invoice inv: invoices){
            String invgetCSV = inv.getCSVfile();
            headers += invgetCSV;
            headers += "\n";
            
            for (InvoiceItem item : inv.getItems()) {
                String itemCSV = item.getCSVfile();
                items += itemCSV;
                items += "\n";
            }
        }
        
        try {
        JFileChooser fcr = new JFileChooser();
        int selection = fcr.showSaveDialog(frame);
            if (selection == JFileChooser.APPROVE_OPTION) {
                File headFile = fcr.getSelectedFile();
            
                FileWriter headfw = new FileWriter(headFile);
                headfw.write(headers);
                headfw.flush();
                headfw.close();
        
                selection = fcr.showSaveDialog(frame);
                if (selection == JFileChooser.APPROVE_OPTION) {
                File itemFile = fcr.getSelectedFile();
                FileWriter Xfw = new FileWriter(itemFile);
                Xfw.write(items);
                Xfw.flush();
                Xfw.close();
                }
            }
        } catch (Exception ex) {                    
            
            }
    }

    private void NewInvoice() {
       invDialog = new InvoiceDialogBox(frame);
        invDialog.setVisible(true);
    }

    private void DeleteSelectedInvoice() {
        int rowSelected = frame.getInvoicesTable().getSelectedRow();
        if (rowSelected != -1) {
            frame.getInvoices().remove(rowSelected);
            frame.getInvTable().fireTableDataChanged();
        }
    }
    

    private void NewItem() {
        itemDialog = new ItemDialogBox(frame);
        itemDialog.setVisible(true);
    }

    private void DeleteItem() {
        
        int rowSelected = frame.getItemsTable().getSelectedRow();
        if (rowSelected != -1 ){
           // Invoice invoice = frame.getInvoices().get(invSelected);
           // invoice.getItems().remove(invSelected);
            ItemsTMmodel itemsTMmodel = (ItemsTMmodel)
                    
            frame.getItemsTable().getModel();
            itemsTMmodel.getItems().remove(rowSelected);
            itemsTMmodel.fireTableDataChanged();
            //frame.getInvTable().fireTableDataChanged();
        }
    }
    

    private void NewInvoiceOk() {
      String Date = invDialog.getInvDateField().getText();
        String CustName = invDialog.getCustNameField().getText();
        int InvNum = frame.getNextInvoiceNum();
        
        Invoice invoice = new Invoice (InvNum, Date, CustName);
        frame.getInvoices().add(invoice);
        frame.getInvTable().fireTableDataChanged();
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    private void NewInvoiceCancel() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    private void createNewLineOK() {
        String item = itemDialog.getItemNameField().getText();
        String count2 = itemDialog.getItemCountField().getText();
        String price2 = itemDialog.getItemPriceField().getText();
        int count = Integer.parseInt(count2);
        double price = Double.parseDouble(price2);
        int selectedInvoice = frame.getInvoicesTable().getSelectedRow();
        if (selectedInvoice != -1) {
            
              Invoice invoice = frame.getInvoices().get(selectedInvoice);
              InvoiceItem itemline = new InvoiceItem(item, price, count, invoice);
              invoice.getItems().add(itemline);
              ItemsTMmodel itemTabM = (ItemsTMmodel) frame.getItemsTable().getModel();
              itemTabM.fireTableDataChanged();
              frame.getInvTable().fireTableDataChanged();
        }  
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;   
    }

    private void createNewLineCancel() {
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

   
}
