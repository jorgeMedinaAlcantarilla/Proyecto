/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class PagosController implements Initializable {

    @FXML
    private TextField entregando;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btnlimpiar;
    @FXML
    private Button btn0;
    @FXML
    private Button btncoma;
    @FXML
    private Button btnTarjeta;
    @FXML
    private Label total;
    @FXML
    private Label entregado;
    @FXML
    private Label pendiente;
    @FXML
    private TableView<Pagos> tablaPagos;
    private double cantidad;
    @FXML
    private TableColumn<?, ?> colTipo;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private Button borrar;
    
    private ObservableList<Pagos> pagos;
    @FXML
    private Label en;
    private double entregadoo;
    private double pendientee;
    @FXML
    private Label mensajeError;
    ObservableList<CuentaMesa> ol;
    private double tarjeta=0;
    private double efectivo=0;
    @FXML
    private Button cobrarr;
    private GestionMesasController controller;
    private String nombreMesa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pagos = FXCollections.observableArrayList();
        
        this.colTipo.setCellValueFactory(new PropertyValueFactory("Tipo"));
         this.colCantidad.setCellValueFactory(new PropertyValueFactory("Cantidad"));

    }    

    @FXML
    private void btn7(MouseEvent event) {
    entregando.setText(entregando.getText()+"7");
    mensajeError.setText("");
    }

    @FXML
    private void btn8(MouseEvent event) {
        entregando.setText(entregando.getText()+"8");
        mensajeError.setText("");
    }

    @FXML
    private void btn9(MouseEvent event) {
        entregando.setText(entregando.getText()+"9");
        mensajeError.setText("");
    }

    @FXML
    private void btn4(MouseEvent event) {
        entregando.setText(entregando.getText()+"4");
        mensajeError.setText("");
    }

    @FXML
    private void btn5(MouseEvent event) {
        entregando.setText(entregando.getText()+"5");
        mensajeError.setText("");
    }

    @FXML
    private void btn6(MouseEvent event) {
        entregando.setText(entregando.getText()+"6");
        mensajeError.setText("");
    }

    @FXML
    private void btn1(MouseEvent event) {
        entregando.setText(entregando.getText()+"1");
        mensajeError.setText("");
    }

    @FXML
    private void btn2(MouseEvent event) {
        entregando.setText(entregando.getText()+"2");
        mensajeError.setText("");
    }

    @FXML
    private void btn3(MouseEvent event) {
        entregando.setText(entregando.getText()+"3");
        mensajeError.setText("");
    }

    @FXML
    private void btnLimpiar(MouseEvent event) {
        entregando.setText("");
        mensajeError.setText("");
    }

    @FXML
    private void btn0(MouseEvent event) {
        entregando.setText(entregando.getText()+"0");
        mensajeError.setText("");
    }

    @FXML
    private void btncoma(MouseEvent event) {
        entregando.setText(".");
        mensajeError.setText("");
    }

    @FXML
    private void btnTarjeta(MouseEvent event) {
        String cantidad = entregando.getText();
        
        String tipo = "tarjeta";
        Pagos p;
        if(!cantidad.isEmpty())
        {
            p = new Pagos(tipo,cantidad);
            this.pagos.add(p);
            this.tablaPagos.setItems(pagos);
            
            double aux = Double.parseDouble(cantidad);
            
         if(aux> pendientee)
              {
                 
                 tarjeta = tarjeta + pendientee;
                 pendientee = pendientee-aux;
            entregadoo = entregadoo + aux;
              }
              else
              {
              tarjeta = tarjeta + aux;
            pendientee = pendientee-aux;
            entregadoo = entregadoo + aux;
              }
                if (pendientee <0)
            {
                pendiente.setText("Pendiente: 0,00€");
            }
                else
                {
                     pendiente.setText("Pendiente: "+pendientee+"€");
                }
           
            entregado.setText("Entregado: "+entregadoo+"€");
            entregando.setText("");
            
        }
        else
        {
            tarjeta = tarjeta + pendientee;
            String aux1 = String.valueOf(pendientee);
            p = new Pagos(tipo,aux1);
            this.pagos.add(p);
            this.tablaPagos.setItems(pagos);
            cantidad = "0";
              double aux = Double.parseDouble(cantidad);
              aux = pendientee;
            pendientee = pendientee-aux;
            entregadoo = entregadoo + aux;
            
            mensajeError.setText("");
              if (pendientee <0)
            {
                pendiente.setText("Pendiente: 0,00€");
            }
              else
              {
                  pendiente.setText("Pendiente: "+pendientee+"€");
              }
            
            entregado.setText("Entregado: "+entregadoo+"€");
            entregando.setText("");
            //tarjeta = tarjeta+ entregadoo;
        }
    }

    @FXML
    private void btnEfectivo(MouseEvent event) {
         String cantidad = entregando.getText();
        String tipo = "ejectivo";
         Pagos p;
        if(!cantidad.isEmpty())
        {
             p = new Pagos(tipo,cantidad);
            this.pagos.add(p);
            this.tablaPagos.setItems(pagos);
              double aux = Double.parseDouble(cantidad);
              if(aux> pendientee)
              {
                 
                 efectivo = efectivo + pendientee;
                 pendientee = pendientee-aux;
            entregadoo = entregadoo + aux;
              }
              else
              {
              efectivo = efectivo + aux;
            pendientee = pendientee-aux;
            entregadoo = entregadoo + aux;
              }
             if (pendientee <0)
            {
                pendiente.setText("Pendiente: 0,00€");
            }
             else
             {
               pendiente.setText("Pendiente: "+pendientee+"€");
             }
             
            entregado.setText("Entregado: "+entregadoo+"€");
            entregando.setText("");
        }
        else
        {
            efectivo = efectivo + pendientee;
            String aux1 = String.valueOf(pendientee);
            p = new Pagos(tipo,aux1);
            this.pagos.add(p);
            this.tablaPagos.setItems(pagos);
            cantidad = "0";
              double aux = Double.parseDouble(cantidad);
               aux = pendientee;
            pendientee = pendientee-aux;
            entregadoo = entregadoo + aux;
            
            mensajeError.setText("");
            if (pendientee <0)
            {
                pendiente.setText("Pendiente: 0,00€");
            }
            else
            {
                 pendiente.setText("Pendiente: "+pendientee+"€");
            }
             
            entregado.setText("Entregado: "+entregadoo+"€");
           // efectivo = efectivo+ entregadoo;
            entregando.setText("");
        }
      
    }

    @FXML
    private void cobrar(MouseEvent event) {
        
         try
            {
           
            if(entregadoo>=cantidad)
            {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/cobrar.fxml"));
            System.out.println("efectivo: "+efectivo);
            System.out.println("tarjeta: "+tarjeta);
            Pane root = loader.load();
            CobrarController controlador = (CobrarController)loader.getController();
            controlador.pagado(efectivo,tarjeta,entregadoo,cantidad,this.nombreMesa);
            //controlador.setObtenerMesass(nombre);
            controlador.setController(this.controller);
            Scene scene = new Scene (root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
             stage.initStyle( StageStyle.TRANSPARENT );
           
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            //stage.setMaximized(true);
            stage.show();
            controller.getTablaCuenta();
            Stage stage2 = (Stage) this.cobrarr.getScene().getWindow();
            stage2.close();
            }
            
            else
            {
                mensajeError.setText("Saldo insuficiente.");
            }
            
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }
    
    public void  cantidad(double cantidad ,String nombreMesa)
    {
        this.cantidad = cantidad;
        this.pendientee = cantidad;
        total.setText("Total: "+this.cantidad+"€");
        pendiente.setText("Pendiente: "+this.pendientee+"€");
        this.nombreMesa=nombreMesa;
        
        
    }

    @FXML
    private void borrar(MouseEvent event) {
        
        Pagos p = this.tablaPagos.getSelectionModel().getSelectedItem();
        
        if(p!=null)
        {
            this.pagos.remove(p);
            this.tablaPagos.refresh();
            double caux = Double.parseDouble(p.getCantidad());
            entregadoo = entregadoo - caux;
            pendientee = pendientee +caux;
            if (pendientee <0)
            {
                pendiente.setText("Pendiente: 0,00€");
            }
            else
            {
                 pendiente.setText("Pendiente: "+pendientee+"€");
            }
            entregado.setText("Entregado: "+entregadoo+"€");
           
            
            
        }
    }
    
    public void setController(GestionMesasController controller)
    {
        this.controller = controller;
    }
    
    
}
