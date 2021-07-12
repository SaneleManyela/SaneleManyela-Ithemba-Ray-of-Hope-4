/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.awt.Point;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Oriana
 */
public class frmPrincipal extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmPrincipal
     * @param frmMain
     */
    public frmPrincipal(frmMainWindow frmMain) {
        initComponents();
        this.setClosable(true);
        this.setResizable(true);
        this.setLocation(new Point(250, 10));
        this.setTitle("Principal Member Policy");
        this.dataModels.mComboBoxData("SELECT DISTINCT CoverAmount FROM Covers ORDER BY CoverAmount ASC", cboCover);
        this.dataModels.mComboBoxData("SELECT Premium FROM Covers ORDER BY Premium ASC", cboPremium);
        
        if(frmMain.mGetUserRole().equals("User/Client")) {
            if(this.databaseProcedures.mCheckIfRecordExists("SELECT * FROM Principal_Members WHERE Account="+frmLogin.mGetUserLoginID())) {
                this.lstPrincipal.setModel(this.dataModels.mListModel(new String[] {
                    "SELECT IDNum FROM Principal_Members WHERE Account="+frmLogin.mGetUserLoginID(),
                    "SELECT FName FROM Principal_Members WHERE IDNum =", "SELECT LName FROM Principal_Members WHERE IDNum="
                }));
            }
        } else {
            if(this.databaseProcedures.mCheckIfRecordExists("SELECT * FROM Principal_Members")) {
                this.lstPrincipal.setModel(this.dataModels.mListModel(new String[] {
                "SELECT IDNum FROM Principal_Members",
                "SELECT FName FROM Principal_Members WHERE IDNum =",
                "SELECT LName FROM Principal_Members WHERE IDNum="
                }));
            }
        }
        this.frmMain = frmMain;
        this.txtDateOfBirth.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        this.txtFirstName.requestFocusInWindow();
    }

    private final clsDataModels dataModels = new clsDataModels();
    private final clsDatabaseProcedures databaseProcedures = new clsDatabaseProcedures();
    private final clsValidate validate = new clsValidate();
    private final frmLogin frmLogin = new frmLogin();
    private final frmMainWindow frmMain;
    
    private Long lngIDNum = 0L;
    
    public void mSetAccountID(int intAcc) {
        mPolicyRegistrationProcedure(intAcc);
    }
    
    private String[] mGetPolicyDetails() {
        return new String[] {
          txtIDNumber.getText().trim(), txtFirstName.getText().trim(), 
            txtLastName.getText().trim(), txtDateOfBirth.getText().trim(),
            txtResAddress.getText().trim(), txtPhoneNo.getText().trim(),
            txtEmailAddress.getText().trim(), cboCover.getSelectedItem().toString(),
            cboPremium.getSelectedItem().toString()
        };
    }
    
    private void mSetPrincipalMemberDetails(Long lngIDNum) {
        this.lngIDNum = lngIDNum;
        if(this.lngIDNum != 0L) {
            
            String[] arrPrincipalDetails = databaseProcedures.mFetchRecord(
                    "SELECT FName, LName, IDNum, DOB, Address, Tel, Email, CoverID FROM "
                            + "Principal_Members WHERE IDNum="+this.lngIDNum);
            
            txtFirstName.setText(arrPrincipalDetails[0]);
            txtLastName.setText(arrPrincipalDetails[1]);
            txtIDNumber.setText(arrPrincipalDetails[2]);
            txtDateOfBirth.setText(arrPrincipalDetails[3]);
            txtResAddress.setText(arrPrincipalDetails[4]);
            txtPhoneNo.setText(arrPrincipalDetails[5]);
            txtEmailAddress.setText(arrPrincipalDetails[6]);
            cboCover.setSelectedItem(databaseProcedures.mFieldValue("SELECT CoverAmount FROM Covers WHERE ID ="+arrPrincipalDetails[7]));
            cboPremium.setSelectedItem(databaseProcedures.mFieldValue("SELECT Premium FROM Covers WHERE ID ="+arrPrincipalDetails[7]));
        }
    }
    
    private String mCheckInput() {
        if(txtFirstName.getText().equals("")) {
            return "Specify first name!";
            
        } else if(txtLastName.getText().equals("")) {
            return "Specify last name!";
            
        } else if(txtIDNumber.getText().equals("")) {
            return "Specify ID number!";
            
        } else if(txtIDNumber.getText().length() != 13) {
            return "ID number must be 13 digits!";
            
        }  else if(txtDateOfBirth.getText().equals("")) {
            return "Specify date of birth!";
            
        } else if(txtPhoneNo.getText().equals("")) {
            return "Specify phone number!";
            
        } else if(txtPhoneNo.getText().length() != 10) {
            return "Phone number must be 10 digits!";
            
        } else if(txtEmailAddress.getText().equals("")) {
            return "Specify email address!";
            
        } else if(txtResAddress.getText().equals("")) {
            return "Specify residential address!";   
        }
        return "";
    }
    
    private void mCreatePolicy(int intAcc) {
        if(mCheckInput().equals("")) {
            
            if(databaseProcedures.mCheckIfRecordExists("SELECT * FROM Principal_Members WHERE IDNum="+txtIDNumber.getText().trim())) {  
                JOptionPane.showMessageDialog(this, "This policy is already registered!", "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID No. field").equals("")
                    && validate.mValidateContactNumber(txtPhoneNo.getText()).equals("")
                    && validate.mValidateDate(txtDateOfBirth.getText()).equals("")
                    && validate.mValidateEmail(txtEmailAddress.getText()).equals("")) {
                
                try{
                    
                   if(Period.between((LocalDate.parse(txtDateOfBirth.getText().trim())),
                            LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))).getYears() < 18 
                        || Period.between((LocalDate.parse(txtDateOfBirth.getText().trim())),
                                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))).getYears() >= 65) {
            
                        JOptionPane.showMessageDialog(this, "A Principal Member age must be between 18 to 64 years!",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                    
                    } else if(databaseProcedures.mInsertRecord("INSERT INTO Principal_Members (IDNum, FName, LName, DOB, Address, Tel, Email, CoverID, Account)"
                            + "VALUES('"+mGetPolicyDetails()[0]+"','"+mGetPolicyDetails()[1]+"','"+mGetPolicyDetails()[2]
                            +"','"+mGetPolicyDetails()[3]+"','"+mGetPolicyDetails()[4]+"','"+mGetPolicyDetails()[5]
                            +"','"+mGetPolicyDetails()[6]+"','"+databaseProcedures.mFieldValue("SELECT ID FROM Covers WHERE CoverAmount="+mGetPolicyDetails()[7]+" AND Premium ="+mGetPolicyDetails()[8])+
                            "','"+intAcc+"')")) {
                        
                        JOptionPane.showMessageDialog(null, "Principal Member policy has been registered.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                        
                        if(JOptionPane.showConfirmDialog(this, "Register beneficiaries?", "Register Beneficiaries",
                                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)  {
                            this.dispose();
                            frmBeneficiary frmBeneficiary = new frmBeneficiary();
                            frmMain.dsktpMain.add(frmBeneficiary);
                            frmBeneficiary.setVisible(true);
                        }
                    }
                    
                } catch(DateTimeException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR!!", JOptionPane.ERROR_MESSAGE);
                }
                
            } else if(!validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID No. field").equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID No. field"), 
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                txtIDNumber.requestFocusInWindow();
                
            } else if(!validate.mValidateContactNumber(txtPhoneNo.getText()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateContactNumber(txtPhoneNo.getText()), 
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                txtPhoneNo.requestFocusInWindow();
                
            } else if(!validate.mValidateDate(txtDateOfBirth.getText()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateDate(txtDateOfBirth.getText()), 
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                txtDateOfBirth.requestFocusInWindow();
                
            } else if(!validate.mValidateEmail(txtEmailAddress.getText()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateEmail(txtEmailAddress.getText()), 
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                txtEmailAddress.requestFocusInWindow();
            }
            
        } else {
            JOptionPane.showMessageDialog(this, mCheckInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mPolicyRegistrationProcedure(int intAcc) {
        switch(databaseProcedures.mFieldValue("SELECT Role FROM Users_Login WHERE ID="+intAcc)) {
            case "User/Client":
                mCreatePolicy(intAcc);
                break;
                
            case "Administrator":
                if(mCheckInput().equals("")) {
                    if(JOptionPane.showConfirmDialog(this, "Add an login account for this member?",
                    "Add Login Account", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
                        frmUserAccount frmAccount = new frmUserAccount("Add Account");
                        frmAccount.mSetPrincipalPointer(this);
                        frmMain.dsktpMain.add(frmAccount);
                        frmAccount.setVisible(true);
                    } 
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Specify policy details!", "WARNING", JOptionPane.WARNING_MESSAGE); 
                }
                break;
        }
    }
    
    private void mUpdateInitiator() {
        switch(frmMain.mGetUserRole()) {
            case "Administrator":
                if(!lstPrincipal.isSelectionEmpty()) {
                    
                    mSetPrincipalMemberDetails(databaseProcedures.mGetIDNumber("SELECT IDNum FROM Principal_Members WHERE FName='"+
                            lstPrincipal.getSelectedValue().substring(0, lstPrincipal.getSelectedValue().indexOf(" ")).trim()+
                            "' AND LName='"+lstPrincipal.getSelectedValue().substring(lstPrincipal.getSelectedValue().indexOf(" "), lstPrincipal.getSelectedValue().length()).trim()+"'"));
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Select in the list a member!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            break;
            
            case "User/Client":
                if(frmLogin.mGetPrincipalIDNumber() != 0L) {
                    mSetPrincipalMemberDetails(frmLogin.mGetPrincipalIDNumber());
                } 
                break;
        }
    }
    
    private void mUpdateFinaliser() {
        if(mCheckInput().equals("")) {
            if(validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID field").equals("")
                    && validate.mValidateDate(txtDateOfBirth.getText()).equals("")
                    && validate.mValidateContactNumber(txtPhoneNo.getText().trim()).equals("")
                    && validate.mValidateEmail(txtEmailAddress.getText().trim()).equals("")) {
                
                if(databaseProcedures.mUpdateRecord("UPDATE Principal_Members SET IDNum="+mGetPolicyDetails()[0]+","
                        + "FName='"+mGetPolicyDetails()[1]+"', LName='"+mGetPolicyDetails()[2]+"', DOB='"+mGetPolicyDetails()[3]+
                        "', Address='"+mGetPolicyDetails()[4]+"', Tel='"+mGetPolicyDetails()[5]+"', Email='"+mGetPolicyDetails()[6]+
                        "', CoverID="+databaseProcedures.mFieldValue("SELECT ID FROM Covers WHERE CoverAmount="+mGetPolicyDetails()[7]+
                                " AND Premium ="+mGetPolicyDetails()[8])+" WHERE IDNum ="+this.lngIDNum)) {
                    
                    JOptionPane.showMessageDialog(this, "Update successful", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                    
                }
                
            } else if(!validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID field").equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateDigitsField(txtIDNumber.getText().trim(), "ID field"), "WARNING", JOptionPane.WARNING_MESSAGE);
                txtIDNumber.requestFocusInWindow();
                
            } else if(!validate.mValidateDate(txtDateOfBirth.getText()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateDate(txtDateOfBirth.getText()), "WARNING", JOptionPane.WARNING_MESSAGE);
                txtDateOfBirth.requestFocusInWindow();
                
            } else if(!validate.mValidateContactNumber(txtPhoneNo.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateContactNumber(txtPhoneNo.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
                txtEmailAddress.requestFocusInWindow();
                
            } else if(validate.mValidateEmail(txtEmailAddress.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateEmail(txtEmailAddress.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
                txtEmailAddress.requestFocusInWindow();
            }
        }else {
            JOptionPane.showMessageDialog(this, mCheckInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        } 
    }
    
    private void mDeletePolicy() {
        switch(frmMain.mGetUserRole()) {
            case "Administrator":
                if(!lstPrincipal.isSelectionEmpty()) {
                    
                    mSetPrincipalMemberDetails(databaseProcedures.mGetIDNumber("SELECT IDNum FROM Principal_Members WHERE FName='"+
                            lstPrincipal.getSelectedValue().substring(0, lstPrincipal.getSelectedValue().indexOf(" ")).trim()+
                            "' AND LName='"+lstPrincipal.getSelectedValue().substring(lstPrincipal.getSelectedValue().indexOf(" "),
                                    lstPrincipal.getSelectedValue().length()).trim()+"'"));
                    
                    if(databaseProcedures.mCheckIfRecordExists("SELECT PrincipalID FROM Beneficiaries WHERE PrincipalID ="+this.lngIDNum)) {
                        if(databaseProcedures.mDeleteRecord("DELETE FROM Beneficiaries WHERE PrincipalID ="+this.lngIDNum)
                                && databaseProcedures.mDeleteRecord("DELETE FROM Principal_Members WHERE IDNum ="+this.lngIDNum)) {
                            JOptionPane.showMessageDialog(this, "Principal Policy and related details deleted.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if(this.lngIDNum != 0L){
                        if(databaseProcedures.mDeleteRecord("DELETE FROM Principal_Members WHERE IDNum ="+this.lngIDNum)) {
                            JOptionPane.showMessageDialog(this, "Principal Policy deleted. ", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    
                    this.lstPrincipal.setModel(this.dataModels.mListModel(new String[] {
                        "SELECT IDNum FROM Principal_Members",
                        "SELECT FName FROM Principal_Members WHERE IDNum =",
                        "SELECT LName FROM Principal_Members WHERE IDNum="
                    }));
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Select in the list a member!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            break;
            
            case "User/Client":
                if(frmLogin.mGetPrincipalIDNumber() != 0L) {
                    if(databaseProcedures.mCheckIfRecordExists("SELECT PrincipalID FROM Beneficiaries WHERE PrincipalID="+this.frmLogin.mGetPrincipalIDNumber())) {
                        
                        if(databaseProcedures.mDeleteRecord("DELETE FROM Beneficiaries WHERE PrincipalID ="+frmLogin.mGetPrincipalIDNumber())
                                && databaseProcedures.mDeleteRecord("DELETE FROM Principal_Members WHERE IDNum ="+frmLogin.mGetPrincipalIDNumber())) {
                            
                            JOptionPane.showMessageDialog(this, "Principal Policy and related details deleted.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                            
                            this.lstPrincipal.setModel(this.dataModels.mListModel(new String[] {
                                "SELECT IDNum FROM Principal_Members WHERE Account="+frmLogin.mGetUserLoginID(),
                                "SELECT FName FROM Principal_Members WHERE IDNum =", "SELECT LName FROM Principal_Members WHERE IDNum="
                            }));
                        }
                    } else if(databaseProcedures.mDeleteRecord("DELETE FROM Principal_Members WHERE IDNum ="+this.frmLogin.mGetPrincipalIDNumber())) {
                        JOptionPane.showMessageDialog(this, "Principal Policy deleted.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                        this.lstPrincipal.setModel(this.dataModels.mListModel(new String[] {
                            "SELECT IDNum FROM Principal_Members WHERE Account="+frmLogin.mGetUserLoginID(),
                            "SELECT FName FROM Principal_Members WHERE IDNum =", "SELECT LName FROM Principal_Members WHERE IDNum="
                        }));
                    }
                } 
                break;
        }
    }
    
    private void mClearTextFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtIDNumber.setText("");
        txtDateOfBirth.setText("");
        txtPhoneNo.setText("");
        txtEmailAddress.setText("");
        txtResAddress.setText("");
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpPrincipal = new javax.swing.JPanel();
        lblPrincipalPolicyHeading = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblIdentityNumber = new javax.swing.JLabel();
        txtIDNumber = new javax.swing.JTextField();
        lblPhoneNo = new javax.swing.JLabel();
        txtPhoneNo = new javax.swing.JTextField();
        lblEmailAddress = new javax.swing.JLabel();
        txtEmailAddress = new javax.swing.JTextField();
        lblResAddress = new javax.swing.JLabel();
        spTextAreaPane = new javax.swing.JScrollPane();
        txtResAddress = new javax.swing.JTextArea();
        lblCover = new javax.swing.JLabel();
        cboCover = new javax.swing.JComboBox<>();
        lblPremium = new javax.swing.JLabel();
        cboPremium = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        spListPane = new javax.swing.JScrollPane();
        lstPrincipal = new javax.swing.JList<>();
        lblDateOfBirth = new javax.swing.JLabel();
        txtDateOfBirth = new javax.swing.JTextField();

        jpPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblPrincipalPolicyHeading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPrincipalPolicyHeading.setText("Principal Policy Registration");

        lblFirstName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFirstName.setText("First Name");

        lblLastName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLastName.setText("Last Name");

        lblIdentityNumber.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIdentityNumber.setText("ID Number");

        lblPhoneNo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPhoneNo.setText("Phone No");

        lblEmailAddress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblEmailAddress.setText("Email Address");

        lblResAddress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblResAddress.setText("Res. Address");

        txtResAddress.setColumns(20);
        txtResAddress.setLineWrap(true);
        txtResAddress.setRows(5);
        spTextAreaPane.setViewportView(txtResAddress);

        lblCover.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCover.setText("Cover");

        lblPremium.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPremium.setText("Premium");

        btnAdd.setBackground(new java.awt.Color(102, 255, 255));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(102, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
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

        spListPane.setViewportView(lstPrincipal);

        lblDateOfBirth.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDateOfBirth.setText("Date Of Birth");

        javax.swing.GroupLayout jpPrincipalLayout = new javax.swing.GroupLayout(jpPrincipal);
        jpPrincipal.setLayout(jpPrincipalLayout);
        jpPrincipalLayout.setHorizontalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDateOfBirth)
                    .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblResAddress)
                                        .addComponent(lblFirstName)
                                        .addComponent(lblLastName)
                                        .addComponent(lblIdentityNumber))
                                    .addComponent(lblPhoneNo))
                                .addComponent(lblEmailAddress))
                            .addComponent(lblCover))
                        .addComponent(lblPremium))
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtDateOfBirth)
                        .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtIDNumber, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtPhoneNo, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtEmailAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(spTextAreaPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(cboCover, javax.swing.GroupLayout.Alignment.TRAILING, 0, 135, Short.MAX_VALUE))
                    .addComponent(cboPremium, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnDelete)
                        .addGap(20, 20, 20)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(spListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPrincipalPolicyHeading)
                .addGap(228, 228, 228))
        );
        jpPrincipalLayout.setVerticalGroup(
            jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPrincipalPolicyHeading)
                .addGap(34, 34, 34)
                .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrincipalLayout.createSequentialGroup()
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFirstName)
                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLastName)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIdentityNumber)
                            .addComponent(txtIDNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDateOfBirth)
                            .addComponent(txtDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPhoneNo)
                            .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmailAddress)
                            .addComponent(txtEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblResAddress)
                            .addComponent(spTextAreaPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCover)
                            .addComponent(cboCover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPremium)
                            .addComponent(cboPremium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jpPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete)
                            .addComponent(btnClear)))
                    .addComponent(spListPane))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        this.mPolicyRegistrationProcedure(frmLogin.mGetUserLoginID());
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if(btnUpdate.getText().equals("Update")) {
            this.mUpdateInitiator();
            if(mCheckInput().equals("")) {
                btnUpdate.setText("Save..");
            }
        } else if(btnUpdate.getText().equals("Save..")) {
            this.mUpdateFinaliser();
            btnUpdate.setText("Update");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        this.mDeletePolicy();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        this.mClearTextFields();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboCover;
    private javax.swing.JComboBox<String> cboPremium;
    private javax.swing.JPanel jpPrincipal;
    private javax.swing.JLabel lblCover;
    private javax.swing.JLabel lblDateOfBirth;
    private javax.swing.JLabel lblEmailAddress;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblIdentityNumber;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPhoneNo;
    private javax.swing.JLabel lblPremium;
    private javax.swing.JLabel lblPrincipalPolicyHeading;
    private javax.swing.JLabel lblResAddress;
    private javax.swing.JList<String> lstPrincipal;
    private javax.swing.JScrollPane spListPane;
    private javax.swing.JScrollPane spTextAreaPane;
    private javax.swing.JTextField txtDateOfBirth;
    private javax.swing.JTextField txtEmailAddress;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtIDNumber;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPhoneNo;
    private javax.swing.JTextArea txtResAddress;
    // End of variables declaration//GEN-END:variables
}
