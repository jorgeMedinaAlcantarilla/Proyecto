


import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.jnlp.PrintService;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skatt
 */
public class prueba {
    
    public static void main (String[]args) throws FileNotFoundException
    {
          String Header =
                   "******Resturant Management*******;"
                   // + "Date:"+dd.format(gg.getTime())+"       Time:"+ddd.format(gg.getTime())+"\n;"
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
       String[] parts = zbill.split(";");   
         
    
        
}
    
    
   
}
