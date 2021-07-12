/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.awt.Point;
import javax.swing.JOptionPane;

/**
 *
 * @author Oriana
 */
public class frmCovers extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmCovers
     */
    public frmCovers() {
        initComponents();
        this.setLocation(new Point(370, 45));
        this.setClosable(true);
        this.setTitle("Add Covers");
        txtAmount.requestFocusInWindow();
    }
    
    private final clsDatabaseProcedures databaseProcedures = new clsDatabaseProcedures();
    private final clsValidate validate = new clsValidate();
    
    private void mAddCover() {
        try {
            if(txtAmount.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Specify cover amount!", "WARNING", JOptionPane.WARNING_MESSAGE);
                txtAmount.requestFocusInWindow();
                
            } else if(Integer.parseInt(txtAmount.getText()) < 15000) {
                JOptionPane.showMessageDialog(this, "Cover amount must not be less than R15000!", "WARNING", JOptionPane.WARNING_MESSAGE);
                txtAmount.requestFocusInWindow();
                
            } else if(txtCategory.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Specify cover category!", "WARNING", JOptionPane.WARNING_MESSAGE);
                txtCategory.requestFocusInWindow();
                
            } else if(txtPremium.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Specify the premium for the cover!", "WARNING", JOptionPane.WARNING_MESSAGE);
                txtPremium.requestFocusInWindow();
                
            }else if(Integer.parseInt(txtPremium.getText()) < 120) {
                JOptionPane.showMessageDialog(this, "Premium amount must not be less than R120!", "WARNING", JOptionPane.WARNING_MESSAGE);
                txtPremium.requestFocusInWindow();
                
            } else if(validate.mValidateDigitsField(txtAmount.getText().trim(), "Amount field").equals("")
                    && validate.mValidateDigitsField(txtPremium.getText().trim(), "Premium field").equals("")) {
                
                if(databaseProcedures.mCheckIfRecordExists("SELECT * FROM Covers WHERE CoverAmount ="+txtAmount.getText().trim()+" AND Premium ="+txtPremium.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Policy cover exists!", "WARNING", JOptionPane.WARNING_MESSAGE);
                    txtAmount.requestFocusInWindow();
                    
                } else if(databaseProcedures.mInsertRecord("INSERT INTO Covers (CoverAmount, Category, Premium) "
                        + "VALUES('"+txtAmount.getText().trim()+"','"+txtCategory.getText().trim()+"','"+txtPremium.getText().trim()+"')")) {
                    
                    JOptionPane.showMessageDialog(this, "Policy cover added", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                    txtAmount.setText("");
                    txtCategory.setText("");
                    txtPremium.setText("");
                }
                
            } else if(!validate.mValidateDigitsField(txtAmount.getText().trim(), "Amount field").equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateDigitsField(txtAmount.getText().trim(), "Amount field"), "WARNING", JOptionPane.WARNING_MESSAGE);
                txtAmount.requestFocusInWindow();
                
            } else if(validate.mValidateDigitsField(txtPremium.getText().trim(), "Premium field").equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateDigitsField(txtPremium.getText().trim(), "Premium field"), "WARNING", JOptionPane.WARNING_MESSAGE);
                txtPremium.requestFocusInWindow();
                
            }
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpCovers = new javax.swing.JPanel();
        lblCovers = new javax.swing.JLabel();
        lblCoverAmount = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblCategory = new javax.swing.JLabel();
        txtCategory = new javax.swing.JTextField();
        lblPremium = new javax.swing.JLabel();
        txtPremium = new javax.swing.JTextField();
        btnAddCover = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        jpCovers.setBackground(new java.awt.Color(255, 255, 255));

        lblCovers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCovers.setText("Covers");

        lblCoverAmount.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCoverAmount.setText("Amount");

        lblCategory.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCategory.setText("Category");

        lblPremium.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPremium.setText("Premium");

        btnAddCover.setBackground(new java.awt.Color(102, 255, 255));
        btnAddCover.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAddCover.setText("Add Cover");
        btnAddCover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCoverActionPerformed(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(102, 255, 255));
        btnClose.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCoversLayout = new javax.swing.GroupLayout(jpCovers);
        jpCovers.setLayout(jpCoversLayout);
        jpCoversLayout.setHorizontalGroup(
            jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCoversLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(lblCovers)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpCoversLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCoversLayout.createSequentialGroup()
                        .addGroup(jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCoverAmount)
                            .addComponent(lblCategory)
                            .addComponent(lblPremium))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(txtCategory)
                            .addComponent(txtPremium)))
                    .addGroup(jpCoversLayout.createSequentialGroup()
                        .addComponent(btnAddCover)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        jpCoversLayout.setVerticalGroup(
            jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCoversLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCoversLayout.createSequentialGroup()
                        .addComponent(lblCovers)
                        .addGap(57, 57, 57)
                        .addComponent(lblCoverAmount)))
                .addGap(45, 45, 45)
                .addGroup(jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategory)
                    .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPremium)
                    .addComponent(txtPremium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jpCoversLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCover)
                    .addComponent(btnClose))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpCovers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpCovers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddCoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCoverActionPerformed
        mAddCover();
    }//GEN-LAST:event_btnAddCoverActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCover;
    private javax.swing.JButton btnClose;
    private javax.swing.JPanel jpCovers;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblCoverAmount;
    private javax.swing.JLabel lblCovers;
    private javax.swing.JLabel lblPremium;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtPremium;
    // End of variables declaration//GEN-END:variables
}