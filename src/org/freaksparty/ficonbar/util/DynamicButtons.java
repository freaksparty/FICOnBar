/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freaksparty.ficonbar.util;

import FICOnBar.entity.Product;
import FICOnBar.entity.ProductType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 *
 * @author Osane
 */
public class DynamicButtons extends javax.swing.JPanel {
    
    private float total = 0;
    private final Session session = DBSession.getSession();
       
    public DynamicButtons(List<ButtonEntity> btnEntity){
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(this.getSize().width/2, this.getSize().height));
        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER,20,20));
        this.add(topButtonPanel);
        addButtons(btnEntity, topButtonPanel);
        
     }

    
    private void addButtons(List<ButtonEntity> btnEntity, JPanel topButtonPanel){
        JButton button;

        for(ButtonEntity bt:btnEntity){
            button = new javax.swing.JButton();
            if(bt instanceof ProductType){
                ProductType tp = (ProductType)bt;
                button.setText(tp.getName());
                button.addMouseListener(tipoProductoMouseListener(tp.getProductTypeId(),topButtonPanel));
                button.setToolTipText(tp.getName());
                button.setPreferredSize(new Dimension(250,250));
            }
            else{
                Product p = (Product)bt;
                button.setText(p.getName());
                button.setToolTipText(p.getName());
                button.addMouseListener(productoMouseListener(p));
                button.setPreferredSize(new Dimension(250,250));
            }              
            topButtonPanel.add(button);
        }      
    }
    
    private MouseListener tipoProductoMouseListener(int id, JPanel topButtonPanel ){
        MouseListener tpml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                Query query = session.createQuery("Select p from Product p where p.productTypeByProductType.productTypeId = :productType and p.productTypeByComplementOf is null").setParameter("productType",id);
                List<Product> listProductos = query.list();
                
                if(!listProductos.isEmpty()){
                    Iterator it = listProductos.iterator();
                    List<ButtonEntity> temp = new ArrayList<ButtonEntity>(listProductos.size());
                    while(it.hasNext()){
                        temp.add((ButtonEntity)it.next());
                    }
                    topButtonPanel.removeAll();
                    topButtonPanel.repaint();
                    addButtons(temp, topButtonPanel);
                    topButtonPanel.revalidate();
                    JLabel lblComplements = new JLabel("------ Complementos ------");
                    lblComplements.setFont(new Font("Cambria",1,48));
                    add(lblComplements);
                    JPanel bottomButtonPanel = new JPanel();
                    bottomButtonPanel.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER,20,20));
                    int areComplements = addComplements(id, bottomButtonPanel);
                    if(areComplements == 1)
                        add(bottomButtonPanel);
                    else
                        remove(lblComplements);
                }  
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
        return tpml;
    }
    
    private MouseListener productoMouseListener(Product p){
        MouseListener pml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                float precio = Util.getPrice(p.getProductId(), Customer.clientType.getClientTypeId());
                Object[] producto = {p.getName(),1,precio};
                DefaultTableModel tableModel = (DefaultTableModel)DynamicComponents.getTblCustomerProductoList().getModel();
                tableModel.addRow(producto);

                
                //If user has been changed, total price will be different.
                String textTotal = DynamicComponents.getTxtTotalPrice().getText();
                float altTotal = 0;
                if(textTotal.length() > 0){
                    textTotal = textTotal.substring(textTotal.length() - (textTotal.length() - 8));
                    textTotal = textTotal.replace(',', '.');
                    altTotal = Float.parseFloat(textTotal);
                    total = altTotal == total ? total + precio : altTotal + precio;
                }
                else
                    total = total + precio;

                String totalString = String.format("%.2f", total);
                DynamicComponents.getTxtTotalPrice().setText("Total" + "\t\t\t" + totalString);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
        return pml;
    }
    
    private int addComplements(int idTipoProducto, JPanel bottomButtonLayeredPane){
        Query query = session.createQuery("Select p from Product p where p.productTypeByComplementOf.productTypeId = :idTipoProducto").setParameter("idTipoProducto", idTipoProducto);
        List<ButtonEntity> listComplements = query.list();
        if(listComplements.size() > 0){
            addButtons(listComplements, bottomButtonLayeredPane);
            return 1;
        }
        return 0;
    }
}
