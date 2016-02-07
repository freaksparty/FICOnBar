/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freaksparty.ficonbar.util;

import FICOnBar.entity.Client;
import FICOnBar.entity.ClientType;
import FICOnBar.entity.Product;
import FICOnBar.entity.ProductType;
import FICOnBar.entity.Sale;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Osane
 */
public class Util {
    
    private static Session session = DBSession.getSession();
    
    public Util(){}
    
    public static float getPrice(int productId, int clientType){
        Query query = session.createQuery("Select p.amount from Price p where p.product.productId = :productId and p.clientType.clientTypeId = :clientType")
                .setParameter("productId", productId)
                .setParameter("clientType", clientType);
        float precio = (float)query.uniqueResult();
        
        return precio;
    }
      
    public static void registerLogToDB(String message, int userId){
        try{
            session.beginTransaction();
            Query query = session.createSQLQuery("Insert into App_log"
                                            + "(description,user_id) values (:message, :userid)")
                    .setParameter("message", message)
                    .setParameter("userid", userId);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch(HibernateException hEx){
           if(session.getTransaction() != null)
               session.getTransaction().rollback();
           throw(hEx);
       }
    }
    
    public static String decreaseAmount(float decreaseValue, String oldValue){
        String txtTotal = oldValue;
        txtTotal = txtTotal.substring(txtTotal.length() - (txtTotal.length() - 8));
        txtTotal = txtTotal.replace(',', '.');
        float currentAmount = Float.parseFloat(txtTotal);
        float newAmount = currentAmount - decreaseValue;
        
        return String.format("%.2f", newAmount);
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Sale queries">
    public static List<Sale> getSales(int productoId){
        Query query = session.createQuery("Select s.product "
                    + "from Sale s "
                    + " where s.product.productId = :productId"
                    + " and s.client.clientId = :clientId")
                .setParameter("productId", productoId)
                .setParameter("clientId", Customer.client.getClientId());
        return query.list();
    }
    
    public static void insertSales(float price, int productId) throws HibernateException{
        try{
        session.beginTransaction();
        Query insert = session.createSQLQuery("Insert into Sale"
                         + " (amount, total, product, client)"
                         + " values (:amount, :price, :productId,:clientId)")
                         .setParameter("amount", 1)
                         .setParameter("price", price)
                         .setParameter("productId", productId)
                         .setParameter("clientId", Customer.client.getClientId());
        insert.executeUpdate();
        session.getTransaction().commit();
        }catch(HibernateException hEx){
            if(session.getTransaction() != null)
                session.getTransaction().rollback();
            throw(hEx);
        }
    }
    
    public static void updateSale(float price, int productId) throws HibernateException{
        try{
            session.beginTransaction();
            Query update = session.createSQLQuery("Update Sale s "
                                 + "set amount = amount + 1, total = total + :price "
                                 + " where s.product = :productId"
                                 + " and s.client = :clientId")
                                 .setParameter("price", price)
                                 .setParameter("productId", productId)
                                 .setParameter("clientId", Customer.client.getClientId());
            update.executeUpdate();
            session.getTransaction().commit();
        }catch(HibernateException hEx){
           if(session.getTransaction() != null)
               session.getTransaction().rollback();
           throw(hEx);
       }
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Product queries">
    public static void addProductToDB(String name, String barcode, int productType, Integer complementOf){
        Query insertQuery = null;

        try{
            session.beginTransaction();
            insertQuery = session.createSQLQuery("insert into Product (name,barcode,product_Type,complementOf)"
                                                + " values (:name,:barcode,:productType,:complementOf)")
                                                .setParameter("name", name)
                                                .setParameter("barcode", barcode)
                                                .setParameter("productType", productType)
                                                .setParameter("complementOf", complementOf);
            insertQuery.executeUpdate();
            session.getTransaction().commit();
            registerLogToDB("Se ha añadido el producto " + name, User.user.getUserId());
        }catch(HibernateException hEx){
           if(session.getTransaction() != null)
               session.getTransaction().rollback();
           throw(hEx);
       }        
    }
    
    public static void updatePoductInDB(int productId, String name, String barcode, int type, int complementOf){
        
        Query update = null;
        
        try{
            session.beginTransaction();
            if(barcode.isEmpty() && complementOf == 0){
                update = session.createSQLQuery("update Product set "
                                                     + "name = :name, "
                                                     + "product_Type = :type "
                                                     + "where product_id = :productId")
                                                    .setParameter("productId", productId)
                                                    .setParameter("name", name)
                                                    .setParameter("type", type);         
            }
            else if(complementOf == 0){
                update = session.createSQLQuery("update Product set "
                                                 + "name = :name, "
                                                 + "barcode = :barcode, "
                                                 + "product_Type = :type "
                                                 + "where product_id = :productId")
                                                .setParameter("productId", productId)
                                                .setParameter("name", name)
                                                .setParameter("type", type)
                                                .setParameter("barcode", barcode);
            }
            else if(barcode.isEmpty()){
            update = session.createSQLQuery("update Product set "
                                                 + "name = :name, "
                                                 + "product_Type = :type, "
                                                 + "complementOf = :complementOf "
                                                 + "where product_id = :productId")
                                                .setParameter("productId", productId)
                                                .setParameter("name", name)
                                                .setParameter("type", type)
                                                .setParameter("complementOf", complementOf);       
            }
            else{
                update = session.createSQLQuery("update Product set "
                                                    + "name = :name, "
                                                    + "barcode = :barcode, "
                                                    + "product_Type = :type, "
                                                    + "complementOf = :complementOf "
                                                    + "where product_id = :productId")      
                                                   .setParameter("productId", productId)                        
                                                   .setParameter("name", name)
                                                   .setParameter("type", type)
                                                   .setParameter("barcode", barcode)
                                                   .setParameter("complementOf", complementOf);
            }
            update.executeUpdate();
            session.getTransaction().commit();
        }catch(HibernateException hEx){
            if(session.getTransaction() != null)
                session.getTransaction().rollback();
            throw(hEx);
        }
    }
    
    public static void delProductFromDB(int productId){
        Query deleteQuery = null;
        try{
            session.beginTransaction();
            deleteQuery = session.createSQLQuery("delete from Product "
                                                + "where product_id = :productId")
                                                .setParameter("productId", productId);
            deleteQuery.executeUpdate();
            session.getTransaction().commit();
            registerLogToDB("Se ha eliminado el producto " + productId, User.user.getUserId());
        }catch(HibernateException hEx){
           if(session.getTransaction() != null)
               session.getTransaction().rollback();
           throw(hEx);
       } 
    }
    
    public static List<Product> getProductByName(String pName){
        Query query  = session.createQuery("select p from Product p where p.name = :name").setParameter("name", pName);
        return query.list();
    }
    
    public static List<Product> getProductByType(int type){
        Query query = session.createQuery("select p "
                                        + "from Product p "
                                        + "where p.productTypeByProductType = :type")
                                        .setParameter("type", type);
        return query.list();
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Product type queries">
    public static List<ProductType> getProductTypeAll(){
        Query query = session.createQuery("select pt from ProductType pt order by pt.productTypeId");
        return query.list();
    }
    
    public static void addProductTypeToDB(String name){
        try{
            session.beginTransaction();
            Query insert = session.createSQLQuery("insert into product_type (name) values (:name)").setParameter("name", name);
            insert.executeUpdate();
            session.getTransaction().commit();
            
        }catch(HibernateException hEx){
           if(session.getTransaction() != null)
               session.getTransaction().rollback();
           throw(hEx);
       } 
    }
    
    public static void deleteProductTypeFromDB(String name){
        try{
            session.beginTransaction();
            Query delete = session.createSQLQuery("delete from product_type where name = :name").setParameter("name", name);
            delete.executeUpdate();
            session.getTransaction().commit();
            
        }catch(HibernateException hEx){
           if(session.getTransaction() != null)
               session.getTransaction().rollback();
           throw(hEx);
       } 
    }
//</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Client queries">
    public static void addClientToDB(String name, int type, String barcode){
        try{
            session.beginTransaction();
            Query insert = session.createSQLQuery("Insert into client (name,client_Type,barcode) "
                                                + "values (:name, :type, :barcode)")
                                                .setParameter("name", name)
                                                .setParameter("type", type)
                                                .setParameter("barcode", barcode);
            insert.executeUpdate();
            session.getTransaction().commit();
        }catch(HibernateException hEx){
            if(session.getTransaction() != null)
               session.getTransaction().rollback();
            throw(hEx);
       }
    }
    
    public static void delClientFromDB(int id){
        try{
            session.beginTransaction();
            Query delete = session.createSQLQuery("Delete from client where client_id = :id").setParameter("id", id);
            
            delete.executeUpdate();
            session.getTransaction().commit();
            
        }catch(HibernateException hEx){
            if(session.getTransaction() != null)
               session.getTransaction().rollback();
            throw(hEx);
       }
    }
    
    public static List<Client> getClientsByType(int type){
        try{
            Query query = session.createQuery("select c from client where c.clientType = :type").setParameter("type", type);
            return query.list();
            
        }catch(HibernateException hEx){
            throw(hEx);
       }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Client type queries">
    public static ClientType getClientTypeByName(String name){
        Query query  = session.createQuery("select ct from ClientType ct where ct.name = :name").setParameter("name", name);
        return (ClientType)query.uniqueResult();
    }

    public static List<ClientType> getClientTypeAll(){
        Query query = session.createQuery("select ct from ClientType ct");
        return query.list();
    }
    //</editor-fold>

    /*public static int getStock(int idProducto){
        Query query = session.createQuery("Select p.packStock from Product p where idProducto = :idProducto").setParameter("idProducto", idProducto);
        return (int)query.uniqueResult();
    }*/
    

    
   /* public static void updateStock(int idProducto, String op, int units){
        
        int newStock = -1;
        switch(op){
            case "+":
                newStock = getStock(idProducto) + units;
                break;
            case "-":
                newStock = getStock(idProducto) - units;
                break;                
        }
        
        session.beginTransaction();
        Query update = session.createSQLQuery("Update Product p "
                + "set packStock = :newStock "
                + "where idProducto = :idProducto")
                .setParameter("newStock", newStock)
                .setParameter("idProducto", idProducto);
    }*/
}
