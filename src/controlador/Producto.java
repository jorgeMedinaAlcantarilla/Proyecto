/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Blob;

/**
 *
 * @author skatt
 */
public class Producto {
    
    private int id;
    private String nombre;
    private String Categoria;
    private Blob imagen;
    private String precio;

    public Producto() {
    }
    public Producto(String nombre,String precio)
    {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(int id, String nombre, String Categoria, Blob imagen, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.Categoria = Categoria;
        this.imagen = imagen;
        this.precio = precio;
    }

    public int getId() {
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

    public String getCategoria() {
        return Categoria;
    }

    public void seCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    
    
    
    
}
