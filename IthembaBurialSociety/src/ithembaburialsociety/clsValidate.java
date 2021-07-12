/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.util.regex.*;

/**
 *
 * @author Oriana
 */
public class clsValidate {
    public clsValidate() {
    }
    
    public String mValidateContactNumber(String strContactNo) {
        Pattern ptnContactNo = Pattern.compile("(0)?[0-9]{9}");
        Matcher mtcContactNo = ptnContactNo.matcher(strContactNo);
        if(!mtcContactNo.matches()) {
            return "Provided contact number is invalid!";
        }
        return "";
    }
    
    public String mValidateDate(String strDate) {
        Pattern ptnDate = Pattern.compile(
                "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$");
        Matcher mtcDate = ptnDate.matcher(strDate);
        if(!mtcDate.matches()) {
            return "Date format is invalid!";
        }
        return "";
    }
    
    public String mValidateDigitsField(String strField, String strFieldName){
        for(char c : strField.toCharArray()) {
            if(c != '.' && !Character.isDigit(c)) {
                return strFieldName+" contains non-digits! Enter digits only.";
            }
        }
        return "";
    }
    
    public String mValidateEmail(String strEmail) {
        Pattern ptnEmail = Pattern.compile("^(.+)@(.+)$");
        Matcher mtcEmail = ptnEmail.matcher(strEmail);
        if(!mtcEmail.matches()) {
            return "Email address is invalid!";
        }
        return "";
    }
}