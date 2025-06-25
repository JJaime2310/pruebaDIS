package com.turismo.backend.controller;

import com.turismo.backend.model.TurismoEntry;
import com.turismo.backend.service.JsonReaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;

@RestController
public class TurismoController {

    private final JsonReaderService jsonReaderService = new JsonReaderService();

    @GetMapping("/api/turismo")
    public List<TurismoEntry> getFiltrado(
            @RequestParam(required = false) String comunidad,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String mes
    ) {
        List<TurismoEntry> datos = jsonReaderService.cargarDatos();
        for (TurismoEntry entry : datos) {
            System.out.println("FROM: " + entry.getFrom().getComunidad());
        }
        return jsonReaderService.filtrarDatos(comunidad, provincia, mes);
    }
}
