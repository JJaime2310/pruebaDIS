package org.vaadin.example.models;

import org.vaadin.example.models.TurismoComunidad;

import java.util.List;
import java.util.Map;

public class ComunidadesAgrupadas {
    private Map<String, List<TurismoComunidad>> comunidades;

    // Getters y setters
    public Map<String, List<TurismoComunidad>> getComunidades() {
        return comunidades;
    }

    public void setComunidades(Map<String, List<TurismoComunidad>> comunidades) {
        this.comunidades = comunidades;
    }
}
