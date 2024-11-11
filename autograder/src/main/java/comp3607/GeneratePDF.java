package comp3607;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {

    public static void generatePDF(String reportContent, String destinationDir, String fileName) throws DocumentException, IOException {
        // Create the full path for the PDF file
        Path outputPath = Paths.get(destinationDir, fileName + ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath.toString()));
        document.open();

        // Set fonts for the report content
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Add report content as a single paragraph (you may split or format as needed)
        document.add(new Paragraph(reportContent, contentFont));

        document.close();
        System.out.println("PDF report generated at: " + outputPath.toString());
    }
}
