/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class PrincipalController implements Initializable {

    @FXML
    private Label empresa;
    @FXML
    private ImageView logo;
     private Blob imagenn;
    @FXML
    private Button exitt;
    @FXML
    private ImageView btnVerMesas1;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
       bd bd=new bd();
         
          Connection con = bd.getConnection();
        String query = "SELECT * FROM empresa";
        int registro =0;
        String nom="";
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
           
            
            while(rs.next())
            {
                nom = rs.getString("nombre");
                imagenn = rs.getBlob("logo");
                
                 
            }
              rs.close();
              st.close();
            
            
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(imagenn!=null)
        {
            try
                      {
                      InputStream is = imagenn.getBinaryStream();
                       BufferedImage bf;
                       bf = ImageIO.read(is);
                        Image image = SwingFXUtils.toFXImage(bf, null);
                        logo.setImage(image);
                        empresa.setText(nom);
                      }
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
        }
        else
        {
            logo.setImage(null);
             empresa.setText(nom);
        }
      
        
    }    

  

    @FXML
    private void abrirConf(MouseEvent event) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Configuracion.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.initStyle( StageStyle.UNDECORATED );
            stage.setMaximized(true);
            stage.show();
            System.out.println("sasasa");
    }

    @FXML
    private void verMesas(MouseEvent event) throws IOException {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/planoMesas.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
            stage.initStyle( StageStyle.TRANSPARENT );
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            
            
            //stage.setMaximized(true);
            stage.show();
            System.out.println("sasasa");
    }

    @FXML
    private void abrirTickets(MouseEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/verTickets.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
             stage.initStyle( StageStyle.TRANSPARENT );
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            
            stage.show();
            System.out.println("sasasa");
         
    }

    @FXML
    private void abrirZ(MouseEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VisorFacturaz.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
             stage.initStyle( StageStyle.TRANSPARENT );
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            
            stage.show();
            System.out.println("sasasa");
    }

   

    @FXML
    private void exitt(MouseEvent event) {
        System.exit(1);
    }


}
    

