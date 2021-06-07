/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class EstadisticasController implements Initializable {

    @FXML
    private ScrollPane scroll;
    bd bd;
    private ArrayList<Factura> alFactura;
      private ArrayList<CuentaMesa> cm;
     HashMap<String, Integer> numProductos;
      

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = new bd();
        
        Connection con = bd.getConnection();
          alFactura = new ArrayList<Factura>();
          cm = new ArrayList<CuentaMesa>();
          numProductos= new HashMap<String, Integer>();
          Statement st;
          ResultSet rs;
         String query = "SELECT * FROM cierrecaja WHERE id = (SELECT MAX(id) from cierrecaja)";
         int idcc=0;
              try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
           
            
            while(rs.next())
            {
               
               idcc = rs.getInt("id");
             
            }
            
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
              
        String query2 = "SELECT * FROM factura where idcierrecaja = '"+idcc+"'";      
        String query3=" SELECT  \n" +
            " C.id,\n" +
            " C.cantidad,\n" +
            " C.nombreProducto,\n" +
            " C.precioProducto,C.total,\n" +
            " C.idFactura,\n" +
            " C.nombreMesa\n" +
            " \n" +
            "FROM factura F\n" +
            "JOIN cuentamesa C\n" +
            "ON F.id = C.idFactura\n" +
            "where F.idcierrecaja = '"+idcc+"'"
                + "and C.idFactura<>0"; 
     
        
       
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query3);
            CuentaMesa cuentaMesa;
  
            while(rs.next())
                
            {
                int count = numProductos.containsKey(rs.getString("nombreProducto")) ? numProductos.get(rs.getString("nombreProducto")) : 0; 
                numProductos.put(rs.getString("nombreProducto"), count + 1);
                




              
//cuentaMesa = new CuentaMesa(rs.getInt("cantidad"),rs.getString("nombreProducto"),rs.getDouble("precioProducto"),rs.getDouble("total"));  
              //cm.add(cuentaMesa);
           /*   if(!numProductos.containsKey(rs.getString("nombreProducto")))
              {
                  numProductos.put(rs.getString("nombreProducto"), 1);
              }
              else
              {
                  //int count1 = numProductos.containsKey(rs.getString("nombreProducto")) ? numProductos.get(rs.getString("nombreProducto")) : 0;
                  Integer count = numProductos.get(rs.getString("nombreProducto"));
                   System.out.println("nummm"+count);
                  


                  if (count == null) 
                  { 
                      numProductos.put(rs.getString("nombreProducto"), 1);
                  } 
                  else
                  { 
                      numProductos.put(rs.getString("nombreProducto"),numProductos.get(count+1));

                  }


                  
              }
           */
            }
         
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
           System.out.println(numProductos);
          try
        {
            st = con.createStatement();
            rs = st.executeQuery(query2);
            
  
            while(rs.next())
                
            {   
              
                alFactura.add(new Factura(rs.getInt("id"),rs.getDouble("efectivo"),rs.getDouble("tarjeta"),rs.getDouble("total"),rs.getDouble("entregado"),rs.getString("fecha"),rs.getString("hora"),rs.getInt("cancelado"),rs.getInt("idcierrecaja")));
           
            }

           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
          String pn = "";
          String pro="";
          int num ;
         for (Map.Entry<String, Integer> entry : numProductos.entrySet()) {
    System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
    pro = entry.getKey();
    num = entry.getValue();
    pn = pn + pro +": "+num+"\n";
}
         //Chart a = new Chart();
         System.out.println(pn);
          Text text = new Text(pn);
          scroll.setContent(text);
    } 
    
    
    
}
