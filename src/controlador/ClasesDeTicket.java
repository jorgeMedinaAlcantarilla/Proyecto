package controlador;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javafx.collections.ObservableList;
import proyecto1.bd;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skatt
 */
public class ClasesDeTicket {
    
    bd bd;
    private String nombree;
    private String direccion;
    private String nif;
  
   
   
   public ClasesDeTicket()
   {
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
               
                this.nombree = rs.getString("nombre");
                direccion = rs.getString("direccion");
                nif = rs.getString("nif");
           
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
        
        
    
   }
    
    public void preforma(ObservableList<CuentaMesa> cuentaMesa,double total)
    {
         
       
        
        
        
        
         GregorianCalendar gg = new GregorianCalendar();
        SimpleDateFormat dd = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat ddd = new SimpleDateFormat("HH:mm");
        
         String aux="";
         
         String unidades="";
         String unidadesMax="";
         String nombre = "";
         String nombreMax="";
         String cantidad="";
         String cantidadMax="";
         
        
        
        for(int i = 0;i<cuentaMesa.size();i++)
       
        {
            unidades = String.valueOf(cuentaMesa.get(i).getUds());
            //StringBuilder undsMax = new StringBuilder(unidades);
            //undsMax.ensureCapacity(20);
            //rightpad(unidades,10);
            //unidadesMax = unidades.substring(0,8);
            nombre = cuentaMesa.get(i).getNombre();
           // nombreMax = nombre.substring(0,8);
           // StringBuilder nmbre = new StringBuilder(nombre);
            //nmbre.ensureCapacity(20);
            
            cantidad = String.valueOf(cuentaMesa.get(i).getTotal())+"€";
          //  cantidadMax = cantidad.substring(0,8);
            //StringBuilder cntd = new StringBuilder(cantidad);
            //cntd.ensureCapacity(20);
            
            aux = aux +rightpad(unidades,11)+rightpad(nombre,12)+rightpad(cantidad,15)+"\n;";
        }
        
           String Header =
                   "******  "+this.nombree+"  *******;"
                    + ""+direccion+"\n;"
                    + ""+nif+"\n;"
                    + ""+dd.format(gg.getTime())+"  "+ddd.format(gg.getTime())+"\n;"
                    + "Estado: SIN COBRAR.                 \n;"
                    + "---------------------------------\n;"
                    + "Descripcion de productos              \n;"
                    + "Uds.       Nombre      Precio\n;"
                  
                    + "---------------------------------\n;";
        
                    String a=aux+";";
             String h=Header+a;
        String ant  =
                    "\n;---------------------------------\n;"
                    + "Total :           "+total+"€   \n;"
                    + "---------------------------------\n;"
                    + "---------------------------------\n;"
                    
                    + "---------------------------------\n;"
                    + "       Software Developed by      \n ;"
                    + "        Skatt9 S.A                ;"
                    + "*********************************\n;"
                    + "       Gracias por si visita             \n;"
                    + "_________________________________\n;";
        
        String zbill = h + ant;
        Ticket1 t1 = new Ticket1();
        t1.printCard(zbill);
        System.out.println(aux);
        
       
        
    }
    public void ticketPagado(ObservableList<CuentaMesa> cuentaMesa,double total,int llaveFactura)
    {
            bd = new bd();
        Connection con = bd.getConnection();
        Statement st;
        ResultSet rs;
        Factura f= null;
        System.out.println("ll"+llaveFactura);
                  try
        {
            String query3 = "SELECT * FROM factura where id = '"+llaveFactura+"'";
            st = con.createStatement();
            rs = st.executeQuery(query3);
           
            
            while(rs.next())
            {
              f=new Factura(rs.getInt("id"),rs.getDouble("efectivo"),rs.getDouble("tarjeta"),rs.getDouble("total"),rs.getDouble("entregado"),rs.getString("fecha"),rs.getString("hora"),rs.getInt("cancelado"),rs.getInt("idcierrecaja"));

              
           
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
        
        
         GregorianCalendar gg = new GregorianCalendar();
        SimpleDateFormat dd = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat ddd = new SimpleDateFormat("HH:mm");
        
         String aux="";
         
         String unidades="";
         String unidadesMax="";
         String nombre = "";
         String nombreMax="";
         String cantidad="";
         String cantidadMax="";
         
        
        
        for(int i = 0;i<cuentaMesa.size();i++)
       
        {
            unidades = String.valueOf(cuentaMesa.get(i).getUds());
            //StringBuilder undsMax = new StringBuilder(unidades);
            //undsMax.ensureCapacity(20);
            //rightpad(unidades,10);
            //unidadesMax = unidades.substring(0,8);
            nombre = cuentaMesa.get(i).getNombre();
           // nombreMax = nombre.substring(0,8);
           // StringBuilder nmbre = new StringBuilder(nombre);
            //nmbre.ensureCapacity(20);
            
            cantidad = String.valueOf(cuentaMesa.get(i).getTotal())+"€";
          //  cantidadMax = cantidad.substring(0,8);
            //StringBuilder cntd = new StringBuilder(cantidad);
            //cntd.ensureCapacity(20);
            
            aux = aux +rightpad(unidades,11)+rightpad(nombre,12)+rightpad(cantidad,15)+"\n;";
        }
        
           String Header =
                   "******  "+this.nombree+"  *******;"
                    + ""+direccion+"\n;"
                    + ""+nif+"\n;"
                    + ""+f.getFecha()+"  "+f.getHora()+"\n;"
                    + "Numero: "+f.getId()+".                 \n;"
                    + "---------------------------------\n;"
                    + "Descripcion de productos              \n;"
                    + "Uds.       Nombre      Precio\n;"
                  
                    + "---------------------------------\n;";
        
                    String a=aux+";";
             String h=Header+a;
        String ant  =
                    "\n;---------------------------------\n;"
                    + "Total :           "+total+"€   \n;"
                    + "---------------------------------\n;"
                    + "---------------------------------\n;"
                    
                    + "---------------------------------\n;"
                    + "       Software Developed by      \n ;"
                    + "        Skatt9 S.A                ;"
                    + "*********************************\n;"
                    + "       Gracias por si visita             \n;"
                    + "_________________________________\n;";
        
        String zbill = h + ant;
        Ticket1 t1 = new Ticket1();
        t1.printCard(zbill);
        System.out.println(aux);
        
       
        
    }
     public static String stringStatico(String string, int length) {
        return String.format("%1$"+length+ "s", string);
        }
     public static String rightpad(String text, int length) {
    return String.format("%-" + length + "." + length + "s", text);
}
    
}

