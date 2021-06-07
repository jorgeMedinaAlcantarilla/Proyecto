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
public class Factura {
    
    private int id;
    private double efectivo;
    private double tarjeta;
    private double total;
    private double entregado;
    private int idcierrecaja;
    private String fecha;
    private String hora;
    private int cancelado;
    

    public Factura() {
    }

    public Factura(int id, double efectivo, double tarjeta, double total, double entregado,String fecha, String hora) {
        this.id = id;
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        this.total = total;
        this.entregado = entregado;
        this.fecha = fecha;
        this.hora=hora;
        
    }
      public Factura(int id, double efectivo, double tarjeta, double total, double entregado,String fecha, String hora,int cancelado,int idcierrecaja) {
        this.id = id;
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        this.total = total;
        this.entregado = entregado;
        this.fecha = fecha;
        this.hora=hora;
        this.cancelado = cancelado;
        this.idcierrecaja = idcierrecaja;
        
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(double efectivo) {
        this.efectivo = efectivo;
    }

    public double getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(double tarjeta) {
        this.tarjeta = tarjeta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getEntregado() {
        return entregado;
    }

    public void setEntregado(double entregado) {
        this.entregado = entregado;
    }

    public int getIdcierrecaja() {
        return idcierrecaja;
    }

    public void setIdcierrecaja(int idcierrecaja) {
        this.idcierrecaja = idcierrecaja;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    
    
    
}
