/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class ConfCategoriasController implements Initializable {

    @FXML
    private TextField textoNombre;
    @FXML
    private Button aniadir;
    @FXML
    private Button actualizar;
    @FXML
    private Button eliminar;
    @FXML
    private TableView<Categoria> tablaCategoriaa;
    @FXML
    private TableColumn<Categoria, String> colNombre;
    @FXML
    private Button salirr;
    
    bd bd;
    @FXML
    private ColorPicker color;
    @FXML
    private Pane colorCategoria;
    
    ObservableList<Categoria> listaTabla = FXCollections.observableArrayList();
    ConfProductosController ccc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = new bd();
        tablaCategoriaa.setPlaceholder(new Label(""));
        aniadir.setDisable(false);
                     actualizar.setDisable(false);
                     eliminar.setDisable(false);
        showTable();
        addListenerForTable();
    }    

    @FXML
    private void aniadir(MouseEvent event) {
        
        String nombre = textoNombre.getText();
        Color auxColor = color.getValue();
        String colorWeb = String.format( "#%02X%02X%02X",
            (int)( auxColor.getRed() * 255 ),
            (int)( auxColor.getGreen() * 255 ),
            (int)( auxColor.getBlue() * 255 ) );
        
        
        
        if(!nombre.isEmpty()&&auxColor!=null)
     
        {
           
            String query = "INSERT INTO `categoria` (`id`, `nombre`,`color`) VALUES (NULL, '"+nombre+"','"+colorWeb+"')";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTable();
             
             
        }
        else
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Datos incorrectos");
            alert.showAndWait(); 
        }
        
    }

    @FXML
    private void actualizar(MouseEvent event) {
        Categoria c=this.tablaCategoriaa.getSelectionModel().getSelectedItem();
            
            String cambio = textoNombre.getText();
            Color auxColor = color.getValue();
             String colorWeb = String.format( "#%02X%02X%02X",
            (int)( auxColor.getRed() * 255 ),
            (int)( auxColor.getGreen() * 255 ),
            (int)( auxColor.getBlue() * 255 ) );
        if(!cambio.isEmpty()&&auxColor !=null)
        {
             String query = "UPDATE `categoria` SET `nombre` = '"+cambio+"' , `color` = '"+colorWeb+"' WHERE `categoria`.`id` = '"+c.getIdd()+"'";
            //String query1 = "INSERT INTO `mesa` (`idMesa`, `nombre`, `idTipoMesa`,`numeroMesa`, `x`, `y`) VALUES (NULL, '"+nombre+strcountMesas+"', '"+tm.getId()+"','"+strcountMesas+"', NULL, NULL);";
           
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTable();
            
        }
        else
        {
               Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Datos incorrectos");
            alert.showAndWait(); 
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
            Categoria c=this.tablaCategoriaa.getSelectionModel().getSelectedItem();
            String nombre = c.getNombre();
       
        if(c!=null)
        {
            String query = "DELETE FROM `categoria` WHERE `categoria`.`nombre` = '"+nombre+"'";
            String query2 = "DELETE FROM `producto` WHERE `producto`.`nombreCategoria` = "+nombre+"";
            //String query1 = "INSERT INTO `mesa` (`idMesa`, `nombre`, `idTipoMesa`,`numeroMesa`, `x`, `y`) VALUES (NULL, '"+nombre+strcountMesas+"', '"+tm.getId()+"','"+strcountMesas+"', NULL, NULL);";
           
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTable();
            
        }
        else
        {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Datos seleccionar un registro");
            alert.showAndWait(); 
        }
          
       
    }

    @FXML
    private void salir(MouseEvent event) {
        ccc.actuCombo();
        Stage stage = (Stage) this.salirr.getScene().getWindow();
        stage.close();
    }
    
    private ObservableList<Categoria> getTablaCategoria()
    {
        
        listaTabla = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM categoria";
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Categoria categoria;
            
            while(rs.next())
            {
                categoria = new Categoria(rs.getInt("id"),rs.getString("nombre"),rs.getString("color"));
                listaTabla.add(categoria);
                
                
                        
            }
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return listaTabla;
        
    }
    
      public void showTable()
    {
           ObservableList<Categoria> listaCategoria= getTablaCategoria();
           this.colNombre.setCellValueFactory(new PropertyValueFactory<Categoria,String>("Nombre"));
           
           tablaCategoriaa.setItems(listaCategoria);
           
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
      
      
        private void addListenerForTable()
      {
         
         
          tablaCategoriaa.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
              if(newSelection != null)
              {   aniadir.setDisable(false);
                     actualizar.setDisable(false);
                     eliminar.setDisable(false);
                  int num = tablaCategoriaa.getSelectionModel().getSelectedIndex();
                  Categoria aux = listaTabla.get(num);
                  if(aux.getColor()!=null)
                  {    textoNombre.setText(aux.getNombre());
                      colorCategoria.setBackground(new Background(new BackgroundFill(Paint.valueOf(aux.getColor()),CornerRadii.EMPTY,Insets.EMPTY)));
                      //colorCategoria.setStyle("-fx-background-color:# "+aux.getColor()+";");
                  }
                  
              }
              else
              {
                   aniadir.setDisable(false);
                     actualizar.setDisable(true);
                     eliminar.setDisable(true);
              }
              
              
                  
              
          });
      }
        public void setData(ConfProductosController ccc)
        {
            this.ccc = ccc;
        }
    
}
