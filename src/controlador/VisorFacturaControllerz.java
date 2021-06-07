/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import proyecto1.bd;


/**
 * FXML Controller class
 *
 * @author skatt
 */
public class VisorFacturaControllerz implements Initializable {
 
     
     
    @FXML
    private AnchorPane ap;
    @FXML
    private ScrollPane vf;
    private String nombre;
    private String direccion;
    private String nif;
    private int idcc;
    private Date ccf;
    private Date cci;
    private double totalTarjeta;
    private double totalEfectivo;
    private int totalFacturas;
    
     GregorianCalendar gg = new GregorianCalendar();
     SimpleDateFormat dd = new SimpleDateFormat("dd/MM/YYYY");
      SimpleDateFormat ddd = new SimpleDateFormat("HH:mm");
   
 bd    bd = new bd();
    @FXML
    private Button im;
    @FXML
    private Button sa;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
       
            Connection con = bd.getConnection();
        String query = "SELECT * FROM empresa";
        String query2 = "SELECT * FROM cierrecaja WHERE id = (SELECT MAX(id) from cierrecaja)";
        
        
        
        Statement st;
        ResultSet rs;
      
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
           
            
            while(rs.next())
            {
               
                nombre = rs.getString("nombre");
                direccion = rs.getString("direccion");
                nif = rs.getString("nif");
           
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
         try
        {
            st = con.createStatement();
            rs = st.executeQuery(query2);
           
            
            while(rs.next())
            {
               
                idcc = rs.getInt("id");
                cci = rs.getDate("f.inicio");
                
             
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
         
         String query3 = "SELECT * FROM factura where idcierrecaja = '"+idcc+"'";
         double auxt=0;
         double auxe=0;
        
           try
        {
            st = con.createStatement();
            rs = st.executeQuery(query3);
           
            
            while(rs.next())
            {
               
                auxt = rs.getDouble("tarjeta");
                auxe = rs.getDouble("efectivo");
                totalEfectivo = totalEfectivo + auxe;
                totalTarjeta = totalTarjeta + auxt;
                totalFacturas ++;
             
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
        
       
      factura();  
    
    }
    
    public String factura()
    {
        String facturaGlobal="";
        if(cci!=null)
        {
        String Header =
                   "**********        "+nombre+"        ***********\n;"
                   // + "Date:"+dd.format(gg.getTime())+"       Time:"+ddd.format(gg.getTime())+"\n;"
                    + "         INFORME DE CAJA GLOBAL         \n;"
                    + "---------------------------------------\n;"
                    + "Fecha de inicio:             "+cci.toString()+"\n;"
                    + "Fecha de cierre:             "+dd.format(gg.getTime())+" "+ddd.format(gg.getTime())+"\n;"
                  
                    + "----------------------------------------\n;";
        
             //String a=jLabel4.getText()+"      "+jTextField1.getText()+"      "+jLabel2.getText()+"\n;";
             //String h=Header+a;
        String ant  =
                    "\n;---------------------------------------\n;"
                    + "Cobros registrados:   \n;"
                    + "Ejectivo:                   "+totalEfectivo+"€\n;"
                    + "Tarjeta:                    "+totalTarjeta+"€\n;"
                    + "----------------------------------------\n;"
                    + "Total:'"               +(totalEfectivo+totalTarjeta)+"€\n;"
                    + "----------------------------------------\n;"
                    + "Total facturas:      "+totalFacturas+"\n;"
                    + "----------------------------------------\n;"
                    + "        Software Developed by      \n ;"
                    + "           Skatt9 S.A                \n;"
                    + "***************************************\n;"
                    + "                                    \n;"
                    + "_______________________________________\n;";
        
        String zbill = Header + ant;
        facturaGlobal = zbill;
       String[] parts = zbill.split(";");  
           String aux ="";
        for(int i =0;i<parts.length;i++)
        {
            aux = aux +parts[i];
        }
        
     
        
      Font font = Font.font("Ubuntu Mono", FontPosture.REGULAR, 20);


//text.setEffect(new Glow(5.0));
//text.setFill(Color.GREEN);
//text.setFont(font); 

//avf.getChildren().add(text);


     
        
       
        Text text3 = new Text(aux);
        text3.setFont(font);
        
        
        vf.setContent(text3);
        }
        return facturaGlobal;
    }

    @FXML
    private void imprimirz(MouseEvent event) {
    GregorianCalendar gg = new GregorianCalendar();
    SimpleDateFormat ddd = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    Ticket1 t1 = new Ticket1();
        t1.printCard(factura());
    String query ="UPDATE cierrecaja SET `f.fin` = '"+ddd.format(gg.getTime())+"' WHERE cierrecaja.id='"+idcc+"'";
    executeQuery(query);
    String query1 = "INSERT INTO `cierrecaja` (`id`, `f.inicio`,`f.fin`) VALUES (NULL, '"+ddd.format(gg.getTime())+"',NULL)";
    executeQuery(query1);
     Stage stage = (Stage) this.im.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(MouseEvent event) {
    Stage stage = (Stage) this.sa.getScene().getWindow();
    stage.close();
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
