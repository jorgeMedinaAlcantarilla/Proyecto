/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.MesasArrasatrar;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import proyecto1.bd;


/**
 * FXML Controller class
 *
 * @author skatt
 */
public class ConfMesasController implements Initializable {
       private Mesa mesa; 
    private  int numMesa=1;
    @FXML
    private AnchorPane panel;
    bd bd;
    @FXML
    private Button salir;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bd=new bd();
        verMesas();
        
    }    

    @FXML
    private void eliminar(MouseEvent event) {
    }
    
     public void initAttributtes(Mesa mesa)
    {
       this.mesa=mesa;
       
        
    }

    @FXML
    private void aniadir(MouseEvent event) {
        
     
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/TipoMesas.fxml"));
            
           
            
        try {
            Pane root = loader.load();
           
            TipoMesasController controlador = loader.getController();
            controlador.recibirParametros(this);
            
            Scene scene = new Scene (root);
            Stage stage = new Stage();
              stage.initStyle( StageStyle.TRANSPARENT );
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño_1.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
           
            stage.setScene(scene);
            stage.show();
       
        } catch (IOException ex) {
            Logger.getLogger(ConfMesasController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    @FXML
    private void salir(MouseEvent event) {
        
        guardarPosicion();
        Stage stage =(Stage) this.salir.getScene().getWindow();
        stage.close();
    }

   public void setData()
   {
       //guardarPosicion();
      guardarPosicion();
       verMesas();
       /*System.out.println("no");
                  MesasArrasatrar node = new MesasArrasatrar();
            node.setPrefSize(98, 80);
            
            node.setText("sas");
            // define the style via css
            node.setStyle(
                "-fx-background-color: #50EB64; "
                //+ "-fx-text-fill: black; "
                +":pressed{-fx-background-color: #2F2B43;\n" +
                    "    -fx-background-radius: 20;};"
                + "-fx-border-color: black;");
            // position the node
            node.setLayoutX(30);
            node.setLayoutY(30);
            // add the node to the root pane 
            panel.getChildren().add(node);
            numMesa=numMesa+1;*/
   }
 
  public void guardarPosicion()
   {
       ObservableList<Mesa> listaMesa = FXCollections.observableArrayList();
       for(int i=0;i<panel.getChildren().size();i++)
       {
           listaMesa.add((Mesa)panel.getChildren().get(i));
       }
        //ObservableList<Mesa> listaMesa= panel.getChildren().get(numMesa);
        Connection con = bd.getConnection();   
            for(int i=0;i<listaMesa.size();i++)
            {
               
                Mesa m = listaMesa.get(i);
                     System.out.println("asd"+m.getX());
                     System.out.println("asd"+m.getNombre());
     String query = "UPDATE `mesa` SET `x` = '"+m.getX()+"', `y` = '"+m.getY()+"' WHERE `mesa`.`nombre` = '"+m.getNombre()+"'";
     
           executeQuery(query);
         
            }
            
            
            
            
   }
    public void verMesas()
    {
           
           ObservableList<Mesa> listaMesa= getInformacionBD();
          // guardarPosicion();
           panel.getChildren().clear();
            for(int i=0;i<listaMesa.size();i++)
            {
                    Mesa m = new Mesa(listaMesa.get(i).getNombre(),listaMesa.get(i).getX(),listaMesa.get(i).getY());
                    
            m.setPrefSize(98, 80);
            
            m.setText(m.getNombre());
            // define the style via css
            m.setStyle("-fx-background-color:#ece8e8;"
                        +"-fx-border-color: #66ff8b;"
                        + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.7), 10, 0.5, 0.0, 0.0 );"
                        + "-fx-background-radius: 20;" 
                        + "-fx-border-radius: 20;"
                        + "-fx-font-size:20;"
                        + "-fx-font-weight:bold;");
            // position the node
            m.setLayoutX(m.getX());
            m.setLayoutY(m.getY());
            System.out.println(m.getLayoutX());
            // add the node to the root pane 
            panel.getChildren().add(m);
          
            }
            
            
  
    }
    private ObservableList<Mesa> getInformacionBD()
    {
        
        ObservableList<Mesa> listaMesa = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM mesa";
        
        Statement st;
        ResultSet rs;
   
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Mesa m;
            
            while(rs.next())
            {
                m = new Mesa(rs.getString("nombre"),rs.getDouble("x"),rs.getDouble("y"));
              
                
             listaMesa.add(m);
                 
            }
           
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaMesa;
        
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
