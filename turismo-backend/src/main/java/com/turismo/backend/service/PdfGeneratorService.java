package com.turismo.backend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.turismo.backend.model.TurismoAgrupadoEntry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGeneratorService {

    public ByteArrayInputStream generarPdf(List<TurismoAgrupadoEntry> datos) {
        Document documento = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(documento, out);
            documento.open();

            Font titulo = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font cuerpo = new Font(Font.HELVETICA, 12);

            documento.add(new Paragraph("Informe de Turismo Agrupado", titulo));
            documento.add(Chunk.NEWLINE);

            for (TurismoAgrupadoEntry entry : datos) {
                documento.add(new Paragraph(
                        "Comunidad: " + entry.getFrom().getComunidad(), cuerpo));
                documento.add(new Paragraph(
                        "Provincia: " + entry.getFrom().getProvincia(), cuerpo));
                documento.add(new Paragraph(
                        "Periodo: " + entry.getTimeRange().getPeriod(), cuerpo));
                documento.add(new Paragraph(
                        "Total: " + entry.getTotal(), cuerpo));
                documento.add(Chunk.NEWLINE);
            }

            documento.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
