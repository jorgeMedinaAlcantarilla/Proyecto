/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author skatt
 */
public class CuentaMesa {
    
    private int id;
    private int idFactura;
    private String nombreMesa;
    private String nombre;
    private double precio;
    private boolean estado;
    private int uds;
    private double total;
   

    public CuentaMesa() {
    }

    public CuentaMesa(int id,int uds,String nombre,double precio,double total) {
        this.id = id;
        this.uds = uds;
        this.nombre = nombre;
        this.precio = precio;
        this.total = total;
    }
      public CuentaMesa(int id,int uds,String nombre,double precio,double total,int idFactura,String nombreMesa) {
        this.id = id;
        this.uds = uds;
        this.nombre = nombre;
        this.precio = precio;
        this.total = total;
        this.idFactura = idFactura;
        this.nombreMesa = nombreMesa;
    }
    public CuentaMesa(int uds,String nombre,double precio,double total) {
        
        this.uds = uds;
        this.nombre = nombre;
        this.precio = precio;
        this.total = total;
    }
     public CuentaMesa(String nombre,double precio) {
        
        
        this.nombre = nombre;
        this.precio = precio;
        
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
     
    
    /*public CuentaMesa(int id, String nombreMesa, String nombreProducto, double precioProducto, boolean estado) {
        this.id = id;
        this.nombreMesa = nombreMesa;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.estado = estado;
    }
    public CuentaMesa(String nombreProducto, double precioProducto)
    {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMesa() {
        return nombreMesa;
    }

    public void setNombreMesa(String nombreMesa) {
        this.nombreMesa = nombreMesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreProducto) {
        this.nombre = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precioProducto) {
        this.precio = precioProducto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getUds() {
        return uds;
    }

    public void setUds(int cantidad) {
        this.uds = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
          
    
}
