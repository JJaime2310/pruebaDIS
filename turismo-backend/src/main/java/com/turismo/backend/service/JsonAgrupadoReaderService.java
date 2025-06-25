package com.turismo.backend.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turismo.backend.model.TurismoAgrupadoEntry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonAgrupadoReaderService {

    private final Gson gson = new Gson();

    public List<TurismoAgrupadoEntry> cargarDatos() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream("Comunidades_Agrupadas.json")
                )
        )) {
            Type wrapperType = new TypeToken<Map<String, List<TurismoAgrupadoEntry>>>() {}.getType();
            Map<String, List<TurismoAgrupadoEntry>> wrapper = gson.fromJson(reader, wrapperType);
            return wrapper.get("");  // <- clave vacía del JSON
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer el archivo agrupado JSON", e);
        }
    }
}
