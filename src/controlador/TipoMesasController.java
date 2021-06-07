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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static proyecto1.PROYECTO1.addTextLimiter;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class TipoMesasController implements Initializable {

    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;
    @FXML
    private TableView<TipoMesa> tablaTipoMesa;
    @FXML
    private TableColumn<TipoMesa,String> colNombre;
    @FXML
    private TableColumn<TipoMesa,String> colDesc;
    
    @FXML
    private Button eliminar;
    @FXML
    private Button salir;
    
    private TipoMesa tipoMesa;
    
    private Mesa mesa;
    @FXML
    private Button elegir;
    
    ConfMesasController confMesas;
    
    bd bd;
    @FXML
    private TableColumn<TipoMesa, Integer> colid;
    @FXML
    private TableColumn<Mesa, String> colNnombreMesa;
    @FXML
    private TableView<Mesa> tablaListaMesa;

    private int contMesas;
    @FXML
    private Button aniadirTM;
    @FXML
    private Button creacionMesas;
    @FXML
    private Button eliminarMesa;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd=new bd();
        
        addTextLimiter(nombre,3);
        addListennerForTable();
        showTable();
        //tipoMesas=FXCollections.observableArrayList();
       // this.colNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
      //  this.colDesc.setCellValueFactory(new PropertyValueFactory("Descripcion"));
       
    }
    
    public void showTable()
    {
           ObservableList<TipoMesa> lista= getTablaTipoMesa();
           //this.colid.setCellValueFactory(new PropertyValueFactory<TipoMesa,Integer>("id"));
           this.colNombre.setCellValueFactory(new PropertyValueFactory<TipoMesa,String>("Nombre"));
           this.colDesc.setCellValueFactory(new PropertyValueFactory<TipoMesa,String>("Descripcion"));
           tablaTipoMesa.setItems(lista);
           
    }
  
   public void showTableMesa()
    {
           ObservableList<Mesa> listaMesa= getTablaMesa();
           
           this.colNombre.setCellValueFactory(new PropertyValueFactory<TipoMesa,String>("Nombre"));
           
           tablaListaMesa.setItems(listaMesa);
           
    }

    
    @FXML
    private void agregarTM(MouseEvent event) {

        String nombre = this.nombre.getText();
        String descripcion = this.descripcion.getText();
        if(!nombre.isEmpty())
        {
             try
             {
            String query = "INSERT INTO `tipo_mesa` (`id`, `nombre`, `descripcion`) VALUES (NULL, '"+nombre+"', '"+descripcion+"')";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTable();
             }
             catch(Exception e)
             {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("El tipo de mesa ya existe");
            alert.showAndWait();
             }
            
            
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No has rellenado el campo nombre.");
            alert.showAndWait();
        }
        
        
    }

    @FXML
    private void eliminar(MouseEvent event) {
        
        TipoMesa tm=this.tablaTipoMesa.getSelectionModel().getSelectedItem();
        int obtId = tm.getId();
        if( tm == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No has seleccionado ningun tipo.");
            alert.showAndWait();
        }
        
        else
        {
           // ObservableList<TipoMesa> lista= getTablaTipoMesa();
           // lista.remove(tm);
           // this.tablaTipoMesa.refresh();
            
            String query = "DELETE FROM `tipo_mesa` WHERE `tipo_mesa`.`id` = "+obtId+"";
            String query2 = "DELETE FROM `mesa` WHERE `mesa`.`idTipoMesa` = "+obtId+"";
             executeQuery(query);
             executeQuery(query2);
                showTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Tipo eliminado");
            alert.showAndWait();
        }
    }

    @FXML
    private void salir(MouseEvent event) {
        
        Stage stage = (Stage) this.salir.getScene().getWindow();
        stage.close();
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    @FXML
    private void elegir(MouseEvent event) {
    
            confMesas.setData();
           
                
                Stage stage =(Stage) this.elegir.getScene().getWindow();
                stage.close();
           
        }

    private ObservableList<TipoMesa> getTablaTipoMesa()
    {
        
        ObservableList<TipoMesa> listaTabla = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM tipo_mesa";
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            TipoMesa tipoMesa;
            
            while(rs.next())
            {
                tipoMesa = new TipoMesa(rs.getInt("id"),rs.getString("nombre"),rs.getString("descripcion"));
                listaTabla.add(tipoMesa);
                
                
                        
            }
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaTabla;
        
    }
    
    private ObservableList<Mesa> getTablaMesa(TipoMesa tipoMesa)
    {   contMesas=1;
        ObservableList<Mesa> listaTablaMesa = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT nombre FROM mesa where idTipoMesa = "+tipoMesa.getId()+"";
        System.out.println(tipoMesa.getId());
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Mesa mesa;
            
            while(rs.next())
            {
                mesa = new Mesa(rs.getString("nombre"));
                listaTablaMesa.add(mesa);
                  contMesas=contMesas+1;      
            }
            
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaTablaMesa;
        
    }
   
     private ObservableList<Mesa> getTablaMesa()
    {
        contMesas=1;
        TipoMesa tm=this.tablaTipoMesa.getSelectionModel().getSelectedItem();
        ObservableList<Mesa> listaMesa = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT nombre FROM mesa where idTipoMesa="+tm.getId()+"";
        
        Statement st;
        ResultSet rs;
        
        
        
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Mesa mesa;
            
            while(rs.next())
            {
                contMesas=contMesas+1;
                mesa = new Mesa(rs.getString("nombre"));
                listaMesa.add(mesa);
                
                
                        
            }
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaMesa;
        
    }
    
    public void recibirParametros(ConfMesasController conMesas)
    {
        this.confMesas=conMesas;
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

    @FXML
    private void creacionMesas(MouseEvent event) {
        
        TipoMesa tm=this.tablaTipoMesa.getSelectionModel().getSelectedItem();
        String nombre = tm.getNombre();
        
       
       
        
        
        
        if(tm!=null)
        {
            String strcountMesas=String.valueOf(contMesas);
            System.out.println("..."+nombre+contMesas);
            String query = "INSERT INTO `mesa` (`id`, `idTipoMesa`, `numeroMesa`,`nombre`, `x`, `y`,estado) VALUES (NULL, '"+tm.getId()+"','"+strcountMesas+"','"+nombre+strcountMesas +"', 50.0, 50.0,0);";
           
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTableMesa();
            
            
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar tipo de mesa");
            alert.showAndWait();
        }
    }

    private void addListennerForTable()
    {
        this.tablaTipoMesa.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            
            if(newSelection!=null)
                {
                    TipoMesa tm = this.tablaTipoMesa.getSelectionModel().getSelectedItem();
                    
                    
        
        ObservableList<Mesa> listaTablaMesa = getTablaMesa(tm);
        this.colNnombreMesa.setCellValueFactory(new PropertyValueFactory<Mesa,String>("Nombre"));
        tablaListaMesa.setItems(listaTablaMesa);
       
                    
                }
        });
    }
    
   /* private void eliminarMesa(MouseEvent event) {
        
        
         Connection con = bd.getConnection();
        Mesa m=this.tablaListaMesa.getSelectionModel().getSelectedItem();
   
        
        if( m == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No has seleccionado ninguna mesa.");
            alert.showAndWait();
        }
        
        else
        {
         System.out.println("aa"+m.getNombre());
      
          String query = "DELETE FROM `mesa` WHERE `mesa`.`idMesa` = "+m.getId()+"";
            
             executeQuery(query);
              showTableMesa();
        }
        
    }*/

   

    @FXML
    private void edlm(MouseEvent event) {
             TipoMesa tm=this.tablaTipoMesa.getSelectionModel().getSelectedItem();
        String nombre = tm.getNombre();
        
       
       
        
        
        
        if(tm!=null)
        {
            int menosUno=contMesas-1;
            String strcountMesas=String.valueOf(menosUno);
            System.out.println("..."+nombre+contMesas);
            String query = "DELETE FROM `mesa` WHERE `mesa`.`numeroMesa` = "+strcountMesas+" AND `mesa`.`idTipoMesa` = "+tm.getId()+"";
            //String query1 = "INSERT INTO `mesa` (`idMesa`, `nombre`, `idTipoMesa`,`numeroMesa`, `x`, `y`) VALUES (NULL, '"+nombre+strcountMesas+"', '"+tm.getId()+"','"+strcountMesas+"', NULL, NULL);";
           
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query);
            showTableMesa();
            
            
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar tipo de mesa");
            alert.showAndWait();
        }
    }

  
 
    
    

    
    
    
    
    
}
