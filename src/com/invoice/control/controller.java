package com.invoice.control;

import com.invoice.display.InvoiceDialogBox;
import com.invoice.display.InvoiceMainFrame;
import com.invoice.display.ItemDialogBox;
import com.invoice.model.Invoice;
import com.invoice.model.InvoiceItem;
import com.invoice.model.InvoicesTMmodel;
import com.invoice.model.ItemsTableModel;
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

public class controller implements ActionListener, ListSelectionListener{
    
    private InvoiceMainFrame frame;
    private InvoiceDialogBox invDialog;
    private ItemDialogBox itemDialog;

    public controller(InvoiceMainFrame frame) {
        this.frame= frame;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String ActionCommand =  e.getActionCommand();
        System.out.println("Excuted Action: " + ActionCommand);
        
        switch (ActionCommand){
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
        
        case "New Item":
            NewItem();
        break;
        
        case "Delete Item":
            DeleteItem();
        break;
        
        case "CRNewInvoiceOK":
            CRNewInvoiceOK();
        break;
        
        case "CRNewInvoiceCancel":
            CRNewInvoiceCancel();
        break;
        
        case "createItemOK":
            createItemOK();
        break;
        
        case "createItemCancel":
            createItemCancel();
        break;
        
       }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedRow = frame.getInvoicesTable().getSelectedRow();
        if (selectedRow != -1) {
        System.out.println("the selected row is: " + frame.getInvoicesTable().getSelectedRow());
        Invoice currInv = frame.getInvoices().get(selectedRow);
        frame.getInvNOLabel().setText(""+currInv.getInvNum());
        frame.getInvDateLabel().setText(currInv.getInvDate());
        frame.getCustNameLabel().setText(currInv.getCustName());
        frame.getTotalAmount().setText(""+ currInv.getInvoiceTotalAmount());
        ItemsTableModel itemsTM = new ItemsTableModel(currInv.getItems());
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
                
                ArrayList <Invoice> invoicesArray = new ArrayList <>();
                for (String headerItem: headerItems){
                    String [] headerSections = headerItem.split(",");
                    int invoiceNum= Integer.parseInt(headerSections[0]);
                    String invoiceDate = headerSections[1];
                    String CustomerName = headerSections[2];
                    
                    Invoice invoice = new Invoice (invoiceNum, invoiceDate, CustomerName);
                    invoicesArray.add(invoice);
                }
                   selection = f.showOpenDialog(frame);
                   if (selection == JFileChooser.APPROVE_OPTION) {
                       File itemFile = f.getSelectedFile();
                       Path itemPath = Paths.get(itemFile.getAbsolutePath());
                       List<String> itemLines = Files.readAllLines(itemPath);

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
        String Mheaders = "";
        String Items = "";
        for (Invoice invoice : invoices) {
            String invCSV = invoice.getCSVfile();
            Mheaders += invCSV;
            Mheaders += "\n";

            for (InvoiceItem item : invoice.getItems()) {
                String lineCSV = item.getCSVfile();
                Items += lineCSV;
                Items += "\n";
            }
        }
        try {
        JFileChooser fcr = new JFileChooser();
        int selection = fcr.showSaveDialog(frame);
            if (selection == JFileChooser.APPROVE_OPTION) {
                File MainHeaderFile = fcr.getSelectedFile();
                
                FileWriter hfw = new FileWriter(MainHeaderFile);
                hfw.write(Mheaders);
                hfw.flush();
                hfw.close();
                selection = fcr.showSaveDialog(frame);
                if (selection == JFileChooser.APPROVE_OPTION) {
                    File ItemFile = fcr.getSelectedFile();
                    
                FileWriter xfw = new FileWriter(ItemFile);
                    xfw.write(Items);
                    xfw.flush();
                    xfw.close();
                }
            }
        }catch (Exception e) {
            
        }
    }

    private void NewInvoice() {
        invDialog = new InvoiceDialogBox(frame);
        invDialog.setVisible(true);
    }

    private void DeleteSelectedInvoice() {
        int row = frame.getInvoicesTable().getSelectedRow();
        if(row != -1){
            frame.getInvoices().remove(row);
            frame.getInvTable().fireTableDataChanged();
        }
    }

    private void NewItem() {
        itemDialog = new ItemDialogBox (frame);
        itemDialog.setVisible(true);
    }

    private void DeleteItem() {
        int invSel = frame.getInvoicesTable().getSelectedRow();
        int row = frame.getItemsTable().getSelectedRow();
        
        if(invSel != -1 && row != -1){
            Invoice invoice2 = frame.getInvoices().get(invSel);
            invoice2.getItems().remove(row);
            ItemsTableModel itemModel = new ItemsTableModel (invoice2.getItems());
            frame.getItemsTable().setModel(itemModel);
            itemModel.fireTableDataChanged();
            frame.getInvTable().fireTableDataChanged();
        }
    }

    private void CRNewInvoiceOK() {
        String InvDate = invDialog.getInvDateField().getText();
        String CustName = invDialog.getCustNameField().getText();
        int num = frame.getNextInvNum();
        
        Invoice invoice = new Invoice (num, InvDate, CustName);
        frame.getInvoices().add(invoice);
        frame.getInvTable().fireTableDataChanged();
        invDialog.dispose();
        invDialog = null;
    }

    private void CRNewInvoiceCancel() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    private void createItemOK() {
        String item = itemDialog.getItemNameField().getText();
        String count = itemDialog.getItemCountField().getText();
        String price = itemDialog.getItemPriceField().getText();
        
        int countx = Integer.parseInt(count);
        double pricex = Double.parseDouble(price);
        int invSelected = frame.getInvoicesTable().getSelectedRow();
        if (invSelected != -1){
        Invoice invoice = frame.getInvoices().get(invSelected);
        InvoiceItem item2 = new InvoiceItem (item, countx, pricex, invoice);
        invoice.getItems().add(item2);
        ItemsTableModel itemTM = (ItemsTableModel) frame.getItemsTable().getModel();
        itemTM.fireTableDataChanged();
        frame.getInvTable().fireTableDataChanged();
        }
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

    private void createItemCancel() {
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

        
}

