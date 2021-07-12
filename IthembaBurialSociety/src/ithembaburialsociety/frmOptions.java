/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithembaburialsociety;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.*;

/**
 *
 * @author Sanele
 */
public class frmOptions extends JDialog {
    
    public frmOptions(JFrame frmParent, String[] arrBtnText, ActionListener[] arrListeners) {
        super(frmParent, "Choose Option", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(300, 160);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mCreateGUI(arrBtnText, arrListeners);
        this.setVisible(true);
    }
        
    private void mCreateGUI(String[] arrBtnText, ActionListener[] arrListener) {
        JPanel jpPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        jpPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        jpPanel.setBackground(new Color(255, 255, 255));
        jpPanel.add(mCreateButton(150, 30, arrBtnText[0], arrListener[0]));
        jpPanel.add(mCreateButton(150, 30, arrBtnText[1], arrListener[1]));
        this.add(jpPanel);
    }
        
    private JButton mCreateButton(int intWidth, int intHeight,
            String strText, ActionListener listener) {
            
        JButton btnButton = new JButton(strText);
        btnButton.addActionListener(listener);
        btnButton.setPreferredSize(new Dimension(intWidth, intHeight));
            
        btnButton.setBackground(new Color(102,255,255));
        btnButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        btnButton.setText(strText);
            
        return btnButton;
    }
    
}
