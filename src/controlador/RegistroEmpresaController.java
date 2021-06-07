/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.sun.javaws.Main;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class RegistroEmpresaController implements Initializable {

    @FXML
    private ImageView imagen;
    @FXML
    private TextField nombre;
    @FXML
    private TextField dir;
    @FXML
    private TextField nif;
    @FXML
    private Button okay;
    private File file;
    bd bd;
    Stage fac;
    int registro =0;
    private String nombree="";
    private String direccion="";
    private String NIIF="";
    private  Blob imagenn=null;

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

        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
           
            
            while(rs.next())
            {
               
                nombree = rs.getString("nombre");
                direccion = rs.getString("direccion");
                NIIF = rs.getString("nif");
                imagenn = rs.getBlob("logo");
                registro ++;
                 
            }
            
            rs.close();
            rs.close();
            
            if(registro!=0)
            {
                if(imagenn!=null)
        {
            try
                      {
                      InputStream is = imagenn.getBinaryStream();
                       BufferedImage bf;
                       bf = ImageIO.read(is);
                        Image image = SwingFXUtils.toFXImage(bf, null);
                        imagen.setImage(image);
                        nombre.setText(nombree);
                        dir.setText(direccion);
                        nif.setText(NIIF);
                        
                      }
                      catch(Exception e)
                      {
                          e.printStackTrace();
                      }
        }
        else
        {       imagen.setImage(null);
               nombre.setText(nombree);
                        dir.setText(direccion);
                        nif.setText(NIIF);
        } 
            }
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
       
       
    }    

    @FXML
    private void selImagen(MouseEvent event) {
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
    private void okay(MouseEvent event)  {
        nombree = nombre.getText();
        direccion = dir.getText();
        NIIF = nif.getText();
       
        if(registro!=0)
        {
           try
           {
            if (file!=null)
            {
                 System.out.println("2");
                   FileInputStream fis = new FileInputStream(file);
                    int len = (int) file.length();
         
          Connection con = bd.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE `empresa` SET nombre = ?,direccion =?, nif =?,logo=?  ");
           
            ps.setString(1, nombree);
            ps.setString(2, direccion);
           
            ps.setString(3, NIIF);
             ps.setBinaryStream(4, fis, len);
            ps.executeUpdate();
             ps.close();
      
           try
           {
    
             System.out.println("4");
            
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
        
             Stage stage = (Stage) this.okay.getScene().getWindow();
             stage.close();  
            }
            else
            {       System.out.println("5");
                try
                {
                String query2 = "UPDATE `empresa` SET `nombre` = '"+nombree+"' , `direccion` = '"+direccion+"',`nif` = '"+NIIF+"'";
                executeQuery(query2);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
               
                Stage stage = (Stage) this.okay.getScene().getWindow();
                stage.close();
            }
           }
           catch(Exception e)
           {
               e.printStackTrace();
           }
        }
        else
        {
        try
        {
         String nombre1 = nombre.getText();
         String direccion = dir.getText();
         String niif = nif.getText();
        
         
          if(!nombre1.isEmpty()&&!direccion.isEmpty()&&!niif.isEmpty()&&file!=null)
          {
            FileInputStream fis = new FileInputStream(file);
            int len = (int) file.length();
         
             
                Connection con = bd.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO `empresa` (`id`, `nombre`,`direccion`,`nif`,`logo`) VALUES (NULL,?,?,?,?)");
           
            ps.setString(1, nombre1);
            ps.setString(2, direccion);
            ps.setString(3, niif);
            ps.setBinaryStream(4, fis, len);
            
            
            int res = ps.executeUpdate();
            con.close();
            if(res > 0)
            {
             Stage stage = (Stage) this.okay.getScene().getWindow();
             stage.close();
                Stage stag2 = (Stage) this.fac.getScene().getWindow();
            stag2.close();
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/principal.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage2 = new Stage();
            stage2.initStyle( StageStyle.TRANSPARENT );
            stage2.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            
            stage2.setScene(scene);
            stage2.setMaximized(true);
            stage2.show();
            System.out.println("sasasa");
                
                
            }
          }
         

         if(!nombre1.isEmpty()&&!direccion.isEmpty()&&!niif.isEmpty()&&file==null)
         {
            String query = "INSERT INTO `empresa` (`id`, `nombre`,`direccion`,`nif`,`logo`) VALUES (NULL, '"+nombre1+"','"+direccion+"','"+niif+"',NULL)";
            executeQuery(query);
            Stage stage = (Stage) this.okay.getScene().getWindow();
            stage.close();
            
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/principal.fxml"));
            
            Pane root = loader.load();
          
        
            Scene scene = new Scene (root);
            Stage stage2 = new Stage();
            stage2.initStyle( StageStyle.TRANSPARENT );
            stage2.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            stage2.setScene(scene);
            stage2.setMaximized(true);
            stage2.show();
        
         }
    
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
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
       
       public void setScene(Stage fac)
       {
           this.fac = fac;
       }
    
}
