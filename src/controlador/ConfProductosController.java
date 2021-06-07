/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class ConfProductosController implements Initializable {

    @FXML
    private Button btnSelecionarImagen;
    @FXML
    private TextField nombreProducto;
    @FXML
    private TextField precioProducto;
    @FXML
    private ComboBox<String> comboCategoriaCrear;
    @FXML
    private Button btnAniadirCategoria;
    @FXML
    private Button btnCrearProducto;
    @FXML
    private ComboBox<String> comboCategoriaVer;
    @FXML
    private Button btnActu;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, String> colCategoria;
    @FXML
    private TableColumn<Producto, Boolean> colImagen;
    bd bd;
    @FXML
    private ImageView imagen;
    
    private File file;
    private Image imagenPoner;
    
    ObservableList<Producto> listaTabla = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = new bd();
        tablaProductos.setPlaceholder(new Label(" "));
        showTable();
        getCombo();
        addListenerForTable();
        btnCrearProducto.setDisable(false);
        btnActu.setDisable(true);
        btnBorrar.setDisable(true);
    }    

    @FXML
    private void seleccionarImagen(MouseEvent event) {
        
        try
        {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.png");
        
        fc.getExtensionFilters().addAll(ext1,ext2);
        
        file = fc.showOpenDialog(ConfiguracionController.getStage());
        BufferedImage bf;
        bf = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bf, null);
        if(image!=null)
        {
            imagen.setImage(image);
        }
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        
        
    }   

    @FXML
    private void aniadirCategoria(MouseEvent event) {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/confCategorias.fxml"));
            
            
            try
            {
            Pane root = loader.load();
          ConfCategoriasController controlador = (ConfCategoriasController)loader.getController(); 
            Scene scene = new Scene (root);
            Stage stage = new Stage();
            controlador.setData(this);
             stage.initStyle( StageStyle.TRANSPARENT );
            
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.setScene(scene);
            stage.show();
            }
            catch(Exception e)
            {
                
            }
    }

    @FXML
    private void crearProducto(MouseEvent event) {
         String nombre = nombreProducto.getText();
         String precio = precioProducto.getText();
       
         String categoria = comboCategoriaCrear.getSelectionModel().getSelectedItem();
         try
         {
          if(!nombre.isEmpty()&&!precio.isEmpty()&&!categoria.isEmpty()&&file!=null)
          {
               FileInputStream fis = new FileInputStream(file);
         int len = (int) file.length();
           /* String query = "INSERT INTO `producto` (`id`, `nombre`,`nombreCategoria`,`imagen`,`precio`) VALUES (NULL, '"+nombre+"','"+categoria+"',NULL,'"+precio+"')";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTable();*/
             
            Connection con = bd.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO `producto` (`id`, `nombre`,`nombreCategoria`,`imagen`,`precio`) VALUES (NULL,?,?,?,?)");
           
            ps.setString(1, nombre);
            ps.setString(2, categoria);
            ps.setBinaryStream(3, fis, len);
            ps.setString(4, precio);
            
            
            int res = ps.executeUpdate();
            
            if(res > 0)
            {
                nombreProducto.clear();
                precioProducto.clear();
                
                comboCategoriaCrear.valueProperty().set(null);
                imagen.setImage(null);
                file = null;
                showTable();
                
                
            }
          }
          else
          {
              
          }
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
          
        
        
         
         if(!nombre.isEmpty()&&!precio.isEmpty()&&!categoria.isEmpty())
         {
        String query = "INSERT INTO `producto` (`id`, `nombre`,`nombreCategoria`,`imagen`,`precio`) VALUES (NULL, '"+nombre+"','"+categoria+"',NULL,'"+precio+"')";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTable();
         }
         else
         {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No has rellenado los campos adecuadamente");
            alert.showAndWait();  
         }

    }
    @FXML
    private void actualizar(MouseEvent event) {
      Producto c=this.tablaProductos.getSelectionModel().getSelectedItem();
            
      String cambio = nombreProducto.getText();
      String precio = precioProducto.getText();
      double precio1 = Double.parseDouble(precio);
      String categoria = comboCategoriaCrear.getSelectionModel().getSelectedItem();
       
       
      if(file!=null)
      {
        if(!cambio.isEmpty()&&!precio.isEmpty()&&!categoria.isEmpty())
        {
            try
            {
                FileInputStream fis = new FileInputStream(file);
                    int len = (int) file.length();
              Connection con = bd.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE `producto` SET nombre = ?,nombreCategoria =?, imagen =?,precio=?  ");
           
            ps.setString(1, cambio);
            ps.setString(2, categoria);
            ps.setBinaryStream(3, fis, len);
            ps.setString(4, precio);
             
            ps.executeUpdate();
             ps.close();
             showTable();
            }
            catch(Exception e)
            {
                
            }
            
        }
      }
      else
      {
            if(!cambio.isEmpty()&&!precio.isEmpty()&&!categoria.isEmpty())
        {
             String query = "UPDATE `producto` SET `nombre` = '"+cambio+"', `precio` = '"+precio1+"' , nombreCategoria = '"+categoria+"' WHERE `producto`.`id` = '"+c.getId()+"';";
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
            alert.setContentText("No has rellenado los campos adecuadamente");
            alert.showAndWait();
            }
      }
    }

    @FXML
    private void borrar(MouseEvent event) {
        
       Producto p=this.tablaProductos.getSelectionModel().getSelectedItem();
       String nombre = p.getNombre();
       
        if(p!=null)
        {
            String query = "DELETE FROM `producto` WHERE `producto`.`nombre` = '"+nombre+"'";
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
            alert.setContentText("Debes seleccionar un producto");
            alert.showAndWait();
        }
    }

    @FXML
    private void salir(MouseEvent event) {
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
        
    }
       private ObservableList<Producto> getTablaProducto()
    {
        
        
        this.listaTabla = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM producto order by nombreCategoria";
        
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
                listaTabla.add(producto);
                
                System.out.println(producto.getCategoria());
                        
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
           ObservableList<Producto> listaProducto= getTablaProducto();
           this.colNombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("Nombre"));
           this.colPrecio.setCellValueFactory(new PropertyValueFactory<Producto,Double>("Precio"));
           this.colCategoria.setCellValueFactory(new PropertyValueFactory<Producto,String>("Categoria"));
           //this.colImagen.setCellValueFactory(new PropertyValueFactory<Producto,Boolean>("Imagen"));
           tablaProductos.setItems(listaProducto);
           
    }
      
         private void getCombo()
    {
        
        ObservableList<String> listaCombo = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT nombre FROM categoria";
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Categoria categoria;
            
            while(rs.next())
            {
                listaCombo.add(rs.getString("nombre"));
                
            }
            comboCategoriaCrear.setItems(null);
            comboCategoriaCrear.setItems(listaCombo);
            
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
      
    }
     
     public void actuCombo()
     {
         getCombo();
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
         
         
          tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
              if(newSelection != null)
              {   //btnCrearProducto.setDisable(true);
                  
                   btnCrearProducto.setDisable(false);
                     btnActu.setDisable(false);
                     btnBorrar.setDisable(false);
                  nombreProducto.setText(""+ newSelection.getNombre());
                  precioProducto.setText(newSelection.getPrecio());
                  comboCategoriaCrear.getSelectionModel().select(newSelection.getCategoria());
                  
                  int num = tablaProductos.getSelectionModel().getSelectedIndex();
                  
                  Producto aux = listaTabla.get(num);
                  
                  if(aux.getImagen()!=null)
                  {
                      try
                      {
                      InputStream is = aux.getImagen().getBinaryStream();
                       BufferedImage bf;
                       bf = ImageIO.read(is);
                        Image image = SwingFXUtils.toFXImage(bf, null);
                        imagen.setImage(image);
                      }
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                  }
                  else
                  {
                      imagen.setImage(null);
                  }
                   
                  
              }
              else
              {
                  imagen.setImage(null);
                  nombreProducto.setText("");
                  precioProducto.setText("");
                  comboCategoriaCrear.getSelectionModel().selectFirst();
                  
                  btnCrearProducto.setDisable(false);
                     btnActu.setDisable(true);
                     btnBorrar.setDisable(true);
                  //imagen.setImage(null);
                 // showTable();
              }
          });
      }
      
      
    
    
    
}
