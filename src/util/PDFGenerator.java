package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import model.Commande;
import model.Plat;

import java.io.FileOutputStream;

public class PDFGenerator {

    public static void generate(Commande cmd) {
        try {
            String fileName = "facture_" + System.currentTimeMillis() + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("FACTURE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Commande n° : " + cmd.getId()));
            document.add(new Paragraph("Date : " + new java.util.Date()));
            document.add(new Paragraph(" "));

            // Tableau des plats (sans quantité, chaque plat apparaît une fois)
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Plat");
            table.addCell("Prix (TND)");

            double total = 0;
            for (Plat p : cmd.getPlats()) {
                table.addCell(p.getNom());
                table.addCell(String.format("%.2f", p.getPrix()));
                total += p.getPrix();
            }
            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total : " + String.format("%.2f TND", total)));

            document.add(new Paragraph("\nMerci et à bientôt !"));
            document.close();

            System.out.println("PDF généré : " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}