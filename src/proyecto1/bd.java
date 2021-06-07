/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author skatt
 */
public class bd {
    
    private static Connection conexion;
    
    private static final String driver = "com.mysql.jdbc.Driver";
     private static final String user = "root";
      private static final String pass = "";
       private static final String url = "jdbc:mysql://localhost:3306/pos_restaurante1";
       
       public bd()
       {
          
       }
       
       public Connection getConnection()
       {
            try
           {
               Class.forName(driver);
               
               conexion =  (Connection)DriverManager.getConnection(url,user,pass);
               
               if(conexion!=null)
               {
                   System.out.println("Fallo");
               }
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
            return conexion;
       }
    
    
    
}
