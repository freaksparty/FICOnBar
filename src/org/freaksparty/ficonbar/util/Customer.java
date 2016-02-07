/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freaksparty.ficonbar.util;

import FICOnBar.entity.Client;
import FICOnBar.entity.ClientType;

/**
 *
 * @author Osane
 */
public abstract class Customer {
    
    public static ClientType clientType;
    public static Client client;
    
    public static final int PARTICIPANT = Util.getClientTypeByName("Participante").getClientTypeId();
    
    public Customer(){};
    
    
}
