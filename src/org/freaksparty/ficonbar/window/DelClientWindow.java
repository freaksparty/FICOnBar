/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freaksparty.ficonbar.window;

import FICOnBar.entity.Client;
import FICOnBar.entity.ClientType;
import java.util.List;
import javax.swing.JOptionPane;
import org.freaksparty.ficonbar.util.User;
import org.freaksparty.ficonbar.util.Util;
import org.hibernate.HibernateException;

/**
 *
 * @author Osane
 */
public class DelClientWindow extends javax.swing.JDialog {

    private List<Client> clients = null; 
    private Client clientToDelete = null;
    /**
     * Creates new form DelClientWindow
     */
    public DelClientWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initTypeCombo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblType = new javax.swing.JLabel();
        cmbType = new javax.swing.JComboBox<>();
        lblClient = new javax.swing.JLabel();
        cmbClient = new javax.swing.JComboBox<>();
        btnAcept = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblType.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        lblType.setText("Tipo");

        cmbType.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        cmbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTypeActionPerformed(evt);
            }
        });

        lblClient.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        lblClient.setText("Cliente");

        cmbClient.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        cmbClient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAcept.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        btnAcept.setText("Aceptar");
        btnAcept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        btnCancel.setText("Cerrar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAcept, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClient)
                            .addComponent(lblType))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblType)
                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClient)
                    .addComponent(cmbClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAcept, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptActionPerformed
        if(cmbType.getSelectedIndex() == 0)
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de cliente.","Warning",JOptionPane.WARNING_MESSAGE);
        if(cmbClient.getSelectedIndex() == 0)
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.","Warning",JOptionPane.WARNING_MESSAGE);
        int confirmDelte = JOptionPane.showInternalConfirmDialog(this, "Seguro que quiere eliminar este cliente?","Confirm",JOptionPane.YES_NO_OPTION);
        if(confirmDelte == JOptionPane.YES_OPTION){
            clientToDelete = clients.get(cmbClient.getSelectedIndex() - 1);
            try{
                Util.delClientFromDB(clientToDelete.getClientId());
                Util.registerLogToDB("Eliminador cliente " + clientToDelete.getName() + " con ID " + clientToDelete.getClientId(), User.user.getUserId());
                JOptionPane.showMessageDialog(this, "El cliente " + clientToDelete.getName() + " se ha eliminado correctamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                cleanFields();
            }catch(HibernateException hEx){
            Util.registerLogToDB(hEx.getCause().getMessage(), User.user.getUserId());
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error, contacte a un administrador.","Error",JOptionPane.ERROR_MESSAGE);
            this.dispose();
            }  
        }
    }//GEN-LAST:event_btnAceptActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void cmbTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTypeActionPerformed
        cmbClient.removeAllItems();
        cmbClient.addItem("-");
        if(cmbType.getSelectedIndex() != 0){
            clients = Util.getClientsByType(cmbType.getSelectedIndex());
            for(Client c:clients)
                cmbClient.addItem(c.getName());
        }
    }//GEN-LAST:event_cmbTypeActionPerformed

    private void initTypeCombo(){
        List<ClientType> clientTypes = Util.getClientTypeAll();
        cmbType.removeAllItems();
        cmbType.addItem("-");
        for(ClientType ct:clientTypes)
            cmbType.addItem(ct.getName());
    }
    
    private void cleanFields(){
        cmbType.setSelectedIndex(0);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DelClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DelClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DelClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DelClientWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DelClientWindow dialog = new DelClientWindow(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcept;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cmbClient;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JLabel lblClient;
    private javax.swing.JLabel lblType;
    // End of variables declaration//GEN-END:variables
}
