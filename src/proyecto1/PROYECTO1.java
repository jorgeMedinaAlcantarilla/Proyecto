/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import com.sun.javaws.Main;
import controlador.Categoria;
import controlador.FondoAuxController;
import controlador.GestionMesasController;
import controlador.PrincipalController;
import controlador.RegistroEmpresaController;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author skatt
 */
public class PROYECTO1 extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
         bd bd=new bd();
         
          Connection con = bd.getConnection();
        String query = "SELECT * FROM empresa";
        int registro =0;
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
           
            
            while(rs.next())
            {
                registro = rs.getInt(1);
                 
            }
            
           rs.close();
           rs.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        if(registro !=0)
        {
              FXMLLoader loader = new FXMLLoader();
     loader.setLocation(Main.class.getResource("/vista/principal.fxml"));
   
     Pane ventana = (Pane) loader.load();
     Scene scene = new Scene(ventana);
     //stage.setFullScreen(true);
     stage.setMaximized(true);
     stage.initStyle( StageStyle.UNDECORATED );
     stage.initStyle( StageStyle.TRANSPARENT );
     scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
     stage.setScene(scene);
      stage.show();
        }
        else
        {
         try
        {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/fondoAux.fxml"));
        Pane root = loader.load();
        FondoAuxController controlador = (FondoAuxController)loader.getController();
        Scene scene = new Scene (root);
        Stage stage2 = new Stage();
        stage2.initStyle( StageStyle.TRANSPARENT );
        stage2.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
        stage2.setScene(scene);
        stage2.initStyle( StageStyle.UNDECORATED );
        stage2.setMaximized(true);
        stage2.show();
           
     
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/vista/registroEmpresa.fxml"));
            Pane root3 = loader2.load();
            RegistroEmpresaController controlador2 = (RegistroEmpresaController)loader2.getController();
            controlador2.setScene(stage2);
        
            Scene scene3 = new Scene (root3);
            Stage stage3 = new Stage();
            
            stage3.setScene(scene3);
            stage3.initStyle( StageStyle.TRANSPARENT );
            
            scene3.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene3.setFill(Color.TRANSPARENT);
            stage3.initModality(Modality.APPLICATION_MODAL);
          
            stage3.show();
          
        }
        catch(Exception e)
        {
         e.printStackTrace();
        }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    public static void addTextLimiter(final TextField tf, final int maxLength) {
    tf.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        }
    });
}
    
}
