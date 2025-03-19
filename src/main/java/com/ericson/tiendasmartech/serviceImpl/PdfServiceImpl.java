package com.ericson.tiendasmartech.serviceImpl;

import com.ericson.tiendasmartech.dto.PedidoDetalleDto;
import com.ericson.tiendasmartech.dto.PedidoDto;
import com.ericson.tiendasmartech.service.PdfService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {
    @Override
    public File generarPdf(PedidoDto pedido) {
        try {
            ByteArrayOutputStream baos = designPdf(pedido);
            String filePath = savePdf(baos, pedido.id());
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                return path.toFile();
            }
        } catch (DocumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private String savePdf(ByteArrayOutputStream baos, long id) {
        String directoryPath = "src/main/resources/static/pdf";
        String fileName = "reporte_pago_" + id + ".pdf";
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

    private ByteArrayOutputStream designPdf(PedidoDto pedido) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, stream);
        document.open();

        PdfPTable headerTable = new PdfPTable(new float[]{2, 8});
        PdfPTable detailTable = new PdfPTable(new float[]{1, 5, 1, 1});
        Paragraph p = new Paragraph();
        PdfPCell cell = new PdfPCell();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.BLUE);
        Font camposFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        Font datosFont = new Font(Font.FontFamily.COURIER, 8, Font.NORMAL, BaseColor.BLACK);
        Paragraph title = new Paragraph("Boleta de pedido", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        p.setSpacingBefore(20);
        document.add(p);

        headerTable.setWidthPercentage(100);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPhrase(new Phrase("Numero de Pago", camposFont));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase(": " + pedido.numero(), new Font(Font.FontFamily.COURIER, 10)));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("Importe Total", camposFont));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase(": S/ " + pedido.total(), new Font(Font.FontFamily.COURIER, 10)));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("Cliente", camposFont));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase(": " + pedido.usuario().nombres(), new Font(Font.FontFamily.COURIER, 10)));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("Entrega", camposFont));
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase(": " + pedido.entrega(), new Font(Font.FontFamily.COURIER, 10)));
        headerTable.addCell(cell);
        document.add(headerTable);

        p.setSpacingBefore(20);
        document.add(p);

        detailTable.setWidthPercentage(100);
        cell.setBorder(Rectangle.BOX);
        cell.setPadding(4f);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPhrase(new Phrase("Cantidad", camposFont));
        detailTable.addCell(cell);
        cell.setPhrase(new Phrase("Nombre", camposFont));
        detailTable.addCell(cell);
        cell.setPhrase(new Phrase("Pecio (S/.)", camposFont));
        detailTable.addCell(cell);
        cell.setPhrase(new Phrase("Total (S/.)", camposFont));
        detailTable.addCell(cell);
        cell.setBackgroundColor(BaseColor.WHITE);
        for (PedidoDetalleDto detalle : pedido.pedidoDetalles()) {
            cell.setPhrase(new Phrase(String.valueOf(detalle.cantidad()), datosFont));
            detailTable.addCell(cell);
            cell.setPhrase(new Phrase(detalle.producto().nombre(), datosFont));
            detailTable.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(detalle.precio()), datosFont));
            detailTable.addCell(cell);
            cell.setPhrase(new Phrase(String.format("%.2f", detalle.precio() * detalle.cantidad()), datosFont));
            detailTable.addCell(cell);
        }
        document.add(detailTable);
        document.close();
        return stream;
    }
}
