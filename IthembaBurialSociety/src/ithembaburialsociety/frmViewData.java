/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.awt.Point;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sanele
 */
public class frmViewData extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmViewData
     * @param strViewData
     */
    public frmViewData(String strViewData) {
        initComponents();
        this.setClosable(true);
        this.setResizable(false);
        this.setLocation(new Point(250, 35));
        
        switch(strViewData) {
            case "Covers":
                this.setTitle("Policy Covers");
                lblView.setText("Policy Covers");
                tblTable = dataModels.mTable("SELECT ID, CoverAmount, Category, Premium FROM Covers", tblTable, dmModel);
                txtSearch.setVisible(false);
                btnSearch.setVisible(false);
                this.pack();
                break;
                
            case "Principal":
                switch(frmMain.mGetUserRole()) {
                    case "User/Client":
                        this.setTitle("Your Policy Details");
                        lblView.setText("Your Policy Details");
                        tblTable = dataModels.mTable(
                                "SELECT IDNum, FName, LName, DOB, Address, Tel, Email, CoverID FROM Principal_Members WHERE Account ="+frmLogin.mGetUserLoginID(),
                                tblTable, dmModel);
                        this.txtSearch.setVisible(false);
                        this.btnSearch.setVisible(false);
                        this.pack();
                        break;
                        
                    case "Administrator":
                        this.setTitle("Principal Members Policies");
                        this.lblView.setText("Principal Members Policies");
                        tblTable = dataModels.mTable("SELECT IDNum, FName, LName, DOB, Address, Tel, Email, CoverID, Account FROM Principal_Members", tblTable, dmModel);
                        txtSearch.setToolTipText("Search by first name and/or by last name.");
                        txtSearch.requestFocusInWindow();
                        break;
                }
                break;
                
            case "Beneficiaries":
                this.setTitle("Registered Beneficiaries");
                switch(frmMain.mGetUserRole()) {
                    case "User/Client":
                        tblTable = dataModels.mTable("SELECT IDNum, FName, LName, Relationship, PrincipalID FROM Beneficiaries WHERE PrincipalID="+
                                databaseProcedures.mGetIDNumber("SELECT IDNum FROM Principal_Members WHERE Account="+frmLogin.mGetUserLoginID()),
                                tblTable, dmModel);
                        txtSearch.setVisible(false);
                        btnSearch.setVisible(false);
                        this.lblView.setText("My Beneficiaries");
                        this.pack();
                        break;
                        
                    case "Administrator":
                        tblTable = dataModels.mTable("SELECT IDNum, FName, LName, Relationship, PrincipalID FROM Beneficiaries", tblTable, dmModel);
                        this.lblView.setText("All Registered Beneficiaries");
                        txtSearch.setToolTipText("Search by first name and/or by last name.");
                        txtSearch.requestFocusInWindow();
                        break;
                }
                break;
        }
        this.strViewData = strViewData;
    }

    private final clsDataModels dataModels = new clsDataModels();
    private final clsDatabaseProcedures databaseProcedures = new clsDatabaseProcedures();
    private DefaultTableModel dmModel = new DefaultTableModel();
    private final frmLogin frmLogin = new frmLogin();
    private final frmMainWindow frmMain = new frmMainWindow();
    String strViewData;    
    
    private void mSearchRecord() {
        switch(strViewData) {
            case "Principal":
                    
                if(txtSearch.getText().equals(" ")) {
                    
                    dmModel = new DefaultTableModel();
                    tblTable = dataModels.mTable("SELECT IDNum, FName, LName, DOB, Email, CoverID FROM Principal_Members WHERE FName LIKE '%"+txtSearch.getText().substring(0,
                        txtSearch.getText().indexOf(" ")).trim()+"%' AND LName LIKE '%"+txtSearch.getText().substring(txtSearch.getText().indexOf(" "), 
                        txtSearch.getText().length()).trim()+"%'", tblTable, dmModel);
                    
                } else {
                    
                    dmModel = new DefaultTableModel();
                    tblTable = dataModels.mTable("SELECT IDNum, FName, LName, DOB, Email, CoverID FROM Principal_Members WHERE FName LIKE '%"+txtSearch.getText().trim()+"%' OR LName LIKE '%"+txtSearch.getText().trim()+"%'", 
                            tblTable, dmModel);
                }
                break;
                    
            case "Beneficiaries":
                    
                if(txtSearch.getText().equals("")) {
                        
                    dmModel = new DefaultTableModel();
                    tblTable = dataModels.mTable("SELECT IDNum, FName, LName, Relationship, PrincipalID FROM Beneficiaries WHERE FName LIKE '%"+txtSearch.getText().substring(0,
                        txtSearch.getText().indexOf(" ")).trim()+"%' AND LName LIKE '%"+txtSearch.getText().substring(txtSearch.getText().indexOf(" "), 
                        txtSearch.getText().length()).trim()+"%'", tblTable, dmModel);
                        
                } else {
                        
                    dmModel = new DefaultTableModel();
                    tblTable = dataModels.mTable("SELECT IDNum, FName, LName, Relationship, PrincipalID FROM Beneficiaries WHERE FName LIKE '%"+txtSearch.getText().trim()+"%' OR LName LIKE '%"+txtSearch.getText().trim()+"%'", tblTable, dmModel);
                        
                }
                    
                break;
        }
        
    }
    
    private void mClearSearchFilter() {
        dmModel = new DefaultTableModel();
        
        switch(strViewData) {
            case "Principal":
                switch(frmMain.mGetUserRole()) {
                    case "User/Client":
                        tblTable = dataModels.mTable(
                                "SELECT IDNum, FName, LName, DOB, Address, Tel, Email, CoverID FROM Principal_Members WHERE Account ="+frmLogin.mGetUserLoginID(),
                                tblTable, dmModel);
                        break;
                        
                    case "Administrator":
                        tblTable = dataModels.mTable("SELECT IDNum, FName, LName, DOB, Address, Tel, Email, CoverID, Account FROM Principal_Members", tblTable, dmModel);
                        break;
                }
                break;
                
            case "Beneficiaries":
                switch(frmMain.mGetUserRole()) {
                    case "User/Client":
                        tblTable = dataModels.mTable("SELECT IDNum, FName, LName, Relationship, PrincipalID FROM Beneficiaries WHERE PrincipalID="+
                                databaseProcedures.mGetIDNumber("SELECT IDNum FROM Principal_Members WHERE Account="+frmLogin.mGetUserLoginID()),
                                tblTable, dmModel);
                        break;
                        
                    case "Administrator":
                        tblTable = dataModels.mTable("SELECT IDNum, FName, LName, Relationship, PrincipalID FROM Beneficiaries", tblTable, dmModel);
                        break;
                }
                break;
        }
        txtSearch.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpViewData = new javax.swing.JPanel();
        spTablePane = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();
        lblView = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();

        jpViewData.setBackground(new java.awt.Color(255, 255, 255));

        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        spTablePane.setViewportView(tblTable);

        lblView.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblView.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnSearch.setBackground(new java.awt.Color(102, 255, 255));
        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpViewDataLayout = new javax.swing.GroupLayout(jpViewData);
        jpViewData.setLayout(jpViewDataLayout);
        jpViewDataLayout.setHorizontalGroup(
            jpViewDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpViewDataLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpViewDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpViewDataLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpViewDataLayout.createSequentialGroup()
                        .addComponent(spTablePane)
                        .addGap(20, 20, 20))))
            .addGroup(jpViewDataLayout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(lblView, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 201, Short.MAX_VALUE))
        );
        jpViewDataLayout.setVerticalGroup(
            jpViewDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpViewDataLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblView, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jpViewDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpViewData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpViewData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if(btnSearch.getText().equals("Search")) {
            if(txtSearch.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Provide a search criterion", "WARNING", JOptionPane.WARNING_MESSAGE);
            } else {
                mSearchRecord();
                btnSearch.setText("Clear");
            }
        } else {
            mClearSearchFilter();
            btnSearch.setText("Search");
        }
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jpViewData;
    private javax.swing.JLabel lblView;
    private javax.swing.JScrollPane spTablePane;
    private javax.swing.JTable tblTable;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
