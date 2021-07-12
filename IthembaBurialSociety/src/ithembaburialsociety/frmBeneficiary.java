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
public class frmBeneficiary extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmBeneficiary
     */
    public frmBeneficiary() {
        initComponents();
        this.setResizable(false);
        this.setLocation(new Point(250, 35));
        this.setClosable(true);
        this.setTitle("Beneficiaries");
        
        switch(frmMain.mGetUserRole()) {
            case "Administrator":
                dataModels.mComboBoxData("SELECT IDNum FROM Principal_Members", cboPrincipalUnder);
                
                if(this.databaseProcedures.mCheckIfRecordExists("SELECT * FROM Beneficiaries")) {
                    this.lstBeneficiaries.setModel(this.dataModels.mListModel(new String[] {
                        "SELECT IDNum FROM Beneficiaries",
                        "SELECT FName FROM Beneficiaries WHERE IDNum =",
                        "SELECT LName FROM Beneficiaries WHERE IDNum="
                    }));
                }
                break;
                
            case "User/Client":
                dataModels.mComboBoxData("SELECT IDNum FROM Principal_Members WHERE Account ="+frmLogin.mGetUserLoginID(), cboPrincipalUnder);
                
                if(!databaseProcedures.mCheckIfRecordExists("SELECT * FROM Principal_Members WHERE Account="+frmLogin.mGetUserLoginID())) {
                    btnAdd.setEnabled(false);
                    btnEdit.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnClear.setVisible(true);
                }
                
                if(frmLogin.mGetPrincipalIDNumber() != 0L) {
                    if(this.databaseProcedures.mCheckIfRecordExists("SELECT * FROM Beneficiaries WHERE PrincipalID="+frmLogin.mGetPrincipalIDNumber())) {
                        this.lstBeneficiaries.setModel(this.dataModels.mListModel(new String[] {
                            "SELECT IDNum FROM Beneficiaries WHERE PrincipalID="+frmLogin.mGetPrincipalIDNumber(),
                            "SELECT FName FROM Beneficiaries WHERE IDNum =", "SELECT LName FROM Beneficiaries WHERE IDNum="
                        }));
                }
            }
                break;
        }        
        txtFirstName.requestFocusInWindow();
    }
    
    private final clsDataModels dataModels = new clsDataModels();
    private final clsDatabaseProcedures databaseProcedures = new clsDatabaseProcedures();
    private final clsValidate validate = new clsValidate();
    
    private final frmMainWindow frmMain = new frmMainWindow();
    private final frmLogin frmLogin = new frmLogin();
    
    private Long lngIDNum = 0L;
    
    private String[] mGetBeneficiaryDetails() {
        return new String[] {
            txtFirstName.getText().trim(), txtLastName.getText().trim(),
            txtIDNumber.getText().trim(), cboPrincipalUnder.getSelectedItem().toString(),
            txtRelationship.getText().trim()
        };
    }
    
    private void mSetBeneficiaryDetails(Long lngBeneficiaryID) {
        this.lngIDNum = lngBeneficiaryID;
        
        if(this.lngIDNum != 0L) {
            String[] arrBeneficiary = databaseProcedures.mFetchRecord(
                    "SELECT FName, LName, IDNum, PrincipalID, Relationship FROM Beneficiaries WHERE IDNum="+this.lngIDNum);
            txtFirstName.setText(arrBeneficiary[0]);
            txtLastName.setText(arrBeneficiary[1]);
            txtIDNumber.setText(arrBeneficiary[2]);
            cboPrincipalUnder.setSelectedItem(arrBeneficiary[3]);
            txtRelationship.setText(arrBeneficiary[4]);
        }
    }
    
    private String mCheckInput() {
        if(txtFirstName.getText().equals("")) {
            return "Specify first name!";
            
        } else if(txtLastName.getText().equals("")) {
            return "Specify last name!";
            
        } else if(txtIDNumber.getText().equals("")) {
            return "Specify ID Number";
            
        } else if(txtIDNumber.getText().length() != 13) {
            return "ID Number must be 13 digits";
            
        } else if(txtRelationship.getText().equals("")) {
            return "Specify relationship!";
        }
        return "";
    }
    
    private void mCreateBeneficiary() {
        if(mCheckInput().equals("")) {
            if(validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID field").equals("")) {
                if(databaseProcedures.mInsertRecord("INSERT INTO Beneficiaries (FName, LName, IDNum, PrincipalID, Relationship)"
                        + "VALUES('"+mGetBeneficiaryDetails()[0]+"','"+mGetBeneficiaryDetails()[1]+"','"+mGetBeneficiaryDetails()[2]+
                        "','"+mGetBeneficiaryDetails()[3]+"','"+mGetBeneficiaryDetails()[4]+"')")) {
                    JOptionPane.showMessageDialog(this, "Beneficiary created", "WMESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }else {
                JOptionPane.showMessageDialog(this, validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID field"), "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, mCheckInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mUpdateInitiator() {
        if(!lstBeneficiaries.isSelectionEmpty()) {
            mSetBeneficiaryDetails(databaseProcedures.mGetIDNumber("SELECT IDNum FROM Beneficiaries WHERE FName ='"+
                            lstBeneficiaries.getSelectedValue().substring(0, lstBeneficiaries.getSelectedValue().indexOf(" ")).trim()+
                            "' AND LName='"+lstBeneficiaries.getSelectedValue().substring(lstBeneficiaries.getSelectedValue().indexOf(" "), 
                                    lstBeneficiaries.getSelectedValue().length()).trim()+"'"));
        } else {
            JOptionPane.showMessageDialog(this, "Select in the list a beneficiary", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mUpdateFinaliser() {
        if(mCheckInput().equals("")) {
            if(validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID field").equals("")) {
                if(databaseProcedures.mUpdateRecord("UPDATE Beneficiaries SET FName='"+mGetBeneficiaryDetails()[0]+"', LName='"+mGetBeneficiaryDetails()[1]
                        +"', IDNum='"+mGetBeneficiaryDetails()[2]+"', PrincipalID='"+mGetBeneficiaryDetails()[3]+"', Relationship='"+mGetBeneficiaryDetails()[4]+
                        "' WHERE IDNum ="+this.lngIDNum)) {
                    JOptionPane.showMessageDialog(this, "Beneficiary registered.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID field"), "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, mCheckInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mBeneficiaryDeletion() {
        if(!lstBeneficiaries.isSelectionEmpty()) {
            mSetBeneficiaryDetails(databaseProcedures.mGetIDNumber("SELECT IDNum FROM Beneficiaries WHERE FName='"+
                            lstBeneficiaries.getSelectedValue().substring(0, lstBeneficiaries.getSelectedValue().indexOf(" ")).trim()+
                            "' AND LName='"+lstBeneficiaries.getSelectedValue().substring(lstBeneficiaries.getSelectedValue().indexOf(" "), 
                                    lstBeneficiaries.getSelectedValue().length()).trim()+"'"));
            
            if(lngIDNum != 0L) {
                
                if(databaseProcedures.mDeleteRecord("DELETE FROM Beneficiaries WHERE IDNum="+lngIDNum)) {
                    JOptionPane.showMessageDialog(this, "Beneficiary deleted successfully.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select in the list a beneficiary", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mClearTextFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtIDNumber.setText("");
        txtRelationship.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpBeneficiary = new javax.swing.JPanel();
        lblBeneficiaries = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblIDNumber = new javax.swing.JLabel();
        txtIDNumber = new javax.swing.JTextField();
        lblPrincipalUnder = new javax.swing.JLabel();
        cboPrincipalUnder = new javax.swing.JComboBox<>();
        lblRelationship = new javax.swing.JLabel();
        txtRelationship = new javax.swing.JTextField();
        spListPane = new javax.swing.JScrollPane();
        lstBeneficiaries = new javax.swing.JList<>();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        jpBeneficiary.setBackground(new java.awt.Color(255, 255, 255));

        lblBeneficiaries.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblBeneficiaries.setText("Beneficiary Management");

        lblFirstName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFirstName.setText("First Name");

        lblLastName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLastName.setText("Last Name");

        lblIDNumber.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIDNumber.setText("ID Number");

        lblPrincipalUnder.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPrincipalUnder.setText("Principal Under");

        lblRelationship.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblRelationship.setText("Relationship");

        spListPane.setViewportView(lstBeneficiaries);

        btnAdd.setBackground(new java.awt.Color(102, 255, 255));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(102, 255, 255));
        btnEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(102, 255, 255));
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(102, 255, 255));
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBeneficiaryLayout = new javax.swing.GroupLayout(jpBeneficiary);
        jpBeneficiary.setLayout(jpBeneficiaryLayout);
        jpBeneficiaryLayout.setHorizontalGroup(
            jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBeneficiaryLayout.createSequentialGroup()
                .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBeneficiaryLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpBeneficiaryLayout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(btnDelete)
                                .addGap(72, 72, 72)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpBeneficiaryLayout.createSequentialGroup()
                                .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFirstName)
                                    .addComponent(lblLastName)
                                    .addComponent(lblRelationship)
                                    .addComponent(lblIDNumber)
                                    .addComponent(lblPrincipalUnder))
                                .addGap(100, 100, 100)
                                .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFirstName)
                                    .addComponent(txtLastName)
                                    .addComponent(txtIDNumber)
                                    .addComponent(cboPrincipalUnder, 0, 100, Short.MAX_VALUE)
                                    .addComponent(txtRelationship))
                                .addGap(35, 35, 35)
                                .addComponent(spListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jpBeneficiaryLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(lblBeneficiaries)))
                .addGap(30, 30, 30))
        );
        jpBeneficiaryLayout.setVerticalGroup(
            jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBeneficiaryLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblBeneficiaries)
                .addGap(55, 55, 55)
                .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpBeneficiaryLayout.createSequentialGroup()
                        .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFirstName)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLastName)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIDNumber)
                            .addComponent(txtIDNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrincipalUnder)
                            .addComponent(cboPrincipalUnder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRelationship)
                            .addComponent(txtRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(60, 60, 60)
                .addGroup(jpBeneficiaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBeneficiary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBeneficiary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClearTextFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        mCreateBeneficiary();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if(btnEdit.getText().equals("Edit")) {
            mUpdateInitiator();
            if(mCheckInput().equals("")) {
                btnEdit.setText("Save");
            }
        } else if(btnEdit.getText().equals("Save")) {
            mUpdateFinaliser();
            btnEdit.setText("Edit");
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        mBeneficiaryDeletion();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JComboBox<String> cboPrincipalUnder;
    private javax.swing.JPanel jpBeneficiary;
    private javax.swing.JLabel lblBeneficiaries;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblIDNumber;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPrincipalUnder;
    private javax.swing.JLabel lblRelationship;
    private javax.swing.JList<String> lstBeneficiaries;
    private javax.swing.JScrollPane spListPane;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtIDNumber;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtRelationship;
    // End of variables declaration//GEN-END:variables
}
