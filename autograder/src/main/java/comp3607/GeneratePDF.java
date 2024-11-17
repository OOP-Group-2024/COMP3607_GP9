package comp3607;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {
    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font CONTENT_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12);
    private static final Font ERROR_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    
    public static void generatePDF(String reportContent, String destinationDir, String studentId) {
        try {
            // Create filename with student ID or "unknown"
            String fileName = String.format("report_%s.pdf", studentId);
            Path outputPath = Paths.get(destinationDir, fileName);
    
            // Create PDF document
            Document document = new Document(PageSize.A4, 36, 36, 60, 36);
            PdfWriter.getInstance(document, new FileOutputStream(outputPath.toString()));
            document.open();
    
            // Add header
            addHeader(document, studentId);
            
            // Parse and add content sections
            addFormattedContent(document, reportContent);
    
            document.close();
            System.out.println("PDF report generated successfully at: " + outputPath.toString());
    
        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addHeader(Document document, String studentId) throws DocumentException {
        Paragraph title = new Paragraph("Assignment Evaluation Report", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        Paragraph studentInfo = new Paragraph("Student ID: " + studentId, HEADER_FONT);
        studentInfo.setSpacingAfter(20);
        document.add(studentInfo);
    }

    private static void addFormattedContent(Document document, String reportContent) throws DocumentException {
        // Split content into sections
        String[] sections = reportContent.split("\n\n");
        
        for (String section : sections) {
            if (section.trim().isEmpty()) continue;

            // Determine section type and format accordingly
            if (section.startsWith("Passed Tests")) {
                addSection(document, "Passed Tests", section.substring("Passed Tests :".length()), false);
            } else if (section.startsWith("Errors")) {
                addSection(document, "Errors", section.substring("Errors:".length()), true);
            } else if (section.startsWith("Test Summary")) {
                addSection(document, "Test Summary", section.substring("Test Summary:".length()), false);
            } else if (section.startsWith("Total Marks")) {
                addMarksSection(document, section);
            }
        }
    }

    private static void addSection(Document document, String title, String content, boolean isError) throws DocumentException {
        // Add section title
        Paragraph sectionTitle = new Paragraph(title, HEADER_FONT);
        sectionTitle.setSpacingBefore(20);
        sectionTitle.setSpacingAfter(10);
        document.add(sectionTitle);

        // Add section content
        Font font = isError ? ERROR_FONT : CONTENT_FONT;
        for (String line : content.split("\n")) {
            if (line.trim().isEmpty()) continue;
            Paragraph paragraph = new Paragraph(line.trim(), font);
            paragraph.setIndentationLeft(20);
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        }
    }

    private static void addMarksSection(Document document, String marksContent) throws DocumentException {
        Paragraph marksPara = new Paragraph(marksContent.trim(), HEADER_FONT);
        marksPara.setSpacingBefore(30);
        marksPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(marksPara);
    }
}