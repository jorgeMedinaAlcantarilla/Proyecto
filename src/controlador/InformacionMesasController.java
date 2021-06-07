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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class InformacionMesasController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grind;
    
    ObservableList<Producto> listaTabla = FXCollections.observableArrayList();
    
    bd bd;
    @FXML
    private AnchorPane anchoPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = new bd();
        listaTabla.addAll(getProductos());
        int column = 0;
        int row = 0;
        
        for(int i = 0; i < listaTabla.size();i++)
        {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/vista/producto.fxml"));
            AnchorPane ap = loader.load(); 
            
            ProductoControlador controlador = loader.getController();
            controlador.setData(listaTabla.get(i));
            
            if (column == 3)
            {
                column = 0;
                row ++;
            }
            
            grind.add(ap,column++, row);
            GridPane.setMargin(ap,new Insets(20));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    
        }
    }



   private ObservableList<Producto> getProductos()
    {
        
        
        ObservableList<Producto> listaPro = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM producto";
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Producto producto;
            
            while(rs.next())
            {
                producto = new Producto(rs.getInt("id"),rs.getString("nombre"),rs.getString("nombreCategoria"),rs.getBlob("imagen"),rs.getString("precio"));
                listaPro.add(producto);
                
                System.out.println(producto.getCategoria());
                        
            }
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaPro;
        
    }    
    
}
