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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class PlanoMesasController implements Initializable {

    @FXML
    private AnchorPane panel;
    @FXML
    private Button salir;
    bd bd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd= new bd();
        verMesas();
    }  
    
      public void verMesas()
    {
           
           ObservableList<Mesa> listaMesa= getInformacionBD();
          // guardarPosicion();
           panel.getChildren().clear();
            for(int i=0;i<listaMesa.size();i++)
            {
                    Mesa m = new Mesa(listaMesa.get(i).getIdd(),listaMesa.get(i).getNombre(),listaMesa.get(i).getIdTipoMesa(),listaMesa.get(i).getNumeroMesa(),listaMesa.get(i).getX(),listaMesa.get(i).getY(),listaMesa.get(i).getEstado());
                    m.getPM(this);
            m.setPrefSize(98, 80);
            
            m.setText(m.getNombre());
            // define the style via css
            switch(m.getEstado())
            {
                case 0:
                m.setStyle("-fx-background-color:#ece8e8;"
                        +"-fx-border-color: #66ff8b;"
                        + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.7), 10, 0.5, 0.0, 0.0 );"
                        + "-fx-background-radius: 20;" 
                        + "-fx-border-radius: 20;"
                        + "-fx-font-size:20;"
                        + "-fx-font-weight:bold;");
                break;
                case 1://m.setBackground(new Background(new BackgroundFill(Paint.valueOf("#66ff8b"),CornerRadii.EMPTY,Insets.EMPTY)));
                m.setStyle("-fx-background-color:#66ff8b;"
                        +"-fx-border-color: #8f5d8f;"
                        + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.7), 10, 0.5, 0.0, 0.0 );"
                        + "-fx-background-radius: 20;" 
                        + "-fx-border-radius: 20;"
                        + "-fx-font-size:20;"
                        + "-fx-font-weight:bold;");
                break;
                case 2://m.setBackground(new Background(new BackgroundFill(Paint.valueOf("#8f5d8f"),CornerRadii.EMPTY,Insets.EMPTY)));
                m.setStyle("-fx-background-color:#8f5d8f;"
                        +"-fx-border-color: #66ff8b;"
                        + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.7), 10, 0.5, 0.0, 0.0 );"
                        + "-fx-background-radius: 20;" 
                        + "-fx-border-radius: 20;"
                        + "-fx-font-size:20;"
                        + "-fx-font-weight:bold;");
                break;
            }
            
            
           /* m.setStyle(
                "-fx-background-color: #50EB64; "
                //+ "-fx-text-fill: black; "
                +":pressed{-fx-background-color: #2F2B43;\n" +
                    "    -fx-background-radius: 20;};"
                + "-fx-border-color: black;");*/
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
                    m = new Mesa(rs.getInt("id"),rs.getString("nombre"),rs.getInt("idtipomesa"),rs.getInt("numeroMesa"),rs.getDouble("x"),rs.getDouble("y"),rs.getInt("estado"));
              
                
             listaMesa.add(m);
                 
            }
           
          
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaMesa;
        
    }

    @FXML
    private void salir(MouseEvent event) {
         Stage stage2 = (Stage) this.salir.getScene().getWindow();
            stage2.close();
    }
    
}
