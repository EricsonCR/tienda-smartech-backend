package com.ericson.tiendasmartech.util;

import com.ericson.tiendasmartech.dto.PedidoDetalleDto;
import com.ericson.tiendasmartech.dto.PedidoDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PdfUtil {

    public File generarPdf(PedidoDto pedido) {
        try {
            ByteArrayOutputStream baos = designPdf(pedido);
            String filePath = savePdf(baos, pedido.numero());
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                return path.toFile();
            }
        } catch (DocumentException | IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String savePdf(ByteArrayOutputStream baos, String numero) {
        String directoryPath = "src/main/resources/static/pdf";
        String fileName = "P" + numero + ".pdf";
        File directory = new File(directoryPath);

        if (!directory.exists()) directory.mkdirs();

        File pdfFile = new File(directory, fileName);

        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(baos.toByteArray());
            return directoryPath + File.separator + fileName;
        } catch (IOException e) {
            return "";
        }
    }

    public ByteArrayOutputStream designPdf(PedidoDto pedido) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, stream);

        document.open();
        document = datosHeader(document);
        document = datosPedido(document, pedido);
        document = datosDetalle(document, pedido);
        document.close();

        return stream;
    }

    private Document datosDetalle(Document document, PedidoDto pedido) throws DocumentException {
        PdfPCell cell;
        PdfPTable detalleTable = new PdfPTable(new float[]{1, 5, 1, 1});
        Font camposFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        Font datosFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
        //DETALLE DEL PEDIDO
        cell = new PdfPCell();
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(4f);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPhrase(new Phrase("Cantidad", camposFont));
        detalleTable.addCell(cell);
        cell.setPhrase(new Phrase("Nombre", camposFont));
        detalleTable.addCell(cell);
        cell.setPhrase(new Phrase("Pecio (S/.)", camposFont));
        detalleTable.addCell(cell);
        cell.setPhrase(new Phrase("Total (S/.)", camposFont));
        detalleTable.addCell(cell);
        cell.setBackgroundColor(BaseColor.WHITE);
        for (PedidoDetalleDto detalle : pedido.pedidoDetalles()) {
            cell.setPhrase(new Phrase("" + detalle.cantidad(), datosFont));
            detalleTable.addCell(cell);
            cell.setPhrase(new Phrase(detalle.producto().nombre(), datosFont));
            detalleTable.addCell(cell);
            cell.setPhrase(new Phrase("" + detalle.precio(), datosFont));
            detalleTable.addCell(cell);
            cell.setPhrase(new Phrase(String.format("%.2f", detalle.precio() * detalle.cantidad()), datosFont));
            detalleTable.addCell(cell);
        }
        if (pedido.precio_envio() > 0) {
            cell.setPhrase(new Phrase("1", datosFont));
            detalleTable.addCell(cell);
            cell.setPhrase(new Phrase("Gastos de envio courier", datosFont));
            detalleTable.addCell(cell);
            cell.setPhrase(new Phrase("" + pedido.precio_envio(), datosFont));
            detalleTable.addCell(cell);
            cell.setPhrase(new Phrase("" + pedido.precio_envio(), datosFont));
            detalleTable.addCell(cell);
        }
        detalleTable.setSpacingAfter(20);
        detalleTable.setWidthPercentage(100);
        document.add(detalleTable);

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(120f);
        table.setLockedWidth(true);
        table.setSpacingAfter(20f);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        cell = new PdfPCell(new Paragraph("Sub Total", camposFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(4f);
        table.addCell(cell);

        String subtotal = String.format("S/ %.2f", (pedido.total() + pedido.precio_envio()) * (0.82));
        cell = new PdfPCell(new Paragraph(subtotal, datosFont));
        cell.setPadding(4f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("IGV (18%)", camposFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(4f);
        table.addCell(cell);

        String igv = String.format("S/ %.2f", (pedido.total() + pedido.precio_envio()) * (0.18));
        cell = new PdfPCell(new Paragraph(igv, datosFont));
        cell.setPadding(4f);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Sub Total", camposFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(4f);
        table.addCell(cell);

        String total = String.format("%.2f", pedido.total() + pedido.precio_envio());
        cell = new PdfPCell(new Paragraph("S/ " + total, datosFont));
        cell.setBackgroundColor(BaseColor.YELLOW);
        cell.setPadding(4f);
        table.addCell(cell);

        document.add(table);

        LineSeparator linea = new LineSeparator();
        linea.setLineWidth(0.5f);
        document.add(linea);

        return document;
    }

    private Document datosPedido(Document document, PedidoDto pedido) throws DocumentException {

        Font camposFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        Font datosFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
        PdfPTable pedidoPTable = new PdfPTable(new float[]{2, 8});
        PdfPCell cell;

        //DATOS DEL PEDIDO
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPhrase(new Phrase("Numero de Pago", camposFont));
        pedidoPTable.addCell(cell);
        cell.setPhrase(new Phrase(": " + pedido.numero(), datosFont));
        pedidoPTable.addCell(cell);
        cell.setPhrase(new Phrase("Importe Total", camposFont));
        pedidoPTable.addCell(cell);
        String total = String.format("%.2f", pedido.total() + pedido.precio_envio());
        cell.setPhrase(new Phrase(": S/ " + total, datosFont));
        pedidoPTable.addCell(cell);
        cell.setPhrase(new Phrase("Cliente", camposFont));
        pedidoPTable.addCell(cell);
        cell.setPhrase(new Phrase(": " + pedido.usuario().nombres(), datosFont));
        pedidoPTable.addCell(cell);
        cell.setPhrase(new Phrase("Entrega", camposFont));
        pedidoPTable.addCell(cell);
        cell.setPhrase(new Phrase(": " + pedido.entrega(), datosFont));
        pedidoPTable.addCell(cell);
        pedidoPTable.setSpacingAfter(20);
        pedidoPTable.setWidthPercentage(100);
        document.add(pedidoPTable);
        return document;
    }

    private Document datosHeader(Document document) throws DocumentException, IOException {
        Font camposFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        Font datosFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
        Font rucBoletaFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
        Font tituloBoletaFont = new Font(Font.FontFamily.COURIER, 14, Font.NORMAL, BaseColor.BLACK);

        Paragraph razonSocial = new Paragraph("CORPORACION SMARTECH EIRL", camposFont);
        razonSocial.setAlignment(Element.ALIGN_CENTER);
        Paragraph direccion1 = new Paragraph("Av. Garcilaso de la Vega N°. 1251", datosFont);
        direccion1.setAlignment(Element.ALIGN_CENTER);
        Paragraph direccion2 = new Paragraph("Tienda 159 C.C COMPUPLAZA", datosFont);
        direccion2.setAlignment(Element.ALIGN_CENTER);
        Paragraph telefonos = new Paragraph("Teléfono(s): (51-1) 423 5463", datosFont);
        telefonos.setAlignment(Element.ALIGN_CENTER);
        Paragraph url = new Paragraph("www.smartech.com.pe", datosFont);
        url.setAlignment(Element.ALIGN_CENTER);
        Paragraph rucBoleta = new Paragraph("RUC : 20509700789", rucBoletaFont);
        rucBoleta.setAlignment(Element.ALIGN_CENTER);
        Paragraph tituloBoleta = new Paragraph("BOLETA ELECTRONICA", tituloBoletaFont);
        tituloBoleta.setAlignment(Element.ALIGN_CENTER);
        Paragraph serieBoleta = new Paragraph("B005-0013804", tituloBoletaFont);
        serieBoleta.setAlignment(Element.ALIGN_CENTER);

        PdfPTable headerTable = new PdfPTable(new float[]{2, 3, 3});
        Image image = Image.getInstance("src/main/resources/static/img/logo-pdf.png");
        image.scaleToFit(130, 130);
        PdfPCell cell;

        //IMAGEN - DATOS EMPRESA - DATOS FACTURACION
        cell = new PdfPCell(image);
        cell.setFixedHeight(80f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(PdfPCell.NO_BORDER);
        headerTable.addCell(cell);

        cell = new PdfPCell();
        cell.setFixedHeight(80f);
        cell.addElement(razonSocial);
        cell.addElement(direccion1);
        cell.addElement(direccion2);
        cell.addElement(telefonos);
        cell.addElement(url);
        cell.setBorder(PdfPCell.NO_BORDER); // Sin bordes
        headerTable.addCell(cell);

        cell = new PdfPCell();
        cell.setFixedHeight(80f);
        cell.addElement(rucBoleta);
        cell.addElement(tituloBoleta);
        cell.addElement(serieBoleta);
        cell.setBorder(PdfPCell.BOX); // Agregar borde a la celda
        headerTable.addCell(cell);

        headerTable.setSpacingAfter(20);
        headerTable.setWidthPercentage(100);
        document.add(headerTable);

        return document;
    }
}
