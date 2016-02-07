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
public class UserException extends Exception {
    
    private String message;
    
    public UserException(){
        message = "Error with user";
    }
    
    public UserException(String message){
        this.message = message;
    }
    
}
