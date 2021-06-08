/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class CobrarController implements Initializable {

    @FXML
    private Label entregado;
    @FXML
    private Label total;
    @FXML
    private Label adevolver;
    @FXML
    private Button imprimir;
    @FXML
    private Button aceptar;
    private ObservableList<CuentaMesa> ol;
    bd bd;
    private double efectivo = 0;
    private double tarjeta = 0;
    private double cantidadTotal;
    private String nombreMesa;
    private GestionMesasController controller;
    private double entregadoo;
    @FXML
    private ImageView imp;
    private int llaveFactura;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = new bd();
        
    }    

    @FXML
    private void imprimir(MouseEvent event) throws SQLException {
         ObservableList<CuentaMesa> listaTablaCuenta = FXCollections.observableArrayList();
         listaTablaCuenta = controller.getTablaCuenta();
        
                 GregorianCalendar gg = new GregorianCalendar();
        
        SimpleDateFormat ddd = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        
          Date date = new Date();
          

        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);

        System.out.println("SQL Date: " + date1);
        java.util.Date d = new java.util.Date();  
        //plantilla = new SimpleDateFormat("dd/MM/yyyy H:mm");
        java.sql.Date date2 = new java.sql.Date(d.getTime());

        
        Connection con = bd.getConnection();
        int numCaja = 0 ;
        int llavePrincipal=0;
        Statement st4;
        ResultSet rs4;
                try
                {
                    String caja = "SELECT MAX(id) FROM cierrecaja";
                    
                    st4 = con.createStatement();
            rs4 = st4.executeQuery(caja);
            Categoria categoria;
            
            while(rs4.next())
            {
                numCaja = rs4.getInt(1);
                          
            }
                }
                catch(Exception e)
                {
                    
                }
       if(numCaja!=0)
       {
           
       }
       else
       {
           
           Statement st;
           ResultSet rs;
           
            String query = "INSERT INTO `cierrecaja` (`id`, `f.inicio`,`f.fin`) VALUES (NULL, '"+ddd.format(gg.getTime())+"',NULL)";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            
            
             st = con.createStatement();
           st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            //st.executeUpdate(query);
            
            rs = st.getGeneratedKeys();
            
            
            
            if(rs.next())
            {
               llavePrincipal = rs.getInt(1);
            }
            System.out.println("llavePrincipal: "+llavePrincipal);
            numCaja = llavePrincipal;
            rs.close();
            st.close();
            
           
             
       }
       

        
       
         double truncate = +this.efectivo+this.tarjeta;
            System.out.println("llll"+entregadoo);    
         String query = "INSERT INTO `factura` (`id`,`efectivo`,`tarjeta`,`total`,`hora`,`fecha`,`idCierreCaja`,`entregado`,cancelado) VALUES (NULL,'"+this.efectivo+"','"+this.tarjeta+"','"+cantidadTotal+"', '"+ddd.format(gg.getTime())+"','"+date1+"','"+numCaja+"','"+this.entregadoo+"',0)";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
           // Connection con = bd.getConnection();
        
        
        Statement st;
       ResultSet rs;
       

       
            st = con.createStatement();
           st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            //st.executeUpdate(query);
            
            rs = st.getGeneratedKeys();
            
            
            
            if(rs.next())
            {
               llaveFactura = rs.getInt(1);
            }
            System.out.println("llave: "+llaveFactura);
            rs.close();
            st.close();
              Statement st2;
       ResultSet rs2;
        st2 = con.createStatement();
            if(llaveFactura!=0)
            {
            
              String query1 ="UPDATE cuentamesa SET idFactura = '"+llaveFactura+"' WHERE cuentamesa.nombreMesa='"+this.nombreMesa+"'and cuentamesa.idFactura IS NULL";
               st2.executeUpdate(query1);
               
            
            }
            st2.close();
            
            
                
 
                
        
       
       String query2 = "UPDATE mesa SET  estado = 0 WHERE mesa.nombre='"+nombreMesa+"'";
         executeQuery(query2);
          ClasesDeTicket cdt = new ClasesDeTicket();
         cdt.ticketPagado(listaTablaCuenta,cantidadTotal,llaveFactura);
       this.controller.showTable();
        Stage stage = (Stage) this.imp.getScene().getWindow();
        stage.close();

            executeQuery(query2);
    }

    @FXML
    private void aceptar(MouseEvent event) throws SQLException {
             GregorianCalendar gg = new GregorianCalendar();
        
        SimpleDateFormat ddd = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        
          Date date = new Date();
          

        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);

        System.out.println("SQL Date: " + date1);
        java.util.Date d = new java.util.Date();  
        //plantilla = new SimpleDateFormat("dd/MM/yyyy H:mm");
        java.sql.Date date2 = new java.sql.Date(d.getTime());

        
        Connection con = bd.getConnection();
        int numCaja = 0 ;
        int llavePrincipal=0;
        Statement st4;
        ResultSet rs4;
                try
                {
                    String caja = "SELECT MAX(id) FROM cierrecaja";
                    
                    st4 = con.createStatement();
            rs4 = st4.executeQuery(caja);
            Categoria categoria;
            
            while(rs4.next())
            {
                numCaja = rs4.getInt(1);
                          
            }
                }
                catch(Exception e)
                {
                    
                }
       if(numCaja!=0)
       {
           
       }
       else
       {
           
           Statement st;
           ResultSet rs;
           cantidadTotal = efectivo + tarjeta;
            String query = "INSERT INTO `cierrecaja` (`id`, `f.inicio`,`f.fin`) VALUES (NULL, '"+ddd.format(gg.getTime())+"',NULL)";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            
            
             st = con.createStatement();
           st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            //st.executeUpdate(query);
            
            rs = st.getGeneratedKeys();
            
            
            
            if(rs.next())
            {
               llavePrincipal = rs.getInt(1);
            }
            System.out.println("llavePrincipal: "+llavePrincipal);
            numCaja = llavePrincipal;
            rs.close();
            st.close();
            
           
             
       }
       
   
  

        
        int llave=0;
         double truncate = +this.efectivo+this.tarjeta;
          SimpleDateFormat ddd1 = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
                
         String query = "INSERT INTO `factura` (`id`,`efectivo`,`tarjeta`,`total`,`hora`,`fecha`,`idCierreCaja`,`entregado`,cancelado) VALUES (NULL,'"+this.efectivo+"','"+this.tarjeta+"','"+cantidadTotal+"', '"+ddd.format(gg.getTime())+"','"+date1+"','"+numCaja+"','"+this.entregadoo+"',0)";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
           // Connection con = bd.getConnection();
        
        
        Statement st;
       ResultSet rs;
       

       
            st = con.createStatement();
           st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            //st.executeUpdate(query);
            
            rs = st.getGeneratedKeys();
            
            
            
            if(rs.next())
            {
               llave = rs.getInt(1);
            }
            System.out.println("llave: "+llave);
            rs.close();
            st.close();
              Statement st2;
       ResultSet rs2;
        st2 = con.createStatement();
            if(llave!=0)
            {
            
              String query1 ="UPDATE cuentamesa SET idFactura = '"+llave+"' WHERE cuentamesa.nombreMesa='"+this.nombreMesa+"'and cuentamesa.idFactura IS NULL";
               st2.executeUpdate(query1);
               
            
            }
            st2.close();
            
            
                
 
                
        
       
       String query2 = "UPDATE mesa SET  estado = 0 WHERE mesa.nombre='"+nombreMesa+"'";
         executeQuery(query2);
       this.controller.showTable();
        Stage stage = (Stage) this.aceptar.getScene().getWindow();
        stage.close();
        
    }
    
    public void pagado(double efectivo, double tarjeta,double entregadoo, double totall,String nombreMesa)
    {
        if(entregadoo>totall)
        {
            efectivo = totall -tarjeta;
        }
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        entregado.setText("Entregado: "+entregadoo);
        total.setText("Total: "+ totall);
        adevolver.setText("A devolver: "+(entregadoo-totall));
        this.nombreMesa=nombreMesa;
        this.entregadoo = entregadoo;
        this.cantidadTotal = totall;
        
    }
    public void setController(GestionMesasController controller)
    {
        this.controller = controller;
    }
    
       private void executeQuery(String query)
    {
        Connection con = bd.getConnection();
        
        
        Statement st;
       
        
        try
        {
            st = con.createStatement();
            st.executeUpdate(query);
            
           
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
