/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author skatt
 */
public class Mesa extends Button{
    
    private int id;
    private int idTipoMesa;
    private int numeroMesa;
    private String nombre;
    private double x;
    private double y;
    private double mousex = 0;
    private double mousey = 0;
    private Button view;
    private boolean dragging = false;
    private boolean moveToFront = true;
    private int estado;
    private PlanoMesasController pmc;

    public Mesa() {
        init();
    }
    public Mesa (String nombre,double x,double y)
    {
        init();
        this.nombre=nombre;
        this.x=x;
        this.y=y;
    }
    public Mesa (String nombre)
    {   init();
        this.nombre = nombre;
    }
     public Mesa (String nombre,int estado)
    {   
        this.nombre = nombre;
        this.estado = estado;
    }
    public Mesa (int id)
    {
        this.id=id;
    }
    public Mesa (int id,String nombre, int idTipoMesa)
    {
        this.id=id;
        this.nombre=nombre;
        this.idTipoMesa=idTipoMesa;
        
    }
    public Mesa(int id,String nombre,int idTipoMesa,int numeroMesa, double x, double y,int estado) {
        this.id = id;
        this.nombre = nombre;
        this.idTipoMesa=idTipoMesa;
        this.numeroMesa=numeroMesa;
        this.x = x;
        this.y = y;
        this.estado = estado;
        infoMesa();
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }
    
    public int getIdTipoMesa() {
        return idTipoMesa;
    }

    public void setIdTipoMesa(int idTipoMesa) {
        this.idTipoMesa = idTipoMesa;
    }

    public int getIdd() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
    private void init() {

        onMousePressedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

               
                mousex = event.getSceneX();
                mousey = event.getSceneY();

                x = getLayoutX();
                System.out.println(x);
                System.out.println(nombre);
               
                y = getLayoutY();

                if (isMoveToFront()) {
                    toFront();
                }
            }
        });

        
        onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            

                double offsetX = event.getSceneX() - mousex;
                double offsetY = event.getSceneY() - mousey;

                x += offsetX;
                y += offsetY;

                double scaledX = x;
                double scaledY = y;

                setLayoutX(scaledX);
                setLayoutY(scaledY);

                dragging = true;

                // again set current Mouse x AND y position
                mousex = event.getSceneX();
                mousey = event.getSceneY();

                event.consume();
            }
        });

        onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                dragging = false;
            }
        });

    }
    public void infoMesa()
    {
          onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
            try
            {
            /*Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/vista/GestionMesas.fxml").openStream());
            GestionMesasController controlador = (GestionMesasController)loader.getController();
          controlador.setObtenerMesa(nombre);
          
                 System.out.println("ññasd"+nombre);
                 
        
            Scene scene = new Scene (root);
           
            
           //controlador.setObtenerMesa(nombre);
            primaryStage.setScene(scene);
           
            primaryStage.setMaximized(true);
            primaryStage.show();
            controlador.setObtenerMesass(nombre);
            //controlador1.setObtenerMesass(nombre);
            System.out.println("sasasa");
            */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionMesas.fxml"));
            
            Pane root = loader.load();
            GestionMesasController controlador = (GestionMesasController)loader.getController();
            controlador.getPM(pmc);
            controlador.setObtenerMesa(nombre);
            controlador.setObtenerMesass(nombre);
        
            Scene scene = new Scene (root);
            Stage stage = new Stage();
              stage.initStyle( StageStyle.TRANSPARENT );
            
            scene.getStylesheets().add(getClass().getResource("/modelo/panelDiseño.css").toString());
            scene.setFill(Color.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            System.out.println("sasasa");
            
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
                
            }
        });
    }

    /**
     * @return the dragging
     */
    protected boolean isDragging() {
        return dragging;
    }


    /**
     * @return the view
     */
    public Button getView() {
        return view;
    }

    /**
     * @param moveToFront the moveToFront to set
     */
    public void setMoveToFront(boolean moveToFront) {
        this.moveToFront = moveToFront;
    }

    /**
     * @return the moveToFront
     */
    public boolean isMoveToFront() {
        return moveToFront;
    }
    
    public void removeNode(Button n) {
        getChildren().remove(n);
    }
    public void getPM(PlanoMesasController pmc)
{
    this.pmc = pmc;
}
    
    
}
