/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.Producto;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class ProductoControlador implements Initializable {

    @FXML
    private VBox vb;
    @FXML
    private Label nombree;
    private String nombre;
    private double precio;
    @FXML
    private Label precioo;
    @FXML
    private ImageView imagee;
    GestionMesasController gestionMesas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setData(Producto producto)
    {
        
         System.out.println("aaa");
         nombre = producto.getNombre();
         precio = Double.parseDouble(producto.getPrecio());
        nombree.setText(producto.getNombre());
        precioo.setText(producto.getPrecio()+"€");
        
        if(producto.getImagen()!=null)
        {
             try
                      {
                      InputStream is = producto.getImagen().getBinaryStream();
                       BufferedImage bf;
                       bf = ImageIO.read(is);
                        Image imagen = SwingFXUtils.toFXImage(bf, null);
                        imagee.setImage(imagen);
                      }
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
        }
        else
        {
            imagee.setImage(null);
        }
        
       
    }

    @FXML
    private void infoProductos(MouseEvent event) {
        
        this.gestionMesas.setDataInfoProductos(nombre,precio);
    }
     public void recibirParametros(GestionMesasController gestionMesas)
    {
        this.gestionMesas = gestionMesas;
    }
    
    
}
