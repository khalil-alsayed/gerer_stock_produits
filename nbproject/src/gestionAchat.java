import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
public class gestionAchat {
    
    public void insertAchat(String id, int quantite, JTable tab1, JTable tab2) {
        
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            DefaultTableModel mod1 = (DefaultTableModel)tab1.getModel();
            DefaultTableModel mod2 = (DefaultTableModel)tab2.getModel();
            String fileName = "achat.txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            BufferedWriter ecrit = new BufferedWriter(new FileWriter(myObj,true));
            ecrit.newLine();
            ecrit.append(id + " // "+ quantite + " //  "+dtf.format(now));
            ecrit.close();
            mod2.addRow(new Object[]{ id, quantite, dtf.format(now)});
            mod2.fireTableRowsInserted(mod2.getRowCount()-1, mod2.getRowCount());
            
            fileName = "produits.txt";
            myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            Scanner lire = new Scanner(myObj);
                ArrayList<Produit> produits = new ArrayList<>();
                int i=0;
            while (lire.hasNextLine()) {
                String data = lire.nextLine();
                String[] parts = data.split(" // ");
                if (parts.length == 4) {
                    if(parts[0].equals(id)){
                        int quan = Integer.parseInt(parts[2].trim()) + quantite;
                        mod1.setValueAt(quan, i, 2);
                        mod1.fireTableCellUpdated(i, 2);
                        Produit prod = new Produit(parts[0], parts[1], quan, Integer.parseInt(parts[3].trim()));
                        produits.add(prod);
                    }else{
                        Produit prod = new Produit( parts[0], parts[1], Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()));
                        produits.add(prod);
                    }
                    i++;
                }
            }
            lire.close();
            FileWriter fEcrit = new FileWriter(myObj);
            fEcrit.write("");
            fEcrit.close();
            BufferedWriter ecrit2 = new BufferedWriter(new FileWriter(myObj,true));
            for(Produit pro : produits){
                ecrit2.write(pro.getID()+" // "+ pro.getNom()+" // " + pro.getQuantite()+" // "+pro.getPrix());
                ecrit2.newLine();
            }
            ecrit2.close();
        } catch (IOException ex) {
            Logger.getLogger(gestionProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void getAchat(JTable tab) {
        try {
        DefaultTableModel model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;//Cela rend toutes les cellules non modifiables
                }
            };
            model.addColumn("Id");
            model.addColumn("quantite");
            model.addColumn("date");
             
            String fileName = "achat.txt";
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
