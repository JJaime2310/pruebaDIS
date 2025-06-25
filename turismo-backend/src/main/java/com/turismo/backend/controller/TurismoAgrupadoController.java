package com.turismo.backend.controller;

import com.turismo.backend.model.TurismoAgrupadoEntry;
import com.turismo.backend.service.JsonAgrupadoReaderService;
import com.turismo.backend.service.PdfGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;



import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/turismo-agrupado")
public class TurismoAgrupadoController {

    private final JsonAgrupadoReaderService jsonAgrupadoReaderService = new JsonAgrupadoReaderService();

    @GetMapping
    public List<TurismoAgrupadoEntry> getFiltrado(
            @RequestParam(required = false) String comunidad,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String mes
    ) {
        List<TurismoAgrupadoEntry> datos = jsonAgrupadoReaderService.cargarDatos();

        return datos.stream()
                .filter(entry -> comunidad == null || entry.getFrom().getComunidad().equalsIgnoreCase(comunidad))
                .filter(entry -> provincia == null || entry.getFrom().getProvincia().equalsIgnoreCase(provincia))
                .filter(entry -> mes == null || entry.getTimeRange().getPeriod().toLowerCase().contains(mes.toLowerCase()))
                .collect(Collectors.toList());
    }
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generarPdf(
            @RequestParam(required = false) String comunidad,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String mes
    ) {
        List<TurismoAgrupadoEntry> datos = jsonAgrupadoReaderService.cargarDatos().stream()
                .filter(entry -> comunidad == null || entry.getFrom().getComunidad().equalsIgnoreCase(comunidad))
                .filter(entry -> provincia == null || entry.getFrom().getProvincia().equalsIgnoreCase(provincia))
                .filter(entry -> mes == null || entry.getTimeRange().getPeriod().equalsIgnoreCase(mes))
                .collect(Collectors.toList());

        PdfGeneratorService pdfService = new PdfGeneratorService();
        ByteArrayInputStream bis = pdfService.generarPdf(datos);

        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=turismo.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

}
