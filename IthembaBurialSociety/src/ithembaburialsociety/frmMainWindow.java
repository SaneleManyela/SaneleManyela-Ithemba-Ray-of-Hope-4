/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Oriana
 */
public class frmMainWindow extends javax.swing.JFrame {

    /**
     * Creates new form frmMainWindow
     */
    public frmMainWindow() {
        initComponents();
        this.setTitle("Ithemba Burial Society: Application- Window");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
    private static String strUserRole;
    
    public String mGetUserRole() {
        return strUserRole;
    }
    
    public void mSetAccessControl(String strUserRole) {
        frmMainWindow.strUserRole = strUserRole;
        switch(frmMainWindow.strUserRole) {
            case "User/Client":
                mnuNewAccount.setVisible(false);
                break;
            case "Administrator":
                mnuNewAccount.setVisible(false);
                break;
            default:
                 btnCovers.setEnabled(false);
                 btnPrincipalMembers.setEnabled(false);
                 btnBeneficiaryMembers.setEnabled(false);
                 btnAccountManagement.setEnabled(false);
                break;
        }
    }
    
    private frmOptions frmOpt;
    
    private void mCovers() {
        frmOpt = new frmOptions(this, new String[] {
            "Manage Covers", "View Covers"
        }, new ActionListener[] {
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmCovers frmCovers = new frmCovers();
                    frmMainWindow.this.dsktpMain.add(frmCovers);
                    frmCovers.setVisible(true);
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmViewData frmViewCovers = new frmViewData("Covers");
                    frmMainWindow.this.dsktpMain.add(frmViewCovers);
                    frmViewCovers.setVisible(true);
                }
            }
        });
    }
    
    private void mPrincipalMembers() {
        frmOpt = new frmOptions(this, new String[] {
            "Pricipal Policies", "Principal Details"
        }, new ActionListener[]{
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmPrincipal frmPrincipalPolicy = new frmPrincipal(frmMainWindow.this);
                    frmMainWindow.this.dsktpMain.add(frmPrincipalPolicy);
                    frmPrincipalPolicy.setVisible(true);
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmViewData frmViewPrincipal = new frmViewData("Principal");
                    frmMainWindow.this.dsktpMain.add(frmViewPrincipal);
                    frmViewPrincipal.setVisible(true);
                }
            }
        });
    }
    
    private void mBeneficiaries() {
        frmOpt = new frmOptions(this, new String[] {
           "Manage Beneficiaries", "View Beneficiaries" 
        }, new ActionListener[] {
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmBeneficiary frmBeneficiary = new frmBeneficiary();
                    frmMainWindow.this.dsktpMain.add(frmBeneficiary);
                    frmBeneficiary.setVisible(true);
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmViewData frmViewBeneficiaries = new frmViewData("Beneficiaries");
                    frmMainWindow.this.dsktpMain.add(frmViewBeneficiaries);
                    frmViewBeneficiaries.setVisible(true);
                }
            }
        });
    }
    
    private void mAccountManagement() {
        frmOpt = new frmOptions(this, new String[]{
            "Update Account", "Delete Account"
        }, new ActionListener[] {
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmUserAccount frmAccount = new frmUserAccount("Update Account");
                    frmMainWindow.this.dsktpMain.add(frmAccount);
                    frmAccount.setVisible(true);
                }
            },
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frmUserAccount frmAccount = new frmUserAccount("Delete Account");
                    frmMainWindow.this.dsktpMain.add(frmAccount);
                    frmAccount.setVisible(true);
                }
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dsktpMain = new javax.swing.JDesktopPane();
        lblFrmMain = new javax.swing.JLabel();
        btnPrincipalMembers = new javax.swing.JButton();
        btnCovers = new javax.swing.JButton();
        btnBeneficiaryMembers = new javax.swing.JButton();
        btnAccountManagement = new javax.swing.JButton();
        mbMenuBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuItemLogout = new javax.swing.JMenuItem();
        mnuItemExit = new javax.swing.JMenuItem();
        mnuNewAccount = new javax.swing.JMenu();
        mnuItemAddAccount = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dsktpMain.setBackground(new java.awt.Color(255, 255, 255));

        lblFrmMain.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblFrmMain.setText("Ithemba - Ray of Hope");

        btnPrincipalMembers.setBackground(new java.awt.Color(102, 255, 255));
        btnPrincipalMembers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPrincipalMembers.setText("Principal Members");
        btnPrincipalMembers.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnPrincipalMembers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrincipalMembersActionPerformed(evt);
            }
        });

        btnCovers.setBackground(new java.awt.Color(102, 255, 255));
        btnCovers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCovers.setText("Covers");
        btnCovers.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnCovers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoversActionPerformed(evt);
            }
        });

        btnBeneficiaryMembers.setBackground(new java.awt.Color(102, 255, 255));
        btnBeneficiaryMembers.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBeneficiaryMembers.setText("Beneficiary Members");
        btnBeneficiaryMembers.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnBeneficiaryMembers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeneficiaryMembersActionPerformed(evt);
            }
        });

        btnAccountManagement.setBackground(new java.awt.Color(102, 255, 255));
        btnAccountManagement.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAccountManagement.setText("Account Management");
        btnAccountManagement.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAccountManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountManagementActionPerformed(evt);
            }
        });

        dsktpMain.setLayer(lblFrmMain, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpMain.setLayer(btnPrincipalMembers, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpMain.setLayer(btnCovers, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpMain.setLayer(btnBeneficiaryMembers, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dsktpMain.setLayer(btnAccountManagement, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dsktpMainLayout = new javax.swing.GroupLayout(dsktpMain);
        dsktpMain.setLayout(dsktpMainLayout);
        dsktpMainLayout.setHorizontalGroup(
            dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpMainLayout.createSequentialGroup()
                .addGroup(dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsktpMainLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addGroup(dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCovers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBeneficiaryMembers, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                        .addGap(400, 400, 400)
                        .addGroup(dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPrincipalMembers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAccountManagement, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
                    .addGroup(dsktpMainLayout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addComponent(lblFrmMain)))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        dsktpMainLayout.setVerticalGroup(
            dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsktpMainLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblFrmMain)
                .addGap(39, 39, 39)
                .addGroup(dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrincipalMembers, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCovers, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(dsktpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAccountManagement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBeneficiaryMembers, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );

        mnuFile.setText("File");

        mnuItemLogout.setText("Logout");
        mnuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemLogoutActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItemLogout);

        mnuItemExit.setText("Exit");
        mnuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItemExit);

        mbMenuBar.add(mnuFile);

        mnuNewAccount.setText("New Account");

        mnuItemAddAccount.setText("Add Account");
        mnuItemAddAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemAddAccountActionPerformed(evt);
            }
        });
        mnuNewAccount.add(mnuItemAddAccount);

        mbMenuBar.add(mnuNewAccount);

        setJMenuBar(mbMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpMain)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dsktpMain)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBeneficiaryMembersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeneficiaryMembersActionPerformed
        mBeneficiaries();
    }//GEN-LAST:event_btnBeneficiaryMembersActionPerformed

    private void btnCoversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoversActionPerformed
        if(strUserRole.equals("User/Client")) {
            frmViewData frmViewCovers = new frmViewData("Covers");
            this.dsktpMain.add(frmViewCovers);
            frmViewCovers.setVisible(true);
        } else {
            mCovers();
        }
    }//GEN-LAST:event_btnCoversActionPerformed

    private void btnPrincipalMembersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrincipalMembersActionPerformed
        mPrincipalMembers();
    }//GEN-LAST:event_btnPrincipalMembersActionPerformed

    private void btnAccountManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountManagementActionPerformed
        mAccountManagement();
    }//GEN-LAST:event_btnAccountManagementActionPerformed

    private void mnuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemLogoutActionPerformed
        frmLogin frmLogin = new frmLogin();
        frmLogin.mSetUserLoginID(0);
        frmLogin.mSetPrincipalIDNumber(0L);
        frmLogin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mnuItemLogoutActionPerformed

    private void mnuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuItemExitActionPerformed

    private void mnuItemAddAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemAddAccountActionPerformed
        frmUserAccount frmAccount = new frmUserAccount("Add Account");
        this.dsktpMain.add(frmAccount);
        frmAccount.setVisible(true);
    }//GEN-LAST:event_mnuItemAddAccountActionPerformed

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
            java.util.logging.Logger.getLogger(frmMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccountManagement;
    private javax.swing.JButton btnBeneficiaryMembers;
    private javax.swing.JButton btnCovers;
    private javax.swing.JButton btnPrincipalMembers;
    public javax.swing.JDesktopPane dsktpMain;
    private javax.swing.JLabel lblFrmMain;
    private javax.swing.JMenuBar mbMenuBar;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuItemAddAccount;
    private javax.swing.JMenuItem mnuItemExit;
    private javax.swing.JMenuItem mnuItemLogout;
    private javax.swing.JMenu mnuNewAccount;
    // End of variables declaration//GEN-END:variables
}
