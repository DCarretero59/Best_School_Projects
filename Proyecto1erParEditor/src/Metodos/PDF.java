/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.io.FileOutputStream;

/**
 *
 * @author Diego
 */
public class PDF {

    public PDF(String route) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(route + ".pdf"));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            //PdfTemplate template = contentByte.createTemplate(jPanel9.getWidth(), jPanel9.getHeight());
           // Graphics2D g2 = template.createGraphics(jPanel9.getWidth(), jPanel9.getHeight());
           // g2.scale(0.8, 0.8);
            //jPanel9.printAll(g2); // also tried with jp.paint(g2), same result
          //  g2.dispose();
            //document.close();
            //contentByte.addTemplate(template, 25, -140);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }

        }
    }
}
