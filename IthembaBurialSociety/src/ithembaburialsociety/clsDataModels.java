/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
    * @author Oriana
 */
public class clsDataModels {
    private final clsDatabaseProcedures databaseProcedures = new clsDatabaseProcedures();
    
    public void mComboBoxData(String strQuery, JComboBox cbo) {
        Connection conMyConnection = databaseProcedures.mDatabaseConnection();
        try (Statement stStatement = conMyConnection.prepareStatement(strQuery); ResultSet rs = stStatement.executeQuery(strQuery)){
            while(rs.next()) {
                cbo.addItem(rs.getString(1));
            }
            
            conMyConnection.close();
            stStatement.close();
            rs.close();
        } catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public DefaultListModel mListModel(String[] strQueries) {
        DefaultListModel model = new DefaultListModel();
        try (Statement stStatement = databaseProcedures.mDatabaseConnection().prepareStatement(strQueries[0])){
            ResultSet rs = stStatement.executeQuery(strQueries[0]);
            ArrayList lst = new ArrayList();
            
            while(rs.next()) {
                lst.add(rs.getLong(1));
            }
            
            for(int i = 0; i < lst.size(); i++) {
                Long intCurrentValue = Long.parseLong(String.valueOf(lst.get(i)));
                
                model.addElement(databaseProcedures.mFieldValue(strQueries[1]+intCurrentValue) +" "+ databaseProcedures.mFieldValue(strQueries[2]+intCurrentValue));
            }
            
        } catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }
    
    public JTable mTable(String strQuery, JTable tblTable, DefaultTableModel model) {
        model = mTableModel(strQuery, model);
        tblTable.setModel(model);
        tblTable.setFillsViewportHeight(true);
        tblTable.validate();
        return tblTable;
    }
    
    public DefaultTableModel mTableModel(String strQuery, DefaultTableModel model) {
        Connection conMyConnection = databaseProcedures.mDatabaseConnection();
        try(Statement stStatement = conMyConnection.prepareStatement(strQuery)) {
            ResultSet rs = stStatement.executeQuery(strQuery);
            ResultSetMetaData rsmt = rs.getMetaData();
            int intColumnCount = rsmt.getColumnCount();
            
            for(int i = 1; i <= intColumnCount; i++) {
                model.addColumn(rsmt.getColumnName(i));
            }
            
            while(rs.next()){
                String[] arrRow = new String[intColumnCount + 1];
                for(int i = 1; i <= intColumnCount; i++) {
                    arrRow[i] = rs.getString(i);
                }
                model.addRow(mRemoveEmptyIndices(arrRow).toArray(
                        new String[mRemoveEmptyIndices(arrRow).size()]));
            }
            return model;
        } catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try{
                conMyConnection.close();
            } catch(SQLException | NullPointerException ex) {
            }
        }
        return model;
    }
    
    public List<String> mRemoveEmptyIndices(String[] arr) {
        List<String> values = new ArrayList<>();
        try{
            for(String element: arr) {
                if(element != null){
                    values.add(element);
                }
            }
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return values;
    }
}