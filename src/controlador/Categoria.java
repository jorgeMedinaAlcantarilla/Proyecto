/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author skatt
 */
public class Categoria extends Button {
    
    private int id;
    private String nombre;
    private String color;
    GestionMesasController confMesas;

    public Categoria() {
    }

    public Categoria(int id, String nombre, String color) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
    }
  
     public Categoria(String nombre, String color) {
         this.nombre = nombre;
        this.color = color;
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
    public void infoMesa()
    {
          onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            
                confMesas.setData(getNombre());
                
            }
        });
    }
    public void setController(GestionMesasController gmc)
    {
        this.confMesas=gmc;
        infoMesa();
    }
    
    
    
    
}
