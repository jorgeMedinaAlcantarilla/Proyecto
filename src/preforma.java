
import controlador.Ticket1;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skatt
 */
public class preforma {
    
    public static void main (String []args)
    {
        GregorianCalendar gg = new GregorianCalendar();
        SimpleDateFormat dd = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat ddd = new SimpleDateFormat("HH:mm");
        
           String Header =
                   "******Resturant Management*******;"
                    + "Date:"+dd.format(gg.getTime())+"       Time:"+ddd.format(gg.getTime())+"\n;"
                    + "         Original Receipt        \n;"
                    + "---------------------------------\n;"
                    + "Product Description              \n;"
                    + "Uds.           Nombre      Precio\n;"
                  
                    + "---------------------------------\n;";
        
             //String a=jLabel4.getText()+"      "+jTextField1.getText()+"      "+jLabel2.getText()+"\n;";
             //String h=Header+a;
        String ant  =
                    "\n;---------------------------------\n;"
                    + "Total Amount:      Rs."+10000+"\n;"
                    + "---------------------------------\n;"
                    + "---------------------------------\n;"
                    + "For Further Detail                \n;"
                    + "Please Call/Whatsapp 0340-8351689\n;"
                    + "---------------------------------\n;"
                    + "       Software Developed by      \n ;"
                    + "        Skatt9 S.A                ;"
                    + "*********************************\n;"
                    + "            Thank You             \n;"
                    + "_________________________________\n;";
        
        String zbill = Header + ant;
        Ticket1 t1 = new Ticket1();
        t1.printCard(zbill);
        
        
    }
    
}
