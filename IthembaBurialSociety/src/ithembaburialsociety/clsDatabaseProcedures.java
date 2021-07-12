/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Oriana
 */
public class clsDatabaseProcedures {
    public clsDatabaseProcedures(){
    }
    
    public Connection mDatabaseConnection() {
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/burial_society_db", "root", "password");
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public boolean mInsertRecord(String strQuery) {
        Connection conMyConnection = mDatabaseConnection();
        try (Statement stStatement = conMyConnection.prepareStatement(strQuery)){
            stStatement.execute(strQuery);
            stStatement.close();
            conMyConnection.close();
            return true;
        } catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                conMyConnection.close();
            } catch(SQLException | NullPointerException ex) {
            }
        }
        return false;
    }
        
    public boolean mUpdateRecord(String strQuery) {
        Connection conMyConnection = mDatabaseConnection();
        try (Statement stStatement = conMyConnection.prepareStatement(strQuery)){
            stStatement.executeUpdate(strQuery);
            stStatement.close();
            conMyConnection.close();
            return true;
            
        } catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try{
                conMyConnection.close();
            } catch(SQLException | NullPointerException ex) {
            }
        }
        return false;
    }
    
    public boolean mDeleteRecord(String strQuery) {
       Connection conMyConnection = mDatabaseConnection();
       try(Statement stStatement = conMyConnection.prepareStatement(strQuery)) {
           stStatement.execute(strQuery);
           stStatement.close();
           conMyConnection.close();
           return true;
       } catch(SQLException | NullPointerException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
       } finally {
           try {
               conMyConnection.close();
           } catch(SQLException | NullPointerException ex) {
           }
       }
       return false;
    }
    
    public String[] mFetchRecord(String strQuery) {
        String[] arrRecord = null;
        Connection conMyConnection = mDatabaseConnection();
        try(Statement stStatement = conMyConnection.prepareStatement(strQuery); 
                ResultSet rs = stStatement.executeQuery(strQuery)){
            ResultSetMetaData rsmt = rs.getMetaData();
            arrRecord = new String[rsmt.getColumnCount() + 1];
            
            while(rs.next()) {
                for(int i = 1; i < arrRecord.length; i++) {
                    arrRecord[i] = rs.getString(i);
                }
            }
            conMyConnection.close();
            stStatement.close();
            rs.close();
            
        }catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally{
            try{
                conMyConnection.close();
            } catch(SQLException | NullPointerException ex) {
            }
        }
        arrRecord = new clsDataModels().mRemoveEmptyIndices(arrRecord).toArray(
                new String[new clsDataModels().mRemoveEmptyIndices(arrRecord).size()]);
        return arrRecord;
    }
    
    public boolean mCheckIfRecordExists(String strQuery) {
        Connection conMyConnection = mDatabaseConnection();
        boolean boolExist = false;
        try (Statement stStatement = conMyConnection.prepareStatement(strQuery);
                ResultSet rs = stStatement.executeQuery(strQuery)){
            boolExist = rs.next();
            conMyConnection.close();
            stStatement.close();
            rs.close();
        } catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try{
                conMyConnection.close();
            } catch(SQLException | NullPointerException ex) {
            }
        }
        return boolExist;
    }
    
    public String mFieldValue(String strQuery) {
        Connection conMyConnection = mDatabaseConnection();
        try(Statement stStatement = mDatabaseConnection().prepareStatement(strQuery);
                ResultSet rs = stStatement.executeQuery(strQuery)) {
            while(rs.next()) {
                return rs.getString(1);
            }
            conMyConnection.close();
            stStatement.close();
            rs.close();
        } catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally{
            try {
                conMyConnection.close();
            } catch(SQLException | NullPointerException ex) {
            }
        }
        return "";
    }
    
    public Long mGetIDNumber(String strQuery) {
        Connection conMyConnection = mDatabaseConnection();
        try(Statement stStatement = conMyConnection.prepareStatement(strQuery);
                ResultSet rs = stStatement.executeQuery(strQuery)) {
            while(rs.next()) {
                return rs.getLong(1);
            }
            conMyConnection.close();
            stStatement.close();
            rs.close();
        }catch(SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try{
                conMyConnection.close();
            } catch(SQLException | NullPointerException ex) {
            }
        }
        return 0L;
    }
}