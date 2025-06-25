package com.turismo.backend.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turismo.backend.model.TurismoEntry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonReaderService {

    private final Gson gson = new Gson();

    public List<TurismoEntry> cargarDatos() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream("TurismoComunidades.json")
                )
        )) {
            Type listType = new TypeToken<List<TurismoEntry>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }
    public List<TurismoEntry> filtrarDatos(String comunidad, String provincia, String mes) {
        List<TurismoEntry> datos = cargarDatos();

        return datos.stream()
                .filter(e -> comunidad == null || comunidad.equalsIgnoreCase(e.getFrom().getComunidad()))
                .filter(e -> provincia == null || provincia.equalsIgnoreCase(e.getFrom().getProvincia()))
                .filter(e -> mes == null || mes.equalsIgnoreCase(e.getTimeRange().getPeriod()))
                .toList();
    }
}

