/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.awt.Point;
import javax.swing.*;

/**
 *
 * @author Oriana
 */
public class frmUserAccount extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmUserAccount
     * @param strUserAction
     */
    public frmUserAccount(String strUserAction) {
        initComponents();
        this.setResizable(false);
        this.setLocation(new Point(250, 35));
        this.setClosable(true);
        switch(strUserAction) {
            case "Add Account":
                this.setTitle("Login Account");
                txtUsername.requestFocusInWindow();
                break;
                
            case "Update Account":
                this.setTitle("Update Account");
                btnRegister.setText("Update");
                
                String[] arrUserDetails = databaseProcedures.mFetchRecord(
                "SELECT Username, Password FROM Users_Login WHERE ID ="+frmLogin.mGetUserLoginID());
                
                txtUsername.setText(arrUserDetails[0]);
                txtPassword.setText(arrUserDetails[1]);
                cboRole.setEnabled(false);
                break;
                
            case "Delete Account":
                this.setTitle("Delete Account");
                btnRegister.setText("Delete");
                
                String[] arrDetails = databaseProcedures.mFetchRecord(
                "SELECT Username, Password, Role FROM Users_Login WHERE ID ="+frmLogin.mGetUserLoginID());
                
                txtUsername.setText(arrDetails[0]);
                txtPassword.setText(arrDetails[1]);
                cboRole.setSelectedItem(arrDetails[2]);
                break;
        }
    }
    
    private final clsDatabaseProcedures databaseProcedures = new clsDatabaseProcedures();
    private final frmLogin frmLogin = new frmLogin();
    private frmPrincipal frmPrincipalRegistration;
    
    public void mSetPrincipalPointer(frmPrincipal principal) {
        this.frmPrincipalRegistration = principal;
    }
    
    private void mAddAccount() {
        if(txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Username!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocusInWindow();
            
        } else if(txtPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Password!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocusInWindow();
            
        } else if(databaseProcedures.mCheckIfRecordExists("SELECT Username FROM Users_Login WHERE Username ='"+txtUsername.getText().trim()+"'")) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "WARNING", JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocusInWindow();
            
        } else if(databaseProcedures.mInsertRecord("INSERT INTO Users_Login(Username, Password, Role)"
                + "VALUES('"+txtUsername.getText().trim()+"','"+txtPassword.getText().trim()+"','"+cboRole.getSelectedItem().toString()+"')")) {
            
            if(frmLogin.mGetUserLoginID() == 0) {
                JOptionPane.showMessageDialog(this, "Account has been created.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
            
            } else {
                JOptionPane.showMessageDialog(this, "Principal Account registered", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                this.frmPrincipalRegistration.mSetAccountID(
                        Integer.parseInt(databaseProcedures.mFieldValue("SELECT ID FROM Users_Login ORDER BY ID DESC LIMIT 1")));
            }
        }
    }
    
    private void mUpdateLoginAccount() {
        if(txtUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Username!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocusInWindow();
            
        } else if(txtPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Enter Password!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocusInWindow();
            
        } else if(!databaseProcedures.mFieldValue("SELECT Username FROM Users_Login WHERE ID ='"+frmLogin.mGetUserLoginID()+"'").equals(txtUsername.getText().trim())
                && databaseProcedures.mCheckIfRecordExists("SELECT Username FROM Users_Login WHERE "
                        + "Username ='"+txtUsername.getText().trim()+"'")) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "WARNING", JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocusInWindow();
            
        } else if(databaseProcedures.mUpdateRecord("UPDATE Users_Login SET Username ='"+txtUsername.getText().trim()
                +"', Password ='"+txtPassword.getText()+"' WHERE ID="+frmLogin.mGetUserLoginID())) {
            JOptionPane.showMessageDialog(this, "Update successful", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void mDeleteAccount() {
        if(databaseProcedures.mDeleteRecord("DELETE FROM Users_Login WHERE ID ="+frmLogin.mGetUserLoginID())) {
           
           if(databaseProcedures.mFieldValue("SELECT Role FROM Users_Login WHERE ID="+frmLogin.mGetUserLoginID()).equals("User/Client")) {
               databaseProcedures.mDeleteRecord("DELETE FROM Beneficiaries WHERE PrincipalID="+
                       databaseProcedures.mFieldValue("SELECT IDNum FROM Principal_Members WHERE Account ="+frmLogin.mGetUserLoginID()));
               
               databaseProcedures.mDeleteRecord("DELETE FROM Principal_Members WHERE IDNum="+
                       databaseProcedures.mFieldValue("SELECT IDNum FROM Principal_Members WHERE Account ="+frmLogin.mGetUserLoginID()));       
            } 
            JOptionPane.showMessageDialog(this, "Login Account Deleted.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            this.getTopLevelAncestor().setVisible(false);
            frmLogin.mSetUserLoginID(0);
            frmLogin.mSetPrincipalIDNumber(0L);
            frmLogin.setVisible(true);
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

        jpUserAccountPanel = new javax.swing.JPanel();
        lblUserAccount = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        lblRole = new javax.swing.JLabel();
        cboRole = new javax.swing.JComboBox<>();
        btnRegister = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        jpUserAccountPanel.setBackground(new java.awt.Color(255, 255, 255));

        lblUserAccount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUserAccount.setText("User Account");

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsername.setText("Username");

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPassword.setText("Password");

        lblRole.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblRole.setText("Role");

        cboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User/Client", "Administrator" }));

        btnRegister.setBackground(new java.awt.Color(102, 255, 255));
        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(102, 255, 255));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpUserAccountPanelLayout = new javax.swing.GroupLayout(jpUserAccountPanel);
        jpUserAccountPanel.setLayout(jpUserAccountPanelLayout);
        jpUserAccountPanelLayout.setHorizontalGroup(
            jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUserAccountPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpUserAccountPanelLayout.createSequentialGroup()
                        .addGroup(jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPassword)
                            .addComponent(lblRole)
                            .addComponent(lblUsername))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword)
                            .addComponent(cboRole, 0, 110, Short.MAX_VALUE)))
                    .addGroup(jpUserAccountPanelLayout.createSequentialGroup()
                        .addComponent(btnRegister)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
            .addGroup(jpUserAccountPanelLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(lblUserAccount)
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jpUserAccountPanelLayout.setVerticalGroup(
            jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUserAccountPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblUserAccount)
                .addGap(28, 28, 28)
                .addGroup(jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername))
                .addGap(40, 40, 40)
                .addGroup(jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRole)
                    .addComponent(cboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jpUserAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(btnClose))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpUserAccountPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpUserAccountPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        if(btnRegister.getText().equals("Register")) {
            mAddAccount();
        } else if(btnRegister.getText().equals("Update")) {
            mUpdateLoginAccount();
        } else if(btnRegister.getText().equals("Delete")) {
            mDeleteAccount();
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRegister;
    private javax.swing.JComboBox<String> cboRole;
    private javax.swing.JPanel jpUserAccountPanel;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblUserAccount;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
