/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freaksparty.ficonbar.util;

import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Osane
 */
public class DynamicComponents {

    private static JTable tblCustomerProductoList;
    private static JTextField txtTotalPrice;
    
    public DynamicComponents(){}
    
    public static void setTblCustomerProductoList(JTable table){
        tblCustomerProductoList = table;
    }
    
    public static JTable getTblCustomerProductoList(){
        return tblCustomerProductoList;
    }
    
    public static void setTxtTotalPrice(JTextField text){
        txtTotalPrice = text;
    }
    
    public static JTextField getTxtTotalPrice(){
        return txtTotalPrice;
    }
    
}
