/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freaksparty.ficonbar.exception;
/**
 *
 * @author Osane
 */
public class UserTypeException extends Exception{
    
    private String message;
    
    public UserTypeException(){
        message = "Error with user type";
    }
    
    public UserTypeException(String msg){
        message = msg;
    }
    
}
