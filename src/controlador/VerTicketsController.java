/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class VerTicketsController implements Initializable {

    @FXML
    private ScrollPane vf;
      bd bd;
      private ArrayList<Factura> alFactura;
      private ArrayList<CuentaMesa> cm;
       private ArrayList<CuentaMesa> auxCm;
      private String nombre;
      private String direccion;
      private String nif;
    @FXML
    private Button atras;
    @FXML
    private Button siguiente;
    private Button reavrir;
    @FXML
    private Button imprimir;
    @FXML
    private Button salir;
    private static int numFactura =0;
    
      private   String unidades;
      private  String nomPro;
      private  String cantidad;
      private  String aux ;
      private  String mesa ;
    @FXML
    private Button reabrir;
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = new bd();
        Connection con = bd.getConnection();
            String query = "SELECT * FROM empresa";

        Statement st;
        ResultSet rs;
        numFactura =0;
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
        
        
        alFactura = new ArrayList<Factura>();
        cm = new ArrayList<CuentaMesa>();
            
       
      
     
         String query2 = "SELECT * FROM cierrecaja WHERE id = (SELECT MAX(id) from cierrecaja)";
         int idcc=0;
              try
        {
            st = con.createStatement();
            rs = st.executeQuery(query2);
           
            
            while(rs.next())
            {
               
               idcc = rs.getInt("id");
             
            }
            
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
         
         
         
         String query3 = "SELECT * FROM factura where idcierrecaja = '"+idcc+"' and cancelado = 0";
         
            String query4=" SELECT  \n" +
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
            "where F.idcierrecaja = '"+idcc+"'";
         double auxt=0;
         double auxe=0;
           try
        {
            st = con.createStatement();
            rs = st.executeQuery(query3);
           
            
            while(rs.next())
            {
               
                alFactura.add(new Factura(rs.getInt("id"),rs.getDouble("efectivo"),rs.getDouble("tarjeta"),rs.getDouble("total"),rs.getDouble("entregado"),rs.getString("fecha"),rs.getString("hora"),rs.getInt("cancelado"),rs.getInt("idcierrecaja")));
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
           try
        {
            st = con.createStatement();
            rs = st.executeQuery(query4);
           
            
            while(rs.next())
            {
               
                //alFactura.add(new Factura(rs.getInt("id"),rs.getDouble("efectivo"),rs.getDouble("tarjeta"),rs.getDouble("total"),rs.getDouble("entregado"),rs.getString("fecha"),rs.getString("hora")));
                cm.add(new CuentaMesa(rs.getInt("id"),rs.getInt("cantidad"),rs.getString("nombreProducto"),rs.getDouble("precioProducto"),rs.getDouble("total"),rs.getInt("idFactura"),rs.getString("nombremesa")));
            }
            rs.close();
            st.close();
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
  
        
         //visualizar();  
         if(!alFactura.isEmpty())
         factura(alFactura,cm);
           
        
    } 
    
    public void visualizar()
    {
        String unidades ="";
        String nomPro = "";
        String cantidad ="";
        String aux ="";
          for(int i = 0;i<cm.size();i++)
       
        {
            if(alFactura.get(alFactura.size()-1+numFactura).getId()==cm.get(i).getIdFactura())
            {
            unidades = String.valueOf(cm.get(i).getUds());
        
            nomPro = cm.get(i).getNombre();
         
            
            cantidad = String.valueOf(cm.get(i).getPrecio())+"€";
        
            
            aux = aux +rightpad(unidades,10)+rightpad(nomPro,10)+rightpad(cantidad,5)+"\n;";
            }
        }
    
      
       String Header =
                   "*****          '"+this.nombre+"'         ******\n;"
                  + "------------------------------------\n;"
                   + "'"+this.direccion+"'              \n;"
                     + "----------------------------------\n;"
                    + "          Factura simplificada           \n;"
                    + "------------------------------------\n;"
                     + "'"+alFactura.get(alFactura.size()-1+numFactura).getFecha()+"'  '"+alFactura.get(alFactura.size()-1+numFactura).getHora()+"'\n"
                         + "---------------------------------\n;"
                         + "                                 \n;"
                    + "Descripcion de productos:              \n;"
                    + "Uds.    Nombre    Precio\n;"
                  
                    + "---------------------------------------\n;";
                    String a=aux+";";
                    String h=Header+a;
             //String a=jLabel4.getText()+"      "+jTextField1.getText()+"      "+jLabel2.getText()+"\n;";
             //String h=Header+a;
        String ant  =
                    "\n;----------------------------------------\n;"
                    + "Total entregado:               "+alFactura.get(alFactura.size()-1+numFactura).getEntregado()+"\n;"
                    + "Total cantidad:               " +alFactura.get(alFactura.size()-1+numFactura).getTotal()+"€\n;"
                    + "------------------------------------------\n;"
                     + "-----------------------------------------\n;"
                    + "       Software Developed by      \n ;"
                    + "        Skatt9 S.A                \n;"
                    + "*******************************************\n;"
                    + "         Gracias por su visita             \n;"
                    + "____________________________________________\n;";
        
        String zbill = h + ant;
       String[] parts = zbill.split(";");  
        String aux2 ="";
        for(int i =0;i<parts.length;i++)
        {
            aux2 = aux2 +parts[i];
        }

      Font font = Font.font("Ubuntu Mono", FontPosture.REGULAR, 20);

        Text text3 = new Text(aux2);
        text3.setFont(font);
        
        
        vf.setContent(text3);
    }
    
    
    public static String stringStatico(String string, int length) {
        return String.format("%1$"+length+ "s", string);
        }
     public static String rightpad(String text, int length) {
    return String.format("%-" + length + "." + length + "s", text);
}

    @FXML
    private void atras(MouseEvent event) {
        numFactura = numFactura -1;
        System.out.println("numFactura:"+numFactura);
         
        int auxint  = -numFactura;
        System.out.println("numFactura:"+auxint);
        System.out.println("tamaño array"+(alFactura.size()));
       if(auxint<alFactura.size())
       {
        factura(alFactura,cm);
       }
       else
       {
           numFactura = alFactura.size()-1;
            numFactura = -numFactura;
       }
        
    }

    @FXML
    private void siguiente(MouseEvent event) {
        
         numFactura = numFactura +1;
         if(numFactura<=0)
         {
          System.out.println(numFactura);
         }
         else
         {
             numFactura = 0;
         }
          
          factura(alFactura,cm);
          
          
    }

    @FXML
    private void reavrir(MouseEvent event) throws IOException {
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/reabrir.fxml"));
            
            Pane root = loader.load();
         ReabrirController controlador = (ReabrirController)loader.getController();
            controlador.setData(this);
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
             stage.initStyle( StageStyle.TRANSPARENT );
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
              stage.show();
          
     
    }

    @FXML
    private void imprimir(MouseEvent event) {
       
        Ticket1 t1 = new Ticket1();
        t1.printCard(factura(alFactura,cm));
    }

    @FXML
    private void salir(MouseEvent event) {
         Stage stage2 = (Stage) this.salir.getScene().getWindow();
            stage2.close();
    }
    
    
    public String factura(ArrayList<Factura> fac,ArrayList<CuentaMesa> cuenta)
    {
        unidades ="";
        nomPro = "";
        cantidad ="";
        aux ="";
        mesa ="";
       auxCm = auxCm = new ArrayList<CuentaMesa>();
       
          for(int i = 0;i<cuenta.size();i++)
       
        {
            if(fac.get(fac.size()-1+numFactura).getCancelado()!=1)
            {
                System.out.println("array:"+numFactura);
            if(fac.get(fac.size()-1+numFactura).getId()==cuenta.get(i).getIdFactura())
            {       
                   System.out.println("Total proddd"+cuenta.get(i).getTotal());
            unidades = String.valueOf(cuenta.get(i).getUds());
        
            nomPro = cuenta.get(i).getNombre();
            mesa = cuenta.get(i).getNombreMesa();
            
            cantidad = String.valueOf(cuenta.get(i).getPrecio())+"€";
               auxCm.add(new CuentaMesa(cuenta.get(i).getId(),cuenta.get(i).getUds(),cuenta.get(i).getNombre(),cuenta.get(i).getPrecio(),cuenta.get(i).getTotal(),cuenta.get(i).getIdFactura(),cuenta.get(i).getNombreMesa()));
            
            aux = aux +stringStatico(unidades,2)+stringStatico(nomPro,12)+stringStatico(cantidad,19)+"\n;";
                
                }
            }
            else
            {
                
            }
        }
    
      
       String Header =
                   "******         "+this.nombre+"        *******\n;"
                  + "-----------------------------------\n;"
                   + ""+this.direccion+"             \n;"
                     + "-----------------------------------\n;"
                    + "          Factura simplificada           \n;"
                    + "-----------------------------------\n;"
                     + "Numero:                       "+alFactura.get(alFactura.size()-1+numFactura).getId()+"\n;"
                    + "Mesa:                          "+mesa+"\n;"
                     + ""+fac.get(alFactura.size()-1+numFactura).getFecha()+"  "+fac.get(fac.size()-1+numFactura).getHora()+"\n;"
                         + "-----------------------------------\n;"
                         + "                                 \n;"
                    + "Descripcion de productos:              \n;"
                    + "Uds.        Nombre        Precio\n;"
                  
                    + "-----------------------------------\n;";
                    String a=aux+";";
                    String h=Header+a;
             //String a=jLabel4.getText()+"      "+jTextField1.getText()+"      "+jLabel2.getText()+"\n;";
             //String h=Header+a;
        String ant  =
                    "\n;-----------------------------------\n;"
                    + "Total entregado:   "+alFactura.get(alFactura.size()-1+numFactura).getEntregado()+"€\n;"

                    + "Total:                 "+fac.get(fac.size()-1+numFactura).getTotal()+"€\n;"
                    + "-----------------------------------\n;"
                     + "-----------------------------------\n;"
                    + "       Software Developed by      \n ;"
                    + "             Skatt9 S.A                \n;"
                    + "**********************************\n;"
                    + "         Gracias por su visita             \n;"
                    + "__________________________________\n;";
       
        String zbill = h + ant;
       String[] parts = zbill.split(";");  
        String aux2 ="";
        for(int i =0;i<parts.length;i++)
        {
            aux2 = aux2 +parts[i];
        }

      Font font = Font.font("Ubuntu Mono", FontPosture.REGULAR, 20);

        Text text3 = new Text(aux2);
        text3.setFont(font);
        
        
        vf.setContent(text3);
        
        return zbill;
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
      public void ponerMesa(String nombreDeMesa)
      {
          
            System.out.println("sasasa");
        
        Connection con = bd.getConnection();
               GregorianCalendar gg = new GregorianCalendar();
        
        SimpleDateFormat ddd = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        
          Date date = new Date();
          long timeInMilliSeconds = date.getTime();
           java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
       Factura f= alFactura.get(alFactura.size()-1+numFactura);
       int llave=0;     
            String query = "UPDATE `factura` SET `cancelado` = 1 where id = '"+f.getId()+"'";
            
            executeQuery(query);
            String query2 = "INSERT INTO `factura` (`id`, `efectivo`,`tarjeta`,`total`,`hora`,`fecha`,`idcierrecaja`,`entregado`,`cancelado`) VALUES (NULL, '"+-(f.getEfectivo())+"','"+-(f.getTarjeta())+"','"+-(f.getTotal())+"','"+ddd.format(gg.getTime())+"','"+date1+"','"+f.getIdcierrecaja()+"','"+-(f.getEntregado())+"',1)";
            Statement st;
       ResultSet rs;
       
       try
       {
       
            st = con.createStatement();
           st.executeUpdate(query2,Statement.RETURN_GENERATED_KEYS);
            //st.executeUpdate(query);
            
            rs = st.getGeneratedKeys();
            
            
            
            if(rs.next())
            {
               llave = rs.getInt(1);
            }
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
            for(int i =0; i<auxCm.size();i++)
            {
               String query3 = "INSERT INTO `cuentaMesa` (`id`, `nombremesa`,`nombreProducto`,`precioProducto`,`idFactura`,`cantidad`,`total`) VALUES (NULL, '"+auxCm.get(i).getNombreMesa()+"','"+auxCm.get(i).getNombre()+"','"+-(auxCm.get(i).getPrecio())+"','"+llave+"','"+-(auxCm.get(i).getUds())+"','"+-(auxCm.get(i).getTotal())+"')";
            executeQuery(query3);  
             System.out.println("gettotal"+auxCm.get(i).getTotal());
            }
             for(int i =0; i<auxCm.size();i++)
            {
              String query4 = "INSERT INTO `cuentaMesa` (`id`, `nombremesa`,`nombreProducto`,`precioProducto`,`idFactura`,`cantidad`,`total`) VALUES (NULL, '"+nombreDeMesa+"','"+auxCm.get(i).getNombre()+"','"+auxCm.get(i).getPrecio()+"','"+0+"','"+auxCm.get(i).getUds()+"','"+auxCm.get(i).getTotal()+"')";

            executeQuery(query4);  
            }
             String query5 = "UPDATE mesa SET  estado = 1 WHERE mesa.nombre='"+nombreDeMesa+"'";
         executeQuery(query5);
          Stage stage = (Stage) this.reabrir.getScene().getWindow();
        stage.close();
           

      }
    
}
