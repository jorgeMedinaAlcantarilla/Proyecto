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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class ReabrirController implements Initializable {

    ObservableList<Mesa> listaTabla = FXCollections.observableArrayList();
    bd bd;
    @FXML
    private TableView<Mesa> tablaMesa;
    @FXML
    private TableColumn<Mesa, String> colMesa;
    @FXML
    private Button aceptar;
    @FXML
    private Button salir;
    private VerTicketsController vtc;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = new bd();
        showTable();
    }    

    @FXML
    private void aceptarr(MouseEvent event) {
        
        Mesa m = this.tablaMesa.getSelectionModel().getSelectedItem();
        
        String nombre = m.getNombre();
        
        if(!nombre.isEmpty())
        {
         vtc.ponerMesa(nombre);
         Stage stage = (Stage) this.aceptar.getScene().getWindow();
        stage.close();
        }
        
        
    }

    @FXML
    private void salirr(MouseEvent event) {
         Stage stage = (Stage) this.salir.getScene().getWindow();
        stage.close();
    }
    
      private ObservableList<Mesa> getTablaMesa()
    {
        
        listaTabla = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT nombre FROM mesa where estado = 0";
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Mesa m;
            
            while(rs.next())
            {
                m = new Mesa(rs.getString("nombre"));
                listaTabla.add(m);
                
                
                        
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
           ObservableList<Mesa> listaMesa = getTablaMesa();
           this.colMesa.setCellValueFactory(new PropertyValueFactory<Mesa,String>("Nombre"));
           
           tablaMesa.setItems(listaMesa);
           
    }
     
      public void setData(VerTicketsController vtc)
      {
          this.vtc = vtc;
      }
    
}
