/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class ConfiguracionController implements Initializable {

    private static Stage stage;
    @FXML
    private Button salir;
    @FXML
    private ImageView saliir;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void editorMesas(MouseEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/confMesas.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
             stage.initStyle( StageStyle.TRANSPARENT );
            
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void editorProductos(MouseEvent event) {
        
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/confProductos.fxml"));
            
             try
             {
            Pane root = loader.load();
          
            Scene scene = new Scene (root);
            Stage stage = new Stage();
              stage.initStyle( StageStyle.TRANSPARENT );
            
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.setScene(scene);
            setStage(stage);
            stage.show();
             }
             catch(Exception e)
             {
                 
             }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ConfiguracionController.stage = stage;
    }

    @FXML
    private void editarEmpresa(MouseEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/registroEmpresa.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
             stage.initStyle( StageStyle.TRANSPARENT );
            
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void salir(MouseEvent event) {
         Stage stage2 = (Stage) this.saliir.getScene().getWindow();
            stage2.close();
    }
    
    
    
}
