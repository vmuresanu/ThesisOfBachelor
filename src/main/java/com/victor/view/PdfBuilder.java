package com.victor.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.victor.model.UserDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by vmuresanu on 5/13/2017.
 */
@Component
public class PdfBuilder extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        final ServletContext servletContext = request.getSession().getServletContext();
        final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        final String temperotyFilePath = tempDirectory.getAbsolutePath();
        final String customPath = "D://temp/";

        UserDocument doc = (UserDocument) model.get("doc");

        System.out.println(model.get("user"));

        String fileName = "PDF_REPORT_" + doc.getUser().getSsoId() + "_" + doc.getId() + ".pdf";

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        createPDF(customPath + "\\" + fileName, doc);
        System.out.println(model.get("user"));
        System.out.println(model.get("doc"));

        try {
            createPDF(customPath + "\\" + fileName, doc);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = convertPDFToByteArrayOutputStream(customPath + "\\" + fileName);
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private static Document createPDF(String file, UserDocument userDocument) {

        Document document = null;

        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);

            addTitlePage(document);

            createContent(document, userDocument);

            document.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static void addMetaData(Document document) {
        document.addTitle("Generate PDF report");
        document.addSubject("Generate PDF report");
        document.addAuthor("Victor");
        document.addCreator("Victor");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {

        Paragraph preface = new Paragraph("PDF Report", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD));
        preface.setAlignment(Element.ALIGN_CENTER);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        preface.add(new Paragraph("Report created on "
                + simpleDateFormat.format(new Date()), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
        document.add(preface);
        document.add(Chunk.NEWLINE);

    }

    private static void documentText(Document document, UserDocument userDocument) throws DocumentException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        DecimalFormat df = new DecimalFormat("00");

        document.add(new Paragraph("Nr: " + userDocument.getId(), contentFont));
        document.add(new Paragraph("Date: " + simpleDateFormat.format(new Date()), contentFont));
        document.add(new Paragraph("FirstName: " + userDocument.getFirstName(), contentFont));
        document.add(new Paragraph("LastName: " + userDocument.getLastName(), contentFont));
        document.add(new Paragraph("Email: " + userDocument.getEmail(), contentFont));
        document.add(new Paragraph("Document Type: " + userDocument.getDocumentType(), contentFont));
        document.add(new Paragraph("Need Notary: " + userDocument.getNeedNotary(), contentFont));
        document.add(new Paragraph("Deadline: " +
                userDocument.getDeadline().getDayOfMonth() + "/" +
                df.format(userDocument.getDeadline().getMonthOfYear()) + "/" +
                userDocument.getDeadline().getYear() + " " +
                df.format(userDocument.getDeadline().getHourOfDay()-3) + ":" +
                df.format(userDocument.getDeadline().getMinuteOfHour()), contentFont));
        document.add(new Paragraph("Price: " + userDocument.getPrice() + " MDL", contentFont));

        Paragraph secrName = new Paragraph("Created by : " + userDocument.getUser().getFirstName()
                + " " + userDocument.getUser().getLastName());
        secrName.setAlignment(Element.ALIGN_RIGHT);
        Paragraph secrSign = new Paragraph("Signature: _______________");
        secrSign.setAlignment(Element.ALIGN_RIGHT);

        Paragraph clientName = new Paragraph("Client : " + userDocument.getFirstName()
                + " " + userDocument.getLastName());
        clientName.setAlignment(Element.ALIGN_RIGHT);
        Paragraph clientSign = new Paragraph("Signature: _______________");
        clientSign.setAlignment(Element.ALIGN_RIGHT);

        document.add(secrName);
        document.add(secrSign);
        document.add(clientName);
        document.add(clientSign);
    }

    private static void createContent(Document document, UserDocument userDocument) throws DocumentException {
        Paragraph documentLine = new Paragraph("------------------------------------------------------------------");
        documentLine.setAlignment(Element.ALIGN_CENTER);
        documentText(document, userDocument);
        for (int i = 0; i < 5; i++){
            document.add(Chunk.NEWLINE);
        }
        document.add(documentLine);
        document.add(Chunk.NEWLINE);
        documentText(document, userDocument);
    }

    private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos;
    }
}

