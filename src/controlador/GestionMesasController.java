/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.Mesa;
import controlador.Producto;
import controlador.ProductoControlador;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import proyecto1.bd;

/**
 * FXML Controller class
 *
 * @author skatt
 */
public class GestionMesasController implements Initializable {

    @FXML
    private GridPane grind;
    @FXML
    private ScrollPane scroll;
    private String nombreMesa;
    
    
     ObservableList<Categoria> listaCa = FXCollections.observableArrayList();
    
    bd bd;
    @FXML
    private ScrollPane scrollCategorias;
    @FXML
    private HBox hca;
    private GridPane grindca;
    private Pane pan;
    @FXML
    private GridPane gridCa;
    @FXML
    private Label hora;
    @FXML
    private Label mesa;
    @FXML
    private TextField numeros;
    
    @FXML
    private TableColumn<CuentaMesa, String> colNombre;
    @FXML
    private TableColumn<CuentaMesa, Double> colPrecio;
    @FXML
    private TableView<CuentaMesa> tablaCuenta;
    @FXML
    private TableColumn<CuentaMesa, Integer> unds;
    @FXML
    private TableColumn<CuentaMesa, Double> total;
   
     ObservableList<CuentaMesa> listaTablaCuenta = FXCollections.observableArrayList();
    @FXML
    private Label dineroTotal;
    
    
    private double aniadirDinero;
    HashMap cantidad = new HashMap<String,CuentaMesa>();
    @FXML
    private Button atras;
    private PlanoMesasController pmc;
    @FXML
    private Button btnPonerCantidad;
    @FXML
    private Button btnPonerPrecio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
         dineroTotal.setText("Total: "+aniadirDinero+"€");
          bd = new bd();
        //listaTabla.addAll(getProductos());
        
        
        
        tablaCuenta.setPlaceholder(new Label(""));
        getCategorias();
        //int column = 6;
        //int row = 0;
        
