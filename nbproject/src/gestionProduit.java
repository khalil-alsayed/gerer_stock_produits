import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author khalil al sayed
 */
public class gestionProduit {


    public void getProduits(JTable tab) {
        try {
            
            DefaultTableModel model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;//Cela rend toutes les cellules non modifiables
                }
            };
            model.addColumn("Id");
            model.addColumn("nom");
            model.addColumn("quantite");
            model.addColumn("prix");
            
            String fileName = "produits.txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("Creation du fichier: " + myObj.getName());
            } else {
                System.out.println("Fichier deja existe.");
            }
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(" // ");
                if (parts.length > 1) {
                    model.addRow(new Object[]{parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim()});
                }
            }
            myReader.close();
            tab.setShowGrid(true);
            tab.setShowVerticalLines(true);
            tab.setModel(model);
            JScrollPane pane = new JScrollPane(tab);
            model.fireTableDataChanged();
        } catch (IOException ex) {
            Logger.getLogger(gestionProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateProduit(Produit p) {
        try {
            //System.out.println(p.getID()+", quantite: "+ p.getQuantite());
            String fileName = "produits.txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            Scanner myReader = new Scanner(myObj);
                ArrayList<Produit> produits = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(" // ");
                if (parts.length == 4) {
                    if(parts[0].equals(p.getID())){
                        int quan = Integer.parseInt(parts[2].trim()) - p.getQuantite();
                        p.setQuantite(quan);
                        p.setNom(parts[1]);
                        p.setPrix(Integer.parseInt(parts[3].trim()));
                        produits.add(p);
                    }else{
                        Produit prod = new Produit( parts[0], parts[1], Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()));
                        produits.add(prod);
                    }
                }
            }
            myReader.close();
            FileWriter fwriter = new FileWriter(myObj);
            fwriter.write("");
            fwriter.close();
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(myObj,true));
            for(Produit pro : produits){
                myWriter.write(pro.getID()+" // "+ pro.getNom()+" // " + pro.getQuantite()+" // "+pro.getPrix());
                myWriter.newLine();
            }
            myWriter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(gestionProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertRecord(Produit p) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String fileName = "orders.txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(myObj,true));
            myWriter.newLine();
            myWriter.append(p.getID() + " // "+ p.getQuantite() + " //  "+dtf.format(now));
            myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(gestionProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getOrders(JTable tab) {
        try {
            DefaultTableModel model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;//Cela rend toutes les cellules non modifiables
                }
            };
            model.addColumn("Id");
            model.addColumn("quantite");
            model.addColumn("date");

            
            String fileName = "orders.txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(" // ");
                if (parts.length == 3) {
                    model.addRow(new Object[]{parts[0].trim(), parts[1].trim(), parts[2].trim()});
                }
            }
            myReader.close();
            tab.setShowGrid(true);
            tab.setShowVerticalLines(true);
            tab.setModel(model);
            JScrollPane pane = new JScrollPane(tab);
            model.fireTableDataChanged();
        } catch (IOException ex) {
            Logger.getLogger(gestionProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