        obtenerHora();

        
       /* for(int i = 0; i < listaTabla.size();i++)
        {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/vista/producto.fxml"));
            VBox ap = loader.load(); 
            
            ProductoControlador controlador = loader.getController();
            controlador.setData(listaTabla.get(i));
            
            if (column == 6)
            {
                column = 0;
                row ++;
            }
            
            grind.add(ap,column++, row);
           // GridPane.setMargin(ap,new Insets(15));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    
        }*/
      Pane p = new Pane();
      scrollCategorias.setContent(p);
     
      
    int columna = 0;
    int spacing = 30;
        for(int i = 0; i < listaCa.size();i++)
        {
        try
        {
            Categoria categoria = listaCa.get(i);
            categoria.setPrefSize(150, 150);
                       // categoria.setBackground(new Background(new BackgroundFill(Paint.valueOf(categoria.getColor()),CornerRadii.EMPTY,Insets.EMPTY)));
System.out.println(categoria.getColor());
             categoria.setStyle("-fx-background-color:"+categoria.getColor()+";"
                        +"-fx-border-color: black;"
                        + "-fx-effect: dropshadow( gaussian, rgba(0, 0, 0, 0.7), 10, 0.5, 0.0, 0.0 );"
                        + "-fx-background-radius: 20;" 
                        + "-fx-border-radius: 20;"
                        + "-fx-font-size:20;"
                        + "-fx-font-weight:bold;");
            categoria.setText(categoria.getNombre());
            categoria.setLayoutX(spacing*(i+1) + categoria.getPrefWidth()*i);
            categoria.setLayoutY(20);
            categoria.setController(this);
       
             p.getChildren().add(categoria);
            
            //GridPane.setMargin(categoria,new Insets(20));
            
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
       // scrollCategorias.setContent(p);
    
        }
        showTable();
    } 
    
    
    private ObservableList<Producto> getProductos(String categoria)
    {
        
        
        ObservableList<Producto> listaPro = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM producto where nombreCategoria = '"+categoria+"'";
        
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
      private ObservableList<Categoria> getCategorias()
    {
        
        
        listaCa = FXCollections.observableArrayList();
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
                listaCa.add(categoria);
                
                
                        
            }
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaCa;
        
    }
      public void setData(String nombre)
      { 
         grind.getChildren().clear();
          ObservableList<Producto> listaTabla = FXCollections.observableArrayList();
          listaTabla.addAll(getProductos(nombre));
          //getProductos(nombre);
          int column = 7;
          int row = 0;
          
          for(int i = 0; i < listaTabla.size();i++)
        {
        try
        {
            if(listaTabla.get(i).getImagen()!=null)
            {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/vista/producto.fxml"));
            VBox ap = loader.load(); 
            
            ProductoControlador controlador = loader.getController();
            controlador.setData(listaTabla.get(i));
            controlador.recibirParametros(this);
            
            if (column == 7)
            {
                column = 0;
                row ++;
            }
            
            grind.add(ap,column++, row);
            GridPane.setMargin(ap,new Insets(15));
            }
            else
            {
                 FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/vista/producto_1.fxml"));
            VBox ap = loader.load(); 
            
            ProductoControlador1 controlador = loader.getController();
            controlador.setData(listaTabla.get(i));
            controlador.recibirParametros(this);
            
            if (column == 7)
            {
                column = 0;
                row ++;
            }
            
            grind.add(ap,column++, row);
            GridPane.setMargin(ap,new Insets(15));
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    
        }
      }

    @FXML
    private void limpiar(MouseEvent event) {
        CuentaMesa cm = this.tablaCuenta.getSelectionModel().getSelectedItem();
        
        if(cm!=null)
        {
             //String query = "DELETE FROM `cuentamesa` WHERE `cuentamesa`.`id` = '"+cm.getNombre()+"'";
             //String query1 = "DELETE FROM cuentamesa WHERE nombreProducto = '"+cm.getNombre()+"' LIMIT 1";
             String query2 = "UPDATE cuentamesa SET  cantidad = cantidad -1,total = total - precioProducto WHERE cuentamesa.id='"+cm.getId()+"'";

            executeQuery(query2);
            showTable();
        }
    }
    public void obtenerHora()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        System.out.println("yyyy/MM/dd HH:mm:ss-> "+dtf.format(LocalDateTime.now()));
        int horas,minutos,segundos;
        horas = calendar.get(Calendar.HOUR_OF_DAY);
        minutos = calendar.get(Calendar.MINUTE);
        segundos = calendar.get(Calendar.SECOND);
        hora.setText(dtf.format(LocalDateTime.now()));
    }
    public void setObtenerMesa(String nombreMesa)
    {
        
        
        mesa.setText("Mesa: "+nombreMesa);
        this.nombreMesa = nombreMesa;
       
    }
     public void setObtenerMesass(String nombreMesa)
    {
        
         
        this.listaTablaCuenta = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM cuentaMesa where cuentaMesa.nombreMesa = '"+nombreMesa+"' and cuentaMesa.idFactura IS NULL";
        System.out.println("kkuuuuggggkk"+nombreMesa);
        String query1 = "SELECT * FROM cuentaMesa";
        
        Statement st;
        ResultSet rs;
        
        
        aniadirDinero = 0;
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            CuentaMesa cuentaMesa;
            final Integer suma = 1;
            ArrayList palabras= new ArrayList<String>();
            HashMap<String, CuentaMesa> mapaDeFrecuencias = new HashMap<>();
            
            while(rs.next())
                
            {   
              /*  

//CuentaMesa cm = new CuentaMesa(rs.getString("nombreProducto"),rs.getDouble("precioProducto"));
                    if (mapaDeFrecuencias.containsKey(rs.getString("nombreProducto"))) {
                       
                        CuentaMesa cm = mapaDeFrecuencias.get(rs.getString("nombreProducto"));
                        cm.setUds(cm.getUds()+1);
                     mapaDeFrecuencias.put(rs.getString("nombreProducto"), cm);
                    } else {
                        CuentaMesa cm2 = new CuentaMesa(rs.getString("nombreProducto"),rs.getDouble("precioProducto"));
                        cm2.setUds(1);
                              mapaDeFrecuencias.put(rs.getString("nombreProducto"), cm2);
                 }
                    
                */ cuentaMesa = new CuentaMesa(rs.getInt("cantidad"),rs.getString("nombreProducto"),rs.getDouble("precioProducto"),rs.getDouble("total"));  
              this.listaTablaCuenta.add(cuentaMesa);
                
                //cuentaMesa = new CuentaMesa(rs.getInt("id"),312,rs.getString("nombreProducto"),rs.getDouble("precioProducto"),23);
               // this.listaTablaCuenta.add(cuentaMesa);
               // aniadirDinero = aniadirDinero+cuentaMesa.getTotal();
                
                aniadirDinero = aniadirDinero+cuentaMesa.getTotal();
                        
            }
            /*for (HashMap.Entry<String, CuentaMesa> entry : mapaDeFrecuencias.entrySet()) {
                
            System.out.printf("Palabra '%s' con frecuencia %d\n", entry.getKey(), entry.getValue().getUds());
           cuentaMesa = new CuentaMesa(entry.getValue().getUds(),entry.getKey(),entry.getValue().getPrecio(),(entry.getValue().getPrecio()*entry.getValue().getUds()));
           this.listaTablaCuenta.add(cuentaMesa);
           
           
        }*/
            
            dineroTotal.setText("Total: "+aniadirDinero+"€");
            System.out.println("jjjjjjjjjjjjjjjj "+ cantidad.size());
            
           
        this.unds.setCellValueFactory(new PropertyValueFactory<CuentaMesa,Integer>("Uds"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory<CuentaMesa,String>("Nombre"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory<CuentaMesa,Double>("Precio"));
      this.total.setCellValueFactory(new PropertyValueFactory<CuentaMesa,Double>("Total"));
            tablaCuenta.setItems(listaTablaCuenta);
           
            
            
            
           
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    

    @FXML
    private void pulsar8(MouseEvent event) {
        numeros.setText(numeros.getText()+"8");
    }

    @FXML
    private void pulsar7(MouseEvent event) {
        numeros.setText(numeros.getText()+"7");
    }

    @FXML
    private void pulsar9(MouseEvent event) {
        numeros.setText(numeros.getText()+"9");
    }

    @FXML
    private void pulsar2(MouseEvent event) {
        numeros.setText(numeros.getText()+"2");
    }

    @FXML
    private void pulsar4(MouseEvent event) {
        numeros.setText(numeros.getText()+"4");
    }

    @FXML
    private void pulsar5(MouseEvent event) {
        numeros.setText(numeros.getText()+"5");
    }

    @FXML
    private void pulsar6(MouseEvent event) {
        numeros.setText(numeros.getText()+"6");
    }

    @FXML
    private void pulsar3(MouseEvent event) {
        numeros.setText(numeros.getText()+"3");
    }

    @FXML
    private void pulsarLimpiar(MouseEvent event) {
        numeros.setText("");
    }

    @FXML
    private void pulsarPunto(MouseEvent event) {
        numeros.setText(numeros.getText()+".");
    }

    @FXML
    private void pulsar0(MouseEvent event) {
        numeros.setText(numeros.getText()+"0");
    }
    
    public void setDataInfoProductos(String nombre,double precio)
    {
        ObservableList<CuentaMesa> listaCuenta= getTablaCuenta();
        CuentaMesa aux=null;
        
        for(int i = 0;i<listaCuenta.size();i++)
        {
            if(nombre.equalsIgnoreCase(listaCuenta.get(i).getNombre()))
            {
                aux =listaCuenta.get(i);
                
            }
        }
        
        
            if(aux==null)
            {
                try
                {
         String query = "INSERT INTO `cuentamesa` (`id`, `nombreMesa`,`nombreProducto`,`precioProducto`,`idFactura`,`cantidad`,`total`) VALUES (NULL, '"+this.nombreMesa+"','"+nombre+"','"+precio+"', NULL,1,'"+precio+"')";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            Connection con = bd.getConnection();
        
        
        Statement st;
       
        
       
            st = con.createStatement();
            st.executeUpdate(query);
            showTable();
                }
                catch(Exception e)
                {
                    
                }
            
            }
            else
            {
            
            
                
      
       
            String query1 ="UPDATE cuentamesa SET total = (cantidad+1) * precioProducto , cantidad = cantidad +1 WHERE cuentamesa.nombreProducto='"+nombre+"'and cuentamesa.id='"+aux.getId()+"'";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query1);
            showTable();
        
            
        
            }
          
        
        
    }
    
    public String getNombreMesa()
    {
        return this.nombreMesa;
    }
      public void showTable()
    {
           ObservableList<CuentaMesa> listaCuenta= getTablaCuenta();
           
        this.unds.setCellValueFactory(new PropertyValueFactory<CuentaMesa,Integer>("Uds"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory<CuentaMesa,String>("Nombre"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory<CuentaMesa,Double>("Precio"));
      this.total.setCellValueFactory(new PropertyValueFactory<CuentaMesa,Double>("Total"));
            tablaCuenta.setItems(listaCuenta);
      
           
    }
         public ObservableList<CuentaMesa> getTablaCuenta()
    {
        
        String aux = this.nombreMesa;
        this.listaTablaCuenta = FXCollections.observableArrayList();
        Connection con = bd.getConnection();
        String query = "SELECT * FROM cuentaMesa where cuentaMesa.nombreMesa = '"+this.nombreMesa+"' and cuentaMesa.idfactura IS NULL";
        //System.out.println("kkkk"+aux);
        String query1 = "SELECT * FROM cuentaMesa";
        
        Statement st;
        ResultSet rs;
        
        
        aniadirDinero = 0;
        try
        {
            st = con.createStatement();
            rs = st.executeQuery(query);
            CuentaMesa cuentaMesa;
            final Integer suma = 1;
            ArrayList palabras= new ArrayList<String>();
            HashMap<String, CuentaMesa> mapaDeFrecuencias = new HashMap<>();
            
            while(rs.next())
            {       //CuentaMesa cm = new CuentaMesa(rs.getString("nombreProducto"),rs.getDouble("precioProducto"));
                   // if (mapaDeFrecuencias.containsKey(rs.getString("nombreProducto"))) {
                       
                     //   CuentaMesa cm = mapaDeFrecuencias.get(rs.getString("nombreProducto"));
                     //   cm.setUds(cm.getUds()+1);
                   //  mapaDeFrecuencias.put(rs.getString("nombreProducto"), cm);
                   // } else {
                       // CuentaMesa cm2 = new CuentaMesa(rs.getString("nombreProducto"),rs.getDouble("precioProducto"));
                      //  cm2.setUds(1);
                           //   mapaDeFrecuencias.put(rs.getString("nombreProducto"), cm2);
               //  }
                    
                
                
                //cuentaMesa = new CuentaMesa(rs.getInt("id"),312,rs.getString("nombreProducto"),rs.getDouble("precioProducto"),23);
               // this.listaTablaCuenta.add(cuentaMesa);
               // aniadirDinero = aniadirDinero+cuentaMesa.getTotal();
                
              cuentaMesa = new CuentaMesa(rs.getInt("id"),rs.getInt("cantidad"),rs.getString("nombreProducto"),rs.getDouble("precioProducto"),rs.getDouble("total"));  
              if(cuentaMesa.getUds()!=0)
              {
              this.listaTablaCuenta.add(cuentaMesa);
              aniadirDinero = aniadirDinero+cuentaMesa.getTotal();
              }
              else
              {
                  String query2 = "DELETE FROM `cuentamesa` WHERE `cuentamesa`.`id` = '"+cuentaMesa.getId()+"' and `cuentamesa`.`idfactura` IS NULL and `cuentamesa`.`nombremesa`='"+this.nombreMesa+"'";
                  executeQuery(query2);
                  System.out.println("BORRANDO: "+cuentaMesa.getId());
                  
              }
                        
            }
            /*for (HashMap.Entry<String, CuentaMesa> entry : mapaDeFrecuencias.entrySet()) {
                
            System.out.printf("Palabra '%s' con frecuencia %d\n", entry.getKey(), entry.getValue().getUds());
           cuentaMesa = new CuentaMesa(entry.getValue().getUds(),entry.getKey(),entry.getValue().getPrecio(),(entry.getValue().getPrecio()*entry.getValue().getUds()));
           this.listaTablaCuenta.add(cuentaMesa);
           
           
        }*/
            
            dineroTotal.setText("Total: "+aniadirDinero+"€");
            System.out.println("jjjjjjjjjjjjjjjj "+ cantidad.size());
           
            
            
            
           
           
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return this.listaTablaCuenta;
        
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
    private void imprimirTicket(MouseEvent event) {
        if(!this.listaTablaCuenta.isEmpty())
        {
        ClasesDeTicket cdt = new ClasesDeTicket();
        cdt.preforma(this.listaTablaCuenta,aniadirDinero);
        String query2 = "UPDATE mesa SET  estado = 2 WHERE mesa.nombre='"+nombreMesa+"'";

            executeQuery(query2);
        }
        
    }

    @FXML
    public void pagos(MouseEvent event) {
        try
            {
           
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/pagos.fxml"));
            
            Pane root = loader.load();
            PagosController controlador = (PagosController)loader.getController();
           List<CuentaMesa> al = new ArrayList<CuentaMesa>();
            al = getTablaCuenta();
            for(int i=0;i<this.listaTablaCuenta.size();i++)
            {
                System.out.println("Gestor: "+this.listaTablaCuenta.get(i).getNombreMesa()+aniadirDinero);
            }
            controlador.cantidad(aniadirDinero,nombreMesa);
            controlador.setController(this);
            //controlador.setObtenerMesass(nombre);
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
             stage.initStyle( StageStyle.TRANSPARENT );
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            //stage.setMaximized(true);
            stage.show();
            
            
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }

    @FXML
    private void atras(MouseEvent event) {
           if(!this.listaTablaCuenta.isEmpty())  
       { 
           
           int estado =0;
             String query = "SELECT estado FROM mesa where nombre = '"+nombreMesa+"'";
             System.out.println("nombre de la mesa"+ nombreMesa);
        Statement st;
        ResultSet rs;
   
        try
        {
            Connection con = bd.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            
            while(rs.next())
            {
                    estado = rs.getInt("estado");
           
            }
            rs.close();
            st.close();
          
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(estado!=2)
        {
             String query2 = "UPDATE mesa SET  estado = 1 WHERE mesa.nombre='"+nombreMesa+"'";
             executeQuery(query2);
        }

            
            }
            else
            {
             String query2 = "UPDATE mesa SET  estado = 0 WHERE mesa.nombre='"+nombreMesa+"'";

            executeQuery(query2);
            }
           pmc.verMesas();
         Stage stage = (Stage) this.atras.getScene().getWindow();
        stage.close();
        
    }

   
public void getPM(PlanoMesasController pmc)
{
    this.pmc = pmc;
}

    @FXML
    private void cobrarr(MouseEvent event) {
        try
            {
                System.out.println("aaaab"+numeros.getText());
              if(!numeros.getText().equals(""))
              {
           int totalEntregado=Integer.parseInt(numeros.getText());
            if(totalEntregado>=aniadirDinero)
            {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/cobrar.fxml"));
            
            Pane root = loader.load();
            CobrarController controlador = (CobrarController)loader.getController();
            controlador.pagado(totalEntregado,0,totalEntregado,aniadirDinero,this.nombreMesa);
            //controlador.setObtenerMesass(nombre);
            controlador.setController(this);
            Scene scene = new Scene (root);
            Stage stage = new Stage();
             stage.initStyle( StageStyle.TRANSPARENT );
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            //stage.setMaximized(true);
            stage.show();
            getTablaCuenta();
            numeros.setText("");
           // Stage stage2 = (Stage) this.cobrarr.getScene().getWindow();
            //stage2.close();
            }
            
            else
            {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Dinero entregado insuficiente.");
            alert.showAndWait(); 
            }
              }
              else
              {
                      Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una cantidad.");
            alert.showAndWait(); 
              }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }

    @FXML
    private void ponerCantidad(MouseEvent event) {
         CuentaMesa cm = this.tablaCuenta.getSelectionModel().getSelectedItem();
        
        if(!numeros.getText().equals("")&&cm!=null)
        {
             Double cantidad=Double.parseDouble(numeros.getText());
             String query1 ="UPDATE cuentamesa SET cantidad = '"+cantidad+"',total = '"+cantidad+"'* precioProducto WHERE cuentamesa.nombreProducto='"+cm.getNombre()+"'and cuentamesa.id='"+cm.getId()+"'";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query1);
            showTable();
        }
        
    }

    @FXML
    private void ponerPrecio(MouseEvent event) {
          CuentaMesa cm = this.tablaCuenta.getSelectionModel().getSelectedItem();
        
        if(!numeros.getText().equals("")&&cm!=null)
        {
             Double cantidadPrecio=Double.parseDouble(numeros.getText());
             String query1 ="UPDATE cuentamesa SET precioProducto = '"+cantidadPrecio+"',total = ('"+cantidadPrecio+"') * cantidad WHERE cuentamesa.nombreProducto='"+cm.getNombre()+"'and cuentamesa.id='"+cm.getId()+"'";
            //String query = "INSERT INTO 'tipo_mesa' (nombre,descripcion) VALUES ('"+nombre+"','"+descripcion+"')";
            executeQuery(query1);
            showTable();
        }
    }

    @FXML
    private void pulsar1(MouseEvent event) {
        numeros.setText(numeros.getText()+"1");
    }
    
}
